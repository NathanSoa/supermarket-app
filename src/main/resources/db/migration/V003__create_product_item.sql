CREATE TABLE product_item(
     id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
     quantity INT,
     product_id BIGINT(20) NOT NULL,
     user_id BIGINT(20) NOT NULL,
     FOREIGN KEY (product_id) REFERENCES product(id),
     FOREIGN KEY (user_id) REFERENCES app_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;