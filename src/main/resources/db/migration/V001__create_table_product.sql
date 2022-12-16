CREATE TABLE product (
     id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(50) NOT NULL,
     photo longblob,
     description VARCHAR(50) NOT NULL,
     active BOOLEAN NOT NULL,
     price DOUBLE NOT NULL
);