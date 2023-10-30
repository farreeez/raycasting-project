import java.util.Timer;
import java.util.TimerTask;

public class Player {
  private double posY = 3, posX = 3; // x and y start position
  private double angle = Math.toRadians(270);
  private int res;
  private double fov = 90;
  private double inc;
  private double[][] viewPlane;

  public Player(int res) {
    int pos = 0;
    this.res = res;
    inc = Math.toRadians(fov / (this.res));
    viewPlane = new double[this.res][3];
    for (int i = (this.res - 1) / 2; i >= 1; i--) {
      viewPlane[pos][0] = angle + inc * i;
      pos++;
    }
    viewPlane[(this.res - 1) / 2][0] = angle;
    for (int i = 0; i < (this.res - 1) / 2; i++) {
      viewPlane[i + 1 + (this.res - 1) / 2][0] = angle - inc * (i + 1);
    }
    changeDirection();
  }


  public double[] getPosition() {
    double[] position = { posY, posX };
    return position;
  }

  public double getAngle() {
    return viewPlane[(res - 1) / 2][0];
  }

  private double[] ddaCasterHelper(int i, double orgPosX, double orgPosY) {
    double[] imageArray = new double[11];
    double hypX = 1000000000;
    double hypY = 1000000000;
    double yStep = 0;
    double xStep = 0;
    double xMain = 0;
    double yMain = 0;
    int roundingValue = 32;
    double posX = orgPosX * roundingValue;
    double posY = orgPosY * roundingValue;
    boolean noBoundary = true;
    int count = 0;
    int xCount = 0;
    int y = 0;
    int x = 0;
    int[][] worldMap = World.getMap();
    // viewPlane[i][1] is dirY
    // viewPlane[i][2] is dirX
    while (noBoundary && count < 2000) {
      // code for the ray to calculate the x boundaries
      boolean allowed = true;
      if (viewPlane[i][1] != 0) {
        if (count == 0) {
          hypX = 0;
        }
        if (hypX <= hypY) {
          // if the position of the player is not on the edges
          if (count == 0) {
            // calculates the length of the step that is needed to reach the closest edge.
            double posYfloor = Math.floor(posY);
            if (viewPlane[i][1] > 0) {
              yStep = -(posY - posYfloor);
            } else {
              yStep = (1 - (posY - posYfloor));
            }
          } else {
            // adds or subtracts one from yStep depending on the direction of
            // viewPlane[i][1] to reach
            // the next edge
            yStep += getSign(viewPlane[i][1]) * -1;
          }
          // calculates the hypotinuse and the x factor that the line moves by.
          hypX = Math.abs(yStep)
              * Math.sqrt(1 + Math.pow(viewPlane[i][2] / viewPlane[i][1], 2))
              / roundingValue;
          allowed = false;
        }
      }

      if (viewPlane[i][2] != 0) {
        if (count == 0) {
          hypY = 0;
        }
        if ((hypY <= hypX && allowed) || xCount == 0) {
          if (xCount == 0) {
            double posXfloor = Math.floor(posX);
            if (viewPlane[i][2] < 0) {
              xStep = -(posX - posXfloor);
            } else {
              xStep = 1 - (posX - posXfloor);
            }
          } else {
            xStep += getSign(viewPlane[i][2]);
          }
          hypY = Math.abs(xStep)
              * Math.sqrt(1 + Math.pow(viewPlane[i][1] / viewPlane[i][2], 2))
              / roundingValue;
          xCount++;
        }
      }

      if (hypX < hypY) {
        yMain = yStep;
        imageArray[3] = 1;
      } else if (hypY < hypX) {
        xMain = xStep;
        imageArray[3] = 0.5;
      }

      y = (int) Math.floor((yMain + posY) / roundingValue);
      x = (int) Math.floor((xMain + posX) / roundingValue);
      if (x >= 0 && y >= 0 && y < worldMap.length && x < worldMap[y].length) {
        if (worldMap[y][x] != 0) {
          noBoundary = false;
        }
      }
      count++;
    }

    imageArray[0] = Math.min(hypX, hypY);
    if (imageArray[3] == 0.5) {
      // y
      double opposite = Math.sin(viewPlane[i][0]) * imageArray[0];
      imageArray[4] = (opposite - orgPosY) - Math.floor(opposite - orgPosY);
    } else {
      // x
      double adjacent = Math.cos(viewPlane[i][0]) * imageArray[0];
      imageArray[4] = (adjacent + orgPosX) - Math.floor(adjacent + orgPosX);
    }

    imageArray[7] = (viewPlane[i][0] * 180) / Math.PI;
    imageArray[8] = orgPosX;
    imageArray[9] = orgPosY;
    imageArray[5] = y;
    imageArray[6] = x;
    imageArray[10] = worldMap[y][x];

    if (x >= 0 && y >= 0 && y < worldMap.length && x < worldMap[y].length) {
      imageArray[1] = worldMap[y][x];
    }
    imageArray[2] = viewPlane[(res - 1) / 2][0] - viewPlane[i][0];
    if (imageArray[2] < 0) {
      imageArray[2] += 2 * Math.PI;
    } else if (imageArray[2] > 2 * Math.PI) {
      imageArray[2] -= 2 * Math.PI;
    }
    return imageArray;
  }

