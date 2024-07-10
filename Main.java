import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.*;

public class Main extends JPanel implements KeyListener, ActionListener {
  private static int screenWidth = 980;
  private static int screenHeight = 720;
  private int resFactor = 6;
  private int res = Math.round(screenWidth / resFactor);
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
  private double[][] imageArray;
  private double[] wallDist;
  private List<Sprite> sprites;
  private double[][] spriteAngles;
  private Gun gun;

  public Main() {

    player = new Player(res);
    imageArray = player.ddaCaster();
    wallDist = new double[imageArray.length];
    setFocusable(true);
    addKeyListener(this);

    sprites = World.getsprites();
    arrangeSprites(sprites, player.getPosition());
    spriteAngles = new double[sprites.size()][3];
    gun = World.getGun();

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
    this.addMouseListener(
        new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
          }

          @Override
          public void mousePressed(MouseEvent e) {
            int pressCode = e.getButton();
            if (pressCode == MouseEvent.BUTTON1) {
              gun.fire();
              boolean notHit = true;
              boolean spriteInWay = false;
              int ray = 0;
              for (int i = 0; i < spriteAngles.length; i++) {
                if ((res - 1) / 2 >= spriteAngles[i][0] && (res - 1) / 2 <= spriteAngles[i][1]) {
                  spriteInWay = true;
                  ray = i;
                }
              }

              if (spriteInWay
                  && isBehind((res - 1) / 2, spriteAngles[ray][2])
                  && spriteAngles[ray][2] >= 0.5) {
                // code for clicking on player or object
                if (sprites.get(ray) instanceof Ai) {
                  Ai ai = (Ai) sprites.get(ray);
                  ai.shot(gun.getDamage());
                  Sprite sp = (Sprite) ai;
                  sprites.set(ray, sp);
                  notHit = false;
                }
              }

              if (notHit) {
                if (imageArray[(res - 1) / 2][1] == -1) {
                  int[][] worldMap = World.getMap();
                  worldMap[(int) imageArray[(res - 1) / 2][5]][(int) imageArray[(res - 1) / 2][6]] = 0;
                  World.setMap(worldMap);
                }
              }
            }
          }

          @Override
          public void mouseReleased(MouseEvent e) {
          }

          @Override
          public void mouseEntered(MouseEvent e) {
          }

          @Override
          public void mouseExited(MouseEvent e) {
          }
        });
  }

  public static void main(String[] args) {
    frame = new JFrame("Raycaster");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(screenWidth, screenHeight));
    Main main = new Main();
    main.setBackground(Color.black);
    frame.add(main);
    frame.pack();
    frame.setVisible(true);
    main.startGame();
  }

  private void startGame() {
    timer = new Timer(15, this);
    timer.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int screenWidth = Main.screenWidth - 17;
    List<BufferedImage> wallTextures = World.getWallTextures();
    List<BufferedImage> floorCeilingTextures = World.getFloorCeilingTextures();
    double heightOfFloor = (screenHeight - 40) / 2.0;
    BufferedImage image = wallTextures.get(0);
    BufferedImage floor = floorCeilingTextures.get(0);

    int textureHeight = image.getHeight();

    // drawing floor and ceiling
    for (int i = 0; i < Math.ceil(res / 2.0); i++) {
      int floorScreenPosY = (int) Math.floor(heightOfFloor + i * resFactor);
      int ceilingScreenPosY = (int) Math.ceil(heightOfFloor - i * resFactor);

      double steps = (screenWidth) / (double) imageArray.length;

      for (int j = 0; j < imageArray.length; j++) {
        double currentDistance = textureHeight / (Math.cos(imageArray[j][2]) * resFactor * i * 2 * 0.8);

        double posX = imageArray[0][8] + currentDistance * Math.cos(Math.toRadians(imageArray[j][7]));
        double posY = imageArray[0][9] - currentDistance * Math.sin(Math.toRadians(imageArray[j][7]));

        Color floorColor = Color.white;

        if (posY >= 0
            && posY < World.floorTexture.length
            && posX >= 0
            && posX < World.floorTexture[(int) posY].length) {
          double factor = 1 / Math.pow(currentDistance, 0.2);
          if (World.floorTexture[(int) Math.floor(posY)][(int) Math.floor(posX)] == 1) {
            double ty = posY - Math.floor(posY);
            double tx = posX - Math.floor(posX);
            tx *= floor.getWidth();
            ty *= floor.getHeight();
            floorColor = new Color(floor.getRGB((int) Math.floor(tx), (int) Math.floor(ty)));
          }
          g.setColor(adjustColorBrightness(floorColor, factor));
          g.fillRect(
              (int) Math.floor(j * steps), floorScreenPosY, (int) Math.ceil(steps), resFactor);
          g.fillRect(
              (int) Math.floor(j * steps), ceilingScreenPosY, (int) Math.ceil(steps), resFactor);
        }
      }
    }

    // drawing walls
    for (int i = 0; i < imageArray.length; i++) {
      double originalDistance = imageArray[i][0];
      double factor = 0;
      if (imageArray[i][1] != 0) {
        factor = imageArray[i][3] / Math.pow(originalDistance, 0.2);
      }

      factor = Math.min(factor, 1.3);

      double distanceFactor = Math.cos(imageArray[i][2]) * originalDistance;
      wallDist[i] = distanceFactor;

      distanceFactor *= 0.8;

      int height = (int) Math.round(textureHeight / distanceFactor);

      if (imageArray[i][1] == -1) {
        image = wallTextures.get(1);
      } else {
        image = wallTextures.get(0);
      }

      int textureWidth = image.getWidth();
      double width = ((double) screenWidth) / imageArray.length;
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

      double textureFactorFraction = 0;
      if (height != 0) {
        textureFactorFraction = ((double) textureHeight) / height;
      }
      double startingHeight = (screenHeight - 40 - height) / 2.0;
      double start = 0;
      if (startingHeight < 0) {
        start = startingHeight * -1;
      }

      double heightScalingFactor = ((double)textureHeight) / res;
      height = (int) Math.ceil(height / heightScalingFactor);
      start = (int) Math.ceil(start / heightScalingFactor);

      for (int j = (int) start; j < height; j++) {
        try {
          Color color = new Color(
              image.getRGB(
                  (int) Math.floor(xTexture * textureWidth),
                  (int) (j * textureFactorFraction * heightScalingFactor)));
          if (imageArray[i][1] != 0) {
            g.setColor(adjustColorBrightness(color, factor));
          } else {
            g.setColor(color);
          }
          g.fillRect(
              (int) Math.round(width * i),
              (int) Math.ceil(startingHeight + j * heightScalingFactor),
              (int) Math.ceil(width),
              (int) Math.ceil(heightScalingFactor) + 1);
        } catch (Exception e) {
        }
        if ((startingHeight + j * heightScalingFactor) > screenHeight - 40) {
          break;
        }
      }
    }

    int[][] worldMap = World.getMap();
    int width = (int) Math.floor((double) screenWidth / 6);
    int height = (worldMap.length / worldMap[0].length) * width;
    int smallWidth = (int) Math.floor((double) width / worldMap[0].length);
    int smallHeight = (int) Math.floor((double) height / worldMap.length);

    for (int i = 0; i < worldMap.length; i++) {
      for (int j = 0; j < worldMap[i].length; j++) {
        Color color = Color.BLACK;
        if (worldMap[i][j] > 0) {
          color = Color.GRAY;
        } else if (worldMap[i][j] == -1) {
          color = Color.YELLOW;
        }
        g.setColor(color);
        g.fillRect(
            screenWidth - width + 1 + smallWidth * j, smallHeight * i, smallWidth, smallHeight);
      }
    }

    // drawing sprites

    arrangeSprites(sprites, player.getPosition());
    double currentPosX = imageArray[0][8];
    double currentPosY = imageArray[0][9];
    double playerAngle = Math.toRadians(imageArray[(res - 1) / 2][7]);
    for (int i = 0; i < sprites.size(); i++) {
      boolean dead = false;
      Sprite currentSprite = sprites.get(i);
      if (currentSprite instanceof Ai) {
        Ai ai = (Ai) currentSprite;
        if (ai.isDead()) {
          dead = true;
        }
      }
      double[] spritePos = currentSprite.getpos();

      double spriteDirY = -(spritePos[0] - currentPosY);
      double spriteDirX = (spritePos[1] - currentPosX);
      double spriteAngle = findAngle(spriteDirY, spriteDirX, playerAngle);

      double angleDiff = fixAngle(Math.PI / 2 - spriteAngle);
      double orgAngleDiff = angleDiff;

      double orgDistanceFromPlayer = distBetweenPoints(currentPosX, currentPosY, spritePos[1], spritePos[0]);

      spriteAngles[i][2] = orgDistanceFromPlayer;

      double distanceFromPlayer = orgDistanceFromPlayer;

      BufferedImage img = currentSprite.getTexture();
      double currentTextureHeight = textureHeight;
      if (dead) {
        currentTextureHeight /= 4;
      }
      
      if (isBiggerThan(spriteAngle, Math.PI / 2)) {
        angleDiff += Math.PI / 4;
      } else {
        angleDiff = Math.PI / 4 - angleDiff;
      }
      double middlePos = angleDiff * screenWidth / (Math.PI / 2);

      if (middlePos > 0 && middlePos < screenWidth) {
        distanceFromPlayer = orgDistanceFromPlayer
            * Math.cos(imageArray[(int) Math.floor(middlePos / resFactor)][2]);
      }

      double spriteHeight = (currentTextureHeight / (distanceFromPlayer * 0.8));
      double spriteProportion = ((double) img.getWidth()) / img.getHeight();
      double spriteWidth = (spriteProportion * spriteHeight);
      double adjustedSpriteWidth = spriteWidth / resFactor;
      double widthFactor = img.getWidth() / adjustedSpriteWidth;
      double heightFactor = img.getHeight() / spriteHeight;

      double spritePixel = middlePos + 0 * 4 - spriteWidth / 2;
      spritePixel = (int) Math.floor((((double) spritePixel) / screenWidth) * imageArray.length);
      double rightAngle = 0;
      double leftAngle = 0;

      if (spritePixel >= 0 && spritePixel <= screenWidth) {
        leftAngle = (int) spritePixel;
      }
      spritePixel = middlePos + adjustedSpriteWidth * 4 - spriteWidth / 2;
      spritePixel = (int) Math.floor((((double) spritePixel) / screenWidth) * imageArray.length);
      if (spritePixel >= 0 && spritePixel <= imageArray.length) {
        rightAngle = (int) spritePixel;
      }
      double[] arr = { leftAngle, rightAngle, orgDistanceFromPlayer };
      spriteAngles[i] = arr;

      double adjustedSpriteHeight = spriteHeight / resFactor;

      for (int j = 0; j < adjustedSpriteWidth; j++) {
        int screenX = (int) Math.round(middlePos + j * resFactor - spriteWidth / 2);
        int currentRay = (int) Math.floor((((double) screenX) / screenWidth) * imageArray.length);
        if ((orgAngleDiff <= Math.toRadians(45)) && (distanceFromPlayer >= 0.5)) {
          if (isBehind(currentRay, distanceFromPlayer)) {
            for (int k = 0; k < adjustedSpriteHeight; k++) {
              int tx = (int) Math.floor(j * widthFactor);
              int ty = (int) Math.floor(k * heightFactor * resFactor);
              int rgb = img.getRGB(tx, ty);
              int screenY = (int) Math.round(
                  ((screenHeight - spriteHeight - 40) / 2) + k * resFactor);
              if (dead) {
                int num = (int) (spriteHeight - k * resFactor - 1);
                int tempScreenHeight = screenHeight;
                double pos = (tempScreenHeight - (tempScreenHeight / distanceFromPlayer)) / 2 + (tempScreenHeight / distanceFromPlayer);
                screenY = (int) Math.round(pos - num);
              }
              if ((rgb >> 24 & 0xFF) != 0) {
                g.setColor(new Color(rgb));
                g.fillRect(screenX, screenY, resFactor, resFactor);
              }
            }
          }
        }
      }
    }

    double[] position = player.getPosition();
    g.setColor(Color.WHITE);
    int radius = 16;
    int playerPosX = screenWidth - width + 1 + (int) Math.round(smallWidth * position[1]);
    int playerPosY = (int) Math.round(smallHeight * position[0]);
    g.fillOval(playerPosX, playerPosY, radius, radius);
    double angle = player.getAngle() + Math.PI / 2;
    int playerPeekX = playerPosX + (int) (radius / 2 * Math.sin(angle)) + radius / 4;
    int playerPeekY = playerPosY + (int) (radius / 2 * Math.cos(angle)) + radius / 4;
    g.setColor(Color.BLUE);
    g.fillOval(playerPeekX, playerPeekY, radius / 2, radius / 2);
    g.setColor(Color.blue);

    int crosairSize = 16;
    g.fillRect(
        screenWidth / 2 - crosairSize / 2,
        (screenHeight - 40) / 2 - crosairSize / 2,
        crosairSize,
        crosairSize);

    g.drawImage(
        gun.getImg(), (2 * screenWidth) / 3, screenHeight - 40 - gun.getImg().getHeight(), this);
  }

  private boolean isBehind(int index, double distance) {
    if (index < 0 || index >= wallDist.length) {
      return false;
    }

    if (wallDist[index] < distance) {
      return false;
    }

    return true;
  }

  // consider changing to nlogn
  private void arrangeSprites(List<Sprite> sprites, double[] position) {
    for (int i = 0; i < sprites.size(); i++) {
      int current = i;
      while (current > 0
          && sprites.get(current).getDist(position) > sprites.get(current - 1).getDist(position)) {
        Sprite saved = sprites.get(current);
        sprites.set(current, sprites.get(current - 1));
        sprites.set(current - 1, saved);
        current--;
      }
    }
  }

  private double findAngle(double spriteDirY, double spriteDirX, double currentAngle) {
    double fakeSpriteDirX = Math.abs(spriteDirX);
    double fakeSpriteDirY = Math.abs(spriteDirY);
    double angle = Math.atan(fakeSpriteDirY / fakeSpriteDirX);

    if (spriteDirX < 0 && spriteDirY > 0) {
      angle = Math.PI - angle;
    } else if (spriteDirX < 0 && spriteDirY < 0) {
      angle += Math.PI;
    } else if (spriteDirX > 0 && spriteDirY < 0) {
      angle = 2 * Math.PI - angle;
    }
    // angle %= Math.PI*2;
    angle += (Math.PI / 2 - currentAngle);

    if (angle < 0) {
      angle += Math.PI * 2;
    }

    return angle;
  }

  private boolean isBiggerThan(double spriteAngle, double currentAngle) {
    boolean bigger = false;
    spriteAngle = spriteAngle % (Math.PI * 2);
    currentAngle = currentAngle % (Math.PI * 2);
    int multiple90Sprite = (int) (spriteAngle / (Math.PI * 0.5));
    int multiple90Current = (int) (currentAngle / (Math.PI * 0.5));
    if (multiple90Sprite == multiple90Current && spriteAngle < currentAngle) {
      bigger = true;
    } else {
      if (multiple90Current > 0 && multiple90Sprite < multiple90Current) {
        bigger = true;
      } else if (multiple90Sprite == 3) {
        bigger = true;
      }
    }
    return bigger;
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
    } else if (keyCode == KeyEvent.VK_M) {
      if (mouseLock) {
        setCursor(originalCursor);
      } else {
        setCursor(blankCursor);
      }
      mouseLock = !mouseLock;
    }
  }

  private double fixAngle(double angle) {
    if (angle < 0) {
      angle += Math.PI * 2;
    }

    if (angle > Math.PI) {
      angle = Math.PI * 2 - angle;
    }

    return angle;
  }

  public static double distBetweenPoints(double x1, double y1, double x2, double y2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
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
    imageArray = player.ddaCaster();
    repaint();
  }
}