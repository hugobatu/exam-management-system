DROP SCHEMA IF EXISTS exam_management_schema CASCADE;
CREATE SCHEMA exam_management_schema;
SET search_path TO exam_management_schema;


-- Drop tables in reverse order of dependencies
drop table if exists BangTinh cascade;
drop table if exists ChiTietDangKy cascade;
drop table if exists LichThi cascade;
drop table if exists GiaHan cascade;
drop table if exists PhieuDuThi cascade;
drop table if exists DanhSachThi cascade;
drop table if exists PhongThi cascade;
drop table if exists ThiSinh cascade;
drop table if exists HoaDon cascade;
drop table if exists PhieuDangKy cascade;
drop table if exists KhachHang cascade;
drop table if exists NhanVien cascade;
drop table if exists PhongBan cascade;
drop table if exists Account cascade;
drop table if exists ChungChi cascade;


-- Bảng tài khoản (lưu tài khoản nhân viên)
create table Account (
	acc_id serial PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    hashed_password VARCHAR(255) NOT NULL,
    account_type VARCHAR(10),
    salt VARCHAR(255) NOT null
);
-- Bảng Phòng ban - tương ứng vai trò của nhân viên.
create table PhongBan(
	ma_phong_ban serial primary key,
	ten_phong_ban VARCHAR(100),
	luong int
);
-- Bảng Nhân viên
create table NhanVien(
	ma_nhan_vien serial primary key,
	ma_phong_ban int,
	acc_id int,
	ho_ten varchar(255),
	email varchar(255),
	so_dien_thoai varchar(15),
	gioi_tinh varchar(3) check (gioi_tinh in ('Nam', 'Nữ')),
	dia_chi text,
	foreign key (ma_phong_ban) references PhongBan(ma_phong_ban),
	foreign key (acc_id) references Account(acc_id)
);
-- Bảng Khách hàng
create table KhachHang (
    ma_khach_hang serial primary key,
    loai_khach_hang varchar(50) check (loai_khach_hang in ('Tự do', 'Đơn vị')),
    ho_ten varchar(255),
    so_dien_thoai varchar(15),
    email varchar(255),
    dia_chi text
);

-- Bảng Thí sinh
create table ThiSinh(
	ma_thi_sinh serial primary key,
	ma_khach_hang int,
	ho_ten varchar(255),
	ngay_sinh date,
	so_dien_thoai varchar(15),
	email varchar(255),
	dia_chi text,
	foreign key (ma_khach_hang) references KhachHang(ma_khach_hang)
);

-- Bảng Phiếu đăng ký
create table PhieuDangKy (
	ma_phieu_dang_ky serial,
	ma_khach_hang int,
	ngay_dang_ky date,
    trang_thai_thanh_toan varchar(50) check (trang_thai_thanh_toan in ('Huy', 'Da thanh toan', 'Chua thanh toan')),
    ghi_chu text,
    primary key (ma_phieu_dang_ky),
    foreign key (ma_khach_hang) references KhachHang(ma_khach_hang)
);

-- Bảng Phòng thi
create table PhongThi (
	ma_phong_thi serial primary key,
	suc_chua int,
	trang_thai varchar(50)
);

-- Bảng Chứng chỉ
create table ChungChi (
    ma_chung_chi serial primary key,
    ten_chung_chi varchar(255),
    loai_chung_chi varchar(100),
    le_phi_thi decimal(12,3)
);

-- Bảng Lịch thi
create table LichThi (
    ma_lich_thi serial primary key,
    ma_phong_thi int,
    ma_giam_thi int,
	ma_chung_chi int,
    ngay_gio_thi timestamp,
    dia_diem_thi text,
    so_luong_dang_ky_con_lai int,
    foreign key (ma_phong_thi) references PhongThi(ma_phong_thi),
	foreign key (ma_giam_thi) references NhanVien(ma_nhan_vien),
	foreign key (ma_chung_chi) references ChungChi(ma_chung_chi)
);

CREATE TABLE ChiTietDangKy (
    ma_chi_tiet serial PRIMARY KEY,
    ma_thi_sinh int NOT NULL,
    ma_phieu_dang_ky int NOT NULL,
    ma_lich_thi int NOT NULL,
    foreign key (ma_thi_sinh) REFERENCES ThiSinh(ma_thi_sinh),
    foreign key (ma_phieu_dang_ky) REFERENCES PhieuDangKy(ma_phieu_dang_ky),
    foreign key (ma_lich_thi) REFERENCES LichThi(ma_lich_thi),
    UNIQUE (ma_thi_sinh, ma_phieu_dang_ky, ma_lich_thi)
);


