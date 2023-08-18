import java.awt.*;
import javax.swing.*;

public class Main extends JPanel {
  static int mapWidth = 5;
  static int mapHeight = 5;
  static int screenWidth = 640;
  static int screenHeight = 480;
  // static int worldMap[][] = {
  // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 4, 0, 0, 0, 0, 5, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 4, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
  // { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
  // };
  static int worldMap[][] = { { 1, 2, 1, 2, 1 },
      { 1, 0, 0, 0, 1 },
      { 2, 0, 0, 0, 2 },
      { 1, 0, 2, 0, 1 },
      { 1, 2, 1, 2, 1 } };

  public static void main(String[] args) {
    double posX = 2, posY = 2; // x and y start position
    double dirX = -1, dirY = 0; // initial direction vector
    double planeX = 0, planeY = 0.66; // the 2d raycaster version of camera plane
    double time = 0; // time of current frame
    double oldTime = 0; // time of previous frame

    JFrame frame = new JFrame("Raycaster");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(screenWidth, screenHeight));
    frame.pack();
    frame.setVisible(true);

    double hypX = 1000000000;
    double hypY = 1000000000;
    double hypMain = -1;
    double xStep = 0;
    double yStep = 0;
    double xn = 0;
    double yn = 0;
    double xMain = 0;
    double yMain = 0;
    boolean noBoundary = true;
    int count = 0;
    while (noBoundary) {
      // code for the ray to calculate the x boundaries
      if (dirY != 0) {
        if (count == 0) {
          double posXfloor = Math.floor(posX);
          // finding xStep
          xStep = (1 - (posX - posXfloor)) * Integer.signum((int) Math.ceil(dirY)) * -1;
        } else {
          xStep++;
        }
        yn = (Math.abs(dirX) / Math.abs(dirY)) * xStep * Integer.signum((int) Math.ceil(dirX)) * -1;
        hypX = xStep * Math.sqrt(1 + Math.pow(dirX / dirY, 2));
      }

      if (dirX != 0) {
        if (count == 0) {
          double postYfloor = Math.floor(posY);
          yStep = (1 - (posY - postYfloor)) * Integer.signum((int) Math.ceil(dirX)) * -1;
        } else {
          yStep++;
        }
        xn = (Math.abs(dirY) / Math.abs(dirX)) * yStep * Integer.signum((int) Math.ceil(dirY)) * -1;
        hypY = yStep * Math.sqrt(1 + Math.pow(dirY / dirX, 2));
      }

      if(hypX < hypY){
        xMain = xStep;
        yMain = yn;
      } else {
        xMain = xn;
        yMain = yStep;
      }

      System.out.println(xMain + posX);
      System.out.println(yMain + posY);
      System.out.println(worldMap[(int) (xMain + posX)][(int) (yMain + posY)]);
      if(worldMap[(int) (xMain + posX)][(int) (yMain + posY)] != 0){
        noBoundary = false;
      }
      count++;
    }
  }
}