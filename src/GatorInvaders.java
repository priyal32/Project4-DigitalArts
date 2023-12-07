import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


public class GatorInvaders {

    static int level = 1;
    static GameObject shooter;
    static ArrayList<GameObject> Invaders = new ArrayList<>();

    static ArrayList<GameObject> bullets = new ArrayList<>();

    static ArrayList<GameObject> enemyBullets = new ArrayList<>();

    static ArrayList<GameObject> Lives = new ArrayList<>();

    static void Start(){
        Lives.clear();
        Invaders.clear();
        bullets.clear();
        enemyBullets.clear();
        shooter = new GameObject(150,390);
        shooter.shape = new Rectangle2D.Float(0, 0, 50, 50);
        shooter.material = new Material("resources/Ship.png");
        shooter.scripts.add(new ShipMovement(shooter,6));
        //System.out.println(shooter.shape.getBounds().getY());

        GatorEngine.Create(shooter);


        GenerateEnemies(level);

        int x = 0;

        for(int i = 0; i < 3; i++){
            GameObject Life;
            Life = new GameObject(x+=30,460);
            Life.shape = new Rectangle2D.Float(0, 0, 20, 30);
            Life.material = new Material("resources/Ship.png");
            Lives.add(Life);
            GatorEngine.Create(Life);
        }

    }

    private static void GenerateEnemies(int level) {
        int locationX = 10;
        int locationY = 60;
        int invaderLevel = 0;
        HashSet<Integer> enemy2 = new HashSet<>(Arrays.asList(0,5,7,14,17));
        HashSet<Integer> enemy2_1 = new HashSet<>(Arrays.asList(6,10));
        HashSet<Integer> enemy3 = new HashSet<>(Arrays.asList(12,1,17));

        for(int i = 0; i < 18; i++) {
            GameObject invader = new GameObject(locationX+=60, locationY);
            if((i + 1) % 6 == 0){
                locationY+= 50;
                locationX = 5;
            }
            invader.shape = new Ellipse2D.Double(0, 0, 50, 40);
            if(level == 2 && (enemy2.contains(i))){
                invaderLevel = 2;
                invader.material = new Material("resources/Enemy2.png");
            }else if(level == 3 && enemy2_1.contains(i)){
                invaderLevel = 2;
                invader.material = new Material("resources/Enemy2.png");
            }else if(level == 3 && enemy3.contains(i)){
                invaderLevel = 3;
                invader.material = new Material("resources/Enemy3.png");
            }else{
                invaderLevel = 1;
                invader.material = new Material("resources/Enemy1.png");
            }

            invader.scripts.add(new InvaderMovement(invader, 1, invaderLevel));
            Invaders.add(invader);
            GatorEngine.Create(invader);
        }
    }

    public static boolean pastShip(GameObject gameObject){
        return gameObject.transform.getTranslateY() > shooter.transform.getTranslateY();
    }

    public static boolean outOfWindow(GameObject gameObject){
        if(gameObject.transform.getTranslateX()<=0) return true;
        if(gameObject.transform.getTranslateX() + gameObject.shape.getBounds2D().getWidth() >=GatorEngine.WIDTH)
            return true;
        if(gameObject.transform.getTranslateY()<0){
            return true;
        }
        return gameObject.transform.getTranslateY() > GatorEngine.HEIGHT;
    }

    public static void gameEnd(int path){
        ResetGame();
        level = 1;
        GatorEngine.setPath(path);
        GatorEngine.CREATELIST.add(GatorEngine.endButton);
    }

    public static void ResetGame(){
        GatorEngine.DELETELIST.addAll(GatorInvaders.Invaders);
        GatorEngine.DELETELIST.addAll(GatorInvaders.bullets);
        GatorEngine.DELETELIST.add(GatorInvaders.shooter);
        GatorEngine.DELETELIST.addAll(GatorInvaders.Lives);
        GatorEngine.DELETELIST.addAll(GatorInvaders.enemyBullets);

    }

    public static void NextLevel(){
        if(level == 3){
            level = 1;
        }else{
            level++;
        }
        ResetGame();
        Start();
    }
}
