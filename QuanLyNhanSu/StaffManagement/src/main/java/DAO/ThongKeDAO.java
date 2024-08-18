/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import UTILS.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThongKeDAO {
    private List<Object[]> getListOfArray(String sql,String[] cols,Object...args){
        try{
            List<Object[]> ds = new ArrayList<>();
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
                Object[] value = new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    value[i]=rs.getObject(cols[i]);
                }
                ds.add(value);
            }
            rs.getStatement().getConnection().close();
            return ds;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public List<Object[]> getUngVien(String tencv){
        String sql = "{CALL sp_sl_ungvien(?)}";
        String[] cols ={"TongUngVien","SLpass","SLfail"};
        return this.getListOfArray(sql, cols,tencv);
    }
    public List<Object[]> getTuoi(int mintuoi, int maxtuoi){
        String sql = "{CALL sp_sl_tuoi(?,?)}";
        String[] cols = {"SLNV"};
        return this.getListOfArray(sql, cols,mintuoi,maxtuoi);
    }
    public List<Object[]> getLuong(double minluong, double maxluong){
        String sql ="{CALL sp_sl_luong(?,?)}";
        String[] cols = {"SLNV"};
        return this.getListOfArray(sql, cols,minluong,maxluong);
    }
    public List<Object[]> getPhongBan(String tenpb){
        String sql ="{CALL sp_sl_pb(?)}";
        String[] cols = {"SLNV","SLCV","HOTENTRUONGPHONG"};
        return this.getListOfArray(sql, cols, tenpb);
    }
    public List<Object[]> getHopDong(){
        String sql ="{CALL sp_sl_hd()}";
        String[] cols = {"TenHopDong","SLNV"};
        return this.getListOfArray(sql, cols);
    }
}
