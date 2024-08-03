CREATE DATABASE SubwayScreenDB;

USE SubwayScreenDB;

CREATE TABLE Advertisements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    media_type ENUM('PDF', 'MPG', 'JPEG', 'BMP') NOT NULL,
    media_path VARCHAR(255) NOT NULL,
    display_duration INT NOT NULL DEFAULT 10,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO Advertisements (title, media_type, media_path)
VALUES 
    ('Fashion Sale', 'JPEG', './/media//Fashion_Sale.jpeg'),
    ('Fitness Center Membership', 'JPEG', './/media//Fitness_Center_Membership.jpeg'),
    ('Restaurant Promotion', 'JPEG', './/media//Restaurant_Promotion.jpeg'),
    ('Tech Product Launch', 'JPEG', './/media//Tech_Product_Launch.jpeg'),
    ('Travel Agency Deal', 'JPEG', './/media//Travel_Agency_Deal.jpeg');

