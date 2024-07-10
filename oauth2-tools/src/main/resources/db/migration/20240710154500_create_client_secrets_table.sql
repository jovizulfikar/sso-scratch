create table client_secrets (
    id varchar(255) not null, 
    client_id varchar(255), 
    expires_at timestamp(6), 
    issued_at timestamp(6), 
    secret varchar(255), 
    primary key (id)
);

alter table if exists client_secrets add constraint FKjoq9qaxbktk6to8hfmhxcapwv foreign key (client_id) references clients;