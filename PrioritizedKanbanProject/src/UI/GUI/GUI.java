package UI.GUI;

import javax.swing.*;
import java.awt.*;

public class GUI{
    frameStruct struct;

    public static void main(String[] args){
        new GUI();
    }

    public GUI(){

        struct = new frameStruct();

        JPanel header = new headerPanel(struct, struct.errorLabel, struct.ptm);
        JPanel menu = new menuPanel(struct, struct.errorLabel, struct.ptm);

        JPanel scrollMenu = new JPanel();
        scrollMenu.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane(menu);
        scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        scrollMenu.add(scroll);

        struct.frame.add(header, BorderLayout.NORTH);
        struct.frame.add(scrollMenu, BorderLayout.WEST);

        struct.replacePanel(new viewTasksPanel(struct, struct.errorLabel, struct.ptm));
        struct.errorLabel.setText("Welcome!!!");
    }
}
