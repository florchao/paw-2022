CREATE TABLE IF NOT EXISTS users (
    userId INTEGER IDENTITY PRIMARY KEY,
    email varchar(100) UNIQUE NOT NULL,
    password varchar (100) NOT NULL,
    role INTEGER NOT NULL
);

create table if not exists employee(
    employeeID INT,
    name varchar(100),
    location varchar(100),
    availability varchar(100),
    experienceYears INT,
    abilities varchar(100),
     PRIMARY KEY (employeeID)

);

create table if not exists employer(
    employerID INT,
    name varchar(100),
    PRIMARY KEY (employerID)

);

CREATE TABLE IF NOT EXISTS profile_images
(
    image  BIT,
    userId INTEGER PRIMARY KEY

);

CREATE TABLE IF NOT EXISTS  experiences (
    experiencesID INTEGER IDENTITY PRIMARY KEY,
    employeeID INT,
    title varchar(1000),
    since DATE,
    until DATE,
    description varchar(1000),

);

CREATE TABLE IF NOT EXISTS contact (
    employeeID INT,
    employerID INT,
    message varchar(1000),
    phone varchar(1000),
    created DATE,
    PRIMARY KEY(employeeID, employerID)
);

create table if not exists jobs(
    jobId INTEGER IDENTITY PRIMARY KEY,
    employerID INT,
    title varchar(100),
    location varchar(100),
    availability varchar(100),
    experienceYears INT,
    abilities varchar(100),
    description varchar(100000),
);

create table if not exists applicants(
    employeeID INT NOT NULL,
    jobID INT NOT NULL,
    PRIMARY KEY(employeeID, jobID)
);
