import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class World {
  private static int worldMap[][] = {
    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 2, 2, -1, 2, 2, 0, 0, 0, 0, 3, 3, -1, 3, 3, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 4, 0, 0, 0, 0, 5, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 4, -1, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 4, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
  };

  public static int floorTexture[][] = new int[25][24];

  private static List<BufferedImage> wallTextures = new ArrayList<>();
  private static List<BufferedImage> floorCeilingTextures = new ArrayList<>();
  private static boolean unloaded = true;
  private static List<Sprite> sprites = new ArrayList<>();
  private static Gun gun;

  private static void loadTextures() {
    if (unloaded) {
      try {
        wallTextures.add(ImageIO.read(new File("./wallTextures/1.png")));
        wallTextures.add(ImageIO.read(new File("./wallTextures/2.jpg")));
        floorCeilingTextures.add(ImageIO.read(new File("./floorAndCeilingTextures/1.jpg")));
        BufferedImage gameMasterImg = ImageIO.read(new File("./sprites/stickman.png"));
        BufferedImage deadGameMasterImg = ImageIO.read(new File("./sprites/deadstickman.png"));
        BufferedImage man = ImageIO.read(new File("./sprites/stickman.png"));
        BufferedImage unFired = ImageIO.read(new File("./fingerGun/noFire.png"));
        BufferedImage fired = ImageIO.read(new File("./fingerGun/fire.png"));
        Sprite gameMaster = new Ai(8.49, 6.5, gameMasterImg, 50, deadGameMasterImg);
        Sprite manman = new Ai(17.5, 6.5, gameMasterImg,50,deadGameMasterImg);
        Sprite pp = new Ai(11.5, 12.5, gameMasterImg,50,deadGameMasterImg);
        gun = new Gun(unFired, 10, fired);
        sprites.add(gameMaster);
        sprites.add(manman);
        sprites.add(pp);
      } catch (Exception e) {
        System.out.println(e);
      }
      unloaded = false;
    }

    for (int i = 0; i < worldMap.length; i++) {
      for (int j = 0; j < worldMap[0].length; j++) {
        floorTexture[i][j] = 1;
      }
    }
  }

  public static Gun getGun(){
    loadTextures();
    return gun;
  }

  public static List<Sprite> getsprites() {
    loadTextures();
    return sprites;
  }

  public static int[][] getMap() {
    return worldMap;
  }

  public static List<BufferedImage> getWallTextures() {
    loadTextures();
    return wallTextures;
  }

  public static List<BufferedImage> getFloorCeilingTextures() {
    loadTextures();
    return floorCeilingTextures;
  }

  public static void setMap(int[][] worldMap2) {
    worldMap = worldMap2;
  }
}
