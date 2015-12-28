/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.levex.games.fallingsand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Levex
 */
public class Properties {

//    public static ArrayList<DefaultMutableTreeNode> groups = new ArrayList<DefaultMutableTreeNode>();
    public static HashMap<Integer, String> groups = new HashMap<Integer, String>();
    public static HashMap<Integer, ArrayList<Integer>> group_elements = new HashMap<Integer, ArrayList<Integer>>();
    public static HashMap<String, Character> ids = new HashMap<String, Character>();
    public static HashMap<Integer, String> name = new HashMap<Integer, String>();
    public static HashMap<Integer, String> info = new HashMap<Integer, String>();
    public static HashMap<Integer, String> ingroup = new HashMap<Integer, String>();
    public static HashMap<Integer, Color> color = new HashMap<Integer, Color>();
    public static HashMap<Integer, Method> phys = new HashMap<Integer, Method>();
    public static HashMap<Integer, Boolean> showInRPG = new HashMap<Integer, Boolean>();

    public static ArrayList<Integer> shouldnt_display = new ArrayList<Integer>();
//     type="gas"
    public static HashMap<Integer, String> type = new HashMap<Integer, String>();
//     density="30"
    public static HashMap<Integer, Integer> density = new HashMap<Integer, Integer>();
//             chemic="neutral"
    public static HashMap<Integer, Integer> chemic = new HashMap<Integer, Integer>();
    //public static HashMap<Integer, Integer> fireCatchy = new HashMap<Integer, Integer>();
//             resistant="1"
    public static HashMap<Integer, Boolean> isAffectedByAcid = new HashMap<Integer, Boolean>();
//             burnable="0"
    public static HashMap<Integer, Float> isBurnable = new HashMap<Integer, Float>();
    public static HashMap<Integer, Interaction> interaction = new HashMap<Integer, Interaction>();
    public static HashMap<Integer, Boolean> isFluid = new HashMap<Integer, Boolean>();
    public static HashMap<Integer, Boolean> isStatic = new HashMap<Integer, Boolean>();

