create table STUDENTS (
   id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   first_name VARCHAR(20) default NULL,
   last_name  VARCHAR(20) default NULL,
   adress VARCHAR(240) default NULL,
   PRIMARY KEY (id)
);

