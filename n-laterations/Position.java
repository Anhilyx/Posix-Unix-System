public class Position extends AbstractPosition {
    public Position(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    public double distanceTo(AbstractPosition autre) {
        // d = sqrt((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)
        return Math.sqrt(Math.pow(this.x - autre.getX(), 2) + 
                         Math.pow(this.y - autre.getY(), 2) + 
                         Math.pow(this.z - autre.getZ(), 2));
    }
}