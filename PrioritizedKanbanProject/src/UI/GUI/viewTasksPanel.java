package UI.GUI;

import PrioritizedQueue.Task;
import PrioritizedQueue.prioritizedTaskManager;
import Utilities.ResponseT;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class viewTasksPanel extends JPanel {

    public viewTasksPanel(frameStruct struct) {
        GridBagLayout grid = new GridBagLayout();
        JPanel panel = new JPanel();
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.add(new JLabel("           "), BorderLayout.EAST);
        this.add(new JLabel("           "), BorderLayout.WEST);

        ResponseT<java.util.List<Task>> resp = struct.ptm.getTasks();

        if(resp.errorOccurred()){
            struct.errorLabel.setText(resp.getErrorMessage());
        }else{
            java.util.List<Task> tasks = resp.getValue();
            if(tasks.size() == 0){
                struct.errorLabel.setText("no tasks to display.");
            }else{
                struct.errorLabel.setText(" ");
                List<JComponent> components = new ArrayList<>();

                GridBagConstraints gbc = new GridBagConstraints();

//                grid.columnWidths = new int[]{200, 300, 400, 50};
                panel.setLayout(grid); // priority, name, deadline, delete

                gbc.ipadx = 25; // add padding
                gbc.ipady = 25;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(1, 1, 1, 1);

                for (Task task : tasks) {
                    JLabel priorityLabel = newJLabel(Integer.toString(task.getPriority()));
                    String name = task.getName();
                    JLabel nameLabel = newJLabel(name.length() > 15 ? name.substring(0,12) + "..." : name);
                    LocalDate deadline = task.getDeadline();
                    JLabel deadlineLabel = newJLabel(deadline == null ? "" : deadline.format(DateTimeFormatter.ofPattern("d/M/yyyy")));
                    components.add(priorityLabel);
                    components.add(nameLabel);
                    components.add(deadlineLabel);
                    JButton button = new JButton("-");
                    button.addActionListener(e -> {
                        struct.ptm.setCurrTask(task);
                        struct.replacePanel(new nextTaskPanel(struct, task,
                                () -> struct.replacePanel(new viewTasksPanel(struct))));
                    });
                    components.add(button);
                }
                components.add(0, newJLabel("priority"));
                components.add(1, newJLabel("name"));
                components.add(2, newJLabel("deadline"));
                components.add(3, newJLabel(" "));

                int i = 0;
                for (JComponent c : components) {
                    c.setBorder(BorderFactory.createLineBorder(new Color(0x57D1F7)));
                    gbc.gridy = i / 4;
                    gbc.gridx = i++ % 4;
                    panel.add(c, gbc);
                }
            }
        }

        panel.setAlignmentY(Component.TOP_ALIGNMENT);
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
