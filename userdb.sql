CREATE DATABASE IF NOT EXISTS userdb;
USE userdb;

-- Drop the table if it already exists (optional)
DROP TABLE IF EXISTS users;

-- Create Users Table (Removed id and created_at columns)
CREATE TABLE IF NOT EXISTS users (
    Username VARCHAR(100) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL
);

-- Insert User (Avoiding Duplicates)
INSERT INTO users (username, password)
SELECT 'user', SHA2('securepassword', 256)
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user');

-- Insert 'danielpadilla' with password 'summer' (Plaintext)
INSERT INTO users (username, password)
SELECT 'danielpadilla', 'summer'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'danielpadilla');

-- Insert 'kathrynb' with password 'winter' (Plaintext)
INSERT INTO users (username, password)
SELECT 'kathrynb', 'winter'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'kathrynb');

-- Authenticate Multiple Users (Plaintext Password Comparison)
SELECT * FROM users 
WHERE (username = 'danielpadilla' AND password = 'summer')
   OR (username = 'kathrynb' AND password = 'winter');
