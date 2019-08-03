
DROP TABLE IF EXISTS STUDENT;

CREATE TABLE STUDENT
(
   id integer NOT NULL,
   name varchar(64) NOT NULL,
   email varchar(64) NOT NULL,
   constraint student_pk primary key(id)
)
;

INSERT INTO STUDENT (id,name,email) VALUES (1,'james' ,'james@yahoo.com');
INSERT INTO STUDENT (id,name,email) VALUES (2,'kevin' ,'kevin@yahoo.com');
INSERT INTO STUDENT (id,name,email) VALUES (3,'ricky' ,'ricky@yahoo.com');