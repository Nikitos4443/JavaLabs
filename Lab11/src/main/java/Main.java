import config.DBInitializer;
import models.TaskModel;
import repository.DepartmentRepository;
import repository.TaskRepository;
import repository.WorkerRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        var connection = DBInitializer.getConnection();
        DBInitializer.runSqlScript(connection, "sql/create_tables.sql");
        DBInitializer.runSqlScript(connection, "sql/insert_data.sql");

        var workerRepository = new WorkerRepository(connection);
        var taskRepository = new TaskRepository(connection);
        var departmentRepository = new DepartmentRepository(connection);

        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("""
                    Database interactions app
                    1. Get all workers
                    2. Get all tasks
                    3. Get workers by department
                    4. Add task
                    5. Get tasks by worker
                    6. Remove worker
                    0. Exit
                    """);
            System.out.print("> ");
            String option = scanner.nextLine();

            switch (option) {
                case "1": {
                    var allWorkers = workerRepository.getWorkers();
                    printArray(allWorkers);
                }
                    break;
                case "2": {
                    var allTasks = taskRepository.getTasks();
                    printArray(allTasks);
                }
                    break;
                case "3": {
                    var allDepartments = departmentRepository.getDepartments();
                    System.out.println("All departments: ");
                    printArray(allDepartments);

                    System.out.println("Enter department id from which you want to find workers: ");
                    System.out.print("> ");
                    String departmentIdStr = scanner.nextLine();
                    int departmentId;
                    try {
                        departmentId = Integer.parseInt(departmentIdStr);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid department id!");
                        break;
                    }
                    var workersByDepartment = workerRepository.getWorkersByDepartmentId(departmentId);
                    if(workersByDepartment.isEmpty()) {
                        System.out.println("This department has no workers or does not exist");
                    }
                    printArray(workersByDepartment);
                }
                    break;
                case "4": {
                    var allWorkers = workerRepository.getWorkers();
                    System.out.println("All workers: ");
                    printArray(allWorkers);

                    System.out.println("Enter task description: ");
                    System.out.print("> ");
                    String description = scanner.nextLine();

                    System.out.println("Enter worker id you want set task to: ");
                    System.out.print("> ");
                    String workerId = scanner.nextLine();

                    var newTask = new TaskModel(description, Integer.parseInt(workerId));
                    var isTaskAdded = taskRepository.addTask(newTask);
                    System.out.println(isTaskAdded ? "Task successfully added" : "Task was not added");
                }
                    break;
                case "5": {
                    var allWorkers = workerRepository.getWorkers();
                    System.out.println("All workers: ");
                    printArray(allWorkers);

                    System.out.println("Enter worker id by which you want to find tasks: ");
                    System.out.print("> ");
                    String workerId = scanner.nextLine();

                    var tasksByWorker = taskRepository.getTasksByWorkerId(Integer.parseInt(workerId));
                    if(tasksByWorker.isEmpty()) {
                        System.out.println("This worker has no tasks or does not exist");
                    }
                    printArray(tasksByWorker);
                }
                    break;
                case "6": {
                    var allWorkers = workerRepository.getWorkers();
                    System.out.println("All workers: ");
                    printArray(allWorkers);

                    System.out.println("Enter worker id you want to remove: ");
                    System.out.print("> ");
                    String workerId = scanner.nextLine();

                    var isWorkerRemoved = workerRepository.removeWorker(Integer.parseInt(workerId));
                    System.out.println(isWorkerRemoved ? "Worker successfully removed" : "Worker was not removed");
                }
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option");
            }

            System.out.println();
        }
    }

    public static void printArray(List<?> list) {
        for(Object o : list) {
            System.out.println(o);
        }
        System.out.println();
    }
}
