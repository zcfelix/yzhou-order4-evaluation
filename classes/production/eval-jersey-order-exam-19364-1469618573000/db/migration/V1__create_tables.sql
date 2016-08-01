CREATE TABLE users (
  id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  role VARCHAR(255),
  password varchar(255)
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL
);