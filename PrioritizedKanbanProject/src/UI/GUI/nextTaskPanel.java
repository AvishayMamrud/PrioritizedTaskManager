package UI.GUI;

import PrioritizedQueue.Task;
import PrioritizedQueue.prioritizedTaskManager;
import Utilities.Response;
import Utilities.ResponseT;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class nextTaskPanel extends JPanel {

    public nextTaskPanel(frameStruct struct, JLabel errorLabel, prioritizedTaskManager ptm) {
        ResponseT<Task> resp = ptm.peekTask();
        if(resp.getValue() != null){
            Task task = resp.getValue();
            BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(boxlayout);

            Font nameFont = new Font("MV Boli", Font.BOLD | Font.ITALIC, 35);
            Font descFont = new Font("MV Boli", Font.ITALIC, 20);
            Font deadlineFont = new Font("MV Boli", Font.BOLD | Font.ITALIC, 15);
            Font priorityFont = new Font("MV Boli", Font.BOLD, 30);

            JLabel name_label = new JLabel();
            JLabel desc_label = new JLabel();
            JLabel deadline_label = new JLabel();
            JLabel priority_label = new JLabel();

            name_label.setText(task.getName());
            desc_label.setText(task.getDescription());
            LocalDate date = task.getDeadline();
            if(date == null){
                deadline_label.setText(" ");
            }else{
                deadline_label.setText("the deadline is on " + date.toString());
            }
            priority_label.setText("priority - " + task.getPriority());

            name_label.setFont(nameFont);
            desc_label.setFont(descFont);
            deadline_label.setFont(deadlineFont);
            priority_label.setFont(priorityFont);

            name_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            desc_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            deadline_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            priority_label.setAlignmentX(Component.CENTER_ALIGNMENT);

            name_label.setForeground(new Color(0x4E42F0));
            desc_label.setForeground(new Color(0x4E42F0));
            deadline_label.setForeground(new Color(0x4E42F0));
            priority_label.setForeground(new Color(0x4E42F0));


            JButton btn_delete = new JButton("Delete");
            JButton btn_edit = new JButton("Edit");
            JButton btn_finish = new JButton("Finish");
            JButton btn_next = new JButton("Next");
            btn_delete.setFont(descFont);
            btn_edit.setFont(descFont);
            btn_finish.setFont(descFont);
            btn_next.setFont(descFont);
            btn_delete.addActionListener(e -> {
                Response response = ptm.removeTask(task);
                if(response.errorOccurred()){
                    errorLabel.setText(response.getErrorMessage());
                }else{
                    new nextTaskPanel(struct, new JLabel(), ptm);
                    errorLabel.setText("task has been removed.");
                }
                SwingUtilities.updateComponentTreeUI(struct.frame);
            });
            btn_edit.addActionListener(e -> {

            });
            btn_finish.addActionListener(e -> {
                Response response = ptm.finishTask(task.getName());
                if(response.errorOccurred()){
                    errorLabel.setText(response.getErrorMessage());
                }else{
                    new nextTaskPanel(struct, new JLabel(), ptm);
                    errorLabel.setText("task finished! Great Job!!");
                }
                SwingUtilities.updateComponentTreeUI(struct.frame);
            });
            btn_next.addActionListener(e -> {
                struct.replacePanel(new nextTaskPanel(struct, errorLabel, ptm));
            });

            this.add(name_label);
            this.add(desc_label);
            this.add(deadline_label);
            this.add(priority_label);
            this.add(btn_delete);
            this.add(btn_edit);
            this.add(btn_finish);
            this.add(btn_next);

            this.setBackground(new Color(0xBDE2FB));
            struct.frame.add(this, BorderLayout.CENTER);
        }
        if(resp.errorOccurred()){
            errorLabel.setText(resp.getErrorMessage());
        }else{
            errorLabel.setText("");
        }
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
