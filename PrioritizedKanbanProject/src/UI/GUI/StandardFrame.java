package UI.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class StandardFrame extends JFrame {

    public StandardFrame(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Task Manager");
        ImageIcon image = new ImageIcon("icon.png");
        this.setIconImage(image.getImage());
        this.setVisible(true);
    }
}

//public class StandardFrame extends JFrame implements KeyListener {
//
//    public StandardFrame(){
//
//        addKeyListener(this);
//
//        GraphicsDevice gd =
//                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//
//        if (gd.isFullScreenSupported()) {
//            setUndecorated(true);
//            gd.setFullScreenWindow(this);
//        } else {
//            System.err.println("Full screen not supported");
//            setSize(100, 100); // just something to let you see the window
//            setVisible(true);
//        }
//
//
//
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        this.setSize(600, 512);
//        this.setTitle("Task Manager");
//        ImageIcon image = new ImageIcon("icon.png");
//        this.setIconImage(image.getImage());
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        setVisible(false);
//        dispose();
//    }
//}
