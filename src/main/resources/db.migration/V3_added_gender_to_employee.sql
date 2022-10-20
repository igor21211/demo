alter table users
    add if not exists gender varchar;

alter table users
    drop gender;

alter table users
    add if not exists gender varchar;