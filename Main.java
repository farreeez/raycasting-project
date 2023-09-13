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
  private static int screenWidth = 960;
  private static int screenHeight = 720;
  private int res = Math.round(screenWidth / 3);
  public static boolean debug = false;
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
  private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
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
    blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

    originalCursor = getCursor();

    centreX = screenWidth / 2;
    centreY = screenHeight / 2;
    this.addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseMoved(MouseEvent e) {
            if (mouseLock) {
              double factor = 50 / (double) centreX;
              Point location = frame.getLocation();
              player.mouseAim(factor * (location.x + centreX - e.getXOnScreen()));
              robot.mouseMove(location.x + centreX, location.y + centreY);
            }
          }
        });
    System.out.println(Math.pow(((double) 720) / (435), 1 / 0.8));
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
    List<BufferedImage> textures = World.getTextures();
    BufferedImage floor = textures.get(1);
    int heightOfFloor = (screenHeight - 40) / 2;
    BufferedImage image = textures.get(0);

    int textureHeight = image.getHeight();
    int maxDistance = (int) Math.round(Math.pow(textureHeight, 1 / 0.8));
    double floorFactor = maxDistance / (double) heightOfFloor;
    double xStep = (double) (screenWidth) / imageArray.length;

    // System.out.println(perpAngle);
    for (int i = 1; i < heightOfFloor; i++) {
      int screenPosY = heightOfFloor + i;

      double steps = (screenWidth) / (double) imageArray.length;

      for (int j = 0; j < imageArray.length; j++) {

        double currentDistance = textureHeight / (Math.cos(imageArray[j][2]) * i * 2);

        if (j == (screenWidth - 1) / 2) {
          System.out.println(currentDistance);
        }

        double posX = imageArray[0][8] + currentDistance * Math.cos(Math.toRadians(imageArray[j][7]));
        double posY = imageArray[0][9] - currentDistance * Math.sin(Math.toRadians(imageArray[j][7]));

        Color floorColor;
        floorColor = Color.YELLOW;
        try {
          int color = 10 * World.floorTexture[(int) Math.round(posY)][(int) Math.round(posX)];
          // int color = 2;
          floorColor = new Color(color, color, color);
          g.setColor(floorColor);
          g.drawLine((int) Math.ceil(j * steps), screenPosY,(int) Math.ceil((j + 1)*steps), screenPosY);
        } catch (Exception e) {
        }
      }
    }

    for (int i = 0; i < imageArray.length; i++) {
      double originalDistance = imageArray[i][0];
      double factor = 0;
      if (imageArray[i][1] != 0) {
        factor = imageArray[i][3] / Math.pow(originalDistance, 0.2);
      }

      factor = Math.min(factor, 1.3);

      // if (imageArray[i][1] == 2) {
      // color = Color.GREEN;
      // } else if (imageArray[i][1] == 3) {
      // color = Color.RED;
      // } else if (imageArray[i][1] == 1) {
      // color = Color.BLUE;
      // }

      double distanceFactor = Math.cos(imageArray[i][2]) * originalDistance;

      // Color color = Color.black;

      int height = (int) Math.round(textureHeight / distanceFactor);
      // if(i == (res-1)/2)System.out.println(height);

      int textureWidth = image.getWidth();
      int width = (int) Math.round((double) screenWidth / (imageArray.length));
      double xTexture = imageArray[i][4];
      if (imageArray[i][3] == 1) {
        if (imageArray[i][7] > 180) {
          xTexture = 1 - xTexture;
        }
      } else {
        if (imageArray[i][7] < 90 || imageArray[i][7] > 270) {
          xTexture = 1 - xTexture;
        }
      }

      // if(xTexture == 1){
      // xTexture = 0.99;
      // }

      double textureFactorFraction = 0;
      if (height != 0) {
        textureFactorFraction = ((double) textureHeight) / height;
      }
      int startingHeight = (screenHeight - 40 - height) / 2;
      int start = 0;
      if (startingHeight < 0) {
        start = startingHeight * -1;
      }
      for (int j = start; j < height; j++) {
        Color color = new Color(
            image.getRGB(
                (int) Math.floor(xTexture * textureWidth), (int) (j * textureFactorFraction)));
        if (imageArray[i][1] != 0) {
          g.setColor(adjustColorBrightness(color, factor));
        } else {
          g.setColor(color);
        }
        g.drawLine(width * i, startingHeight + j, width * (i + 1), startingHeight + j);
        if ((startingHeight + j) > screenHeight - 40) {
          if (i == (res - 1) / 2) {
            // System.out.println(j);
            // System.out.println(Math.cos(rounder(imageArray[i][2])) * originalDistance);
          }
          break;
        }

        if (j == height - 1 && i == (res - 1) / 2) {
          // System.out.println(j);
          // System.out.println(Math.cos(rounder(imageArray[i][2])) * originalDistance);
        }
      }
      if (debug) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(24, 17.0f));
        g.drawString("y: " + rounder(imageArray[i][5]), width * (i), 100);
        g.drawString("x: " + rounder(imageArray[i][6]), width * (i), 200);
        // g.drawString("ang: " + rounder(imageArray[i][7]), width * (i), 300);
        g.drawString("x: " + rounder(imageArray[i][8]), width * (i), 400);
        g.drawString("y: " + rounder(imageArray[i][9]), width * (i), 500);
        // g.drawString("colour: " + rounder(imageArray[i][10]), width * (i), 600);
        g.drawString("d: " + rounder(imageArray[i][0]), width * i, 600);
      }
    }

    int[][] worldMap = player.getMap();
    int width = (int) Math.floor((double) screenWidth / 6);
    int height = (worldMap.length / worldMap[0].length) * width;
    int smallWidth = (int) Math.floor((double) width / worldMap[0].length);
    int smallHeight = (int) Math.floor((double) height / worldMap.length);

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
        g.fillRect(
            screenWidth - width + 1 + smallWidth * j, smallHeight * i, smallWidth, smallHeight);
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

    g.setColor(Color.blue);

    int crosairSize = 24;
    g.fillRect(
        screenWidth / 2 - crosairSize / 2,
        screenHeight / 2 - crosairSize,
        crosairSize,
        crosairSize);
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
