/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CLASS.TaiKhoan;
import UTILS.JDBCHelper;
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
public class TaiKhoanDAO extends HRDAO<TaiKhoan, String>{
    String INSERT_SQL="INSERT INTO Tai_Khoan (TenDangNhap, MatKhau, MaNhanVien) VALUES (?, ?, ?);";
    String UPDATE_SQL="UPDATE Tai_Khoan SET MatKhau = ?, MaNhanVien = ? WHERE TenDangNhap = ?";
    String DELETE_SQL = "DELETE FROM Tai_Khoan WHERE TenDangNhap = ?";
    String SELECT_ALL_SQL ="SELECT * FROM Tai_Khoan";
    String SELECT_BY_ID_SQL = "SELECT * FROM Tai_Khoan WHERE TenDangNhap = ?";

    @Override
    public void insert(TaiKhoan entity) {
        try {
            JDBCHelper.update(INSERT_SQL,entity.getTenDangNhap(),entity.getMatKhau(),entity.getMaNhanVien());
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(TaiKhoan entity) {
        try {
            JDBCHelper.update(UPDATE_SQL,entity.getMatKhau(),entity.getMaNhanVien(),entity.getTenDangNhap());
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String id) {
        try{
            JDBCHelper.update(DELETE_SQL, id);
        }
        catch(SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public TaiKhoan selectById(String id) {
        List<TaiKhoan> ds = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(ds.isEmpty()){
            return null;
        }
        return ds.get(0);        
    }

    @Override
    public List<TaiKhoan> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<TaiKhoan> selectBySql(String sql, Object... args) {
        List<TaiKhoan> ds = new ArrayList<TaiKhoan>();
        try{
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                TaiKhoan a = new TaiKhoan();
                a.setTenDangNhap(rs.getString("TenDangNhap"));
                a.setMatKhau(rs.getString("MatKhau"));
                a.setMaNhanVien(rs.getString("MaNhanVien"));
                ds.add(a);
            }
            rs.getStatement().getConnection().close();
            return ds;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public TaiKhoan selectByMaNhanVien(String maNhanVien) {
        String sql = "SELECT * FROM Tai_Khoan WHERE MaNhanVien = ?";
        List<TaiKhoan> list = this.selectBySql(sql, maNhanVien);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    public String maPb (String mapb){
        String sql = "select maphongban\n" +
"from tai_khoan\n" +
"right join nhan_vien on nhan_vien.manhanvien = tai_khoan.manhanvien\n" +
"where nhan_vien.manhanvien = ?";
        return (String) JDBCHelper.value(sql,mapb);
    }
    
    public String getNextTenTk() {
        String sql = "SELECT MAX(TenDangNhap) AS MaxTenDn FROM Tai_Khoan";
        try {
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maxTenDn = rs.getString("MaxTenDn");
                if (maxTenDn != null) {
                    int nextId = Integer.parseInt(maxTenDn.substring(2)) + 1;
                    return String.format("us%03d", nextId);
                }
            }
            return "UV001"; // Trường hợp không có dữ liệu nào
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy tên đăng nhập tiếp theo", e);
        }
    }
}
