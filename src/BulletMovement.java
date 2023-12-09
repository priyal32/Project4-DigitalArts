import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

class BulletMovement extends ScriptableBehavior{

    int speed=1;
    Boolean stop = false;
    BulletMovement(GameObject gameObject, int speed) {
        super(gameObject);
        this.speed = speed;
    }

    @Override
    public void Start() {
    }

    @Override
    public void Update() {
        gameObject.Translate(0, -speed);
    }
}
