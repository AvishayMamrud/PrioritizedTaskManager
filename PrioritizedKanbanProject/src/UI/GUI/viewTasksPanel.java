package UI.GUI;

import PrioritizedQueue.Task;
import PrioritizedQueue.prioritizedTaskManager;
import Utilities.ResponseT;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class viewTasksPanel extends JPanel {

    public viewTasksPanel(frameStruct struct, JLabel errorLabel, prioritizedTaskManager ptm) {
        GridBagLayout grid = new GridBagLayout();
        ResponseT<java.util.List<Task>> resp = ptm.getTasks();

        if(resp.errorOccurred()){
            errorLabel.setText(resp.getErrorMessage());
        }else{
            java.util.List<Task> tasks = resp.getValue();
            if(tasks.size() == 0){
                errorLabel.setText("no tasks to display.");
            }else{
                errorLabel.setText("");
                List<JComponent> components = new ArrayList<>();

                GridBagConstraints gbc = new GridBagConstraints();

//                grid.columnWidths = new int[]{200, 300, 400, 50};
                this.setLayout(grid); // priority, name, deadline, delete

                gbc.ipadx = 25; // add padding
                gbc.ipady = 25;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(1, 1, 1, 1);

                for (Task task : tasks) {
                    JLabel label1 = newJLabel(Integer.toString(task.getPriority()));
                    JLabel label2 = newJLabel(task.getName());
                    LocalDate deadline = task.getDeadline();
                    JLabel label3 = newJLabel(deadline == null ? "" : deadline.toString());
                    components.add(label1);
                    components.add(label2);
                    components.add(label3);
                    JButton button = new JButton("-");
                    button.addActionListener(e -> {
                        System.out.println(ptm.removeTask(task));
                        this.remove(label1);
                        this.remove(label2);
                        this.remove(label3);
                        this.remove(button);
                        struct.errorLabel.setText("The task removed successfully");
                        SwingUtilities.updateComponentTreeUI(struct.frame);
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
                    this.add(c, gbc);
                }
            }
        }

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
