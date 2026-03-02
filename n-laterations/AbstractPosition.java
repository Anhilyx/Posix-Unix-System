public abstract class AbstractPosition {
    protected double x;
    protected double y;
    protected double z;

    public AbstractPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public abstract double distanceTo(AbstractPosition autre);

    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f)", x, y, z);
    }
}