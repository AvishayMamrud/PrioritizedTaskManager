package UI.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StandardFrame extends JFrame {

    public StandardFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 512);
        this.setTitle("Task Manager");
        ImageIcon image = new ImageIcon("icon.png");
        this.setIconImage(image.getImage());
//        JButton button = new JButton("click me");
//        button.setFont(new Font("MV Boli", Font.ITALIC | Font.BOLD, 20));
//        button.addActionListener(backAction);
//        button.setSize(10, 10);
//        this.add(button, BorderLayout.NORTH);
//        this.getContentPane().setBackground(new Color(0x00FF00));
        //this.pack();
        this.setVisible(true);
    }
}
