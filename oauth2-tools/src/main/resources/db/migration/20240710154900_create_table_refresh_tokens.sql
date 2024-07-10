create table refresh_tokens (
    id varchar(255) not null, 
    client_id varchar(255), 
    expired_at timestamp(6), 
    user_id varchar(255), 
    value varchar(255), 
    primary key (id)
);

alter table if exists refresh_tokens drop constraint if exists UK49ntxwlntp8kp4u0gge8g8oot;
alter table if exists refresh_tokens add constraint UK49ntxwlntp8kp4u0gge8g8oot unique (value);
