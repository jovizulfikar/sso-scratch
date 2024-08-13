create table access_token_blacklist (
    id varchar(255) not null,
    jti varchar(255) not null,
    expired_at timestamp(6),
    primary key (id)
);

alter table if exists access_token_blacklist drop constraint if exists UKtr647mb4756lm98so749nb21o;
alter table if exists access_token_blacklist add constraint UKtr647mb4756lm98so749nb21o unique (jti);