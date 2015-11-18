INSERT INTO Media_Type (MEDIA_TYPE_NAME) VALUES ('Image');
INSERT INTO Media_Type (MEDIA_TYPE_NAME) VALUES ('Video');

#Article Table
INSERT INTO ARTICLE (ARTICLE_CONTENT, ARTICLE_CREATED, ARTICLE_NAME, ARTICLE_PUBLISHED)
VALUES ("Some Content", now(), "Some name", 1);

INSERT INTO ARTICLE (ARTICLE_CONTENT, ARTICLE_CREATED, ARTICLE_NAME, ARTICLE_PUBLISHED)
VALUES ("First Content", now(), "First name", 1);

INSERT INTO ARTICLE (ARTICLE_CONTENT, ARTICLE_CREATED, ARTICLE_NAME, ARTICLE_PUBLISHED)
VALUES ("Second Content", now(), "Second name", 1);


INSERT INTO user (id, name, login, password) VALUES (1, 'Roy', 'roy', 'spring');
INSERT INTO user (id, name, login, password) VALUES (2, 'Craig', 'craig', 'spring');
INSERT INTO user (id, name, login, password) VALUES (3, 'Greg', 'greg', 'spring');

INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (3, 'ROLE_GUEST');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO user_role (user_id, role_id) VALUES (3, 1);
