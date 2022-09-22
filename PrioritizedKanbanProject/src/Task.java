import java.time.LocalDate;

public class Task {
    private String name;
    private String description;
    private LocalDate deadline;
    private int priority;

    public Task(String name, String description, LocalDate deadline, int priority){
        if(name == null)
            throw new NullPointerException("a task must have a distinctive name.");
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public void setDeadline(LocalDate newDeadline){
        this.deadline = newDeadline;
    }

    public void setPriority(int newPriority){
        this.priority = newPriority;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public LocalDate getDeadline(){
        return deadline;
    }

    public int getPriority(){
        return priority;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Task)
            return this.name.equals(((Task)other).getName());
        return false;
    }
}
