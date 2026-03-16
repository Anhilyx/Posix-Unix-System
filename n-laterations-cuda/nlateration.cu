#include <iostream>
#include <cmath>
#include <cfloat>
#include <iomanip> // Pour l'affichage des décimales

// ============================================================================
// 1. PARAMÈTRES ET CONSTANTES
// ============================================================================
#define NUM_EMITTERS 4

const float PAS = 0.01f;        // Plus précis (0.01 au lieu de 0.05)
const float BORNE_MAX = 6.0f;

struct Emitter {
    float x, y, z;
    float d;
};

const Emitter host_emitters[NUM_EMITTERS] = {
    {0.5f, 0.5f, 0.5f, 3.0f},
    {4.0f, 0.0f, 0.0f, 2.0f},
    {4.0f, 5.0f, 5.0f, 4.2f},
    {3.0f, 3.0f, 3.0f, 2.5f}
};
// ============================================================================

struct Result {
    float erreur;
    float x, y, z;
};

__constant__ Emitter d_emitters[NUM_EMITTERS];

__global__ void gridSearchKernel(Result* d_blockResults, int N, float pas) {
    extern __shared__ Result sdata[];
    unsigned int tid = threadIdx.x;
    unsigned long long idx = blockIdx.x * blockDim.x + threadIdx.x;

    sdata[tid].erreur = FLT_MAX;

    if (idx < (unsigned long long)N * N * N) {
        int ix = idx / (N * N);
        int iy = (idx / N) % N;
        int iz = idx % N;

        float lx = ix * pas;
        float ly = iy * pas;
        float lz = iz * pas;

        float erreur_totale = 0.0f;
        for (int e = 0; e < NUM_EMITTERS; ++e) {
            float dx = lx - d_emitters[e].x;
            float dy = ly - d_emitters[e].y;
            float dz = lz - d_emitters[e].z;
            erreur_totale += fabsf(sqrtf(dx * dx + dy * dy + dz * dz) - d_emitters[e].d);
        }

        sdata[tid].erreur = erreur_totale;
        sdata[tid].x = lx; sdata[tid].y = ly; sdata[tid].z = lz;
    }
    __syncthreads();

    for (unsigned int s = blockDim.x / 2; s > 0; s >>= 1) {
        if (tid < s) {
            if (sdata[tid + s].erreur < sdata[tid].erreur) sdata[tid] = sdata[tid + s];
        }
        __syncthreads();
    }
    if (tid == 0) d_blockResults[blockIdx.x] = sdata[0];
}

int main() {
    int N = (int)(BORNE_MAX / PAS) + 1;
    unsigned long long total_points = (unsigned long long)N * N * N;
    
    std::cout << "--- Configuration RTX 40xx (CUDA 13.0) ---" << std::endl;
    std::cout << "Points a calculer : " << total_points << std::endl;

    cudaMemcpyToSymbol(d_emitters, host_emitters, NUM_EMITTERS * sizeof(Emitter));

    int blockSize = 256;
    int numBlocks = (total_points + blockSize - 1) / blockSize;
    Result *d_blockResults;
    cudaMalloc((void**)&d_blockResults, numBlocks * sizeof(Result));

    // --- CHRONOMETRAGE ---
    cudaEvent_t start, stop;
    cudaEventCreate(&start);
    cudaEventCreate(&stop);

    cudaEventRecord(start); // Top départ

    size_t sharedMemSize = blockSize * sizeof(Result);
    gridSearchKernel<<<numBlocks, blockSize, sharedMemSize>>>(d_blockResults, N, PAS);

    cudaEventRecord(stop); // Top fin
    cudaEventSynchronize(stop);

    float milliseconds = 0;
    cudaEventElapsedTime(&milliseconds, start, stop);
    // ---------------------

    Result* h_blockResults = new Result[numBlocks];
    cudaMemcpy(h_blockResults, d_blockResults, numBlocks * sizeof(Result), cudaMemcpyDeviceToHost);

    Result best = {FLT_MAX, 0, 0, 0};
    for (int i = 0; i < numBlocks; ++i) {
        if (h_blockResults[i].erreur < best.erreur) best = h_blockResults[i];
    }

    std::cout << std::fixed << std::setprecision(4);
    std::cout << "\nRESULTATS :" << std::endl;
    std::cout << "  Position : (" << best.x << ", " << best.y << ", " << best.z << ")" << std::endl;
    std::cout << "  Erreur   : " << best.erreur << std::endl;
    std::cout << "\nPERFORMANCE :" << std::endl;
    std::cout << "  Temps de calcul GPU : " << milliseconds << " ms" << std::endl;

    cudaFree(d_blockResults);
    delete[] h_blockResults;
    cudaEventDestroy(start);
    cudaEventDestroy(stop);

    return 0;
}