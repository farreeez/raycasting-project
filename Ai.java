import java.awt.image.BufferedImage;

public class Ai extends Sprite {
  int health;
  boolean dead;
  BufferedImage deadSprite;

  public Ai(double posX, double posY, BufferedImage sprite, int health, BufferedImage deadSprite) {
    super(posX, posY, sprite);
    this.health = health;
    this.deadSprite = deadSprite;
    if (health <= 0) {
      dead = true;
      this.sprite = deadSprite;
    }
  }

  public void shot(int damage) {
    health -= damage;
    if (health <= 0) {
      dead = true;
      this.sprite = deadSprite;
    }
  }

  public boolean isDead() {
    return dead;
  }
}
