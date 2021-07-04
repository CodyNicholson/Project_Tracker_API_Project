create table project (id uuid not null, code_link varchar(255), deployed_link varchar(255), description varchar(255), documentation_link varchar(255), end_date timestamp, name varchar(255), start_date timestamp, primary key (id));
create table project_task (id uuid not null, acceptance_criteria varchar(255), blockers varchar(255), description varchar(255), name varchar(255), points float8 not null, project_id uuid, status varchar(255), primary key (id));
alter table if exists project_task add constraint fk_project_id foreign key (project_id) references project;
