package UI.GUI;

import PrioritizedQueue.prioritizedTaskManager;

import javax.swing.*;
import java.awt.*;

public class headerPanel extends JPanel {

    public headerPanel(frameStruct struct){
        this.setLayout(new BorderLayout());

        struct.errorLabel.setFont(new Font("MV Boli", Font.BOLD, 16));
        struct.errorLabel.setForeground(Color.red);
        struct.errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel label = new JLabel("Task Manager");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("MV Boli", Font.BOLD, 35));
        this.setBackground(new Color(0x739AF1));
        this.setOpaque(true);

        this.add(label, BorderLayout.NORTH);
        this.add(struct.errorLabel, BorderLayout.CENTER);
    }
}
