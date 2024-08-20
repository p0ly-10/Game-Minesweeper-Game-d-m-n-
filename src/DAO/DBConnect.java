/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author PC
 */
public class DBConnect {
    private Connection connection;
    
    public void open(){
        String strServer = "DESKTOP-3UH0O3T";
        String strDatabase = "GameDoMin";
        String strUser = "sa";
        String strPassword = "123";
        try {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(driver);
            String connectionURL = "jdbc:sqlserver://" + strServer
                    + ":1433;databaseName=" + strDatabase
                    + ";user=" + strUser + ";password=" + strPassword;
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("ket noi ok");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
            
    }
    public void close(){
        try {
            this.connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
            
    }
    
    public ResultSet executeQuery(String sql){
        ResultSet rs = null;
        try {
            Statement sm =  connection.createStatement();
            rs = sm.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int excuteUpdate(String sql){
        int n = -1;
        try {
            Statement sm = connection.createStatement();
            n = sm.executeUpdate(sql);
        } catch 
                (Exception e) {
            e.printStackTrace();
        }
        return n;
    }
}
