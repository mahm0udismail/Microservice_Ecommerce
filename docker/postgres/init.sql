-- This script runs automatically when the postgres container starts for the first time.
-- It creates the three separate databases needed by the microservices.

CREATE DATABASE product_db;
CREATE DATABASE order_db;
CREATE DATABASE payment_db;
