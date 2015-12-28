/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Date;
import java.util.Random;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.lwjgl.Sys;
//import org.newdawn.slick.Color;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

/**
 *
 * @author Levex
 */
public class Canvas {

    public static int MAP_WIDTH = 500;
    public static int MAP_HEIGHT = 500 ;

    public static int MAP_X_MAX = MAP_WIDTH-4;
    public static int MAP_Y_MAX = MAP_HEIGHT-2 ;

    /*private static BufferedImage bi = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private static Graphics2D big = bi.createGraphics();*/
    private static Image im = null;
    private static Graphics im_g = null;

    public static int frames = 0;

    public static Rectangle needUpdate = new Rectangle(0, 0, MAP_WIDTH+2, MAP_HEIGHT+2);

    private static Image toRender = null;

    public static Graphics g = null;

    public static volatile char[][] map = new char[MAP_WIDTH+4][MAP_HEIGHT+4];
    public static volatile char[][] energy = new char[MAP_WIDTH+4][MAP_HEIGHT+4];
    public static volatile boolean[][] draw = new boolean[MAP_WIDTH+4][MAP_HEIGHT+4];
    public static volatile float[][] temperature = new float[MAP_WIDTH+4][MAP_HEIGHT+4];
    public static volatile char[][] pressure = new char[MAP_WIDTH+4][MAP_HEIGHT+4];
    public static PointRa changed = new PointRa();


    public static char sand = 0;
    //static Method[] methods = Physics.class.getDeclaredMethods();
    //public static ArrayList<Method> physs = new ArrayList<Method>();
    //static PhysX[] ma = null;
    private static Random rnd = new Random();

    private static int brrr = 0;

    public static FileWriter log;

    public static void writeLog(String str) throws IOException {
        log.append(Calendar.getInstance().get(Calendar.YEAR)+". "+Calendar.getInstance().get(Calendar.MONTH)+". "+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+". : "+str);
        System.out.println(Calendar.getInstance().get(Calendar.YEAR)+". "+Calendar.getInstance().get(Calendar.MONTH)+" "+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+": "+str);
    }

    public static char getPressure(int x0, int y0) { // need to fasten it!
        char p = 0;
        for(int y =y0; y > 0; y--) {
            if(map[x0][y] == 0) {
                break;
            }
            p++;
        }
        return (char) (p / 2);
    }


    public static int checkRoute(int x0, int dest, int y) {
        int step = 1;
        if(dest < x0) step = -1;
        int toReturn = 0;
        //System.err.println("x0: "+x0+" dest:"+dest);
        for(int x = x0; x != dest; x+=step) {
            //System.err.println("ITERATION: "+x);
            if(map[x+step][y] != 0) {
                //System.err.println("...");
                return toReturn;
            }
            toReturn++;
        }
        return toReturn;
    }

    /*
     * 
     * Moves horizontally. Used for wind.
     * @args x0 - Original position
     * @args y0 - Original position
     * @args x2 - New X position
     * @rgs type - Type of particle (used for speed)
     *
     */
    public static void moveParticle(int x0, int y0, int x2, char type) {
        int step = 1;
        if(x2 < x0) {

            step = -1;
        }
        for(int x=x0;x!=x2; x+=step) {
            if(map[x][y0] != 0 || x >= MAP_WIDTH || x <= 0) {
                Canvas.map[x][y0] = type;
                Canvas.draw[x][y0] = false;
                Canvas.map[x0][y0] = 0;
                return;
            }
            if(rnd.nextBoolean()) {
                Canvas.map[x][y0] = type;
                Canvas.map[x0][y0] = 0;
                Canvas.draw[x][y0] = false;
                continue;
            }
            x+=step;
        }
    }


