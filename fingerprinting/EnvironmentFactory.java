import java.util.ArrayList;
import java.util.List;

public class EnvironmentFactory {

    public static Environment createDefaultEnvironment() {

        List<FingerprintCell> cells = new ArrayList<>();

        cells.add(createCell(2,2, List.of(-38,-27,-54,-13)));
        cells.add(createCell(6,2, List.of(-74,-52,-68,-33)));
        cells.add(createCell(10,2, List.of(-43,-28,-12,-40)));

        cells.add(createCell(2,6, List.of(-34,-27,-38,-41)));
        cells.add(createCell(6,6, List.of(-64,-48,-72,-35)));
        cells.add(createCell(10,6, List.of(-45,-37,-20,-15)));

        cells.add(createCell(2,10, List.of(-17,-50,-44,-33)));
        cells.add(createCell(6,10, List.of(-27,-28,-32,-45)));
        cells.add(createCell(10,10, List.of(-30,-20,-60,-40)));

        return new Environment(cells);
    }

    private static FingerprintCell createCell(double x, double y, List<Integer> values){

        FingerprintPosition position = new FingerprintPosition(x,y);

        SignalVector vector = new SignalVector(values);

        Fingerprint fingerprint = new Fingerprint(vector);

        return new FingerprintCell(position,fingerprint);
    }
}