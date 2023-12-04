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

        if(gameObject.CollidesWith(GatorInvaders.shooter)){

            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GatorInvaders.shooter.material.setImg("resources/Ship.png");
                    ((Timer) e.getSource()).stop();

                }
            });
            if(GatorInvaders.shooter.CollidesWith(gameObject)){
                GatorInvaders.shooter.material.setImg("resources/ShipHit.png");
                timer.start();

            }



            //System.out.println("enemy bullet hit shooter");
            // subtract life if it has any and add bullet to deletelist
            if(GatorInvaders.Lives.size() > 1){
                System.out.println("Lives greater than 1");
                GatorEngine.DELETELIST.add(gameObject);
                int index = GatorInvaders.Lives.size() - 1;
                GatorEngine.DELETELIST.add(GatorInvaders.Lives.get(index));
                GatorInvaders.Lives.remove(index);
            }else{
                // game over
                System.out.println("Lives over");

                GatorEngine.DELETELIST.add(gameObject);
                GatorInvaders.gameEnd(2);

            }
        }
    }
}