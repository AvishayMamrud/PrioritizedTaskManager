package UI.GUI;

import PrioritizedQueue.prioritizedTaskManager;

import javax.swing.*;
import java.awt.*;

public class frameStruct {
    prioritizedTaskManager ptm = new prioritizedTaskManager(5);
    JFrame frame;
    JPanel panel;
    final JLabel errorLabel;

    public frameStruct(){
        this.errorLabel = new JLabel(" ");
        this.frame = new StandardFrame();
        this.frame.setLayout(new BorderLayout());

        this.panel = new JPanel();
        frame.add(this.panel, BorderLayout.CENTER);
    }

    public void replacePanel(JPanel panel){

        this.frame.remove(this.panel);
        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());
        this.panel.add(new JScrollPane(
                                    panel,
                                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                        BorderLayout.CENTER);

        frame.add(this.panel, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(frame);
    }
}
