import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.time.Instant;
class ShipMovement extends ScriptableBehavior {


    int speed = 1;
    public static boolean immunity = false;

    int delay = 0;
    int shootingDelay = 0;
    boolean pressedAlready = false;

    ShipMovement(GameObject gameObject, int speed) {
        super(gameObject);
        this.speed = speed;
    }

    @Override
    public void Start() {
        delay = 0;
        shootingDelay = 0;
    }

    @Override
    public void Update() {


        if (Input.GetKeyDown('d') && gameObject.transform.getTranslateX() + gameObject.shape.getBounds2D().getWidth() <= 499) {
                gameObject.Translate(speed, 0);
        }
        if (Input.GetKeyDown('a')  && gameObject.transform.getTranslateX() >= 0) {
                gameObject.Translate(-speed, 0);
        }
        if (Input.GetKeyPressed(' ') && delay - shootingDelay > 10) {
                GameObject bullet = new GameObject((int) GatorInvaders.shooter.Bounds().getX() + 25, (int)  GatorInvaders.shooter.Bounds().getY() - 23);
                bullet.shape = new Ellipse2D.Double(0, 0, 9, 9);
                bullet.material = new Material(Color.YELLOW, Color.GREEN, 1);
                bullet.scripts.add(new BulletMovement(bullet, 30));
                GatorInvaders.bullets.add(bullet);
                GatorEngine.Create(bullet);
                shootingDelay = delay;
        }
        delay++;

    }
}