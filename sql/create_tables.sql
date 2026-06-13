CREATE DATABASE IF NOT EXISTS library_system;
USE library_system;

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    student_no VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role_level ENUM('NORMAL','VIP') NOT NULL DEFAULT 'NORMAL',
    status ENUM('ACTIVE','SUSPENDED') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    authors VARCHAR(255),
    subjects VARCHAR(255),
    publisher VARCHAR(255),
    publish_year VARCHAR(20),
    edition VARCHAR(100),
    format_desc VARCHAR(100),
    source VARCHAR(255),
    note TEXT,
    status ENUM('AVAILABLE','BORROWED','REMOVED') NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS book_isbns (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    isbn VARCHAR(50) NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

CREATE TABLE IF NOT EXISTS borrow_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATETIME NOT NULL,
    due_date DATETIME NOT NULL,
    return_date DATETIME NULL,
    borrow_days INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

INSERT IGNORE INTO admins (username, password) VALUES ('admin', '1234');

INSERT IGNORE INTO users (student_no, name, password, role_level, status)
VALUES ('A12345678', '王小明', '1234', 'NORMAL', 'ACTIVE'),
       ('B12345678', '張家豪', '1234', 'VIP', 'ACTIVE');

INSERT INTO books (title, authors, subjects, publisher, publish_year, edition, format_desc, source, note, status)
SELECT 'Design as Art', 'Bruno Munari', '藝術設計', 'Penguin', '2021', '初版', 'Paperback', '測試資料', 'Demo book', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'Design as Art');

INSERT INTO books (title, authors, subjects, publisher, publish_year, edition, format_desc, source, note, status)
SELECT 'The Architecture of Science', '王大明', '資訊科技', 'Springer', '2021', '初版', 'Hardcover', '測試資料', 'Demo book', 'AVAILABLE'
WHERE NOT EXISTS (SELECT 1 FROM books WHERE title = 'The Architecture of Science');
