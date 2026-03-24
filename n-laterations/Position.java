public class Position extends AbstractPosition {
    public Position(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    public double distanceTo(AbstractPosition autre) {
        return Math.sqrt(Math.pow(this.x - autre.getX(), 2) +
                         Math.pow(this.y - autre.getY(), 2) + 
                         Math.pow(this.z - autre.getZ(), 2));
    }
}