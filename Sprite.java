import java.awt.image.BufferedImage;

public class Sprite {
    protected double posX;
    protected double posY;
    protected BufferedImage sprite;

    public Sprite(double posX, double posY, BufferedImage sprite){
        this.posX = posX;
        this.posY = posY;
        this.sprite = sprite;
    }

    public double[] getpos() {
        double [] pos = {posY,posX};
        return pos;
    }

    public void setPosX(double x){
        posX = x;
    }

    public void setPosY(double y){
        posY = y;
    }

    public double getDist(double[] playerPosition){
        double pX = playerPosition[1];
        double pY = playerPosition[0];

        return Main.distBetweenPoints(posX, posY, pX, pY);
    }

    public BufferedImage getTexture(){
        return sprite;
    }
}
