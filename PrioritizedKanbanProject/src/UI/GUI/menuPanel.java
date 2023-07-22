package UI.GUI;

import PrioritizedQueue.prioritizedTaskManager;

import javax.swing.*;
import java.awt.*;

public class menuPanel extends JPanel {

    public menuPanel(frameStruct struct, JLabel errorLabel, prioritizedTaskManager ptm){

        JPanel panel2 = new JPanel();
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 15; // add padding
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 5, 5);
        panel2.setLayout(grid);

        JPanel panel3 = new JPanel();
        Font font = new Font("MV Boli", Font.BOLD, 16);

        JButton button1 = new JButton("New Task");
        JButton button2 = new JButton("View All Tasks");
        JButton button3 = new JButton("Next Task");
        button1.addActionListener(e -> struct.replacePanel(new addTaskPanel(struct, errorLabel, ptm)));
        button2.addActionListener(e -> struct.replacePanel(new viewTasksPanel(struct, errorLabel, ptm)));
        button3.addActionListener(e -> struct.replacePanel(new nextTaskPanel(struct, errorLabel, ptm)));
        button1.setFont(font);
        button2.setFont(font);
        button3.setFont(font);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel2.add(button1, gbc);
        gbc.gridy = 1;
        panel2.add(button2, gbc);
        gbc.gridy = 2;
        panel2.add(button3, gbc);

        this.add(panel2);
        this.add(panel3);
    }
}
