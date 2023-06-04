package UI.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HeaderPanel extends JPanel {
    public HeaderPanel(ActionListener previous){
        JButton backButton = new JButton("Go Back");
        //JButton nextButton = new JButton("Go Forward");
        backButton.addActionListener(previous);
        //nextButton.addActionListener(next);
        this.setLayout(new BorderLayout());

        this.setBackground(Color.RED);
        backButton.setBackground(Color.MAGENTA);
        //nextButton.setBackground(Color.BLUE);

        JLabel label = new JLabel("Task Manager");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("MV Boli", Font.BOLD, 16));

        this.add(backButton, BorderLayout.WEST);
        this.add(label, BorderLayout.CENTER);
        //this.add(nextButton, BorderLayout.EAST);
//        this.setMinimumSize(new Dimension(label.getWidth() + nextButton.getWidth() + backButton.getWidth(), this.getHeight()));
    }
}
