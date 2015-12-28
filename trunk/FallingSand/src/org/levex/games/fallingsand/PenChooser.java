/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PenChooser.java
 *
 * Created on 2010.12.04., 8:19:35
 */

package org.levex.games.fallingsand;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.tree.DefaultMutableTreeNode;
import org.lwjgl.Sys;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
//import javax.swing.JToggleButton;

/**
 *
 * @author Levex
 */
public class PenChooser extends javax.swing.JFrame {

    /** Creates new form PenChooser */
    public PenChooser() {
        initComponents();
        this.setTitle(Start.TITLE);
        //this.setLocation(Start.gc.getScreenWidth()/2-525, Start.gc.getScreenHeight()/2-250);
        jSlider1.setValue(4);
        jLabel1.setText("Select an element!");
        loadElements();
    }

     public final void loadElements() {
        DefaultMutableTreeNode treeNode1 = new DefaultMutableTreeNode(Start.TITLE);
        //boolean f = true;
        for(int j = 0; j < Properties.groups.size(); j++) { // Loop throught elements...
            //f = true;
            ArrayList<Integer> group_lmnts = Properties.group_elements.get(j); // Get the category
            DefaultMutableTreeNode dmt = new DefaultMutableTreeNode(Properties.groups.get(j));
            for(int i = 0; i < group_lmnts.size(); i++) {
                //if(!Properties.shouldnt_display.contains(i))
                    if(Start.gm == Start.GAME_MODES.RPG) {
                        if(Properties.showInRPG.get((int)Properties.ids.get(Properties.name.get(group_lmnts.get(i))))) {
                            dmt.add(new DefaultMutableTreeNode(Properties.name.get(group_lmnts.get(i))));
                        }
                    } else {
                        dmt.add(new DefaultMutableTreeNode(Properties.name.get(group_lmnts.get(i))));
                    }
                //}
            }
            treeNode1.add(dmt);
        }
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
    }

