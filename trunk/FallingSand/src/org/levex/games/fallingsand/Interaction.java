/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

/**
 *
 * @author Levex
 * Should be a struct for speed, prolly.
 */
public class Interaction {
    public String with, into;
    public float prob = 0, g = 0, s = 0;
    public Interaction(String with, String into, float p, float g, float s) {
        this.with = with;
        this.into = into;
        this.prob = p;
        this.g = g;
        this.s = s;
    }
}
