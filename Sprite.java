import java.awt.image.BufferedImage;

public class Sprite {
    private double posX;
    private double posY;
    private BufferedImage sprite;

    public Sprite(double posX, double posY, BufferedImage sprite){
        this.posX = posX;
        this.posY = posY;
        this.sprite = sprite;
    }

    public double[] getpos() {
        double [] pos = {posX,posY};
        return pos;
    }

    public void setPosX(double x){
        posX = x;
    }

    public void setPosY(double y){
        posY = y;
    }

    public BufferedImage getTexture(){
        return sprite;
    }
}
