/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

import java.util.HashMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Levex
 */
public @Deprecated class GUI {
   // public Image sand = null;
    public HashMap<String, Integer[]> data = new HashMap<String, Integer[]>();
    public HashMap<String, Image> images = new HashMap<String, Image>();
    public GUI() throws SlickException {
        /*images.put("sand", new Image("data/sand.gif"));
        data.put("sand", new Integer[] {9, 573, Sand.SAND});
        images.put("acid", new Image("data/acid.gif"));
        data.put("acid", new Integer[] {9+64, 573, Sand.ACID});*/
        
    }
    public void render() {

        /*images.get("sand").draw(data.get("sand")[0], data.get("sand")[1]);
        images.get("acid").draw(data.get("acid")[0], data.get("acid")[1]);*/
    }
}
