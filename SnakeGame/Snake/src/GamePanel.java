import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class GamePanel extends JPanel implements ActionListener {
    private final BufferedImage appleImage = ImageIO.read(new File("./Images/apple.png"));
    private final BufferedImage snakeImage = ImageIO.read(new File("./Images/snake.png"));

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int UNIT_SIZE;
    private final int GAME_UNITS;

    private int[] x;
    private int[] y;

    private int bodyParts;
    private int appleX;
    private int appleY;

    private int applesEaten;
    private int bestScore = 0;

    private boolean running;
    private boolean drawingFinished;

    private enum Directions {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    private Directions direction;

    private static final class XAndY {
        int x;
        int y;

        public XAndY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            XAndY xAndY = (XAndY) o;
            return x == xAndY.x && y == xAndY.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    private static RandomizedSet<XAndY> randomSet = new RandomizedSet<>();

    public GamePanel(int screenSize, int uSize, int DELAY) throws IOException {
        SCREEN_HEIGHT = screenSize;
        SCREEN_WIDTH = screenSize;
        UNIT_SIZE = uSize;
        GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;

        x = new int[GAME_UNITS];
        y = new int[GAME_UNITS];

        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
            for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                randomSet.insert(new XAndY(i * UNIT_SIZE, j * UNIT_SIZE));
            }
        }

        direction = Directions.RIGHT;

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new GameAdapter());

        new Timer(DELAY, this).start();
        startGame();
    }

    public void startGame() {
        bodyParts = 6;
        // движение перед созданием яблока для того, чтобы удалить ненужные координаты
        move();
        createApple();
        running = true;
    }

    public void restartGame() {
        System.out.println("Restarted");
        randomSet = new RandomizedSet<>();
        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
            for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                randomSet.insert(new XAndY(i * UNIT_SIZE, j * UNIT_SIZE));
            }
        }

        direction = Directions.RIGHT;

        bodyParts = 6;
        applesEaten = 0;

        x = new int[GAME_UNITS];
        y = new int[GAME_UNITS];

        running = true;
        move();
        createApple();

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (!running) {
            gameOver(g);
            return;
        }

        for (int i = 1; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }

        g.setColor(Color.RED);
        g.drawImage(appleImage, appleX, appleY, UNIT_SIZE, UNIT_SIZE, this);

        for (int i = 0; i < bodyParts; i++) {
            // т.к картинку головы я не нарисовал, вся змея одинаковая
            g.drawImage(snakeImage, x[i], y[i], UNIT_SIZE, UNIT_SIZE, this);
            if (i == 0) {
                g.setColor(new Color(3, 73, 3));
                g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }

        drawingFinished = true;
    }

    public void createApple() {
        XAndY randCoordinate = randomSet.getRandom();
        if (randCoordinate == null) return;
        appleX = randCoordinate.getX();
        appleY = randCoordinate.getY();
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            if (i == bodyParts) randomSet.insert(new XAndY(x[i], y[i]));

            if (x[i] >= 0) {
                x[i] = x[i - 1] % SCREEN_WIDTH;
            } else {
                x[i] = SCREEN_WIDTH;
            }

            if (y[i] >= 0) {
                y[i] = y[i - 1] % SCREEN_HEIGHT;
            } else {
                y[i] = SCREEN_HEIGHT;
            }

            randomSet.remove(new XAndY(x[i], y[i]));
        }

        switch (direction) {
            case RIGHT -> x[0] = x[0] + UNIT_SIZE;
            case LEFT -> x[0] = x[0] - UNIT_SIZE;
            case UP -> y[0] = y[0] - UNIT_SIZE;
            case DOWN -> y[0] = y[0] + UNIT_SIZE;
            default -> throw new RuntimeException("WTF ARE U?");
        }

        if (x[0] >= 0) {
            x[0] %= SCREEN_WIDTH;
        } else {
            x[0] = SCREEN_WIDTH;
        }

        if (y[0] >= 0) {
            y[0] %= SCREEN_HEIGHT;
        } else {
            y[0] = SCREEN_HEIGHT;
        }

        randomSet.remove(new XAndY(x[0], y[0]));
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            randomSet.remove(new XAndY(x[0], y[0]));
            createApple();

            applesEaten++;
            bodyParts++;
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }

            // Commented collision with borders
            /*
                if (x[0] < 0 || x[0] >= SCREEN_WIDTH) {
                    running = false;
                }

                if (y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
                    running = false;
                }
            */
        }
    }

    public void gameOver(Graphics g) {
        if (applesEaten > bestScore) bestScore = applesEaten;

        g.setColor(Color.RED);
        g.setFont(new Font("Comic Sans MS", Font.ITALIC, 40));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Best score: " + bestScore, (SCREEN_WIDTH - fontMetrics.stringWidth("Best score: " + bestScore)) / 2,
                SCREEN_HEIGHT / 2 - g.getFont().getSize() * 2);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 75));
        fontMetrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - fontMetrics.stringWidth("GAME OVER")) / 2,
                SCREEN_HEIGHT / 2);

        g.setFont(new Font("Comic Sans MS", Font.ITALIC, 40));
        fontMetrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + applesEaten)) / 2,
                SCREEN_HEIGHT / 2 + g.getFont().getSize());
        drawingFinished = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollisions();
            checkApple();
            repaint();
        }
    }

    private class GameAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!drawingFinished) return;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != Directions.RIGHT && running) {
                        direction = Directions.LEFT;
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != Directions.LEFT && running) {
                        direction = Directions.RIGHT;
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != Directions.UP && running) {
                        direction = Directions.DOWN;
                    }
                }
                case KeyEvent.VK_UP -> {
                    if (direction != Directions.DOWN && running) {
                        direction = Directions.UP;
                    }
                }
                default -> restartGame();
            }

            if (running) {
                drawingFinished = false;
            }
        }
    }
}
