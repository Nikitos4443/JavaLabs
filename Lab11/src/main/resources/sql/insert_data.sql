INSERT INTO department (name, phone)
VALUES ('HR', '380931112233'),
       ('IT', '380671234567'),
       ('Finance', '380501234567'),
       ('Marketing', '380631112244'),
       ('Support', '380991234111');


INSERT INTO worker (name, surname, position, department_id)
VALUES ('Ivan', 'Petrenko', 'Developer', 2),
       ('Olena', 'Koval', 'QA Engineer', 2),
       ('Serhii', 'Bondar', 'Accountant', 3),
       ('Oksana', 'Tkachenko', 'HR Manager', 1),
       ('Dmytro', 'Shevchenko', 'Support Agent', 5),
       ('Kateryna', 'Melnyk', 'Marketer', 4),
       ('Andrii', 'Hnatyuk', 'SysAdmin', 2);

INSERT INTO task (description, worker_id)
VALUES ('Fix login bug', 1),
       ('Test registration form', 2),
       ('Prepare monthly report', 3),
       ('Conduct interview', 4),
       ('Respond to client emails', 5),
       ('Create social media post', 6),
       ('Update server configuration', 7),
       ('Optimize database queries', 1),
       ('Test new payment flow', 2),
       ('Plan team meeting', 4);


