------------------------------------------
----------------- Cau 1 ------------------
--Bảng Project thêm ProjectManagerID Tham chiếu đến EmployeeID
--Bảng Work_Done Thêm EmployeeID (FK) vì theo mô tả của đề: 
--' employees report the work done on their modules as well as other employees’ modules (if any).'

------------------------------------------
----------------- Cau 2 ------------------
create database Project_Management;
go
use Project_Management;
go
create table Employee (
	EmployeeID INT PRIMARY KEY,
    LastName NVARCHAR(50) NOT NULL,
    FirstName NVARCHAR(50) NOT NULL,
    HireDate DATE NOT NULL,
    Status NVARCHAR(20),
    SupervisorID INT,
    SocialSecurityNumber VARCHAR(20) UNIQUE,
    FOREIGN KEY (SupervisorID) REFERENCES Employee(EmployeeID)
)

create table Projects (
    ProjectID INT PRIMARY KEY,
    ProjectName NVARCHAR(100) NOT NULL,
    ProjectStartDate DATE NOT NULL,
    ProjectDescription NVARCHAR(MAX),
    ProjectDetail NVARCHAR(MAX),
    ProjectCompletedOn DATE,
    ProjectManagerID INT,
    FOREIGN KEY (ProjectManagerID) REFERENCES Employee(EmployeeID)
)

create table Project_Modules(
    ModuleID INT PRIMARY KEY,
    ProjectID INT NOT NULL,
    EmployeeID INT NOT NULL, 
    ProjectModulesDate DATE NOT NULL,
    ProjectModulesCompletedOn DATE, 
    ProjectModulesDescription NVARCHAR(MAX),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID) ON DELETE CASCADE,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
)

create table Work_Done(
    WorkDoneID INT PRIMARY KEY,
    ModuleID INT NOT NULL,
    EmployeeID INT NOT NULL, 
    WorkDoneDate DATE, 
    WorkDoneDescription NVARCHAR(MAX),
    WorkDoneStatus NVARCHAR(50),
    FOREIGN KEY (ModuleID) REFERENCES Project_Modules(ModuleID) ON DELETE CASCADE,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);



insert into Employee (EmployeeID, LastName, FirstName, HireDate, Status, SupervisorID, SocialSecurityNumber)
values 
(1, N'Nguyen', N'Anh', '2020-01-01', 'Active', NULL, 'SSN001'),
(2, N'Tran', N'Binh', '2020-02-01', 'Active', 1, 'SSN002'),
(3, N'Le', N'Cuong', '2020-03-01', 'Active', 1, 'SSN003'),
(4, N'Pham', N'Dung', '2021-01-01', 'Active', 2, 'SSN004'),
(5, N'Hoang', N'Em', '2021-05-01', 'Active', 2, 'SSN005'),
(6, N'Vu', N'Fan', '2022-01-01', 'Active', 3, 'SSN006'),
(7, N'Dang', N'Giang', '2022-03-01', 'Active', 3, 'SSN007'),
(8, N'Bui', N'Hoa', '2023-01-01', 'Active', 1, 'SSN008'),
(9, N'Do', N'Inh', '2023-06-01', 'Active', 4, 'SSN009'),
(10, N'Ngo', N'Kien', '2024-01-01', 'Active', 4, 'SSN010');

insert into Projects (ProjectID, ProjectName, ProjectStartDate, ProjectDescription, ProjectCompletedOn, ProjectManagerID)
values 
(101, N'E-Commerce System', '2023-01-01', N'Build online shop', '2023-12-30', 1),
(102, N'HR Management', '2024-01-01', N'Internal tool', NULL, 1),
(103, N'Mobile App Game', '2023-05-01', N'RPG Game', '2024-01-15', 2),
(104, N'AI Research', '2024-02-01', N'Machine learning', NULL, 3),
(105, N'Old System Migrated', '2022-01-01', N'Legacy data', '2022-12-01', 1),
(106, N'Blockchain Payment', '2024-03-01', N'Crypto wallet', NULL, 4);

insert into Project_Modules (ModuleID, ProjectID, EmployeeID, ProjectModulesDate, ProjectModulesCompletedOn, ProjectModulesDescription)
values 
(1, 101, 2, '2023-02-01', '2023-05-01', N'Login Module'),
(2, 101, 3, '2023-03-01', '2023-06-01', N'Cart Module'),
(3, 102, 4, '2024-01-10', NULL, N'Attendance Module'),
(4, 102, 5, '2024-01-15', NULL, N'Payroll Module'),
(5, 103, 6, '2023-06-01', '2023-12-01', N'Graphics Module'),
(6, 105, 2, '2022-02-01', '2022-05-01', N'Data Extraction');

