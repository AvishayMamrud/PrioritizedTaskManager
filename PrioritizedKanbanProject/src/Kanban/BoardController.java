package Kanban;

import java.time.LocalDate;
import java.util.*;

public class BoardController {
    private Map<String, Board> boards;
    private static BoardController instance = null;
    private static final String INITIAL_COLUMN_NAME = "TODO";

    public static BoardController getInstance(){
        if(instance == null){
            instance = new BoardController();
        }
        return instance;
    }

    private BoardController(){
        boards = new HashMap<>();
    }

    public void addBoard(String boardName){
        if(boards.containsKey(boardName))
            throw new IllegalArgumentException("A board must have a distinct name.");
        Board board = new Board(boardName);
        boards.put(boardName, board);
    }

    public boolean removeBoard(String boardName){
        return boards.remove(boardName) != null;
    }

    public void setBoardName(String oldName, String newName){
        if(boards.containsKey(newName))
            throw new IllegalArgumentException("There is already board named '" + newName + "'.");
        Board board = boards.remove(oldName);
        if(board == null)
            throw new NoSuchElementException("No such board with the name " + oldName);
        board.setName(newName);
        boards.put(newName, board);
    }

    public void addColumn(String boardName, String columnName){
        getBoard(boardName).addColumn(columnName);
    }

    public boolean removeColumn(String boardName, String columnName){
        return getBoard(boardName).removeColumn(columnName);
    }

    public void repositionColumn(String boardName, String columnName, int index){
        getBoard(boardName).repositionColumn(columnName, index);
    }

    public void setColumnName(String boardName, String oldName, String newName){
        getBoard(boardName).setColumnName(oldName, newName);
    }

    public void addTask(String boardName, String columnName, String taskName, String description, LocalDate deadline, int priority){
        getBoard(boardName).addTask(columnName, taskName, description, deadline, priority);
    }

    public boolean removeTask(String boardName, String columnName, String taskName){
        return getBoard(boardName).removeTask(columnName, taskName);
    }

    public void setTaskName(String boardName, String columnName, String oldName, String newName){
        getBoard(boardName).setTaskName(columnName, oldName, newName);
    }

    public void setTaskDescription(String boardName, String columnName, String taskName, String newDesc){
        getBoard(boardName).setTaskDescription(columnName, taskName, newDesc);
    }

    public void setTaskDeadline(String boardName, String columnName, String taskName, LocalDate newDeadline){
        getBoard(boardName).setTaskDeadline(columnName, taskName, newDeadline);
    }

    public void setTaskPriority(String boardName, String columnName, String taskName, int newPriority){
        getBoard(boardName).setTaskPriority(columnName, taskName, newPriority);
    }

    public void repositionTask(String boardName, String oldColumnName, String newColumnName, String taskName){
        getBoard(boardName).repositionTask(oldColumnName, newColumnName, taskName);
    }

    private Board getBoard(String boardName){
        if(!boards.containsKey(boardName))
            throw new NoSuchElementException("No such board with the name " + boardName);
        return boards.get(boardName);
    }

    public Collection<String> getBoardNames() {
        return boards.keySet();
    }

    public Collection<String> getColumnsNames(String boardName) {
        return getBoard(boardName).getColumnsNames();
    }

    public List<String> getTasksNames(String boardName, String columnName) {
        return getBoard(boardName).getTasksNames(columnName);
    }

    public Task getTask(String boardName, String columnName, String taskName) {
        return getBoard(boardName).getTask(columnName, taskName);
    }
}
