CREATE TABLE customers (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    created_at TIMESTAMP
);

CREATE TABLE accounts (
    id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    balance DECIMAL,
    created_at TIMESTAMP,
    PRIMARY KEY (customer_id, id)
);

CREATE TABLE transactions (
    id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    amount DECIMAL NOT NULL,
    created_at TIMESTAMP,
    PRIMARY KEY (customer_id, id)
);

CREATE TABLE cards (
    id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    card_number VARCHAR(255),
    expires_at TIMESTAMP,
    PRIMARY KEY (customer_id, id)
);
