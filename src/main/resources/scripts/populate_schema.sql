use projectservlet;
select * from users;
SET FOREIGN_KEY_CHECKS=0;
insert into users(username, password, authority, enabled)
values('admin', '$2a$10$9yv2nXQ4hUg3pTEtOXerI.w9bZkiHiKY.9ZDD9AROK2KS6v7i7BOi',
'ROLE_ADMIN', true);
SET FOREIGN_KEY_CHECKS=1;