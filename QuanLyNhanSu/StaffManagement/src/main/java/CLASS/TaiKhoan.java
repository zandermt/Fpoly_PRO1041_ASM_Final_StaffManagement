/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASS;

/**
 *
 * @author Admin
 */
public class TaiKhoan {
    private String TenDangNhap;
    private String MatKhau;
    private String MaNhanVien;

    public TaiKhoan() {
    }

    public TaiKhoan(String TenDangNhap, String MatKhau, String MaNhanVien) {
        this.TenDangNhap = TenDangNhap;
        this.MatKhau = MatKhau;
        this.MaNhanVien = MaNhanVien;
    }

    public String getTenDangNhap() {
        return this.TenDangNhap;
    }

    public void setTenDangNhap(String TenDangNhap) {
        this.TenDangNhap = TenDangNhap;
    }

    public String getMatKhau() {
        return this.MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getMaNhanVien() {
        return this.MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }
}
