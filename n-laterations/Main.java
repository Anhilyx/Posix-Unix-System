import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Début de la N-Latération ===");

        // 1. Initialisation des données du PDF
        List<AbstractEmetteur> emetteurs = new ArrayList<>();
        emetteurs.add(new Emetteur(new Position(0.5, 0.5, 0.5), 3.0));
        emetteurs.add(new Emetteur(new Position(4.0, 0.0, 0.0), 2.0));
        emetteurs.add(new Emetteur(new Position(4.0, 5.0, 5.0), 4.2));
        emetteurs.add(new Emetteur(new Position(3.0, 3.0, 3.0), 2.5));

        // 2. Variables pour la recherche
        double pas = 0.05; // Précision de la recherche (comme dans l'annexe du PDF)
        double borneMax = 6.0; // Borne maximale de l'espace de recherche (X, Y, Z)
        
        AbstractCell meilleureCellule = null;
        double erreurMinimale = Double.MAX_VALUE;

        // 3. Algorithme de recherche spatiale (Grid Search)
        // On teste chaque "Cell" possible dans l'espace 3D
        for (double i = 0; i <= borneMax; i += pas) {
            for (double j = 0; j <= borneMax; j += pas) {
                for (double k = 0; k <= borneMax; k += pas) {
                    
                    // On instancie la cellule à cette position
                    Cell celluleTest = new Cell(new Position(i, j, k));
                    
                    // On évalue son erreur par rapport aux 4 émetteurs
                    celluleTest.evaluerErreur(emetteurs);
                    
                    // Si l'erreur est plus petite que la meilleure trouvée jusqu'ici, on sauvegarde
                    if (celluleTest.getErreurTotale() < erreurMinimale) {
                        erreurMinimale = celluleTest.getErreurTotale();
                        meilleureCellule = celluleTest;
                    }
                }
            }
        }

        // 4. Affichage du résultat final
        if (meilleureCellule != null) {
            System.out.println("Position estimée du Terminal Mobile : " + meilleureCellule.getPosition());
            System.out.println("Marge d'erreur résiduelle (minimisaton) : " + erreurMinimale);
        }
    }
}