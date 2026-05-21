CREATE DATABASE IF NOT EXISTS library_system;
USE library_system;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    student_no VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_level ENUM('NORMAL', 'VIP') NOT NULL DEFAULT 'NORMAL',
    status ENUM('ACTIVE', 'SUSPENDED') NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    authors VARCHAR(255),
    subjects VARCHAR(255),
    publisher VARCHAR(255),
    publish_year VARCHAR(10),
    edition VARCHAR(50),
    format_desc VARCHAR(100),
    source VARCHAR(100),
    note TEXT,
    status ENUM('AVAILABLE', 'BORROWED') NOT NULL DEFAULT 'AVAILABLE'
);

CREATE TABLE book_isbns (
    isbn_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    isbn VARCHAR(20),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

CREATE TABLE borrow_records (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATETIME NOT NULL,
    due_date DATETIME NOT NULL,
    return_date DATETIME,
    borrow_days INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);