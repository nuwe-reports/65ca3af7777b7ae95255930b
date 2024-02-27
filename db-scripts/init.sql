CREATE TABLE IF NOT EXISTS doctors (
    id INT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    email VARCHAR(255),
    UNIQUE KEY email_unique (email)
    );

CREATE TABLE IF NOT EXISTS patient (
    id INT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    email VARCHAR(255),
    UNIQUE KEY email_unique (email)
    );
    
CREATE TABLE IF NOT EXISTS room (
    room_name VARCHAR(255) PRIMARY KEY
    );
    
CREATE TABLE IF NOT EXISTS appointment (
    id INT PRIMARY KEY,
    starts_at TIMESTAMP NOT NULL,
    finishes_at TIMESTAMP NOT NULL,
    doctor_id INT,
    patient_id INT,
    room_name VARCHAR(255),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (patient_id) REFERENCES patient(id),
    FOREIGN KEY (room_name) REFERENCES room(room_name)
    );

INSERT INTO doctors (id, first_name, last_name, age, email)
VALUES(1, 'Perla', 'Amalia', 24, 'p.amalia@hospital.accwe'),
(2, 'Miren', 'Iniesta', 24, 'm.iniesta@hospital.accwe');

INSERT INTO patient (id, first_name, last_name, age, email) 
VALUES(1, 'Jose Luis', 'Olaya', 37, 'j.olaya@email.com'),
(2, 'Paulino', 'Antunez', 37, 'p.antunez@email.com');

INSERT INTO room (room_name)
VALUES('Dermatology'),
('Traumatology');

INSERT INTO appointment (id, starts_at, finishes_at, doctor_id, patient_id, room_name) 
VALUES(1, '2024-02-14 10:00:00', '2024-02-14 11:00:00', 1, 1, 'Dermatology');