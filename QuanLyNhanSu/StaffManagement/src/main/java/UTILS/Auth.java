/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTILS;

import CLASS.NhanVien;
import CLASS.TaiKhoan;
import DAO.NhanVienDAO;

/**
 *
 * @author Zander
 */
public class Auth {
    public static TaiKhoan user = null;
    public static NhanVienDAO nv;

    public static void clear() {
        Auth.user = null;
        Auth.nv =null;
    }

    public static boolean isLogin() {
        
        return Auth.user != null;
    }
    
//    public static boolean isManager() {
//        // Kiểm tra xem người dùng đã đăng nhập và đối tượng DAO không null
//        if (Auth.user == null || Auth.nv == null) {
//            return false;
//        }
//        NhanVien nhanVien = Auth.nv.selectById(id)
//
//        // Kiểm tra xem người dùng có phải là quản lý không
//        return Auth.nv.isManager(Auth.user.getMaNhanVien());
//    }
    
    public static boolean isManager(){
        NhanVienDAO nvdao = new NhanVienDAO();
        NhanVien nhanVien = nvdao.selectById(Auth.user.getMaNhanVien());
        if (Auth.user == null ) {
            return false; // Không đăng nhập hoặc không có thông tin nhân viên
        }        
        
        if (nhanVien == null) {
            return false; // Không tìm thấy nhân viên
        }
        
        System.out.println(nhanVien.getMaChucVu());
        System.out.println("CV001".equals(nhanVien.getMaChucVu()));
        
        return "CV001".equals(nhanVien.getMaChucVu().trim()); // Kiểm tra mã chức vụ
    }
 
}
