/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

//import org.newdawn.slick.GameContainer;

import java.util.Random;


/**
 *
 * @author Levex
 */
public class Penshape implements Pregen {
//    private static final int MAP_WIDTH = Canvas.MAP_WIDTH;
//    private static final int MAP_HEIGHT = Canvas.MAP_HEIGHT;
    private static final int MAP_X_MAX = Canvas.MAP_X_MAX;
    private static final int MAP_Y_MAX = Canvas.MAP_Y_MAX;
    private static final Random rnd = new Random();

    public static void Disc(int centerx, int centery, int diameter, char material) {
        int r = diameter/2;
        int rr = r*r;
        for(int x=centerx-r;x<=centerx+r;++x){
            if(x<2 || x>MAP_X_MAX) continue;
            int dx = x-centerx;
            int dy = (int)Math.round(Math.sqrt(rr - dx*dx));
            for(int y=centery-dy;y<=centery+dy;++y){
                if(y<2 || y>MAP_Y_MAX) continue;
                Canvas.map[x][y] = material;
                if (material==Properties.ids.get("fire") || material == Properties.ids.get("steam")) {
                    Canvas.energy[x][y] = (char) (100 + (rnd.nextInt(40)));
                    Canvas.temperature[x][y] = 100;
                }
            }
        }
    }

    public static void Square(int xo, int yo,int diameter, char material) {
        int radius=diameter/2;
        for(int x = xo-radius; x < xo+radius; x++) {
            if(x<=2) continue;
            if(x>=MAP_X_MAX) continue;
            for(int y =yo-radius; y<yo+radius;y++) {
                if(y<=2) continue;
                if(y>=MAP_Y_MAX) continue;
                Canvas.map[x][y] =(char) material;
            }
        }
    }

    static void Triangle(int xo, int yo,int diameter, char material) {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void Circle(int xo, int yo, int diameter, char material) {
      int radius=diameter/2;
      Canvas.setPixel(xo,yo + radius, material);
      Canvas.setPixel(xo,yo - radius,material);
      Canvas.setPixel(xo + radius,yo,material);
      Canvas.setPixel(xo - radius,yo,material);

      int f = 1 - radius;
      int ddF_x = 1;
      int ddF_y = -2 * radius;
      int x = 0;
      int y = radius;

      while(x < y) {
            // ddF_x == 2 * x + 1;
            // ddF_y == -2 * y;
            // f == x*x + y*y - radius*radius + 2*x - y + 1;
            if(f >= 0) {
              y--;
              ddF_y += 2;
              f += ddF_y;
            }
            x++;
            ddF_x += 2;
            f += ddF_x;
            Canvas.setPixel(xo + x,yo + y,material);
            Canvas.setPixel(xo - x,yo + y,material);
            Canvas.setPixel(xo + x,yo - y,material);
            Canvas.setPixel(xo - x,yo - y,material);
            Canvas.setPixel(xo + y,yo + x,material);
            Canvas.setPixel(xo - y,yo + x,material);
            Canvas.setPixel(xo + y,yo - x,material);
            Canvas.setPixel(xo - y,yo - x,material);
        }
    }
}
