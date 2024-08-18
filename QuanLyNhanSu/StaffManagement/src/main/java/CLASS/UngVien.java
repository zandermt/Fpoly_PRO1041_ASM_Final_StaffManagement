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
public class UngVien {
    private String MaUngVien;
    private String HoTen;
    private String SDT;
    private String DiaChi;
    private Date NgaySinh;
    private boolean GioiTinh;
    private String Email;
    private String MoTa;
    private String MaChucVu;

    public UngVien() {
    }

    public UngVien(String MaUngVien, String HoTen, String SDT, String DiaChi, Date NgaySinh, boolean GioiTinh, String Email, String MoTa, String MaChucVu) {
        this.MaUngVien = MaUngVien;
        this.HoTen = HoTen;
        this.SDT = SDT;
        this.DiaChi = DiaChi;
        this.NgaySinh = NgaySinh;
        this.GioiTinh = GioiTinh;
        this.Email = Email;
        this.MoTa = MoTa;
        this.MaChucVu = MaChucVu;
    }

    public String getMaUngVien() {
        return MaUngVien;
    }

    public void setMaUngVien(String MaUngVien) {
        this.MaUngVien = MaUngVien;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getMaChucVu() {
        return MaChucVu;
    }

    public void setMaChucVu(String MaChucVu) {
        this.MaChucVu = MaChucVu;
    }

    
}
