public class Player {
    // private int worldMap[][] = {
    // { 2, 2, 2, 2, 2 },
    // { 3, 0, 0, 0, 3 },
    // { 3, 0, 0, 0, 3 },
    // { 3, 0, 0, 0, 3 },
    // { 3, 0, 0, 0, 3 },
    // { 3, 0, 0, 0, 3 },
    // { 3, 0, 0, 0, 3 },
    // { 1, 1, 1, 1, 1 } };

    private int worldMap[][] = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 4, 0, 0, 0, 0, 5, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 4, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
    };

    private double posY = 3, posX = 2; // x and y start position
    private double angle = Math.PI / 2;
    private double time = 0; // time of current frame
    private double oldTime = 0; // time of previous frame
    private int res = 1501;
    private double inc = Math.PI / (2 * res);
    private double[][] viewPlane = new double[res][3];

    // public double[] ddaCaster() {
    // return ddaCaster(dirX, dirY);
    // }

    public Player() {
        int pos = 0;
        for (int i = (res - 1) / 2; i >= 1; i--) {
            viewPlane[pos][0] = angle + inc * i;
            pos++;
        }
        System.out.println(angle);
        viewPlane[(res - 1) / 2 + 1][0] = angle;
        for (int i = 0; i < (res - 1) / 2 + 1; i++) {
            viewPlane[i + (res - 1) / 2][0] = angle - inc * (i + 1);
        }
        changeDirection();
    }

    public double[][] ddaCaster() {
        double[][] imageArray = new double[res][3];
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
            while (noBoundary && (Math.min(hypX, hypY) < 1000 || count == 0)) {
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
                            // adds or subtracts one from yStep depending on the direction of
                            // viewPlane[i][1] to reach
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
                imageArray[i][2] = (double) worldMap[y][x];
            }
            // imageArray[i][0] is the length of the hypMain
            // imageArray[i][1] is the integer denoting the colour/texture
            imageArray[i][0] = Math.sqrt(Math.pow(yMain, 2) + Math.pow(xMain, 2));
            imageArray[i][1] = colour;
            imageArray[i][2] = viewPlane[(res - 1) / 2][0] - viewPlane[i][0];
            if (imageArray[i][2] < 0) {
                imageArray[i][2] += 2 * Math.PI;
            } else if (imageArray[i][2] > 2 * Math.PI) {
                imageArray[i][2] -= 2 * Math.PI;
            }
        }
        return imageArray;
    }

    // rotates the angle to the right
    public void rotateRight() {
        for (int i = 0; i < viewPlane.length; i++) {
            viewPlane[i][0] -= 0.2;
        }
        changeDirection();
    }

    // rotates the angle to the left
    public void rotateLeft() {
        for (int i = 0; i < viewPlane.length; i++) {
            viewPlane[i][0] += 0.2;
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
                // System.out.println("down: " + (viewPlane[i][0]*180)/Math.PI);
            } else {
                viewPlane[i][1] = 1;
                // System.out.println("up: " + (viewPlane[i][0]*180)/Math.PI);
            }
            viewPlane[i][2] = viewPlane[i][1] / Math.tan(viewPlane[i][0]);
        }
    }
    // public void changeDirection(double angle) {
    // angle = angle - Math.floor(angle / (2 * Math.PI)) * (2 * Math.PI);
    // if (angle > Math.PI || angle < 0) {
    // System.out.println("down");
    // } else {
    // System.out.println("up");
    // }
    // }

    private int getSign(double num) {
        if (num < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public void moveRight() {
        double angle = viewPlane[(res - 1) / 2][0] - Math.PI / 2;
        move(angle);
    }

    public void moveLeft() {
        double angle = viewPlane[(res - 1) / 2][0] + Math.PI / 2;
        move(angle);
    }

    public void moveForward() {
        double angle = viewPlane[(res - 1) / 2][0];
        move(angle);
    }

    public void moveBack() {
        double angle = viewPlane[(res - 1) / 2][0] + Math.PI;
        move(angle);
    }

    private void move(double angle) {
        angle = angle - Math.floor(angle / (2 * Math.PI)) * (2 * Math.PI);
        double posX = this.posX;
        double posY = this.posY;
        if (angle >= 0 && angle <= Math.PI / 2) {
            posY -= Math.abs(Math.sin(angle));
            posX += Math.abs(Math.cos(angle));
        } else if (angle > Math.PI / 2 && angle <= Math.PI) {
            posY -= Math.abs(Math.sin(Math.PI - angle));
            posX -= Math.abs(Math.cos(Math.PI - angle));
        } else if (angle > Math.PI && angle <= (3 * Math.PI) / 2) {
            posY += Math.abs(Math.sin(angle - Math.PI));
            posX -= Math.abs(Math.cos(angle - Math.PI));
        } else {
            posY += Math.abs(Math.sin(2 * Math.PI - angle));
            posX += Math.abs(Math.cos(2 * Math.PI - angle));
        }

        int y = (int) Math.round(posY);
        int x = (int) Math.round(posX);
        if (y >= 0 && y < worldMap.length && worldMap[y][(int) Math.round(this.posX)] == 0) {
            this.posY = posY;
        }

        if (x >= 0 && x < worldMap[y].length && worldMap[(int) Math.round(this.posY)][x] == 0) {
            this.posX = posX;
        }
    }
}
