--============================================================================================================================

--1. USERS TABLE
--------------------------

INSERT INTO users VALUES
('admin2', 'admin123', 'ADMIN'),
('kasun', 'pass123', 'STUDENT'),
('nimali', 'pass123', 'STUDENT'),
('ravi', 'pass123', 'STUDENT'),
('saman', 'pass123', 'STUDENT'),
('dilini', 'pass123', 'STUDENT'),
('silva', 'pass123', 'LECTURER'),
('perera', 'pass123', 'LECTURER'),
('fernando', 'pass123', 'LECTURER'),
('jayasinghe', 'pass123', 'LECTURER');


--============================================================================================================================

--2. DEPARTMNETS TABLE
--------------------------

INSERT INTO departments VALUES
('Computer Science', 'Dr. A. Silva', 'CS,SE,IT', '25'),
('Engineering', 'Dr. B. Perera', 'ENG,ME', '40'),
('Business', 'Dr. C. Fernando', 'BBA,MBA', '30'),
('Science', 'Dr. D. Jayasinghe', 'BIO,CHE,PHY', '35'),
('Mathematics', 'Dr. E. Gunasekara', 'MATH', '20'),
('Law', 'Dr. F. Rodrigo', 'LLB', '15'),
('Medicine', 'Dr. G. Samarasinghe', 'MBBS', '60'),
('Arts', 'Dr. H. Wijesinghe', 'ARTS', '22'),
('Education', 'Dr. I. De Alwis', 'BEd', '18'),
('Management', 'Dr. J. Abeysekara', 'BM,MScM', '28');


--============================================================================================================================

--3. DEGREES TABLE
--------------------------

INSERT INTO degrees VALUES
('BSc Computer Science', 'Computer Science', '200'),
('BSc Software Engineering', 'Computer Science', '180'),
('BSc Information Technology', 'Computer Science', '150'),
('BEng Mechanical', 'Engineering', '120'),
('BBA', 'Business', '220'),
('MBA', 'Business', '80'),
('BSc Biology', 'Science', '140'),
('BSc Mathematics', 'Mathematics', '100'),
('LLB', 'Law', '90'),
('MBBS', 'Medicine', '300');



--============================================================================================================================

--4. LECTURERS TABLE
--------------------------

INSERT INTO lecturers VALUES
('silva@uni.lk', 'Dr. Nimal Silva', 'Computer Science', 'CS101,CS102', '0711111111'),
('perera@uni.lk', 'Dr. Sunil Perera', 'Engineering', 'ENG201', '0722222222'),
('fernando@uni.lk', 'Dr. Kamal Fernando', 'Business', 'BUS101', '0733333333'),
('jayasinghe@uni.lk', 'Dr. Ruwan Jayasinghe', 'Science', 'SCI110', '0744444444'),
('gunasekara@uni.lk', 'Dr. Anura Gunasekara', 'Mathematics', 'MAT101', '0755555555'),
('rodrigo@uni.lk', 'Dr. Pradeep Rodrigo', 'Law', 'LAW100', '0766666666'),
('samar@uni.lk', 'Dr. Isuru Samarasinghe', 'Medicine', 'MED101', '0777777777'),
('wijesinghe@uni.lk', 'Dr. Chaminda Wijesinghe', 'Arts', 'ART105', '0788888888'),
('alwis@uni.lk', 'Dr. Sanduni De Alwis', 'Education', 'EDU120', '0799999999'),
('abey@uni.lk', 'Dr. Tharindu Abeysekara', 'Management', 'MGT200', '0700000000');


UPDATE lecturers SET salary_id = 1 WHERE email = 'silva@uni.lk';
UPDATE lecturers SET salary_id = 2 WHERE email = 'perera@uni.lk';
UPDATE lecturers SET salary_id = 3 WHERE email = 'fernando@uni.lk';
UPDATE lecturers SET salary_id = 4 WHERE email = 'jayasinghe@uni.lk';
UPDATE lecturers SET salary_id = 5 WHERE email = 'gunasekara@uni.lk';
UPDATE lecturers SET salary_id = 6 WHERE email = 'rodrigo@uni.lk';
UPDATE lecturers SET salary_id = 7 WHERE email = 'samar@uni.lk';
UPDATE lecturers SET salary_id = 8 WHERE email = 'wijesinghe@uni.lk';
UPDATE lecturers SET salary_id = 9 WHERE email = 'alwis@uni.lk';
UPDATE lecturers SET salary_id = 10 WHERE email = 'abey@uni.lk';

--============================================================================================================================

--5. COURSES TABLE
--------------------------

INSERT INTO courses VALUES
('CS101', 'Programming Fundamentals', '3', 'silva@uni.lk'),
('CS102', 'Data Structures', '3', 'silva@uni.lk'),
('ENG201', 'Thermodynamics', '4', 'perera@uni.lk'),
('BUS101', 'Introduction to Business', '3', 'fernando@uni.lk'),
('SCI110', 'Basic Biology', '3', 'jayasinghe@uni.lk'),
('MAT101', 'Calculus I', '4', 'gunasekara@uni.lk'),
('LAW100', 'Legal Systems', '3', 'rodrigo@uni.lk'),
('MED101', 'Human Anatomy', '5', 'samar@uni.lk'),
('ART105', 'History of Art', '2', 'wijesinghe@uni.lk'),
('MGT200', 'Operations Management', '3', 'abey@uni.lk');


--============================================================================================================================

--6. STUDENTS TABLE
--------------------------

INSERT INTO students VALUES
('ST001', 'Kasun Perera', 'BSc Computer Science', 'kasun@uni.lk', '0711234567'),
('ST002', 'Nimali Silva', 'BSc Software Engineering', 'nimali@uni.lk', '0721234567'),
('ST003', 'Ravi Fernando', 'BSc Information Technology', 'ravi@uni.lk', '0731234567'),
('ST004', 'Saman Jayasuriya', 'BEng Mechanical', 'saman@uni.lk', '0741234567'),
('ST005', 'Dilini Peris', 'BBA', 'dilini@uni.lk', '0751234567'),
('ST006', 'Chamara Gunathilaka', 'MBA', 'chamara@uni.lk', '0761234567'),
('ST007', 'Ishara Wickramasinghe', 'BSc Biology', 'ishara@uni.lk', '0771234567'),
('ST008', 'Pasan Fernando', 'BSc Mathematics', 'pasan@uni.lk', '0781234567'),
('ST009', 'Thilina Rodrigo', 'LLB', 'thilina@uni.lk', '0791234567'),
('ST010', 'Sanduni Amarasinghe', 'MBBS', 'sanduni@uni.lk', '0701234567');



--============================================================================================================================

--7. ENROLLMENTS TABLE
--------------------------

INSERT INTO enrollments (student_id, course_code, semester, grade) VALUES
('ST001', 'CS101', 'Semester 1', 'A'),
('ST001', 'CS102', 'Semester 2', 'B+'),
('ST002', 'CS101', 'Semester 1', 'A-'),
('ST003', 'CS102', 'Semester 2', 'B'),
('ST004', 'ENG201', 'Semester 1', 'B+'),
('ST005', 'BUS101', 'Semester 1', 'A'),
('ST006', 'MGT200', 'Semester 2', 'A-'),
('ST007', 'SCI110', 'Semester 1', 'B'),
('ST008', 'MAT101', 'Semester 1', 'A'),
('ST009', 'LAW100', 'Semester 1', 'B+');

