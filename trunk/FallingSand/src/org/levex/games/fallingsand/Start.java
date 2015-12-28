/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
//import org.lwjgl.opengl.GL11;
import org.lwjgl.Sys;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
//import org.newdawn.slick.geom.Circle;
//import org.newdawn.slick.geom.Polygon;
//import org.newdawn.slick.geom.Rectangle;
//import org.newdawn.slick.geom.Shape;
//import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Levex
 */
public class Start extends BasicGame{



    /*static {
        System.loadLibrary("LevLib");
        // I LOVE NATIVES :))))))))))
    }*/

    /*//public static final native void test();
    public static native String getVersion();
    public static native String getWindowName();
//    public static native String getNameOfSand(int sand);
    public static native void initNatives(int lang);*/

    public static String VERSION = "0.4_11";
    public static String TITLE = "Sandye";

    public static GameContainer gc;
    public static Graphics graphics;
    public static short height = 0;
    public static boolean busy;
    static GUI gui = null;

    public static int KEY_CLEAR = Input.KEY_C;

    public static final int STATE_MENU = 0;
    public static final int STATE_GAMEPLAY = 1;
    public static int CURRENT_STATE = 1;

    public static enum GAME_MODES {SANDBOX, RPG};
    public static GAME_MODES gm = GAME_MODES.SANDBOX;

    public static boolean falls = false;
    public static boolean showDebugText = true;

    public static boolean isPaused = false;

    public static String IP = "127.0.0.1";

    public static Image start = null;

    public static boolean TapWaterFlow = false;

    public static PenChooser pc = null;

    public static boolean isMultiTest = false;

    public static boolean isApplet = false;

    static Socket clientSocket = null;

    static BufferedReader inFromUser = null;
    static DataOutputStream outToServer = null;
    static BufferedReader inFromServer = null;

    private static Start instance = null;

    public int currentPenSize = 1;
    public static HashMap<String, Method> physx = new HashMap<String, Method>();
//    char currentSand = 0x01;

