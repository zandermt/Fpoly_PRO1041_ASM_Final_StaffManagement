/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASS;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class NhanVien {
    private String MaNhanVien;
    private String HoTen;
    private Date NgaySinh;
    private String SDT;
    private boolean GioiTinh;
    private String DiaChi;
    private String Email;
    private String MaPhongban;
    private String MaChucVu;
    private String MaUngVien;
    private String MaLoaiHd;
    private double Luong = 0;
    private Date NgayBatDau;
    private Date NgayKetThuc;

    public NhanVien() {
    }

    public NhanVien(String MaNhanVien, String HoTen, Date NgaySinh, String SDT, boolean GioiTinh, String DiaChi, String Email, String MaPhongban, String MaChucVu, String MaUngVien, String MaLoaiHd, double Luong, Date NgayBatDau, Date NgayKetThuc) {
        this.MaNhanVien = MaNhanVien;
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.SDT = SDT;
        this.GioiTinh = GioiTinh;
        this.DiaChi = DiaChi;
        this.Email = Email;
        this.MaPhongban = MaPhongban;
        this.MaChucVu = MaChucVu;
        this.MaUngVien = MaUngVien;
        this.MaLoaiHd = MaLoaiHd;
        this.Luong = Luong;
        this.NgayBatDau = NgayBatDau;
        this.NgayKetThuc = NgayKetThuc;
    }

    public String getMaNhanVien() {
        return this.MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getHoTen() {
        return this.HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public Date getNgaySinh() {
        return this.NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getSDT() {
        return this.SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public boolean isGioiTinh() {
        return this.GioiTinh;
    }

    public boolean getGioiTinh() {
        return this.GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getDiaChi() {
        return this.DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getMaPhongban() {
        return this.MaPhongban;
    }

    public void setMaPhongban(String MaPhongban) {
        this.MaPhongban = MaPhongban;
    }

    public String getMaChucVu() {
        return this.MaChucVu;
    }

    public void setMaChucVu(String MaChucVu) {
        this.MaChucVu = MaChucVu;
    }

    public String getMaUngVien() {
        return this.MaUngVien;
    }

    public void setMaUngVien(String MaUngVien) {
        this.MaUngVien = MaUngVien;
    }

    public String getMaLoaiHd() {
        return this.MaLoaiHd;
    }

    public void setMaLoaiHd(String MaLoaiHd) {
        this.MaLoaiHd = MaLoaiHd;
    }

    public double getLuong() {
        return this.Luong;
    }

    public void setLuong(double Luong) {
        this.Luong = Luong;
    }
    
    public void setNgayBatDau(Date NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }
    
    public Date getNgayBatDau() {
        return this.NgayBatDau;
    }
    
    public void setNgayKetThuc(Date NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }
    
    public Date getNgayKetThuc() {
        return this.NgayKetThuc;
    }
    
    @Override
    public String toString() {
        return this.MaLoaiHd; // hoặc trả về bất kỳ thông tin nào bạn muốn hiển thị
    }   
}