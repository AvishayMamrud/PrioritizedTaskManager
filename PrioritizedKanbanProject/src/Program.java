import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    private final BoardController bc;
    private final Scanner scan = new Scanner(System.in);

    public Program(){
        bc = BoardController.getInstance();
    }

    public void displayMainMenu(){
        while(true) {
            try{
                System.out.println("\nselect your operation:");
                System.out.println("1. add board");
                System.out.println("2. view my boards");

                displayBackOption();
                int choice = waitForNum(0, 2, "choice");
                switch (choice) {
                    case 0:
                        return;
                    case 1: //add a new board
                        bc.addBoard(waitForString("new board's name"));
                        break;
                    case 2: //view my boards
                        displayBoardsMenu();
                        break;
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void displayBoardsMenu(){
        while(true){
            try{
                List<String> boards = new ArrayList<>(bc.getBoardNames());
                System.out.println("\nyour boards:");
                int index = 0;
                for (String boardName : boards) {
                    System.out.println(++index + ". " + boardName);
                }
                displayBackOption();
                int choice = waitForNum(0, index, "choice");
                if(choice == 0){
                    return;
                }else{
                    displayBoard(boards.get(choice - 1));
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void displayBoard(String boardName){
        while(true){
            try{
                List<String> colNames = new ArrayList<>(bc.getColumnsNames(boardName));

                System.out.println("\n" + boardName + "'s columns:");
                int index = 0;
                for (String colName : colNames) {
                    System.out.println(++index + ". " + colName);
                }
                System.out.println("\nmore operations:");
                System.out.println(++index + ". add column");
                System.out.println(++index + ". reposition column");
                System.out.println(++index + ". rename board");
                System.out.println(++index + ". delete board");

                displayBackOption();
                int choice = waitForNum(0, index, "choice");

                if(choice == 0) {
                    return;
                }else if (choice > colNames.size()) {
                    switch (choice - colNames.size()){
                        case 1: //add column
                            bc.addColumn(boardName, waitForString("column name"));
                            break;
                        case 2: //reposition column
                            bc.repositionColumn(boardName, colNames.get(waitForNum(1, colNames.size(), "column index") - 1), waitForNum(1, colNames.size(), "new position") - 1);
                            break;
                        case 3: //rename board
                            String newBoardName = waitForString("new board name");
                            bc.setBoardName(boardName, newBoardName);
                            boardName = newBoardName;
                            break;
                        case 4: //delete board
                            bc.removeBoard(boardName);
                            return;
                    }
                }else{
                    displayColumn(boardName, colNames.get(choice - 1));
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void displayColumn(String boardName, String columnName){
        while(true){
            try{
                List<String> tasksNames = new ArrayList<>(bc.getTasksNames(boardName, columnName));

                System.out.println("\n" + columnName + "'s tasks:");
                int index = 0;
                for (String taskName : tasksNames) {
                    System.out.println(++index + ". " + taskName);
                }
                System.out.println("\nmore operations:");
                System.out.println(++index + ". add task");
                System.out.println(++index + ". rename column");
                System.out.println(++index + ". delete column");

                displayBackOption();
                int choice = waitForNum(0, index, "choice");

                if(choice == 0){
                    return;
                }else if (choice <= tasksNames.size()){
                    displayTask(boardName, columnName, tasksNames.get(choice - 1));
                }else {
                    switch (choice - tasksNames.size()){
                        case 1: //add task
                            String taskName = waitForString("task name");
                            String description = waitForString("task description");
                            LocalDate deadline = waitForDate("task deadline");
                            int priority = waitForNum(1, 5, "task priority");
                            bc.addTask(boardName, columnName, taskName, description, deadline, priority);
                            break;
                        case 2: //rename column
                            String newColumnName = waitForString("new column name");
                            bc.setColumnName(boardName, columnName, newColumnName);
                            columnName = newColumnName;
                            break;
                        case 3: //delete column
                            bc.removeColumn(boardName, columnName);
                            return;
                    }
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void displayTask(String boardName, String columnName, String taskName) {
        while(true){
            try{
                System.out.println("\n" + taskName + "'s details:");
                System.out.println(bc.getTask(boardName, columnName, taskName).toString());
                int index = 0;
                System.out.println("\nmore operations:");
                System.out.println(++index + ". rename task");
                System.out.println(++index + ". change description");
                System.out.println(++index + ". change deadline");
                System.out.println(++index + ". change priority");
                System.out.println(++index + ". delete task");

                displayBackOption();
                int choice = waitForNum(0, index, "choice");

                if(choice == 0){
                    return;
                }else{
                    switch (choice) {
                        case 1: //rename task
                            String newTaskName = waitForString("new name");
                            bc.setTaskName(boardName, columnName, taskName, newTaskName);
                            taskName = newTaskName;
                            break;
                        case 2: //change description
                            bc.setTaskDescription(boardName, columnName, taskName, waitForString("new description"));
                            break;
                        case 3: //change deadline
                            bc.setTaskDeadline(boardName, columnName, taskName, waitForDate("new deadline"));
                            break;
                        case 4: //change priority
                            bc.setTaskPriority(boardName, columnName, taskName, waitForNum(0, 5, "priority"));
                            break;
                        case 5: //delete task
                            bc.removeTask(boardName, columnName, taskName);
                            return;
                    }
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

    public static void main(String[] args){
        Program program = new Program();
        program.displayMainMenu();
    }
}
