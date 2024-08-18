/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CLASS.UngVien;
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
public class UngVienDAO extends HRDAO<UngVien, String>{
    String INSERT_SQL="INSERT INTO Ung_Vien (MaUngVien, HoTen, GioiTinh, DiaChi, SDT, Email, NgaySinh, MaChucVu, MoTa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    String UPDATE_SQL="UPDATE Ung_Vien SET HoTen = ?, GioiTinh = ?, DiaChi = ?, SDT = ?, Email = ?, NgaySinh = ?, MaChucVu = ?, MoTa = ? WHERE MaUngVien = ?";
    String DELETE_SQL = "DELETE FROM Ung_Vien WHERE MaUngVien = ?";
    String SELECT_ALL_SQL ="SELECT * FROM Ung_Vien";
    String SELECT_BY_ID_SQL = "SELECT * FROM Ung_Vien WHERE MaUngVien = ?";

    @Override
    public void insert(UngVien entity) {
        try {
            JDBCHelper.update(INSERT_SQL,entity.getMaUngVien(),entity.getHoTen(),entity.isGioiTinh(),entity.getDiaChi(),entity.getSDT(),entity.getEmail(),entity.getNgaySinh(),entity.getMaChucVu(),entity.getMoTa());
        } catch (SQLException ex) {
            Logger.getLogger(UngVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(UngVien entity) {
        try {
            JDBCHelper.update(UPDATE_SQL,entity.getHoTen(),entity.isGioiTinh(),entity.getDiaChi(),entity.getSDT(),entity.getEmail(),entity.getNgaySinh(),entity.getMaChucVu(),entity.getMoTa(),entity.getMaUngVien());
        } catch (SQLException ex) {
            Logger.getLogger(UngVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String id) {
        try{
            JDBCHelper.update(DELETE_SQL, id);
        }
        catch(SQLException ex) {
            Logger.getLogger(UngVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public UngVien selectById(String id) {
        List<UngVien> ds = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(ds.isEmpty()){
            return null;
        }
        return ds.get(0);        
    }

    @Override
    public List<UngVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<UngVien> selectBySql(String sql, Object... args) {
        List<UngVien> ds = new ArrayList<UngVien>();
        try{
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                UngVien a = new UngVien();
                a.setMaUngVien(rs.getString("MaUngVien"));
                a.setHoTen(rs.getNString("HoTen"));
                a.setSDT(rs.getString("SDT"));
                a.setDiaChi(rs.getNString("DiaChi"));
                a.setNgaySinh(rs.getDate("NgaySinh"));
                a.setGioiTinh(rs.getBoolean("GioiTinh")); 
                a.setEmail(rs.getString("Email"));
                a.setMoTa(rs.getNString("MoTa"));
                a.setMaChucVu(rs.getString("MaChucVu"));
                ds.add(a);
            }
            rs.getStatement().getConnection().close();
            return ds;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<UngVien> selectByHoTen(String hoten){
        String sql="Select * from Ung_Vien where HoTen like ?";
        return this.selectBySql(sql,"%"+hoten+"%");
    }
    
    public  List<UngVien> selectByMaChucVu(String id) {
        String sql="Select * from Ung_Vien where MaChucVu like ?";
        return this.selectBySql(sql,"%"+id+"%");              
    }
    
    public UngVien selectBySdt(String id) {
        List<UngVien> ds = this.selectBySql("Select * from Ung_Vien where SDT like ?", id);
        if(ds.isEmpty()){
            return null;
        }
        return ds.get(0);        
    }
    
    public UngVien selectByEmail(String id) {
        List<UngVien> ds = this.selectBySql("Select * from Ung_Vien where Email like ?", id);
        if(ds.isEmpty()){
            return null;
        }
        return ds.get(0);        
    }

    public boolean isEmailExists(String email) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        UngVien nguoiDung = null;

        String query = "SELECT COUNT(*) FROM Ung_Vien WHERE Email = ?";
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
        String query = "SELECT COUNT(*) FROM Ung_vien WHERE SDT = ?";
        PreparedStatement ps = a.prepareStatement(query);
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            int count = rs.getInt(1);
            return count>0;
        }
        return false;
    }
    
    public String getNextMaUngVien() {
        String sql = "SELECT MAX(MaUngVien) AS MaxMaUngVien FROM Ung_Vien";
        try {
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maxMaUngVien = rs.getString("MaxMaUngVien");
                if (maxMaUngVien != null) {
                    int nextId = Integer.parseInt(maxMaUngVien.substring(2)) + 1;
                    return String.format("UV%03d", nextId);
                }
            }
            return "UV001"; // Trường hợp không có dữ liệu nào
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy mã ứng viên tiếp theo", e);
        }
    }
    
    public String tenCv(String manv, String machucvu) {
    String sql = "SELECT TenChucVu FROM Ung_Vien "
                 + "JOIN Chuc_Vu ON Chuc_Vu.MaChucVu = Ung_Vien.MaChucVu "
                 + "WHERE Ung_Vien.MaUngVien = ? AND Ung_Vien.MaChucVu = ?";
    return (String) JDBCHelper.value(sql, manv, machucvu);
    }
    
    public String maCv (String tencv){
        String sql = "Select maChucVu from chuc_vu where tenchucvu like ?";
        return (String) JDBCHelper.value(sql,tencv);
    }
}
