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
    FOREIGN KEY (employeeID) REFERENCES users(userID) ON DELETE CASCADE,
    PRIMARY KEY (employeeID)

);

create table if not exists employer(
    employerID INT,
    name varchar(100),
    FOREIGN KEY (employerID) REFERENCES users(userID) ON DELETE CASCADE,
    PRIMARY KEY (employerID)

);


CREATE TABLE IF NOT EXISTS  experiences (
                experiencesID INTEGER IDENTITY PRIMARY KEY,
                employeeID INT,
                title varchar(1000),
                since DATE,
                until DATE,
                description varchar(1000),
                FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS contact (
    employeeID INT,
    employerID INT,
    message varchar(1000),
    phone varchar(1000),
    created DATE,
    FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE,
    FOREIGN KEY (employerID) REFERENCES users(userid) ON DELETE CASCADE,
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
    FOREIGN KEY (employerID) REFERENCES employer(employerID) ON DELETE CASCADE
);
