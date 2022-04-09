CREATE TABLE IF NOT EXISTS users (
                userId SERIAL PRIMARY KEY,
                email TEXT UNIQUE NOT NULL
--                 password TEXT NOT NULL
                );

CREATE TABLE IF NOT EXISTS employee(
                employeeID INT,
                name TEXT,
                location TEXT,
                availability TEXT,
                FOREIGN KEY (employeeID) REFERENCES users(userId) ON DELETE CASCADE,
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