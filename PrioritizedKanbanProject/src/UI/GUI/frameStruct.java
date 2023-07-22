package UI.GUI;

import PrioritizedQueue.prioritizedTaskManager;

import javax.swing.*;
import java.awt.*;

public class frameStruct {
    prioritizedTaskManager ptm = new prioritizedTaskManager(5);
    JFrame frame;
    JPanel panel;
    final JLabel errorLabel = new JLabel();

    public frameStruct(){
        this.frame = new StandardFrame();
        this.frame.setLayout(new BorderLayout());

        this.panel = new JPanel();
        frame.add(this.panel, BorderLayout.CENTER);
    }

    public void replacePanel(JPanel panel){

        this.frame.remove(this.panel);
        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());
        this.panel.add(new JScrollPane(panel));
        frame.add(this.panel, BorderLayout.CENTER);

        SwingUtilities.updateComponentTreeUI(frame);
    }
}
