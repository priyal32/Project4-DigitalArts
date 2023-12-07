import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

class EnemyBulletMovement extends ScriptableBehavior {

    int speed = 0;
    EnemyBulletMovement(GameObject g, int speed) {
        super(g);
        this.speed = speed;
    }

    @Override
    public void Start() {

    }

    @Override
    public void Update() {
        gameObject.Translate(0, speed);


        Timer timer = new Timer(1500, e -> {
            GatorInvaders.shooter.material.setImg("resources/Ship.png");
            ((Timer) e.getSource()).stop();
            ShipMovement.immunity = false;

        });
        if(GatorInvaders.shooter.CollidesWith(gameObject)){
            GatorInvaders.shooter.material.setImg("resources/ShipHit.png");
            if(!ShipMovement.immunity){
                // subtract life if it has any and add bullet to deletelist
                if(GatorInvaders.Lives.size() > 1){
                    GatorEngine.DELETELIST.add(gameObject);
                    int index = GatorInvaders.Lives.size() - 1;
                    GatorEngine.DELETELIST.add(GatorInvaders.Lives.get(index));
                    GatorInvaders.Lives.remove(index);
                }else{
                    // game over
                    GatorEngine.DELETELIST.add(gameObject);
                    GatorInvaders.gameEnd(2);
                }
                ShipMovement.immunity = true;
                timer.start();

            }

        }
    }
}