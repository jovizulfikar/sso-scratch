create table if not exists users (
    id varchar(255) not null, 
    email varchar(255), 
    password varchar(255), 
    phone_number varchar(255), 
    username varchar(255) not null, 
    primary key (id)
);

alter table if exists users drop constraint if exists UKr43af9ap4edm43mmtq01oddj6;
alter table if exists users add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);