-- Create Database
CREATE DATABASE IF NOT EXISTS LMS;
USE LMS;

-- Create Books Table
CREATE TABLE Books (
    BookID INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Author VARCHAR(255) NOT NULL,
    ISBN VARCHAR(13) UNIQUE NOT NULL,
    Available BOOLEAN DEFAULT TRUE
);

-- Create Students Table
CREATE TABLE Students (
    StudentID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL
);

-- Create BorrowedBooks Table
CREATE TABLE BorrowedBooks (
    BorrowID INT AUTO_INCREMENT PRIMARY KEY,
    BookID INT,
    StudentID INT,
    BorrowDate DATE,
    ReturnDate DATE,
    FOREIGN KEY (BookID) REFERENCES Books(BookID),
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID)
);

-- Insert Sample Books
INSERT INTO Books (Title, Author, ISBN) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565'),
('To Kill a Mockingbird', 'Harper Lee', '9780061120084'),
('1984', 'George Orwell', '9780451524935');

-- Insert Sample Students
INSERT INTO Students (Name, Email) VALUES
('Alice Johnson', 'alice.johnson@example.com'),
('Bob Smith', 'bob.smith@example.com'),
('Charlie Brown', 'charlie.brown@example.com');

-- Borrow a Book
INSERT INTO BorrowedBooks (BookID, StudentID, BorrowDate) VALUES
(1, 1, '2023-10-01');

-- Return a Book
UPDATE BorrowedBooks
SET ReturnDate = '2023-10-15'
WHERE BorrowID = 1;

-- Update Book Availability to False when Borrowed
UPDATE Books
SET Available = FALSE
WHERE BookID = 1;

-- Update Book Availability to True when Returned
UPDATE Books
SET Available = TRUE
WHERE BookID = 1;
