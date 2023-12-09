import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.time.Instant;
import java.util.Random;


// TODO: Higher level enemies should have a higher probability of shooting bullets
// TODO: invader movement is kinda wonky


public class InvaderMovement extends  ScriptableBehavior {
    int j = 1;
    long start = 0;
    double speed;

    int delay = 0;
    boolean movementChanged = false;

    int invaderLevel;


    InvaderMovement(GameObject g, double speed, int invaderLevel) {
        super(g);
        this.speed = speed;
        this.invaderLevel = invaderLevel;
    }

    @Override
    public void Start() {
        delay = 0;
    }

    @Override
    public void Update() {
        if(GatorInvaders.pastShip(gameObject)){
            GatorInvaders.gameEnd(2);
        }

        if (delay - start >= 40) {
            j++;
            if (j > 4) {
                j = 1;
            }
            start = delay;
            Random random = new Random();

            int probability = 0;
            if(invaderLevel == 1)
                probability = random.nextInt(20);
            else if (invaderLevel == 2)
                probability = random.nextInt(22);
            else if(invaderLevel == 3)
                probability = random.nextInt(24);

            if (probability > 17) {
                GameObject bullet = new GameObject((int) gameObject.Bounds().getX() + 10, (int) gameObject.Bounds().getY() - 10);
                bullet.shape = new Ellipse2D.Double(0, 0, 7, 7);
                bullet.material = new Material(Color.BLUE, Color.ORANGE, 3);
                bullet.scripts.add(new EnemyBulletMovement(bullet, 10));
                GatorEngine.Create(bullet);
                GatorInvaders.enemyBullets.add(bullet);
            }

        }
        delay++;

        switch (j) {
            case 1:
                gameObject.Translate((float) speed, 0);
                break;
            case 2:
                gameObject.Translate((float) 0, (float) speed);
                break;
            case 3:
                gameObject.Translate((float) -speed, 0);
                break;
            case 4:
                gameObject.Translate((float) 0, (float) speed);
                break;
        }


        GameObject bulletToRemove = null;
        for(GameObject bullet : GatorInvaders.bullets){
            if(this.gameObject.CollidesWith(bullet)){
                if(invaderLevel == 1) {
                    GatorEngine.DELETELIST.add(gameObject);
                    GatorInvaders.Invaders.remove(gameObject);

                }else{
                    changeInvaderLevel(invaderLevel);
                }
                bulletToRemove = bullet;
                break;
            }
        }
        if(bulletToRemove != null){
            GatorEngine.DELETELIST.add(bulletToRemove);
            GatorInvaders.bullets.remove(bulletToRemove);
        }
        if(GatorInvaders.Invaders.isEmpty()){
            GatorInvaders.NextLevel();
        }

    }

    public void changeInvaderLevel(int level){
        if(level == 3){
            invaderLevel = 2;
            gameObject.material = new Material("resources/Enemy2.png");
        }
        if(level == 2){
            invaderLevel = 1;
            gameObject.material = new Material("resources/Enemy1.png");
        }
    }
}
