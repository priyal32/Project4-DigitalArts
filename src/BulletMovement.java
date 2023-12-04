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

//        if(stop){
//            return;
//        }
//
//       // GameObject invaderToRemove = null;
//        for(GameObject invader : GatorInvaders.Invaders){
//            if(this.gameObject.CollidesWith(invader)){
//                System.out.println("collided with an invader");
//
//                speed = 0;
//                GatorEngine.DELETELIST.add(gameObject);
//                GatorInvaders.bullets.remove(gameObject);
//                //invaderToRemove = invader;
//                stop = true;
//                break;
//            }
//        }
//
//        if(!stop){
            gameObject.Translate(0, -speed);
       // }
       //if(invaderToRemove != null)
          // GatorInvaders.Invaders.remove(invaderToRemove);


    }
}
