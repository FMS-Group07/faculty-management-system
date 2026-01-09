--============================================================================================================================

--1. USERS TABLE
--------------------------

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    CONSTRAINT chk_role CHECK (role IN ('ADMIN', 'STUDENT', 'LECTURER'))
);


--============================================================================================================================

--2. DEPARTMENTS TABLE
--------------------------

CREATE TABLE departments (
    department_name VARCHAR(100) PRIMARY KEY,
    hod VARCHAR(100),
    degrees TEXT,
    no_of_staff VARCHAR(50)
);


--============================================================================================================================

--3. DEGREES TABLE
--------------------------

CREATE TABLE degrees (
    degree_name VARCHAR(100) PRIMARY KEY,
    department VARCHAR(100),
    no_of_students VARCHAR(50),
    FOREIGN KEY (department) REFERENCES departments(department_name) 
        ON DELETE SET NULL 
        ON UPDATE CASCADE
);


--============================================================================================================================

--4. LECTURERS TABLE
--------------------------

CREATE TABLE lecturers (
    email VARCHAR(100) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    courses_teaching TEXT, 
    mobile VARCHAR(20),
    FOREIGN KEY (department) REFERENCES departments(department_name) 
        ON DELETE SET NULL 
        ON UPDATE CASCADE
);


--============================================================================================================================

--5. COURSES TABLE
--------------------------

CREATE TABLE courses (
    course_code VARCHAR(50) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    credits VARCHAR(10),
    lecturer VARCHAR(100),
    FOREIGN KEY (lecturer) REFERENCES lecturers(email) 
        ON DELETE SET NULL 
        ON UPDATE CASCADE
);



--============================================================================================================================

--6. STUDENTS TABLE
--------------------------

CREATE TABLE students (
    student_id VARCHAR(50) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    degree VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile VARCHAR(20),
    salary_id INT,
    CONSTRAINT fk_degree
        FOREIGN KEY (degree) REFERENCES degrees(degree_name)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_salary
        FOREIGN KEY (salary_id) REFERENCES salaries(salary_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);


--============================================================================================================================

--7. ENROLLMENTS TABLE
--------------------------

CREATE TABLE enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL,
    course_code VARCHAR(50) NOT NULL,
    semester VARCHAR(20),
    grade VARCHAR(5),
    FOREIGN KEY (student_id) REFERENCES students(student_id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    FOREIGN KEY (course_code) REFERENCES courses(course_code) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    UNIQUE(student_id, course_code)
);


