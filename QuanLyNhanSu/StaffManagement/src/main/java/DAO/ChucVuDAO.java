/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CLASS.ChucVu;
import UTILS.JDBCHelper;
import java.sql.Connection;
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
public class ChucVuDAO extends HRDAO<ChucVu, String>{
    String INSERT_SQL="INSERT INTO Chuc_Vu (MaChucVu, TenChucVu, MoTa) VALUES (?, ?, ?);";
    String UPDATE_SQL="UPDATE Chuc_Vu SET TenChucVu = ?, MoTa = ? WHERE MaChucVu = ?";
    String DELETE_SQL = "DELETE FROM Chuc_Vu WHERE MaChucVu = ?";
    String SELECT_ALL_SQL ="SELECT * FROM Chuc_Vu";
    String SELECT_BY_ID_SQL = "SELECT * FROM Chuc_Vu WHERE MaChucVu = ?";

    @Override
    public void insert(ChucVu entity) {
        try {
            JDBCHelper.update(INSERT_SQL,entity.getMaChucVu(),entity.getTenChucVu(),entity.getMota());
        } catch (SQLException ex) {
            Logger.getLogger(ChucVuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ChucVu entity) {
        try {
            JDBCHelper.update(UPDATE_SQL,entity.getTenChucVu(),entity.getMota(),entity.getMaChucVu());
        } catch (SQLException ex) {
            Logger.getLogger(ChucVuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String id) {
        try{
            JDBCHelper.update(DELETE_SQL, id);
        }
        catch(SQLException ex) {
            Logger.getLogger(ChucVuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ChucVu selectById(String id) {
        List<ChucVu> ds = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(ds.isEmpty()){
            return null;
        }
        return ds.get(0);        
    }
    
    public List<ChucVu> selectByten(String hoten){
        String sql="Select * from Chuc_Vu where tenChucVu like ?";
        return this.selectBySql(sql,"%"+hoten+"%");
    }
    

    @Override
    public List<ChucVu> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<ChucVu> selectBySql(String sql, Object... args) {
        List<ChucVu> ds = new ArrayList<ChucVu>();
        try{
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                ChucVu a = new ChucVu();
                a.setMaChucVu(rs.getString("MaChucVu"));
                a.setTenChucVu(rs.getNString("TenChucVu"));
                a.setMota(rs.getNString("MoTa"));
                ds.add(a);
            }
            rs.getStatement().getConnection().close();
            return ds;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public String getNextMaChucVu() {
        String sql = "SELECT MAX(MaChucVu) AS MaxMaChucVu FROM Chuc_Vu";
        try {
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maxMaChucVu = rs.getString("MaxMaChucVu");
                if (maxMaChucVu != null) {
                    int nextId = Integer.parseInt(maxMaChucVu.substring(2)) + 1;
                    return String.format("CV%03d", nextId);
                }
            }
            return "CV001";
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy mã chức vụ tiếp theo", e);
        }
    }
    
}
