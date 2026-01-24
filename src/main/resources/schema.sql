CREATE TABLE IF NOT EXISTS transactions
(
    id         VARCHAR(36) PRIMARY KEY,
    user_id    VARCHAR(36)    NOT NULL,
    amount     NUMERIC(19, 2) NOT NULL,
    currency   VARCHAR(3)     NOT NULL,
    country    VARCHAR(20),
    device_id  VARCHAR(255),
    status     VARCHAR(50)    NOT NULL,
    score      INTEGER        NOT NULL,
    created_at TIMESTAMP      NOT NULL
);