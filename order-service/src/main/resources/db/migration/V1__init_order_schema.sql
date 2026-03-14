CREATE TABLE IF NOT EXISTS orders
(
    id             SERIAL PRIMARY KEY,
    reference      VARCHAR(255),
    total_amount   NUMERIC(38, 2),
    payment_method VARCHAR(50),
    customer_id    VARCHAR(255),
    created_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_lines
(
    id         SERIAL PRIMARY KEY,
    order_id   INTEGER REFERENCES orders (id),
    product_id INTEGER,
    quantity   DOUBLE PRECISION
);
