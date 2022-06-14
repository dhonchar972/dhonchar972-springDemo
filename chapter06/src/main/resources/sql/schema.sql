CREATE DATABASE musicdb;

CREATE TABLE musicdb.SINGER (
    ID INT NOT NULL AUTO_INCREMENT
    , FIRST_NAME VARCHAR(60) NOT NULL
    , LAST_NAME VARCHAR(40) NOT NULL
    , BIRTH_DATE DATE
    , UNIQUE (FIRST_NAME, LAST_NAME)
    , PRIMARY KEY (ID)
);

CREATE TABLE musicdb.ALBUM (
    ID INT NOT NULL AUTO_INCREMENT
    , SINGER_ID INT NOT NULL
    , TITLE VARCHAR(100) NOT NULL
    , RELEASE_DATE DATE
    , UNIQUE (SINGER_ID, TITLE)
    , PRIMARY KEY (ID)
    , CONSTRAINT FK_ALBUM
       FOREIGN KEY (SINGER_ID)
           REFERENCES SINGER (ID)
           ON DELETE CASCADE
);