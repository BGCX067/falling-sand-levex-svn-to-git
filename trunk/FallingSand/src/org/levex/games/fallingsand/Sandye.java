/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

import org.newdawn.slick.Color;
//import org.newdawn.slick.GameContainer;
//import org.newdawn.slick.Graphics;

/**
 *
 * @author Levex
 */
@Deprecated
public final class Sandye {

    /*
     *
     * Historical class, no longer used
     * @deprecated
     *
     */

    public static final int ERASER = 0x0;
    public static final Color ERASER_COLOR = Color.black;
    public static final int SAND = 0x1;
    public static final Color SAND_COLOR = new Color(244, 164, 96);
    public static final int WALL = 0x2;
    public static final Color WALL_COLOR = new Color(139, 137, 137);
    public static final int WATER = 0x3;
    public static final Color WATER_COLOR = Color.blue;
    public static final int PLANT = 0x4;
    public static final Color PLANT_COLOR = Color.green;
    public static final int FIRE = 0x5;
    public static final Color FIRE_COLOR = Color.red;
    public static final int ACID = 0x6;
    public static final Color ACID_COLOR = new Color(124, 252, 0);
    public static final int OIL = 0x7;
    public static final Color OIL_COLOR = new Color(139, 69, 19);
    public static final int STEAM = 0x8;
    public static final Color STEAM_COLOR = new Color(135, 206, 250);
    public static final int TORCH = 0x9;
    public static final Color TORCH_COLOR = new Color(255, 140, 0);
    public static final int WATER_TORCH = 0xA;
    public static final Color WATER_TORCH_COLOR = new Color(30, 144, 255);
    public static final int WATER_TAP_CLOSED = 0xB;
    public static final Color WATER_TAP_CLOSED_COLOR = new Color(132,112,255);
    public static final int WATER_TAP_OPENED = 0xC;
    public static final Color WATER_TAP_OPENED_COLOR = new Color(240,255,240);
    public static final int SALT = 0xD;
    public static final Color SALT_COLOR = new Color(255,245,238);
    public static final int SALT_WATER = 0xE;
    public static final Color SALT_WATER_COLOR = new Color(0,191,255);
    public static final int FILTER = 0xF;
    public static final Color FILTER_COLOR = Color.yellow;
    public static final int OIL_TORCH = 0x10;
    public static final Color OIL_TORCH_COLOR = new Color(165, 42, 42);
    public static final int SAND_TORCH = 0x11;
    public static final Color SAND_TORCH_COLOR = new Color(160, 82, 45);
    public static final int SMOKE = 0x12;
    public static final Color SMOKE_COLOR = new Color(139, 125, 107);
    public static final int TNT = 0x13;
    public static final Color TNT_COLOR = Color.pink;
    public static final int IRON = 0x14;
    public static final Color IRON_COLOR = new Color(230,230,250);
    public static final int CIGARETTE = 0x15;
    public static final Color CIGARETTE_COLOR = new Color(178,34,34);
    public static final int ACID_FILTER = 0x16;
    public static final Color ACID_FILTER_COLOR = Color.orange;
    public static final int ANTI_ACID = 0x17;
    public static final Color ANTI_ACID_COLOR = Color.darkGray;
    public static final int SALT_FILTER = 0x18;
    public static final Color SALT_FILTER_COLOR = new Color(85,107,47);
    public static final int MAGMA = 0x19;
    public static final Color MAGMA_COLOR = new Color(255,69,0);
    public static final int GAS = 0x1A;
    public static final int LIME = 0x1B;
    public static final int ICE = 0x1C;
    public static final int VINE = 0x1D;
    public static final int SEED = 0x1E;

    // static final int LIME = 0x00;
    public static final Color  LIME_COLOR = new Color(0,0,0);
    public static final int LYE = ANTI_ACID;
    public static final Color  LYE_COLOR = ANTI_ACID_COLOR;
    public static final int SAND_TAP = SAND_TORCH;
    public static final Color  SAND_TAP_COLOR = SAND_TORCH_COLOR;
    public static final int SALT_TAP = 0x00;
    public static final Color  SALT_TAP_COLOR = new Color(0,0,0);
    public static final int LIME_TAP = 0x00;
    public static final Color  LIME_TAP_COLOR = new Color(0,0,0);
    public static final int OIL_TAP = OIL_TORCH;
    public static final Color  OIL_TAP_COLOR = OIL_TORCH_COLOR;
    public static final int GAS_TAP = 0x00;
    public static final Color  GAS_TAP_COLOR = new Color(0,0,0);
    public static final int SMOKE_TAP = 0x00;
    public static final Color  SMOKE_TAP_COLOR = new Color(0,0,0);
    public static final int WATER_TAP = WATER_TORCH;
    public static final Color  WATER_TAP_COLOR = WATER_TORCH_COLOR;
    public static final int ACID_TAP = 0x00;
    public static final Color  ACID_TAP_COLOR = new Color(0,0,0);
    public static final int LYE_TAP = 0x00;
    public static final Color  LYE_TAP_COLOR = new Color(0,0,0);
    public static final int CONCRETE = WALL;
    public static final Color  CONCRETE_COLOR = WALL_COLOR;
    public static final int WOOD = 0x00;
    public static final Color  WOOD_COLOR = new Color(0,0,0);
    //public static final int ICE = 0x00;
    public static final Color  ICE_COLOR = new Color(0,0,0);
    public static final int FLUID_FILTER = FILTER;
    public static final Color  FLUID_FILTER_COLOR = FILTER_COLOR;
    public static final int FIRE_TORCH = FIRE;
    public static final Color  FIRE_TORCH_COLOR = FIRE_COLOR;
    public static final int VOLCANO = MAGMA;
    public static final Color  VOLCANO_COLOR = MAGMA_COLOR;
    public static final int GEYSER = 0x00;
    public static final Color  GEYSER_COLOR = new Color(0,0,0);
    public static final int TURBINE = 0x00;
    public static final Color  TURBINE_COLOR = new Color(0,0,0);
    public static final int HOOVER = 0x00;
    public static final Color  HOOVER_COLOR = new Color(0,0,0);
    public static final int MATCH = CIGARETTE;
    public static final Color  MATCH_COLOR = CIGARETTE_COLOR;


    public static String toString(int s) {
        return Properties.name.get(s);
    }
}
