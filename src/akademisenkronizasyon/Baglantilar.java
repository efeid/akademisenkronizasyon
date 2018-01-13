/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akademisenkronizasyon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author umit
 */
public class Baglantilar {
    
    
        public  Connection OracleBaglanti(){
    
        Connection connection = null;

        try {

         Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return connection;

        }

        System.out.println("Oracle JDBC Driver Registered!");
    
        
        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.0.248:1521/drcistdg", "MNG_PM", "Likeit");
            
        } catch (SQLException e) {

            System.out.println("Bağlantı hatası!");
            e.printStackTrace();
            return connection;
        }

        if (connection != null) {
            System.out.println("Veritabanı işlemlerini yapabilirsiniz");
        } else {
            System.out.println("Bağlantı kurulamadı!");
        }
    
        return connection;
        }
        
        
        
        
        public Connection MysqlBaglanti(String DataBase){
        
            
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.0.2/"+DataBase+"?useUnicode=true&characterEncoding=UTF-8";
        String username="root";
        String password="";
        Connection con = null;
        
        
        try {
        Class.forName(driver);
        //System.out.println("JDBC surucu basari ile yüklendi.");
        } catch (Exception e) {
        System.out.println("JDBC surucu Yüklenemedi. "+e.getMessage());
        System.exit(0);
        }
        
        try {
 
        con = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
    
        System.out.print("Bağlantı Kurulamadı "+e.getMessage());

        }
        
        
            return con;
    
        }
        
}
