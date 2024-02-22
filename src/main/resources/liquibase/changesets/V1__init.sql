CREATE TABLE if not exists users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE if not exists tasks
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    status           VARCHAR(20)  NOT NULL,
    start_time       TIMESTAMP,
    planned_end_time TIMESTAMP
);

create table if not exists users_tasks
(
    user_id bigint not null,
    task_id bigint not null,
    primary key (user_id, task_id),
    constraint fk_users_tasks_users foreign key (user_id) references users (id) on delete cascade on update no action,
    constraint fk_users_tasks_tasks foreign key (task_id) references tasks (id) on delete cascade on update no action
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id serial      not null,
    role    varchar(255) not null,
    primary key (user_id, role),
    constraint fk_users_roles_users foreign key (user_id) references users (id) on delete cascade on UPDATE no action
);