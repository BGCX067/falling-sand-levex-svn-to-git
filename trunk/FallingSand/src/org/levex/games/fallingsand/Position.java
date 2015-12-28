/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

/**
 *
 * @author Levex
 */
public class Position {


    public byte x, y, framelife;
    public Position(byte x, byte y, byte framelife) {
        this.x=x;
        this.y=y;
        this.framelife = framelife;
    }

}
