package PrioritizedQueue;

import Utilities.Response;
import Utilities.ResponseT;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class prioritizedTaskManager {
    private final Queue<Task>[] tasks;
    private int totalTasks;
    private final int[] counters;
    private int totalCounter;
    private Task currTask;
    private final dbManager dal;

    public prioritizedTaskManager(int priorityRange) {
        //tasks = new Queue[priorityRange];
        dal = new dbManager(priorityRange);
        dal.initialize();
        ResponseT<Queue<Task>[]> resp = dal.getTasks();
        if (!resp.errorOccurred()){
            tasks = resp.getValue();
        }else{
            tasks = new Queue[priorityRange];
            for(int i = 0; i < priorityRange; i++) {
                tasks[i] = new LinkedList<>();
            }
        }
        totalTasks = 0;
        counters = new int[priorityRange];
        totalCounter = 0;
        for(int i = 0; i < priorityRange; i++){
            //tasks[i] = new LinkedList<>();
            totalTasks += tasks[i].size();
            counters[i] = 0;
        }
    }

    public Response addTask(String name, String description, LocalDate deadline, int priority){
        try{
            if(name == null || name.equals(""))
                throw new IllegalArgumentException("a task must have a unique name.");
            if(priority < 1 || priority > 5)
                throw new IllegalArgumentException("the priority scale is 1-5.");
            Task task = new Task(name, description, deadline, priority);
            Response resp = dal.addTask(task);
            if(resp.errorOccurred()){
                return resp;
            }
            tasks[priority - 1].add(task);
            totalTasks++;
            if(counters[priority - 1] == 0) {
                float sum  = .0f;
                for (int x = 0; x < tasks.length; x++) {
                    sum += ((float)(tasks[x].size()) / totalTasks) * (x + 1);
                }
                counters[priority - 1] = (int)((totalCounter * priority) / (totalTasks * sum - priority));
                totalCounter += counters[priority - 1];
            }else{
                counters[priority - 1]++;
                totalCounter++;
            }
            return new Response();
        }catch(Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Task> peekTask(){
        int i = tasks.length - 1;
        if(totalTasks == 0)
            return new ResponseT<>("no tasks to display.");
        float[] calc = new float[tasks.length];
        float sum = .0f;
        for (int x = 0; x < tasks.length; x++) {
            calc[x] = ((float)(tasks[x].size()) / totalTasks) * (1 + x / 2.f);
            sum += calc[x];
        }
//        System.out.println("\n--------------------------------------------------------\n");
//        System.out.println("sum of calc = " + sum);
//        System.out.println("calc = (tasks[x].size() / totalTasks) * (x + 1) = " + Arrays.toString(calc));
//        System.out.println("total count = " + totalCounter);
//        System.out.println("counters = " + Arrays.toString(counters));
//        System.out.println("\n--------------------------------------------------------\n");
        if(totalTasks > 1 || (totalTasks > 0 && currTask == null)) {
            int nextQueue = tasks.length - 1;

            float maxGap = -1.0f;
            while (i >= 0) {
                if (tasks[i].size() > 0 && (currTask == null || i + 1 != currTask.getPriority() || tasks[i].size() > 1)) { // change to max gap rather than the higher priority checked first
                    if (totalCounter == 0) {
                        nextQueue = i;
                        break;
                    }else if (maxGap < (calc[i] / sum) - ((float) (counters[i]) / totalCounter)) {
                        nextQueue = i;
                        maxGap = (calc[i] / sum) - ((float) (counters[i]) / totalCounter);
                    }
                }
                i--;
            }

            counters[nextQueue]++;
            totalCounter++;

            currTask = tasks[nextQueue].remove();
            tasks[nextQueue].add(currTask);
            return new ResponseT<>(currTask);
        }else {
            return new ResponseT<>("there are no more tasks in here...", currTask);
        }
    }

    public ResponseT<Boolean> removeTask(String name){
        int priority = currTask.getPriority();
        if(currTask.getName().equals(name)) {
            Response resp = dal.removeTask(name);
            if(resp.errorOccurred())
                return new ResponseT<>(resp.getErrorMessage());
            ResponseT<Boolean> result = new ResponseT<>(tasks[priority].removeIf(task -> task.getName().equals(name)));
            currTask = null;
            totalTasks--;
            return result;
        }else{ return new ResponseT<>(false); }
    }

    public ResponseT<Boolean> removeTask(Task task){
        Response resp = dal.removeTask(task.getName());
        if(resp.errorOccurred())
            return new ResponseT<>(resp.getErrorMessage());
        ResponseT<Boolean> result = new ResponseT<>(tasks[task.getPriority() - 1].removeIf(elem -> elem.getName().equals(task.getName())));
        currTask = null;
        totalTasks--;
        return result;
    }

    public Response setTaskName(String name, String newTaskName) {
        if(currTask.getName().equals(name)){
            currTask.setName(newTaskName);
            return dal.updateTask(currTask.getName(), currTask);
        }
        return new Response("not the correct task-name.");
    }

    public Response setTaskDescription(String name, String new_description) {
        if(currTask.getName().equals(name)){
            currTask.setDescription(new_description);
            return dal.updateTask(currTask.getName(), currTask);
        }
        return new Response("not the correct task-name.");
    }

    public Response setTaskDeadline(String name, LocalDate new_deadline) {
        if(currTask.getName().equals(name)) {
            currTask.setDeadline(new_deadline);
            return dal.updateTask(currTask.getName(), currTask);
        }
        return new Response("not the correct task-name.");
    }

    public Response setTaskPriority(String name, int priority) {
        if(currTask.getName().equals(name)) {
            int oldPriority = currTask.getPriority();
            currTask.setPriority(priority);
            tasks[oldPriority - 1].removeIf(task -> task.getName().equals(name));
            tasks[priority - 1].add(currTask);
            return dal.updateTask(currTask.getName(), currTask);
        }
        return new Response("not the correct task-name.");
    }

    public ResponseT<List<Task>> getTasks(){
        List<Task> allTasks = new ArrayList<>();
        for (Queue<Task> q : tasks) {
            allTasks.addAll(q);
        }
        allTasks.sort((t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()));
        return new ResponseT<>(allTasks);
    }

    public Response finishTask(String name) {
        if(currTask.getName().equals(name)) {
            Response resp = dal.finishTask(currTask);
            if(resp.errorOccurred())
                return resp;
            int priority = currTask.getPriority();
            tasks[priority - 1].removeIf(task -> task.getName().equals(name));
            return new Response();
        }
        return new Response("not the correct task-name.");
    }

    public Response updateTask(String oldName, int oldPriority, String newName, String newDesc, LocalDate newDeadline, int newPriority) {
        try {
            if (newName == null || newName.equals(""))
                throw new IllegalArgumentException("a task must have a unique name.");
            if (newPriority < 1 || newPriority > 5)
                throw new IllegalArgumentException("the priority scale is 1-5.");

            currTask = tasks[oldPriority - 1].stream().filter(x -> x.getName().equals(oldName)).iterator().next();

            currTask.setName(newName);
            currTask.setDescription(newDesc);
            currTask.setDeadline(newDeadline);
            currTask.setPriority(newPriority);

            System.out.println(newDeadline);

            Response resp = dal.updateTask(oldName, currTask);
            if (resp.errorOccurred()) {
                return resp;
            }
            if(oldPriority != newPriority){
                tasks[oldPriority - 1].remove(currTask);
                tasks[newPriority - 1].add(currTask);
            }
            return new Response();
        }catch(Exception e2){
            return new Response(e2.getMessage());
        }
    }

    public void setCurrTask(Task task) {
        int priority = task.getPriority();
        tasks[priority - 1].removeIf(e -> e.equals(task));
        tasks[priority - 1].add(task);
        currTask = task;
        counters[priority - 1]++;
        totalCounter++;
    }
}