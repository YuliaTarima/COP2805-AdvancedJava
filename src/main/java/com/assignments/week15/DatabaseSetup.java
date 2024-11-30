package com.assignments.week15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:src/main/java/com/assignments/week15/test.db";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Drop tables if they exist
            stmt.execute("DROP TABLE IF EXISTS Student;");
            stmt.execute("DROP TABLE IF EXISTS Course;");
            stmt.execute("DROP TABLE IF EXISTS College;");
            stmt.execute("DROP TABLE IF EXISTS Subject;");
            stmt.execute("DROP TABLE IF EXISTS Department;");
            stmt.execute("DROP TABLE IF EXISTS Enrollment;");
            stmt.execute("DROP TABLE IF EXISTS TaughtBy;");
            stmt.execute("DROP TABLE IF EXISTS Faculty;");

            // Create tables
            stmt.execute("CREATE TABLE Department (" +
                    "deptId CHAR(4) NOT NULL PRIMARY KEY, " +
                    "name VARCHAR(25) UNIQUE, " +
                    "chairId VARCHAR(9), " +
                    "collegeId CHAR(4));");

            stmt.execute("CREATE TABLE Enrollment (" +
                    "ssn CHAR(9) NOT NULL, " +
                    "courseId CHAR(5) NOT NULL, " +
                    "dateRegistered DATE, " +
                    "grade CHAR(1), " +
                    "PRIMARY KEY (ssn, courseId));");

            stmt.execute("CREATE TABLE TaughtBy (" +
                    "courseId CHAR(5), " +
                    "ssn CHAR(9));");

            stmt.execute("CREATE TABLE Faculty (" +
                    "ssn CHAR(9) NOT NULL PRIMARY KEY, " +
                    "firstName VARCHAR(25), " +
                    "mi CHAR(1), " +
                    "lastName VARCHAR(25), " +
                    "phone CHAR(10), " +
                    "email VARCHAR(50), " +
                    "office VARCHAR(15), " +
                    "startTime DATE, " +
                    "rank VARCHAR(20), " +
                    "salary DOUBLE, " +
                    "deptId CHAR(4));");

            stmt.execute("CREATE TABLE Subject (" +
                    "subjectId CHAR(4) NOT NULL PRIMARY KEY, " +
                    "name VARCHAR(25), " +
                    "startTime DATE, " +
                    "deptId CHAR(4));");

            stmt.execute("CREATE TABLE Student (" +
                    "ssn CHAR(9) NOT NULL PRIMARY KEY, " +
                    "firstName VARCHAR(25), " +
                    "mi CHAR(1), " +
                    "lastName VARCHAR(25), " +
                    "phone CHAR(11), " +
                    "birthDate DATE, " +
                    "street VARCHAR(100), " +
                    "zipCode CHAR(5), " +
                    "deptId CHAR(4), " +
                    "FOREIGN KEY (deptId) REFERENCES Department(deptId));");

            stmt.execute("CREATE TABLE Course (" +
                    "courseId CHAR(5) NOT NULL PRIMARY KEY, " +
                    "subjectId CHAR(4) NOT NULL, " +
                    "courseNumber INTEGER, " +
                    "title VARCHAR(50) NOT NULL, " +
                    "numOfCredits INTEGER, " +
                    "FOREIGN KEY (subjectId) REFERENCES Subject(subjectId));");

            stmt.execute("CREATE TABLE College (" +
                    "collegeId CHAR(4) NOT NULL PRIMARY KEY, " +
                    "name VARCHAR(25) NOT NULL, " +
                    "since DATE, " +
                    "deanId CHAR(9), " +
                    "FOREIGN KEY (deanId) REFERENCES Faculty(ssn));");

            // Insert data into Department table
            stmt.execute("INSERT INTO Department VALUES ('CS', 'Computer Science', '111221115', 'SC');");
            stmt.execute("INSERT INTO Department VALUES ('MATH', 'Mathematics', '111221116', 'SC');");
            stmt.execute("INSERT INTO Department VALUES ('CHEM', 'Chemistry', '111225555', 'SC');");
            stmt.execute("INSERT INTO Department VALUES ('EDUC', 'Education', '333114444', 'EDUC');");
            stmt.execute("INSERT INTO Department VALUES ('ACCT', 'Accounting', '333115555', 'BUSS');");
            stmt.execute("INSERT INTO Department VALUES ('BIOL', 'Biology', '111225555', 'SC');");

            // Insert data into Enrollment table
            stmt.execute("INSERT INTO Enrollment VALUES ('444111110', '11111', DATE('now'), 'A');");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111110', '11112', DATE('now'), 'B');");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111110', '11113', DATE('now'), 'C');");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111111', '11111', DATE('now'), 'D');");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111111', '11112', DATE('now'), 'F');");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111111', '11113', DATE('now'), 'A');");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111112', '11111', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111112', '11112', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111112', '11114', DATE('now'), 'B');");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111112', '11115', DATE('now'), 'C');");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111112', '11116', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111113', '11111', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111113', '11113', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111114', '11115', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111115', '11115', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111115', '11116', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111116', '11111', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111117', '11111', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111118', '11111', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111118', '11112', DATE('now'), NULL);");
            stmt.execute("INSERT INTO Enrollment VALUES ('444111118', '11113', DATE('now'), NULL);");

            // Insert data into TaughtBy table
            stmt.execute("INSERT INTO TaughtBy VALUES ('11111', '111221111');");
            stmt.execute("INSERT INTO TaughtBy VALUES ('11112', '111221111');");
            stmt.execute("INSERT INTO TaughtBy VALUES ('11113', '111221111');");
            stmt.execute("INSERT INTO TaughtBy VALUES ('11114', '111221115');");
            stmt.execute("INSERT INTO TaughtBy VALUES ('11115', '111221110');");
            stmt.execute("INSERT INTO TaughtBy VALUES ('11116', '111221115');");
            stmt.execute("INSERT INTO TaughtBy VALUES ('11117', '111221116');");
            stmt.execute("INSERT INTO TaughtBy VALUES ('11118', '111221112');");

            // Insert data into Subject table
            stmt.execute("INSERT INTO Subject VALUES ('CSCI', 'Computer Science', '1980-08-01', 'CS');");
            stmt.execute("INSERT INTO Subject VALUES ('ITEC', 'Information Technology', '2002-01-01', 'CS');");
            stmt.execute("INSERT INTO Subject VALUES ('MATH', 'Mathematical Science', '1935-08-01', 'MATH');");
            stmt.execute("INSERT INTO Subject VALUES ('STAT', 'Statistics', '1980-08-01', 'MATH');");
            stmt.execute("INSERT INTO Subject VALUES ('EDUC', 'Education', '1980-08-01', 'EDUC');");

            // Insert data into Faculty table
            stmt.execute("INSERT INTO Faculty VALUES (111221110, 'Patty', 'R', 'Smith', '9129215555', " +
                    "'patty@yahoo.com', 'SC129', '1976-10-11', 'Full Professor', 60000, 'MATH');");
            stmt.execute("INSERT INTO Faculty VALUES (111221111, 'George', 'P', 'Franklin', '9129212525', " +
                    "'george@yahoo.com', 'SC130', '1986-10-12', 'Associate Professor', 65000, 'CS');");
            stmt.execute("INSERT INTO Faculty VALUES (111221112, 'Jean', 'D', 'Yao', '9129215556', " +
                    "'jean@yahoo.com', 'SC131', '1976-08-13', 'Full Professor', 65000, 'MATH');");
            stmt.execute("INSERT INTO Faculty VALUES ('111221113', 'Frank', 'E', 'Goldman', '9129215557', " +
                    "'frank@yahoo.com', 'SC132', '1996-01-14', 'Assistant Professor', 60000, 'ACCT');");
            stmt.execute("INSERT INTO Faculty VALUES ('111221114', 'Steve', 'T', 'Templeton', '9129215558', " +
                    "'steve@yahoo.com', 'UH132', '1998-01-01', 'Assistant Professor', 60000, 'ACCT');");
            stmt.execute("INSERT INTO Faculty VALUES ('111221115', 'Alex', 'T', 'Bedat', '9129215559', " +
                    "'alex@yahoo.com', 'SC133', '2000-01-01', 'Full Professor', 95000, 'CS');");
            stmt.execute("INSERT INTO Faculty VALUES ('111221116', 'Judy', 'T', 'Woo', '9129215560', " +
                    "'judy@yahoo.com', 'SC133', '2000-01-01', 'Full Professor', 55000, 'MATH');");
            stmt.execute("INSERT INTO Faculty VALUES ('111221117', 'Joe', 'R', 'Chang', '9129215561', " +
                    "'joe@yahoo.com', 'ED133', '2000-01-01', 'Full Professor', 55000, 'EDUC');");
            stmt.execute("INSERT INTO Faculty VALUES ('111221118', 'Francis', 'R', 'Chin', '9129215562', " +
                    "'joe@yahoo.com', 'ED133', '1989-01-01', 'Full Professor', 75000, 'BIOL');");
            stmt.execute("INSERT INTO Faculty VALUES ('111221119', 'Ray', 'R', 'Smith', '9129215563', " +
                    "'ray@yahoo.com', 'SC133', '1994-01-01', 'Full Professor', 85000, 'CHEM');");

            // Insert data into Student table
            stmt.execute("INSERT INTO Student VALUES ('444111110', 'Jacob', 'R', 'Smith', NULL, " +
                    "'1985-04-09', '99 Kingston Street', '31435', 'BIOL');");
            stmt.execute("INSERT INTO Student VALUES ('444111111', 'John', 'K', 'Stevenson', '9129219434', " +
                    "NULL, '100 Main Street', '31411', 'BIOL');");
            stmt.execute("INSERT INTO Student VALUES ('444111112', 'George', 'R', 'Heintz', '9129213454', " +
                    "'1974-10-10', '1200 Abercorn Street', '31419', 'CS');");
            stmt.execute("INSERT INTO Student VALUES ('444111113', 'Frank', 'E', 'Jones', '9125919434', " +
                    "'1970-09-09', '100 Main Street', '31411', 'BIOL');");
            stmt.execute("INSERT INTO Student VALUES ('444111114', 'Jean', 'K', 'Smith', '9129219434', " +
                    "'1970-02-09', '100 Main Street', '31411', 'CHEM');");
            stmt.execute("INSERT INTO Student VALUES ('444111115', 'Josh', 'R', 'Woo', '7075989434', " +
                    "'1970-02-09', '555 Franklin Street', '31411', 'CHEM');");
            stmt.execute("INSERT INTO Student VALUES ('444111116', 'Josh', 'R', 'Smith', '9129219434', " +
                    "'1973-02-09', '100 Main Street', '31411', 'BIOL');");
            stmt.execute("INSERT INTO Student VALUES ('444111117', 'Joy', 'P', 'Kennedy', '9129229434', " +
                    "'1974-03-19', '103 Bay Street', '31412', 'CS');");
            stmt.execute("INSERT INTO Student VALUES ('444111118', 'Toni', 'R', 'Peterson', '9129229434', " +
                    "'1964-04-29', '103 Bay Street', '31412', 'MATH');");
            stmt.execute("INSERT INTO Student VALUES ('444111119', 'Patrick', 'R', 'Stoneman', NULL, " +
                    "'1969-04-29', '101 Washington Street', '31435', 'MATH');");
            stmt.execute("INSERT INTO Student VALUES ('444111120', 'Rick', 'R', 'Carter', NULL, " +
                    "'1986-04-09', '19 West Ford Street', '31411', 'BIOL');");

            // Insert data into Course table
            stmt.execute("INSERT INTO Course VALUES ('11111', 'CSCI', 1301, 'Intro to Java I', 4);");
            stmt.execute("INSERT INTO Course VALUES ('11112', 'CSCI', 1302, 'Intro to Java II', 3);");
            stmt.execute("INSERT INTO Course VALUES ('11113', 'CSCI', 4720, 'Database Systems', 3);");
            stmt.execute("INSERT INTO Course VALUES ('11114', 'CSCI', 4750, 'Rapid Java Application', 3);");
            stmt.execute("INSERT INTO Course VALUES ('11115', 'MATH', 2750, 'Calculus I', 5);");
            stmt.execute("INSERT INTO Course VALUES ('11116', 'MATH', 3750, 'Calculus II', 5);");
            stmt.execute("INSERT INTO Course VALUES ('11117', 'EDUC', 1111, 'Reading', 3);");
            stmt.execute("INSERT INTO Course VALUES ('11118', 'ITEC', 1344, 'Database Administration', 3);");

            // Insert data into College table
            stmt.execute("INSERT INTO College VALUES ('SC', 'Science', '1994-01-01', '111221110');");
            stmt.execute("INSERT INTO College VALUES ('NURS', 'Nursing', '1994-01-01', NULL);");
            stmt.execute("INSERT INTO College VALUES ('EDUC', 'Education', '1994-01-01', '111221117');");
            stmt.execute("INSERT INTO College VALUES ('BUSS', 'Business', '1994-01-01', '111221114');");

            System.out.println("Database setup completed successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}