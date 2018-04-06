CREATE TABLE laboratory
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    title VARCHAR(50) NOT NULL,
    curricula VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    lab_nr INT NOT NULL
);
