import java.util.List;

public class Cell extends AbstractCell {
    public Cell(AbstractPosition position) {
        super(position);
    }

    @Override
    public void evaluerErreur(List<AbstractEmetteur> emetteurs) {
        this.erreurTotale = 0.0;
        for (AbstractEmetteur e : emetteurs) {
            this.erreurTotale += e.calculerErreur(this.position);
        }
    }
}