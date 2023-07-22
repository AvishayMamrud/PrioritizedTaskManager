package UI.GUI;

import javax.swing.*;

public class StandardFrame extends JFrame {

    public StandardFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 512);
        this.setTitle("Task Manager");
        ImageIcon image = new ImageIcon("icon.png");
        this.setIconImage(image.getImage());
        this.setVisible(true);
    }
}
