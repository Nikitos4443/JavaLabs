package repository;

import models.DepartmentModel;
import models.TaskModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {
    private final Connection connection;

    public DepartmentRepository(Connection connection) {
        this.connection = connection;
    }

    public List<DepartmentModel> getDepartments() {
        String GET_ALL_DEPARTMENTS_SQL = "SELECT * FROM department";
        List<DepartmentModel> departments = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(GET_ALL_DEPARTMENTS_SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int departmentId = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");

                DepartmentModel department = new DepartmentModel(departmentId, name, phone);
                departments.add(department);
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }

        return departments;
    }
}
