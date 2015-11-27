INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (3, 'ROLE_GUEST');

INSERT INTO user (name, login, password) VALUES ('Gaurav', 'gaurav', 'password');

INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO jai_hind.article (ARTICLE_CONTENT, ARTICLE_NAME, ARTICLE_PUBLISHED, ARTICLE_CREATED, USER_ID)
  VALUE ('ArticleContent1', 'ArticleName1', 1, now(), 1);
INSERT INTO jai_hind.article (ARTICLE_CONTENT, ARTICLE_NAME, ARTICLE_PUBLISHED, ARTICLE_CREATED, USER_ID)
  VALUE ('ArticleContent2', 'ArticleName2', 1, now(), 1);
#insert into jai_hind.article value('ArticleContent2','ArticleName2',1, now(), 1);
#insert into jai_hind.article value('ArticleContent3','ArticleName3',1, now(), 1);


