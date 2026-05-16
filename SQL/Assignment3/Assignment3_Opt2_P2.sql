create database Assignmen3_Opt2_P2;
use Assignmen3_Opt2_P2;

create table Department (
    Department_Number varchar(20) primary key,
    Department_Name nvarchar(200) not null
);

create table Employee_Table (
    Employee_Number varchar(20) primary key,
    Employee_Name nvarchar(200) not null,
    Department_Number varchar(20),
    foreign key (Department_Number) references Department(Department_Number)
);

create table Employee_Skill_Table (
    Employee_Number varchar(20),
    Skill_Code varchar(20),
    Date_Registered date,
    primary key (Employee_Number, Skill_Code),
    foreign key (Employee_Number) references Employee_Table(Employee_Number)
);

go

insert into Department (Department_Number, Department_Name) values 
('D01', N'Phòng Kỹ Thuật'),
('D02', N'Phòng Nhân Sự'),
('D03', N'Phòng Tài Chính');


insert into Employee_Table (Employee_Number, Employee_Name, Department_Number) values 
('E001', N'Nguyễn Văn An', 'D01'),
('E002', N'Trần Thị Bình', 'D02'),
('E003', N'Lê Minh Cường', 'D01'),
('E004', N'Đặng Mỹ Linh', 'D01');


insert into Employee_Skill_Table (Employee_Number, Skill_Code, [Date_Registered]) values 
('E001', 'Java', '2026-01-15'),
('E001', 'SQL', '2026-02-10'),
('E002', 'Communication', '2026-03-05'),
('E003', 'Python', '2026-03-20');



-- ================================
-- ================= 2 ============
-- solution 1 (join)
select e.Employee_Number, e.Employee_Name
from Employee_Table e
join Employee_Skill_Table ek on e.Employee_Number = ek.Employee_Number
where ek.Skill_Code = 'Java';

-- solution 2 (subquery)
select Employee_Number, Employee_Name
from Employee_Table
where Employee_Number in (
        select ek.Employee_Number
        from Employee_Skill_Table ek
        where ek.Skill_Code = 'Java'
);


-- ======================
-- ============ 3 =======
select d.Department_Number, d.Department_Name, e.Employee_Name
from Employee_Table e
join Department d on e.Department_Number = d.Department_Number
where d.Department_Number in (
    select et.Department_Number
    from Employee_Table et
    group by et.Department_Number
    having count(et.Employee_Number) >= 3
    )
   


-- ======================
-- ============ 4 =======

select Employee_Number, Employee_Name
from Employee_Table
where Employee_Number in (
        select et.Employee_Number
        from Employee_Table et
        join Employee_Skill_Table est on et.Employee_Number = est.Employee_Number
        group by et.Employee_Number
        having count(est.Skill_Code) >= 2
    );



-- ======================
-- ============ 5 =======

create view V_EmployMutipleSkill
as
select e.Employee_Number, e.Employee_Name
from Employee_Table e
join Department d on e.Department_Number = d.Department_Number
where e.Employee_Number in (
       select est.Employee_Number
       from Employee_Skill_Table est
       group by est.Employee_Number
       having count(est.Skill_Code) >= 2
    )

select * from V_EmployMutipleSkill;