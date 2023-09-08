import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;

public class Main extends JPanel implements KeyListener, ActionListener {
  private static int screenWidth = 1280;
  private static int screenHeight = 720;
  private int res = screenWidth - 17;
  public static boolean debug = false;
  // private int res = screenWidth - 17;
  private Player player;
  private Timer timer;
  public static boolean forward = false;
  public static boolean backward = false;
  public static boolean left = false;
  public static boolean right = false;
  public static boolean rotateRight = false;
  public static boolean rotateLeft = false;
  private boolean mvFor = true;
  private boolean mvBack = true;
  private boolean mvRight = true;
  private boolean mvLeft = true;
  private boolean rtRight = true;
  private boolean rtLeft = true;
  private static GraphicsDevice device = GraphicsEnvironment
      .getLocalGraphicsEnvironment().getScreenDevices()[1];
  private boolean fullscreen = false;
  private static JFrame frame;
  private int centreX;
  private int centreY;
  private Robot robot;
  private static boolean mouseLock = false;
  private static Cursor originalCursor;
  private static Cursor blankCursor;

  public Main() {
    if (debug) {
      res = 21;
    } else if (res % 2 == 0) {
      res += 1;
    }

    player = new Player(res);
    setFocusable(true);
    addKeyListener(this);

    try {
      this.robot = new Robot();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Transparent 16 x 16 pixel cursor image.
    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    // Create a new blank cursor.
    blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        cursorImg, new Point(0, 0), "blank cursor");

    originalCursor = getCursor();

    centreX = screenWidth / 2;
    centreY = screenHeight / 2;
    this.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        if (mouseLock) {
          double factor = 50 / (double) centreX;
          player.mouseAim(factor * (centreX - e.getXOnScreen()));
          robot.mouseMove(centreX, centreY);
        }
      }
    });
  }

  public static void main(String[] args) {
    frame = new JFrame("Raycaster");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(screenWidth, screenHeight));
    Main main = new Main();
    main.setBackground(Color.gray);
    frame.add(main);
    frame.pack();
    frame.setVisible(true);
    main.startGame();
  }

  private void startGame() {
    timer = new Timer(15, this);
    timer.start();
  }

  private double rounder(double num) {
    return Math.round(num * 100.0) / 100.0;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int screenWidth = Main.screenWidth - 17;
    double[][] imageArray = player.ddaCaster();
    for (int i = 0; i < imageArray.length; i++) {
      double originalDistance = imageArray[i][0];
      double factor = 0;
      if (imageArray[i][1] != 0) {
        factor = imageArray[i][3] / Math.pow(originalDistance, 0.2);
      }

      // if (imageArray[i][1] == 2) {
      // color = Color.GREEN;
      // } else if (imageArray[i][1] == 3) {
      // color = Color.RED;
      // } else if (imageArray[i][1] == 1) {
      // color = Color.BLUE;
      // }

      List<BufferedImage> textures = World.getTextures();
      BufferedImage image = textures.get(0);

      double distance = Math.cos(imageArray[i][2]) * originalDistance;
      double distanceFactor = Math.pow(distance, 0.8);
      // Color color = Color.black;
      if (distanceFactor < 1) {
        distanceFactor = 1;
      }
      int height = (int) Math.round(((double) screenHeight - 40) / distanceFactor);
      int startingHeight = (screenHeight - 40 - height) / 2;
      int width = (int) Math.round((double) screenWidth / (imageArray.length));
      int textureFactor = screenHeight/height;
      for (int j = 0; j < height; j++) {
        Color color = new Color(image.getRGB((int) Math.floor(imageArray[i][4] * screenWidth), j * textureFactor));
        if (imageArray[i][1] != 0) {
          g.setColor(adjustColorBrightness(color, factor));
        } else {
          g.setColor(color);
        }
        g.drawLine(width * i, startingHeight + j, width * (i + 1), startingHeight + j);
      }
      if (debug) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(24, 17.0f));
        g.drawString("y: " + rounder(imageArray[i][4]), width * (i), 100);
        g.drawString("x: " + rounder(imageArray[i][5]), width * (i), 200);
        g.drawString("ang: " + rounder(imageArray[i][6]), width * (i), 300);
        g.drawString("x: " + rounder(imageArray[i][7]), width * (i), 400);
        g.drawString("y: " + rounder(imageArray[i][8]), width * (i), 500);
        g.drawString("colour: " + rounder(imageArray[i][9]), width * (i), 600);
        g.drawString("dist: " + rounder(imageArray[i][0]), width * i, 700);
      }
    }

    int[][] worldMap = player.getMap();
    int width = (int) Math.round((double) screenWidth / 6);
    int height = (worldMap.length / worldMap[0].length) * width;
    int smallWidth = (int) Math.round((double) width / worldMap[0].length);
    int smallHeight = (int) Math.round((double) height / worldMap.length);

    for (int i = 0; i < worldMap.length; i++) {
      for (int j = 0; j < worldMap[i].length; j++) {
        Color color = Color.BLACK;
        if (worldMap[i][j] == 2) {
          color = Color.GREEN;
        } else if (worldMap[i][j] == 3) {
          color = Color.RED;
        } else if (worldMap[i][j] == 1) {
          color = Color.BLUE;
        } else if (worldMap[i][j] != 0) {
          color = Color.GRAY;
        }
        g.setColor(color);
        g.fillRect(screenWidth - width + 1 + smallWidth * j, smallHeight * i, smallWidth, smallHeight);
      }
    }

    double[] position = player.getPosition();
    g.setColor(Color.WHITE);
    int radius = 16;
    int playerPosX = screenWidth - width + 1 + (int) Math.round(smallWidth * position[1]);
    int playerPosY = (int) Math.round(smallHeight * position[0]);
    g.fillOval(playerPosX, playerPosY, radius, radius);
    double angle = player.getAngle() + Math.PI / 2;
    // angle = angle - Math.floor(angle / (2 * Math.PI)) * (2 * Math.PI);
    int playerPeekX = playerPosX + (int) (radius / 2 * Math.sin(angle)) + radius / 4;
    int playerPeekY = playerPosY + (int) (radius / 2 * Math.cos(angle)) + radius / 4;
    g.setColor(Color.BLUE);
    g.fillOval(playerPeekX, playerPeekY, radius / 2, radius / 2);
  }

  private static Color adjustColorBrightness(Color color, double factor) {
    int red = (int) (color.getRed() * factor);
    int green = (int) (color.getGreen() * factor);
    int blue = (int) (color.getBlue() * factor);

    // Ensure that the values are within the valid range (0-255)
    red = Math.min(255, Math.max(0, red));
    green = Math.min(255, Math.max(0, green));
    blue = Math.min(255, Math.max(0, blue));

    return new Color(red, green, blue);
  }

  @Override
  public void keyPressed(KeyEvent move) {
    int keyCode = move.getKeyCode();
    if (keyCode == KeyEvent.VK_RIGHT) {
      rotateRight = true;
      if (rtRight) {
        player.rotateRight();
        rtRight = false;
      }
    } else if (keyCode == KeyEvent.VK_LEFT) {
      rotateLeft = true;
      if (rtLeft) {
        player.rotateLeft();
        rtLeft = false;
      }
    } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
      forward = true;
      if (mvFor) {
        player.moveForward();
        mvFor = false;
      }
    } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
      backward = true;
      if (mvBack) {
        player.moveBack();
        mvBack = false;
      }
    } else if (keyCode == KeyEvent.VK_A) {
      left = true;
      if (mvLeft) {
        player.moveLeft();
        mvLeft = false;
      }
    } else if (keyCode == KeyEvent.VK_D) {
      right = true;
      if (mvRight) {
        player.moveRight();
        mvRight = false;
      }
      // currently not working correctly
    } else if (keyCode == KeyEvent.VK_F1) {
      if (fullscreen) {
        device.setFullScreenWindow(null);
      } else {
        device.setFullScreenWindow(frame);
      }

      fullscreen = !fullscreen;
    } else if (keyCode == KeyEvent.VK_F2) {
      if (mouseLock) {
        setCursor(originalCursor);
      } else {
        setCursor(blankCursor);
      }
      mouseLock = !mouseLock;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_RIGHT) {
      rotateRight = false;
      rtRight = true;
    } else if (keyCode == KeyEvent.VK_LEFT) {
      rotateLeft = false;
      rtLeft = true;
    } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
      forward = false;
      mvFor = true;
    } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
      backward = false;
      mvBack = true;
    } else if (keyCode == KeyEvent.VK_A) {
      left = false;
      mvLeft = true;
    } else if (keyCode == KeyEvent.VK_D) {
      right = false;
      mvRight = true;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }
}