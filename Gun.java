import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Gun {
  private int damage;
  private BufferedImage fired;
  private BufferedImage original;
  private boolean go = true;
  private File soundFile;

  public Gun(BufferedImage original, int damage, BufferedImage fired) {
    this.original = original;
    this.damage = damage;
    this.fired = fired;
    soundFile = new File("./fingerGun/bsho.wav");
  }

  public int getDamage() {
    return damage;
  }

  public BufferedImage getImg() {
    return original;
  }

  public void fire() {
    if (go) {
      gunSound();
      go = false;
      BufferedImage saved = original;
      original = fired;
      Timer timer = new Timer();
      timer.schedule(
          new TimerTask() {
            @Override
            public void run() {
              original = saved;
              go = true;
            }
          },
          150);
    }
  }

  private void gunSound(){
    try {
      AudioInputStream input = AudioSystem.getAudioInputStream(soundFile);
      Clip clip = AudioSystem.getClip();
      clip.open(input);
      clip.start();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  public boolean getGo() {
    return go;
  }
}
