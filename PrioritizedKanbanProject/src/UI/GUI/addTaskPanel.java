package UI.GUI;

import Utilities.Response;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class addTaskPanel extends JPanel{

    public addTaskPanel(frameStruct struct) {
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 25; // add padding
        gbc.ipady = 25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(1, 1, 1, 1);
        this.setLayout(grid);

        Font font = new Font("MV Boli", Font.BOLD, 16);

        JLabel name_label = newJLabel("Name");
        JLabel desc_label = newJLabel("Description");
        JLabel deadline_label = newJLabel("Deadline");
        JLabel priority_label = newJLabel("Priority");

        JTextField name_textf = new JTextField();
        JTextArea desc_textf = new JTextArea();
        desc_textf.setLineWrap(true);
        desc_textf.setWrapStyleWord(true);
        DateFormat format = new SimpleDateFormat("d/M/yyyy");
        JFormattedTextField deadline_textf = new JFormattedTextField(format);
        deadline_textf.setText("12/11/2020");
        JTextField priority_textf = new JTextField();

        JButton button = new JButton("Submit");
        button.addActionListener(e -> {
            String name = name_textf.getText();
            String desc = desc_textf.getText();
            String dl = deadline_textf.getText();
            String priority = priority_textf.getText();
            if(name.equals("") || desc.equals("") || priority.equals("")){
                struct.errorLabel.setText("some required fields are not filled.");
                SwingUtilities.updateComponentTreeUI(struct.frame);
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate deadline = dl.equals("") ? null : LocalDate.parse(deadline_textf.getText(), formatter);

            Response resp = struct.ptm.addTask(name_textf.getText(), desc_textf.getText(), deadline, Integer.parseInt(priority_textf.getText()));
            if(resp.errorOccurred()){
                struct.errorLabel.setText(resp.getErrorMessage());
                SwingUtilities.updateComponentTreeUI(struct.frame);
            }else{
                name_textf.setText("");
                desc_textf.setText("");
                deadline_textf.setText("");
                priority_textf.setText("");
                struct.errorLabel.setText("task added successfully!!");
                SwingUtilities.updateComponentTreeUI(struct.frame);
            }
        });

        List<JComponent> components = new ArrayList<>();

        components.add(name_label);
        components.add(name_textf);
        components.add(desc_label);
        components.add(desc_textf);
        components.add(deadline_label);
        components.add(deadline_textf);
        components.add(priority_label);
        components.add(priority_textf);

        int i = 0;
        for (JComponent c : components) {
            c.setBorder(BorderFactory.createLineBorder(new Color(0x57D1F7))); // turquoise
            c.setFont(font);
            gbc.weightx = ((i % 2) + 1) * ((i % 2) + 1);
            gbc.gridy = i / 2;
            gbc.gridx = i++ % 2;
            this.add(c, gbc);
        }
        button.setFont(font);
        gbc.gridy = i / 2;
        gbc.gridx = i % 2;
        gbc.gridwidth = 2;
        this.add(button, gbc);

        struct.errorLabel.setText("");
        this.setAlignmentY(Component.TOP_ALIGNMENT);
    }

    private JLabel newJLabel(String caption){
        JLabel label = new JLabel(caption);
        label.setForeground(new Color(0x4E42F0)); // darker blue
        label.setBackground(new Color(0xABCDF3)); // light blue
        label.setFont(new Font("MV Boli", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        return label;
    }
}
