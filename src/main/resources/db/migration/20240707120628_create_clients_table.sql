create table if not exists clients (
    id varchar(255) not null, 
    access_token_ttl bigint, 
    client_id varchar(255) not null, 
    issued_at timestamp(6), 
    name varchar(255), 
    refresh_token_ttl bigint, 
    primary key (id)
);

alter table if exists clients drop constraint if exists UK2og8x0i6lngghy4cqupje9dki;
alter table if exists clients add constraint UK2og8x0i6lngghy4cqupje9dki unique (client_id);
