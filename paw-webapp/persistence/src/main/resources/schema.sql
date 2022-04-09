CREATE TABLE IF NOT EXISTS users (
                userId SERIAL PRIMARY KEY,
                username TEXT UNIQUE NOT NULL
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