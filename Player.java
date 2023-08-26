public class Player {
    private int worldMap[][] = {
            { 2, 2, 3, 2, 2 },
            { 3, 0, 2, 0, 3 },
            { 3, 0, 2, 0, 3 },
            { 3, 0, 2, 0, 3 },
            { 3, 0, 2, 0, 3 },
            { 3, 0, 0, 0, 3 },
            { 3, 0, 0, 0, 3 },
            { 1, 1, 1, 1, 1 } };

    private double posY = 5, posX = 2; // x and y start position
    private double dirX = 0, dirY = 1; // initial direction vector
    private double angle = Math.atan(dirY / dirX);
    private double time = 0; // time of current frame
    private double oldTime = 0; // time of previous frame
    private int res = 201;
    private double inc = Math.PI / 402;
    private double[][] viewPlane = new double[res][3];

    // public double[] ddaCaster() {
    // return ddaCaster(dirX, dirY);
    // }

    public Player() {
        int pos = 0;
        for (int i = 100; i >= 1; i--) {
            viewPlane[pos][0] = angle - inc * i;
            pos++;
        }
        viewPlane[101][0] = angle;
        for (int i = 0; i < 101; i++) {
            viewPlane[i + 100][0] = angle + inc * i;
        }
        changeDirection();
    }

    public double[][] ddaCaster() {
        double[][] imageArray = new double[201][2];
        for (int i = 0; i < viewPlane.length; i++) {
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
            while (noBoundary && (Math.min(hypX, hypY) < 100 || count == 0)) {
                // code for the ray to calculate the x boundaries
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
                            // adds or subtracts one from yStep depending on the direction of viewPlane[i][1] to reach
                            // the next edge
                            yStep += getSign(viewPlane[i][1]) * -1;
                        }
                        // calculates the hypotinuse and the x factor that the line moves by.
                        if (viewPlane[i][2] == 0) {
                            xn = 0;
                        } else {
                            xn = yStep / ((viewPlane[i][1] / viewPlane[i][2])) * -1;
                        }
                        hypX = Math.abs(yStep) * Math.sqrt(1 + Math.pow(viewPlane[i][2] / viewPlane[i][1], 2));
                    }
                }

                if (viewPlane[i][2] != 0) {
                    if (count == 0) {
                        hypY = 0;
                    }
                    if (hypY <= hypX) {
                        if (count == 0) {
                            double posXfloor = Math.floor(posX);
                            // xStep = (1 - (posX - postYfloor)) * getSign(viewPlane[i][2]) *
                            // -1;
                            if (viewPlane[i][2] > 0) {
                                xStep = 1 - (posX - posXfloor);
                            } else {
                                xStep = -(posX - posXfloor);
                            }
                        } else {
                            xStep += getSign(viewPlane[i][2]);
                        }
                        if (viewPlane[i][1] == 0) {
                            yn = 0;
                        } else {
                            yn = (xStep * (viewPlane[i][1] / viewPlane[i][2])) * -1;
                        }
                        hypY = Math.abs(xStep) * Math.sqrt(1 + Math.pow(viewPlane[i][1] / viewPlane[i][2], 2));
                    }
                }

                if (hypX < hypY) {
                    yMain = yStep;
                    xMain = xn;
                } else {
                    yMain = yn;
                    xMain = xStep;
                }

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
            // imageArray[i][0] is the length of the hypMain
            // imageArray[i][1] is the integer denoting the colour/texture
            imageArray[i][0] = Math.sqrt(Math.pow(yMain, 2) + Math.pow(xMain, 2));
            imageArray[i][1] = colour;
        }
        return imageArray;
    }

    // rotates the angle to the right
    public void rotateRight() {
        for (int i = 0; i < viewPlane.length; i++) {
            viewPlane[i][0] += 0.3926991;
        }
        changeDirection();
    }

    // rotates the angle to the left
    public void rotateLeft() {
        for (int i = 0; i < viewPlane.length; i++) {
            viewPlane[i][0] -= 0.3926991;
        }
        changeDirection();
    }

    // changes the direction vector according to the angle
    // viewPlane[i][0] is the angle
    // viewPlane[i][1] is the dirY
    // viewPlane[i][2] is the dirX
    private void changeDirection() {
        for (int i = 0; i < viewPlane.length; i++) {
            viewPlane[i][0] = viewPlane[i][0] - Math.floor(viewPlane[i][0] / (2 * Math.PI)) * (2 * Math.PI);
            if (viewPlane[i][0] > Math.PI || viewPlane[i][0] < 0) {
                viewPlane[i][1] = -1;
            } else {
                viewPlane[i][1] = 1;
            }
            viewPlane[i][2] = Math.tan(viewPlane[i][0]) * viewPlane[i][1];
        }
    }

    private int getSign(double num) {
        if (num < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
