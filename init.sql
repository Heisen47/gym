CREATE DATABASE IF NOT EXISTS gym_management;
CREATE USER IF NOT EXISTS 'springuser'@'%' IDENTIFIED WITH mysql_native_password BY 'springuser';
GRANT ALL PRIVILEGES ON gym_management.* TO 'springuser'@'%';
FLUSH PRIVILEGES;