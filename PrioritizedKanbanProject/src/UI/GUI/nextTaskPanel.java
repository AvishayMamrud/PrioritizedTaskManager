package UI.GUI;

import PrioritizedQueue.Task;
import PrioritizedQueue.prioritizedTaskManager;
import Utilities.Response;
import Utilities.ResponseT;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class nextTaskPanel extends JPanel {
    public nextTaskPanel(frameStruct struct, Runnable backCommand) {
        ResponseT<Task> resp = struct.ptm.peekTask();
        if(resp.getValue() != null){
            Task task = resp.getValue();
            Runnable callBack;
            callBack = backCommand == null ? () -> struct.replacePanel(new nextTaskPanel(struct, null)) : backCommand;
            JPanel taskDetails = new JPanel();
            BoxLayout boxlayout = new BoxLayout(taskDetails, BoxLayout.Y_AXIS);
            taskDetails.setLayout(boxlayout);

            JPanel buttons = new JPanel();
            buttons.setLayout(new FlowLayout());

            this.setLayout(new BorderLayout());
            this.add(taskDetails, BorderLayout.CENTER);
            this.add(new JLabel("           "), BorderLayout.EAST);
            this.add(new JLabel("           "), BorderLayout.WEST);

            Font nameFont = new Font("MV Boli", Font.BOLD | Font.ITALIC, 35);
            Font descFont = new Font("MV Boli", Font.ITALIC, 20);
            Font deadlineFont = new Font("MV Boli", Font.BOLD | Font.ITALIC, 15);
            Font priorityFont = new Font("MV Boli", Font.BOLD, 30);

            JLabel name_label = new JLabel(task.getName());
            JTextArea desc_text_area = new JTextArea(task.getDescription(), 1,1);
            LocalDate date = task.getDeadline();
            String dateStr;
            if(date == null){
                dateStr = "No deadline specified.";
            }else{
                dateStr = "the deadline is on " + date.format(DateTimeFormatter.ofPattern("d/M/yyyy"));
            }
            JLabel deadline_label = new JLabel(dateStr);
            JLabel priority_label = new JLabel("priority - " + task.getPriority());

            desc_text_area.setLineWrap(true);
            desc_text_area.setWrapStyleWord(true);
            desc_text_area.setEditable(false);
            desc_text_area.setBackground(new Color(0xBDE2FB));
//        desc_text_area.


            taskDetails.registerKeyboardAction(e -> callBack.run(), KeyStroke.getKeyStroke('\b'), JComponent.WHEN_IN_FOCUSED_WINDOW);
            taskDetails.registerKeyboardAction(e -> {
                struct.errorLabel.setText("num of rows in description - " + desc_text_area.getRows());
                SwingUtilities.updateComponentTreeUI(struct.frame);
            }, KeyStroke.getKeyStroke('f'), JComponent.WHEN_IN_FOCUSED_WINDOW);

            name_label.setFont(nameFont);
            desc_text_area.setFont(descFont);
            deadline_label.setFont(deadlineFont);
            priority_label.setFont(priorityFont);

            name_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            desc_text_area.setAlignmentX(Component.CENTER_ALIGNMENT);
            deadline_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            priority_label.setAlignmentX(Component.CENTER_ALIGNMENT);

            name_label.setForeground(new Color(0x4E42F0));
            desc_text_area.setForeground(new Color(0x4E42F0));
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
                Response response = struct.ptm.removeTask(task);
                if(response.errorOccurred()){
                    struct.errorLabel.setText(response.getErrorMessage());
                }else{
                    callBack.run();
                    struct.errorLabel.setText("This task has been removed");
                }
                SwingUtilities.updateComponentTreeUI(struct.frame);
            });
            btn_edit.addActionListener(e -> struct.replacePanel(new editTaskPanel(struct, task, callBack)));
            btn_finish.addActionListener(e -> {
                Response response = struct.ptm.finishTask(task.getName());
                if(response.errorOccurred()){
                    struct.errorLabel.setText(response.getErrorMessage());
                }else{
                    callBack.run();
                    struct.errorLabel.setText("task finished! Great Job!!");
                }
                SwingUtilities.updateComponentTreeUI(struct.frame);
            });
            btn_next.addActionListener(e -> struct.replacePanel(new nextTaskPanel(struct, null)));

            taskDetails.add(name_label);
            taskDetails.add(desc_text_area);
            taskDetails.add(deadline_label);
            taskDetails.add(priority_label);
            taskDetails.add(buttons);
            buttons.add(btn_delete);
            buttons.add(btn_edit);
            buttons.add(btn_finish);
            buttons.add(btn_next);

            this.setBackground(new Color(0xBDE2FB));
            taskDetails.setBackground(new Color(0xBDE2FB));
            buttons.setBackground(new Color(0xBDE2FB));
        }
        if(resp.errorOccurred()){
            struct.errorLabel.setText(resp.getErrorMessage());
        }else{
            struct.errorLabel.setText(" ");
        }
    }

    public nextTaskPanel(frameStruct struct, Task task, Runnable backCommand) {
        Runnable callBack;
        callBack = backCommand == null ? () -> struct.replacePanel(new nextTaskPanel(struct, null)) : backCommand;

        JPanel taskDetails = new JPanel();
        BoxLayout boxlayout = new BoxLayout(taskDetails, BoxLayout.Y_AXIS);
        taskDetails.setLayout(boxlayout);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        this.setLayout(new BorderLayout());
        this.add(taskDetails, BorderLayout.CENTER);
        this.add(new JLabel("           "), BorderLayout.EAST);
        this.add(new JLabel("           "), BorderLayout.WEST);

        Font nameFont = new Font("MV Boli", Font.BOLD | Font.ITALIC, 35);
        Font descFont = new Font("MV Boli", Font.ITALIC, 20);
        Font deadlineFont = new Font("MV Boli", Font.BOLD | Font.ITALIC, 15);
        Font priorityFont = new Font("MV Boli", Font.BOLD, 30);

        JLabel name_label = new JLabel(task.getName());
        JTextArea desc_text_area = new JTextArea(task.getDescription(), 1,1);
        LocalDate date = task.getDeadline();
        String dateStr;
        if(date == null){
            dateStr = "No deadline specified.";
        }else{
            dateStr = "the deadline is on " + date.format(DateTimeFormatter.ofPattern("d/M/yyyy"));
        }
        JLabel deadline_label = new JLabel(dateStr);
        JLabel priority_label = new JLabel("priority - " + task.getPriority());

        desc_text_area.setLineWrap(true);
        desc_text_area.setWrapStyleWord(true);
        desc_text_area.setEditable(false);
        desc_text_area.setBackground(new Color(0xBDE2FB));
//        desc_text_area.


        taskDetails.registerKeyboardAction(e -> callBack.run(), KeyStroke.getKeyStroke('\b'), JComponent.WHEN_IN_FOCUSED_WINDOW);
        taskDetails.registerKeyboardAction(e -> {
            struct.errorLabel.setText("num of rows in description - " + desc_text_area.getRows());
            SwingUtilities.updateComponentTreeUI(struct.frame);
        }, KeyStroke.getKeyStroke('f'), JComponent.WHEN_IN_FOCUSED_WINDOW);

        name_label.setFont(nameFont);
        desc_text_area.setFont(descFont);
        deadline_label.setFont(deadlineFont);
        priority_label.setFont(priorityFont);

        name_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        desc_text_area.setAlignmentX(Component.CENTER_ALIGNMENT);
        deadline_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        priority_label.setAlignmentX(Component.CENTER_ALIGNMENT);

        name_label.setForeground(new Color(0x4E42F0));
        desc_text_area.setForeground(new Color(0x4E42F0));
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
            Response response = struct.ptm.removeTask(task);
            if(response.errorOccurred()){
                struct.errorLabel.setText(response.getErrorMessage());
            }else{
                callBack.run();
                struct.errorLabel.setText("This task has been removed");
            }
            SwingUtilities.updateComponentTreeUI(struct.frame);
        });
        btn_edit.addActionListener(e -> struct.replacePanel(new editTaskPanel(struct, task, callBack)));
        btn_finish.addActionListener(e -> {
            Response response = struct.ptm.finishTask(task.getName());
            if(response.errorOccurred()){
                struct.errorLabel.setText(response.getErrorMessage());
            }else{
                callBack.run();
                struct.errorLabel.setText("task finished! Great Job!!");
            }
            SwingUtilities.updateComponentTreeUI(struct.frame);
        });
        btn_next.addActionListener(e -> struct.replacePanel(new nextTaskPanel(struct, null)));

        taskDetails.add(name_label);
        taskDetails.add(desc_text_area);
        taskDetails.add(deadline_label);
        taskDetails.add(priority_label);
        taskDetails.add(buttons);
        buttons.add(btn_delete);
        buttons.add(btn_edit);
        buttons.add(btn_finish);
        buttons.add(btn_next);

        this.setBackground(new Color(0xBDE2FB));
        taskDetails.setBackground(new Color(0xBDE2FB));
        buttons.setBackground(new Color(0xBDE2FB));
//        struct.frame.add(this, BorderLayout.CENTER);
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
