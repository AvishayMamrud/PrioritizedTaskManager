import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

public class Column {
    private String name;
    private Map<String, Task> tasks;

    public Column(String name){
        if(name == null || name.equals(""))
            throw new IllegalArgumentException("A column must have a name.");
        this.name = name;
    }

    public void setName(String newName){
        if(newName == null || newName.equals(""))
            throw new IllegalArgumentException("A column must have a name.");
        this.name = newName;
    }

    public String getName(){
        return name;
    }

    public void addTask(String name, String description, LocalDate deadline, int priority){
        if(!tasks.containsKey(name))
            throw new IllegalArgumentException("A task must have a distinct name.");
        Task newTask = new Task(name, description, deadline, priority);
        tasks.put(name, newTask);
    }

    public boolean removeTask(String name){
        return tasks.remove(name) != null;
    }

    public void setTaskName(String oldName, String newName) {
        Task task = tasks.remove(oldName);
        if(task == null)
            throw new NoSuchElementException("No such task with the name " + oldName);
        task.setName(newName);
        tasks.put(newName, task);
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Column)
            return this.name.equals(((Column)other).getName());
        return false;
    }

    public Task getTask(String taskName) {
        if(!tasks.containsKey(taskName))
            throw new NoSuchElementException("No such task with the name '" + taskName + "'");
        return tasks.get(taskName);
    }

    public void insertTask(Task task) {
        if(tasks.containsKey(task.getName()))
            throw new IllegalArgumentException("A task with the name '" + task.getName() + "' already exists in this column");
        tasks.put(task.getName(), task);
    }

    public void setTaskDescription(String taskName, String newDesc) {
        getTask(taskName).setDescription(newDesc);
    }

    public void setTaskDeadline(String taskName, LocalDate newDeadline) {
        getTask(taskName).setDeadline(newDeadline);
    }

    public void setTaskPriority(String taskName, int newPriority) {
        getTask(taskName).setPriority(newPriority);
    }
}
