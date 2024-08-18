/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package UTILS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class TEST_CONNECT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         String dbUrl = "jdbc:sqlserver://13.71.106.166:1433;databaseName=SD19301_Team5;encrypt=true;trustServerCertificate=true;";
        String admin ="usr01_team5";
        String pass ="qlnhanvien";
        try{
            Class.forName(driver);
            Connection a = DriverManager.getConnection(dbUrl,admin,pass);
            System.out.print("success");
        }
        catch (Exception e){
            System.out.print("fail");
            e.printStackTrace();
            
        }
        
    }   
    
}
