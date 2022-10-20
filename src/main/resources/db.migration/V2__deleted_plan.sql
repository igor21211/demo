alter table users
    drop plan;

alter table users
    add if not exists plan Integer;