    public char sand = Properties.ids.get("concrete");
    public int sandSize = 4;
    public int sandShape = 0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jSlider1 = new javax.swing.JSlider();
        jComboBox1 = new javax.swing.JComboBox();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jScrollPane2.setViewportView(jTree2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Options");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Materials");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sand");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Salt");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Lime");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Oil");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Gas");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Smoke");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Steam");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Water");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Acid");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Lye");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Taps");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sand tap");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Salt tap");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Lime tap");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Oil tap");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Gas tap");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Smokestack");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Steam tap");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Water tap");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Acid tap");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Lye tap");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Builds");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Concrete");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Iron");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Wood");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Plant");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Vine");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Ice");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Fluid Filter");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Acid Filter");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Salt Filter");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Equipments");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Fire torch");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Volcano");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Geyser");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Turbine");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Hoover");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Tooles");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Match");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("TNT");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Eraser");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Utilities");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Saver");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Loader");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Tester");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Quit");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jSlider1.setMajorTickSpacing(1);
        jSlider1.setMaximum(64);
        jSlider1.setMinimum(4);
        jSlider1.setMinorTickSpacing(1);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                handleStateChange(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Disc", "Square", "Triangle", "Circle" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                penShape(evt);
            }
        });

        label1.setText("Handler's form and size:");

        label2.setText("Elements of "+Start.TITLE);

        jLabel1.setText(Properties.info.get(sand));

        jButton1.setText("Remap Keys");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        //System.err.println(evt.getPath().toString());
        String path = evt.getPath().toString();
        path = path.replace("]", "");
        String[] pp = path.split(",");
        String pa = pp[pp.length-1];
        pa = pa.trim();
        //System.err.println(pa);
        try {
            sand = Properties.ids.get(pa);
            if(sand == Properties.ids.get("eraser")) {
                jLabel1.setText(Properties.info.get((int) sand));
                sand = 0;
                return;
            }
        } catch(Exception e) {
            ;
        }
        /*//Options
        //Materials
        if(path.endsWith("Sand")) {
            sand = Sand.SAND;
        } else if(path.endsWith("Salt")) {
            sand = Sand.SALT;
        } else if(path.endsWith("Lime")) {
            sand = Sand.LIME;
        } else if(path.endsWith("Oil")) {
            sand = Sand.OIL;
        } else if(path.endsWith("Gas")) {
            sand = Sand.GAS;
        } else if(path.endsWith("Smoke")) {
            sand = Sand.SMOKE;
        } else if(path.endsWith("Steam")) {
            sand = Sand.STEAM;
        } else if(path.endsWith("Water")) {
            sand = Sand.WATER;
        } else if(path.endsWith("Seed")) {
            sand = Sand.SEED;
        } else if(path.endsWith("Acid")) {
            sand = Sand.ACID;
        } else if(path.endsWith("Lye")) {
            sand = Sand.LYE;
            sand = Sand.ANTI_ACID;
            //Taps
        } else if(path.endsWith("Sand tap")) {
            sand = Sand.SAND_TAP;
//            sand = Sand.SAND_TORCH;
        } else if(path.endsWith("Salt tap")) {
            sand = Sand.SALT_TAP;
        } else if(path.endsWith("Lime tap")) {
            sand = Sand.LIME_TAP;
        } else if(path.endsWith("Oil tap")) {
            sand = Sand.OIL_TAP;
            //sand = Sand.OIL_TORCH;
        } else if(path.endsWith("Gas tap")) {
            sand = Sand.GAS_TAP;
        } else if(path.endsWith("Smokestack")) {
            sand = Sand.SMOKE_TAP;
        } else if(path.endsWith("Steam tap")) {
            sand = Sand.ANTI_ACID;
        } else if(path.endsWith("Water tap")) {
            sand = Sand.WATER_TAP;
  //          sand = Sand.WATER_TORCH;
        } else if(path.endsWith("Acid tap")) {
            sand = Sand.ACID_TAP;
        } else if(path.endsWith("Lye tap")) {
            sand = Sand.LYE_TAP;
            //Builds
        } else if(path.endsWith("Concrete")) {
            sand = Sand.CONCRETE;
            //sand = Sand.WALL;
        } else if(path.endsWith("Iron")) {
            sand = Sand.IRON;
        } else if(path.endsWith("Wood")) {
            sand = Sand.WOOD;
        } else if(path.endsWith("Plant")) {
            sand = Sand.PLANT;
        } else if(path.endsWith("Vine")) {
            sand = Sand.VINE;
        } else if(path.endsWith("Ice")) {
            sand = Sand.ICE;
        } else if(path.endsWith("Fluid Filter")) {
            sand = Sand.FLUID_FILTER;
            //sand = Sand.FILTER;
        } else if(path.endsWith("Acid Filter")) {
            sand = Sand.ACID_FILTER;
        } else if(path.endsWith("Salt Filter")) {
            sand = Sand.SALT_FILTER;
            //Equipments
        } else if(path.endsWith("Fire torch")) {
            sand = Sand.FIRE_TORCH;
            //sand = Sand.FIRE;
        } else if(path.endsWith("Volcano")) {
            sand = Sand.VOLCANO;
            //sand = Sand.MAGMA;
        } else if(path.endsWith("Geyser")) {
            sand = Sand.GEYSER;
        } else if(path.endsWith("Turbine")) {
            sand = Sand.TURBINE;
        } else if(path.endsWith("Hoover")) {
            sand = Sand.HOOVER;
            //Tools
        } else if(path.endsWith("Match")) {
            sand = Sand.MATCH;
            //sand = Sand.CIGARETTE;
        } else if(path.endsWith("TNT")) {
            sand = Sand.TNT;
        } else if(path.endsWith("Eraser")) {
            sand = Sand.ERASER;
            //Utilities
        } else if(path.endsWith("Saver")) {
            try {
                Start.save();
            } catch (Exception ex) {
                Logger.getLogger(PenChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(path.endsWith("Loader")) {
            try {
                Start.load();
            } catch (Exception ex) {
                Logger.getLogger(PenChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(path.endsWith("Tester")) {
            try {
                Canvas.generate((int) System.currentTimeMillis());
            } catch (SlickException ex) {
                Logger.getLogger(PenChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(path.endsWith("Quit")) {
            int r = JOptionPane.showConfirmDialog(rootPane, "Are you sure?", "Sandie", JOptionPane.YES_NO_OPTION);
            if(r == JOptionPane.YES_OPTION) {
                System.gc();
                System.exit(0);
            }
        }*/
        jLabel1.setText(Properties.info.get((int) sand));
    }//GEN-LAST:event_jTree1ValueChanged

    private void handleStateChange(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_handleStateChange
        JSlider source = (JSlider)evt.getSource();
        sandSize = source.getValue();
    }//GEN-LAST:event_handleStateChange

    private void penShape(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_penShape
        if(evt.getItem() == "Disc") {
            System.err.println("Disc");
            sandShape = 0;
        } else if(evt.getItem() == "Square") {
            System.err.println("Square");
            sandShape = 1;
        } else if(evt.getItem() == "Triangle") {
            System.err.println("Triangle");
            sandShape = 2;
        } else if(evt.getItem() == "Circle") {
            System.err.println("Circle");
            sandShape = 3;
        }
    }//GEN-LAST:event_penShape

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Sys.alert("Sandye", "Future work.");
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PenChooser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTree jTree2;
    private java.awt.Label label1;
    private java.awt.Label label2;
    // End of variables declaration//GEN-END:variables

}