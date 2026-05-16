create database QuanLyBanHang;
go
use QuanLyBanHang;
go

create table LOAISP (
    MaLoaiSP VARCHAR(10) primary key,
    TenLoaiSP NVARCHAR(100)
);

create table NHANVIEN (
    MaNV VARCHAR(10) primary key,
    HoTenNV NVARCHAR(100),
    GioiTinh NVARCHAR(10),
    QueQuan NVARCHAR(100),
    Tuoi INT
);

create table SANPHAM (
    MaSP VARCHAR(10) primary key,
    TenSP NVARCHAR(100),
    MaLoaiSP VARCHAR(10),
    GiaBan DECIMAL(15,2),
    foreign key (MaLoaiSP) references LOAISP(MaLoaiSP)
        on delete set null on update cascade
);

create table BANHANG (
    MaNV VARCHAR(10),
    MaSP VARCHAR(10),
    SoLuongDaBan INT default 0,
    primary key (MaNV, MaSP),
    foreign key (MaNV) references NHANVIEN(MaNV)
        on delete cascade,
    foreign key (MaSP) references SANPHAM(MaSP)
        on delete cascade
);
go

insert into LOAISP values
('Type01', N'Bột giặt'),
('Type02', N'Mỹ phẩm'),
('Type03', N'Đồ dùng Học tập'),
('Type04', N'Đồ gia dụng'),
('Type05', N'Thực phẩm'),
('Type06', N'Điện tử'),
('Type07', N'Thời trang'),
('Type08', N'Dược phẩm'),
('Type09', N'Văn phòng phẩm'),
('Type10', N'Nước giải khát');

insert into NHANVIEN values
('NV01', N'Nguyễn Chí Phèo', N'Nam', N'Quảng Trị', 18),
('NV02', N'Nguyễn Thị Hoa', N'Nữ', N'Kon Tum', 22),
('NV03', N'Trần Thu Thảo', N'Nữ', N'Kon Tum', 25),
('NV04', N'Lê Mai Anh', N'Nữ', N'Gia Lai', 28),
('NV05', N'Nguyễn Văn Nam', N'Nam', N'Bình Định', 23),
('NV06', N'Lê Thị Thuỳ', N'Nữ', N'Bình Định', 23),
('NV07', N'Phạm Hoàng Long', N'Nam', N'Gia Lai', 30),
('NV08', N'Ngô Thanh Hoa', N'Nữ', N'Đà Nẵng', 26),
('NV09', N'Nguyễn Công Phượng', N'Nam', N'Nghệ An', 29),
('NV10', N'Đặng Văn Lâm', N'Nam', N'Nga', 31),
('NV11', N'Trần Thị Bưởi', N'Nữ', N'Cần Thơ', 35),
('NV12', N'Lý Văn Toàn', N'Nam', N'Hải Phòng', 24);

insert into SANPHAM values
('SP01', N'Bột giặt OMO', 'Type01', 150000),
('SP02', N'Kem chống nắng', 'Type02', 1200000),
('SP03', N'Son môi MAC 1', 'Type02', 1800000),
('SP04', N'Bột giặt ABC', 'Type01', 3200000),
('SP05', N'Bút vẽ kỹ thuật', 'Type03', 500000),
('SP06', N'Vở học sinh', 'Type03', 10000),
('SP07', N'Sữa tắm Dove', 'Type02', 150000),
('SP08', N'Bút chì màu', 'Type09', 15000),
('SP09', N'Máy tính Casio', 'Type03', 450000),
('SP10', N'Nước khoáng L', 'Type10', 5000);

insert into BANHANG values
('NV02', 'SP01', 10),
('NV02', 'SP03', 5),
('NV03', 'SP02', 20),
('NV04', 'SP03', 10),
('NV03', 'SP05', 12),
('NV07', 'SP05', 5),
('NV05', 'SP07', 8),
('NV09', 'SP01', 2),
('NV10', 'SP06', 15),
('NV12', 'SP09', 3);
go

