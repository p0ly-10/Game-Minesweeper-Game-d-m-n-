/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import POJO.nguoiChoi;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class nguoichoiDAO {
    public static ArrayList<nguoiChoi> layDanhSachNguoiChoi(){
        ArrayList<nguoiChoi> dsNC = new ArrayList<nguoiChoi>();
        try {
            String sql = "SELECT * FROM NGUOICHOI";
            DBConnect provider = new DBConnect();
            provider.open();
            ResultSet rs = provider.executeQuery(sql);
            while(rs.next()){
                nguoiChoi nc = new nguoiChoi();
                nc.setTenNguoiChoi(rs.getString("TENNGUOICHOI"));
                nc.setSoTranDaChoi(rs.getInt("SOTRANDACHOI"));
                nc.setSoTranThang(rs.getInt("SOTRANTHANG"));
               
                dsNC.add(nc);
            }
            provider.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsNC;
    }
    public static boolean themNguoiChoi(nguoiChoi nc){
         boolean kq = false;
         String sql = String.format("INSERT INTO NGUOICHOI VALUES ('%s'),'0','0';",nc.getTenNguoiChoi());
         DBConnect provider = new DBConnect();
         provider.open();
         int n = provider.excuteUpdate(sql);
         if(n == 1){
             kq = true;
         }
         provider.close();
         return kq;
     }
    public static boolean kiemTraDangNhap(String tenNC,String mk) throws SQLException{
       
        boolean kq = false;
        String cautruyvan = "select * from NGUOICHOI where TENNGUOICHOI= '" + tenNC + "' and MATKHAU= '" + mk + "' ";
        //SSystem.out.println(cautruyvan);
        DBConnect provider = new DBConnect();
            provider.open();
            ResultSet rs = provider.executeQuery(cautruyvan);
        if (rs.next()) { 
                kq = true;
            }
        return kq;

    }
    public static boolean kiemTraTaiKhoan(String tenNC) {
    boolean exists = false;
    try {
        String sql = "SELECT * FROM NGUOICHOI WHERE TENNGUOICHOI = '" + tenNC + "'";
        DBConnect provider = new DBConnect();
        provider.open();
        ResultSet rs = provider.executeQuery(sql);
        if (rs.next()) {
            exists = true;
        }
        provider.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return exists;
}
     public static nguoiChoi addThongTin(String tenNC,String mk)
    {
        nguoiChoi nc = new nguoiChoi();
        try {
            String cautruyvan = "select * from NGUOICHOI where TENNGUOICHOI= '" + tenNC + "' and MATKHAU= '" + mk + "' ";
            DBConnect provider = new DBConnect();
            provider.open();
            ResultSet rs = provider.executeQuery(cautruyvan);
            while(rs.next()){
                nc.setTenNguoiChoi(rs.getString("TENNGUOICHOI"));
                
                nc.setSoTranDaChoi(rs.getInt("SOTRANDACHOI"));
                nc.setSoTranThang(rs.getInt("SOTRANTHANG"));
              
                
            }
            provider.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nc;
    }
    
    public static boolean capNhatSoTran(nguoiChoi nc) {
        boolean kq = false;
       String sql = String.format("UPDATE NGUOICHOI SET SOTRANDACHOI = %d WHERE TENNGUOICHOI = '%s'", 
                                nc.getSoTranDaChoi(), nc.getTenNguoiChoi());;        
        DBConnect provider = new DBConnect();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if (n == 1){
            kq = true;
        }
        provider.close();
        return kq;
    }
    public static boolean capNhatSoTranThang(nguoiChoi nc) {
        boolean kq = false;
        String sql = String.format("UPDATE NGUOICHOI SET SOTRANTHANG= '%d'" 
                +"WHERE TENNGUOICHOI= '%s'", nc.getSoTranThang(),nc.getTenNguoiChoi());        
        DBConnect provider = new DBConnect();
        provider.open();
        int n = provider.excuteUpdate(sql);
        if (n == 1){
            kq = true;
        }
        provider.close();
        return kq;
    }
    public static boolean dangKyTaiKhoan(String tenNC, String mk) {
    boolean success = false;
    tenNC = tenNC.replaceAll("'", "''"); 
    mk = mk.replaceAll("'", "''"); 
    String sql = "INSERT INTO NGUOICHOI (TENNGUOICHOI, MATKHAU, SOTRANDACHOI, SOTRANTHANG) VALUES ('" + tenNC + "', '" + mk + "', 0, 0)";
    DBConnect provider = new DBConnect();
    provider.open();
    int n = provider.excuteUpdate(sql);
    if (n == 1) {
        success = true;
    }
    provider.close();
    return success;}
}
