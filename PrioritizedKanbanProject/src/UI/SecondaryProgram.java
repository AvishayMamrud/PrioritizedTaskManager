package UI;

import PrioritizedQueue.Task;
import PrioritizedQueue.prioritizedTaskManager;
import Utilities.Response;
import Utilities.ResponseT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class SecondaryProgram {

    private final Scanner scan = new Scanner(System.in);
    private final prioritizedTaskManager ptm = new prioritizedTaskManager(5);

    private void displayTask() {
        while(true){
            try{
                ResponseT<Task> resp = ptm.peekTask();
                if(resp.errorOccurred()) {
                    System.out.println(resp.getErrorMessage());
                    return;
                }
                Task task = resp.getValue();
                System.out.println("\n" + task.getName() + "'s details:");
                System.out.println(task.toString());
                int index = 0;
                System.out.println("\nmore operations:");
                System.out.println(++index + ". rename task");
                System.out.println(++index + ". change description");
                System.out.println(++index + ". change deadline");
                System.out.println(++index + ". change priority");
                System.out.println(++index + ". delete task");
                System.out.println(++index + ". next task");
                System.out.println(++index + ". finish task");

                displayBackOption();
                int choice = waitForNum(0, index, "choice");

                if(choice == 0){
                    return;
                }else{
                    Response resp2;
                    switch (choice) {
                        case 1: // rename task
                            String newTaskName = waitForString("new name");
                            resp2 = ptm.setTaskName(task.getName(), newTaskName);
                            break;
                        case 2: // change description
                            resp2 = ptm.setTaskDescription(task.getName(), waitForString("new description"));
                            break;
                        case 3: // change deadline
                            resp2 = ptm.setTaskDeadline(task.getName(), waitForDate("new deadline"));
                            break;
                        case 4: // change priority
                            resp2 = ptm.setTaskPriority(task.getName(), waitForNum(0, 5, "priority"));
                            break;
                        case 5: // delete task
                            resp2 = ptm.removeTask(task.getName());
                            break;
                        case 6: // next task
                            continue;
                        case 7: // finish task
                            resp2 = ptm.finishTask(task.getName());
                            break;
                        default:
                            resp2 = new Response();
                    }
                    if(resp2.errorOccurred())
                        System.out.println(resp2.getErrorMessage());
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void displayBackOption(){
        System.out.println("\nto go back press 0");
    }

    private int waitForNum(int min, int max, String wish){
        System.out.print("\nselect a " + wish + " in the range ("+ min + "-" + max + "): ");
        int num = -1;
        do{
            try {
                num = Integer.parseInt(scan.nextLine());
            } catch (Exception ignored) {
                System.out.println("\nstill waiting for a number...: ");
            }
        }while(!(num <= max && num >= min));
        return num;
    }

    private String waitForString(String wish){
        System.out.print("\ntype a " + wish + ": ");
        return scan.nextLine();
    }

    private LocalDate waitForDate(String wish) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate date = null;
        while(date == null){
            System.out.print("type in a " + wish + " date (d/M/yyyy): ");
            String userInput = scan.nextLine();
            try{
                date = LocalDate.parse(userInput, dateFormat);
            }catch(Exception e){
                System.out.println("\nthe date does not match the pattern (d/M/yyyy)\n");
            }
        }
        return date;
    }

    public void addTask(){
        String taskName = waitForString("task name");
        String description = waitForString("task description");
        LocalDate deadline = waitForDate("task deadline");
        int priority = waitForNum(1, 5, "task priority");
        ptm.addTask(taskName, description, deadline, priority);
    }

    public void displayMainMenu(){
        while(true) {
            try{
                int index = 0;
                System.out.println("\nselect your operation:");
                System.out.println(++index + ". add task");
                System.out.println(++index + ". next task");
                System.out.println(++index + ". view all tasks");

                displayBackOption();
                int choice = waitForNum(0, index, "choice");
                switch (choice) {
                    case 0:
                        return;
                    case 1: // add a new task
                        addTask();
                        System.out.println("\nthe task inserted successfully!!");
                        break;
                    case 2: // next task
                        displayTask();
                        break;
                    case 3: // view all tasks
                        displayAllTasks();
                        break;
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void displayAllTasks(){
        ResponseT<List<Task>> allTasks = ptm.getTasks();
        if(allTasks.errorOccurred())
            System.out.println(allTasks.getValue());
        System.out.printf("\n%10s  |  %15s  |  %10s  |  %s\n", "name", "description", "deadline", "priority");
        for (Task task : allTasks.getValue()) {
            System.out.print("------------+-------------------+--------------+----------\n");
            String name = task.getName();
            name = name.length() <= 10 ? name : name.substring(0, 10);
            String desc = task.getDescription();
            desc = desc.length() <= 15 ? desc : desc.substring(0, 15);
            System.out.printf("%10s  |  %15s  |  %10s  |  %1d\n", name, desc, task.getDeadline().toString(), task.getPriority());
        }
    }

    public void test(){
        ptm.addTask("1descrilrjkhsrdescrilrjkhsrdescrilrjkhsrdescrilrjkhsr", "descrilrjkhsreghbbhiption1234", LocalDate.now().plusDays(1), 5);
        ptm.addTask("2descrition123descrition123descrition123descrition123", "descrition123", LocalDate.now().plusDays(2), 2);
        ptm.addTask("3descrition124descrition124descrition124descrition124", "descrition124", LocalDate.now().plusDays(3), 1);
        ptm.addTask("4descrition134descrition134descrition134descrition134", "descrition134", LocalDate.now().plusDays(4), 4);
        ptm.addTask("5descrilrjkhsrdescrilrjkhsrdescrilrjkhsrdescrilrjkhsr", "descrilrjkhsreghbbhiption234", LocalDate.now().plusDays(5), 5);
        ptm.addTask("6descrilrjkhsrdescrilrjkhsrdescrilrjkhsrdescrilrjkhsr", "descrilrjkhsreghbbhiptio1234", LocalDate.now().plusDays(5), 4);
    }

    public static void main(String[] args){
        SecondaryProgram program = new SecondaryProgram();

        //--------------------------------------
        program.test();
        //--------------------------------------

        program.displayMainMenu();
    }
}
