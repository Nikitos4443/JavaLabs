package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskModel {
    private int id;
    private String description;
    private int worker_id;

    public TaskModel(String description, int worker_id) {
        this.description = description;
        this.worker_id = worker_id;
    }
}

