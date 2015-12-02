INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (3, 'ROLE_GUEST');

INSERT INTO user (name, login, password) VALUES ('Gaurav', 'gaurav', 'password');

INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO jai_hind.article (ARTICLE_CONTENT, ARTICLE_NAME, ARTICLE_PUBLISHED, ARTICLE_CREATED, USER_ID, PUBLISHED_DATE, CATEGORY)
  VALUE ('ArticleContent1', 'ArticleName1', 1, now(), 1, now(), "desh");
INSERT INTO jai_hind.article (ARTICLE_CONTENT, ARTICLE_NAME, ARTICLE_PUBLISHED, ARTICLE_CREATED, USER_ID, PUBLISHED_DATE, CATEGORY)
  VALUE ('ArticleContent2', 'ArticleName2', 1, now(), 1, now(), "desh");
INSERT INTO jai_hind.article (ARTICLE_CONTENT, ARTICLE_NAME, ARTICLE_PUBLISHED, ARTICLE_CREATED, USER_ID, PUBLISHED_DATE, CATEGORY)
  VALUE ('ArticleContent3', 'ArticleName3', 1, now(), 1, now(), "desh");
INSERT INTO jai_hind.article (ARTICLE_CONTENT, ARTICLE_NAME, ARTICLE_PUBLISHED, ARTICLE_CREATED, USER_ID, PUBLISHED_DATE, CATEGORY)
  VALUE ('ArticleContent4', 'ArticleName4', 1, now(), 1, now(), "desh");
INSERT INTO jai_hind.article (ARTICLE_CONTENT, ARTICLE_NAME, ARTICLE_PUBLISHED, ARTICLE_CREATED, USER_ID, PUBLISHED_DATE, CATEGORY)
  VALUE ('ArticleContent5', 'ArticleName5', 1, now(), 1, now(), "desh");

