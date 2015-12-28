/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

import java.util.HashMap;

/**
 *
 * @author Levex
 */
public class Wind {
    public static int xspeed = 0;
    private static int yspeed = 0;
    private static int MAP_WIDTH = Canvas.MAP_WIDTH; // Copy for extra speed;
    private static int MAP_HEIGHT = Canvas.MAP_HEIGHT; // Copy for extra speed;

    public static final char DIRECTION_RIGHT = 20;
    public static final char DIRECTION_LEFT = 30;
    public static final char DIRECTION_UP = 40;
    public static final char DIRECTION_DOWN = 50;

    public static final char NO_WIND = 0;

    public static final char FROM_RIGHT = 22;
    public static final char FROM_LEFT = 33;
    public static final char FROM_UP = 44;
    public static final char FROM_DOWN = 55;

    public static char[][] wind = new char[MAP_WIDTH+4][MAP_HEIGHT+4];
    //public static HashMap<Integer, byte[]> cache = new HashMap<Integer, byte[]>();

    public static void initWind() {
        for(int x = 0; x <MAP_WIDTH+4; x++) {
            for(int y = 0; y < MAP_HEIGHT+4; y++) {
                wind[x][y] = NO_WIND;
            }
        }
    }

    public static void setWind(char dir, char power, int x, int y) {
        if(dir%10!=0) {
            System.err.println("Wind was set to invalid direction!");
            return;
        }
        wind[x][y] = dir;
    }

    /*
     *
     * @return byte array containing the following: index0: x POSITION index1: DIRECTION
     *
     */
    public static char[] getHorizontalWind(int y) {
        /*if(cache.containsKey(y)) {
            return cache.get(y);
        }*/
        for(int x=0; x<MAP_WIDTH;x++) {
            /*if(Canvas.map[x+2][y] != 0) {
                return null;
            }*/
            if(wind[x][y] != NO_WIND) {
                //cache.put(y, new byte[] { (byte)x, (byte)wind[x][y]});
                //System.err.println("XPOS: "+x+"  wind:"+(byte)wind[x][y]);
                return new char[] { (char)x, (char)wind[x][y]};
            }
        }
        return null;
    }
}
