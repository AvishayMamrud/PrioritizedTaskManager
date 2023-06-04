package UI.GUI;

import javax.swing.*;
import java.awt.*;

import PrioritizedQueue.Task;
import PrioritizedQueue.prioritizedTaskManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Utilities.Response;
import Utilities.ResponseT;

public class GUI{

    prioritizedTaskManager ptm;
    JFrame frame;
    JPanel panel = new JPanel();
    final JLabel errorLabel = new JLabel();

    public static void main(String[] args){
        new GUI().getMainWindow();
    }

    public JFrame getMainWindow(){
        ptm =  new prioritizedTaskManager(5);
        frame = new StandardFrame();
        frame.setLayout(new BorderLayout());
        JPanel header = getHeaderPanel();
        JPanel menu = getMenuPanel();
        frame.add(header, BorderLayout.NORTH);
        frame.add(menu, BorderLayout.WEST);
        frame.add(getViewTasksPanel(), BorderLayout.CENTER);
        errorLabel.setText("Welcome!!!");
        //frame.pack();
        return frame;
    }

    private JPanel getHeaderPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
//        panel.setBackground(new Color(0x739AF1));

//        JPanel innerPanel = new JPanel();
        errorLabel.setFont(new Font("MV Boli", Font.BOLD, 16));
        errorLabel.setForeground(Color.red);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel label = new JLabel("Task Manager");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("MV Boli", Font.BOLD, 35));
        label.setBackground(new Color(0x739AF1));
        label.setOpaque(true);

        panel.add(label, BorderLayout.NORTH);
        panel.add(errorLabel, BorderLayout.CENTER);
        return panel;
    }
    
    public JPanel getMenuPanel(){
        JPanel panel = new JPanel();
//        BorderLayout borderLayout = new BorderLayout();
//        panel.setLayout(borderLayout);

        JPanel panel2 = new JPanel();
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 15; // add padding
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel2.setLayout(grid);

        JPanel panel3 = new JPanel();
        Font font = new Font("MV Boli", Font.BOLD, 16);

        JButton button1 = new JButton("New Task");
        JButton button2 = new JButton("View All Tasks");
        JButton button3 = new JButton("Next Task");
        button1.addActionListener(e -> getAddTaskPanel());
        button2.addActionListener(e -> getViewTasksPanel());
        button3.addActionListener(e -> getNextTask());
        button1.setFont(font);
        button2.setFont(font);
        button3.setFont(font);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel2.add(button1, gbc);
        gbc.gridy = 1;
        panel2.add(button2, gbc);
        gbc.gridy = 2;
        panel2.add(button3, gbc);

        panel.add(panel2);
        panel.add(panel3);

        return panel;
    }

    private JPanel getNextTask() {
        frame.remove(panel);
        panel = new JPanel();
        ResponseT<Task> resp = ptm.peekTask();
        if(resp.getValue() != null){
            Task task = resp.getValue();
            BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(boxlayout);

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
                    getNextTask();
                    errorLabel.setText("task has been removed.");
                }
                SwingUtilities.updateComponentTreeUI(frame);
            });
            btn_edit.addActionListener(e -> {

            });
            btn_finish.addActionListener(e -> {
                Response response = ptm.finishTask(task.getName());
                if(response.errorOccurred()){
                    errorLabel.setText(response.getErrorMessage());
                }else{
                    getNextTask();
                    errorLabel.setText("task finished! Great Job!!");
                }
                SwingUtilities.updateComponentTreeUI(frame);
            });
            btn_next.addActionListener(e -> {
                getNextTask();
            });

            panel.add(name_label);
            panel.add(desc_label);
            panel.add(deadline_label);
            panel.add(priority_label);
            panel.add(btn_delete);
            panel.add(btn_edit);
            panel.add(btn_finish);
            panel.add(btn_next);

            panel.setBackground(new Color(0xBDE2FB));
            frame.add(panel, BorderLayout.CENTER);
        }
        if(resp.errorOccurred()){
            errorLabel.setText(resp.getErrorMessage());
        }else{
            errorLabel.setText("");
        }

        SwingUtilities.updateComponentTreeUI(frame);
        return panel;
    }

    private JPanel getAddTaskPanel() {
        frame.remove(panel);
        panel = new JPanel();
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 25; // add padding
        gbc.ipady = 25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(1, 1, 1, 1);
        panel.setLayout(grid);

        Font font = new Font("MV Boli", Font.BOLD, 16);

        JLabel name_label = newJLabel("Name");
        JLabel desc_label = newJLabel("Description");
        JLabel deadline_label = newJLabel("Deadline");
        JLabel priority_label = newJLabel("Priority");

        JTextField name_textf = new JTextField();
        JTextArea desc_textf = new JTextArea();
        DateFormat format = new SimpleDateFormat("d/M/yyyy");
        JFormattedTextField deadline_textf = new JFormattedTextField(format);
        deadline_textf.setText("12/11/2020");
        JTextField priority_textf = new JTextField();


//        Dimension dim = new Dimension(.getWidth(), name_label.getHeight());
//        name_textf.setSize(dim);
//        desc_textf.setSize(dim);
//        deadline_textf.setSize(dim);
//        priority_textf.setSize(dim);


        JButton button = new JButton("Submit");
        button.addActionListener(e -> {
            String name = name_textf.getText();
            String desc = desc_textf.getText();
            String dl = deadline_textf.getText();
            String priority = priority_textf.getText();
            if(name.equals("") || desc.equals("") || priority.equals("")){
                errorLabel.setText("some required fields are not filled.");
                SwingUtilities.updateComponentTreeUI(frame);
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate deadline = dl.equals("") ? null : LocalDate.parse(deadline_textf.getText(), formatter);

            Response resp = ptm.addTask(name_textf.getText(), desc_textf.getText(), deadline, Integer.parseInt(priority_textf.getText()));
            if(resp.errorOccurred()){
                errorLabel.setText(resp.getErrorMessage());
                SwingUtilities.updateComponentTreeUI(frame);
            }else{
                name_textf.setText("");
                desc_textf.setText("");
                deadline_textf.setText("");
                priority_textf.setText("");
                errorLabel.setText("task added successfully!!");
                SwingUtilities.updateComponentTreeUI(frame);
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
            panel.add(c, gbc);
        }
        button.setFont(font);
        gbc.gridy = i / 2;
        gbc.gridx = i % 2;
        gbc.gridwidth = 2;
        panel.add(button, gbc);

        errorLabel.setText("");
        panel.setAlignmentY(Component.TOP_ALIGNMENT);
        frame.add(panel, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(frame);
        return panel;
    }

    private JPanel getViewTasksPanel() {
        GridBagLayout grid = new GridBagLayout();
        ResponseT<List<Task>> resp = ptm.getTasks();
        frame.remove(panel);
        panel = new JPanel();
        if(resp.errorOccurred()){
            errorLabel.setText(resp.getErrorMessage());
        }else{
            List<Task> tasks = resp.getValue();
            if(tasks.size() == 0){
                errorLabel.setText("no tasks to display.");
            }else{
                errorLabel.setText("");
                List<JComponent> components = new ArrayList<>();

                GridBagConstraints gbc = new GridBagConstraints();

//                grid.columnWidths = new int[]{200, 300, 400, 50};
                panel.setLayout(grid); // priority, name, deadline, delete

                gbc.ipadx = 25; // add padding
                gbc.ipady = 25;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(1, 1, 1, 1);

                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
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
                        panel.remove(label1);
                        panel.remove(label2);
                        panel.remove(label3);
                        panel.remove(button);
                        SwingUtilities.updateComponentTreeUI(panel);
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
        frame.add(panel, BorderLayout.CENTER);
        SwingUtilities.updateComponentTreeUI(frame);
        return panel;
    }

    private JLabel newJLabel(String caption){
        JLabel label = new JLabel(caption);
        label.setForeground(new Color(0x4E42F0)); // darker blue
        label.setBackground(new Color(0xABCDF3)); // light blue
        label.setFont(new Font("MV Boli", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        //label.setMaximumSize(new Dimension(100, 100));
        label.setOpaque(true);
        return label;
    }
}
