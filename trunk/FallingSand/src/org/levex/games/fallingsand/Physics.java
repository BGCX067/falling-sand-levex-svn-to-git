/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.newdawn.slick.SlickException;
//import org.newdawn.slick.Sound;
//import com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.Random;
import org.lwjgl.Sys;
//import org.newdawn.slick.Color;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
//import static org.levex.games.fallingsand.Canvas.map;

/**
 *
 * @author Levex
 * @description Awesome Physics system :)
 * @since 2010
 * @brain Myself.
 * @lol daddy go away -.-'
 */
public class Physics {
    // draw map energy
    static Random rnd = new Random();
    public static final int MAP_WIDTH = Canvas.MAP_WIDTH;
    public static final int MAP_HEIGHT = Canvas.MAP_HEIGHT;
    public static final int MAP_X_MAX = Canvas.MAP_X_MAX;
    public static final int MAP_Y_MAX = Canvas.MAP_Y_MAX;
    public static Graphics g = null;
//    public static int balszel = 0;
//    public static int jobbszel = 0;
//    public static int fuggs = 0;

    /*@PhysX(name="bytype")
    public static void bytype(int xo, int y, int oldx,char type) throws IOException {
        switch(type) {
            case Sand.ACID:
                acid(xo, y, oldx,type);
                break;
            case Sand.FILTER:
                filter(xo, y, oldx,type);
                break;
            case Sand.SAND:
                sand(xo, y, oldx,type);
                break;
            case Sand.STEAM:
                steam(xo, y, oldx,type);
                break;
            case Sand.IRON:
                iron(xo,y, oldx,type);
                break;
            case Sand.WALL:
                wall(xo, y, oldx,type);
                break;
            case Sand.TORCH:
                torch(xo, y, oldx,type);
                break;
            case Sand.ICE:
                ice(xo, y, oldx, type);
                break;
            case Sand.SEED:
                seed(xo, y, oldx, type);
                break;
            case Sand.VINE:
                vine(xo, y, oldx, type);
                // DO NOT PUT A BREAK; HERE! IT IS INTENTIONAL
            case Sand.WATER_TORCH:
                water_torch(xo, y, oldx,type);
                break;
            case Sand.WATER_TAP_CLOSED:
            case Sand.WATER_TAP_OPENED:
                water_tap(xo,y, oldx,type);
                break;
            /*case Sand.OIL:
                oil(x, y, oldx,type);
                break;
            case Sand.FIRE:
                fire(xo, y, oldx,type);
                break;
            case Sand.PLANT:
                plant(xo, y, oldx,type);
                break;
            case Sand.SALT:
                salt(xo, y, oldx,type);
                break;
            case Sand.MAGMA:
                magma(xo, y, oldx,type);
                break;
            case Sand.GAS:
                gas(xo, y, oldx, type);
                break;
            case Sand.OIL:
                oil(xo, y, oldx,type);
                break;
            case Sand.WATER:
                water_NEW(xo, y, oldx,type);
                break;
            case Sand.SALT_WATER:
                saltWater(xo, y, oldx,type);
                break;
            case Sand.OIL_TORCH:
                oilTorch(xo, y, oldx,type);
                break;
            case Sand.SAND_TORCH:
                sandTorch(xo, y, oldx,type);
                break;
            case Sand.SMOKE:
                smoke(xo, y, oldx,type);
                break;
            case Sand.LIME:
                lime(xo, y, oldx, type);
                break;
            case Sand.TNT:
                tnt(xo, y, oldx,type);
                break;
            case Sand.CIGARETTE:
                cigarette(xo, y, oldx,type);
                break;
            case Sand.ACID_FILTER:
                acid_filter(xo, y, oldx,type);
                break;
            case Sand.ANTI_ACID:
                anti_acid(xo, y, oldx,type);
                break;
            case Sand.SALT_FILTER:
                salt_filter(xo, y, oldx,type);
                break;
            case Sand.ERASER:
                eraser(xo,y,oldx,type);
                break;
            default:
                System.err.println("No physics for "+Sand.toString(Canvas.map[xo][y])+" ("+(int)Canvas.map[xo][y]+")");
                break;
        }
    }*/
    @PhysX(name="ice")
    public static void ice(int xo, int y, int oldx,char type){
        for(int x=xo;x!=oldx+1;x++) {
            if(Canvas.temperature[x][y] >= 1f) { Canvas.map[x][y] = Properties.ids.get("water"); }
        }
    }
    @PhysX(name="custom")
    public static void custom(int xo, int y, int oldx,char type){
        for(int x=xo;x!=oldx+1;x++) {
            if(!Canvas.draw[x][y]) continue;
            Interaction ia = Properties.interaction.get((int)type);
            char i1 = Properties.ids.get(ia.with);
            char i2 = Properties.ids.get(ia.into);
            float g = ia.g; // Gravity
            byte g2 = new Float(g).byteValue();
            float s = ia.s; // Slip
            float p = ia.prob; // Probability of transform
            float r = 1; // A random number ^^
            boolean slipped = false; // Bool for isDoneWithSlip

            /* Slip check */
            if(s == 0f) {
                // Do nothing, it falls solidly.
            } else {
                float r2 = rnd.nextFloat();
                if(r2 <= s) {
                    if(rnd.nextBoolean()) {
                        if(Canvas.map[x+1][y+g2] == 0) {
                            Canvas.map[x+1][y+g2] = type;
                            Canvas.map[x][y] = 0;
                            Canvas.draw[x+1][y+g2] = false;
                            slipped = true;
                        }
                    } else {
                        if(Canvas.map[x-1][y+g2] == 0) {
                            Canvas.map[x-1][y+g2] = type;
                            Canvas.map[x][y] = 0;
                            Canvas.draw[x-1][y+g2] = false;
                            slipped = true;
                        }
                    }

                }
            }

            /* Gravity check */
            if(!slipped) {
                if(g == 0f) {
                    // Do nothing, it's solid.
                } else if(g > 0) {
                    // Let's fall down.
                    if(Canvas.map[x][y+1] == 0) {
                        Canvas.map[x][y+1] = type;
                        Canvas.map[x][y] = 0;
                        Canvas.draw[x][y+1] = false;
                    }
                } else {
                    // g is negative, so lets fall 'up'
                    if(Canvas.map[x][y-1] == 0) {
                        Canvas.map[x][y-1] = type;
                        Canvas.map[x][y] = 0;
                        Canvas.draw[x][y-1] = false;
                    }
                }
            }

            if(ia.prob > 0) {
                r = rnd.nextFloat();
                if(r == 1f) {
                    r = 0.9f;
                }
            }
            if(Canvas.map[x+1][y] == i1 && p >= r ) {
                Canvas.map[x][y] = i2;
            }
            if(Canvas.map[x-1][y] == i1 && p >= r) {
                Canvas.map[x][y] = i2;
            }
            if(Canvas.map[x][y+1] == i1 && p >= r) {
                Canvas.map[x][y] = i2;
            }
            if(Canvas.map[x][y-1] == i1 && p >= r) {
                Canvas.map[x][y] = i2;
            }
        }
    }
    @PhysX(name="eraser")
    public static void eraser(int xo, int y, int oldx,char type){
    }

    public static void handleTemp(int x, int y) {
        /*float r = rnd.nextFloat();
        if(r > 0.8)*/
        float t = Canvas.temperature[x][y];
            if(Canvas.temperature[x+1][y] < t) { Canvas.temperature[x+1][y]+=1f; Canvas.temperature[x][y]-=1f;}
        //if(r > 0.6)
            if(Canvas.temperature[x-1][y] < t) { Canvas.temperature[x-1][y]+=1f; Canvas.temperature[x][y]-=1f;}
        //if(r > 0.4)
            if(Canvas.temperature[x][y+1] < t) { Canvas.temperature[x][y+1]+=1f; Canvas.temperature[x][y]-=1f;}
       // if(r > 0.2)
            if(Canvas.temperature[x][y-1] < t) { Canvas.temperature[x][y-1]+=1f; Canvas.temperature[x][y]-=1f;}
    }

