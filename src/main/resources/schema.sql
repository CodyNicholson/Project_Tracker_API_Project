create table public.project (id uuid not null, code_link varchar(255), deployed_link varchar(255), description varchar(255), documentation_link varchar(255), end_date timestamp, name varchar(255), start_date timestamp, primary key (id))
create table public.project_task (id uuid not null, acceptance_criteria varchar(255), status varchar(255), summary varchar(255), project_id uuid not null, primary key (id))
alter table if exists public.project_task add constraint FKoki9jr57ykahgi2wve418pxvi foreign key (project_id) references public.project
