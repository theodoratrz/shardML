CREATE TABLE users (
    id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    country_id INT,
    created_at TIMESTAMP
);

CREATE TABLE posts (
    id INT NOT NULL,
    user_id INT NOT NULL,
    content TEXT,
    created_at TIMESTAMP,
    PRIMARY KEY (user_id, id)
);

CREATE TABLE comments (
    id INT NOT NULL,
    post_id INT NOT NULL,
    post_user_id INT NOT NULL,
    user_id INT,
    body TEXT,
    created_at TIMESTAMP,
    PRIMARY KEY (post_user_id, id)
);

CREATE TABLE likes (
    id INT NOT NULL,
    user_id INT NOT NULL,
    post_id INT,
    created_at TIMESTAMP,
    PRIMARY KEY (user_id, id)
);

CREATE TABLE countries (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) UNIQUE
);
