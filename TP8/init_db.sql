DROP DATABASE IF EXISTS testdb;
CREATE DATABASE testdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE testdb;
CREATE TABLE user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    email VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO user(nom, email) VALUES
('Found Mazen', 'foundmazen@gmail.com'),
('Badr Samih', 'badrsamih@gmail.com'),
('Marwa Falouh', 'marwafalouh@gmail.com');
SELECT '✅ Base réinitialisée avec succès !' as Message;
SELECT * FROM user;
