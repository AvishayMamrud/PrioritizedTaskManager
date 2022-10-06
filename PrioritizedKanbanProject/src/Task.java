import java.time.LocalDate;

public class Task {
    private String name;
    private String description;
    private LocalDate deadline;
    private int priority;
    private float currPriority;

    public Task(String name, String description, LocalDate deadline, int priority){
        if(name == null || name.equals(""))
            throw new NullPointerException("A task must have a name.");
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

    public void setName(String newName){
        if(newName == null || newName.equals(""))
            throw new NullPointerException("A task must have a name.");
        this.name = newName;
    }

    public String getName(){
        return name;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public String getDescription(){
        return description;
    }

    public void setDeadline(LocalDate newDeadline){
        this.deadline = newDeadline;
    }

    public LocalDate getDeadline(){
        return deadline;
    }

    public void setPriority(int newPriority){
        this.priority = newPriority;
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

    @Override
    public String toString(){
        return "\nName: " + name +
                "\nDescription: " + description +
                "\nDeadline: " + deadline.toString() +
                "\nPriority: " + priority;
    }

    public void resetPriority() {
        currPriority = priority;
    }

    public float getCurrPriority() {
        return currPriority;
    }
}
