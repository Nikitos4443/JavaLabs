package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentModel {
    private int id;
    private String name;
    private String phone;

    public DepartmentModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}

