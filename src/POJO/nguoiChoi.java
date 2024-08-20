/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author PC
 */
public class nguoiChoi {
    String tenNguoiChoi;
    String MatKhau;
    int soTranDaChoi;
    int soTranThang;
  

    public nguoiChoi() {
    }

    public nguoiChoi(String tenNguoiChoi, String MatKhau, int soTranDaChoi, int soTranThang) {
        this.tenNguoiChoi = tenNguoiChoi;
        this.MatKhau = MatKhau;
        this.soTranDaChoi = soTranDaChoi;
        this.soTranThang = soTranThang;
     
    }

    public String getTenNguoiChoi() {
        return tenNguoiChoi;
    }

    public void setTenNguoiChoi(String tenNguoiChoi) {
        this.tenNguoiChoi = tenNguoiChoi;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public int getSoTranDaChoi() {
        return soTranDaChoi;
    }

    public void setSoTranDaChoi(int soTranDaChoi) {
        this.soTranDaChoi = soTranDaChoi;
    }

    public int getSoTranThang() {
        return soTranThang;
    }

    public void setSoTranThang(int soTranThang) {
        this.soTranThang = soTranThang;
    }

  
    
}
