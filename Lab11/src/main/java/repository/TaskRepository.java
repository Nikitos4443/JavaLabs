package repository;

import models.TaskModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private final Connection connection;

    public TaskRepository(Connection connection) {
        this.connection = connection;
    }

    public List<TaskModel> getTasks() {
        String GET_ALL_TASKS_SQL = "SELECT * FROM task";
        List<TaskModel> tasks = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(GET_ALL_TASKS_SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int taskId = rs.getInt("id");
                String description = rs.getString("description");
                int departmentId = rs.getInt("worker_id");

                TaskModel task = new TaskModel(taskId, description, departmentId);
                tasks.add(task);
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }

        return tasks;
    }

    public List<TaskModel> getTasksByWorkerId(int workerId) {
        String GET_TASKS_BY_WORKER_SQL = "SELECT * FROM task WHERE worker_id = ?";
        List<TaskModel> tasks = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(GET_TASKS_BY_WORKER_SQL)) {
            ps.setInt(1, workerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int taskId = rs.getInt("id");
                String description = rs.getString("description");
                int departmentId = rs.getInt("worker_id");

                TaskModel task = new TaskModel(taskId, description, departmentId);
                tasks.add(task);
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }

        return tasks;
    }

    public boolean addTask(TaskModel task) {
        String ADD_TASK_SQL = "INSERT INTO task (description, worker_id) VALUES (?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(ADD_TASK_SQL)) {
            ps.setString(1, task.getDescription());
            ps.setInt(2, task.getWorker_id());

            int res = ps.executeUpdate();
            if (res == 0) {
                return false;
            }
        }catch(SQLException e){
//            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
}

