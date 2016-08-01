CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    user_id INT NOT NULL ,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL ,
    total_price DOUBLE NOT NULL ,
    time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orderitems (
    order_id INT NOT NULL ,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    amount DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE payments (
    order_id INT NOT NULL ,
    pay_type VARCHAR (255) NOT NULL,
    amount DOUBLE NOT NULL,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);