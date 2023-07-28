package PrioritizedQueue;

import Utilities.Response;
import Utilities.ResponseT;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

public class dbManager {

    private String dbName = "db.db";
    private String path = "JDBC:sqlite:" + dbName;
    private final int priorityRange;

    //TABLES NAMES
    public static String tasksTbl = "Tasks";
    public static String completedTbl = "Complete";

    public dbManager(int priorityRange) {
        this.priorityRange = priorityRange;
    }

    public void initialize() {
        if (!new File(dbName).exists()) {
            createNewDatabase();
            createTasksTbl();
            createCompletedTbl();
        }
    }

    private Response createNewDatabase() {
        try(Connection conn = DriverManager.getConnection(path)) {
            Class.forName("org.sqlite.JDBC");
            if (conn == null) throw new SQLException("connection is null.");
            return new Response();
        }catch(Exception e){
            return new Response(e.getMessage());
        }
    }

    private void createTasksTbl() {
        String sql = "create table if not exists " + tasksTbl + "(\n" +
                "            name text PRIMARY KEY,\n" +
                "            description text,\n" +
                "            deadline date,\n" +
                "            priority integer not null check(priority >= 1 and priority <= 5)\n" +
                "    );";
        try (Connection con = DriverManager.getConnection(path)) {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createCompletedTbl() {
        String sql = "create table if not exists " + completedTbl + "(\n" +
                "            name text PRIMARY KEY,\n" +
                "            description text,\n" +
                "            deadline date,\n" +
                "            finishTime date not null\n" +
                "    );";
        try (Connection con = DriverManager.getConnection(path)) {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResponseT<Queue<Task>[]> getTasks() {
        try (Connection con = DriverManager.getConnection(path)) {
            Queue<Task>[] tasks = new Queue[priorityRange];
            for(int i = 0; i < 5; i++){
                tasks[i] = new LinkedList<>();
            }
            String sqlStatement = "select * from " + tasksTbl + ";";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String desc = rs.getString("description");
                Date dl = rs.getDate("deadline");
                LocalDate deadline = dl == null ? null : dl.toLocalDate();
                int priority = rs.getInt("priority");
                tasks[priority - 1].add(new Task(name, desc, deadline, priority));
            }
            return new ResponseT<>(tasks);
        } catch (SQLException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response addTask(Task task){
        try (Connection con = DriverManager.getConnection(path)) {
            String sqlStatement = "insert into " + tasksTbl + " (name, description, deadline, priority) values (?, ?, ?, ?);";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            LocalDate dl = task.getDeadline();
            p.setString(1, task.getName());
            p.setString(2, task.getDescription());
            p.setDate(3, dl == null ? null : Date.valueOf(dl));
            p.setInt(4, task.getPriority());
            p.executeUpdate();
            return new Response();
        } catch (SQLException e) {
            return new Response(e.getMessage());
        }
    }

    public Response updateTask(String oldName, Task task){
        try (Connection con = DriverManager.getConnection(path)) {
            String sqlStatement = "update " + tasksTbl + " set name = ?, description = ?, deadline = ?, priority = ? where name = ?;";
//            System.out.println(task.getName() + " "
//                    + task.getDescription() + " "
//                    + Date.valueOf(task.getDeadline()) + " "
//                    + task.getPriority() + " "
//                    + oldName);
            LocalDate dl = task.getDeadline();
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, task.getName());
            p.setString(2, task.getDescription());
            p.setDate(3, dl == null ? null : Date.valueOf(dl));
            p.setInt(4, task.getPriority());
            p.setString(5, oldName);

            p.executeUpdate();
            return new Response();
        } catch (SQLException e) {
            return new Response("database error occurred. there might be another task with the same name.");
        }
    }

    public Response removeTask(String name){
        try (Connection con = DriverManager.getConnection(path)) {
            String sqlStatement = "delete from " + tasksTbl + " where name = ?;";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            p.setString(1, name);
            p.executeUpdate();
            return new Response();
        } catch (SQLException e) {
            return new Response(e.getMessage());
        }
    }

    public Response finishTask(Task task){
        Response resp = removeTask(task.getName());
        if(resp.errorOccurred()) return resp;
        return addCompletedTask(task, LocalDate.now());
    }

    private Response addCompletedTask(Task task, LocalDate finishTime) {
        try (Connection con = DriverManager.getConnection(path)) {
            String sqlStatement = "insert into " + completedTbl + " (name, description, deadline, finishTime) values (?, ?, ?, ?);";
            PreparedStatement p = con.prepareStatement(sqlStatement);
            LocalDate dl = task.getDeadline();
            p.setString(1, task.getName());
            p.setString(2, task.getDescription());
            p.setDate(3, dl == null ? null : Date.valueOf(dl));
            p.setDate(4, Date.valueOf(finishTime));
            p.executeUpdate();
            return new Response();
        } catch (SQLException e) {
            return new Response(e.getMessage());
        }
    }

    public static void main(String[] args){
        dbManager db = new dbManager(5);
        db.initialize();
    }
}