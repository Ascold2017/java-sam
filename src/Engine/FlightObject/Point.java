package Engine.FlightObject;

public class Point {
    public double x = 0;
    public double y = 0;
    public double z = 0;
    public double v = 0;

    public Point(double x, double y, double z, double v){
        this.x = x;
        this.y = y;
        this.z = z;
        this.v = v;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.v = 0;
    }

    public Point copy() {
       return new Point(this.x, this.y, this.z, this.v);
    }

    @Override
    public String toString() {
        return "[" + this.x + "," + this.y + "," + this.z + "," + this.v + "]";
    }

}
