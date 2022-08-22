create table if not exists users (
    id varchar(36) primary key not null,
    email varchar(255) not null unique
);
