create table users
(
    id              serial,
    country         varchar(42),
    date_activation timestamp,
    date_created    timestamp,
    email           varchar(255),
    gender          varchar(255),
    name            varchar(255),
    number_of_phone bigint,
    plan            integer,
    employee_id     integer,
    primary key (id),
    constraint fkd0dbv9cscj2d4e1cedej5674o
        foreign key (employee_id) references card
);