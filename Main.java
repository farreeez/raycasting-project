import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Main extends JPanel implements KeyListener, ActionListener {
  private static int screenWidth = 1920;
  private static int screenHeight = 1080;
  private Player player = new Player(screenWidth - 17);
  private Timer timer;
  public static boolean forward = false;
  public static boolean backward = false;
  public static boolean right = false;
  public static boolean left = false;
  private boolean mvfor = true;
  private boolean mvback = true;
  private boolean rtRight = true;
  private boolean rtLeft = true;

  public Main() {
    setFocusable(true);
    addKeyListener(this);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Raycaster");
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

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int screenWidth = Main.screenWidth - 17;
    double[][] imageArray = player.ddaCaster();
    // System.out.println("y: "+imageArray[100][2]);
    // System.out.println("x: "+imageArray[100][3]);
    // System.out.println("angle: "+imageArray[100][1]);
    for (int i = 0; i < imageArray.length; i++) {
      double factor = imageArray[i][3] / Math.pow(imageArray[i][0], 0.2);
      // if(!(imageArray[i][0] > 0 && imageArray[i][0] < 7)){
      // factor = 0;
      // }
      Color color = Color.GRAY;
      if (imageArray[i][1] == 2) {
        color = Color.GREEN;
        // System.out.println("green: x: " + imageArray[i][2] + " y: " +
        // imageArray[i][3]);
      } else if (imageArray[i][1] == 3) {
        color = Color.RED;
        // System.out.println("red: x: " + imageArray[i][2] + " y: " +
        // imageArray[i][3]);
      } else if (imageArray[i][1] == 1) {
        color = Color.BLUE;
        // System.out.println("blue: x: " + imageArray[i][2] + " y: " +
        // imageArray[i][3]);
      }
      g.setColor(adjustColorBrightness(color, factor));
      double distance = Math.cos(imageArray[i][2]) * imageArray[i][0];
      int width = (int) Math.round((double) screenWidth / (imageArray.length));
      int height = (int) Math.round(((double) screenHeight - 40) / Math.pow(distance, 0.8));
      g.fillRect(width * (i), (screenHeight - 40 - height) / 2, width, height);
    }

    int[][] worldMap = player.getMap();
    int width = (int) Math.floor((double) screenWidth / 6);
    int height = (worldMap.length / worldMap[0].length) * width;

    g.setColor(Color.BLACK);
    g.fillRect(screenWidth - width + 1, 0, width, height);
    for (int i = 0; i < worldMap.length; i++) {
      for (int j = 0; j < worldMap[i].length; j++) {
        Color color = Color.BLACK;
        if (worldMap[i][j] == 2) {
          color = Color.GREEN;
        } else if (worldMap[i][j] == 3) {
          color = Color.RED;
        } else if (worldMap[i][j] == 1) {
          color = Color.BLUE;
        }
        int smallWidth = (int) Math.round((double) width / worldMap[i].length);
        int smallHeight = (int) Math.round((double) height / worldMap.length);
        g.setColor(color);
        g.fillRect(screenWidth - width + 1 + smallWidth * j, smallHeight * i, smallWidth, smallHeight);
      }
    }
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
    if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
      right = true;
      if (rtRight) {
        player.rotateRight();
        rtRight = false;
      }
    } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
      left = true;
      if (rtLeft) {
        player.rotateLeft();
        rtLeft = false;
      }
    } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
      forward = true;
      if (mvfor) {
        player.moveForward();
        mvfor = false;
      }
    } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
      backward = true;
      if (mvback) {
        player.moveBack();
        mvback = false;
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stu
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
      right = false;
      rtRight = true;
    } else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
      left = false;
      rtLeft = true;
    } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
      forward = false;
      mvfor = true;
    } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
      backward = false;
      mvback = true;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }
}