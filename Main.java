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
  static int worldMap[][] = {
      { 1, 2, 4, 2, 7 },
      { 9, 0, 0, 0, 18 },
      { 3, 0, 0, 0, 5 },
      { 1, 0, 6, 0, 2 },
      { 8, 2, 1, 2, 12 } };

  public static void main(String[] args) {
    double posY = 2, posX = 2; // x and y start position
    double dirX = -0.74, dirY = 1; // initial direction vector
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
    double yStep = 0;
    double xStep = 0;
    double yn = 0;
    double xn = 0;
    double xMain = 0;
    double yMain = 0;
    boolean noBoundary = true;
    int count = 0;
    while (noBoundary) {
      // code for the ray to calculate the x boundaries
      if (dirY != 0) {
        if (count == 0) {
          hypX = 0;
        }
        if (hypX <= hypY) {
          // if the position of the player is not on the edges
          if (count == 0) {
            // calculates the length of the step that is needed to reach the closest edge.
            double posYfloor = Math.floor(posY);
            if (dirY > 0) {
              yStep = -(posY - posYfloor);
            } else {
              yStep = (1 - (posY - posYfloor));
            }
          } else {
            // adds or subtracts one from yStep depending on the direction of dirY to reach
            // the next edge
            yStep += getSign(dirY) * -1;
          }
          System.out.println("yStep: "+yStep);
          // calculates the hypotinuse and the x factor that the line moves by.
          if (dirX == 0) {
            xn = 0;
          } else {
            xn = yStep / ((dirY / dirX)) * -1;
            System.out.println("xn: "+xn);
          }
          hypX = Math.abs(yStep) * Math.sqrt(1 + Math.pow(dirX / dirY, 2));
          System.out.println("hypx: " +hypX);
        }
      }

      if (dirX != 0) {
        if (count == 0) {
          hypY = 0;
        }
        if (hypY <= hypX) {
          if (count == 0) {
            double posXfloor = Math.floor(posX);
            // xStep = (1 - (posX - postYfloor)) * getSign(dirX) *
            // -1;
            if (dirX > 0) {
              xStep = 1 - (posX - posXfloor);
            } else {
              xStep = -(posX - posXfloor);
            }
          } else {
            xStep += getSign(dirX);
            System.out.println("pp: "+getSign(dirX));
          }
          System.out.println("xStep: "+xStep);
          if (dirY == 0) {
            yn = 0;
          } else {
            yn = (xStep * (dirY / dirX)) * -1;
          }
          System.out.println("yn: "+yn);
          hypY = Math.abs(xStep) * Math.sqrt(1 + Math.pow(dirY / dirX, 2));
          System.out.println("hypY: "+hypY);
        }
      }

      if (hypX < hypY) {
        yMain = yStep;
        xMain = xn;
        // System.out.println(xn);
      } else {
        yMain = yn;
        xMain = xStep;
        // System.out.println(xStep);
      }

      System.out.println(xMain + posX + " top");
      System.out.println(yMain + posY + " bottom");
      System.out.println(worldMap[(int) Math.round(yMain + posY)][(int) Math.round(xMain + posX)]);

      if (worldMap[(int) Math.round(yMain + posY)][(int) Math.round(xMain + posX)] != 0) {
        noBoundary = false;
      }
      count++;
    }
  }

  private static int getSign(double num){
    if(num < 0){
      return -1;
    } else {
      return 1;
    }
  }
}