    @PhysX(name="carbon")
    public static void carbon(int xo, int y, int oldx,char type){
            for(int x=xo;x!=oldx+1; x++) {
//                if(Canvas.temperature[x][y]!=(char)0) Canvas.temperature[x][y]=(char) (Canvas.temperature[x][y] - 1);
                if(rnd.nextFloat() < 0.9) {
                    if(Canvas.getPressure(x, y) > 100) {
                        Canvas.map[x][y] = Properties.ids.get("oil");
                    }
                }
                if(rnd.nextFloat() > 0.9 && rnd.nextBoolean() && rnd.nextBoolean() && Canvas.getPressure(x, y) > 100) { Canvas.map[x][y] = Properties.ids.get("diamond"); }
            }
    }

    @PhysX(name="napalm")
    public static void napalm(int xo, int y, int oldx,char type) {
//*        //pergetés
        if (xo==oldx) {
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[xo+1][y+1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } // EDDIG JO
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }

//   */
/*
        int step = -1;
        int kezdo = MAP_WIDTH+step;
        int vegzo = 2;
        if(rnd.nextBoolean()) {
            step = 1;
            kezdo = 2;
            vegzo = MAP_WIDTH-step;
        }*/

        //NEM perget
        for(int x = xo; x != oldx+1; x++) {
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            if(Canvas.draw[x][y]==false) continue;
            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }
            if(rnd.nextBoolean()) {
                if(Properties.density.get((int)Canvas.map[x][y-1]) > Properties.density.get((int)Properties.ids.get("water"))) {
                    //char type1 = Canvas.map[x][y];
                    char type2 = Canvas.map[x][y-1];
                    Canvas.map[x][y] = type2;
                    Canvas.map[x][y-1] = type;
                    Canvas.draw[x][y-1] = false;
                    continue;
                }
            }
            if(Canvas.map[x+1][y] == Properties.ids.get("fire")) {
                Penshape.Disc(x, y, 256, Properties.ids.get("fire"));
                continue;
            }
            if(Canvas.map[x-1][y] == Properties.ids.get("fire")) {
                Penshape.Disc(x, y, 256, Properties.ids.get("fire"));
                continue;
            }
            if(Canvas.map[x][y+1] == Properties.ids.get("fire")) {
                Penshape.Disc(x, y, 256, Properties.ids.get("fire"));
                continue;
            }
            if(Canvas.map[x][y-1] == Properties.ids.get("fire")) {
                Penshape.Disc(x, y, 256, Properties.ids.get("fire"));
                continue;
            }
            if(rnd.nextBoolean()) { //balra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //jobbra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }
            /** SZÉTFOLYIK
             *
             * @what it  go
             * @does make horizontal
             * @do   water hehe
             *
             */
            if(2*x<xo+oldx) {
                if(Canvas.map[x-1][y] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y] = type;
                    continue;
                }
             } else {
                if( Canvas.map[x+1][y] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y] = type;
                    continue;
                }
            }
            /** END **/

        }
        }

    @PhysX(name="bugs")
    public static void bugs(int xo, int y, int oldx,char type){
        //Wind.setWind(Wind.DIRECTION_LEFT,(char) 100, xo, y);
            for(int x=xo;x!=oldx+1; x++) {
//                if(Canvas.temperature[x][y]!=(char)0) Canvas.temperature[x][y]=(char) (Canvas.temperature[x][y] - 1);
                 if(Canvas.draw[x][y] == false) continue;
                float r = rnd.nextFloat();
                if(rnd.nextBoolean()) {
                    if(r > 0.8) {
                        if(Canvas.map[x+1][y] == Properties.ids.get("plant") && Canvas.draw[x+1][y]) {
                            Canvas.map[x+1][y] = type;
                            Canvas.draw[x+1][y] = false;
                            continue;
                        }
                        continue;
                    }
                    if(r > 0.6) {
                        if(Canvas.map[x-1][y] == Properties.ids.get("plant") && Canvas.draw[x-1][y]) {
                            Canvas.map[x-1][y] = type;
                            Canvas.draw[x-1][y] = false;
                            continue;
                        }
                    }
                    if(r > 0.4) {
                        if(Canvas.map[x][y+1] == Properties.ids.get("plant") && Canvas.draw[x][y+1]) {
                            Canvas.map[x][y+1] = type;
                            Canvas.draw[x][y+1] = false;
                            continue;
                        }
                    }
                    if(r > 0.2) {
                        if(Canvas.map[x][y-1] == Properties.ids.get("plant") && Canvas.draw[x][y-1]) {
                            Canvas.map[x][y-1] = type;
                            Canvas.draw[x][y-1] = false;
                            continue;
                        }
                    }
                    if(Canvas.map[x-1][y] != Properties.ids.get("plant") && Canvas.map[x+1][y] != Properties.ids.get("plant") && Canvas.map[x][y-1] != Properties.ids.get("plant") && Canvas.map[x][y+1] != Properties.ids.get("plant") && rnd.nextBoolean() && rnd.nextBoolean())
                    Canvas.map[x][y] = 0;
                }
            }
    }

    @PhysX(name="wind")
    public static void wind(int xo, int y, int oldx,char type){
        Wind.setWind(Wind.DIRECTION_LEFT,(char) 100, xo, y);
            for(int x=xo;x!=oldx+1; x++) {
//                if(Canvas.temperature[x][y]!=(char)0) Canvas.temperature[x][y]=(char) (Canvas.temperature[x][y] - 1);
            }
    }

    @PhysX(name="sand")
    public static void sand(int xo, int y, int oldx,char type) {
//*        //pereg a széle
        int wind = Wind.NO_WIND; 
         if (xo==oldx) {
             if(rnd.nextBoolean() ) {
                 char[] w = Wind.getHorizontalWind(y);
                 boolean f = true;
                 if(w != null) {
                     if(w[0] > xo && w[1] == Wind.DIRECTION_RIGHT && f) { // jobbra van és iránya ==jobbra
                         wind = Wind.NO_WIND;
                         f = false;
                     }
                     if(w[0] > xo && w[1] == Wind.DIRECTION_LEFT && f) {
                         wind = Wind.FROM_RIGHT;
                         //System.err.println("FROM RIGHT xo:"+xo+" w0:"+(int)w[0]+" w1:"+(int)w[1]);
                         f = false;
                     }
                     if(w[0] < xo && w[1] == Wind.DIRECTION_LEFT && f) {
                         wind = Wind.NO_WIND;
                         f = false;
                     }
                     if(w[0] < xo && w[1] == Wind.DIRECTION_RIGHT && f) {
                         wind = Wind.FROM_LEFT;
                         //System.err.println("FROM LEFT xo:"+xo+" w0:"+(int)w[0]+" w1:"+(int)w[1]);
                         f = false;
                     }
                }
             }
             if(wind != Wind.NO_WIND && xo+10 <= MAP_WIDTH && xo-10 >= 0) {
                 int move = 0;
                switch(wind) {
                    case Wind.FROM_LEFT:
                        move = Canvas.checkRoute(xo, xo+10, y);
                        //System.err.println("move: "+move);
                            Canvas.map[xo+move][y] = type;
                            Canvas.draw[xo+move][y] = false;
                            if(move!=0)
                            Canvas.map[xo][y] = 0;
                        return;
                    case Wind.FROM_RIGHT:
                        move = Canvas.checkRoute(xo, xo-10, y);
                        //System.err.println("move: "+move);
                            Canvas.map[xo-move][y] = type;
                            Canvas.draw[xo-move][y] = false;
                            if(move!=0)
                            Canvas.map[xo][y] = 0;
                        return;
                }
            }
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } // EDDIG JO
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }
//   */
        //közepe esik ha tud
        for(int x = xo; x != oldx+1; x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }
            if(rnd.nextBoolean()) { //jobbra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //balra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }
        }
    }
    // @todo fix fire
    @PhysX(name="steam")
    public static void steam(int xo, int y, int oldx,char type) throws IOException {
        if (oldx<xo) Sys.alert("STEAM", "xo:"+xo+" oldx: "+oldx);
        //Canvas.writeLog("FIRE: xo"+xo+" y"+y+" oldx"+oldx);
        boolean f;
        if(y-1 == 2) {
            for(int x = xo; x != oldx+1; x++) Canvas.map[x][y] = 0;
            Canvas.sand --;
            return;
        }

        if(xo-2 == 0) {
            Canvas.map[xo][y] = 0;
            return;
        }
        if(xo+2 == MAP_WIDTH) {
            Canvas.map[xo][y] = 0;
            return;
        }

//*        //pergetés
        if (xo==oldx) {
            //if (Canvas.energy[xo][y]>0) {
                if(rnd.nextBoolean()) { //jobbra
                    if(Canvas.map[xo+1][y-1] == 0 && Canvas.draw[xo][y]) {
                        //új hely beállítása
                        Canvas.map[xo+1][y-1] = type;
                        Canvas.draw[xo+1][y-1] = false;
                        //Canvas.energy[xo+1][y-1]=(char)(Canvas.energy[xo][y]-1);
                        //régi hely beállítása
                        Canvas.map[xo][y] = 0;
                        //Canvas.energy[xo][y]=0;
                        //Canvas.temperature[xo][y]=0;
                    }
                } else { //balra
                    if (Canvas.map[xo-1][y-1] == 0 && Canvas.draw[xo][y]) {
                        //új hely beállítása
                        Canvas.map[xo-1][y-1] = type;
                        Canvas.draw[xo-1][y-1] = false;
                       // Canvas.energy[xo-1][y-1]=(char)(Canvas.energy[xo][y]-1);
                        //régi hely beállítása
                        Canvas.map[xo][y] = 0;
                       // Canvas.energy[xo][y]=0;
                       // Canvas.temperature[xo][y]=0;
                    }
                }
           // }
        } else {
           // if (Canvas.energy[xo][y]>0) {
                if(rnd.nextBoolean()) {//balra
                    if (Canvas.map[xo - 1][y-1] == 0 && Canvas.draw[xo][y]) {
                        //új hely beállítása
                        Canvas.map[xo-1][y-1] = type;
                        Canvas.draw[xo-1][y-1] = false;
                       // Canvas.energy[xo-1][y-1]=(char)(Canvas.energy[xo][y]-1);
                        //régi hely beállítása
                        Canvas.map[xo][y] = 0;
                       // Canvas.energy[xo][y]=0;
                       // Canvas.temperature[xo][y]=0;
                    }
                }
           // }
           // if (Canvas.energy[oldx][y]>0) {
                if(rnd.nextBoolean()) {//jobbra
                    if(Canvas.map[oldx+1][y-1] == 0 && Canvas.draw[xo][y]) {
                        //új hely beállítása
                        Canvas.map[oldx+1][y-1] = type;
                        Canvas.draw[oldx+1][y-1] = false;
                        //Canvas.energy[oldx+1][y-1]=(char)(Canvas.energy[oldx][y]-1);
                        //régi hely beállítása
                        Canvas.map[oldx][y] = 0;
                        //Canvas.energy[oldx][y]=0;
                      //  Canvas.temperature[oldx][y]=0;
                    }
                }
           // }
        }
//   */

        for(int x =xo; x!=oldx+1;x++) {
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
           // if (Canvas.energy[x][y]>0) {
                //if(Canvas.)
                if((Canvas.map[x][y-1] == Properties.ids.get("water") || Canvas.map[x][y-1] == Properties.ids.get("salt water") ) && Canvas.draw[x][y]) {
                    char t = Canvas.map[x][y-1];
                    Canvas.map[x][y-1] = Properties.ids.get("steam");
                    Canvas.map[x][y] = t;
                    Canvas.draw[x][y-1] = false;
                }
                if (Canvas.map[x][y-1]==0 && Canvas.draw[x][y]) {//mozog
                    //új hely beállítása
                    Canvas.map[x][y-1] = type;
                    Canvas.draw[x][y-1] = false;
                   // Canvas.energy[x][y-1]=(char)(Canvas.energy[x][y]-1);
                    //régi hely beállítása
                    Canvas.map[x][y] = 0;
                   // Canvas.energy[x][y]=0;
                   // Canvas.temperature[x][y]=0;
                } else {//áll
                    //régi hely beállítása
                   // Canvas.energy[x][y]--;
//                    if (Canvas.temperature[x][y]>0) Canvas.temperature[x][y]--;
                }
            /*} else {
                //régi hely beállítása
                Canvas.map[x][y] = 0;
                if (Canvas.temperature[x][y]>0) Canvas.temperature[x][y]--;
            }*/
        }
    }
    @PhysX(name="concrete")
    public static void wall(int xo, int y, int oldx,char type){
    }

    @PhysX(name="cloner")
    public static void cloner(int xo, int y, int oldx,char type){
        for(int x=xo;x!=oldx+1;x++) {
            if(Canvas.map[x][y+1] == 0) {
                Canvas.map[x][y+1] = type;
                Canvas.map[x][y] =0;
                Canvas.draw[x][y+1] = false;
                continue;
            }
            Canvas.map[x][y] = Canvas.map[x][y+1];
        }
    }

    @PhysX(name="lime")
    public static void lime(int xo, int y, int oldx,char type){
        for(int x=xo;x!=oldx+1;x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
        }
    }
    @PhysX(name="iron")
    public static void iron(int xo, int y, int oldx,char type){
        for(int x=xo;x!=oldx+1;x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            //if(Canvas.temperature[x][y+1]!=0) Canvas.temperature[x][y-1]+=2;
        }
        //byte t = Canvas.temperature[xo][y];
    }
    @PhysX(name="torch")
    public static void torch(int xo, int y, int oldx,char type) {
        if(xo == oldx) {
            if(rnd.nextFloat() > 0.7 && Canvas.map[xo][y-2] == 0) {
                Canvas.map[xo][y-2] = Properties.ids.get("fire");
                Canvas.draw[xo][y-2] = false;
                Canvas.temperature[xo][y-2] = 100;
            }
        } else {
            for(int x = xo; x!=oldx+1;x++) {
//                if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
                if(rnd.nextFloat() > 0.7 && Canvas.map[x][y-2] == 0) {
                    Canvas.map[x][y-2] = Properties.ids.get("fire");
                    Canvas.energy[x][y-2] = 60;
                    Canvas.temperature[x][y-2] = 100;
                }
            }
        }
    }
    @PhysX(name="match")
    public static void cigarette(int xo, int y, int oldx,char type) {
        if(xo == oldx) {
            if(Canvas.map[xo][y+1] == 0) {
                    Canvas.map[xo][y+1] = Properties.ids.get("match");
                    Canvas.map[xo][y] = 0;
                    Canvas.draw[xo][y+1] = false;
                    return;
            }
            if(rnd.nextFloat() > 0.7 && Canvas.map[xo][y-2] == 0) {
                Canvas.map[xo][y-2] = Properties.ids.get("fire");
                Canvas.draw[xo][y-2] = false;
//                Canvas.temperature[xo][y-2] = 60;
            }
        } else {
            for(int x = xo; x!=oldx+1;x++) {
//                if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
                if(Canvas.map[x][y+1] == 0) {
                    Canvas.map[x][y+1] = Properties.ids.get("match");
                    Canvas.map[x][y] = 0;
                    Canvas.draw[x][y+1] = false;
                    continue;
                }
                if(Properties.isBurnable.get((int)Canvas.map[x][y+1]) > 0) {
                    if(rnd.nextFloat() > 0.7) {
                        Canvas.map[x][y+1] = Properties.ids.get("fire");
                        Canvas.energy[x][y+1] = 60;
//                        Canvas.temperature[x][y-2] = 100;
                    }
                }
                if(rnd.nextFloat() > 0.7 && Canvas.map[x][y-2] == 0) {
                    Canvas.map[x][y-2] = Properties.ids.get("fire");
                    Canvas.energy[x][y-2] = 60;
//                    Canvas.temperature[x][y-2] = 100;
                }
            }
        }
    }
    @PhysX(name="water_tap")
    public static void water_torch(int xo, int y, int oldx,char type) {
        if(xo == oldx) {
            if(rnd.nextFloat() > 0.7 && Canvas.map[xo][y+2] == 0) {
                Canvas.map[xo][y+2] = Properties.ids.get("water");
                Canvas.draw[xo][y+2] = false;
                Canvas.energy[xo][y+2] = 30;
            }
        } else {
            for(int x = xo; x!=oldx+1;x++) {
//                if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
                if(rnd.nextFloat() > 0.7 && Canvas.map[x][y+2] == 0) {
                    Canvas.map[x][y+2] = Properties.ids.get("water");
                    Canvas.energy[x][y+2] = 30;
                }
            }
        }
    }
    @PhysX(name="gas")
    public static void gas(int xo, int y, int oldx,char type) {
            for(int x = xo; x!=oldx+1;x++) {
                boolean boom = false;
//                if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
                float r = rnd.nextFloat();
                if(Canvas.draw[x][y]) {
                    if(Canvas.map[x][y+1] == Properties.ids.get("fire")) {
                        boom = true;
                    }
                    if(r > 0.8 && !boom) {
                        if(Canvas.map[x+1][y] == 0) {
                            Canvas.map[x][y] = 0;
                            Canvas.map[x+1][y] = Properties.ids.get("gas");
                            Canvas.draw[x+1][y] = false;
                        }
                        continue;
                    }
                    if(r > 0.6 && !boom) {
                        if(Canvas.map[x-1][y] == 0) {
                            Canvas.map[x][y] = 0;
                            Canvas.map[x-1][y] = Properties.ids.get("gas");
                            Canvas.draw[x-1][y] = false;
                        }
                        continue;
                    }
                    if(r > 0.4 && !boom) {
                        if(Canvas.map[x][y+1] == 0) {
                            Canvas.map[x][y] = 0;
                            Canvas.map[x][y+1] = Properties.ids.get("gas");
                            Canvas.draw[x][y+1] = false;
                        }
                        continue;
                    }
                    if(r > 0.2 && !boom) {
                        if(Canvas.map[x][y-1] == 0) {
                            Canvas.map[x][y] = 0;
                            Canvas.map[x][y-1] = Properties.ids.get("gas");
                            Canvas.draw[x][y+1] = false;
                        }
                        continue;
                    }
                    if(boom) {
                        Penshape.Disc(x, y, 128, Properties.ids.get("fire"));
                    }
                }
            }
    }
    @PhysX(name="disapeering")
    public static void disapeering(int xo, int y, int oldx,char type) {
            for(int x = xo; x!=oldx+1;x++) {
//                if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
                float r = rnd.nextFloat();
                if(r > 0.8) {
                    if(Canvas.map[x+1][y] == 0) {
                        Canvas.map[x][y] = 0;
                        Canvas.map[x+1][y] = 0;
                    }
                    continue;
                }
                if(r > 0.6) {
                    if(Canvas.map[x-1][y] == 0) {
                        Canvas.map[x][y] = 0;
                        Canvas.map[x-1][y] = 0;
                    }
                    continue;
                }
                if(r > 0.4) {
                    if(Canvas.map[x][y+1] == 0) {
                        Canvas.map[x][y] = 0;
                        Canvas.map[x][y+1] = 0;
                    }
                    continue;
                }
                if(r > 0.2) {
                    if(Canvas.map[x][y-1] == 0) {
                        Canvas.map[x][y] = 0;
                        Canvas.map[x][y-1] = 0;
                    }
                    continue;
                }
            }
    }

    /*// @todo fix water tap
    @PhysX(name="water_tap2")
    public static void water_tap(int x, int y, int oldx,char type) {
        if(type ==  && Start.TapWaterFlow) {
            if(Canvas.map[x][y-1] == Properties.ids.get("water")) {
                Canvas.map[x][y-1] = 0;
                Canvas.map[x][y+1] = Properties.ids.get("water");
            }
            /*
            g.setColor(Sand.WATER_TAP_OPENED_COLOR);
            g.drawRect(x, y, 1, 1);
             
        } else {
            /*
            g.setColor(Sand.WATER_TAP_CLOSED_COLOR);
            g.drawRect(x, y, 1, 1);
             
        }
    }*/
    @PhysX(name="oil")
    public static void oil(int xo, int y, int oldx,char type) {
//*        //pergetés
        if (xo==oldx) {
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[xo+1][y+1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } 
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }

        //NEM perget
        for(int x = xo; x != oldx+1; x++) {
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }
/*            if(Canvas.temperature[x][y] >= 300) {
                Canvas.map[x][y] = Properties.ids.get("fire");
                //Canvas.temperature[x][y] = 0;
                Canvas.energy[x][y] = 60;
                continue;
            }*/
            if(rnd.nextBoolean()) { //balra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //jobbra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }
            /** SZÉTFOLYIK
             *
             * @what it  go
             * @does make horizontal
             * @do   water hehe
             *
             */
            if(2*x<xo+oldx) {
                if(Canvas.map[x-1][y] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y] = type;
                    continue;
                }
             } else {
                if( Canvas.map[x+1][y] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y] = type;
                    continue;
                }
            }
            /** END **/

        }
        }
    // @todo fix fire
    @PhysX(name="fire")
    public static void fire(int xo, int y, int oldx,char type) throws IOException {
        boolean f;
        if(y-1 == 2) {
            for(int x = xo; x != oldx+1; x++) Canvas.map[x][y] = 0;
            Canvas.sand --;
            return;
        }
        if(xo-2 == 0) {
            Canvas.map[xo][y] = 0;
            return;
        }
        if(xo+2 == MAP_WIDTH) {
            Canvas.map[xo][y] = 0;
            return;
        }

//*        //pergetés
        if (xo==oldx) {
            if (Canvas.energy[xo][y]>0) {
                if(rnd.nextBoolean()) { //jobbra
                    if(Canvas.map[xo+1][y-1] == 0 && Canvas.draw[xo][y]) {
                        //új hely beállítása
                        Canvas.map[xo+1][y-1] = type;
                        Canvas.draw[xo+1][y-1] = false;
                        Canvas.energy[xo+1][y-1]=(char)(Canvas.energy[xo][y]-1);
/*                        if (Canvas.temperature[xo][y]>0) {
                            Canvas.temperature[xo+1][y-1]=(char)(Canvas.temperature[xo][y]-1);
                        } else {
                            Canvas.temperature[xo+1][y-1]=0;
                        }
 */
                        //régi hely beállítása
                        Canvas.map[xo][y] = 0;
                        Canvas.energy[xo][y]=0;
                        //Canvas.temperature[xo][y]=0;
                    }
                } else { //balra
                    if (Canvas.map[xo-1][y-1] == 0 && Canvas.draw[xo][y]) {
                        //új hely beállítása
                        Canvas.map[xo-1][y-1] = type;
                        Canvas.draw[xo-1][y-1] = false;
                        Canvas.energy[xo-1][y-1]=(char)(Canvas.energy[xo][y]-1);
/*                        if (Canvas.temperature[xo][y]>0) {
                            Canvas.temperature[xo-1][y-1]=(char)(Canvas.temperature[xo][y]-1);
                        } else {
                            Canvas.temperature[xo-1][y-1]=0;
                        }
 */
                        //régi hely beállítása
                        Canvas.map[xo][y] = 0;
                        Canvas.energy[xo][y]=0;
                       // Canvas.temperature[xo][y]=0;
                    }
                }
            }
        } else {
            if (Canvas.energy[xo][y]>0) {
                if(rnd.nextBoolean()) {//balra
                    if (Canvas.map[xo - 1][y-1] == 0 && Canvas.draw[xo][y]) {
                        //új hely beállítása
                        Canvas.map[xo-1][y-1] = type;
                        Canvas.draw[xo-1][y-1] = false;
                        Canvas.energy[xo-1][y-1]=(char)(Canvas.energy[xo][y]-1);
/*                        if (Canvas.temperature[xo][y]>0) {
                            Canvas.temperature[xo-1][y-1]=(char)(Canvas.temperature[xo][y]-1);
                        } else {
                            Canvas.temperature[xo-1][y-1]=0;
                        }
 */
                        //régi hely beállítása
                        Canvas.map[xo][y] = 0;
                        Canvas.energy[xo][y]=0;
                       // Canvas.temperature[xo][y]=0;
                    }
                }
            }
            if (Canvas.energy[oldx][y]>0) {
                if(rnd.nextBoolean()) {//jobbra
                    if(Canvas.map[oldx+1][y-1] == 0 && Canvas.draw[oldx][y]) {
                        //új hely beállítása
                        Canvas.map[oldx+1][y-1] = type;
                        Canvas.draw[oldx+1][y-1] = false;
                        Canvas.energy[oldx+1][y-1]=(char)(Canvas.energy[oldx][y]-1);
/*                        if (Canvas.temperature[oldx][y]>0) {
                            Canvas.temperature[oldx+1][y-1]=(char)(Canvas.temperature[oldx][y]-1);
                        } else {
                            Canvas.temperature[oldx+1][y-1]=0;
                        }
 */
                        //régi hely beállítása
                        Canvas.map[oldx][y] = 0;
                        Canvas.energy[oldx][y]=0;
                      //  Canvas.temperature[oldx][y]=0;
                    }
                }
            }
        }
//   */

        for(int x =xo; x!=oldx+1;x++) {
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            if (Canvas.energy[x][y]>0) {
                if(Canvas.draw[x][y]) {
                        

                    //if(rnd.nextBoolean()) {
                        float r = rnd.nextFloat();
                        float r2 = rnd.nextFloat(); // For calculating chance of catching fire. :)
                        boolean o = true;
                        if( r>0.8 && o) {
                            if(Properties.isBurnable.get((int)Canvas.map[x+1][y]) >= r2) {
                                Canvas.map[x+1][y] = Properties.ids.get("fire");
                                Canvas.energy[x+1][y] = 100;
                                Canvas.draw[x+1][y] = false;
                                continue;
                            }
                            o = false;
                        }
                        if( r>0.6 && o) {
                            if(Properties.isBurnable.get((int)Canvas.map[x-1][y]) >= r2) {
                                Canvas.map[x-1][y] = Properties.ids.get("fire");
                                Canvas.energy[x-1][y] = 100;
                                Canvas.draw[x-1][y] = false;
                                continue;
                            }
                            o = false;
                        }
                        if( r>0.4 && o) {
                            if(Properties.isBurnable.get((int)Canvas.map[x][y+1]) >= r2) {
                                Canvas.map[x][y+1] = Properties.ids.get("fire");
                                Canvas.energy[x][y+1] = 100;
                                Canvas.draw[x][y+1] = false;
                                continue;
                            }
                            o = false;
                        }
                        if( r>0.2 && o) {
                            if(Properties.isBurnable.get((int)Canvas.map[x][y-1]) >= r2) {
                                Canvas.map[x][y-1] = Properties.ids.get("fire");
                                Canvas.energy[x][y-1] = 100;
                                Canvas.draw[x][y-1] = false;
                                continue;
                            }
                            o = false;
                        }
                   // }
                }
                if (Canvas.map[x][y-1]==0 && Canvas.draw[x][y]) {//mozog
                    //új hely beállítása
                    Canvas.map[x][y-1] = type;
                    Canvas.draw[x][y-1] = false;
                    Canvas.energy[x][y-1]=(char)(Canvas.energy[x][y]-1);
/*                    if (Canvas.temperature[x][y]>0) {
                        Canvas.temperature[x][y-1]=(char)(Canvas.temperature[x][y]-1);
                    } else {
                        Canvas.temperature[x][y-1]=0;
                    }
 */
                    //régi hely beállítása
                    Canvas.map[x][y] = 0;
                    Canvas.energy[x][y]=0;
                   // Canvas.temperature[x][y]=0;
                } else {//áll
                    //régi hely beállítása
                    Canvas.energy[x][y]--;
//                    if (Canvas.temperature[x][y]>0) Canvas.temperature[x][y]--;
                }
            } else {
                //régi hely beállítása
                Canvas.map[x][y] = 0;
 //               if (Canvas.temperature[x][y]>0) Canvas.temperature[x][y]--;
            }
        }
    }
    @PhysX(name="acid")
    public static void acid(int xo, int y, int oldx,char type) {
//*        //pergetés
        if (xo==oldx) {
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[xo+1][y+1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } // EDDIG JO
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }
//   */
        //NEM perget
        for(int x = xo; x != oldx+1; x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }
            if(rnd.nextBoolean()) {
                float r = rnd.nextFloat();
                if(r > 0.8) {
                    if(Properties.isAffectedByAcid.get((int) Canvas.map[x+1][y])) {
                        Canvas.map[x+1][y] = 0;
                        continue;
                    }
                    continue;
                }
                if(r > 0.6) {
                    if(Properties.isAffectedByAcid.get((int) Canvas.map[x-1][y])) {
                        Canvas.map[x-1][y] = 0;
                        continue;
                    }
                    continue;
                }
               if(r > 0.4) {
                    if(Properties.isAffectedByAcid.get((int) Canvas.map[x][y+1])) {
                        Canvas.map[x][y+1] = 0;
                        continue;
                    }
                    continue;
                }
                if(r > 0.2) {
                    if(Properties.isAffectedByAcid.get((int) Canvas.map[x][y-1])) {
                        Canvas.map[x][y-1] = 0;
                        continue;
                    }
                    continue;
                }
            }
            if(rnd.nextBoolean()) { //jobbra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //balra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }
        }
    }
    @PhysX(name="plant")
    public static void plant(int xo, int y, int oldx,char type) {
        for(int x=xo; x!=oldx+1;x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if(Canvas.draw[x][y] == false) continue;
            float r = rnd.nextFloat();
            if(rnd.nextBoolean()) {
                if(r > 0.8) {
                    if(Canvas.map[x+1][y] == Properties.ids.get("water") && Canvas.draw[x+1][y]) {
                        Canvas.map[x+1][y] = Properties.ids.get("plant");
                        Canvas.draw[x+1][y] = false;
                        continue;
                    }
                    continue;
                }
                if(r > 0.6) {
                    if(Canvas.map[x-1][y] == Properties.ids.get("water") && Canvas.draw[x-1][y]) {
                        Canvas.map[x-1][y] = Properties.ids.get("plant");
                        Canvas.draw[x-1][y] = false;
                        continue;
                    }
                }
                if(r > 0.4) {
                    if(Canvas.map[x][y+1] == Properties.ids.get("water") && Canvas.draw[x][y+1]) {
                        Canvas.map[x][y+1] = Properties.ids.get("plant");
                        Canvas.draw[x][y+1] = false;
                        continue;
                    }
                }
                if(r > 0.2) {
                    if(Canvas.map[x][y-1] == Properties.ids.get("water") && Canvas.draw[x][y-1]) {
                        Canvas.map[x][y-1] = Properties.ids.get("plant");
                        Canvas.draw[x][y-1] = false;
                        continue;
                    }
                }
            }

        }
    }

    @PhysX(name="vine")
    public static void vine(int xo, int y, int oldx,char type) {
        for(int x=xo; x!=oldx+1;x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if(Canvas.draw[x][y] == false) continue;
            if(Canvas.map[x][y+1] == 0) {
                Canvas.map[x][y+1] = Properties.ids.get("vine");;
                Canvas.draw[x][y+1] = false;
                continue;
            }
            if(Canvas.map[x+1][y] == Properties.ids.get("water")) {
                Canvas.map[x+1][y] = Properties.ids.get("plant");
                Canvas.draw[x+1][y] = false;
                return;
            }
            if(Canvas.map[x-1][y] == Properties.ids.get("water")) {
                Canvas.map[x-1][y] = Properties.ids.get("plant");
                Canvas.draw[x-1][y] = false;
                return;
            }
            if(Canvas.map[x][y-1] == Properties.ids.get("water")) {
                Canvas.map[x][y-1] = Properties.ids.get("plant");
                Canvas.draw[x][y-1] = false;
                return;
            }


        }
    }
    @PhysX(name="seed")
    public static void seed(int xo, int y, int oldx,char type) {
        for(int x=xo; x!=oldx+1;x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if(Canvas.draw[x][y] == false) continue;
            if(Canvas.map[x][y+1] == 0) {
                Canvas.map[x][y+1] = Properties.ids.get("seed");
                Canvas.map[x][y] = 0;
                Canvas.draw[x][y+1] = false;
                continue;
            }
        }
    }


    @PhysX(name="salt")
    public static void salt(int xo, int y, int oldx,char type) {
//*        //pergetés
        if (xo==oldx) {
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[xo+1][y+1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } // EDDIG JO
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }
//   */
        //NEM perget
        for(int x = xo; x != oldx+1; x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }

            if(Canvas.map[x+1][y] == Properties.ids.get("water")) {
                Canvas.map[x][y] = Properties.ids.get("salt water");
                //Canvas.map[x][y] = 0;
            }
            if(Canvas.map[x-1][y] == Properties.ids.get("water")) {
                Canvas.map[x][y] = Properties.ids.get("salt water");
               // Canvas.map[x][y] = 0;
            }
            if(Canvas.map[x][y+1] == Properties.ids.get("water")) {
                Canvas.map[x][y] = Properties.ids.get("salt water");
                //Canvas.map[x][y] = 0;
            }
            if(Canvas.map[x][y-1] == Properties.ids.get("water")) {
                Canvas.map[x][y] = Properties.ids.get("salt water");
                //Canvas.map[x][y] = 0;
            }

            if(rnd.nextBoolean()) { //jobbra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //balra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }

        if(rnd.nextBoolean()) { //pergetés
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
 //                   Canvas.draw[x+1][y+1] = false;
                    return;
                } else if (Canvas.map[x - 1][y + 1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
 //                   Canvas.draw[x-1][y+1] = false;
                    return;
                }
            } else { //balra
                if (Canvas.map[x - 1][y + 1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
//                    Canvas.draw[x-1][y+1] = false;
                    return;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
 //                   Canvas.draw[x+1][y+1] = false;
                    return;
                }
            }
        }
        //NEM perget
        if(Canvas.map[x][y+1] == 0) { //lefelé esik
            Canvas.map[x][y] = 0;
            Canvas.map[x][y+1] = type;
//            Canvas.draw[x][y+1] = false;
            return;
        }
        if(rnd.nextBoolean()) { //jobbra szeretne esni
            if(Canvas.map[x-1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x-1][y+1] = type;
//                Canvas.draw[x-1][y+1] = false;
                return;
            } else if(Canvas.map[x+1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x+1][y+1] = type;
 //               Canvas.draw[x+1][y+1] = false;
                return;
            }
        } else { //balra szeretne esni
            if(Canvas.map[x+1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x+1][y+1] = type;
 //               Canvas.draw[x+1][y+1] = false;
                return;
            } else if(Canvas.map[x-1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x-1][y+1] = type;
 //               Canvas.draw[x-1][y+1] = false;
                return;
            }
        }
      }
    }
    @PhysX(name="lava")
    public static void magma(int xo, int y, int oldx,char type) {
//*        //pergetés
        if (xo==oldx) {
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[xo+1][y+1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } // EDDIG JO
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }
//   */
        //NEM perget
        for(int x = xo; x != oldx+1; x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }

            if(Canvas.map[x+1][y] != 0 && Canvas.map[x+1][y] != Properties.ids.get("magma")) {
                Canvas.map[x+1][y] = 0;
                Canvas.map[x][y] = Properties.ids.get("fire");
                Canvas.energy[x+1][y] = 60;
            }
            if(Canvas.map[x-1][y] != 0 && Canvas.map[x-1][y] != Properties.ids.get("magma")) {
                Canvas.map[x-1][y] = 0;
                Canvas.map[x][y] = Properties.ids.get("fire");
                Canvas.energy[x-1][y] = 60;
            }
            if(Canvas.map[x][y+1] != 0 && Canvas.map[x][y+1] != Properties.ids.get("magma")) {
                Canvas.map[x][y+1] = 0;
                Canvas.map[x][y] = Properties.ids.get("fire");
                Canvas.energy[x][y+1] = 60;
            }
            if(Canvas.map[x][y-1] != 0 && Canvas.map[x][y-1] != Properties.ids.get("magma")) {
               Canvas.map[x][y-1] = 0;
                Canvas.map[x][y] = Properties.ids.get("fire");
                Canvas.energy[x][y-1] = 60;
            }

            if(rnd.nextBoolean()) { //jobbra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //balra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }

        if(rnd.nextBoolean()) { //pergetés
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
 //                   Canvas.draw[x+1][y+1] = false;
                    return;
                } else if (Canvas.map[x - 1][y + 1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
 //                   Canvas.draw[x-1][y+1] = false;
                    return;
                }
            } else { //balra
                if (Canvas.map[x - 1][y + 1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
//                    Canvas.draw[x-1][y+1] = false;
                    return;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
 //                   Canvas.draw[x+1][y+1] = false;
                    return;
                }
            }
        }
        //NEM perget
        if(Canvas.map[x][y+1] == 0) { //lefelé esik
            Canvas.map[x][y] = 0;
            Canvas.map[x][y+1] = type;
//            Canvas.draw[x][y+1] = false;
            return;
        }
        if(rnd.nextBoolean()) { //jobbra szeretne esni
            if(Canvas.map[x-1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x-1][y+1] = type;
//                Canvas.draw[x-1][y+1] = false;
                return;
            } else if(Canvas.map[x+1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x+1][y+1] = type;
 //               Canvas.draw[x+1][y+1] = false;
                return;
            }
        } else { //balra szeretne esni
            if(Canvas.map[x+1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x+1][y+1] = type;
 //               Canvas.draw[x+1][y+1] = false;
                return;
            } else if(Canvas.map[x-1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x-1][y+1] = type;
 //               Canvas.draw[x-1][y+1] = false;
                return;
            }
        }
      }
    }
    @PhysX(name="saltWater")
    public static void saltWater(int xo, int y, int oldx,char type) {
//*        //pergetés
        if (xo==oldx) {
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[xo+1][y+1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } // EDDIG JO
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }
//   */
        //NEM perget
        for(int x = xo; x != oldx+1; x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;

            if(rnd.nextBoolean()) {
                float r = rnd.nextFloat();
                if(r > 0.8) {
                    if(Canvas.map[x+1][y] == Properties.ids.get("water")) {
                        Canvas.map[x+1][y] = Properties.ids.get("salt water");
                    }
                    continue;
                }
                if(r > 0.6){
                    if(Canvas.map[x-1][y] == Properties.ids.get("water")) {
                        Canvas.map[x-1][y] = Properties.ids.get("salt water");
                    }
                    continue;
                }
                if(r > 0.4) {
                    if(Canvas.map[x][y+1] == Properties.ids.get("water")) {
                        Canvas.map[x][y+1] = Properties.ids.get("salt water");
                    }
                    continue;
                }
                if(r > 0.2) {
                    if(Canvas.map[x][y-1] == Properties.ids.get("water")) {
                        Canvas.map[x][y-1] = Properties.ids.get("salt water");
                    }
                    continue;
                }

            }
            if(Canvas.map[x][y+1] == Properties.ids.get("sand")) {
                Canvas.map[x][y+1] = Properties.ids.get("acid");
            }

            if(Canvas.temperature[x][y] > 10) {
                Canvas.map[x][y-1] = Properties.ids.get("steam");
                Canvas.map[x][y] = Properties.ids.get("salt");
            }

            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }
            if(rnd.nextBoolean()) { //jobbra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //balra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }
        }
    }
    @PhysX(name="fluid_filter")
    public static void filter(int xo, int y, int oldx,char type){
        for(int x=xo; x!=oldx+1; x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if(Properties.isFluid.get((int) Canvas.map[x][y-1])) {
                Canvas.map[x][y+2] = Canvas.map[x][y-1];
                Canvas.map[x][y-1] = 0;
            }
       }
    }
    @PhysX(name="salt_filter")
    public static void salt_filter(int xo, int y, int oldx,char type){
        for(int x=xo; x!=oldx+1; x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if(Canvas.map[x][y-1] == Properties.ids.get("salt water")) {
                boolean t = true;
                int a = 1;
                while(t) {
                    if(Canvas.map[x][y+a] == Properties.ids.get("eraser")) {
                        Canvas.map[x][y-1] = Properties.ids.get("salt");
                        Canvas.map[x][y+a] = Properties.ids.get("water");
                        break;
                    }
                    a++;
                }
                Canvas.draw[x][y+1] = false;
                //Canvas.map[x][y-1] = Sand,S; // -.-' @ToDo Fix the SaltWater Filter...  :/
            }
       }
    }
    @PhysX(name="acid_filter")
    public static void acid_filter(int xo, int y, int oldx,char type){
        for(int x=xo; x!=oldx+1; x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if(Canvas.map[x][y-1] == Properties.ids.get("acid")) {
                Canvas.map[x][y+2] = Canvas.map[x][y-1];
                Canvas.map[x][y-1] = 0;
            }
       }
    }
    @PhysX(name="oil_tap")
    public static void oilTorch(int xo, int y, int oldx,char type) {
        for(int x=xo; x!=oldx+1;x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if(rnd.nextFloat() > 0.7 && Canvas.map[x][y+2] == 0) {
                Canvas.map[x][y+2] = Properties.ids.get("oil");
//                Canvas.energy[x][y+2] = 30;
            }
        }
    }
    @PhysX(name="sand_tap")
    public static void sandTorch(int xo, int y, int oldx,char type) {
        for(int x=xo; x!=oldx+1;x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if(rnd.nextFloat() > 0.7 && Canvas.map[x][y+2] == 0) {
                Canvas.map[x][y+2] = Properties.ids.get("sand");
                Canvas.energy[x][y+2] = 30;
            }
        }
    }
    @PhysX(name="multidestroyer")
    public static void multidestroyer(int xo, int y, int oldx,char type) {
        for(int x=xo; x!=oldx+1;x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
            if(rnd.nextBoolean()) {
                float r = rnd.nextFloat();
                if(r>0.8) {
                    Canvas.map[x+1][y] = type;
                    continue;
                }
                if(r>0.6) {
                    Canvas.map[x-1][y] = type;
                    continue;
                }
                if(r>0.4) {
                    Canvas.map[x][y+1] = type;
                    continue;
                }
                if(r>0.2) {
                    Canvas.map[x][y-1] = type;
                    continue;
                }
            }
        }
    }
    @PhysX(name="lightning")
    public static void lightning(int x, int y, int oldx,char type){
        g.setColor(Properties.color.get((int)type));
        g.drawRect(x, y, 1, 1);
        boolean f = false;
        if(y+1 == MAP_HEIGHT || y == 0) {
            Canvas.map[x][y] = 0;
            Canvas.sand --;
            return;
        }

        char energya = Canvas.energy[x][y];

        if(energya == 0) {
            Canvas.map[x][y] = 0;
            Canvas.energy[x][y] = 60;
            return;
        }

        if(rnd.nextBoolean()) {
            if(Canvas.map[x-1][y-1] == 0 && rnd.nextBoolean()) {
                Canvas.map[x][y] = 0;
                Canvas.map[x-1][y-1] =type;
                Canvas.energy[x-1][y-1] = (char) (energya - 1);
 //               Canvas.draw[x-1][y-1] = false;
                return;
            } else if(Canvas.map[x+1][y-1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x+1][y-1] = type;
                Canvas.energy[x+1][y-1] = (char) (energya - 1);
//                Canvas.draw[x+1][y-1] = false;
                return;
            }
        }

        if(Canvas.energy[x][y] > 0) {
            /*if(Canvas.map[x+1][y] == Properties.ids.get("plant")) {
                Canvas.map[x+1][y] = Properties.ids.get("smoke");
//                Canvas.draw[x+1][y] = false;
                Canvas.energy[x+1][y] = (char) (energya - 1);
                Canvas.map[x][y] = 0;
                Canvas.energy[x][y] = 60;
                //f = true;
            }
            if(Canvas.map[x-1][y] == Properties.ids.get("plant")) {
                Canvas.map[x-1][y] = Properties.ids.get("smoke");
 //               Canvas.draw[x-1][y] = false;
                Canvas.map[x][y] = 0;
                Canvas.energy[x-1][y] = (char) (energya - 1);
                Canvas.energy[x][y] = 60;
                //f = true;
            }
            if(Canvas.map[x][y+1] == Properties.ids.get("plant")) {
                Canvas.map[x][y+1] = Properties.ids.get("smoke");
 //               Canvas.draw[x][y+1] = false;
                Canvas.energy[x][y+1] = (char) (energya - 1);
                Canvas.map[x][y] = 0;
                Canvas.energy[x][y] = 60;
                //f = true;
            }
            if(Canvas.map[x][y-1] == Properties.ids.get("plant")) {
                Canvas.map[x][y-1] = Properties.ids.get("smoke");
//                Canvas.draw[x][y-1] = false;
                Canvas.energy[x][y-1] = (char) (energya - 1);
                Canvas.map[x][y] = 0;
                Canvas.energy[x][y] = 60;
                f = true;
            }*/
            if(Canvas.map[x][y-1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x][y-1] = type;
 //               Canvas.draw[x][y-1] = false;
                Canvas.energy[x][y-1] = (char) (energya - 1);
                //System.out.println(new Integer(energya));
                Canvas.energy[x][y] = 60;
            }
        } else {
            Canvas.map[x][y] = 0;
            Canvas.energy[x][y] = 60;
//            Canvas.draw[x][y] = false;
        }
    }
    @PhysX(name="smoke")
    public static void smoke(int xo, int y, int oldx,char type){
        if(y+1 == MAP_HEIGHT || y == 3) {
            for(int x = xo; x != oldx+1; x++) Canvas.map[x][y] = 0;
            Canvas.sand --;
            return;
        }

           for(int x=xo;x!=oldx+1; x++) {
//                if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
                if(rnd.nextBoolean()) {
                    if(rnd.nextBoolean()) {
                        if(Canvas.map[x-1][y-1] == 0 && Canvas.draw[x][y]) {
                            Canvas.map[x-1][y-1] = Properties.ids.get("smoke");
                            Canvas.map[x][y] = 0;
                            Canvas.draw[x-1][y-1] = false;
                            return;
                        }
                     } else
                        if(Canvas.map[x+1][y-1] == 0 && Canvas.draw[x][y]) {
                            Canvas.map[x+1][y-1] = Properties.ids.get("smoke");
                            Canvas.map[x][y] = 0;
                            Canvas.draw[x+1][y-1] = false;
                            return;
                        }
                }

                if(Canvas.map[x][y-1] == 0 && Canvas.draw[x][y]) {
                    Canvas.map[x][y-1] = Properties.ids.get("smoke");
                    Canvas.map[x][y] = 0;
                    Canvas.draw[x][y-1] = false;
                }
            }
    }
    @PhysX(name="tnt")
    public static void tnt(int xo, int y, int oldx,char type){
        for(int x=xo;x!=oldx+1;x++) {
//            if(Canvas.temperature[x][y]!=0) Canvas.temperature[x][y]--;
           // if(Canvas.temperature[x][y+1]!=0) Canvas.temperature[x][y-1]+=2;
            /*if(Canvas.temperature[x][y] >= 100) {
                Penshape.Disc(x, y, 128, Properties.ids.get("fire"));
                //Canvas.drawCircle(x, y-128, 128, Sand.SMOKE, Start.gc);
            }*/
        }
        //byte t = Canvas.temperature[xo][y];
    }
    public static void test(int x, int y, int oldx,char type) {
        if (x<0) System.err.printf("x: % 3d y: % 3d oldx: % 3d \n",x,y,oldx);
    }
    public static void water(int xo, int y, int oldx,char type) {
//*        //pergetés
        if (xo==oldx) {
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[xo+1][y+1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } // EDDIG JO
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }
//   */
        //NEM perget
        for(int x = xo; x != oldx+1; x++) {
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            
            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }
            if(rnd.nextBoolean()) { //jobbra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //balra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }
        }
        }
    @PhysX(name="water")
    public static void water_NEW(int xo, int y, int oldx,char type) {
//*        //pergetés
        if (xo==oldx) {
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[xo+1][y+1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } // EDDIG JO
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }

//   */
/*
        int step = -1;
        int kezdo = MAP_WIDTH+step;
        int vegzo = 2;
        if(rnd.nextBoolean()) {
            step = 1;
            kezdo = 2;
            vegzo = MAP_WIDTH-step;
        }*/

        //NEM perget
        for(int x = xo; x != oldx+1; x++) {
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            if(Canvas.draw[x][y]==false) continue;
            if(Canvas.temperature[x][y] >= 10 && rnd.nextBoolean() && rnd.nextBoolean()) {
                Canvas.map[x][y] = Properties.ids.get("steam");
                Canvas.draw[x][y] = false;
            }
            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }
            if(rnd.nextBoolean()) {
                if(Properties.density.get((int)Canvas.map[x][y-1]) > Properties.density.get((int)Properties.ids.get("water"))) {
                    //char type1 = Canvas.map[x][y];
                    char type2 = Canvas.map[x][y-1];
                    Canvas.map[x][y] = type2;
                    Canvas.map[x][y-1] = type;
                    Canvas.draw[x][y-1] = false;
                    continue;
                }
            }
/*            if(Canvas.temperature[x][y] >= 300) {
                Canvas.map[x][y+1] = Properties.ids.get("steam");
                Canvas.temperature[x][y] = 0;
                Canvas.map[x][y] = 0;
                continue;
            }*/
            if(rnd.nextBoolean()) { //balra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //jobbra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }
            /** SZÉTFOLYIK
             *
             * @what it  go
             * @does make horizontal
             * @do   water hehe
             *
             */
            if(2*x<xo+oldx) {
                if(Canvas.map[x-1][y] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y] = type;
                    continue;
                }
             } else {
                if( Canvas.map[x+1][y] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y] = type;
                    continue;
                }
            }
            /** END **/

        }
        }
    public static void water_OLD(int x, int y, int oldx,char type) {
        if(Canvas.map[x][y+1] == Properties.ids.get("fire")) {
            Canvas.map[x][y] = Properties.ids.get("steam");
            return;
        }

        if(rnd.nextBoolean()) { //pergetés
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
  //                  Canvas.draw[x+1][y+1] = false;
                    return;
                } else if (Canvas.map[x - 1][y + 1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
   //                 Canvas.draw[x-1][y+1] = false;
                    return;
                }
            } else { //balra
                if (Canvas.map[x - 1][y + 1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    return;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
   //                 Canvas.draw[x+1][y+1] = false;
                    return;
                }
            }
        }
        //NEM perget
        if(Canvas.map[x][y+1] == 0) {// || (Properties.isFluid.get((int)Canvas.map[x][y+1]) && Properties.density.get((int)Canvas.map[x][y+1]) > 10)) { //lefelé esik
            if(Properties.isFluid.get((int) Canvas.map[x][y+1]) && Properties.density.get((int)Canvas.map[x][y+1]) > 10) {
                Canvas.map[x][y+1] = Canvas.map[x][y];
                Canvas.map[x][y] = Canvas.map[x][y+1];
                return;
            }
            Canvas.map[x][y] = 0;
            Canvas.map[x][y+1] = type;
 //           Canvas.draw[x][y+1] = false;
            return;
        }
        if(rnd.nextBoolean()) { //jobbra szeretne esni
            if(Canvas.map[x-1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x-1][y+1] = type;
  //              Canvas.draw[x-1][y+1] = false;
                return;
            } else if(Canvas.map[x+1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x+1][y+1] = type;
  //              Canvas.draw[x+1][y+1] = false;
                return;
            }
        } else { //balra szeretne esni
            if(Canvas.map[x+1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x+1][y+1] = type;
  //              Canvas.draw[x+1][y+1] = false;
                return;
            } else if(Canvas.map[x-1][y+1] == 0) {
                Canvas.map[x][y] = 0;
                Canvas.map[x-1][y+1] = type;
   //             Canvas.draw[x-1][y+1] = false;
                return;
            }
        }
    }
    @PhysX(name="lye")
    public static void anti_acid(int xo, int y, int oldx,char type) {
//*        //pergetés
        if (xo==oldx) {
            if(rnd.nextBoolean()) { //jobbra
                if(Canvas.map[xo+1][y+1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo+1][y+1] = type;
                }
            } else { //balra
                if (Canvas.map[xo - 1][y + 1] == 0) {
                    Canvas.map[xo][y] = 0;
                    Canvas.map[xo-1][y+1] = type;
                }
            } // EDDIG JO
        } else {
//            rnd = new Random(System.currentTimeMillis());
            if(rnd.nextBoolean()) {//balra
//                balszel++;
                if (Canvas.map[xo - 1][y + 1] == 0) {
                   Canvas.map[xo][y] = 0;
                   Canvas.map[xo-1][y+1] = type;
                }
            }
            if(rnd.nextBoolean()) {//jobbra
//                jobbszel++;
                if(Canvas.map[oldx+1][y+1] == 0) {
                    Canvas.map[oldx][y] = 0;
                    Canvas.map[oldx+1][y+1] = type;
                }
            }
        }
        //NEM perget
        for(int x = xo; x != oldx+1; x++) {
            if ( (x==xo || x==oldx) && Canvas.map[x][y]==0) continue;
            if(Canvas.map[x][y+1] == 0) { //lefelé esik
                Canvas.map[x][y] = 0;
                Canvas.map[x][y+1] = type;
    //            Canvas.draw[x][y+1] = false;
                continue;
            }
            if(rnd.nextBoolean()) { //balra szeretne esni
                if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
    //                Canvas.draw[x-1][y+1] = false;
                    continue;
                } else if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                }
            } else { //jobbra szeretne esni
                if(Canvas.map[x+1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y+1] = type;
    //                Canvas.draw[x+1][y+1] = false;
                    continue;
                } else if(Canvas.map[x-1][y+1] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y+1] = type;
     //               Canvas.draw[x-1][y+1] = false;
                        continue;
                }
            }
            if(2*x<xo+oldx) {
                if(Canvas.map[x-1][y] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x-1][y] = type;
                    continue;
                }
             } else {
                if( Canvas.map[x+1][y] == 0) {
                    Canvas.map[x][y] = 0;
                    Canvas.map[x+1][y] = type;
                    continue;
                }
            }
            if(Canvas.map[x][y+1] == Properties.ids.get("acid")) {
                Canvas.draw[x][y+1] = false;
                Canvas.map[x][y+1] = 0;
            } else {
                Canvas.map[x][y] = Properties.ids.get("smoke");
            }
            /** END **/

        }
        }
    }

// =)d