create table CAR_ADDITION (
   id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   addition_name VARCHAR(20) default NULL,
   addition_type  VARCHAR(100) default NULL,
   car_id  INT default NULL,
   PRIMARY KEY (id)
);

