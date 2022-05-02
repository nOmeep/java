import javax.swing.*;
import java.awt.*;

public class Solver {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(560, 660);
        window.setBackground(Color.WHITE);
        Cube cube = new Cube(3);
        window.add(cube);
        window.setVisible(true);
        cube.solve();
    }
}
