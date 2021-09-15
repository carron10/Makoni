/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Muleya
 */
public class Solver {

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(Solver::new);
    }
    JFrame frame;
    JPanel main;
    private Board board;
   

    private javax.swing.JMenu Action;
    private javax.swing.JMenu file;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
   // private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem locations;
   
    private Graph gg = new Graph(100);
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int xx = size.width;
    int yy = size.height;
    Solver solver;

    public Solver() {

        load_images();
        frame = new JFrame();
        main = new JPanel();
        init();
        solver = this;
    }

    /*void locations() {
        new locations(this).setVisible(true);
    }*/
    /**
     *
     * This is the initialise method
     *
     *
     */
    private void init() {
        jMenuBar1 = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        Action = new javax.swing.JMenu();
        locations = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        
        file.setText("File");
        jMenuBar1.add(file);

        Action.setText("Action");

        locations.setText("View shortest routes table");
        Action.add(locations);

        jMenuItem1.setText("Show routes in table");
        Action.add(jMenuItem1);
        locations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                    new shot_distance_table(getBoard(), solver).setVisible(true);
               
            }
        });
        jMenuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                    java.awt.EventQueue.invokeLater(() -> {
            new Project(solver).setVisible(true);
        });
               
            }
        });
        
        

        jMenuBar1.add(Action);
        frame.setJMenuBar(jMenuBar1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, xx, yy);
        //frame.setUndecorated(true);
        frame.setLayout(null);
        // frame.setResizable(false);
        board = new Board(this);

        frame.setLayout(null);
        frame.setContentPane(getBoard());

        // frame.add(main);
        frame.setVisible(true);

    }

    final class Drag1 {

        private volatile int screenX = 0;
        private volatile int screenY = 0;
        private volatile int myX = 0;
        private volatile int myY = 0;
        //private Point from;
        private final Board outer;

        public Drag1(JLabel label, final Board outer) {
            this.outer = outer;
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    screenX = e.getXOnScreen();
                    screenY = e.getYOnScreen();

                    myX = label.getX();
                    myY = label.getY();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            label.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int deltaX = e.getXOnScreen() - screenX;
                    int deltaY = e.getYOnScreen() - screenY;
                    label.setLocation(myX + deltaX, myY + deltaY);
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            });
        }
    }

    /**
     * @param args the command line arguments
     */
    private void load_images() {
        cancel = new ImageIcon(getClass().getResource("/solver/cancel.png"));
    }

    public ImageIcon cancel = null;

    /**
     * @return the gg
     */
    public Graph getGg() {
        return gg;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }
}
