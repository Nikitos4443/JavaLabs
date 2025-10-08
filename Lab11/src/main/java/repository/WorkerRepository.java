package repository;

import models.WorkerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerRepository {
    private final Connection connection;

    public WorkerRepository(Connection connection) {
        this.connection = connection;
    }

    public WorkerModel getWorkerById(int id) {
        String GET_WORKER_BY_ID_SQL = "SELECT * FROM worker WHERE id = ?";
        WorkerModel workerModel = null;

        try(PreparedStatement ps = connection.prepareStatement(GET_WORKER_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int workerId = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String position = rs.getString("position");
                int departmentId = rs.getInt("department_id");

                workerModel = new WorkerModel(workerId, name, surname, position, departmentId);
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }

        return workerModel;
    }

    public List<WorkerModel> getWorkers() {
        String GET_ALL_WORKERS_SQL = "SELECT * FROM worker";
        List<WorkerModel> workers = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(GET_ALL_WORKERS_SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int workerId = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String position = rs.getString("position");
                int departmentId = rs.getInt("department_id");

                WorkerModel worker = new WorkerModel(workerId, name, surname, position, departmentId);
                workers.add(worker);
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }

        return workers;
    }

    public List<WorkerModel> getWorkersByDepartmentId(int departmentId) {
        String GET_WORKERS_BY_DEPARTMENT_SQL = "SELECT * FROM worker WHERE department_id = ?";
        List<WorkerModel> workers = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(GET_WORKERS_BY_DEPARTMENT_SQL)) {
            ps.setInt(1, departmentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int workerId = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String position = rs.getString("position");

                WorkerModel worker = new WorkerModel(workerId, name, surname, position, departmentId);
                workers.add(worker);
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }

        return workers;
    }

    public boolean removeWorker(int id) {
        String REMOVE_WORKER_SQL = "DELETE FROM worker WHERE id = ?";

        try(PreparedStatement ps = connection.prepareStatement(REMOVE_WORKER_SQL)) {
            ps.setInt(1, id);
            int res = ps.executeUpdate();
            if (res == 0) {
                return false;
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }
}
