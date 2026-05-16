CREATE DATABASE student_management;
USE student_management;

CREATE TABLE IF NOT EXISTS admin_users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Default admin credentials: admin / admin123
INSERT INTO admin_users (username, password)
VALUES ('admin', 'admin123')
ON DUPLICATE KEY UPDATE username = username;

-- ============================================================
-- Table: students
-- ============================================================
CREATE TABLE IF NOT EXISTS students (
    student_id  INT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    phone       VARCHAR(15)  NOT NULL,
    department  VARCHAR(100) NOT NULL,
    year        TINYINT      NOT NULL CHECK (year BETWEEN 1 AND 4),
    cgpa        DECIMAL(4,2) NOT NULL CHECK (cgpa BETWEEN 0.00 AND 10.00),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- Sample Data
-- ============================================================
INSERT INTO students (full_name, email, phone, department, year, cgpa) VALUES
('Aarav Sharma',    'aarav.sharma@example.com',    '9876543210', 'Computer Science',    3, 8.75),
('Priya Patel',     'priya.patel@example.com',     '8765432109', 'Information Technology', 2, 9.10),
('Rohan Mehta',     'rohan.mehta@example.com',     '7654321098', 'Electronics',         4, 7.90),
('Sneha Gupta',     'sneha.gupta@example.com',     '6543210987', 'Mechanical',          1, 8.20),
('Karan Singh',     'karan.singh@example.com',     '9988776655', 'Civil Engineering',   3, 7.55),
('Ananya Reddy',    'ananya.reddy@example.com',    '9123456789', 'Computer Science',    2, 9.40),
('Vikram Joshi',    'vikram.joshi@example.com',    '8901234567', 'Information Technology', 4, 8.00),
('Deepika Nair',    'deepika.nair@example.com',    '7890123456', 'Electronics',         1, 7.80);

-- ============================================================


ALTER USER 'root'@'localhost' IDENTIFIED BY 'meet123';
FLUSH PRIVILEGES;

USE student_management;

SELECT * FROM admin_users
WHERE username='admin'
AND password='admin123';