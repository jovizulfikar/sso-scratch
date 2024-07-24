create table if not exists api_scopes (
    id varchar(255) not null, 
    name varchar(255), 
    primary key (id)
);

alter table if exists api_scopes drop constraint if exists UK3ouyu3mqt07um9jin2tpw3r9j;
alter table if exists api_scopes add constraint UK3ouyu3mqt07um9jin2tpw3r9j unique (name);