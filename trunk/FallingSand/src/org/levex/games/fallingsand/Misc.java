/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

import org.newdawn.slick.Color;

/**
 *
 * @author Levex
 */
public class Misc {

    public static Color parseColor(String code) {
        String[] rgb = code.split("_");
        //System.err.println("R: "+rgb[0]+" G: "+rgb[1]+" B: "+rgb[2]);
        Color temp = new Color(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2]));
        return temp;
    }

}
