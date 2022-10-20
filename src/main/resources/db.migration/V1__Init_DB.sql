create sequence hibernate_sequence start 1 increment 1

create table card
(
    id     int4 not null,
    number TEXT not null,
    primary key (id)
);

create table course
(
    id          int4                        not null,
    department  TEXT                        not null,
    name        TEXT                        not null,
    created_at  TIMESTAMP WITHOUT TIME ZONE not null,
    employee_id int4,
    primary key (id)
);

create table users
(
    id              serial not null,
    country         varchar(42),
    date_activation timestamp,
    date_created    timestamp,
    email           varchar(255),
    name            varchar(255),
    number_of_phone int8,
    plan            int4,
    employee_id     int4,
    primary key (id)
);

alter table course
    add constraint FKqcihurtk6r07sy7acayoxqox1 foreign key (employee_id) references users
alter table users
    add constraint FKd0dbv9cscj2d4e1cedej5674o foreign key (employee_id) references card