-- =======================================
-- CÂU 1
-- =========================================
insert into NHANVIEN values('NV13', N'Trần Văn B', N'Nam', N'Huế', 26);

-- ====================================
-- CÂU 2
-- ====================================
delete from NHANVIEN
where GioiTinh = N'Nữ' and QueQuan = N'Kon Tum';

-- =========================================
-- CÂU 3
-- =========================================
update SANPHAM
set GiaBan = GiaBan * 2
where MaLoaiSP = 'Type01';

-- ====================================
-- CÂU 4
-- ====================================
select * from NHANVIEN;

-- =========================================
-- CÂU 5
-- =========================================
select * from NHANVIEN
where Tuoi >= 23 and QueQuan = N'Binh Định';
-- ==================================
-- CÂU 6
-- ==================================
select sp.MaSP
from SANPHAM sp
join BANHANG bh on sp.MaSP = bh.MaSP;

-- ================================
-- CÂU 7
-- ===================================
select *
from NHANVIEN
where HoTenNV like N'Nguyễn %';

-- =========================================
-- CÂU 8
-- =========================================
select *
from NHANVIEN
where right(HoTenNV, charindex(' ', reverse(HoTenNV)) - 1) = N'Hoa';

-- =========================================
-- CÂU 9
-- =========================================
select *
from SANPHAM
where len(TenSP) = 12;

-- =========================================
-- CÂU 10
-- =========================================
select *
from SANPHAM sp
join LOAISP l on sp.MaLoaiSP = l.MaLoaiSP
where l.TenLoaiSP = N'Mỹ phẩm';

-- =========================================
-- CÂU 11
-- =========================================
select *
from SANPHAM sp1
where sp1.MaSP not in (select MaSP from BANHANG)
and sp1.GiaBan < 20000;

-- =========================================
-- CÂU 12
-- =========================================

select * from NHANVIEN nv
where nv.MaNV in (
    select nv2.MaNV
    from NHANVIEN nv2
    left join BANHANG bh on nv2.MaNV = bh.MaNV
    left join SANPHAM sp on bh.MaSP = sp.MaSP
    group by nv2.MaNV
    having (count(distinct sp.MaSP) = 1 and max(sp.TenSP) = N'Bột giặt OMO')
        or count(sp.MaSP) = 0);

-- =========================================
-- CÂU 13
-- =========================================
select distinct nv.MaNV 
from NHANVIEN nv
left join BANHANG bh on nv.MaNV = bh.MaNV
where QueQuan = N'Gia Lai' and bh.MaNV is null;

-- =========================================
-- CÂU 14
-- =========================================
select *
from SANPHAM sp
join LOAISP lsp on sp.MaLoaiSP = lsp.MaLoaiSP;

-- =========================================
-- CÂU 15
-- =========================================
select *
from NHANVIEN nv
join BANHANG bh on nv.MaNV = bh.MaNV
join SANPHAM sp on bh.MaSP = sp.MaSP
join LOAISP lsp on sp.MaLoaiSP = lsp.MaLoaiSP;

-- =========================================
-- CÂU 16
-- =========================================
select distinct lsp.MaLoaiSP, lsp.TenLoaiSP
from LOAISP lsp
join SANPHAM sp on lsp.MaLoaiSP = sp.MaLoaiSP
join BANHANG bh on sp.MaSP = bh.MaSP;

-- =========================================
-- CÂU 17
-- =========================================
select distinct HoTenNV from NHANVIEN;

-- =========================================
-- CÂU 18
-- =========================================
select MaNV, HoTenNV,
    case 
        when QueQuan is null then N'Cõi trên'
        else QueQuan
    end as QueQuan
from NHANVIEN;

-- =========================================
-- CÂU 19
-- =========================================
select *
from (
    select *, dense_rank() over (order by Tuoi desc) rnk
    from NHANVIEN) t
where rnk <= 5;

-- =========================================
-- CÂU 20
-- =========================================
select *
from NHANVIEN
where Tuoi between 25 and 35;