package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkerModel {
    private int id;
    private String name;
    private String surname;
    private String position;
    private Integer departmentId;
}
