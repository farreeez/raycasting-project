public class Player {
    private int worldMap[][] = {
            { 1, 2, 3, 1, 2 },
            { 2, 0, 0, 0, 3 },
            { 3, 0, 0, 0, 1 },
            { 2, 0, 0, 0, 2 },
            { 1, 3, 2, 1, 3 } };

    private double posY = 2, posX = 2; // x and y start position
    private double dirX = -0.26, dirY = 1; // initial direction vector
    private double angle = Math.atan(dirX / dirY);
    private double planeX = 0, planeY = 0.66; // the 2d raycaster version of camera plane
    private double time = 0; // time of current frame
    private double oldTime = 0; // time of previous frame

    // public double[] ddaCaster() {
    // return ddaCaster(dirX, dirY);
    // }

    public double[] ddaCaster() {
        double hypX = 1000000000;
        double hypY = 1000000000;
        double yStep = 0;
        double xStep = 0;
        double yn = 0;
        double xn = 0;
        double xMain = 0;
        double yMain = 0;
        boolean noBoundary = true;
        int count = 0;
        while (noBoundary && (Math.min(hypX, hypY) < 500 || count == 0)) {
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
                    // calculates the hypotinuse and the x factor that the line moves by.
                    if (dirX == 0) {
                        xn = 0;
                    } else {
                        xn = yStep / ((dirY / dirX)) * -1;
                    }
                    hypX = Math.abs(yStep) * Math.sqrt(1 + Math.pow(dirX / dirY, 2));
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
                    }
                    if (dirY == 0) {
                        yn = 0;
                    } else {
                        yn = (xStep * (dirY / dirX)) * -1;
                    }
                    hypY = Math.abs(xStep) * Math.sqrt(1 + Math.pow(dirY / dirX, 2));
                }
            }

            if (hypX < hypY) {
                yMain = yStep;
                xMain = xn;
            } else {
                yMain = yn;
                xMain = xStep;
            }

            System.out.println("t: " + dirX);
            System.out.println(dirY);
            int y = (int) (Math.floor(yMain) + posY);
            int x = (int) (Math.floor(xMain) + posX);
            if (y >= 0 && x >= 0 && y < worldMap.length && x < worldMap[y].length) {
                if (worldMap[y][x] != 0) {
                    noBoundary = false;
                }
            }
            count++;
        }
        int y = (int) (Math.floor(yMain) + posY);
        int x = (int) (Math.floor(xMain) + posX);
        int colour = 0;
        if (y >= 0 && x >= 0 && y < worldMap.length && x < worldMap[y].length) {
            colour = worldMap[y][x];
        }
        double[] a = { Math.sqrt(Math.pow(yMain, 2) + Math.pow(xMain, 2)), colour };
        return a;
    }

    // rotates the angle to the right
    public void rotateRight() {
        angle -= 0.3926991;
        changeDirection();
    }

    // rotates the angle to the left
    public void rotateLeft() {
        angle += 0.3926991;
        changeDirection();
    }

    // changes the direction vector according to the angle
    private void changeDirection() {
        angle = angle - Math.floor(angle / (2 * Math.PI)) * (2 * Math.PI);
        if (angle > 180 || angle < 0) {
            dirY = -1;
        } else {
            dirY = 1;
        }
        dirX = Math.tan(angle) * dirY;
    }

    private int getSign(double num) {
        if (num < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
