CREATE TABLE IF NOT EXISTS users (
                userId INTEGER IDENTITY PRIMARY KEY,
                username varchar(100) UNIQUE NOT NULL
--                 password varchar (100) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee(
                employeeID INT,
                name varchar(1000),
                location varchar(1000),
                availability varchar(1000),
                FOREIGN KEY (employeeID) REFERENCES users(userId) ON DELETE CASCADE,
                PRIMARY KEY (employeeID)
);

CREATE TABLE IF NOT EXISTS  experiences (
                experiencesID SERIAL,
                employeeID INT,
                title TEXT,
                since DATE,
                until DATE,
                description TEXT,
                FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE,
                PRIMARY KEY (experiencesID)
);
