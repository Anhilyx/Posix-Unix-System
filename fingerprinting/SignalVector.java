import java.util.List;

public class SignalVector {

    private final List<Integer> rssiValues;

    public SignalVector(List<Integer> rssiValues) {
        this.rssiValues = rssiValues;
    }

    public List<Integer> getValues() {
        return rssiValues;
    }

}