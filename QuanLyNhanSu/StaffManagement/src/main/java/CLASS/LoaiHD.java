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
public class LoaiHD {
    private String MaLoaiHd;
    private int ThoiHan;
    private String TenHopDong;
    private String MoTa;

    public LoaiHD() {
    }

    public LoaiHD(String MaLoaiHd, int ThoiHan, String TenHopDong, String MoTa) {
        this.MaLoaiHd = MaLoaiHd;
        this.ThoiHan = ThoiHan;
        this.TenHopDong = TenHopDong;
        this.MoTa = MoTa;
    }

    public String getMaLoaiHd() {
        return this.MaLoaiHd;
    }

    public void setMaLoaiHd(String MaLoaiHd) {
        this.MaLoaiHd = MaLoaiHd;
    }

    public int getThoiHan() {
        return this.ThoiHan;
    }

    public void setThoiHan(int ThoiHan) {
        this.ThoiHan = ThoiHan;
    }

    public String getTenHopDong() {
        return this.TenHopDong;
    }

    public void setTenHopDong(String TenHopDong) {
        this.TenHopDong = TenHopDong;
    }

    public String getMoTa() {
        return this.MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }
    @Override
    public String toString() {
        return this.MaLoaiHd; // hoặc trả về bất kỳ thông tin nào bạn muốn hiển thị
    }
    public String toTenHd(){
        return this.TenHopDong;
    }
}
