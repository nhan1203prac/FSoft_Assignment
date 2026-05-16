create database QuanLyDonHang;
go
use QuanLyDonHang;
go

create table DMSANPHAM(
    MaDM varchar(20) primary key,
    TenDanhMuc nvarchar(255) not null,
    MoTa nvarchar(max));

create table SANPHAM(
    MaSP varchar(20) primary key,
    MaDM varchar(20),
    TenSP nvarchar(255) not null,
    GiaTien decimal(18,2),
    SoLuong int,
    XuatXu nvarchar(100),
    constraint FK_SANPHAM_DMSANPHAM foreign key (MaDM) references DMSANPHAM(MaDM));

create table KHACHHANG (
    MaKH varchar(20) primary key,
    TenKH nvarchar(100) not null,
    Email varchar(100),
    SoDT varchar(15),
    DiaChi nvarchar(255));

create table THANHTOAN (
    MaTT varchar(20) primary key,
    PhuongThucTT nvarchar(100) not null);

create table DONHANG (
    MaDH varchar(20) primary key,
    MaKH varchar(20),
    MaTT varchar(20),
    NgayDat datetime default getdate(),
    constraint FK_DONHANG_KHACHHANG foreign key (MaKH) references KHACHHANG(MaKH),
    constraint FK_DONHANG_THANHTOAN foreign key (MaTT) references THANHTOAN(MaTT));

create table CHITIETDONHANG (
    MaDH varchar(20),
    MaSP varchar(20),
    SoLuong int,
    TongTien decimal(18, 2),
    primary key (MaDH, MaSP),
    constraint FK_CTDH_DONHANG foreign key (MaDH) references DONHANG(MaDH),
    constraint FK_CTDH_SANPHAM foreign key (MaSP) references SANPHAM(MaSP));

insert into DMSANPHAM values 
('DM01', N'Thoi trang nu', N'Quan ao phu nu'),
('DM02', N'Thoi trang nam', N'Quan ao nam gioi');

insert into SANPHAM values 
('SP01', 'DM01', N'Vay xoe', 500000, 60, 'VN'),
('SP02', 'DM02', N'Ao thun nam', 200000, 120, N'Trung Quoc'),
('SP03', 'DM01', N'Ao so mi nu', 350000, 10, 'VN');

insert into KHACHHANG values 
('KH01', N'Nguyen Van An', 'an@gmail.com', '0912345678', N'Da Nang'),
('KH02', N'Tran Thi Binh', 'binh@gmail.com', '0987654321', N'Lang Son'),
('KH03', N'Alice', 'alice@gmail.com', '0905111222', N'Da Nang');

insert into THANHTOAN values 
('TT01', 'Visa'),
('TT02', 'JCB'),
('TT03', 'Cash');

insert into DONHANG values 
('DH01', 'KH01', 'TT01', '2023-10-01'),
('DH02', 'KH03', 'TT02', '2023-10-05');

insert into CHITIETDONHANG values 
('DH01', 'SP01', 6, 3000000),
('DH02', 'SP02', 2, 400000);

------- 1
select * from SANPHAM;

------- 2
delete from KHACHHANG where DiaChi = N'Lang Son';

------- 3
update SANPHAM set XuatXu = N'Viet Nam' where XuatXu = 'VN';

------- 4
select sp.*, dm.TenDanhMuc
from SANPHAM sp
join DMSANPHAM dm on sp.MaDM = dm.MaDM
where (dm.TenDanhMuc = N'Thoi trang nu' and sp.SoLuong > 50)
   or (dm.TenDanhMuc = N'Thoi trang nam' and sp.SoLuong > 100);

-------- 5
-- TH1: Đề bài yêu cầu là tên trong chuỗi có Họ, tên lót và tên
select * from KHACHHANG
where 
    right(TenKH, charindex(' ', reverse(TenKH) + ' ') - 1) like 'A%' 
    and 
    len(right(TenKH, charindex(' ', reverse(TenKH) + ' ') - 1)) = 5;

-- TH2: Cả field HoTen
select * from KHACHHANG
where TenKH like 'A%'
and len(TenKH) = 5;

--------- 6
select * from SANPHAM
order by TenSP desc, SoLuong asc;

--------- 7
select KQ.MaKH, KQ.TenKH, count(KQ.MaSP) as SoLoaiSanPhamMuaTren5
from (
    select KH.MaKH, KH.TenKH, CT.MaSP, sum(CT.SoLuong) as TongTungLoai 
    from KHACHHANG KH
    join DONHANG DH on KH.MaKH = DH.MaKH
    join CHITIETDONHANG CT on DH.MaDH = CT.MaDH
    group by KH.MaKH, KH.TenKH, CT.MaSP
    ) as KQ
where KQ.TongTungLoai > 5
group by KQ.MaKH, KQ.TenKH;

---------- 8
select distinct TenKH
from KHACHHANG;

---------- 9
select kh.MaKH, kh.TenKH, 
    sp.TenSP, 
    ct.SoLuong, 
    dh.NgayDat, 
    sp.GiaTien, 
    ct.TongTien
from KHACHHANG kh
join DONHANG dh on kh.MaKH = dh.MaKH
join CHITIETDONHANG ct on dh.MaDH = ct.MaDH
join SANPHAM sp on ct.MaSP = sp.MaSP;

---------------- 10
select 
    kh.MaKH, kh.TenKH, 
    dh.MaDH, 
    sp.TenSP, 
    ct.SoLuong, 
    ct.TongTien
from KHACHHANG kh
left join DONHANG dh on kh.MaKH = dh.MaKH
left join CHITIETDONHANG ct on dh.MaDH = ct.MaDH
left join SANPHAM sp on ct.MaSP = sp.MaSP;

--------------- 11
select distinct 
    kh.MaKH, 
    kh.TenKH
from KHACHHANG kh
join DONHANG dh on kh.MaKH = dh.MaKH
join THANHTOAN tt on dh.MaTT = tt.MaTT
where tt.PhuongThucTT in ('Visa', 'JCB');

-------------- 12
select MaKH, TenKH
from KHACHHANG
where MaKH not in (
      select MaKH
      from DONHANG
    );

------------- 13
select 
    kh.MaKH, kh.TenKH, 
    sp.TenSP, 
    ct.SoLuong, 
    sp.GiaTien, 
    tt.PhuongThucTT, 
    dh.NgayDat, 
    ct.TongTien
from KHACHHANG kh
join DONHANG dh on kh.MaKH = dh.MaKH
join THANHTOAN tt on dh.MaTT = tt.MaTT
join CHITIETDONHANG ct on dh.MaDH = ct.MaDH
join SANPHAM sp on ct.MaSP = sp.MaSP
where kh.DiaChi = N'Da Nang' 
  and kh.MaKH in (
      select MaKH 
      from DONHANG 
      group by MaKH 
      having count(MaDH) = 1
  )
order by kh.TenKH asc;