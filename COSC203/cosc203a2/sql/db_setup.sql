-- create user
CREATE USER 'cosc203' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'cosc203' WITH GRANT OPTION;

-- create database
DROP TABLE IF EXISTS Photos;
DROP TABLE IF EXISTS Bird;
DROP TABLE IF EXISTS ConservationStatus;


DROP DATABASE ASGN2;

CREATE DATABASE ASGN2;
USE ASGN2;

-- create tables
CREATE TABLE ConservationStatus (
    status_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(255) NOT NULL,
    status_colour CHAR(7) NOT NULL
);


CREATE TABLE Bird (
    bird_id INT AUTO_INCREMENT,
    primary_name VARCHAR(255),
    english_name VARCHAR(255),
    scientific_name VARCHAR(255),
    order_name VARCHAR(255),
    family VARCHAR(255),
    weight INT,
    length INT,
    status_id INT, 
    CONSTRAINT bird_pk PRIMARY KEY (bird_id, status_id),
    CONSTRAINT bird_status_fk FOREIGN KEY (status_id) REFERENCES ConservationStatus(status_id)
);


CREATE TABLE Photos (
    photo_id INT AUTO_INCREMENT,
    bird_id INT,
    filename VARCHAR(255),
    photographer VARCHAR(255),
    CONSTRAINT photo_pk PRIMARY KEY (photo_id, bird_id),
    CONSTRAINT photo_bird_fk FOREIGN KEY (bird_id) REFERENCES Bird(bird_id)
);

