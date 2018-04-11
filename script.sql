CREATE TABLE laboratory
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    title VARCHAR(50) NOT NULL,
    curricula VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    lab_nr INT NOT NULL
);

CREATE TABLE user
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password CHAR(128) NOT NULL,
    role ENUM('ADMIN','STUDENT') NOT NULL
);
CREATE UNIQUE INDEX user_email_uindex ON user (email);

CREATE TABLE student
(
  id           INT AUTO_INCREMENT
    PRIMARY KEY,
  group_nr     CHAR(5)     NOT NULL,
  hobby        VARCHAR(30) NULL,
  password_set BIT         NOT NULL,
  CONSTRAINT student_user_id_fk
  FOREIGN KEY (id) REFERENCES user (id)
)

CREATE TABLE attendance
(
    lab_id INT NOT NULL,
    student_id INT NOT NULL,
    attended BIT NOT NULL,
    CONSTRAINT lab_id FOREIGN KEY (lab_id) REFERENCES laboratory (id),
    CONSTRAINT student_id FOREIGN KEY (student_id) REFERENCES student (id)
);

CREATE TABLE assignment
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    deadline DATE NOT NULL,
    description TEXT NOT NULL,
    lab_id INT NOT NULL,
    CONSTRAINT lab_id FOREIGN KEY (lab_id) REFERENCES laboratory (id);
);

CREATE TABLE submission
(
    student_id INT NOT NULL,
    assignment_id INT NOT NULL,
    date DATE NOT NULL,
    description TEXT NOT NULL,
    grade INT,
    CONSTRAINT student_fk FOREIGN KEY (student_id) REFERENCES student (id),
    CONSTRAINT assignment_fk FOREIGN KEY (assignment_id) REFERENCES assignment (id)
);