  public double[][] ddaCaster() {
    double[][] imageArray = new double[res][10];
    if (Main.debug) {
      imageArray = new double[res][11];
    }
    for (int i = 0; i < viewPlane.length; i++) {
      imageArray[i] = ddaCasterHelper(i, this.posX, this.posY);
    }
    return imageArray;
  }

  // rotates the angle to the right
  public void rotateRight() {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        if (Main.rotateRight) {
          for (int i = 0; i < viewPlane.length; i++) {
            viewPlane[i][0] -= 0.017;
          }
          changeDirection();
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleAtFixedRate(task, 0, 5);
  }

  // rotates the angle to the left
  public void rotateLeft() {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        if (Main.rotateLeft) {
          for (int i = 0; i < viewPlane.length; i++) {
            viewPlane[i][0] += 0.017;
          }
          changeDirection();
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleAtFixedRate(task, 0, 5);
  }

  public void mouseAim(double movement) {
    for (int i = 0; i < viewPlane.length; i++) {
      viewPlane[i][0] += (Math.PI / 100) * movement * 0.8;
    }
    changeDirection();
  }

  // changes the direction vector according to the angle
  // viewPlane[i][0] is the angle
  // viewPlane[i][1] is the dirY
  // viewPlane[i][2] is the dirX
  private void changeDirection() {
    // changeDirection(Math.PI / 4 + Math.PI);
    for (int i = 0; i < viewPlane.length; i++) {
      viewPlane[i][0] = viewPlane[i][0] - Math.floor(viewPlane[i][0] / (2 * Math.PI)) * (2 * Math.PI);
      if (viewPlane[i][0] > Math.PI || viewPlane[i][0] < 0) {
        viewPlane[i][1] = -1;
      } else {
        viewPlane[i][1] = 1;
      }
      viewPlane[i][2] = viewPlane[i][1] / Math.tan(viewPlane[i][0]);
    }
  }

  private int getSign(double num) {
    if (num < 0) {
      return -1;
    } else {
      return 1;
    }
  }

  public void moveForward() {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        if (Main.forward) {
          double angle = viewPlane[(res - 1) / 2][0];
          move(angle);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleAtFixedRate(task, 0, 5);
  }

  public void moveBack() {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        if (Main.backward) {
          double angle = viewPlane[(res - 1) / 2][0] + Math.PI;
          move(angle);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleAtFixedRate(task, 0, 5);
  }

  public void moveRight() {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        if (Main.right) {
          double angle = viewPlane[(res - 1) / 2][0] - Math.PI / 2;
          move(angle);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleAtFixedRate(task, 0, 5);
  }

  public void moveLeft() {
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        if (Main.left) {
          double angle = viewPlane[(res - 1) / 2][0] + Math.PI / 2;
          move(angle);
        } else {
          timer.cancel();
        }
      }
    };
    timer.scheduleAtFixedRate(task, 0, 5);
  }

  private void move(double angle) {
    int[][] worldMap = World.getMap();
    double speed = 0.02;
    angle = angle - Math.floor(angle / (2 * Math.PI)) * (2 * Math.PI);
    double posX = this.posX;
    double posY = this.posY;
    if (angle >= 0 && angle <= Math.PI / 2) {
      posY -= Math.abs(Math.sin(angle) * speed);
      posX += Math.abs(Math.cos(angle) * speed);
    } else if (angle > Math.PI / 2 && angle <= Math.PI) {
      posY -= Math.abs(Math.sin(Math.PI - angle) * speed);
      posX -= Math.abs(Math.cos(Math.PI - angle) * speed);
    } else if (angle > Math.PI && angle <= (3 * Math.PI) / 2) {
      posY += Math.abs(Math.sin(angle - Math.PI) * speed);
      posX -= Math.abs(Math.cos(angle - Math.PI) * speed);
    } else {
      posY += Math.abs(Math.sin(2 * Math.PI - angle) * speed);
      posX += Math.abs(Math.cos(2 * Math.PI - angle) * speed);
    }

    int y = (int) Math.floor(posY);
    int x = (int) Math.floor(posX);

    int currentX = (int) Math.floor(this.posX);

    if (posY >= 0) {
      if (y < worldMap.length && worldMap[y][currentX] == 0) {
        this.posY = posY;
      }
    }

    if (posX >= 0
        && x < worldMap[(int) Math.floor(this.posY)].length
        && worldMap[(int) Math.floor(this.posY)][x] == 0) {
      this.posX = posX;
    }
  }

}