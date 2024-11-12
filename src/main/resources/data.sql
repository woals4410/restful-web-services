INSERT INTO user_details(id, birth_date, name)
VALUES(1001, current_date(), 'JJAM');

INSERT INTO user_details(id, birth_date, name)
VALUES(1002, current_date(), 'JJI');

INSERT INTO user_details(id, birth_date, name)
VALUES(1003, current_date(), 'RANGA');

INSERT INTO post(id, description, user_id)
VALUES(2001, 'I want to get a JOB!', 1001);

INSERT INTO post(id, description, user_id)
VALUES(2002, 'I want to get a JOB too~', 1002);

INSERT INTO post(id, description, user_id)
VALUES(2003, 'I want to Learn AWS', 1001);

INSERT INTO post(id, description, user_id)
VALUES(2004, 'I want to Learn AI', 1002);