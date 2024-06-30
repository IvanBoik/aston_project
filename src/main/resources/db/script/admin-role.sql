INSERT INTO role(id,name) VALUES (3,'ROLE_ADMIN');
UPDATE "user" SET role_id=3 WHERE id = 4;