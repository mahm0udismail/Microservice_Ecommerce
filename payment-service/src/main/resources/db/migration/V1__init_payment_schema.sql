CREATE TABLE IF NOT EXISTS payments
(
    id             SERIAL PRIMARY KEY,
    reference      VARCHAR(255),
    amount         NUMERIC(38, 2),
    payment_method VARCHAR(50),
    order_id       INTEGER,
    created_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
