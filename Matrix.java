public class Matrix {
    public static Vector2D[] rotation(Vector2D[] vec, double rotation) {
        double rad = rotation * (Math.PI / 180);
        for (int i = 0; i < vec.length; i++) {
            vec[i].set(new Vector2D(
                vec[i].x * Math.cos(rad) - vec[i].y * Math.sin(rad),
                vec[i].x * Math.sin(rad) + vec[i].y * Math.cos(rad)    
            ));
        }
        return vec;
    }

    public static Vector2D[] augmented(Vector2D[] vec, Vector2D magnification) {
        for (int i = 0; i < vec.length; i++) {
            vec[i].set(new Vector2D(
                vec[i].x * magnification.x,
                vec[i].y * magnification.y
            ));
        }
        return vec;
    }

    public static Vector2D[] translation(Vector2D[] vec, Vector2D movement) {
        for (int i = 0; i < vec.length; i++) {
            vec[i].set(new Vector2D(
                vec[i].x + movement.x,
                vec[i].y + movement.y
            ));
        }
        return vec;
    }
}
