CREATE TABLE customers (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    created_at TIMESTAMP
);

CREATE TABLE accounts (
    id BIGINT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    balance DECIMAL,
    created_at TIMESTAMP
);

CREATE TABLE transactions (
    id BIGINT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    amount DECIMAL NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE cards (
    id BIGINT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    card_number VARCHAR(255) UNIQUE,
    expires_at TIMESTAMP
);
