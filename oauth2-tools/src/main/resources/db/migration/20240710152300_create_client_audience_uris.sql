create table client_audience_uris (
    client_id varchar(255) not null, 
    audience_uri varchar(255)
);

alter table if exists client_audience_uris add constraint FKls50o7nxloevaugpg08q4rsvp foreign key (client_id) references clients;