/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CLASS.PhongBan;
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
public class PhongBanDAO extends HRDAO<PhongBan, String>{
    String INSERT_SQL="INSERT INTO Phong_Ban (MaPhongBan, TenPhongBan, MoTa, MaTruongPhong) VALUES (?, ?, ?, ?);";
    String UPDATE_SQL="UPDATE Phong_Ban SET TenPhongBan = ?, MoTa = ?, MaTruongPhong = ? WHERE MaPhongBan = ?";
    String DELETE_SQL = "DELETE FROM Phong_Ban WHERE MaPhongBan = ?";
    String SELECT_ALL_SQL ="SELECT * FROM Phong_Ban";
    String SELECT_BY_ID_SQL = "SELECT * FROM Phong_Ban WHERE MaPhongBan = ?";

    @Override
    public void insert(PhongBan entity) {
        try {
            JDBCHelper.update(INSERT_SQL,entity.getMaPhongBan(),entity.getTenPhongBan(),entity.getMoTa(),entity.getMaTruongPhong());
        } catch (SQLException ex) {
            Logger.getLogger(PhongBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(PhongBan entity) {
        try {
            JDBCHelper.update(UPDATE_SQL,entity.getTenPhongBan(),entity.getMoTa(),entity.getMaTruongPhong(),entity.getMaPhongBan());
        } catch (SQLException ex) {
            Logger.getLogger(PhongBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String id) {
        try{
            JDBCHelper.update(DELETE_SQL, id);
        }
        catch(SQLException ex) {
            Logger.getLogger(PhongBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public PhongBan selectById(String id) {
        List<PhongBan> ds = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(ds.isEmpty()){
            return null;
        }
        return ds.get(0);        
    }

    @Override
    public List<PhongBan> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<PhongBan> selectBySql(String sql, Object... args) {
        List<PhongBan> ds = new ArrayList<PhongBan>();
        try{
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                PhongBan a = new PhongBan();
                a.setMaPhongBan(rs.getString("MaPhongBan"));
                a.setTenPhongBan(rs.getNString("TenPhongBan"));
                a.setMoTa(rs.getNString("MoTa"));
                a.setMaTruongPhong(rs.getString("MaTruongPhong"));
                ds.add(a);
            }
            rs.getStatement().getConnection().close();
            return ds;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public List<PhongBan> selectByTenPhongBan(String id){
        String sql="Select * from Phong_Ban where TenPhongBan like ?";
        return this.selectBySql(sql,"%"+id+"%");
    }
    
    public List<PhongBan> selectByMaTruongPhong(String id){
        String sql="Select * from Phong_Ban where MaTruongPhong like ?";
        return this.selectBySql(sql,"%"+id+"%");
    }
    
    public String getNextMaPhongBan() {
    String sql = "SELECT MAX(MaPhongBan) FROM Phong_Ban";
    try {
        ResultSet rs = JDBCHelper.query(sql);
        if (rs.next()) {
            String maxMaPB = rs.getString(1);
            if (maxMaPB != null) {
                // Tách phần số từ MaPhongBan và tăng giá trị
                int nextNumber = Integer.parseInt(maxMaPB.substring(2)) + 1;
                // Ghép phần chữ "PB" với phần số đã tăng
                return String.format("PB%03d", nextNumber);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "PB001";
}

}
