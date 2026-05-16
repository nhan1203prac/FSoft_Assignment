create database Fresher_Trainee;
go 
use Fresher_Trainee;
go

create table Trainee(
	TraineeID int identity(1,1) primary key,
	Full_Name nvarchar(100) not null,
	Birth_Date Date not null,
	Gender varchar(10) not null check (Gender in ('male', 'female')),
	ET_IQ tinyint check (ET_IQ between 0 and 20),
	ET_Gmath tinyint check (ET_Gmath between 0 and 20),
	ET_English tinyint check (ET_English between 0 and 50),
	Training_Class varchar(20) not null,
	Evaluation_Notes nvarchar(max)
);
go
INSERT INTO Trainee (Full_Name, Birth_Date, Gender, ET_IQ, ET_Gmath, ET_English, Training_Class, Evaluation_Notes)
VALUES 
(N'Nguyễn Văn A', '2002-05-15', 'male', 15, 18, 45, 'NET01', N'Tiềm năng tốt, tiếng Anh xuất sắc.'),
(N'Trần Thị B', '2003-08-20', 'female', 12, 14, 38, 'JAVA02', N'Cần cải thiện kỹ năng logic.'),
(N'Lê Hoàng C', '2001-12-01', 'male', 19, 20, 30, 'NET01', N'Tư duy GMath cực kỳ ấn tượng.'),
(N'Phạm Minh D', '2002-03-10', 'male', 10, 10, 25, 'PY03', N'Mức trung bình, cần nỗ lực thêm.'),
(N'Vũ Phương E', '2003-11-25', 'female', 14, 12, 48, 'JAVA02', N'Giao tiếp tốt, phù hợp làm lead.'),
(N'Đặng Văn F', '2002-07-30', 'male', 17, 15, 40, 'NET01', N'Kỹ thuật vững.'),
(N'Hoàng Thị G', '2004-01-12', 'female', 13, 16, 35, 'PY03', N'Học hỏi nhanh.'),
(N'Bùi Văn H', '2002-09-05', 'male', 16, 17, 42, 'JAVA02', N'Kỹ năng giải quyết vấn đề tốt.'),
(N'Ngô Mai I', '2003-06-18', 'female', 11, 13, 49, 'NET01', N'Điểm tiếng Anh gần như tuyệt đối.'),
(N'Lý Thành J', '2001-04-22', 'male', 20, 19, 33, 'PY03', N'Ứng viên xuất sắc cho vị trí AI.');

----------------------------
-------------- B -----------
alter table Trainee 
add Fsoft_Account varchar(50) null;

update Trainee
set Fsoft_Account = 'Fresher_' + cast(TraineeID as varchar);

alter table Trainee 
alter column Fsoft_Account varchar(50) not null;

alter table Trainee 
add constraint UQ_Fsoft_Account unique (Fsoft_Account);

------------------------------
------------- c --------------
go

create view View_ETPassedTrainees as
select
	TraineeID, Full_Name, Birth_Date, Gender,
	Fsoft_Account, ET_IQ, ET_Gmath, ET_English,
	Training_Class
from Trainee
where (ET_IQ + ET_Gmath) >= 20
	and ET_IQ >= 8
	and ET_Gmath >= 8
	and ET_English >= 18;


SELECT * FROM View_ETPassedTrainees;

-----------------------------
----------- D ---------------
select month(Birth_Date) AS Birth_Month, count(TraineeID) as Total_Passed
from View_ETPassedTrainees
group by month(Birth_Date)
order by Birth_Month;


-----------------------------
---------- E ----------------
SELECT TOP 1
    TraineeID,
    Full_Name,
    Fsoft_Account,
    Birth_Date,
    DATEDIFF(YEAR, Birth_Date, GETDATE()) AS Age,
    Gender,
    Training_Class,
    ET_IQ,
    ET_Gmath,
    ET_English,
    Evaluation_Notes
FROM Trainee
ORDER BY LEN(Full_Name) DESC;