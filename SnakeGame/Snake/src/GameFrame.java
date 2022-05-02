import javax.swing.*;
import java.io.IOException;

public final class GameFrame extends JFrame {
    public GameFrame() throws IOException {
        add(new GamePanel(600, 50, 100));
        setTitle("Snake game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
