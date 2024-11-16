-- Tabla users --
CREATE TABLE users (
    user_id SERIAL NOT NULL,
    name CHAR(40) NOT NULL,
    email CHAR(50) NOT NULL,
	password CHAR(50) NOT NULL,
    PRIMARY KEY (user_id)
);

-- Tabla Task --
CREATE TABLE task (
	task_id SEIAL NOT NULL,
	user_id INT NOT NULL,
	title CHAR(50) NOT NULL,
	description CHAR(500),
	expiration_date CHAR(10) NOT NULL,
	state CHAR(10) NOT NULL,
	PRIMARY KEY (task_id)
);