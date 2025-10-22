CREATE USER IF NOT EXISTS 'ecom_user'@'%' IDENTIFIED BY 'ecompass';
GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecom_user'@'%';
FLUSH PRIVILEGES;