insert into person(username, email, is_confirmed, role, password, created_at) VALUES ('roma','roman@gmail.com',false,'ROLE_USER','$2a$10$eiRVe334lfXmSCdeWkBTbeZQYQsteFtPvvJsGOXN52Mmj96/rRT4.',now());


insert into article (person_id, name, text, created_at)
values (1,'SOME roma ARTICLE','SOME roma TEXT',now());

insert into article (person_id, name, text, created_at)
values (1,'SOME roma ARTICLE 1','SOME roma TEXT 1',now());

insert into article (person_id, name, text, created_at)
values (1,'SOME roma ARTICLE 2','SOME roma TEXT 2',now());

insert into article (person_id, name, text, created_at)
values (1,'SOME roma ARTICLE 3','SOME roma TEXT 3',now());

insert into article (person_id, name, text, created_at)
values (1,'SOME roma ARTICLE 4','SOME roma TEXT 4',now());

insert into article (person_id, name, text, created_at)
values (1,'SOME roma ARTICLE 5','SOME roma TEXT 5',now());

insert into article (person_id, name, text, created_at)
values (1,'SOME roma ARTICLE 6','SOME roma TEXT 6',now());