-- Bảng Hóa đơn
create table HoaDon(
	ma_hoa_don serial primary key,
	ma_phieu_dang_ky int,
	ma_nhan_vien_lap int,
	ngay_thanh_toan date,
	hinh_thuc_thanh_toan varchar(50) check (hinh_thuc_thanh_toan in ('Tiền mặt', 'Chuyển khoản')),
	trang_thai varchar(50) check (trang_thai in ('Chưa thanh toán', 'Đã thanh toán')),
	tien_giam_gia decimal(12, 3),
	tong_tien decimal(12, 3),
	foreign key (ma_phieu_dang_ky) references PhieuDangKy(ma_phieu_dang_ky),
	foreign key (ma_nhan_vien_lap) references NhanVien(ma_nhan_vien)
);
-- Bảng Chi tiết hóa đơn
create table ChiTietHoaDon(
	ma_hoa_don int,
	ma_chi_tiet int,
	primary key (ma_hoa_don, ma_chi_tiet),
	foreign key (ma_chi_tiet) references ChiTietDangKy(ma_chi_tiet),
	foreign key (ma_hoa_don) references HoaDon(ma_hoa_don)
); 


-- Bảng Phiếu dự thi
create table PhieuDuThi (
	ma_phieu_du_thi serial,
	ma_chi_tiet int,
	so_bao_danh int,
	ngay_phat_hanh date,
	primary key (ma_phieu_du_thi),
	foreign key (ma_chi_tiet) references ChiTietDangKy(ma_chi_tiet)
);


-- Bảng Gia hạn
create table GiaHan (
	ma_gia_han serial,
	ma_phieu_du_thi int,
	ngay_gia_han date,
	li_do_gia_han varchar(50),
	trang_thai_gia_han varchar(50) check (trang_thai_gia_han in ('Chấp nhận', 'Từ chối')),
	phi_gia_han decimal(12, 3),
	primary key (ma_gia_han),
	foreign key (ma_phieu_du_thi) references PhieuDuThi(ma_phieu_du_thi)
);

-- Bảng bảng tính
create table BangTinh (
	ma_chung_chi_cap int primary key,
	ma_phieu_du_thi int,
	diem decimal(5, 2),
	ngay_cap date,
	ngay_het_han date,
	don_vi_cap varchar(255),
	trang_thai_nhan varchar(50) check (trang_thai_nhan in ('Đã nhận', 'Chưa nhận')),
	foreign key (ma_phieu_du_thi) references PhieuDuThi(ma_phieu_du_thi)
);

-- Số lượng đăng ký còn lại không được âm
ALTER TABLE LichThi
ADD CONSTRAINT check_so_luong_con_lai CHECK (so_luong_dang_ky_con_lai >= 0);


-- Hàm kiểm tra số lần gia hạn
CREATE OR REPLACE FUNCTION check_gia_han_limit()
RETURNS TRIGGER AS $$
BEGIN
    IF (
        SELECT COUNT(*) 
        FROM GiaHan 
        WHERE ma_phieu_du_thi = NEW.ma_phieu_du_thi
		AND trang_thai_gia_han = 'Chấp nhận'
    ) >= 2 THEN
        RAISE EXCEPTION 'Một phiếu dự thi chỉ được phép gia hạn tối đa 2 lần.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Gắn trigger vào bảng GiaHan
CREATE TRIGGER trg_check_gia_han_limit
BEFORE INSERT ON GiaHan
FOR EACH ROW
EXECUTE FUNCTION check_gia_han_limit();

CREATE OR REPLACE FUNCTION update_soluong_conlai()
RETURNS TRIGGER AS $$
DECLARE
    total_capacity INT;
    lich_thi_id INT;
BEGIN
    IF TG_OP = 'INSERT' THEN
        lich_thi_id := NEW.ma_lich_thi;

        UPDATE LichThi
        SET so_luong_dang_ky_con_lai = so_luong_dang_ky_con_lai - 1
        WHERE ma_lich_thi = lich_thi_id;
    ELSIF TG_OP = 'DELETE' THEN
        lich_thi_id := OLD.ma_lich_thi;

        UPDATE LichThi
        SET so_luong_dang_ky_con_lai = so_luong_dang_ky_con_lai + 1
        WHERE ma_lich_thi = lich_thi_id;
    END IF;

    SELECT suc_chua INTO total_capacity
    FROM PhongThi
    WHERE ma_phong_thi = (SELECT ma_phong_thi FROM LichThi WHERE ma_lich_thi = lich_thi_id);

    IF total_capacity IS NULL THEN
        RAISE EXCEPTION 'Không tìm thấy sức chứa cho phòng thi của lịch thi %', lich_thi_id;
    END IF;

    IF (SELECT so_luong_dang_ky_con_lai FROM LichThi WHERE ma_lich_thi = lich_thi_id) < 0 THEN
        RAISE EXCEPTION 'Số lượng đăng ký còn lại không thể âm cho lịch thi %', lich_thi_id;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;