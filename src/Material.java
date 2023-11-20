import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Material {
    Color fill, border;
    int borderWidth;
    boolean isShape = true;
    BufferedImage img = null;

    //TODO: create default, black fill and border with zero borderWidth
    Material(){
        this.fill = Color.BLACK;
        this.borderWidth = 0;
    }

    //TODO: set the fields
    public Material(Color fill, Color border, int borderWidth) {
        this.fill = fill;
        this.border = border;
        this.borderWidth = borderWidth;
    }

    //TODO: load the image at the path and set isShape flag to false
    public Material(String path){
        setImg(path);
        isShape = false;
    }

    //Getters and Setters, done for you!
    public Color getFill() {
        return fill;
    }

    public void setFill(Color fill) {
        this.fill = fill;
    }

    public Color getBorder() {
        return border;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int stroke_width) {
        this.borderWidth = stroke_width;
    }

    public BufferedImage getImg(){return img;}

    //TODO: write this part, load the image and set it
    public void setImg(String path){

        try {
            File f = new File(path);
            BufferedImage image = ImageIO.read(f);
            setImg(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImg(BufferedImage img){this.img=img;}


}
