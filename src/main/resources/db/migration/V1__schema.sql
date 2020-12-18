drop table if exists list;
drop table if exists task;

CREATE TABLE list (
   id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   title               VARCHAR(50) NOT NULL
);

CREATE TABLE task (
   id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   description         VARCHAR(255) NOT NULL,
   list_id         	   BIGINT NOT NULL,
   status              VARCHAR(50) NOT NULL,
   FOREIGN KEY (list_id) REFERENCES list (id) ON DELETE RESTRICT ON UPDATE CASCADE
);