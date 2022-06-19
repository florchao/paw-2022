CREATE TABLE IF NOT EXISTS users (
                                     userId integer identity PRIMARY KEY,
                                     email varchar(100) UNIQUE NOT NULL,
                                     password varchar(100) NOT NULL,
                                     role INT NOT NULL
);

create table if not exists employee(
                                       employeeID INT,
                                       name varchar(100),
                                       location varchar(100),
                                       availability varchar(100),
                                       experienceYears INT,
                                       abilities varchar(100),
                                       voteCount INT,
                                       rating float,
                                       PRIMARY KEY (employeeID),
                                       foreign key (employeeID) REFERENCES users(userID) ON DELETE CASCADE

);

create table if not exists employer(
                                       employerID INT,
                                       name varchar(100),
                                       PRIMARY KEY (employerID)
);

CREATE TABLE IF NOT EXISTS  contact (
                                        employeeID INT,
                                        employerID INT,
                                        message varchar(100),
                                        phone varchar(100),
                                        created DATE,
                                        PRIMARY KEY (employeeID, employerID)
);

CREATE TABLE IF NOT EXISTS profile_images
(
    image  bit,
    userId INTEGER PRIMARY KEY

);

create table if not exists jobs(
                                   jobID integer identity PRIMARY KEY,
                                   employerID INT,
                                   title varchar(100),
                                   location varchar(100),
                                   availability varchar(100),
                                   experienceYears INT,
                                   abilities varchar(100),
                                   description varchar(100),
                                   opened BOOLEAN
);

create table if not exists applicants(
                                         employeeID INT NOT NULL,
                                         jobID INT NOT NULL,
                                         PRIMARY KEY(employeeID, jobID),
                                         FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE,
                                         FOREIGN KEY (jobID) REFERENCES jobs(jobid) ON DELETE CASCADE
);

create table if not exists review(
                                     reviewID integer identity PRIMARY KEY,
                                     employeeId INT NOT NULL,
                                     employerId INT NOT NULL,
                                     review varchar(100),
                                     created DATE,
                                     FOREIGN KEY (employeeID) REFERENCES employee(employeeid) ON DELETE CASCADE,
                                     FOREIGN KEY (employerID) REFERENCES users(userid) ON DELETE CASCADE,
                                     UNIQUE (employeeId, employerId)
);
CREATE TABLE IF NOT EXISTS  ratings (
                                        employeeID INT,
                                        employerID INT,
                                        rating INT,
                                        PRIMARY KEY (employeeID, employerID)

);
