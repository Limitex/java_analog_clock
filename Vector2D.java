import java.util.function.Function;

public class Vector2D {
    public double x;
    public double y;
    public Vector2D() { this.x = 0.0; this.y = 0.0; }
    public Vector2D(double s) { this.x = s; this.y = s; }
    public Vector2D(double x, double y) { this.x = x; this.y = y; }
    public int getIntX() { return (int)x; }
    public int getIntY() { return (int)y; }

    public Vector2D add(Vector2D vec) {x += vec.x; y += vec.y; return this;}
    public Vector2D set(Vector2D vec) {x = vec.x; y = vec.y; return this;}

    public static double[] getXpoints(Vector2D[] vec) {
        return getPoints(vec, (v) -> v.x);
    }

    public static double[] getYpoints(Vector2D[] vec) {
        return getPoints(vec, (v) -> v.y);
    }

    public static int[] getIntXpoints(Vector2D[] vec) {
        return toInt(getXpoints(vec));
    }

    public static int[] getIntYpoints(Vector2D[] vec) {
        return toInt(getYpoints(vec));
    }

    private static double[] getPoints(Vector2D[] vec, Function<Vector2D, Double> func) {
        double[] points = new double[vec.length];
        for (int index = 0; index < vec.length; index++)
            points[index] = func.apply(vec[index]);
        return points;
    }

    private static int[] toInt(double[] d) {
        int[] r = new int[d.length];
        for (int i = 0; i < d.length; i++) r[i] = (int)d[i];
        return r;
    }
}