    public static Color parseColor(String code) {
        String[] rgb = code.split("_");
        //System.err.println("R: "+rgb[0]+" G: "+rgb[1]+" B: "+rgb[2]);
        Color temp = new Color(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2]));
        return temp;
    }

    public static void loadElements() {
        int id=0;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = null;
            if(Start.isApplet) {
                      URL u;
                      InputStream is = null;
                      DataInputStream dis;
                      String s;
                      PrintStream ps = new PrintStream(new File("elements.xml"));
                      try {
                         u = new URL("http://sandye.sf.net/elements.xml");
                         is = u.openStream();         // throws an IOException
                         dis = new DataInputStream(new BufferedInputStream(is));

                         while ((s = dis.readLine()) != null) {
                             String s2 = new String(s.getBytes("UTF-8"));
                             ps.println(s2);
                         }
                       } catch(Exception e) {
                           ;
                       }

            }
                dom = db.parse("elements.xml");
            //get the root element
            Element docEle = dom.getDocumentElement();

            NodeList grouplist = docEle.getElementsByTagName("group");
            if(grouplist != null && grouplist.getLength() > 0) {
                for(int j = 0 ; j < grouplist.getLength();j++) {
                    Element group = (Element) grouplist.item(j);
                    String nam = group.getAttribute("name");
                    groups.put(j, nam);
                    String _category = group.getAttribute("category");
                    System.out.println("Group added: "+groups.get(groups.size()-1).toString());

                    NodeList elemList = group.getElementsByTagName(_category);
                    if(elemList== null || elemList.getLength() == 0) continue;
                    ArrayList<Integer> group_lmnts = new ArrayList<Integer>();
                    for(int i = 0 ; i < elemList.getLength();i++) {
                        Element elemen = (Element)elemList.item(i);
                        NodeList elemProps = elemen.getElementsByTagName("properties");
                        if(elemProps== null || elemProps.getLength() == 0) continue;
                        Element elprops = (Element)elemProps.item(0);
                        String _name = elemen.getAttribute("name");
                        _name = _name.toLowerCase();
                        /*if(_name.equals("nothing")) {
                            name.put(0, "nothing");
                            ids.put("nothing", (char)0);
                            id++;
                            continue;
                        }*/
                        name.put(id, _name);
                        ids.put(_name,(char)id); //figyelni egyediségre!!
                        String _info = elemen.getAttribute("info");
                        String _menu = elemen.getAttribute("dontadd");
                        /*if(!_menu.equalsIgnoreCase("")) {
                            shouldnt_display.add(id);
                        }*/
                        info.put(id, _info);

                        Color _color = parseColor(elprops.getAttribute("color"));
                        color.put(id,_color);
                        String _physx = elprops.getAttribute("physx");
                        if(!Start.physx.containsKey(_physx)) {
                            System.err.println("Unknown physics: "+_physx+". Applying default physics: concrete");
                            _physx = "concrete";
                        }
                        phys.put(id,Start.physx.get(_physx));
                        String _type = elprops.getAttribute("type");
                        type.put(id, _type);
                        showInRPG.put(id, (Integer.parseInt(elprops.getAttribute("gamemode"))==1)?true:false);
                        isFluid.put(id, _type.equals("fluid"));
                        density.put(id, Integer.parseInt(elprops.getAttribute("density")));
                        isStatic.put(id, (!_type.equals("fluid") && !_type.equals("gas")));
                        String _chemic = elprops.getAttribute("chemic");
                        chemic.put(id, (_chemic.equals("Acid")?1:(_chemic.equals("Lye")?-1:0)) );
                        Integer resis = Integer.parseInt(elprops.getAttribute("resistant"));
                        isAffectedByAcid.put(id, resis==1?false:true);
                        Float burn = Float.parseFloat(elprops.getAttribute("burnable"));
                        isBurnable.put(id, burn.floatValue());
                        if(_menu.equalsIgnoreCase("")) group_lmnts.add(id);

                        /* Now let's parse interactions */
                        if(_physx.equals("custom")) { // But only if it uses a Custom Physics.
                            NodeList n = elemen.getElementsByTagName("interact");
                            //Sys.alert("","Boom");
                            for(int a = 0; a < n.getLength(); a++) {
                                Element e = (Element) n.item(a);
                                String with = e.getAttribute("with");
                                NodeList b = e.getElementsByTagName("transform");
                                Element E = null;
                                E = (Element) b.item(0);
                                if(E == null) { Sys.alert("","No Transform is found in INTERACT... Skipping..."); break; }
                                String into = E.getTextContent();
                                into = into.trim().toLowerCase();
                                with = with.trim().toLowerCase();
                                Float p = Float.parseFloat(E.getAttribute("probability"));
                                
                                /* Gravity Tag */
                                float gravity = 0f;
                                float slip = 0f;
                                Element elGrav = (Element) elemen.getElementsByTagName("gravity").item(0);
                                slip = Float.parseFloat(elGrav.getAttribute("slip"));
                                gravity = Float.parseFloat(elGrav.getAttribute("gravity"));

                                
                                /* Let's put together the interaction */
                                Interaction ia = new Interaction(with, into, p, gravity, slip);
                                interaction.put(id,ia);
                            }
                        }

                        id++;
                    }
                    group_elements.put(j,group_lmnts);
                }
            }
            System.out.println("Loaded: "+groups.size()+" groups, "+name.size()+" elements.");
        } catch (Exception ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setupProps() {
        //Options
        //Materials
        //names.put(Sand.SAND,"Sand");
        //names.put(Sand.SALT,"Salt");
        //names.put(Sand.LIME,"Lime");
        //names.put(Sand.OIL,"Oil");
        //names.put(Sand.GAS,"Gas");
        //names.put(Sand.SMOKE,"Smoke");
        //names.put(Sand.STEAM,"Steam");
        //names.put(Sand.WATER,"Water");
       // names.put(Sand.ACID,"Acid");
        //names.put(Sand.LYE,"Lye");
        //            sand = Sand.ANTI_ACID;
        //Taps
       // names.put(Sand.SAND_TAP,"Sand tap");
        //            sand = Sand.SAND_TORCH;
        //names.put(Sand.SALT_TAP,"Salt tap");
        //names.put(Sand.LIME_TAP,"Lime tap");
       // names.put(Sand.OIL_TAP,"Oil tap");
        //sand = Sand.OIL_TORCH;
        //names.put(Sand.GAS_TAP,"Gas tap");
        //names.put(Sand.SMOKE_TAP,"Smokestack");
        //names.put(Sand.ANTI_ACID,"Steam tap");
        //names.put(Sand.WATER_TAP,"Water tap");
        //          sand = Sand.WATER_TORCH;
        //names.put(Sand.ACID_TAP,"Acid tap");
        //names.put(Sand.LYE_TAP,"Lye tap");
        //Builds
        //names.put(Sand.CONCRETE,"Concrete");
        //names.put(Sand.WALL,"Wall");
        //names.put(Sand.IRON,"Iron");
        //names.put(Sand.WOOD,"Wood");
        //names.put(Sand.PLANT,"Plant");
        //names.put(Sand.ICE,"Ice");
        //names.put(Sand.FLUID_FILTER,"Fluid Filter");
        //sand = Sand.FILTER;
        //names.put(Sand.ACID_FILTER,"Acid Filter");
        //names.put(Sand.SALT_FILTER,"Salt Filter");
        //Equipments
        //names.put(Sand.FIRE_TORCH,"Fire torch");
        //sand = Sand.FIRE;
        //names.put(Sand.VOLCANO,"Volcano");
        //sand = Sand.MAGMA;
        //names.put(Sand.GEYSER,"Geyser");
        //names.put(Sand.TURBINE,"Turbine");
        //names.put(Sand.HOOVER,"Hoover");
        //Tools
        //names.put(Sand.MATCH,"Match");
        //sand = Sand.CIGARETTE;
        //names.put(Sand.TNT,"TNT");
        //names.put(Sand.ERASER,"Eraser");

        //density.put(Sand.WATER, 10);
        //density.put(Sand.ACID, 20);
        //density.put(Sand.SALT_WATER, 30);

        /*isFluid.put(Sand.WATER, Boolean.TRUE);
        isFluid.put(Sand.ACID, Boolean.TRUE);
        isFluid.put(Sand.ERASER, Boolean.FALSE);
        isFluid.put(Sand.FIRE, Boolean.FALSE);
        isFluid.put(Sand.OIL, Boolean.TRUE);
        isFluid.put(Sand.PLANT, Boolean.FALSE);
        isFluid.put(Sand.SAND, Boolean.FALSE);
        isFluid.put(Sand.STEAM, Boolean.FALSE);
        isFluid.put(Sand.TORCH, Boolean.FALSE);
        isFluid.put(Sand.WALL, Boolean.FALSE);
        isFluid.put(Sand.WATER_TAP_CLOSED, Boolean.FALSE);
        isFluid.put(Sand.WATER_TAP_OPENED, Boolean.FALSE);
        isFluid.put(Sand.WATER_TORCH, Boolean.FALSE);
        isFluid.put(Sand.SALT, Boolean.FALSE);
        isFluid.put(Sand.SALT_WATER, Boolean.TRUE);
        isFluid.put(Sand.FILTER, Boolean.FALSE);
        isFluid.put(Sand.OIL_TORCH, Boolean.FALSE);
        isFluid.put(Sand.SAND_TORCH, Boolean.FALSE);
        isFluid.put(Sand.SMOKE, Boolean.FALSE);
        isFluid.put(Sand.IRON, Boolean.FALSE);
        isFluid.put(Sand.CIGARETTE, Boolean.FALSE);
        isFluid.put(Sand.ACID_FILTER, Boolean.FALSE);
        isFluid.put(Sand.ANTI_ACID, Boolean.TRUE);
        isFluid.put(Sand.SALT_FILTER, Boolean.FALSE);
        isFluid.put(Sand.TNT, Boolean.FALSE);
        isFluid.put(Sand.MAGMA, Boolean.TRUE);*/

        /*colors.put(Sand.ACID, Sand.ACID_COLOR);
        //colors.put(Sand.ERASER, Sand.ERASER_COLOR);
        colors.put(Sand.FIRE, Sand.FIRE_COLOR);
        colors.put(Sand.OIL, Sand.OIL_COLOR);
        colors.put(Sand.PLANT, Sand.PLANT_COLOR);
        colors.put(Sand.SALT, Sand.SALT_COLOR);
       // colors.put(Sand.SAND, Sand.SAND_COLOR);
        colors.put(Sand.STEAM, Sand.STEAM_COLOR);
        colors.put(Sand.TORCH, Sand.TORCH_COLOR);
        colors.put(Sand.WALL, Sand.WALL_COLOR);
        colors.put(Sand.WATER, Sand.WATER_COLOR);
        colors.put(Sand.WATER_TAP_CLOSED, Sand.WATER_TAP_CLOSED_COLOR);
        colors.put(Sand.WATER_TAP_OPENED, Sand.WATER_TAP_CLOSED_COLOR);
        colors.put(Sand.WATER, Sand.WATER_COLOR);
        colors.put(Sand.SALT_WATER, Sand.SALT_WATER_COLOR);
        colors.put(Sand.FILTER, Sand.FILTER_COLOR);
        colors.put(Sand.OIL_TORCH, Sand.OIL_TORCH_COLOR);
        colors.put(Sand.SAND_TORCH, Sand.SAND_TORCH_COLOR);
        colors.put(Sand.SMOKE, Sand.SMOKE_COLOR);
        colors.put(Sand.IRON, Sand.IRON_COLOR);
        colors.put(Sand.CIGARETTE, Sand.CIGARETTE_COLOR);
        colors.put(Sand.ACID_FILTER, Sand.ACID_FILTER_COLOR);
        colors.put(Sand.ANTI_ACID, Sand.ANTI_ACID_COLOR);
        colors.put(Sand.SALT_FILTER, Sand.SALT_FILTER_COLOR);
        colors.put(Sand.TNT, Sand.TNT_COLOR);
        colors.put(Sand.MAGMA, Sand.MAGMA_COLOR);*/

        //colors.put(Sand.LIME, Sand.LIME_COLOR);
        //colors.put(Sand.GAS, Sand.GAS_COLOR);
        //colors.put(Sand.LYE, Sand.LYE_COLOR);
        //colors.put(Sand.SAND_TAP, Sand.SAND_TAP_COLOR);
        //colors.put(Sand.SALT_TAP, Sand.SALT_TAP_COLOR);
        //colors.put(Sand.LIME_TAP, Sand.LIME_TAP_COLOR);
        //colors.put(Sand.OIL_TAP, Sand.OIL_TAP_COLOR);
        //colors.put(Sand.GAS_TAP, Sand.GAS_TAP_COLOR);
        //colors.put(Sand.SMOKE_TAP, Sand.SMOKE_TAP_COLOR);
        //colors.put(Sand.WATER_TAP, Sand.WATER_TAP_COLOR);
        //colors.put(Sand.ACID_TAP, Sand.ACID_TAP_COLOR);
        //colors.put(Sand.LYE_TAP, Sand.LYE_TAP_COLOR);
        //colors.put(Sand.CONCRETE, Sand.CONCRETE_COLOR);
        //colors.put(Sand.WOOD, Sand.WOOD_COLOR);
        //colors.put(Sand.ICE, Sand.ICE_COLOR);
        //colors.put(Sand.FLUID_FILTER, Sand.FLUID_FILTER_COLOR);
        //colors.put(Sand.FIRE_TORCH, Sand.FIRE_TORCH_COLOR);
        //colors.put(Sand.VOLCANO, Sand.VOLCANO_COLOR);
        //colors.put(Sand.GEYSER, Sand.GEYSER_COLOR);
        //colors.put(Sand.TURBINE, Sand.TURBINE_COLOR);
        //colors.put(Sand.HOOVER, Sand.HOOVER_COLOR);
        //colors.put(Sand.MATCH, Sand.MATCH_COLOR);


        /*isStatic.put(Sand.ACID, Boolean.FALSE);
        isStatic.put(Sand.ERASER, Boolean.TRUE);
        isStatic.put(Sand.FILTER, Boolean.TRUE);
        isStatic.put(Sand.FIRE, Boolean.FALSE);
        isStatic.put(Sand.OIL, Boolean.FALSE);
        isStatic.put(Sand.OIL_TORCH, Boolean.TRUE);
        isStatic.put(Sand.PLANT, Boolean.TRUE);
        isStatic.put(Sand.SALT, Boolean.FALSE);
        isStatic.put(Sand.SALT_WATER, Boolean.FALSE);
        isStatic.put(Sand.SAND, Boolean.FALSE);
        isStatic.put(Sand.SAND_TORCH, Boolean.TRUE);
        isStatic.put(Sand.SMOKE, Boolean.FALSE);
        isStatic.put(Sand.STEAM, Boolean.FALSE);
        isStatic.put(Sand.TNT, Boolean.TRUE);
        isStatic.put(Sand.TORCH, Boolean.TRUE);
        isStatic.put(Sand.WALL, Boolean.TRUE);
        isStatic.put(Sand.WATER, Boolean.FALSE);
        isStatic.put(Sand.WATER_TAP_CLOSED, Boolean.TRUE);
        isStatic.put(Sand.WATER_TAP_OPENED, Boolean.TRUE);
        isStatic.put(Sand.WATER_TORCH, Boolean.TRUE);
        isStatic.put(Sand.IRON, Boolean.TRUE);
        isStatic.put(Sand.CIGARETTE, Boolean.FALSE);
        isStatic.put(Sand.ACID_FILTER, Boolean.TRUE);
        isStatic.put(Sand.ANTI_ACID, Boolean.FALSE);
        isStatic.put(Sand.SALT_FILTER, Boolean.TRUE);
        isStatic.put(Sand.MAGMA, Boolean.FALSE);*/

        /*isAffectedByAcid.put(Sand.ACID, Boolean.FALSE);
        isAffectedByAcid.put(Sand.ERASER, Boolean.TRUE);
        isAffectedByAcid.put(Sand.FILTER, Boolean.FALSE);
        isAffectedByAcid.put(Sand.FIRE, Boolean.FALSE);
        isAffectedByAcid.put(Sand.OIL, Boolean.FALSE);
        isAffectedByAcid.put(Sand.OIL_TORCH, Boolean.TRUE);
        isAffectedByAcid.put(Sand.PLANT, Boolean.TRUE);
        isAffectedByAcid.put(Sand.SALT, Boolean.TRUE);
        isAffectedByAcid.put(Sand.SALT_WATER, Boolean.TRUE);
        isAffectedByAcid.put(Sand.SAND, Boolean.TRUE);
        isAffectedByAcid.put(Sand.SAND_TORCH, Boolean.TRUE);
        isAffectedByAcid.put(Sand.SMOKE, Boolean.FALSE);
        isAffectedByAcid.put(Sand.STEAM, Boolean.TRUE);
        isAffectedByAcid.put(Sand.TNT, Boolean.TRUE);
        isAffectedByAcid.put(Sand.TORCH, Boolean.TRUE);
        isAffectedByAcid.put(Sand.WALL, Boolean.FALSE);
        isAffectedByAcid.put(Sand.WATER, Boolean.FALSE);
        isAffectedByAcid.put(Sand.WATER_TAP_CLOSED, Boolean.FALSE);
        isAffectedByAcid.put(Sand.WATER_TAP_OPENED, Boolean.FALSE);
        isAffectedByAcid.put(Sand.WATER_TORCH, Boolean.TRUE);
        isAffectedByAcid.put(Sand.IRON, Boolean.TRUE);
        isAffectedByAcid.put(Sand.CIGARETTE, Boolean.TRUE);
        isAffectedByAcid.put(Sand.ACID_FILTER, Boolean.FALSE);
        isAffectedByAcid.put(Sand.ANTI_ACID, Boolean.FALSE);
        isAffectedByAcid.put(Sand.SALT_FILTER, Boolean.TRUE);
        isAffectedByAcid.put(Sand.MAGMA, Boolean.FALSE);*/

        /*isBurnable.put(Sand.ACID, Boolean.FALSE);
        isBurnable.put(Sand.ERASER, Boolean.FALSE);
        isBurnable.put(Sand.FILTER, Boolean.FALSE);
        isBurnable.put(Sand.FIRE, Boolean.FALSE);
        isBurnable.put(Sand.OIL, Boolean.TRUE);
        isBurnable.put(Sand.OIL_TORCH, Boolean.FALSE);
        isBurnable.put(Sand.PLANT, Boolean.TRUE);
        isBurnable.put(Sand.SALT, Boolean.FALSE);
        isBurnable.put(Sand.SALT_WATER, Boolean.FALSE);
        isBurnable.put(Sand.SAND, Boolean.FALSE);
        isBurnable.put(Sand.SAND_TORCH, Boolean.FALSE);
        isBurnable.put(Sand.SMOKE, Boolean.FALSE);
        isBurnable.put(Sand.STEAM, Boolean.FALSE);
        isBurnable.put(Sand.TNT, Boolean.FALSE);
        isBurnable.put(Sand.TORCH, Boolean.FALSE);
        isBurnable.put(Sand.WALL, Boolean.FALSE);
        isBurnable.put(Sand.WATER, Boolean.FALSE);
        isBurnable.put(Sand.WATER_TAP_CLOSED, Boolean.FALSE);
        isBurnable.put(Sand.WATER_TAP_OPENED, Boolean.FALSE);
        isBurnable.put(Sand.WATER_TORCH, Boolean.FALSE);
        isBurnable.put(Sand.IRON, Boolean.FALSE);
        isBurnable.put(Sand.CIGARETTE, Boolean.FALSE);
        isBurnable.put(Sand.ACID_FILTER, Boolean.FALSE);
        isBurnable.put(Sand.ANTI_ACID, Boolean.FALSE);
        isBurnable.put(Sand.SALT_FILTER, Boolean.FALSE);
        isBurnable.put(Sand.MAGMA, Boolean.FALSE);*/
    }

}
