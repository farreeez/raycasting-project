public class Player {
    // private int worldMap[][] = {
    // { 2, 2, 2, 2, 2 },
    // { 3, 0, 2, 0, 3 },
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

    private double posY = 2.4, posX = 3.2; // x and y start position
    private double angle = 2.49;
    private int res;
    private double fov = 90;
    private double inc;
    private double[][] viewPlane;
    private double hypX = 0;
    private double hypY = 0;

    public Player(int res) {
        int pos = 0;
        this.res = res;
        inc = Math.toRadians(fov / (this.res));
        viewPlane = new double[this.res][3];
        for (int i = (this.res - 1) / 2; i >= 1; i--) {
            viewPlane[pos][0] = angle + inc * i;
            pos++;
        }
        viewPlane[(this.res - 1) / 2 + 1][0] = angle;
        for (int i = 0; i < (this.res - 1) / 2 + 1; i++) {
            viewPlane[i + (this.res - 1) / 2][0] = angle - inc * (i + 1);
        }
        changeDirection();
    }

    public double[][] ddaCaster() {
        double[][] imageArray = new double[res][4];
        for (int i = 0; i < viewPlane.length; i++) {
            hypX = 1000000000;
            hypY = 1000000000;
            double yStep = 0;
            double xStep = 0;
            double xMain = 0;
            double yMain = 0;
            double firstY = 0;
            double firstX = 0;
            boolean noBoundary = true;
            int count = 0;
            int xCount = 0;
            int y = 0;
            int x = 0;
            // viewPlane[i][1] is dirY
            // viewPlane[i][2] is dirX
            while (noBoundary && count < 100) {
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
                        hypX = Math.abs(yStep) * Math.sqrt(1 + Math.pow(viewPlane[i][2] / viewPlane[i][1], 2));
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
                        hypY = Math.abs(xStep) * Math.sqrt(1 + Math.pow(viewPlane[i][1] / viewPlane[i][2], 2));
                        xCount++;
                    }
                }

                if (hypX < hypY) {
                    yMain = yStep;
                    imageArray[i][3] = 1;
                } else if (hypY < hypX) {
                    xMain = xStep;
                    imageArray[i][3] = 0.5;
                }

                y = (int) Math.floor(yMain + (posY));
                x = (int) Math.floor(xMain + (posX));
                if (x >= 0 && y >= 0 && y < worldMap.length && x < worldMap[y].length) {
                    if (worldMap[y][x] != 0) {
                        noBoundary = false;
                    }
                }
                count++;
            }

            // if (i == 10) {
            // System.out.println("y: " + (yMain + posY));
            // System.out.println("x: " + (xMain + posX));
            // System.out.println("diry: " + viewPlane[i][1]);
            // System.out.println("dirx: " + viewPlane[i][2]);
            // System.out.println("posx: " + posX);
            // System.out.println("posy: " + posY);
            // System.out.println("colour: " + worldMap[y][x]);
            // System.out.println("xMain: " + xMain);
            // System.out.println("yMain: " + yMain);
            // System.out.println("-------------------------------------------------------------------");
            // }

            // imageArray[i][1] is for colour.
            // imageArray[i][2] is for the angle between the ray and the player direction
            // this is used for fisheye correction.
            imageArray[i][0] = Math.min(hypX, hypY);
            if (x >= 0 && y >= 0 && y < worldMap.length && x < worldMap[y].length) {
                imageArray[i][1] = worldMap[y][x];
            }
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
        double speed = 0.25;
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
        if (posY >= 0 && y < worldMap.length && worldMap[y][(int) Math.floor(this.posX)] == 0) {
            this.posY = posY;
        }

        if (posX >= 0 && x < worldMap[(int) Math.floor(this.posY)].length
                && worldMap[(int) Math.floor(this.posY)][x] == 0) {
            this.posX = posX;
        }

    }
}