    public Start() throws SlickException, Exception {
        super(TITLE+" "+VERSION);
        Method[] methods = Physics.class.getDeclaredMethods();
        for(Method method : methods) {
            if(method.isAnnotationPresent(PhysX.class)) {
                PhysX a = method.getAnnotation(PhysX.class);
                physx.put(a.name(), method);
            }
        }

        

        Properties.setupProps();
        Properties.loadElements();
        Wind.initWind();

        if(isMultiTest) {
            try {
                clientSocket = new Socket(IP, 8085);
                inFromUser = new BufferedReader(new InputStreamReader(System.in));
                outToServer = new DataOutputStream(clientSocket.getOutputStream());
                inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch(Exception e) {
                Sys.alert("Sandye", "Error: "+e.getMessage());
                Sys.alert("Sandye", "Press M to reinitalize Multiplayer.");
                isMultiTest = false;
            }
        }
        //System.loadLibrary("levex.dll");
        //test();
        instance = this;
    }

    public static Start getInstance() {
        return instance;
    }

    //public final native void test();

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SlickException, Exception {
        int W = Toolkit.getDefaultToolkit().getScreenSize().width;
        int H = Toolkit.getDefaultToolkit().getScreenSize().height;

        PreGameSettings psg = new PreGameSettings();
        psg.setLocation((W-psg.getSize().width)/2, (H-psg.getSize().height)/2); // Levex is awesome. // I know :P
        psg.setVisible(true);

        while(!psg.finished) {}
        AppGameContainer app =	new AppGameContainer(new Start());
         app.setDisplayMode(Canvas.MAP_WIDTH, Canvas.MAP_HEIGHT, false);
         app.setClearEachFrame(true);
         app.setFullscreen(psg.fullscreen);
         app.setVerbose(false);
         //app.
         app.setAlwaysRender(true);
         app.start();
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        //container.setVSync(true);
        gc = container;
         pc = new PenChooser();
         //pc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
         pc.setVisible(true);
         pc.setAlwaysOnTop(true);
        container.setTargetFrameRate(60);
        try {
            Canvas.init();
        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(gm == GAME_MODES.RPG) {
            Canvas.initRPG((int) System.currentTimeMillis());
        }

        //start = new Image("data/start.png");
        Start.gc = container;
        Start.graphics = container.getGraphics();

        Physics.g = container.getGraphics();
        Canvas.g = Physics.g;
        //new Thread(new RenderThread(gc.getGraphics())).start();

        //Canvas.generate(1);
        //gui = new GUI();
        //Canvas.addSandToMap(new SandSand(70,70,Sand.SAND));
        //new Thread(new RenderThread(container.getGraphics())).start();
    }

    public @Override void update(GameContainer gc, int delta) throws SlickException {
        switch(CURRENT_STATE) {
            case STATE_MENU:
                updateMENU(gc, delta);
                break;
            case STATE_GAMEPLAY:
                updateGAMEPLAY(gc, delta);
                break;
        }
    }

    public void updateMENU(GameContainer container, int delta) throws SlickException {
        Input input = container.getInput();
        if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            CURRENT_STATE = STATE_GAMEPLAY;
        }
    }

    public void updateGAMEPLAY(GameContainer container, int delta) throws SlickException {
        //System.gc();
        //container.setTargetFrameRate(60);
        currentPenSize = pc.sandSize;
        Input i = container.getInput();
        /*if(i.isKeyPressed(i.KEY_C)) {
            pc.setVisible(!pc.isVisible());
        }*/
        if(i.isKeyPressed(KEY_CLEAR)) {
            try {
                if(isMultiTest) {
                    outToServer.writeBytes("clear"+'\n');
                }
                Canvas.init();
                Wind.initWind();
            } catch (IOException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(i.isKeyDown(i.KEY_RIGHT) && gm == GAME_MODES.RPG) {
            Canvas.moveCharacterX(delta/2);
        }
        if(i.isKeyDown(i.KEY_LEFT) && gm == GAME_MODES.RPG) {
            Canvas.moveCharacterX((-delta)/2);
        }
        if(i.isKeyDown(i.KEY_UP) && gm == GAME_MODES.RPG) {
            Canvas.moveCharacterY((-delta)/2);
        }
        if(i.isKeyDown(i.KEY_DOWN) && gm == GAME_MODES.RPG) {
            Canvas.moveCharacterY(delta/2);
        }
        if(i.isKeyDown(i.KEY_F) && gm == GAME_MODES.RPG) {
            Canvas.useUtility(Properties.ids.get("fire"));
        }
        if(i.isKeyPressed(i.KEY_M) && gm == GAME_MODES.RPG) {
            Canvas.mine();
        }
        /*if(i.isKeyPressed(i.KEY_D) && gm == GAME_MODES.RPG) {
            char[] inv = Inventory.getInv();
            for(int a = 0; a < inv.length; a++) {
                System.out.println(""+Properties.name.get(a)+" :"+(int)inv[a]);
            }
        }*/
        if(i.isKeyDown(i.KEY_Q)) {
            try {
              //dispose();
              System.gc();
              System.exit(0);
            } catch (Exception ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(i.isKeyPressed(i.KEY_P)) {
            isPaused = !isPaused;
        }

        if(i.isKeyPressed(i.KEY_F)) {
            falls = !falls;
        }

        if(i.isKeyPressed(i.KEY_M) && gm == GAME_MODES.SANDBOX) {
            if(isMultiTest) {
                isMultiTest = false;
                try {
                    clientSocket.close();
                    outToServer.close();
                    inFromServer.close();
                    inFromUser.close();
                } catch (Exception ex) {
                    Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                }
                Sys.alert("Sandye", "Multiplayer has been switched off.");
                return;
            }
            if(!isMultiTest) {
                try {
                    TextPanel tp = new TextPanel();
                    tp.setLocation(container.getScreenWidth()/2, container.getScreenHeight()/2);
                    tp.setAlwaysOnTop(true);
                    tp.setVisible(true);
                    while(!tp.finished) {
                        
                    }
                    tp.dispose();
                    clientSocket = new Socket(IP, 8085);
                    inFromUser = new BufferedReader(new InputStreamReader(System.in));
                    outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    inFromServer =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    isMultiTest = true;
                } catch (Exception ex) {
                    if(ex instanceof UnknownHostException) {
                        Sys.alert("Sandye","Invalid IP adress!");
                        isMultiTest = false;
                        return;
                    }
                    if(ex instanceof ConnectException) {
                        Sys.alert("Sandye", "Error occured: "+ex.getMessage());
                        isMultiTest = false;
                        return;
                    }
                    ex.printStackTrace();
                }
                Sys.alert("Sandye", "Multiplayer has been switched on.");
                return;
            }
        }
        if(i.isKeyPressed(i.KEY_W)) {
            Wind.setWind(Wind.DIRECTION_LEFT, (char)500, i.getMouseX(), i.getMouseY());
        }
        if(i.isKeyPressed(i.KEY_V)) {
            showDebugText = !showDebugText;
            container.setShowFPS(showDebugText);
        }

        /*if(i.isKeyPressed(i.KEY_S)) {
            try {
                save();
            } catch (Exception ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/

        if(i.isKeyDown(i.KEY_G)) {
            try {
                Canvas.initRPG((int) System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*if(i.isKeyPressed(i.KEY_L)) {
            try {
                load();
            } catch (Exception ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            //Canvas.needUpdate = new Rectangle(i.getMouseX()-200, i.getMouseY()-150, 300, 300);
            if(isMultiTest) {
                try {
                    outToServer.writeBytes("click " + (int)i.getMouseX() + " " + (int)i.getMouseY() + " "+(int)pc.sandSize+" " + (int)pc.sand + '\n');
                } catch (IOException ex) {
                    Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            switch(pc.sandShape) {
                case 0:
                    Penshape.Disc(i.getMouseX(), i.getMouseY(), currentPenSize, pc.sand);
                    break;
                case 1:
                    Penshape.Square(i.getMouseX(), i.getMouseY(),currentPenSize, pc.sand);
                    break;
                case 2:
                    Penshape.Triangle(i.getMouseX(), i.getMouseY(),currentPenSize, pc.sand);
                    break;
                case 3:
                    Penshape.Circle(i.getMouseX(), i.getMouseY(), currentPenSize, pc.sand);
                    break;
            }
        }
        if(i.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            int mousex = i.getMouseX(), mousey = i.getMouseY();
                    TapWaterFlow = !TapWaterFlow;
        }
        //Canvas.update(container, delta);
    }

    public static void save() throws Exception {
       JFileChooser chosen = new JFileChooser();
       chosen.setVisible(true);
       chosen.showSaveDialog(null);
       File f = chosen.getSelectedFile();
       if(!f.getAbsolutePath().endsWith(".levmap"))
       f = new File(f.getAbsolutePath()+".levmap");
       FileOutputStream fos = new FileOutputStream(f);
       ObjectOutputStream out = new ObjectOutputStream(fos);
       out.writeObject(Canvas.map);
       out.close();
    }

    public static void load() throws Exception {
        JFileChooser chosen = new JFileChooser();
       int returnVal = chosen.showOpenDialog(null);
       File f = chosen.getSelectedFile();
       FileInputStream fos = new FileInputStream(f);
       ObjectInputStream out = new ObjectInputStream(fos);
       Canvas.map = (char[][]) out.readObject();
       out.close();
    }

    class MouseClickData {
	int x,  y,size, type;
	public MouseClickData(int x, int y,int size, int type) {
		this.x = x;
		this.y = y;
                this.size = size;
		this.type = type;
	}
}

    public @Override void render(GameContainer gc, Graphics g) throws SlickException {
        try {
            if (isMultiTest && inFromServer.ready()) {
               String str = inFromServer.readLine();
               if(str.startsWith("click")) {
                   int[] data =new int[5];
                   data[0] = Integer.parseInt(str.split(" ")[1]); // x
                   data[1] = Integer.parseInt(str.split(" ")[2]); // y
                   data[2] = Integer.parseInt(str.split(" ")[3]); // meret
                   data[3] = Integer.parseInt(str.split(" ")[4]); // tipus
                   MouseClickData mcd = new MouseClickData(data[0], data[1], data[2], data[3]);
                   Penshape.Disc(mcd.x, mcd.y, mcd.size,(char) mcd.type);
               }
               if(str.startsWith("clear")) {
                    try {
                        Canvas.init();
                        Wind.initWind();
                    } catch (IOException ex) {
                        Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
            }
        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch(CURRENT_STATE) {
            case STATE_MENU:
                renderMENU(gc, g);
                break;
            case STATE_GAMEPLAY:
                renderGAMEPLAY(gc, g);
                break;
        }
    }
    public void renderMENU(GameContainer gc, Graphics g) throws SlickException {
        gc.setTargetFrameRate(100000);

        start.drawCentered(Canvas.MAP_WIDTH/2, Canvas.MAP_HEIGHT/2);
    }
    public void renderGAMEPLAY(GameContainer container, Graphics g) throws SlickException {
        try {
            Canvas.render();
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
       /* if(falls) {
            Penshape.Disc(100, 4, 10, (char)Sand.WATER);
            Penshape.Disc(200, 4, 10, (char)Sand.SAND);
            Penshape.Disc(300, 4, 10, (char)Sand.SALT);
            Penshape.Disc(400, 4, 10, (char)Sand.OIL);
        }*/
        //Input i = container.getInput();
        if(showDebugText) {
            g.setColor(Color.white);
            //g.drawString("Press S to save, L to load and Q to quit.",100, 10);
            //g.drawString("Sand: "+Canvas.getSize(), 10, 30);
            if(!isApplet)
            g.drawString("Mouse ON: "+Properties.name.get((int)Canvas.map[container.getInput().getMouseX()][(container.getInput().getMouseY())])+" AT ("+container.getInput().getMouseX()+","+container.getInput().getMouseY()+")", 10, 50);
            g.drawString("Current sand: "+(int) pc.sand, 10, 70);
            //g.drawString("Tap enabled: "+TapWaterFlow, 10, 90);
            if(!isApplet)
            g.drawString("Current Temperature: "+Canvas.temperature[(int) container.getInput().getMouseX()][(int)container.getInput().getMouseY()], 10, 110);
            if(!isApplet)
            g.drawString("Current Energy: "+(int)Canvas.energy[(int) container.getInput().getMouseX()][(int)container.getInput().getMouseY()], 10, 130);
            if(!isApplet)
            g.drawString("Current pressure: "+(int)Canvas.getPressure(container.getInput().getMouseX(), container.getInput().getMouseY())+"Pa", 10, 150);
        }
            /*
        //Circle c = new Circle(container.getInput().getMouseX(), container.getInput().getMouseY(), currentPenSize/2);
        Shape s = null;
        //*
        switch(pc.sandShape) {
            case 0:
            case 1:
                s = new Circle(container.getInput().getMouseX(), container.getInput().getMouseY(), currentPenSize/2);
                break;
            case 2:
                s = new Rectangle(i.getMouseX()-currentPenSize/2,i.getMouseY()-currentPenSize/2, currentPenSize, currentPenSize);
                break;
            case 3:
                //s = new Polygon(Triangle.getPoints(i.getMouseX(), i.getMouseY(), currentPenSize));
                break;
        }
        g.draw(s);
        // */
    }

}
