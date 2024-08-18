/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CLASS.LoaiHD;
import UTILS.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for LoaiHD.
 */
public class LoaiHDDAO extends HRDAO<LoaiHD, String> {
    String INSERT_SQL = "INSERT INTO Loai_Hop_dong (MaLoaiHd, ThoiHan, TenHopDong, MoTa) VALUES (?, ?, ?, ?);";
    String UPDATE_SQL = "UPDATE Loai_Hop_Dong SET TenHopDong = ?, ThoiHan = ?, MoTa = ? WHERE MaLoaiHd = ?";
    String DELETE_SQL = "DELETE FROM Loai_Hop_Dong WHERE MaLoaiHd = ?";
    String SELECT_ALL_SQL = "SELECT * FROM Loai_Hop_Dong";
    String SELECT_BY_ID_SQL = "SELECT * FROM Loai_Hop_Dong WHERE MaLoaiHd = ?";

    @Override
    public void insert(LoaiHD entity) {
        try {
            JDBCHelper.update(INSERT_SQL, entity.getMaLoaiHd(), entity.getThoiHan(), entity.getTenHopDong(), entity.getMoTa());
        } catch (SQLException ex) {
            Logger.getLogger(LoaiHDDAO.class.getName()).log(Level.SEVERE, "Insert failed", ex);
        }
    }

    @Override
    public void update(LoaiHD entity) {
        try {
            JDBCHelper.update(UPDATE_SQL, entity.getTenHopDong(), entity.getThoiHan(), entity.getMoTa(), entity.getMaLoaiHd());
        } catch (SQLException ex) {
            Logger.getLogger(LoaiHDDAO.class.getName()).log(Level.SEVERE, "Update failed", ex);
        }
    }

    @Override
    public void delete(String id) {
        try {
            JDBCHelper.update(DELETE_SQL, id);
        } catch (SQLException ex) {
            Logger.getLogger(LoaiHDDAO.class.getName()).log(Level.SEVERE, "Delete failed", ex);
        }
    }

    @Override
    public LoaiHD selectById(String id) {
        List<LoaiHD> ds = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (ds.isEmpty()) {
            return null;
        }
        return ds.get(0);
    }

    @Override
    public List<LoaiHD> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<LoaiHD> selectBySql(String sql, Object... args) {
        List<LoaiHD> ds = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LoaiHD a = new LoaiHD();
                a.setMaLoaiHd(rs.getString("MaLoaiHd"));
                a.setThoiHan(rs.getInt("ThoiHan"));
                a.setTenHopDong(rs.getNString("TenHopDong"));
                a.setMoTa(rs.getNString("MoTa"));
                ds.add(a);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Select failed", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.getStatement().getConnection().close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoaiHDDAO.class.getName()).log(Level.SEVERE, "Failed to close resources", ex);
            }
        }
        return ds;
    }
    
    public String getNextMaLoaiHD() {
    String sql = "SELECT MAX(MaLoaiHopDong) FROM LoaiHD";
    try {
        ResultSet rs = JDBCHelper.query(sql);
        if (rs.next()) {
            String maxMaLoaiHD = rs.getString(1);
            if (maxMaLoaiHD != null) {
                // Tách phần số từ mã hiện tại
                int number = Integer.parseInt(maxMaLoaiHD.substring(3));
                // Tăng số lên 1 và tạo mã mới
                String newMaLoaiHD = String.format("LHD%02d", number + 1);
                return newMaLoaiHD;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    // Nếu bảng rỗng hoặc có lỗi, trả về mã đầu tiên
    return "LHD01";
}
}
