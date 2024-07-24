create table if not exists client_api_scope (
    client_id varchar(255) not null, 
    api_scope_id varchar(255) not null, 
    primary key (client_id, api_scope_id)
);

alter table if exists client_api_scope add constraint FK6lp2wu8c6g93aotrh95qc0qcq foreign key (api_scope_id) references api_scopes;
alter table if exists client_api_scope add constraint FK91493nmo34evgtlx61yc39un1 foreign key (client_id) references clients;