/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

import java.awt.Point;
import org.newdawn.slick.geom.Polygon;

/**
 *
 * @author Levex
 */
public class Triangle implements Pregen {
    public static void draw(int x, int y, int size) {
        /*int s = Start.pc.sandSize;
        Point a = new Point(x,y-s/2);
        Point b = new Point(x-s/2, y+s/2);
        Point c = new Point(x+s/2, x+s/2);

        Canvas.map[a.x][a.y] = Start.pc.sand;
        Canvas.map[a.x][a.y] = Start.pc.sand;
        Canvas.map[a.x][a.y] = Start.pc.sand;*/
    }

   /* public static float[] getPoints(int x, int y, int size)  {
        int s = Start.pc.sandSize;
        Point a = new Point(x,y-s/2);
        Point b = new Point(x-s/2, y+s/2);
        Point c = new Point(x+s/2, x+s/2);
        float[] f = new float[6];
        f[0] = a.x;
        f[1] = a.y;
        f[2] = b.x;
        f[3] = b.y;
        f[4] = c.x;
        f[5] = c.y;
        return f;
    }*/
}
