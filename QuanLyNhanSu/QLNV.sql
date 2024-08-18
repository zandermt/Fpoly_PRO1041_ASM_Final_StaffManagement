-- Chạy: Chuc_Vu -> Ung_Vien -> Hop_Dong -> Phong_Ban -> Nhan_Vien -> Tai_Khoan -> Thêm Khoá Ngoại -> Insert dữ liệu

-- Tạo bảng Chuc_Vu
CREATE TABLE Chuc_Vu (
    MaChucVu CHAR(5) PRIMARY KEY,
    TenChucVu NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(MAX)
);

-- Tạo bảng Loai_Hop_Dong
CREATE TABLE Loai_Hop_Dong (
    MaLoaiHd CHAR(5) PRIMARY KEY,
    ThoiHan INT NOT NULL,
    TenHopDong NVARCHAR(50) NOT NULL,
    MoTa NVARCHAR(MAX)
);

-- Tạo bảng Ung_Vien
CREATE TABLE Ung_Vien (
    MaUngVien CHAR(5) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    SDT VARCHAR(11) NOT NULL UNIQUE,
    DiaChi NVARCHAR(MAX) NOT NULL,
    NgaySinh DATE NOT NULL,
    GioiTinh BIT DEFAULT(0) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    MoTa NVARCHAR(MAX),
    MaChucVu CHAR(5) NOT NULL,
    FOREIGN KEY (MaChucVu) REFERENCES Chuc_Vu(MaChucVu) ON DELETE NO ACTION ON UPDATE CASCADE
);

-- Tạo bảng Phong_Ban
CREATE TABLE Phong_Ban (
    MaPhongBan CHAR(5) PRIMARY KEY,
    TenPhongBan NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(MAX),
    MaTruongPhong CHAR(5) NULL
);

-- Tạo bảng Nhan_Vien
CREATE TABLE Nhan_Vien (
    MaNhanVien CHAR(5) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    GioiTinh BIT DEFAULT(0) NOT NULL,
    DiaChi NVARCHAR(MAX) NOT NULL,
    SDT VARCHAR(11) NOT NULL UNIQUE,
    Email VARCHAR(100) NOT NULL UNIQUE,
    NgaySinh DATE NOT NULL,
    MaPhongBan CHAR(5) NOT NULL,
    MaChucVu CHAR(5) NOT NULL,
    MaUngVien CHAR(5) NOT NULL UNIQUE,
    MaLoaiHd CHAR(5) NOT NULL,
    Luong FLOAT NOT NULL,
    FOREIGN KEY (MaLoaiHd) REFERENCES Loai_Hop_Dong(MaLoaiHd) ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (MaPhongBan) REFERENCES Phong_Ban(MaPhongBan) ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (MaChucVu) REFERENCES Chuc_Vu(MaChucVu) ON DELETE NO ACTION ON UPDATE CASCADE,
    -- FOREIGN KEY (MaUngVien) REFERENCES Ung_Vien(MaUngVien) ON DELETE NO ACTION ON UPDATE CASCADE
);

ALTER TABLE Nhan_Vien
ADD CONSTRAINT FK__Nhan_Vien__MaUngVien
FOREIGN KEY (MaUngVien) REFERENCES Ung_Vien(MaUngVien)
ON DELETE NO ACTION
ON UPDATE NO ACTION;


