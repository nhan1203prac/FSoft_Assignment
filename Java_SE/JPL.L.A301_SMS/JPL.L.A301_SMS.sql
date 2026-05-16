create database SMS;
go
use SMS;
go

create table Customer (
    customer_id int identity(1,1) primary key,
    customer_name nvarchar(100) not null
);

create table Employee (
    employee_id int identity(1,1) primary key,
    employee_name nvarchar(100) not null,
    salary float not null,
    supervisor_id int null,
    constraint FK_Emp_Supervisor foreign key (supervisor_id) references Employee(employee_id)
);

create table Product (
    product_id int identity(1,1) primary key,
    product_name nvarchar(100) not null,
    list_price float not null
);

create table Orders (
    order_id int identity(1,1) primary key,
    order_date date not null,
    customer_id int not null,
    employee_id int not null,
    total float default 0,
    constraint FK_Orders_Customer foreign key (customer_id) references Customer(customer_id),
    constraint FK_Orders_Employee foreign key (employee_id) references Employee(employee_id)
);

create table LineItem (
    order_id int not null,
    product_id int not null,
    quantity int not null,
    price float not null,
    constraint PK_LineItem primary key (order_id, product_id),
    constraint FK_LineItem_Order foreign key (order_id) references Orders(order_id),
    constraint FK_LineItem_Product foreign key (product_id) references Product(product_id)
);
go

insert into Customer (customer_name) values
(n'Alice Johnson'),
(n'Bob Smith'),
(n'Carol White');

insert into Employee (employee_name, salary, supervisor_id) values
(n'Manager Tom', 5000.00, null),
(n'Staff Jane', 3000.00, 1),
(n'Staff Mike', 2800.00, 1);

insert into Product (product_name, list_price) values
(n'Laptop', 1200.00),
(n'Mouse', 25.00),
(n'Keyboard', 55.00),
(n'Monitor', 350.00);

insert into Orders (order_date, customer_id, employee_id, total) values
('2024-01-10', 1, 2, 0),
('2024-02-15', 1, 3, 0),
('2024-03-20', 2, 2, 0);

insert into LineItem (order_id, product_id, quantity, price) values
(1, 1, 1, 1200.00),
(1, 2, 2, 25.00),
(2, 3, 1, 55.00),
(2, 4, 2, 350.00),
(3, 1, 1, 1200.00),
(3, 3, 1, 55.00);

update Orders
set total = (
    select sum(quantity * price)
    from LineItem
    where LineItem.order_id = Orders.order_id
);
go

create function computeOrderTotal (@orderId int)
returns float
as
begin
    declare @total float;
    select @total = sum(quantity * price)
    from LineItem
    where order_id = @orderId;
    return isnull(@total, 0);
end;
go

create procedure addCustomer
    @customerName nvarchar(100)
as
begin
    set nocount on;
    insert into Customer (customer_name) values (@customerName);
end;
go

create procedure deleteCustomer
    @customerId int
as
begin
    set nocount on;
    delete from LineItem
    where order_id in (
        select order_id from Orders where customer_id = @customerId
    );
    delete from Orders where customer_id = @customerId;
    delete from Customer where customer_id = @customerId;
end;
go

create procedure updateCustomer
    @customerId int,
    @customerName nvarchar(100)
as
begin
    set nocount on;
    update Customer
    set customer_name = @customerName
    where customer_id = @customerId;
end;
go