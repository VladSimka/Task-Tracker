INSERT INTO users (username, password)
VALUES ('mike', '$2a$10$keovN895Z2Sky0kBzdSnMOwzvbo6A6Nf4kQVRcOxl2vvToZgoOidm'),
       ('ivan', '$2a$10$h1wIUvV96vPB/sT4CpxRJ.5DsD5qVh3Xqo8wyfTinPSYHgz9d6/QK'),
       ('JoHN', '$2a$10$oOECSjkZ92kcoWfZGpNzauufu1/GUAuIde10cK9Lv403tL8e65Ccm');

INSERT INTO tasks (title, description, status, start_time, planned_end_time)
VALUES ('Task 1', 'This is my best task ever', 'TO_DO', '2024-02-14 09:00:00', '2024-02-14 12:00:00'),
       ('Task 2', 'Description 2', 'IN_PROCESS', '2024-02-14 13:00:00', '2024-02-14 15:00:00'),
       ('Task 3', 'Description 3', 'COMPLETE', '2024-02-14 10:30:00', '2024-02-14 11:30:00'),
       ('Task 4', 'Description 3', 'COMPLETE', '2024-02-14 10:30:00', '2024-02-14 11:30:00');

INSERT INTO users_tasks (user_id, task_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (3, 4);

INSERT INTO users_roles (user_id, role)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_USER'),
       (2, 'ROLE_ADMIN'),
       (3, 'ROLE_USER');