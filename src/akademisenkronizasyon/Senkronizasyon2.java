/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akademisenkronizasyon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import akademisenkronizasyon.Baglantilar;

/**
 *
 * @author uozturk
 */


 public class Senkronizasyon2{
    
 
    public Senkronizasyon2(){
    
    Connection OracleConnection = null;
    Connection MysqlConnection = null;
    Connection MysqlConnection2 = null;
    
    Statement statement = null;
    Baglantilar baglan= new Baglantilar();
    
    
    String selectTableSQL = "select * from MNGPERSONEL.MVW_NETSIS_PERSONEL ";
    
    	try{
			OracleConnection = baglan.OracleBaglanti();
			statement = OracleConnection.createStatement();          
			ResultSet rs = statement.executeQuery(selectTableSQL);
                        
                        
                        MysqlConnection  = baglan.MysqlBaglanti("stajyer_takip");
                       // MysqlConnection2 = baglan.MysqlBaglanti("akademi1");
                        
               
                        
                        
                        
                // Oracle verileri
		while (rs.next()) {
    
                            
              //    String adSoyad = rs.getString("CD_SICIL_NO")+" "+rs.getString("CH_ADI")+" "+rs.getString("CH_SOYADI");			
              //    System.out.println("Personel : " + adSoyad);   
                  
                  
                  
                  
       /**MYSQL**/
        new Kontrol2(rs,MysqlConnection,"stajyer_takip");
       // new Kontrol2(rs,MysqlConnection2,"akademi1");
                                                                  
    }
                
    MysqlConnection.close();
    //MysqlConnection2.close();
    OracleConnection.close();
                        
    }catch(Exception e){
        
       System.out.println("Hata Oracle \n"+e.getMessage());
        
    }}
    
    
}


class Kontrol2{

    public Kontrol2(ResultSet rs,Connection MysqlConnection,String DBbilgi){
    
     try{
     String sicilNo;     
     sicilNo = rs.getString("CD_SICIL_NO");       
     String SQL = "select * from mdl_user where username='"+sicilNo+"'";
     

     Statement durum = (Statement) MysqlConnection.createStatement();     
     ResultSet mysqlRs = (ResultSet) durum.executeQuery(SQL);
     
          
          
                int rowcount = 0;
                if (mysqlRs.last()) {
                rowcount = mysqlRs.getRow();
                mysqlRs.beforeFirst(); 
                }
         
              //  System.out.println("Personel : " + rowcount);  
                
                String mail=".";
                if(rs.getString("SIRKET_MAIL")!=null)
                mail=rs.getString("SIRKET_MAIL");
                else
                mail=".";
                
                   
               // ZoneId zoneId = ZoneId.of( "Asia/Baghdad" ) ;
               // String cikis = LocalDate.now( zoneId ).toString() ;
                String cikis = "0000-00-00";
              
                if(rs.getString("ISTEN_SON_CIKIS_TARIH")!=null)
                cikis=rs.getString("ISTEN_SON_CIKIS_TARIH");
              
                
                String Bolge=rs.getString("BOLGE");
                if(rs.getInt("RF_BOLGE_KOD")!=0000)
                Bolge +=" BÖLGE";
                
   
                
                if(rowcount==0){
                System.out.println(sicilNo+" "+DBbilgi+" tablosunda yok");                
              
                  
                }else{
                    
                 System.out.println(sicilNo+" "+DBbilgi+"  tablosunda var çıkış tarihi güncelleniyor");                  
                
                    // Her durumda son çıkış tarihlerini güncelle
                  // Bilgiler Mysql için hazırlanıyor
                 String[] Bilgiler2 = {rs.getString("CD_SICIL_NO"),cikis,rs.getString("TC_KIMLIK_NO")};
                 new  UpdateCikisTarih(durum,Bilgiler2);
                                        
                }
                
                
                
                
             
              
                
     }catch( Exception e){
     
      System.out.println("Hata Mysql \n"+e.getMessage());
     
     
     }
          
    
    
    
    }

}





class UpdateCikisTarih{

    public UpdateCikisTarih(Statement st,String[] Bilgi){

      String username = Bilgi[0];
      String per_exit_date = Bilgi[1];
      String per_tc_no = Bilgi[2];
 
 
   try{
       
    st.executeUpdate("UPDATE mdl_user SET per_exit_date='"+per_exit_date+"',per_tc_no='"+per_tc_no+"' "
      + "where username='"+username+"'");
    
    
   
    }catch(Exception e){
            
      //  e.getMessage();
            
            }

    
    }


}