package UI.GUI;

import PrioritizedQueue.Task;
import Utilities.Response;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class editTaskPanel extends JPanel{

    public editTaskPanel(frameStruct struct, Task task, Runnable backCommand) {
        this.setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        this.add(panel1, BorderLayout.CENTER);
        this.add(new JLabel("           "), BorderLayout.EAST);
        this.add(new JLabel("           "), BorderLayout.WEST);

        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 25; // add padding
        gbc.ipady = 25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(1, 1, 1, 1);
        panel1.setLayout(grid);

        Font font = new Font("MV Boli", Font.BOLD, 16);

        JLabel name_label = newJLabel("Name");
        JLabel desc_label = newJLabel("Description");
        JLabel deadline_label = newJLabel("Deadline");
        JLabel priority_label = newJLabel("Priority");

        JTextField name_textf = new JTextField(task.getName());
        JTextArea desc_textf = new JTextArea(task.getDescription());
        desc_textf.setLineWrap(true);
        desc_textf.setWrapStyleWord(true);
        DateFormat format = new SimpleDateFormat("d/M/yyyy");
        JFormattedTextField deadline_textf = new JFormattedTextField(format);
        LocalDate oldDeadline = task.getDeadline();
        String deadlineStr = oldDeadline == null ? "" : oldDeadline.format(DateTimeFormatter.ofPattern("d/M/yyyy"));
        deadline_textf.setText(deadlineStr);
        JTextField priority_textf = new JTextField(String.valueOf(task.getPriority()));

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
            LocalDate deadline = dl == null || dl.equals("") ? null : LocalDate.parse(dl, formatter);

            Response resp = struct.ptm.updateTask(task.getName(), task.getPriority(), name, desc, deadline, Integer.parseInt(priority));
            if(resp.errorOccurred()){
                struct.errorLabel.setText(resp.getErrorMessage());
                SwingUtilities.updateComponentTreeUI(struct.frame);
            }else{
                backCommand.run();
                struct.errorLabel.setText("task changed successfully!!");
                SwingUtilities.updateComponentTreeUI(struct.frame);
            }
        });
        JScrollPane scroll = new JScrollPane(desc_textf, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints panelGBC = new GridBagConstraints();

        panelGBC.weightx = 1;                    //I want to fill whole panel with JTextArea
        panelGBC.weighty = 1;                    //so both weights =1
        panelGBC.fill = GridBagConstraints.BOTH; //and fill is set to BOTH

        panel.add(scroll, panelGBC);
//        panel.setBackground(Color.gray);//this shouldn't be visible
        panel.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBorder(BorderFactory.createEmptyBorder());
        List<JComponent> components = new ArrayList<>();

        components.add(name_label);
        components.add(name_textf);
        components.add(desc_label);
        components.add(panel);
        components.add(deadline_label);
        components.add(deadline_textf);
        components.add(priority_label);
        components.add(priority_textf);

        int i = 0;
        for (JComponent c : components) {
            if(c == panel){
                desc_textf.setBorder(BorderFactory.createLineBorder(new Color(0x57D1F7))); // turquoise
                desc_textf.setFont(font);
            }else{
                c.setBorder(BorderFactory.createLineBorder(new Color(0x57D1F7))); // turquoise
                c.setFont(font);
            }
            gbc.weightx = ((i % 2) + 1) * ((i % 2) + 1);
            gbc.gridy = i / 2;
            gbc.gridx = i++ % 2;
            panel1.add(c, gbc);
        }
        button.setFont(font);
        gbc.gridy = i / 2;
        gbc.gridx = i % 2;
        gbc.gridwidth = 2;
        panel1.add(button, gbc);

        struct.errorLabel.setText(" ");
        panel1.setAlignmentY(Component.TOP_ALIGNMENT);
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
