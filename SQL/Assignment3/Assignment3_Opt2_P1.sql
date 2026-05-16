create database Assignment3_opt2_p1;
use Assignment3_opt2_p1;

go

create table SAN_PHAM(
	Ma_SP varchar(20) primary key,
	Ten_SP nvarchar(200) not null,
	Don_Gia decimal(10,2)
);

create table Khach_Hang(
	Ma_KH varchar(20) primary key,
	Ten_KH nvarchar(200) not null,
	Phone_No varchar(15),
	Ghi_Chu nvarchar(max)
);

create table Don_Hang(
	Ma_DH varchar(20) primary key,
	Ngay_DH date,
	So_Luong int,
	Ma_SP varchar(20),
	Ma_KH varchar(20),
	foreign key (Ma_SP) references SAN_PHAM(Ma_SP),
	foreign key (Ma_KH) references KHACH_HANG(Ma_KH)
);

insert into SAN_PHAM(Ma_SP,Ten_SP,Don_Gia)
values
('SP01', N'Máy giặt', 50000000),
('SP02', N'Tivi', 3000000),
('SP03', N'Điện thoại', 2000000);

insert into Khach_Hang(Ma_KH, Ten_KH, Phone_No, Ghi_Chu)
values
('KH01', N'Võ Thành Nhân', '0989770345', null),
('KH02', N'Phạm Minh Đức', '0869234901', null),
('KH03', N'Trần Minh Phúc', '0978220123', null);

insert into Don_Hang(Ma_DH, Ngay_DH, So_Luong, Ma_SP, Ma_KH)
values
('DH01', '2026-01-12', 3, 'SP01', 'KH01'),
('DH02', '2026-01-25', 6, 'SP02', 'KH02'),
('DH03', '2026-02-28', 1, 'SP01', 'KH02');


create view v_order_slip as
select 
    kh.ten_kh, 
    dh.ngay_dh, 
    sp.ten_sp, 
    dh.so_luong, 
    (dh.so_luong * sp.don_gia) as thanh_tien
from don_hang dh
join san_pham sp on dh.ma_sp = sp.ma_sp
join khach_hang kh on dh.ma_kh = kh.ma_kh;
go

select * from v_order_slip;


