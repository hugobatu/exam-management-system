DROP SCHEMA IF EXISTS exam_management_schema CASCADE;
SET search_path TO exam_management_schema;
create schema exam_management_schema;

-- table account
-- Drop tables in reverse order of dependencies
drop table if exists BangTinh cascade;
drop table if exists KetQuaThi cascade;
drop table if exists ChiTietDangKy cascade;
drop table if exists LichThi cascade;
drop table if exists GiamThi cascade;
drop table if exists GiaHan cascade;
drop table if exists PhieuDuThi cascade;
drop table if exists DanhSachThi cascade;
drop table if exists PhongThi cascade;
drop table if exists ThiSinh cascade;
drop table if exists HoaDon cascade;
drop table if exists ThanhToan cascade;
drop table if exists PhieuDangKy cascade;
drop table if exists KhachHang cascade;
drop table if exists NhanVien cascade;
drop table if exists PhongBan cascade;
drop table if exists Account cascade;
drop table if exists ChungChi cascade;

create table Account (
    username VARCHAR(255) PRIMARY KEY,
    hashed_password VARCHAR(255) NOT NULL,
    account_type VARCHAR(10),
    salt VARCHAR(255) NOT null
);
create table PhongBan(
	ma_phong_ban serial primary key,
	ten_phong_ban VARCHAR(100)
);
create table NhanVien(
	ma_nhan_vien serial primary key,
	ma_phong_ban int,
	username VARCHAR(255),
	foreign key (ma_phong_ban) references PhongBan(ma_phong_ban),
	foreign key (username) references Account(username)
);
-- table KhachHang
create table KhachHang (
    ma_khach_hang serial primary key,
    loai_khach_hang varchar(50),
    ho_ten varchar(255),
    so_dien_thoai varchar(15),
    email varchar(255),
    dia_chi text
);
-- table PhieuDangKy
create table PhieuDangKy (
	ma_phieu_dang_ky serial,
	ma_khach_hang int,
	ngay_dang_ky date,
    trang_thai_thanh_toan varchar(50),
    ghi_chu text,
    nhan_vien_tiep_nhan int,
    primary key (ma_phieu_dang_ky),
    foreign key (ma_khach_hang) references KhachHang(ma_khach_hang)
);
-- table ThanhToan
create table ThanhToan(
	ma_thanh_toan serial,
	ma_phieu_dang_ky int,
	ngay_thanh_toan date,
	hinh_thuc_thanh_toan varchar(50),
	tien_giam_gia decimal(12, 3),
	so_tien decimal(12, 3),
	nhan_vien_thanh_toan int,
	primary key (ma_thanh_toan),
	foreign key (ma_phieu_dang_ky) references PhieuDangKy(ma_phieu_dang_ky),
	foreign key (nhan_vien_thanh_toan) references NhanVien(ma_nhan_vien)
);
-- table HoaDon
create table HoaDon(
	ma_hoa_don serial,
	ma_thanh_toan int,
	primary key (ma_hoa_don),
	foreign key (ma_thanh_toan) references ThanhToan(ma_thanh_toan)
);
-- table ThiSinh
create table ThiSinh(
	ma_thi_sinh serial,
	ma_khach_hang int,
	ho_ten varchar(255),
	ngay_sinh date,
	so_dien_thoai varchar(15),
	email varchar(255),
	dia_chi text,
	primary key (ma_thi_sinh),
	foreign key (ma_khach_hang) references KhachHang(ma_khach_hang)
);
-- table PhongThi
create table PhongThi (
	ma_phong_thi serial,
	suc_chua int,
	trang_thai varchar(50),
	primary key (ma_phong_thi)
);
-- table DanhSachThi
create table DanhSachThi (
	ma_danh_sach serial,
	ma_phong_thi int,
	primary key (ma_danh_sach),
	foreign key (ma_phong_thi) references PhongThi(ma_phong_thi)
);
-- table PhieuDuThi
create table PhieuDuThi (
	ma_phieu_du_thi serial,
	so_bao_danh int,
	ngay_phat_hanh date,
	ma_phong_thi int,
	primary key (ma_phieu_du_thi),
	foreign key (ma_phong_thi) references PhongThi(ma_phong_thi)
);
-- table GiaHan
create table GiaHan (
	ma_gia_han serial,
	ma_phieu_du_thi int,
	ngay_gia_han date,
	li_do_gia_han varchar(50),
	trang_thai_gia_han varchar(50),
	phi_gia_han decimal(12, 3),
	nhan_vien_lap_phieu int,
	primary key (ma_gia_han),
	foreign key (ma_phieu_du_thi) references PhieuDuThi(ma_phieu_du_thi)
);
-- table GiamThi
create table GiamThi (
	ma_giam_thi serial,
	ho_ten varchar(255),
	so_dien_thoai varchar(15),
	email varchar(255),
	dia_chi text,
	primary key (ma_giam_thi)
);
-- table LichThi
create table LichThi (
    ma_lich_thi serial,
    ma_phong_thi int,
    ma_giam_thi int,
    ngay_thi date,
    gio_thi time,
    dia_diem_thi text,
    so_luong_thi_sinh_toi_da int,
    so_luong_dang_ky int,
    primary key (ma_lich_thi),
    foreign key (ma_phong_thi) references PhongThi(ma_phong_thi),
    foreign key (ma_giam_thi) references GiamThi(ma_giam_thi)
);
-- table ChiTietDangKy
create table ChiTietDangKy (
    ma_chi_tiet serial,
    ma_thi_sinh int,
    ma_phieu_dang_ky int,
    ma_lich_thi int,
    ma_phieu_du_thi int,
    primary key (ma_chi_tiet),
    foreign key (ma_thi_sinh) references ThiSinh(ma_thi_sinh),
	foreign key (ma_phieu_dang_ky) references PhieuDangKy(ma_phieu_dang_ky),
    foreign key (ma_lich_thi) references LichThi(ma_lich_thi),
    foreign key (ma_phieu_du_thi) references PhieuDuThi(ma_phieu_du_thi)
);
-- table KetQuaThi
create table KetQuaThi (
    ma_ket_qua serial,
    ma_phieu_du_thi int,
    diem decimal(3,2),
    ket_qua varchar(50),
    ngay_phat_hanh date,
    ghi_chu text,
    primary key (ma_ket_qua),
    foreign key (ma_phieu_du_thi) references PhieuDuThi(ma_phieu_du_thi)
);
-- table ChungChi
create table ChungChi (
    ma_chung_chi serial,
    ten_chung_chi varchar(255),
    loai_chung_chi varchar(100),
    le_phi_thi decimal(12,3),
    primary key (ma_chung_chi)
);
-- table BangTinh
create table BangTinh (
    ma_chung_chi int,
    ma_phieu_du_thi int,
    ma_ket_qua int,
    ngay_phat_hanh date,
    trang_thai_nhan varchar(50),
    primary key(ma_chung_chi, ma_phieu_du_thi),
    foreign key (ma_phieu_du_thi) references PhieuDuThi(ma_phieu_du_thi),
    foreign key (ma_chung_chi) references ChungChi(ma_chung_chi)
);
