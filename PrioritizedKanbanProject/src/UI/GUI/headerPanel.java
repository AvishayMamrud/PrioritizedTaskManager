package UI.GUI;

import PrioritizedQueue.prioritizedTaskManager;

import javax.swing.*;
import java.awt.*;

public class headerPanel extends JPanel {

    public headerPanel(frameStruct struct, JLabel errorLabel, prioritizedTaskManager ptm){
        this.setLayout(new BorderLayout());

        errorLabel.setFont(new Font("MV Boli", Font.BOLD, 16));
        errorLabel.setForeground(Color.red);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel label = new JLabel("Task Manager");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("MV Boli", Font.BOLD, 35));
        label.setBackground(new Color(0x739AF1));
        label.setOpaque(true);

        this.add(label, BorderLayout.NORTH);
        this.add(errorLabel, BorderLayout.CENTER);
    }
}
