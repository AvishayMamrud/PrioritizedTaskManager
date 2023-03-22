package Kanban;

import java.time.LocalDate;
import java.util.*;

public class Board {
    private String name;
    private Map<String, Column> columns;
    private List<String> columnsOrder;

    public Board(String name){
        if(name == null || name.equals(""))
            throw new IllegalArgumentException("A board must have a name.");
        this.name = name;
        columns = new HashMap<>();
        Column todo = new Column("TODO");
        Column done = new Column("DONE");
        columns.put("TODO", todo);
        columns.put("DONE", done);

        columnsOrder = new ArrayList<>();
        columnsOrder.add("TODO");
        columnsOrder.add("DONE");
    }

    public void setName(String newName){
        if(newName == null || newName.equals(""))
            throw new IllegalArgumentException("A board must have a name.");
        this.name = newName;
    }

    public String getName(){
        return name;
    }

    public void addColumn(String columnName) {//}, int index){
        if (columns.containsKey(columnName))
            throw new IllegalArgumentException("A column must have a distinct name.");
        Column col = new Column(columnName);
        columns.put(columnName, col);
        columnsOrder.add(columnsOrder.size() - 1, columnName);
    }

    public boolean removeColumn(String columnName){
        if(columnsOrder.size() <= 1)
            throw new IllegalArgumentException("a board must have at least one column. you may delete the board if you like");
        boolean res = columns.remove(columnName) != null;
        if(res)
            columnsOrder.remove(columnName);
        return res;
    }

    public void setColumnName(String oldName, String newName) {
        Column col = columns.remove(oldName);
        if(col == null)
            throw new NoSuchElementException("No such column with the name " + oldName);
        col.setName(newName);
        columns.put(newName, col);
        int index = columnsOrder.indexOf(oldName);
        columnsOrder.remove(oldName);
        columnsOrder.add(index, newName);
    }

    public void repositionColumn(String columnName, int index){
        if(!columnsOrder.contains(columnName))
            throw new NoSuchElementException("No such column with name " + columnName);
        if(columnsOrder.size() < index || index < 0)
            throw new IndexOutOfBoundsException("index out of range");
        columnsOrder.remove(columnName);
        columnsOrder.add(index, columnName);
    }

    public void addTask(String columnName, String taskName, String description, LocalDate deadline, int priority) {
        getColumn(columnName).addTask(taskName, description, deadline, priority);
    }

    public boolean removeTask(String columnName, String taskName){
        return getColumn(columnName).removeTask(taskName);
    }

    public void setTaskName(String columnName, String oldName, String newName) {
        getColumn(columnName).setTaskName(oldName, newName);
    }

    private Column getColumn(String columnName){
        if(!columns.containsKey(columnName))
            throw new NoSuchElementException("No such column with the name " + columnName);
        return columns.get(columnName);
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Board)
            return this.name.equals(((Board)other).getName());
        return false;
    }

    public void repositionTask(String oldColumnName, String newColumnName, String taskName) {
        Column oldCol = getColumn(oldColumnName);
        Task task = oldCol.getTask(taskName);
        getColumn(newColumnName).insertTask(task);
        task.resetPriority();
        oldCol.removeTask(taskName);
    }

    public void setTaskDescription(String columnName, String taskName, String newDesc) {
        getColumn(columnName).setTaskDescription(taskName, newDesc);
    }

    public void setTaskDeadline(String columnName, String taskName, LocalDate newDeadline) {
        getColumn(columnName).setTaskDeadline(taskName, newDeadline);
    }

    public void setTaskPriority(String columnName, String taskName, int newPriority) {
        getColumn(columnName).setTaskPriority(taskName, newPriority);
    }

    public Collection<String> getColumnsNames() {
        return columnsOrder;
    }

    public List<String> getTasksNames(String columnName) {
        return getColumn(columnName).getTasksNames();
    }

    public Task getTask(String columnName, String taskName) {
        return getColumn(columnName).getTask(taskName);
    }
}
