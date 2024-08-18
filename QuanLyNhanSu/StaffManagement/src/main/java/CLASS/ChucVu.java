/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASS;

/**
 *
 * @author Admin
 */
public class ChucVu {
    private String MaChucVu;
    private String TenChucVu;
    private String Mota;

    public ChucVu() {
    }

    public ChucVu(String MaChucVu, String TenChucVu, String Mota) {
        this.MaChucVu = MaChucVu;
        this.TenChucVu = TenChucVu;
        this.Mota = Mota;
    }

    public String getMaChucVu() {
        return this.MaChucVu;
    }

    public void setMaChucVu(String MaChucVu) {
        this.MaChucVu = MaChucVu;
    }

    public String getTenChucVu() {
        return this.TenChucVu;
    }

    public void setTenChucVu(String TenChucVu) {
        this.TenChucVu = TenChucVu;
    }

    public String getMota() {
        return this.Mota;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }
    @Override
    public String toString() {
        return this.MaChucVu; // hoặc trả về bất kỳ thông tin nào bạn muốn hiển thị
    }
    public String toTenCv(){
        return this.TenChucVu;
    }
}
