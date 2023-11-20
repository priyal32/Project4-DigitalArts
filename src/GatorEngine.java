import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GatorEngine {
    //UI Components (things that are "more" related to the UI)
    static JFrame WINDOW;
    static JPanel DISPLAY_CONTAINER;
    static JLabel DISPLAY_LABEL;
    static BufferedImage DISPLAY;
    static int WIDTH=500, HEIGHT=500;

    //Engine Components (things that are "more" related to the engine structures)
    static Graphics2D RENDERER;
    static ArrayList<GameObject> OBJECTLIST = new ArrayList<>(); //list of GameObjects in the scene
    static ArrayList<GameObject> CREATELIST = new ArrayList<>(); //list of GameObjects to add to OBJECTLIST at the end of the frame
    static ArrayList<GameObject> DELETELIST = new ArrayList<>(); //list of GameObjects to remove from OBJECTLIST at the end fo the frame
    static float FRAMERATE = 60; //target frames per second;
    static float FRAMEDELAY = 1000/FRAMERATE; //target delay between frames
    static Timer FRAMETIMER; //Timer controlling the update loop
    static Thread FRAMETHREAD; //the Thread implementing the update loop
    static Thread ACTIVE_FRAMETHREAD; //a copy of FRAMETHREAD that actually runs.


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CreateEngineWindow();
            }
        });
    }

    static void CreateEngineWindow(){
        //Sets up the GUI
        WINDOW = new JFrame("Gator Engine");
        WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WINDOW.setVisible(true);

        DISPLAY = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
        RENDERER = (Graphics2D) DISPLAY.getGraphics();
        DISPLAY_CONTAINER = new JPanel();
        DISPLAY_CONTAINER.setFocusable(true);
        DISPLAY_LABEL = new JLabel(new ImageIcon(DISPLAY));
        DISPLAY_CONTAINER.add(DISPLAY_LABEL);
        WINDOW.add(DISPLAY_CONTAINER);
        WINDOW.pack();

        //TODO: make this 1)execute Update(), 2) clear any inputs that need to be removed between frames, and 3) repaint the GUI back on the EDT.
        FRAMETHREAD = new Thread(new Runnable() {
            @Override
            public void run() {
                Update();
                Input.UpdateInputs();
                UpdateObjectList();
                SwingUtilities.invokeLater(() -> {
                    WINDOW.repaint();
                });

            }
        });

        //This copies the template thread made above
        ACTIVE_FRAMETHREAD = new Thread(FRAMETHREAD);

        //TODO: create a timer that will create/run ACTIVE_FRAMETHREAD, but only if it it hasn't started/has ended
        FRAMETIMER = new Timer((int)FRAMEDELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!ACTIVE_FRAMETHREAD.isAlive()){
                    ACTIVE_FRAMETHREAD = new Thread(FRAMETHREAD);
                    ACTIVE_FRAMETHREAD.start();
                }
            }
        });
        FRAMETIMER.start();

        Start();

        //===================INPUT=========================
        //Set up some action listeners for input on the PANEL
        //These should update the Input classes ArrayLists and other members
        //TODO: use the correct listener functions to modify INPUT
        DISPLAY_CONTAINER.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                Input.pressed.add(e.getKeyChar());
                Input.held.add(e.getKeyChar());            }

            @Override
            public void keyReleased(KeyEvent e) {
                Object o = e.getKeyChar();
                Input.released.add(e.getKeyChar());
                Input.held.remove(o);
            }
        });
        DISPLAY_CONTAINER.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Input.MouseX = e.getX();
                Input.MouseY = e.getY();
                Input.MouseClicked = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Input.MouseX = e.getX();
                Input.MouseY = e.getY();
                Input.MousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Input.MouseX = e.getX();
                Input.MouseY = e.getY();
                Input.MousePressed = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Input.MouseX = e.getX();
                Input.MouseY = e.getY();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Input.MouseX = e.getX();
                Input.MouseY = e.getY();
            }
        });
        DISPLAY_CONTAINER.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Input.MouseX = e.getX();
                Input.MouseY = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Input.MouseX = e.getX();
                Input.MouseY = e.getY();
            }
        });
    }

    //TODO: add the GameObject to the OBJECTLIST
    static void Create(GameObject g){
        CREATELIST.add(g);
    }

    //TODO: remove the GameObject from the OBJECTLIST
    static void Delete(GameObject g){

        DELETELIST.add(g);
    }

    //TODO: 1) remove objects in DELETELIST from OBJECTLIST, 2) add objects in CREATELIST to OBJECTLIST, 3) remove all items from DELETELIST and CREATELIST
    static void UpdateObjectList(){
        OBJECTLIST.removeAll(DELETELIST);
        OBJECTLIST.addAll(CREATELIST);
        DELETELIST.clear();
        CREATELIST.clear();
    }



    //This begins the "user-side" of the software; above should set up the engine loop, data, etc.
    //Here you can create GameObjects, assign scripts, set parameters, etc.
    //NOTE: This is where we should be able to insert out own code and scripts
    static void Start(){
        //TODO: Start() all objects in OBJECTLIST
        for(GameObject o : OBJECTLIST){
            o.Start();
        }

    }

    //TODO: Redraw the Background(), then Draw() and Update() all GameObjects in OBJECTLIST
    static void Update(){
        // Tests.TestControllerUpdate();
        Background();
        for (GameObject o : OBJECTLIST) {
            o.Draw(RENDERER);
            o.Update();
        }


    }

    //draws a background on the Renderer. right now it is solid, but we could load an image
    //done for you!
    static void Background(){

        RENDERER.setColor(Color.WHITE);
        RENDERER.fillRect(0,0,WIDTH,HEIGHT);
    }

}
