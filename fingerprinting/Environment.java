import java.util.List;

public class Environment {

    private List<Cell> cells;

    public Environment(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Cell> getCells() {
        return cells;
    }
}