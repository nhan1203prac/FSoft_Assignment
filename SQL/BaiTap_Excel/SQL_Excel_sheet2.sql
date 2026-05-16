create database Excel_BT2;
use Excel_BT2;

create table LOAISP (
    MaLoaiSP varchar(20) primary key,
    TenLoaiSP nvarchar(100)
);

create table SANPHAM (
    MaSP varchar(20) primary key,
    TenSP nvarchar(100),
    MaLoaiSP varchar(20),
    GiaBan decimal(18, 2),
    SoLuong int,
    foreign key (MaLoaiSP) references LoaiSP(MaLoaiSP)
);

create table NHANVIEN (
    MaNV varchar(20) primary key,
    HoTenNV nvarchar(100),
    GioiTinh nvarchar(10),
    QueQuan nvarchar(200),
    Tuoi int
);

create table BANHANG (
    MaNV varchar(20),
    MaSP varchar(20),
    SoLuongDaBan int,
    primary key (MaNV, MaSP),
    foreign key (MaNV) references NHANVIEN(MaNV),
    foreign key (MaSP) references SANPHAM(MaSP)
);

go

insert into LOAISP(MaLoaiSP, TenLoaiSP) values 
('L01', N'Điện thoại'),
('L02', N'Bột giặt'),
('L03', N'Kem dưỡng da'),
('L04', N'Gia dụng');


insert into SANPHAM (MaSP, TenSP, MaLoaiSP, GiaBan, SoLuong) values 
('SP001', N'iPhone 15', 'L01', 20000000, 15),
('SP002', N'Omo Matic', 'L02', 150000, 25),
('SP003', N'Kem Pond', 'L03', 100000, 30),  
('SP004', N'Nồi cơm điện', 'L04', 500000, 10),
('SP005', N'Ariel Cửa trên', 'L02', 160000, 5); 


insert into NHANVIEN (MaNV, HoTenNV, GioiTinh, QueQuan, Tuoi) values 
('NV01', N'Nguyễn Văn A', N'Nam', N'Hà Tĩnh', 25),
('NV02', N'Trần Thị B', N'Nữ', N'Hà Nội', 30),
('NV03', N'Lê Văn C', N'Nam', N'Hà Tĩnh', 22),
('NV04', N'Phạm Minh D', N'Nam', N'Đà Nẵng', 28); 

insert into BANHANG (MaNV, MaSP, SoLuongDaBan) values 
('NV01', 'SP001', 5),
('NV01', 'SP002', 6),
('NV02', 'SP003', 2),
('NV03', 'SP005', 4); 


-- ========================
-- ========= 1 ============
select l.MaLoaiSP, sum(sp.SoLuong) as SoLuong
from LOAISP l
join SANPHAM sp on l.MaLoaiSP = sp.MaLoaiSP
group by l.MaLoaiSP;


-- ========================
-- ========= 2 ============
select nv.MaNV, nv.HoTenNV, bh.MaSP
from NHANVIEN nv
join BANHANG bh on nv.MaNV = bh.MaNV


-- ========================
-- ========= 3 ============
select nv.MaNV, nv.HoTenNV, bh.MaSP
from NHANVIEN nv
left join BANHANG bh on nv.MaNV = bh.MaNV


-- ========================
-- ========= 4 ============
select sp.MaSP, sp.TenSP, l.MaLoaiSP, l.TenLoaiSP
from SANPHAM sp
join LOAISP l on sp.MaLoaiSP = l.MaLoaiSP;

-- ========================
-- ========= 5 ============
select * 
from NHANVIEN nv
join BANHANG bh on nv.MaNV = bh.MaNV
group by nv.MaNV, nv.GioiTinh, nv.HoTenNV, nv.QueQuan, nv.Tuoi
having sum(bh.SoLuongDaBan) > 10;

-- ========================
-- ========= 6 ============

select l.MaLoaiSP, l.TenLoaiSP
from LOAISP l
join SANPHAM sp on l.MaLoaiSP = sp.MaLoaiSP
where sp.TenSP <> N'Kem dưỡng da'
group by l.MaLoaiSP, l.TenLoaiSP
having sum(sp.SoLuong) >= 20;

-- ========================
-- ========= 7 ============

select QueQuan, GioiTinh, Tuoi, count(*) as SoLuong
from NHANVIEN
group by QueQuan, GioiTinh, Tuoi;

-- ========================
-- ========= 8 ============
select 
    nv.MaNV, 
    sp.TenSP, 
    lsp.TenLoaiSP
from NHANVIEN nv
join BANHANG bh on nv.MaNV = bh.MaNV
join SANPHAM sp on bh.MaSP = sp.MaSP
join LOAISP lsp on sp.MaLoaiSP = lsp.MaLoaiSP;

-- ========================
-- ========= 9 ============
select COUNT(NV.MaNV) AS SoLuong
from NHANVIEN NV
JOIN BANHANG BH ON NV.MaNV = BH.MaNV
WHERE BH.MaSP = 'SP001';


-- ========================
-- ========= 10 ============
SELECT count(nv.MaNV) as SoLuong
FROM NHANVIEN NV
JOIN BANHANG BH ON NV.MaNV = BH.MaNV
JOIN SANPHAM SP ON BH.MaSP = SP.MaSP
JOIN LOAISP L ON SP.MaLoaiSP = L.MaLoaiSP
WHERE NV.QueQuan = 'Hà Tĩnh' and TenLoaiSP = 'Bột giặt';