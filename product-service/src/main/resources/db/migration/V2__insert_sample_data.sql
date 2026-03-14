INSERT INTO category (name, description) VALUES
('Electronics', 'Electronic devices and accessories'),
('Clothing', 'Apparel and fashion items'),
('Books', 'Books and educational materials');

INSERT INTO product (name, description, available_quantity, price, category_id) VALUES
('Laptop Pro 15', 'High performance laptop with 16GB RAM', 50, 1299.99, 1),
('Wireless Headphones', 'Noise cancelling bluetooth headphones', 100, 199.99, 1),
('Spring Boot in Action', 'Comprehensive guide to Spring Boot', 200, 49.99, 3),
('Running Shoes', 'Lightweight running shoes', 75, 89.99, 2),
('USB-C Hub', '7-in-1 USB-C hub with 4K HDMI', 150, 59.99, 1);
