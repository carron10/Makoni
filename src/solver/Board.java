/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 *
 * @author Muleya
 */
class Board extends JPanel {

    int number_of_location = 13;
    private final Solver outer;
    Board board;
    JButton print;
    String towns[] = {"Harare", "Forbes", "Chiredzi", "Mvuma", "Masvingo", "Beitbridge", "Gwanda", "Bulawayo", "Plumtree", "Gweru", "Chegutu", "Chirundu","Ngundu"};
    int routes[][] = {{11, 0, 355}, {11, 10, 328}, {0, 1, 273}, {0, 10, 110}, {0, 3, 194}, {1, 2, 324}, {0, 2, 433}, {10, 9, 170}, {9, 7, 162}, {9, 3, 83}, {7, 8, 101}, {7, 6, 127}, {6, 5, 193}, {6, 4, 274}, {4, 3, 102},{5, 12, 193}, {4, 12, 95}, {4, 2, 193},{12,2,96}};

    JTextField viewer[] = new JTextField[13];

    Board(final Solver outer) {
        this.outer = outer;
        board = this;

    }

    public void addPopUp(JTextField field, int from, int to, int shortest) {
        //System.out.println(field.getText()+"  "+from.getName()+" "+to.getName());
        JPopupMenu menu = new JPopupMenu();
        Action all_routes = new AbstractAction("Show all routes") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // arbitrary source
                int s = from;
                int d = to;
                Set<String> paths = new TreeSet<>();
                getOuter().getGg().printAllPaths(s, d, paths);
                Set<String> routes = drawRoutes(s, d, paths, shortest);
                ShowAllRoutes sh = new ShowAllRoutes(board, getOuter());
                sh.showRoutes(towns[from], towns[to], routes);
                sh.setVisible(true);
            }
        };
        menu.add(all_routes);
        //menu.add(shotest_route);
        field.setComponentPopupMenu(menu);
        field.repaint();
        repaint();
    }

    Set<String> drawRoutes(int from, int to, Set<String> paths, int shortest) {
        //System.out.println("From " + from.getName() + " to " + to.getName() + " " + paths.toString());
        Iterator<String> dat = paths.iterator();
        int n = -1;
        Set<String> routes = new HashSet<>();
        while (dat.hasNext()) {
            String str = dat.next();
            String store = "";
            int t = 0;
            for (int i = 0; i < str.length(); i++) {
                if (i == 0 || i == str.length() - 1) {
                } else {
                    char s = str.charAt(i);
                    store += String.valueOf(s);
                }
            }
            store = store.replaceAll(" ", "");
            String[] m = store.split(",");
            String route = "";
            int ii = 0;
            for (int i = 0; i < m.length; i++) {
                int j = Integer.parseInt(m[i]);
                if (i == 0) {
                    ii = j;
                } else {
                    t += getDistance(ii, j);
                    if (route.isEmpty()) {
                        route = towns[ii] + " -->> " + getDistance(ii, j) + " -->> " + (towns[j]);

                    } else {
                        route += " -->> " + getDistance(ii, j) + "-->>" + (towns[j]);
                    }
                    ii = j;
                }
            }
            route += "==>>" + t + "<<";
            if (t == shortest) {
                route="Shortest"+route;
            }
            if (n != -1) {
                if (t < n) {
                    n = t;
                }
            } else {
                n = t;
            }
            routes.add(route);
        }
        return routes;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        doDrawing(g);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        setFocusable(false);
        this.setLayout(null);

        for (int i = 0; i < viewer.length; i++) {
            if (i != 0) {
                viewer[i] = new RoundedJTextField(5);
            } else {
                viewer[i] = new JTextField();
            }
            add(viewer[i]);

            viewer[i].setEditable(false);
            viewer[i].setText(" " + i + "");
            this.add(viewer[i]);
            //new Drag_city(viewer[i], this);
        }
        viewer[0].setBounds(429, 66, 20, 20);
        viewer[1].setBounds(588, 30, 20, 20);
        viewer[2].setBounds(704, 189, 20, 20);
        viewer[3].setBounds(510, 198, 20, 20);
        viewer[4].setBounds(592, 310, 20, 20);
        viewer[5].setBounds(755, 361, 20, 20);
        viewer[6].setBounds(648, 493, 20, 20);
        viewer[7].setBounds(528, 588, 20, 20);
        viewer[8].setBounds(709, 651, 20, 20);
        viewer[9].setBounds(410, 390, 20, 20);
        viewer[10].setBounds(328, 183, 20, 20);
        viewer[11].setBounds(163, 117, 20, 20);
        viewer[12].setBounds((592+755)/2, (310+361)/2, 20, 20);

        for (int[] route : routes) {
            int y = route[1];
            int x = route[0];
            getOuter().getGg().addEdge(x, y);
            getOuter().getGg().addEdge(y, x);
        }
    }

    public int getTownByName(String town) {
        for (int i = 0; i < towns.length; i++) {
            if (towns[i].equals(town)) {
                return i;
            }
        }
        return 0;
    }

    public int getLowest(int from, int to, Set<String> paths) {
        //System.out.println("From " + from.getName() + " to " + to.getName() + " " + paths.toString());
        Iterator<String> dat = paths.iterator();

        int n = -1;
        while (dat.hasNext()) {
            String str = dat.next();
            int t = 0;
            str = str.substring(1, str.length() - 1);
            str = str.replaceAll(" ", "");
            String[] m = str.split(",");
            int ii = 0;
            for (int i = 0; i < m.length; i++) {
                int j = Integer.parseInt(m[i]);
                if (i == 0) {
                    ii = j;
                } else {
                    t += getDistance(ii, j);
                    ii = j;
                }
            }
            if (n != -1) {
                if (t < n) {
                    n = t;
                }
            } else {
                n = t;
            }
        }
        return n;
    }

    public int getDistance(int from, int to) {
        for (int[] route : routes) {
            int y = route[1];
            int x = route[0];
            if ((x == from && y == to) || (x == to && y == from)) {
                return route[2];
            }
        }
        return 0;
    }

    public void doDrawing(Graphics g2) {

        Graphics2D g;
        g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(Color.blue);
        // g.drawLine(viewer[11].getX() + 10, viewer[11].getY() + 10, viewer[0].getX(), viewer[0].getY() + 10);
        for (int[] route : routes) {
            int y = route[1];
            int x = route[0];
            int distance = route[2];

            g.drawLine(viewer[x].getX() + 10, viewer[x].getY() + 10, viewer[y].getX(), viewer[y].getY() + 10);
            g.drawString(String.valueOf(distance), (viewer[x].getX() + viewer[y].getX()) / 2, (viewer[x].getY() + viewer[y].getY()) / 2);
        }

        g.setColor(Color.black);

        g.drawString("Key:", 955, 40);
        int n = 40;
        for (int i = 0; i < towns.length; i++) {
            n += 15;
            g.drawString(i + " - " + towns[i], 955, n);
        }

    }

    /**
     * @return the outer
     */
    public Solver getOuter() {
        return outer;
    }
}
// A directed graph using
// adjacency list representation
