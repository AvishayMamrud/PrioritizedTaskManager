import java.time.LocalDate;
import java.util.Collection;

public class Column {
    private String name;
    private Collection<Task> tasks;

    public Column(String name){
        this.name = name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getName(){
        return name;
    }

    public void addTask(String name, String description, LocalDate deadline, int priority){
        Task newTask = new Task(name, description, deadline, priority);
        tasks.add(newTask);
    }

    public boolean removeTask(String name){
        return tasks.removeIf(x -> x.getName().equals(name));
    }
}
