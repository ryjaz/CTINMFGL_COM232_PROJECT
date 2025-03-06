CREATE DATABASE IF NOT EXISTS userdb;
USE userdb;


-- Create Users Table (Removed id and created_at columns)
CREATE TABLE IF NOT EXISTS adminuserdb (
    adminuname VARCHAR(100) UNIQUE NOT NULL,
    adminpword VARCHAR(255) NOT NULL
);

SELECT * FROM adminuserdb;

INSERT INTO adminuserdb 
VALUES ("admin","admin123");

 
 
	


