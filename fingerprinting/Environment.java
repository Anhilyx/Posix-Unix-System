import java.util.List;

public class Environment {

    private List<FingerprintCell> cells;

    public Environment(List<FingerprintCell> cells) {
        this.cells = cells;
    }

    public List<FingerprintCell> getCells() {
        return cells;
    }
}