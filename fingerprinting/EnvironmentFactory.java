import java.util.ArrayList;
import java.util.List;

public class EnvironmentFactory {

    public static Environment createDefaultEnvironment() {

        List<Cell> cells = new ArrayList<>();

        cells.add(createCell(2,2,-45,-60,-50));
        cells.add(createCell(6,2,-50,-65,-55));
        cells.add(createCell(10,2,-48,-62,-53));

        cells.add(createCell(2,6,-40,-55,-45));
        cells.add(createCell(6,6,-42,-58,-47));
        cells.add(createCell(10,6,-46,-61,-52));

        cells.add(createCell(2,10,-43,-57,-48));
        cells.add(createCell(6,10,-44,-59,-49));
        cells.add(createCell(10,10,-47,-63,-54));

        return new Environment(cells);
    }

    private static Cell createCell(double x, double y, int r1, int r2, int r3){

        Position position = new Position(x,y);

        SignalVector vector = new SignalVector(List.of(r1,r2,r3));

        Fingerprint fingerprint = new Fingerprint(vector);

        return new Cell(position,fingerprint);
    }
}