create database EMS;
go
use EMS;
go

CREATE TABLE [dbo].[Employee](
	[EmpNo] [int] NOT NULL
,	[EmpName] [nchar](30) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
,	[BirthDay] [datetime] NOT NULL
,	[DeptNo] [int] NOT NULL
, 	[MgrNo] [nchar](10) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
,	[StartDate] [datetime] NOT NULL
,	[Salary] [money] NOT NULL
,	[Status] [int] NOT NULL
,	[Note] [nchar](100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
,	[Level] [int] NOT NULL
) ON [PRIMARY]

GO

ALTER TABLE Employee 
ADD CONSTRAINT PK_Emp PRIMARY KEY (EmpNo)
GO

ALTER TABLE [dbo].[Employee]  
ADD  CONSTRAINT [chk_Level] 
	CHECK  (([Level]=(7) OR [Level]=(6) OR [Level]=(5) OR [Level]=(4) OR [Level]=(3) OR [Level]=(2) OR [Level]=(1)))
GO
ALTER TABLE [dbo].[Employee]  
ADD  CONSTRAINT [chk_Status] 
	CHECK  (([Status]=(2) OR [Status]=(1) OR [Status]=(0)))

GO
ALTER TABLE [dbo].[Employee]
ADD Email NCHAR(30) 
GO

ALTER TABLE [dbo].[Employee]
ADD CONSTRAINT chk_Email CHECK (Email IS NOT NULL)
GO

ALTER TABLE [dbo].[Employee] 
ADD CONSTRAINT chk_Email1 UNIQUE(Email)

GO
ALTER TABLE Employee
ADD CONSTRAINT DF_EmpNo DEFAULT 0 FOR EmpNo

GO
ALTER TABLE Employee
ADD CONSTRAINT DF_Status DEFAULT 0 FOR Status

GO
CREATE TABLE [dbo].[Skill](
	[SkillNo] [int] IDENTITY(1,1) NOT NULL
,	[SkillName] [nchar](30) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
,	[Note] [nchar](100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) ON [PRIMARY]

GO
ALTER TABLE Skill
ADD CONSTRAINT PK_Skill PRIMARY KEY (SkillNo)

GO
CREATE TABLE [dbo].[Department](
	[DeptNo] [int] IDENTITY(1,1) NOT NULL
,	[DeptName] [nchar](30) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
,	[Note] [nchar](100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) ON [PRIMARY]

GO
ALTER TABLE Department
ADD CONSTRAINT PK_Dept PRIMARY KEY (DeptNo)

GO
CREATE TABLE [dbo].[Emp_Skill](
	[SkillNo] [int] NOT NULL
,	[EmpNo] [int] NOT NULL
,	[SkillLevel] [int] NOT NULL
,	[RegDate] [datetime] NOT NULL
,	[Description] [nchar](100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) ON [PRIMARY]

GO
ALTER TABLE Emp_Skill
ADD CONSTRAINT PK_Emp_Skill PRIMARY KEY (SkillNo, EmpNo)
GO

ALTER TABLE Employee  
ADD  CONSTRAINT [FK_1] FOREIGN KEY([DeptNo])
REFERENCES Department (DeptNo)

GO
ALTER TABLE Emp_Skill
ADD CONSTRAINT [FK_2] FOREIGN KEY ([EmpNo])
REFERENCES Employee([EmpNo])

GO
ALTER TABLE Emp_Skill
ADD CONSTRAINT [FK_3] FOREIGN KEY ([SkillNo])
REFERENCES Skill([SkillNo])

GO


insert into [dbo].[Department] ([DeptName], [Note])
values 
(N'FSOFT.FGC', N'Global Communication'),
(N'FSOFT.FHN', N'FPT Hanoi'),
(N'FSOFT.FDN', N'FPT Da Nang'),
(N'FSOFT.FSG', N'FPT Saigon'),
(N'FSOFT.BU1', N'Business Unit 1'),
(N'FSOFT.BU2', N'Business Unit 2'),
(N'FSOFT.HR', N'Human Resources'),
(N'FSOFT.IT', N'IT Support'),
(N'FSOFT.LAB', N'Innovation Lab'),
(N'FSOFT.TEST', N'Testing Center');

insert into [dbo].[Skill] ([SkillName], [Note])
values 
(N'Java', N'Backend Development'),
(N'.NET', N'ASP.NET Core'),
(N'SQL Server', N'Database Management'),
(N'React Native', N'Mobile App'),
(N'Python', N'Data Science'),
(N'AWS', N'Cloud Computing'),
(N'English', N'Communication'),
(N'Docker', N'DevOps'),
(N'C++', N'Embedded System'),
(N'JavaScript', N'Frontend Development');

SELECT * FROM Employee
insert into [dbo].[Employee] ([EmpNo], [EmpName], [BirthDay], [DeptNo], [MgrNo], [StartDate], [Salary], [Status], [Note], [Level], [Email])
values 
(1, N'Nguyen Van A', '1990-05-15', 1, '0', '2015-01-01', 3000, 1, N'Manager', 5, 'nva@fpt.com'),
(2, N'Tran Thi B', '1998-08-20', 1, '1', '2023-10-10', 1200, 1, NULL, 1, 'btt@fpt.com'),
(3, N'Le Hoang C', '1995-12-01', 2, '1', '2021-06-01', 2500, 1, N'Senior', 4, 'clh@fpt.com'),
(4, N'Pham Minh D', '1993-03-15', 2, '6', '2022-11-20', 1800, 1, NULL, 3, 'dpm@fpt.com'),
(5, N'Vu Phuong E', '2000-11-25', 3, '1', '2024-02-01', 1100, 1, N'Fresher', 1, 'evp@fpt.com'),
(6, N'Dang Van F', '1988-07-30', 3, '0', '2010-05-15', 4000, 1, N'Director', 6, 'fdv@fpt.com'),
(7, N'Hoang Thi G', '1997-01-12', 1, '6', '2021-09-01', 1400, 0, N'Resigned', 2, 'ght@fpt.com'),
(8, N'Bui Van H', '1996-09-05', 4, '6', '2023-12-15', 1600, 1, NULL, 2, 'hbv@fpt.com'),
(9, N'Ngo Kien I', '1992-04-10', 5, '0', '2016-08-20', 3200, 1, N'Manager', 5, 'ink@fpt.com'),
(10, N'Do An J', '1999-02-28', 5, '9', '2023-05-01', 1300, 1, NULL, 2, 'jda@fpt.com');

insert into [dbo].[Emp_Skill] ([SkillNo], [EmpNo], [SkillLevel], [RegDate], [Description])
values 
(1, 1, 3, '2015-02-15', N'Java Bronze'),
(3, 1, 4, '2015-03-10', N'SQL Advanced'), 
(2, 2, 1, '2023-11-01', N'.NET Fresher'),
(9, 4, 3, '2022-12-01', N'C++ Junior'),    
(3, 3, 5, '2021-07-01', N'SQL Expert'),
(1, 4, 3, '2022-12-01', N'Java Silver'),   
(5, 6, 4, '2010-06-01', N'Python Expert'),
(7, 8, 3, '2024-01-01', N'IELTS 7.5'),
(2, 5, 2, '2024-02-01', N'.NET Basic'),
(9, 10, 2, '2023-06-01', N'C++ Basic'),
(6, 10, 3, '2023-07-01', N'AWS Cloud'),
(10, 9, 4, '2016-09-01', N'JS Expert'),
(3, 9, 3, '2016-10-01', N'SQL Professional');

------------------------
---------- B -----------
select 
    e.EmpName, 
    e.Email, 
    d.DeptName
from Employee e
join Department d on e.DeptNo = d.DeptNo
where DATEADD(MONTH, 6, E.StartDate) <= GETDATE();

select * from Employee
----------------------
---------- C ---------

select distinct 
    E.EmpName
from Employee E
join Emp_Skill ES on E.EmpNo = ES.EmpNo
join Skill S on ES.SkillNo = S.SkillNo
where S.SkillName IN (N'C++', N'.NET');


------------------------
---------- D -----------
-- Issues: Kiểu dữ liệu của EmpNo và MgrNo khác nhau mà cũng k có table Manager để chứa email của manager, 
-- trong table Employee cũng k có email của manager
-- => Nên giả dụ MgrNo là field parent của EmpNo khi select sẽ cố ép kiểu 

select 
	E.EmpName AS Ten_Nhan_Vien, 
    M.EmpName AS Ten_Sep, 
    M.Email AS Email_Sep
from Employee E
join Employee M on TRY_CAST(E.MgrNo AS INT) = M.EmpNo
where E.MgrNo <> '0';


----------------------------
----------- E --------------
select 
    D.DeptName, 
    E.EmpName,
    E.BirthDay,
    E.Level,
    E.Salary,
    E.StartDate,
    E.Status
from Department D
join Employee E on D.DeptNo = E.DeptNo
where D.DeptNo IN (
  
    select DeptNo 
    from Employee 
    group by DeptNo 
    having COUNT(EmpNo) >= 2
)
order by D.DeptName;

--------------------------
---------- F -------------

-- Skill numbers là field skillNo
select 
    E.EmpName, 
    E.Email, 
    ES.SkillNo
from Employee E
join Emp_Skill ES on E.EmpNo = ES.EmpNo
order by E.EmpName asc;

-- Skill numbers là số lượng skill
select 
    E.EmpName, 
    E.Email, 
    COUNT(ES.SkillNo) AS Number_of_Skills
from Employee E
left join Emp_Skill ES on E.EmpNo = ES.EmpNo
group by E.EmpName, E.Email
order by E.EmpName asc;


----------------------------------
------------ G -------------------

select 
    EmpName, 
    Email, 
    BirthDay
from Employee
where Status = 1 
  and EmpNo in (
      select EmpNo 
      from Emp_Skill 
      group by EmpNo 
      having COUNT(SkillNo) > 1
  );


  
----------------------------------
------------ H -------------------
CREATE VIEW vw_Employee_Working
AS
SELECT 
    e.EmpName,
    s.SkillName,
    d.DeptName
FROM Employee e
JOIN Department d 
    ON e.DeptNo = d.DeptNo
JOIN Emp_Skill es 
    ON e.EmpNo = es.EmpNo
JOIN Skill s 
    ON es.SkillNo = s.SkillNo
WHERE e.Status = 1;