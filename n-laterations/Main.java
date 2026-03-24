import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Début de la N-Latération ===");
        double timestamp = System.nanoTime();

        List<AbstractEmetteur> emetteurs = new ArrayList<>();
        emetteurs.add(new Emetteur(new Position(0.5, 0.5, 0.5), 3.0));
        emetteurs.add(new Emetteur(new Position(4.0, 0.0, 0.0), 2.0));
        emetteurs.add(new Emetteur(new Position(4.0, 5.0, 5.0), 4.2));
        emetteurs.add(new Emetteur(new Position(3.0, 3.0, 3.0), 2.5));

        double pas = 0.1;
        double borneMax = 6.0;
        
        AbstractCell meilleureCellule = null;
        double erreurMinimale = Double.MAX_VALUE;

        for (double i = 0; i <= borneMax; i += pas) {
            for (double j = 0; j <= borneMax; j += pas) {
                for (double k = 0; k <= borneMax; k += pas) {
                    
                    Cell celluleTest = new Cell(new Position(i, j, k));
                    celluleTest.evaluerErreur(emetteurs);
                    if (celluleTest.getErreurTotale() < erreurMinimale) {
                        erreurMinimale = celluleTest.getErreurTotale();
                        meilleureCellule = celluleTest;
                    }
                }
            }
        }

        if (meilleureCellule != null) {
            System.out.println("Position estimée du Terminal Mobile : " + meilleureCellule.getPosition());
            System.out.println("Marge d'erreur résiduelle (minimisaton) : " + erreurMinimale);
        }
        System.out.println("Trouvé en " + ((int) ((System.nanoTime() - timestamp) / 10000) / 100) + "ms");
    }
}