-- Tạo bảng Tai_Khoan
CREATE TABLE Tai_Khoan (
    TenDangNhap CHAR(5) PRIMARY KEY,
    MatKhau NVARCHAR(50) NOT NULL,
    MaNhanVien CHAR(5) NOT NULL,
    FOREIGN KEY (MaNhanVien) REFERENCES Nhan_Vien(MaNhanVien) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Thêm khoá ngoại cho MaTruongPhong với MaNhanVien

ALTER TABLE Phong_Ban
ADD CONSTRAINT FK_MaTruongPhong
FOREIGN KEY (MaTruongPhong)
REFERENCES Nhan_Vien(MaNhanVien)
ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Thêm dữ liệu mẫu vào các bảng
-- Thêm dữ liệu mẫu cho bảng Phong_Ban
INSERT INTO PHONG_BAN (maPhongBan, tenPhongBan, moTa, maTruongPhong)
VALUES 
    ('PB001', N'Phòng Kỹ Thuật', N'Phòng chuyên về kỹ thuật', 'NV001'),
    ('PB002', N'Phòng Nhân Sự', N'Phòng chuyên về nhân sự', 'NV002'),
    ('PB003', N'Phòng Kinh Doanh', N'Phòng chuyên về kinh doanh', 'NV007'),
    ('PB004', N'Phòng Marketing', N'Phòng chuyên về marketing', 'NV008'),
    ('PB005', N'Phòng IT', N'Phòng chuyên về công nghệ thông tin', 'NV009');

-- Thêm dữ liệu mẫu cho bảng Nhan_Vien
INSERT INTO Nhan_Vien (maNhanVien, HoTen, GioiTinh, DiaChi, SDT, Email, NgaySinh, MaPhongBan, MaChucVu, MaUngVien, MaLoaiHd, Luong)
VALUES
    ('NV001', N'Nguyễn Văn Thắng', 1, N'234 Đường ABC, Quận 1, TP.HCM', '0985654321', 'nguyenvana@example.com', '1990-01-10', 'PB001', 'CV001', 'UV001', 'LHD01', 20000000),
    ('NV002', N'Nguyễn Văn Minh', 1, N'234 Đường ABC, Quận 1, TP.HCM', '0987654321', 'nguyenvanw@example.com', '1990-04-15', 'PB002', 'CV001', 'UV009', 'LHD02', 22000000),
    ('NV006', N'Nguyễn Thị Sa', 0, N'567 Đường KLM, Quận 5, TP.HCM', '0123456789', 'nguyenthis@example.com', '1995-12-20', 'PB001', 'CV002', 'UV010', 'LHD02', 12000000),
    ('NV004', N'Trần Văn Tâm', 1, N'789 Đường UVW, Quận 6, TP.HCM', '0369552147', 'tranvant@example.com', '1990-08-10', 'PB001', 'CV003', 'UV011', 'LHD02', 12000000),
    ('NV003', N'Hồ Thị Ung', 0, N'123 Đường XYZ, Quận 1, TP.HCM', '0765432598', 'hothiu@example.com', '1988-03-25', 'PB002', 'CV004', 'UV012', 'LHD04', 5000000),
    ('NV005', N'Phạm Văn Vinh', 1, N'456 Đường DEF, Quận 2, TP.HCM', '0321456987', 'phamvanv@example.com', '1993-10-30', 'PB001', 'CV002', 'UV013', 'LHD02', 12000000),
    ('NV007', N'Trần Thị Xuân', 0, N'567 Đường XYZ, Quận 2, TP.HCM', '0123556789', 'tranthix@example.com', '1993-08-20', 'PB003', 'CV002', 'UV014', 'LHD03', 9000000),
    ('NV008', N'Hồ Văn Tình', 1, N'789 Đường UVW, Quận 3, TP.HCM', '0369852147', 'hovan.y@example.com', '1988-12-10', 'PB004', 'CV003', 'UV015', 'LHD02', 11000000),
	('NV009',N'Hồ Thị Dung', 0, N'123 Đường DEF, Quận 4, TP.HCM','0765432198', 'hothid@example.com','1995-04-30','PB005','CV005','UV004','LHD03',10000000);

INSERT INTO Nhan_Vien (maNhanVien, HoTen, GioiTinh, DiaChi, SDT, Email, NgaySinh, MaPhongBan, MaChucVu, MaUngVien, MaLoaiHd, Luong)
VALUES('NV010',N'Trần Lệ Chi',1,N'32 Ngô Quyền Tp. Quy Nhơn, Bình Định','0857666776','sadfh@gmail.com','2002-02-02','PB002','CV002','UV002','LHD02',8000000)
-- Thêm dữ liệu mẫu cho bảng Chuc_Vu
INSERT INTO Chuc_Vu (MaChucVu, TenChucVu, MoTa)
VALUES 
    ('CV001', N'Trưởng phòng', N'Chịu trách nhiệm quản lý và điều hành phòng ban'),
    ('CV002', N'Nhân viên', N'Nhân viên làm việc trong phòng ban'),
    ('CV003', N'Nhân viên kinh doanh', N'Thực hiện các công việc kinh doanh theo sự chỉ đạo của lãnh đạo'),
	('CV004',N'Thực Tập Sinh', N'Sinh viên thực tập tại công ty'),
	('CV005',N'Chuyên Viên', N'Chuyên viên phụ trách một lĩnh vực cụ thể');




-- Thêm dữ liệu mẫu cho bảng Ung_Vien
INSERT INTO Ung_Vien (MaUngVien, HoTen, SDT, DiaChi, NgaySinh, GioiTinh, Email, MoTa, MaChucVu)
VALUES
    ('UV001', N'Nguyễn Văn Thắng', '0987654321', N'234 Đường ABC, Quận 1, TP.HCM', '1990-01-10', 1, 'nguyenvan3a@example.com', N'Ứng viên cho vị trí Trưởng Phòng', 'CV001'),
    ('UV002', N'Trần Thị Lam', '0123456789', N'567 Đường XYZ, Quận 2, TP.HCM', '1993-05-20', 0, 'tranb3@example.com', N'Ứng viên cho vị trí Nhân Viên', 'CV002'),
    ('UV003', N'Lê Văn Ca', '0369852147', N'789 Đường UVW, Quận 3, TP.HCM', '1988-08-15', 1, 'levanc3@example.com', N'Ứng viên cho vị trí Thực Tập Sinh', 'CV004'),
    ('UV004', N'Hồ Thị Dung', '0765432198', N'123 Đường DEF, Quận 4, TP.HCM', '1995-04-30', 0, 'hoth3id@example.com', N'Ứng viên cho vị trí Chuyên Viên', 'CV005'),
    ('UV005', N'Phạm Văn Nam', '0321456987', N'456 Đường GHI, Quận 5, TP.HCM', '1992-11-10', 1, 'phamv3e@example.com', N'Ứng viên cho vị trí Nhân Viên Kinh Doanh', 'CV003'),
    ('UV006', N'Nguyễn Thị Vân', '0901234567', N'789 Đường UVW, Quận 6, TP.HCM', '1993-07-25', 0, 'ngu3yenthif@example.com', N'Ứng viên cho vị trí Nhân Viên Kinh Doanh', 'CV003'),
    ('UV007', N'Trần Văn Tiến', '0987653321', N'234 Đường ABC, Quận 1, TP.HCM', '1990-02-15', 1, 'tra3nvg@example.com', N'Ứng viên cho vị trí Trưởng Phòng', 'CV001'),
    ('UV008', N'Lê Thị Hoài', '0123436789', N'567 Đường XYZ, Quận 2, TP.HCM', '1993-06-20', 0, 'leth3ih@example.com', N'Ứng viên cho vị trí Nhân Viên', 'CV002'),
	('UV009', N'Nguyễn Văn Minh',  '0987694321', N'234 Đường ABC, Quận 1, TP.HCM','1990-04-15',1, 'nguye3nvanw@example.com',  N'Ứng viên cho vị trí Trưởng Phòng', 'CV001'),
    ('UV010', N'Nguyễn Thị Sa','0123406789',N'567 Đường KLM, Quận 5, TP.HCM','1990-04-15',0,  'nguy3enthis@example.com', N'Ứng viên cho vị trí Nhân Viên', 'CV002'),
    ('UV011', N'Trần Văn Tâm','0369952147', N'789 Đường UVW, Quận 6, TP.HCM',  '1990-08-10',1,'tranv3ant@example.com',  N'Ứng viên cho vị trí Nhân Viên Kinh Doanh', 'CV003'),
    ('UV012', N'Hồ Thị Ung','0369852107', N'123 Đường XYZ, Quận 1, TP.HCM', '1988-03-25',0,'hot3hiu@example.com',  N'Ứng viên cho vị trí Thực Tập Sinh', 'CV004'),
    ('UV013', N'Phạm Văn Vinh','0321456977', N'456 Đường DEF, Quận 2, TP.HCM', '1993-10-30',1, 'pham3vanv@example.com',  N'Ứng viên cho vị trí Nhân Viên', 'CV002'),
    ('UV014', N'Trần Thị Xuân','0123456799', N'567 Đường XYZ, Quận 2, TP.HCM','1993-08-20',0, 'tran3thix@example.com', N'Ứng viên cho vị trí Nhân Viên', 'CV002'),
    ('UV015', N'Hồ Văn Tình', '0368852147',N'789 Đường UVW, Quận 3, TP.HCM', '1988-12-10',  1,'hov3an.y@example.com', N'Ứng viên cho vị trí Nhân Viên Kinh Doanh', 'CV003');

-- Ví dụ Thêm dữ liệu mẫu cho bảng Hop_Dong
INSERT INTO Loai_Hop_Dong (MaLoaiHd, TenHopDong, ThoiHan, MoTa)
VALUES 
    ('LHD01', N'Hợp đồng vô thời hạn', 240, N'Hợp đồng không xác định thời hạn'),
    ('LHD02', N'Hợp đồng có thời hạn', 12, N'Hợp đồng xác định thời hạn 12 tháng'),
    ('LHD03', N'Hợp đồng mùa vụ', 6, N'Hợp đồng thời vụ 6 tháng'),
    ('LHD04', N'Hợp đồng thử việc', 2, N'Hợp đồng thử việc 2 tháng');


INSERT INTO tai_khoan (tenDangNhap,matKhau,maNhanVien)
VALUES
	('us001','111111','NV002'),
	('us002','222222','NV003');


--Store proceduces
GO
CREATE PROC sp_sl_ungvien (@tencv NVARCHAR(100))
AS BEGIN
	SELECT 	COUNT(uv.MaUngVien) + COUNT(nv.MaUngVien) AS TongUngVien,
			COUNT(nv.MaUngVien) AS SLpass,
			COUNT(uv.MaUngVien) AS SLfail
	FROM Chuc_Vu cv
	LEFT JOIN Ung_Vien uv ON cv.MaChucVu = uv.MaChucVu
	LEFT JOIN Nhan_Vien nv ON uv.MaUngVien = nv.MaUngVien
	WHERE cv.TenChucVu like @tencv
	GROUP BY cv.MaChucVu, cv.TenChucVu
END
GO

CREATE PROC sp_sl_tuoi (@mintuoi INT, @maxtuoi INT)
AS BEGIN
	SELECT count(*) SLNV
	FROM nhan_vien
	WHERE (year(GETDATE())-year(nhan_vien.ngaysinh))>@mintuoi AND (year(GETDATE())-year(nhan_vien.ngaysinh))<@maxtuoi
END
GO

CREATE PROC sp_sl_luong (@minluong FLOAT, @maxluong FLOAT)
AS BEGIN
	SELECT count(*) SLNV
	FROM nhan_vien
	WHERE Nhan_Vien.Luong>@minluong AND Nhan_Vien.Luong<@maxluong
END
GO

CREATE PROC sp_sl_hd
AS BEGIN
	SELECT TenHopDong, count(*) SLNV
	FROM Loai_Hop_Dong
	JOIN Nhan_Vien ON Loai_Hop_Dong.MaLoaiHd = Nhan_Vien.MaLoaiHd
	GROUP BY Nhan_Vien.MaLoaiHd, Loai_Hop_Dong.TenHopDong
END
GO

CREATE PROC sp_sl_pb(@tenpb NVARCHAR(100))
AS BEGIN
	SELECT count(nv.manhanvien) AS SLNV,
			count(DISTINCT nv.maChucVu) AS SLCV,
			tp.HoTen AS HOTENTRUONGPHONG
	FROM Phong_Ban
	LEFT JOIN Nhan_Vien tp ON Phong_Ban.MaTruongPhong = tp.MaNhanVien
	LEFT JOIN Nhan_Vien nv ON Phong_Ban.MaPhongBan = nv.MaPhongBan
	WHERE Phong_Ban.TenPhongBan LIKE @tenpb
	GROUP BY tp.HoTen
END

select * from nhan_vien where MaPhongBan=' '
select * from chuc_vu
select * from phong_ban
select * from ung_vien
select * from loai_hop_dong
select * from tai_khoan where tendangnhap = 'us001'

exec sp_sl_pb N'Phòng Nhân sự'

alter table nhan_vien
add NgayBatDau DATE

update nhan_vien set ngaybatdau = '2020-10-10' where manhanvien ='NV001';
update nhan_vien set ngaybatdau = '2023-12-11' where manhanvien ='NV002';
update nhan_vien set ngaybatdau = '2024-07-10' where manhanvien ='NV003';
update nhan_vien set ngaybatdau = '2023-09-20' where manhanvien ='NV004';
update nhan_vien set ngaybatdau = '2023-09-22' where manhanvien ='NV005';
update nhan_vien set ngaybatdau = '2023-10-10' where manhanvien ='NV006';
update nhan_vien set ngaybatdau = '2024-05-10' where manhanvien ='NV007';
update nhan_vien set ngaybatdau = '2023-11-10' where manhanvien ='NV008';
update nhan_vien set ngaybatdau = '2024-03-10' where manhanvien ='NV009';
update nhan_vien set ngaybatdau = '2023-12-10' where manhanvien ='NV010';
update nhan_vien set ngaybatdau = '2023-12-22' where manhanvien ='NV011';

alter table nhan_vien
add NgayKetThuc DATE


UPDATE nhan_vien
SET NgayKetThuc = DATEADD(MONTH, loai_hop_dong.thoihan, nhan_vien.ngaybatdau)
FROM nhan_vien
JOIN loai_hop_dong
ON Loai_Hop_Dong.MaLoaiHd = Nhan_Vien.MaLoaiHd