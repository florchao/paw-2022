CREATE TABLE IF NOT EXISTS users (
                userId INTEGER IDENTITY PRIMARY KEY,
                email varchar(100) UNIQUE NOT NULL
--                 password varchar (100) NOT NULL
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


CREATE TABLE IF NOT EXISTS  experiences (
                experiencesID INTEGER IDENTITY PRIMARY KEY,
                employeeID INT,
                title varchar(1000),
                since DATE,
                until DATE,
                description varchar(1000),
                FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE

);
