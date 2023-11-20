import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GameObject {
    public AffineTransform transform; //the location/scale/rotation of our object

    // for old transform
    public AffineTransform oldTransform;
    public Shape shape; //the collider/rendered shape of this object
    public Material material; //data about the fill color, border color, and border thickness
    public ArrayList<ScriptableBehavior> scripts = new ArrayList<>(); //all scripts attached to the object
    public boolean active = true; //whether this gets Updated() and Draw()



    //TODO: create the default GameObject use a default AffineTransform, default Material, and a 10x10 pix rectangle Shape at 0,0
    public GameObject(){
        this.transform = new AffineTransform();
        this.material = new Material();
        this.shape = new Rectangle(0,0,10,10);

    }

    //TODO: create the default GameObject, but with its AffineTransform translated to the coordinate x,y
    public GameObject(int x, int y){
        this.transform = new AffineTransform();
        this.transform.translate(x, y);
        this.material = new Material();
        this.shape = new Rectangle(0,0,10,10);

    }

    //TODO: 1) save the pen's old transform, 2) transform it based on this object's transform, 3) draw either the styled shape, or the image scaled to the bounds of the shape.
    public void Draw(Graphics2D pen){
        oldTransform = pen.getTransform();
        pen.transform(transform);

        if(!material.isShape){
            double sX = shape.getBounds().getWidth()/material.getImg().getWidth();
            double sY = shape.getBounds().getHeight()/material.getImg().getHeight();
            pen.scale(sX, sY);
            pen.drawImage(material.getImg(),0,0,null);

        }else{
            pen.setColor(material.getFill());
            pen.fill(shape);
            pen.setColor(material.getBorder());
            pen.setStroke(new BasicStroke(material.getBorderWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            pen.draw(shape);


        }

        pen.setTransform(oldTransform);


    }

    //TODO: start all scripts on the object
    public void Start(){
        for (ScriptableBehavior script: scripts) {
            script.Start();
        }
    }

    //TODO: update all scripts on the object
    public void Update(){
        for (ScriptableBehavior script: scripts) {
            script.Update();
        }
    }

    //TODO: move the GameObject's transform
    public void Translate(float dX, float dY){
        transform.translate(dX,dY);
    }

    //TODO: scale the GameObject's transform around the CENTER of its shape
    public void Scale(float sX, float sY){
            Rectangle2D bounds = shape.getBounds();
            transform.translate(bounds.getCenterX(), bounds.getCenterY());
            transform.scale(sX, sY);
            transform.translate(-bounds.getCenterX(),-bounds.getCenterY());
            Shape s = transform.createTransformedShape(shape);
            s.getBounds().getX();

    }

    //TODO: should return true if the two objects are touching (i.e., the intersection of their areas is not empty)
    public boolean CollidesWith(GameObject other){

        Area area = new Area(shape);
        area.transform(transform);

        Area otherArea = new Area(other.shape);
        otherArea.transform(other.transform);

        area.intersect(otherArea);

        return !area.isEmpty();

    }

    //TODO: should return true of the shape on screen contains the point
    public boolean Contains(Point2D point){
        return shape.contains(point);
    }

}
