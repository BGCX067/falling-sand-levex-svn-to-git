/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Levex
 */
public class RenderThread implements Runnable{

    
    Graphics g = null;
    public RenderThread(Graphics g) {
        this.g = g;
    }

    public void run() {
        while(true) {
            try {
                Canvas.render();
                //g.drawString("Sand: "+Canvas.getSize(), 10, 30);
            } catch (Exception ex) {
                Logger.getLogger(RenderThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
