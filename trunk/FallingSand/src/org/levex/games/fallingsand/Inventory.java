/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

/**
 *
 * @author Levex
 * @description Class for storing RPG mode items, and such.
 *
 */
public class Inventory {

    private static char[] inv = new char[Properties.ids.size()];

    public static void add(char m, int a) {
        inv[m] = (char) (inv[m] + a);
    }

    public static char get(char m) {
        return inv[m];
    }

    public static char[] getInv() {
        return inv;
    }

}
