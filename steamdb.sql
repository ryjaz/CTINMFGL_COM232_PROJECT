
CREATE DATABASE SteamDB;
USE SteamDB;

-- ------------------------------------------ User Table
CREATE TABLE User (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL
);

INSERT INTO User (Username, Password)
VALUES ("danielpadilla", "summer"),
	   ("kathrynb", "winter");

SELECT * FROM User;

-- ------------------------------------------- Admin Table
CREATE TABLE Admin (
    AdminID INT PRIMARY KEY AUTO_INCREMENT,
    AdminUname VARCHAR(50) UNIQUE NOT NULL,
    AdminPword VARCHAR(255) NOT NULL
);

INSERT INTO Admin (AdminUname, AdminPword)
VALUES ("admin","admin123");

SELECT * FROM Admin;

-- ------------------------------------------- Game Table
CREATE TABLE Game (
    GameID INT PRIMARY KEY AUTO_INCREMENT,
    GameName VARCHAR(50) NOT NULL,
    OriginalPrice INT,
    DiscountPercent INT, 
    FinalAmount INT,
    AdminID INT,
    FOREIGN KEY (AdminID) REFERENCES Admin(AdminID) ON DELETE SET NULL
);

-- ----------- For discount price ang delimiter
DELIMITER //

CREATE TRIGGER before_game_insert
BEFORE INSERT ON Game
FOR EACH ROW
BEGIN
    SET NEW.FinalAmount = NEW.OriginalPrice - (NEW.OriginalPrice * NEW.DiscountPercent / 100);
END; //

CREATE TRIGGER before_game_update
BEFORE UPDATE ON Game
FOR EACH ROW
BEGIN
    SET NEW.FinalAmount = NEW.OriginalPrice - (NEW.OriginalPrice * NEW.DiscountPercent / 100);
END; //

DELIMITER ;

INSERT INTO Game (GameName, OriginalPrice, DiscountPercent, AdminID) 
VALUES ('Hello Kitty Island Adventure', 3900, 50, 1),
	   ('Hello Kitty Island Adventure', 3900, 25, 1),
       ('Hello Kitty Island Adventure', 3900, 10, 1),
       ('Crossfire', 899, 69, 1),
       ('Crossfire', 899, 20, 1),
       ('Crossfire', 899, 5, 1),
       ('Sims', 199 , 20, 1),
       ('Sims', 199 , 10, 1),
       ('Sims', 199 , 2, 1),
	   ('Minecraft', 1500, 10, 1),
	   ('Minecraft', 1500, 50, 1),
	   ('Minecraft', 1500, 100, 1);
	

SELECT * FROM Game;

-- ------------------------------------------------------ Library Table

CREATE TABLE `Library` (
    LibraryID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    GameID INT,
    TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP
    FOREIGN KEY (UserID) REFERENCES `User`(UserID) ON DELETE CASCADE,
    FOREIGN KEY (GameID) REFERENCES Game(GameID) ON DELETE CASCADE
);

-- Inserting into Library Table
INSERT INTO Library (UserID, GameID)
VALUES (1, 1);  -- Assuming UserID 1 and GameID 1 exist

-- Query with JOIN (Displays GameName)
SELECT l.LibraryID, u.Username, g.GameName, l.TimeStamp
FROM `Library` l
JOIN `User` u ON l.UserID = u.UserID
JOIN Game g ON l.GameID = g.GameID;


SELECT * FROM `Library`;

-- ------------------------------------------------------ Transaction Table
CREATE TABLE Transaction (
    TransactionID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    GameID INT,
    FinalAmount INT,  -- This will store the final amount for the transaction
    Date DATE NOT NULL,
    PaymentMethod VARCHAR(50) NOT NULL,  -- GCash, PayMaya, Credit Card
    AccountNumber VARCHAR(50) NOT NULL, -- The user's account number
    AccountPassword VARCHAR(255) NOT NULL, -- Password for verification
    AdminID INT,
    FOREIGN KEY (GameID) REFERENCES Game(GameID) ON DELETE CASCADE,
    FOREIGN KEY (UserID) REFERENCES User(UserID) ON DELETE CASCADE,
    FOREIGN KEY (AdminID) REFERENCES Admin(AdminID) ON DELETE SET NULL
);


INSERT INTO Transaction (UserID, GameID, FinalAmount, Date, PaymentMethod, AccountNumber, AccountPassword, AdminID)
VALUES (
    1,  -- UserID
    1,  -- GameID
    (SELECT FinalAmount FROM Game WHERE GameID = 1),  -- Retrieve FinalAmount from Game table
    CURDATE(),  -- Current date
    'Credit Card',  -- Payment method
    '1234567890',  -- Account number
    'securepassword',  -- Account password
    NULL  -- AdminID (if applicable)
);