insert into Work_Done (WorkDoneID, ModuleID, EmployeeID, WorkDoneDate, WorkDoneDescription, WorkDoneStatus)
values 
(1, 1, 2, '2023-02-15', N'Created UI for Login', 'Done'),
(2, 1, 4, '2023-02-20', N'Assisted in Backend Login', 'Done'), 
(3, 2, 3, '2023-03-15', N'Designed DB for Cart', 'Done'),
(4, 3, 4, '2024-01-20', N'Coded check-in logic', 'In Progress'),
(5, 5, 7, '2023-07-01', N'Optimized textures', 'Done'),
(6, 6, 2, '2022-03-01', N'Migrated old table', 'Done');
go

-- ==========================
-- ========= B ==============
create procedure cleanOldProject
as
begin
    declare @DeletedWorkDone int = 0;
    declare @DeletedModules int = 0;
    declare @DeletedProjects int = 0;

    declare @CutOffDate date = DATEADD(MONTH, -3, GetDate());

    begin TRANSACTION;
    begin TRY

        delete wd
        from Work_Done wd
        join Project_Modules pm on wd.ModuleID = pm.ModuleID
        join Projects p on pm.ProjectID = p.ProjectID
        where p.ProjectCompletedOn <= @CutoffDate;
    
        set @DeletedWorkDone = @@ROWCOUNT;


        delete pm
        from Project_Modules pm
        join Projects p on pm.ProjectID = p.ProjectID
        where p.ProjectCompletedOn <= @CutoffDate;

        set @DeletedModules = @@ROWCOUNT;


        delete from Projects
        where ProjectCompletedOn <= @CutoffDate;

        set @DeletedProjects = @@ROWCOUNT;

        COMMIT TRANSACTION;


        PRINT 'Cutoff Date: ' + CAST(@CutoffDate AS VARCHAR(20));
        PRINT 'Number of Projects removed: ' + CAST(@DeletedProjects AS VARCHAR(10));
        PRINT 'Number of Modules removed: ' + CAST(@DeletedModules AS VARCHAR(10));
        PRINT 'Number of Work_Done records removed: ' + CAST(@DeletedWorkDone AS VARCHAR(10));

    end TRY
    begin CATCH
        ROLLBACK TRANSACTION;
    end CATCH
end;

EXEC cleanOldProject;


----------------------------
------------- C ------------
create procedure getEmployeeWorkHistory
        @EmpID int
as
begin 
    IF NOT EXISTS (SELECT 1 FROM Employee where EmployeeID = @EmpID)
    begin
        print 'Employee with id ' + CAST(@EmpID as varchar(10)) + 'does not exist';
        return;
    end

    select distinct
        p.ProjectName,
        pm.ModuleID,
        pm.ProjectModulesDescription,
        pm.ProjectModulesDate,
        case 
            when pm.EmployeeID = @EmpID then 'Owner' 
            else 'Contributor' 
        end as RoleInModule
    from Work_Done wd
    join Project_Modules pm on wd.ModuleID = pm.ModuleID
    join Projects p on pm.ProjectID = p.ProjectID
    where wd.EmployeeID = @EmpID
    order by p.ProjectName, pm.ModuleID;
    
    IF @@ROWCOUNT = 0
    begin
        print 'History not found for Employee ID ' + CAST(@EmpID as varchar(10));
    end
                

end;

exec getEmployeeWorkHistory @EmpID = 4;
----------------------
--------- D ----------

create function getAssistedWork (@EmpID int)
returns table
as 
return
(
    select 
        p.ProjectName,
        pm.ModuleID,
        pm.ProjectModulesDescription ,
        e_owner.FirstName + ' ' + e_owner.LastName as AssignedTo,
        wd.WorkDoneDescription ,
        wd.WorkDoneDate,
        case 
            when wd.WorkDoneDate is null then 'In Progress'
            else 'Completed'
        end as WorkStatus
    from Work_Done wd
    join Project_Modules pm on wd.ModuleID = pm.ModuleID
    join Projects p on pm.ProjectID = p.ProjectID
    join Employee e_owner on pm.EmployeeID = e_owner.EmployeeID
    where wd.EmployeeID = @EmpID 
      and pm.EmployeeID <> @EmpID 
);

select * from dbo.getAssistedWork(4);

---------------------------
------------ F ------------
create trigger trg_ValidateModuleDates
on Project_Modules
AFTER INSERT, UPDATE
AS
BEGIN

    IF EXISTS (
        SELECT 1 
        FROM inserted i
        JOIN Projects p ON i.ProjectID = p.ProjectID
        WHERE
            i.ProjectModulesDate < p.ProjectStartDate
            OR 
            (p.ProjectCompletedOn IS NOT NULL AND i.ProjectModulesCompletedOn > p.ProjectCompletedOn)
    )
    BEGIN
        RAISERROR ('Lỗi dữ liệu: Thời gian của Module phải nằm trong phạm vi thời gian của Dự án!', 16, 1);
        ROLLBACK TRANSACTION;
    END
END;


insert into Project_Modules (ModuleID, ProjectID, EmployeeID, ProjectModulesDate, ProjectModulesCompletedOn, ProjectModulesDescription)
values (99, 101, 2, '2022-12-31', NULL, 'some desc');

update Project_Modules 
set ProjectModulesCompletedOn = '2024-01-15' 
where ModuleID = 1;