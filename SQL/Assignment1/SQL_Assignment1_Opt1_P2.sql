create database Assignment1_Opt1_P2;
go
use Assignment1_Opt1_P2;
go

create table STUDENT(
	SSN CHAR(9) PRIMARY KEY,
	Name NVARCHAR(100),
	Major NVARCHAR(50),
	Bdate DATE
);

create table COURSE(
	CourseNo INT PRIMARY KEY,
	Cname NVARCHAR(100),
	Dept NVARCHAR(50)
);

create table TEXT(
	Book_ISBN VARCHAR(20) PRIMARY KEY,
	Book_Title NVARCHAR(200),
	Publisher NVARCHAR(100),
	Author NVARCHAR(100)
);

create table ENROLL(
	SSN CHAR(9),
	CourseNo INT,
	Quarter NVARCHAR(20),
	Grade CHAR(2),
	PRIMARY KEY(SSN, CourseNo, Quarter),
	FOREIGN KEY(SSN) REFERENCES STUDENT(SSN),
	FOREIGN KEY(CourseNo) REFERENCES COURSE(CourseNo)
);

create table BOOK_ADOPTION(
	CourseNo INT,
	Quarter NVARCHAR(20),
	Book_ISBN VARCHAR(20),
	PRIMARY KEY(CourseNo, Quarter, Book_ISBN),
	FOREIGN KEY(CourseNo) REFERENCES COURSE(CourseNo),
	FOREIGN KEY(Book_ISBN) REFERENCES TEXT(Book_ISBN)
)
