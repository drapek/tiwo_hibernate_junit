create table CAR (
   id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   brand_name VARCHAR(20) default NULL,
   model_name  VARCHAR(20) default NULL,
   production_year  INT default NULL,
   prize  FLOAT default NULL,
   engine_type  VARCHAR(100) default NULL,
   PRIMARY KEY (id)
);

