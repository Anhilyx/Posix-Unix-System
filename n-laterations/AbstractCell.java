import java.util.List;

public abstract class AbstractCell {
    protected AbstractPosition position;
    protected double erreurTotale; // Somme des erreurs par rapport aux émetteurs

    public AbstractCell(AbstractPosition position) {
        this.position = position;
        this.erreurTotale = 0.0;
    }

    public abstract void evaluerErreur(List<AbstractEmetteur> emetteurs);
    
    public double getErreurTotale() { return erreurTotale; }
    public AbstractPosition getPosition() { return position; }
}