    public static void init() throws IOException, SlickException {
        try {
            log = new FileWriter(new File("levlog.log"));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        map = null;
        energy  = null;
        draw = null;
        temperature = null;
        map = new char[MAP_WIDTH+4][MAP_HEIGHT+4];
        energy = new char[MAP_WIDTH+4][MAP_HEIGHT+4];
        draw = new boolean[MAP_WIDTH+4][MAP_HEIGHT+4];
        temperature = new float[MAP_WIDTH+4][MAP_HEIGHT+4];
        pressure = new char[MAP_WIDTH+4][MAP_HEIGHT+4];
        toRender = new Image(MAP_WIDTH+4, MAP_HEIGHT+4);
        for(int x=0;x < MAP_WIDTH; ++x) {
            for(int y=0;y<MAP_HEIGHT;++y) {
                map[x][y] = 0;
                temperature[x][y] = 0f;
                pressure[x][y] = 0;
                energy[x][y] = 0;
                draw[x][y] = true;
                //log.append("X: "+x+" Y: "+y+" T: "+(int)temperature[x][y]+" E: "+(int)energy[x][y]+" D: "+draw[x][y]+"\n");
            }
        }
        /*ma = new PhysX[methods.length];
        for (int i = 0; i<methods.length; i++) {
            PhysX methodAnnotation = methods[i].getAnnotation(PhysX.class);
            if(methodAnnotation == null) continue;
            physs.add(methods[i]);
        }*/
        changed.x = 0;
        changed.y = 0;
        changed.type = 0;
        sand = 0;
        writeLog("Init() done.");
    }

    public static int whatIsHere(int x, int y) {
            return map[x][y];
    }

    public static int getEnergy(int x, int y) {
            return energy[x][y];
    }

    public static int setEnergy(int x, int y, int e) {
        System.err.println("Energy has been set to ("+x+","+y+","+e+","+Integer.toHexString(map[x][y])+")");
        energy[x][y] = (char) e;
        return e;
    }

    public static void setPixel(int x, int y, int material) {
        if(2<=x && x<=MAP_X_MAX && 2<=y && y<=MAP_X_MAX) {
            map[x][y] = (char) material;
        }
    }

    public static int getSize() {
        return sand;
    }

    public static int addSandToMap(int x, int y, char t) {
        map[x][y] = t;
        /*if(t == Sand.WATER_TAP_OPENED ||t == Sand.WATER_wTAP_CLOSED) {
            energy[x][y] = 0;
        }*/
        sand++;
        return t;
    }

    public static void update(GameContainer gc, int delta) {
    }

    public static void moveCharacterX(int x) {
        Generator.moveCharacterX(x);
        map = Generator.map;
    }
     public static void moveCharacterY(int y) {
        Generator.moveCharacterY(y);
        map = Generator.map;
    }
    public static void useUtility(char w) {
        Generator.useUtility(w);
        map = Generator.map;
    }

    public static void mine() {
        Generator.mine(Generator.char_x, Generator.char_y);
        map = Generator.map;
    }

    public static void initRPG(int seed) throws SlickException {
        Generator.init(MAP_WIDTH+2, MAP_HEIGHT+2);
        Generator.generate(seed);
        Generator.addCharacter(Properties.ids.get("char"), (MAP_WIDTH+2)/2, (MAP_HEIGHT+2)/2);
        map = Generator.map;
    }

    public static void generate(int seed) throws SlickException {
        Generator.init(MAP_WIDTH+2, MAP_HEIGHT+2);
        Generator.generate(seed);
        map = Generator.map;
    }

    public static void callPhysics(String ann, Object[] data) throws Exception {
        Start.physx.get(ann).invoke(null, data);
    }

    public static void drawMaterial(int xo, int y, int oldx,char type) throws SlickException {
        if(y <2 || MAP_Y_MAX<y) return;
        if(xo<2) {
            if (oldx<2) return;
            xo=2;
        }
        if(MAP_X_MAX<oldx) {
            if (MAP_X_MAX<xo) return;
            oldx=MAP_X_MAX;
        }
        g.setColor(Properties.color.get((int) type));
        Color c = g.getColor();
        //c.r += temperature[xo][y];
        if(type == Properties.ids.get("lime")) {
            if(xo%2==0) {
                g.drawRect(xo, y, oldx-xo, 1);
            } else {
                g.setColor(Color.darkGray);
                g.drawRect(xo, y, oldx-xo, 1);
            }
            return;
        }
        if (type!=0) {
            //Graphics gi = toRender.getGraphics();
            //if(type == Properties.ids.get("fire")) {
            //    Color c2 = c;
            //    if(rnd.nextBoolean() && rnd.nextBoolean() && rnd.nextBoolean()) {
            //        c2.r = 0.9f;
           //     }
           //     g.setColor(c2);
           // } else {
                g.setColor(c);
          //  }
            g.drawRect(xo, y, oldx-xo, 1);
        }
    }
    public static void render() throws IOException, Exception {
        /*im = new Image(MAP_WIDTH, MAP_HEIGHT);
        im_g = im.getGraphics();*/
        for(int y = MAP_HEIGHT; y!=0; --y) {
            for(int x=MAP_WIDTH;x!=0; --x) {
                draw[x][y] = true;
                if(y <2 || MAP_Y_MAX<y || x<2 || MAP_X_MAX<x) map[x][y] =0;
            }
        }
        //frames++;
        for(int y = MAP_HEIGHT-1; y!=2; --y) {
            int step = -1;
            int kezdo = MAP_X_MAX;
            int vegzo = 0;
            if(rnd.nextBoolean()) {
                step = 1;
                kezdo = 2;
                vegzo = MAP_X_MAX+2;
            }
            int oldx=kezdo;
            char oldtype = map[kezdo][y];
            for(int x=kezdo;x!=vegzo;x=x+step) {
                if(oldtype!=0)if(!Start.isPaused) Physics.handleTemp(x, y);
                if(map[x][y] == oldtype) continue;
                if(step == 1) {
                    if(oldtype!=0)drawMaterial(oldx,y,x-step,oldtype);
                    if(!Start.isPaused)Properties.phys.get((int)oldtype).invoke(null, oldx,y,x-step,oldtype);
//                    callPhysics(Properties.anns.get((int)oldtype), new Object[] {oldx,y,x-step,(char)oldtype});
                    //Physics.bytype(oldx,y,x-step,oldtype);
                } else {
                    if(oldtype!=0)drawMaterial(x-step,y,oldx,oldtype);
                     if(!Start.isPaused)Properties.phys.get((int)oldtype).invoke(null, x-step,y,oldx,oldtype);
//                    callPhysics(Properties.anns.get((int)oldtype), new Object[] {x-step,y,oldx,(char)oldtype});
                    //Physics.bytype(x-step,y,oldx,oldtype);
                }
                oldx=x;
                oldtype=map[oldx][y];
            }
        }
        //toRender.draw(0, 0);
    }
}
