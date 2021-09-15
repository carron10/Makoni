/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import solver.*;

/**
 *
 * @author Muleya
 */
public class Project extends JFrame {

    public Solver solver;

    public Project(solver.Solver solve) {
        this.solver = solve;
        init();
    }

    private void init() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        
        jPanel1.setLayout(null);

        jScrollPane1.setViewportView(jPanel1);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
        );


       
        try {
            Map<location, List<routes>> data = new read_data().getData();
int j=0;
int prv=0;
            for (location loc : data.keySet()) {
                Iterator<routes> n = data.get(loc).iterator();
                int i = 0;
                
                String infor[][] = new String[data.get(loc).size()][4];
                int  distance = 0;
                
                while (n.hasNext()) {
                    routes k = n.next();
                    //infor[i][0] = String.valueOf(k.getNumber());
                   
                   int frm=k.getFrom(),to=k.getTo();
                    Set<String> paths = new TreeSet<>();
                    solver.getGg().printAllPaths(frm, to, paths);
                    infor[i][0] = k.getFrom()+"-"+k.getTo();
                    infor[i][1] = k.getNum_vehicles();
                    infor[i][2] = String.valueOf(k.getTonnes());
                    
                    infor[i][3] = String.valueOf(distance+solver.getBoard().getLowest(frm, to, paths));
                     distance = distance + solver.getBoard().getLowest(frm, to, paths);
                    if(k.getTo()==0){
                        Set<String> pathsz = new TreeSet<>();
                        solver.getGg().printAllPaths(0, 1, pathsz);
                         distance = solver.getBoard().getLowest(0, 1, pathsz);
                    }
                    i++;
                }
                String name = loc.getName();
                
                this.drawTables(infor,name,prv,i*25);
                prv=prv+(i*34);
                j++;
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify                    

    void drawTables(Object table[][],String name,int num,int height) {
        JLabel label=new JLabel(name);
        label.setBounds(30, num, 300, 20);
        label.setForeground(Color.red);
        JTable jTable1 = new javax.swing.JTable();
      javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                (Object[][]) table,
                new String[]{
                    "Client","No. of vehicles", "Quantity(Tonnes)", "Distance(Km)"
                }
        ));
        jTable1.setEnabled(false);
        jScrollPane2.setViewportView(jTable1);
        jScrollPane2.setBounds(0,num+25,850, height);
        jPanel1.setPreferredSize(new Dimension(850,num+height+25));
        jPanel1.add(label);
         jPanel1.add(jScrollPane2);
        pack();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
   
}

class routes {

    private int number;
    private int links;
    private int triaxle;
    private int tonnes, from,to;
    private String num_vehicles;

    public routes(int number, int triaxle, int links, int tonnes, int to, int distance) {
        this.setLinks(links);
        this.setNumber(number);
        this.setTo(to);
        this.setTonnes(tonnes);
        this.setTriaxle(triaxle);
        this.setFrom(distance);
        this.setNum_vehicles(links+" links and "+triaxle+" triaxles");
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the links
     */
    public int getLinks() {
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(int links) {
        this.links = links;
    }

    /**
     * @return the triaxle
     */
    public int getTriaxle() {
        return triaxle;
    }

    /**
     * @param triaxle the triaxle to set
     */
    public void setTriaxle(int triaxle) {
        this.triaxle = triaxle;
    }

    /**
     * @return the to
     */
    public int getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(int to) {
        this.to = to;
    }

    /**
     * @return the tonnes
     */
    public int getTonnes() {
        return tonnes;
    }

    /**
     * @param tonnes the tonnes to set
     */
    public void setTonnes(int tonnes) {
        this.tonnes = tonnes;
    }

    /**
     * @return the from
     */
    public int getDistance() {
        return getFrom();
    }

    /**
     * @param distance the from to set
     */
    public void setFrom(int distance) {
        this.from = distance;
    }

    /**
     * @return the num_vehicles
     */
    public String getNum_vehicles() {
        return num_vehicles;
    }

    /**
     * @param num_vehicles the num_vehicles to set
     */
    public void setNum_vehicles(String num_vehicles) {
        this.num_vehicles = num_vehicles;
    }

    /**
     * @return the from
     */
    public int getFrom() {
        return from;
    }

}

class location {

    private int tonnes;
    private String name;

    public location(int tonnes, String name) {
        this.setName(name);
        this.setTonnes(tonnes);
    }

    /**
     * @return the tonnes
     */
    public int getTonnes() {
        return tonnes;
    }

    /**
     * @param tonnes the tonnes to set
     */
    private void setTonnes(int tonnes) {
        this.tonnes = tonnes;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    private void setName(String name) {
        this.name = name;
    }
}
