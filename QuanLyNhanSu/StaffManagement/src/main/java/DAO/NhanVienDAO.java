/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CLASS.NhanVien;
import UTILS.JDBCHelper;
import static UTILS.JDBCHelper.a;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class NhanVienDAO extends HRDAO<NhanVien, String>{
    String INSERT_SQL="INSERT INTO Nhan_Vien (MaNhanVien, HoTen, GioiTinh, DiaChi, SDT, Email, NgaySinh, MaPhongBan, MaChucVu, MaUngVien, MaLoaiHD, Luong, NgayBatDau, NgayKetThuc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    String UPDATE_SQL="UPDATE Nhan_Vien SET HoTen = ?, GioiTinh = ?, DiaChi = ?, SDT = ?, Email = ?, NgaySinh = ?, MaPhongBan = ?, MaChucVu = ?, MaUngVien = ?, MaLoaiHD = ?, Luong = ?, NgayBatDau = ?, NgayKetThuc = ? WHERE maNhanVien = ?";
    String DELETE_SQL = "DELETE FROM Nhan_Vien WHERE maNhanVien = ?";
    String SELECT_ALL_SQL ="SELECT * FROM Nhan_Vien";
    String SELECT_BY_ID_SQL = "SELECT * FROM Nhan_Vien WHERE MaNhanVien = ?";

    @Override
    public void insert(NhanVien entity) {
        try {
            JDBCHelper.update(INSERT_SQL,entity.getMaNhanVien(),entity.getHoTen(),entity.getGioiTinh(),entity.getDiaChi(),entity.getSDT(),entity.getEmail(),entity.getNgaySinh(),entity.getMaPhongban(),entity.getMaChucVu(),entity.getMaUngVien(),entity.getMaLoaiHd(),entity.getLuong(),entity.getNgayBatDau(),entity.getNgayKetThuc());
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(NhanVien entity) {
        try {
            JDBCHelper.update(UPDATE_SQL,entity.getHoTen(),entity.getGioiTinh(),entity.getDiaChi(),entity.getSDT(),entity.getEmail(),entity.getNgaySinh(),entity.getMaPhongban(),entity.getMaChucVu(),entity.getMaUngVien(),entity.getMaLoaiHd(),entity.getLuong(),entity.getNgayBatDau(),entity.getNgayKetThuc(),entity.getMaNhanVien());
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String id) {
        try{
            JDBCHelper.update(DELETE_SQL, id);
        }
        catch(SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> ds = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(ds.isEmpty()){
            return null;
        }
        return ds.get(0);        
    }
    public List<NhanVien> selectByten(String hoten){
        String sql="Select * from Nhan_Vien where hoten like ?";
        return this.selectBySql(sql,"%"+hoten+"%");
    }
    
    public List<NhanVien> selectByMaPb(String id) {
        String sql="Select * from Nhan_Vien where MaPhongBan like ?";
        return this.selectBySql(sql,"%"+id+"%");                
    }
    
    public  List<NhanVien> selectByMaCv(String id) {
        String sql="Select * from Nhan_Vien where MaChucVu like ?";
        return this.selectBySql(sql,"%"+id+"%");              
    }
    
    public NhanVien selectByMaUv(String id) {
        List<NhanVien> ds = this.selectBySql("Select * from Nhan_Vien where MaUngVien like ?","%"+id+"%");
        if(ds.isEmpty()){
            return null;
        }
        return ds.get(0);        
    }
    
    public List<NhanVien> selectByMaLoaiHD(String id) {
        String sql="Select * from Nhan_Vien where MaLoaiHd like ?";
        return this.selectBySql(sql,"%"+id+"%");            
    }
    
    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> ds = new ArrayList<NhanVien>();
        try{
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                NhanVien a = new NhanVien();
                a.setMaNhanVien(rs.getString("MaNhanVien"));
                a.setHoTen(rs.getNString("HoTen"));
                a.setGioiTinh(rs.getBoolean("GioiTinh"));
                a.setDiaChi(rs.getNString("DiaChi"));
                a.setSDT(rs.getString("SDT"));
                a.setEmail(rs.getString("Email"));
                a.setNgaySinh(rs.getDate("NgaySinh"));
                a.setMaPhongban(rs.getString("MaPhongBan"));
                a.setMaChucVu(rs.getString("MaChucVu"));
                a.setMaUngVien(rs.getString("MaUngVien"));
                a.setMaLoaiHd(rs.getString("MaLoaiHD"));
                a.setLuong(rs.getDouble("Luong"));
                a.setNgayBatDau(rs.getDate("NgayBatDau"));
                a.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                ds.add(a);
            }
            rs.getStatement().getConnection().close();
            return ds;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public boolean isEmailExists(String email) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        NhanVien nguoiDung = null;

        String query = "SELECT COUNT(*) FROM Nhan_Vien WHERE Email = ?";
        statement = a.prepareStatement(query);
        statement.setString(1, email);

        rs = statement.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }
    
    public boolean isPhoneExists(String phone) throws SQLException {
        String query = "SELECT COUNT(*) FROM Nhan_vien WHERE SDT = ?";
        PreparedStatement ps = a.prepareStatement(query);
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            int count = rs.getInt(1);
            return count>0;
        }
        return false;
    }
    
    public String tenCv(String manv, String machucvu) {
    String sql = "SELECT TenChucVu FROM Nhan_Vien "
                 + "JOIN Chuc_Vu ON Chuc_Vu.MaChucVu = Nhan_Vien.MaChucVu "
                 + "WHERE Nhan_Vien.MaNhanVien = ? AND Nhan_Vien.MaChucVu = ?";
    return (String) JDBCHelper.value(sql, manv, machucvu);
    }
    
    public String tenPb(String manv, String maphongban) {
    String sql = "SELECT TenPhongBan FROM Nhan_Vien "
                 + "JOIN Phong_Ban ON Phong_Ban.MaPhongBan = nhan_vien.MaPhongBan "
                 + "WHERE Nhan_Vien.MaNhanVien = ? AND Nhan_Vien.MaPhongBan = ?";
    return (String) JDBCHelper.value(sql, manv, maphongban);
    }
    
    public String tenLhd(String manv, String maloaihd) {
        String sql = "SELECT TenHopDong FROM Nhan_Vien "
                     + "JOIN Loai_Hop_Dong ON Loai_Hop_Dong.MaLoaiHd = Nhan_Vien.MaLoaiHd "
                     + "WHERE Nhan_Vien.MaNhanVien = ? AND Nhan_Vien.MaLoaiHd = ?";
        return (String) JDBCHelper.value(sql, manv, maloaihd);
    }
    
    public String maCv (String tencv){
        String sql = "Select maChucVu from chuc_vu where tenchucvu like ?";
        return (String) JDBCHelper.value(sql,tencv);
    }
    
    public String maPb (String tenpb){
        String sql = "Select maPhongBan from phong_ban where tenphongban like ?";
        return (String) JDBCHelper.value(sql,tenpb);
    }
    
    public String maLhd (String tenlhd){
        String sql = "Select maloaihd from loai_hop_dong where tenhopdong like ?";
        return (String) JDBCHelper.value(sql,tenlhd);
    }
    
    public String getNextMaNhanVien() {
        String sql = "SELECT MAX(MaNhanVien) AS MaxMaNhanVien FROM Nhan_Vien";
        try {
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maxMaNhanVien = rs.getString("MaxMaNhanVien");
                if (maxMaNhanVien != null) {
                    int nextId = Integer.parseInt(maxMaNhanVien.substring(2)) + 1;
                    return String.format("NV%03d", nextId);
                }
            }
            return "NV001";
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy mã nhân viên tiếp theo", e);
        }
    }
    
    

}
