CREATE DATABASE cms_db;
GO

USE cms_db;
GO

CREATE TABLE Member (
    id INT PRIMARY KEY IDENTITY(1,1), 
    Firstname NVARCHAR(50),
    Lastname NVARCHAR(50),
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    Phone VARCHAR(20),
    Email VARCHAR(50) NOT NULL,
    Description NVARCHAR(200),
    CreatedDate DATETIME DEFAULT GETDATE(),
    UpdateTime DATETIME DEFAULT GETDATE()   
);

CREATE TABLE Content (
    id INT PRIMARY KEY IDENTITY(1,1), 
    Title NVARCHAR(200) NOT NULL,     
    Brief NVARCHAR(500) NOT NULL,     
    Content NVARCHAR(MAX) NOT NULL,   
    CreateDate DATETIME DEFAULT GETDATE(),
    UpdateTime DATETIME DEFAULT GETDATE(),
    Sort VARCHAR(10),                
    AuthorId INT NOT NULL,            
    
    CONSTRAINT FK_Content_Member FOREIGN KEY (AuthorId) 
    REFERENCES Member(id) ON DELETE CASCADE
);

CREATE TRIGGER trg_UpdateContentTime
ON Content
AFTER UPDATE
AS
BEGIN
    IF NOT UPDATE(UpdateTime)
    BEGIN
        UPDATE Content
        SET UpdateTime = GETDATE()
        FROM Content 
        INNER JOIN inserted i ON Content.id = i.id;
    END
END;