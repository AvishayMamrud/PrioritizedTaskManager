import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Program {

    private BoardController bc;
    private Scanner scan = new Scanner(System.in);

    public Program(){
        bc = BoardController.getInstance();
    }

    public void displayMainMenu(){
        while(true) {
            System.out.println("select your operation:");
            System.out.println("1. add board");
            System.out.println("2. view my boards");
            //add a new board
            //view my boards
            int choice = waitForNum(2);
            Callback nextFunc = () -> {
                switch (choice) {
                    case 1:
                        bc.addBoard(waitForString("new board's name"));
                        break;
                    case 2:
                        displayBoardsMenu();
                        break;
                }
            };
            Callback backFunc = () -> {throw new IllegalArgumentException("");};
            nextStep(backFunc, nextFunc, choice);
        }
    }

    public void displayBoardsMenu(){
        List<String> boards = new ArrayList<>(bc.getBoardNames());
        System.out.println("your boards:");
        int index = 0;
        for (String boardName : boards) {
            System.out.println(++index + ". " + boardName);
        }

        int choice = waitForNum(index);
        Callback nextFunc = () -> displayBoard(boards.get(choice-1));
        Callback backFunc = this::displayMainMenu;
        nextStep(backFunc, nextFunc, choice);
    }

    private void nextStep(Callback goBack, Callback func, int index){
        if(index == 0)
            goBack.call();
        else func.call();
    }

    public void displayBoard(String boardName){

        List<String> colNames = new ArrayList<>(bc.getColumnsNames(boardName));

        System.out.println(boardName + "'s columns:");
        int index = 0;
        for (String colName : colNames) {
            System.out.println(++index + ". " + colName);
        }
        System.out.println();
        System.out.println(++index + ". add column");
        System.out.println(++index + ". reposition column");
        System.out.println(++index + ". rename board");
        System.out.println(++index + ". delete board");

        int choice = waitForNum(index);

        Callback nextFunc = () -> {
            if (choice <= colNames.size())
                displayColumn(colNames.get(choice - 1));
            else {
                switch (choice - colNames.size()){
                    case 1: //add column
                        bc.addColumn(boardName, waitForString("column name"));
                        break;
                    case 2: //reposition column
                        bc.repositionColumn(boardName, waitForString("column name"), waitForNum(colNames.size()));
                        break;
                    case 3: //rename board
                        bc.setBoardName(boardName, waitForString("new board name"));
                        break;
                    case 4: //delete board
                        bc.removeBoard(boardName);
                        break;
                }
            }
        };
        Callback backFunc = this::displayBoardsMenu;
        nextStep(backFunc, nextFunc, choice);
    }

    private void displayColumn(String columnName){
        /////
    }

    public int waitForNum(int max){
        displayBackOption();
        System.out.print("\nselect an option in the range (0-" + max + "): ");
        boolean gotNum = false;
        int num = 0;
        while(!gotNum){
            try{
                num = scan.nextInt();
                gotNum = num <= max && num >= 0;
            }catch(Exception e){
                System.out.print("\nstill waiting for a number...: ");
            }
        }
        return num;
    }

    private void displayBackOption(){
        System.out.println("\nto go back press 0");
    }

    private String waitForString(String wish){
        System.out.print("type a " + wish + ": ");
        return scan.next();
    }

    public static void main(String[] args){
        Program program = new Program();
        program.displayMainMenu();
    }
}
