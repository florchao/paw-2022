CREATE TABLE IF NOT EXISTS users (
                userId SERIAL PRIMARY KEY,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
                );

create table if not exists employee(
    employeeID INT,
    name TEXT,
    location TEXT,
    availability TEXT,
    experienceYears INT,
    abilities TEXT,
    FOREIGN KEY (employeeID) REFERENCES users(userID) ON DELETE CASCADE,
    PRIMARY KEY (employeeID)

);


CREATE TABLE IF NOT EXISTS  experiences (
                experiencesID SERIAL PRIMARY KEY,
                employeeID INT,
                title TEXT,
                since DATE,
                until DATE,
                description TEXT,
                FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS  contact (
                employeeID INT,
                employerID INT,
                message TEXT,
                created DATE,
                FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE,
                FOREIGN KEY (employerID) REFERENCES users(userid) ON DELETE CASCADE,
                PRIMARY KEY (employeeID, employerID)
    );