import java.util.Calendar;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Program {
    public static void main(String[] args) {
        AnimationFrame frame = new AnimationFrame("ClockAnimation", 600, 600, 10);
        frame.add(new AnimationCanvas());
        frame.setVisible(true);
        frame.start();
    }
}

class AnimationFrame extends JFrame implements Runnable {

    private Thread thread = null;
    private int repaint = 1000;

    public AnimationFrame(String title, int width, int height, int repaint) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        //setResizable(false);
        
        this.thread = new Thread(this);
        this.repaint = repaint;
    }

    public void start() { if (this.thread != null) this.thread.start(); }
    public void stop() { this.thread = null; }

    public void run() {
        while (Thread.currentThread() == this.thread) {
            try { Thread.sleep(this.repaint); } catch (InterruptedException e) {}
            repaint();
        }
    }
}

class AnimationCanvas extends JPanel {

    private final double rotationOffcet = 90;
    private final Vector2D center = new Vector2D(295, 280);

    private final double TimeMax = 60.0;
    private final double TimeToDegRate60 = 360.0 / 60.0;
    private final double TimeToDegRate12 = 360.0 / 12.0;

    private final double OuterCircleSize = 550;
    private final double InnerCircleSize = 25;
    private final double HourLineStart   = 200;
    private final double HourLineEnd     = 260;
    private final double MinuteLineStart = 240;
    private final double MinuteLineEnd   = 260;

    private final double HourHandScale   = 1.4;
    private final double MinuteHandScale = 1.4;
    private final double SecondHandScale = 1.4;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        Calendar c = Calendar.getInstance();
        double mili = c.get(Calendar.MILLISECOND) / 1000.0;
        double second = c.get(Calendar.SECOND) + mili;
        double minute = c.get(Calendar.MINUTE) + (second / TimeMax);
        double hour = c.get(Calendar.HOUR) + (minute / TimeMax);

        for (int i = 0; i < TimeMax; i++) {
            if (i % 5 == 0) 
                rotateLine(g, center, TimeToDegRate60 * i, HourLineStart, HourLineEnd);
            else 
                rotateLine(g, center, TimeToDegRate60 * i, MinuteLineStart, MinuteLineEnd);
        }

        Vector2D[] hourVertexs = Matrix.augmented(
            new VertexData().HourHand, new Vector2D(HourHandScale));
        Vector2D[] minuteVertexs = Matrix.augmented(
            new VertexData().MenuteHand, new Vector2D(MinuteHandScale));
        Vector2D[] secondVertexs = Matrix.augmented(
            new VertexData().SecondHand, new Vector2D(SecondHandScale));
        
        drawHand(g2, hourVertexs, center, TimeToDegRate12 * hour, Color.BLACK);
        drawHand(g2, minuteVertexs, center, TimeToDegRate60 * minute, Color.BLACK);
        drawHand(g2, secondVertexs, center, TimeToDegRate60 * second, Color.RED);

        drawOvalCenter(g, g2, center, new Vector2D(OuterCircleSize), Color.GRAY);
        fillOvalCenter(g, g2, center, new Vector2D(InnerCircleSize), Color.RED);
    }

    private void drawHand(Graphics2D g2, Vector2D[] vertexData, Vector2D position, double rotation, Color c) {
        vertexData = Matrix.rotation(vertexData, rotation - rotationOffcet);
        vertexData = Matrix.translation(vertexData, position);
        drawPolygon(g2, vertexData, c);
    }

    private void drawOvalCenter(Graphics g, Graphics2D g2, Vector2D position, Vector2D size, Color c) {
        g2.setPaint(c);
        g.drawOval(
            (int)(position.x - (size.x / 2.0)),
            (int)(position.y - (size.y / 2.0)),
            size.getIntX(), size.getIntY());
        g2.setPaint(Color.BLACK);
    }
    private void fillOvalCenter(Graphics g, Graphics2D g2,  Vector2D position, Vector2D size, Color c) {
        g2.setPaint(c);
        g.fillOval(
            (int)(position.x - (size.x / 2.0)),
            (int)(position.y - (size.y / 2.0)),
            size.getIntX(), size.getIntY());
        g2.setPaint(Color.BLACK);
    }

    private void rotateLine(Graphics g, Vector2D position, double rotation, double start, double length) {
        Vector2D[] data = new Vector2D[] {
            new Vector2D(start, 0),
            new Vector2D(length, 0)
        };
        data = Matrix.rotation(data, rotation - rotationOffcet);
        data = Matrix.translation(data, position);
        g.drawLine(
            data[0].getIntX(), data[0].getIntY(), 
            data[1].getIntX(), data[1].getIntY());
    }

    private void drawPolygon(Graphics2D g2, Vector2D[] points, Color c) {
        g2.setPaint(c);
        g2.fill(new Polygon(
            Vector2D.getIntXpoints(points), 
            Vector2D.getIntYpoints(points),
            points.length));
        g2.setPaint(Color.black);
    }
}

class VertexData {
    public Vector2D[] SecondHand = new Vector2D[] {
        new Vector2D(-50, 0),
        new Vector2D(-40, -5),
        new Vector2D(190, 0),
        new Vector2D(-40, 5)
    };

    public Vector2D[] MenuteHand = new Vector2D[] {
        new Vector2D(-40, 0),
        new Vector2D(-30, -5),
        new Vector2D(170, -2),
        new Vector2D(170 + 2, 0),
        new Vector2D(170, 2),
        new Vector2D(-30, 5)
    };

    public Vector2D[] HourHand = new Vector2D[] {
        new Vector2D(-25, 0),
        new Vector2D(-20, -5),
        new Vector2D(100, -3),
        new Vector2D(100 + 3, 0),
        new Vector2D(100, 3),
        new Vector2D(-20, 5)
    };
}