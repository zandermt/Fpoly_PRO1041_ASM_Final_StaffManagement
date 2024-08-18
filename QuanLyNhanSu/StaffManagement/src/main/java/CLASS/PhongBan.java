/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASS;

/**
 *
 * @author Admin
 */
public class PhongBan {
    private String MaPhongBan;
    private String TenPhongBan;
    private String MoTa;
    private String MaTruongPhong;

    public PhongBan() {
    }

    public PhongBan(String MaPhongBan, String TenPhongBan, String MoTa, String MaTruongPhong) {
        this.MaPhongBan = MaPhongBan;
        this.TenPhongBan = TenPhongBan;
        this.MoTa = MoTa;
        this.MaTruongPhong = MaTruongPhong;
    }

    public String getMaPhongBan() {
        return this.MaPhongBan;
    }

    public void setMaPhongBan(String MaPhongBan) {
        this.MaPhongBan = MaPhongBan;
    }

    public String getTenPhongBan() {
        return this.TenPhongBan;
    }

    public void setTenPhongBan(String TenPhongBan) {
        this.TenPhongBan = TenPhongBan;
    }

    public String getMoTa() {
        return this.MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getMaTruongPhong() {
        return this.MaTruongPhong;
    }

    public void setMaTruongPhong(String MaTruongPhong) {
        this.MaTruongPhong = MaTruongPhong;
    }
    @Override
    public String toString() {
        return this.MaPhongBan; // hoặc trả về bất kỳ thông tin nào bạn muốn hiển thị
    }
    public String toTenPb(){
        return this.TenPhongBan;
    }
}
