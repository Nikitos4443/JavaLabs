DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS worker;
DROP TABLE IF EXISTS department;

CREATE TABLE department
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(30),
    phone VARCHAR(15)
);

CREATE TABLE worker
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(10),
    surname VARCHAR(20),
    position VARCHAR(20),
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    description VARCHAR(50),
    worker_id INT,
    FOREIGN KEY (worker_id) REFERENCES worker(id) ON DELETE CASCADE
);