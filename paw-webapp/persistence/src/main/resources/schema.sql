CREATE TABLE IF NOT EXISTS users (
                userId SERIAL PRIMARY KEY,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                role INT NOT NULL
                );

create table if not exists employee(
    employeeID INT,
    name TEXT,
    location TEXT,
    availability TEXT,
    experienceYears INT,
    hourlyFee INT,
    abilities TEXT,
    voteCount INT,
    rating float4,
    FOREIGN KEY (employeeID) REFERENCES users(userID) ON DELETE CASCADE,
    PRIMARY KEY (employeeID)

);

create table if not exists employer(
    employerID INT,
    name TEXT,
    FOREIGN KEY (employerID) REFERENCES users(userID) ON DELETE CASCADE,
    PRIMARY KEY (employerID)
);

CREATE TABLE IF NOT EXISTS  contact (
                employeeID INT,
                employerID INT,
                message TEXT,
                phone TEXT,
                created DATE,
                FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE,
                FOREIGN KEY (employerID) REFERENCES users(userid) ON DELETE CASCADE,
                PRIMARY KEY (employeeID, employerID)
    );

CREATE TABLE IF NOT EXISTS profile_images
(
    image  BYTEA,
    userId INTEGER PRIMARY KEY ,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE

    );

create table if not exists jobs(
    jobID SERIAL PRIMARY KEY,
    employerID INT,
    title TEXT,
    location TEXT,
    availability TEXT,
    experienceYears INT,
    abilities TEXT,
    description TEXT,
    opened BOOLEAN,
    FOREIGN KEY (employerID) REFERENCES employer(employerID) ON DELETE CASCADE
);

create table if not exists applicants(
    employeeID INT NOT NULL,
    jobID INT NOT NULL,
    FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE,
    FOREIGN KEY (jobID) REFERENCES jobs(jobid) ON DELETE CASCADE,
    PRIMARY KEY(employeeID, jobID)
);

create table if not exists review(
    reviewID SERIAL PRIMARY KEY,
    employeeId INT NOT NULL,
    employerId INT NOT NULL,
    review TEXT,
    created DATE,
    forEmployee INT,
    FOREIGN KEY (employeeId) REFERENCES employee(employeeid) ON DELETE CASCADE,
    FOREIGN KEY (employerId) REFERENCES employer(employerid) ON DELETE CASCADE,
    UNIQUE (employeeId, employerId, forEmployee)
);

CREATE TABLE IF NOT EXISTS  ratings (
        employeeID INT,
        employerID INT,
        rating INT,
        FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE,
        FOREIGN KEY (employerID) REFERENCES users(userid) ON DELETE CASCADE,
        PRIMARY KEY (employeeID, employerID)

);