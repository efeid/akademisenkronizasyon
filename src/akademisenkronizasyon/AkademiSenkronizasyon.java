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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author uozturk
 */
public class AkademiSenkronizasyon {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            
     
                new islem();
              //  new Senkronizasyon2();
        
                
                
    }
    
}


 class islem{
    
 
    public islem(){
    
    Connection OracleConnection = null;
    Connection MysqlConnection = null;
    
    Statement statement = null;
    Baglantilar baglan= new Baglantilar();

    String selectTableSQL = "select * from MNGPERSONEL.MVW_NETSIS_PERSONEL"; 
    
    
    System.out.println(selectTableSQL);
    	try{
			OracleConnection = baglan.OracleBaglanti();
			statement = OracleConnection.createStatement();          
			ResultSet rs = statement.executeQuery(selectTableSQL);
                        
                        
                        MysqlConnection  = baglan.MysqlBaglanti("stajyer_takip");
                      
                        
                      
                        
                        
                // Oracle verileri
		while (rs.next()) {
    
                            
              //    String adSoyad = rs.getString("CD_SICIL_NO")+" "+rs.getString("CH_ADI")+" "+rs.getString("CH_SOYADI");			
              //    System.out.println("Personel : " + adSoyad);   
                  
                  
                  
                  
       /**MYSQL**/
        new Kontrol(rs,MysqlConnection,"stajyer_takip");
                                                                    
    }
                
    MysqlConnection.close();
    OracleConnection.close();
                        
    }catch(Exception e){
        
       System.out.println("1 Hata Oracle \n"+e.getMessage());
        
    }}
    
    
}


class Kontrol{

    public Kontrol(ResultSet rs,Connection MysqlConnection,String DBbilgi){
    
     try{
     String personel_kodu;     
     personel_kodu = rs.getString("PERSONEL_KODU");       
     String SQL = "select * from stajyer_takip.mdl_personel where personel_kodu='"+personel_kodu+"'";
     

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
               
                /*String cikis = "0000-00-00";
                
                if(rs.getString("ISTEN_SON_CIKIS_TARIH")!=null)
                cikis=rs.getString("ISTEN_SON_CIKIS_TARIH");
              */
                
                /*
                String Bolge=rs.getString("BOLGE");
                if(rs.getInt("RF_BOLGE_KOD")!=0000)
                Bolge +=" BÖLGE";
                */

              
               // Bilgiler Mysql için hazırlanıyor
                String[] Bilgiler = {
                   rs.getString("personel_kodu"),
                   rs.getString("obj_nufus"),
                   rs.getString("tc_kimlik_no"),
                   rs.getString("obj_kimlik"),
                   rs.getString("isim"),
                   rs.getString("soyisim"),
                   rs.getString("scl_calyer1"),
                   rs.getString("calisma_yeri"),
                   rs.getString("obj_dprtmn1"),
                   rs.getString("departman"),
                   rs.getString("obj_grv1"),
                   rs.getString("gorev"),
                   rs.getString("obj_bolge1"),
                   rs.getString("blgkod"),
                   rs.getString("bolge"),
                   rs.getString("obj_brm1"),
                   rs.getString("brmkod"),
                   rs.getString("birim"),
                   rs.getString("calisma_durumu"),
                   rs.getString("enson_ise_giris_tarihi"),
                   rs.getString("scl_cinsiyet"),
                   rs.getString("cinsiyet"),
                   rs.getString("ise_son_giris_tarih"),
                   rs.getString("isten_son_cikis_tarih"),
                   rs.getString("pers_kayit_giris_trh"),
                   rs.getString("pers_kayit_duzeltme_trh"),
                   rs.getString("kisi_kayit_giris_trh"),
                   rs.getString("kisi_kayit_duzeltme_trh"),
                   rs.getString("nufus_kayit_giris_trh"),
                   rs.getString("nufus_kayit_duzeltme_trh"),
                   rs.getString("bolge_kayit_giris_trh"),
                   rs.getString("bolge_kayit_duzeltme_trh"),
                   rs.getString("birim_kayit_giris_trh"),
                   rs.getString("birim_kayit_duzeltme_trh"),
                   rs.getString("isegircik_kayit_giris_trh"),
                   rs.getString("isegircik_kayit_duzeltme_trh"),
                   rs.getString("obj_iletisim"),
                   rs.getString("sirket_mail"),
                   rs.getString("last_upd_date"),
                   rs.getString("dt_dogum_tarihi"),
                   rs.getString("ch_dogum_yeri"),
                   rs.getString("rf_nc_ko_ilce"),
                   rs.getString("rf_nc_ko_il"),
                   rs.getString("lu_medeni_hal"),
                   rs.getString("ch_ayrilis_nedeni"),
                   rs.getString("lisans_universite"),
                   rs.getString("yukseklisans_universite"),
                   rs.getString("lisans_bolum"),
                   rs.getString("yukseklisans_bolum"),
                   rs.getString("kan_grubu"),
                   rs.getString("intcode"),
                   rs.getString("ogrenim_durumu"),
                   rs.getString("ol_universite_adi"),
                   rs.getString("ol_bolum"),
                   rs.getString("cep_telefonu")
                 };
                 
                //auth,username,idnumber,firstname,lastname,email,phone1,institution,department,address,city,url,stask,stask_name,user_status,per_exit_date,per_tc_no
                if(rowcount==0){
                System.out.println(personel_kodu+" "+DBbilgi+" tablosunda yok ekleniyor");                
                       new Insert(durum,Bilgiler);
                  
                }else{
                   
                   //if(rs.getString("LAST_UPD_DATE") == 
                    
                    DateFormat df = new SimpleDateFormat("yyyyMM11");
                    String data = df.format(new Date()) + "000000";
                        
                    if(Bilgiler[38].indexOf(data) != -1  ){      
                    //if (   city.indexOf("BÖLGE") == -1  &&  city.indexOf("BOLGE") == -1 && city.indexOf("MÜDÜRLÜK")== -1  &&  city.indexOf("INTER")==-1  ){
                         System.out.println(personel_kodu+" "+DBbilgi+"  tablosunda var güncelleniyor");                  
                         new Update(durum,Bilgiler);

                    }
                }                        
                
                
     }catch( Exception e){
     
      System.out.println("=>"+e.getMessage());
     
     
     }
          
    
    
    
    }

}


class Insert{

    public Insert(Statement st,String[] Bilgi){
     
        String personel_kodu =Bilgi[0];
        String obj_nufus =Bilgi[1];
        String tc_kimlik_no =Bilgi[2];
        String obj_kimlik =Bilgi[3];
        String isim =Bilgi[4];
        String soyisim =Bilgi[5];
        String scl_calyer1 =Bilgi[6];
        String calisma_yeri =Bilgi[7];
        String obj_dprtmn1 =Bilgi[8];
        String departman =Bilgi[9];
        String obj_grv1 =Bilgi[10];
        String gorev =Bilgi[11];
        String obj_bolge1 =Bilgi[12];
        String blgkod =Bilgi[13];
        String bolge =Bilgi[14];
        String obj_brm1 =Bilgi[15];
        String brmkod =Bilgi[16];
        String birim =Bilgi[17];
        String calisma_durumu =Bilgi[18];
        String enson_ise_giris_tarihi =Bilgi[19];
        String scl_cinsiyet =Bilgi[20];
        String cinsiyet =Bilgi[21];
        String ise_son_giris_tarih =Bilgi[22];
        String isten_son_cikis_tarih =Bilgi[23];
        String pers_kayit_giris_trh =Bilgi[24];
        String pers_kayit_duzeltme_trh =Bilgi[25];
        String kisi_kayit_giris_trh =Bilgi[26];
        String kisi_kayit_duzeltme_trh =Bilgi[27];
        String nufus_kayit_giris_trh =Bilgi[28];
        String nufus_kayit_duzeltme_trh =Bilgi[29];
        String bolge_kayit_giris_trh =Bilgi[30];
        String bolge_kayit_duzeltme_trh =Bilgi[31];
        String birim_kayit_giris_trh =Bilgi[32];
        String birim_kayit_duzeltme_trh =Bilgi[33];
        String isegircik_kayit_giris_trh =Bilgi[34];
        String isegircik_kayit_duzeltme_trh =Bilgi[35];
        String obj_iletisim =Bilgi[36];
        String sirket_mail =Bilgi[37];
        String last_upd_date =Bilgi[38];
        String dt_dogum_tarihi =Bilgi[39];
        String ch_dogum_yeri =Bilgi[40];
        String rf_nc_ko_ilce =Bilgi[41];
        String rf_nc_ko_il =Bilgi[42];
        String lu_medeni_hal =Bilgi[43];
        String ch_ayrilis_nedeni = Bilgi[44];
       //inp=inp.replaceAll("[']+","");
        if(!"".equals(ch_ayrilis_nedeni) ) ch_ayrilis_nedeni =ch_ayrilis_nedeni.replaceAll("[']+","");             
        
        String lisans_universite =Bilgi[45];
        String yukseklisans_universite =Bilgi[46];
        String lisans_bolum =Bilgi[47];
        String yukseklisans_bolum =Bilgi[48];
        String kan_grubu =Bilgi[49];
        String intcode =Bilgi[50];
        String ogrenim_durumu =Bilgi[51];
        String ol_universite_adi =Bilgi[52];
        String ol_bolum =Bilgi[53];
        String cep_telefonu =Bilgi[54];


    try{

      st.executeQuery("INSERT INTO mdl_personel (personel_kodu, obj_nufus, tc_kimlik_no, obj_kimlik, isim, soyisim, scl_calyer1, calisma_yeri, obj_dprtmn1, departman, obj_grv1, gorev, obj_bolge1, blgkod, bolge, obj_brm1, brmkod, birim, calisma_durumu, enson_ise_giris_tarihi, scl_cinsiyet, cinsiyet, ise_son_giris_tarih, isten_son_cikis_tarih, pers_kayit_giris_trh, pers_kayit_duzeltme_trh, kisi_kayit_giris_trh, kisi_kayit_duzeltme_trh, nufus_kayit_giris_trh, nufus_kayit_duzeltme_trh, bolge_kayit_giris_trh, bolge_kayit_duzeltme_trh, birim_kayit_giris_trh, birim_kayit_duzeltme_trh, isegircik_kayit_giris_trh, isegircik_kayit_duzeltme_trh, obj_iletisim, sirket_mail, last_upd_date, dt_dogum_tarihi, ch_dogum_yeri, rf_nc_ko_ilce, rf_nc_ko_il, lu_medeni_hal, ch_ayrilis_nedeni, lisans_universite, yukseklisans_universite, lisans_bolum, yukseklisans_bolum, kan_grubu, intcode, ogrenim_durumu, ol_universite_adi, ol_bolum, cep_telefonu) values ('"+personel_kodu +"' , '"+obj_nufus +"' , '"+tc_kimlik_no +"' , '"+obj_kimlik +"' , '"+isim +"' , '"+soyisim +"' , '"+scl_calyer1 +"' , '"+calisma_yeri +"' , '"+obj_dprtmn1 +"' , '"+departman +"' , '"+obj_grv1 +"' , '"+gorev +"' , '"+obj_bolge1 +"' , '"+blgkod +"' , '"+bolge +"' , '"+obj_brm1 +"' , '"+brmkod +"' , '"+birim +"' , '"+calisma_durumu +"' , '"+enson_ise_giris_tarihi +"' , '"+scl_cinsiyet +"' , '"+cinsiyet +"' , '"+ise_son_giris_tarih +"' , '"+isten_son_cikis_tarih +"' , '"+pers_kayit_giris_trh +"' , '"+pers_kayit_duzeltme_trh +"' , '"+kisi_kayit_giris_trh +"' , '"+kisi_kayit_duzeltme_trh +"' , '"+nufus_kayit_giris_trh +"' , '"+nufus_kayit_duzeltme_trh +"' , '"+bolge_kayit_giris_trh +"' , '"+bolge_kayit_duzeltme_trh +"' , '"+birim_kayit_giris_trh +"' , '"+birim_kayit_duzeltme_trh +"' , '"+isegircik_kayit_giris_trh +"' , '"+isegircik_kayit_duzeltme_trh +"' , '"+obj_iletisim +"' , '"+sirket_mail +"' , '"+last_upd_date +"' , '"+dt_dogum_tarihi +"' , '"+ch_dogum_yeri +"' , '"+rf_nc_ko_ilce +"' , '"+rf_nc_ko_il +"' , '"+lu_medeni_hal +"' , '"+ch_ayrilis_nedeni +"' , '"+lisans_universite +"' , '"+yukseklisans_universite +"' , '"+lisans_bolum +"' , '"+yukseklisans_bolum +"' , '"+kan_grubu +"' , '"+intcode +"' , '"+ogrenim_durumu +"' , '"+ol_universite_adi +"' , '"+ol_bolum +"' , '"+cep_telefonu +"')");      
       
    }catch(Exception e){
            
           //   System.out.println("INSERT INTO mdl_personel (PERSONEL_KODU, OBJ_NUFUS, TC_KIMLIK_NO, OBJ_KIMLIK, ISIM, SOYISIM, SCL_CALYER1, CALISMA_YERI, OBJ_DPRTMN1, DEPARTMAN, OBJ_GRV1, GOREV, OBJ_BOLGE1, BLGKOD, BOLGE, OBJ_BRM1, BRMKOD, BIRIM, CALISMA_DURUMU, ENSON_ISE_GIRIS_TARIHI, SCL_CINSIYET, CINSIYET, ISE_SON_GIRIS_TARIH, ISTEN_SON_CIKIS_TARIH, PERS_KAYIT_GIRIS_TRH, PERS_KAYIT_DUZELTME_TRH, KISI_KAYIT_GIRIS_TRH, KISI_KAYIT_DUZELTME_TRH, NUFUS_KAYIT_GIRIS_TRH, NUFUS_KAYIT_DUZELTME_TRH, BOLGE_KAYIT_GIRIS_TRH, BOLGE_KAYIT_DUZELTME_TRH, BIRIM_KAYIT_GIRIS_TRH, BIRIM_KAYIT_DUZELTME_TRH, ISEGIRCIK_KAYIT_GIRIS_TRH, ISEGIRCIK_KAYIT_DUZELTME_TRH, OBJ_ILETISIM, SIRKET_MAIL, LAST_UPD_DATE, DT_DOGUM_TARIHI, CH_DOGUM_YERI, RF_NC_KO_ILCE, RF_NC_KO_IL, LU_MEDENI_HAL, CH_AYRILIS_NEDENI, LISANS_UNIVERSITE, YUKSEKLISANS_UNIVERSITE, LISANS_BOLUM, YUKSEKLISANS_BOLUM, KAN_GRUBU, INTCODE, OGRENIM_DURUMU, OL_UNIVERSITE_ADI, OL_BOLUM, CEP_TELEFONU ) values ( '"+PERSONEL_KODU+"' ,  '"+OBJ_NUFUS+"' ,  '"+TC_KIMLIK_NO+"' ,  '"+OBJ_KIMLIK+"' ,  '"+ISIM+"' ,  '"+SOYISIM+"' ,  '"+SCL_CALYER1+"' ,  '"+CALISMA_YERI+"' ,  '"+OBJ_DPRTMN1+"' ,  '"+DEPARTMAN+"' ,  '"+OBJ_GRV1+"' ,  '"+GOREV+"' ,  '"+OBJ_BOLGE1+"' ,  '"+BLGKOD+"' ,  '"+BOLGE+"' ,  '"+OBJ_BRM1+"' ,  '"+BRMKOD+"' ,  '"+BIRIM+"' ,  '"+CALISMA_DURUMU+"' ,  '"+ENSON_ISE_GIRIS_TARIHI+"' ,  '"+SCL_CINSIYET+"' ,  '"+CINSIYET+"' ,  '"+ISE_SON_GIRIS_TARIH+"' , '"+ISTEN_SON_CIKIS_TARIH+"' ,  '"+PERS_KAYIT_GIRIS_TRH+"' ,  '"+PERS_KAYIT_DUZELTME_TRH+"' ,  '"+KISI_KAYIT_GIRIS_TRH+"' ,  '"+KISI_KAYIT_DUZELTME_TRH+"' ,  '"+NUFUS_KAYIT_GIRIS_TRH+"' ,  '"+NUFUS_KAYIT_DUZELTME_TRH+"' ,  '"+BOLGE_KAYIT_GIRIS_TRH+"' ,  '"+BOLGE_KAYIT_DUZELTME_TRH+"' ,  '"+BIRIM_KAYIT_GIRIS_TRH+"' ,  '"+BIRIM_KAYIT_DUZELTME_TRH+"' ,  '"+ISEGIRCIK_KAYIT_GIRIS_TRH+"' ,  '"+ISEGIRCIK_KAYIT_DUZELTME_TRH+"' , '"+OBJ_ILETISIM+"' ,  '"+SIRKET_MAIL+"' ,  '"+LAST_UPD_DATE+"' ,  '"+DT_DOGUM_TARIHI+"' ,  '"+CH_DOGUM_YERI+"' ,  '"+RF_NC_KO_ILCE+"' ,  '"+RF_NC_KO_IL+"' ,  '"+LU_MEDENI_HAL+"' ,  '"+CH_AYRILIS_NEDENI+"' ,  '"+LISANS_UNIVERSITE+"' ,  '"+YUKSEKLISANS_UNIVERSITE+"' ,  '"+LISANS_BOLUM+ "',  '"+YUKSEKLISANS_BOLUM+"' ,  '"+KAN_GRUBU+"' ,  '"+INTCODE+"' ,  '"+OGRENIM_DURUMU+"' ,  '"+OL_UNIVERSITE_ADI+"' , '"+OL_BOLUM+"' , '"+CEP_TELEFONU+"')");
            System.out.println("Eklenmedi \n"+e.getMessage());
            
    }

    
    }


}


class Update{

    public  Update(Statement st,String[] Bilgi){
     
        String personel_kodu =Bilgi[0];
        String obj_nufus =Bilgi[1];
        String tc_kimlik_no =Bilgi[2];
        String obj_kimlik =Bilgi[3];
        String isim =Bilgi[4];
        String soyisim =Bilgi[5];
        String scl_calyer1 =Bilgi[6];
        String calisma_yeri =Bilgi[7];
        String obj_dprtmn1 =Bilgi[8];
        String departman =Bilgi[9];
        String obj_grv1 =Bilgi[10];
        String gorev =Bilgi[11];
        String obj_bolge1 =Bilgi[12];
        String blgkod =Bilgi[13];
        String bolge =Bilgi[14];
        String obj_brm1 =Bilgi[15];
        String brmkod =Bilgi[16];
        String birim =Bilgi[17];
        String calisma_durumu =Bilgi[18];
        String enson_ise_giris_tarihi =Bilgi[19];
        String scl_cinsiyet =Bilgi[20];
        String cinsiyet =Bilgi[21];
        String ise_son_giris_tarih =Bilgi[22];
        String isten_son_cikis_tarih =Bilgi[23];
        String pers_kayit_giris_trh =Bilgi[24];
        String pers_kayit_duzeltme_trh =Bilgi[25];
        String kisi_kayit_giris_trh =Bilgi[26];
        String kisi_kayit_duzeltme_trh =Bilgi[27];
        String nufus_kayit_giris_trh =Bilgi[28];
        String nufus_kayit_duzeltme_trh =Bilgi[29];
        String bolge_kayit_giris_trh =Bilgi[30];
        String bolge_kayit_duzeltme_trh =Bilgi[31];
        String birim_kayit_giris_trh =Bilgi[32];
        String birim_kayit_duzeltme_trh =Bilgi[33];
        String isegircik_kayit_giris_trh =Bilgi[34];
        String isegircik_kayit_duzeltme_trh =Bilgi[35];
        String obj_iletisim =Bilgi[36];
        String sirket_mail =Bilgi[37];
        String last_upd_date =Bilgi[38];
        String dt_dogum_tarihi =Bilgi[39];
        String ch_dogum_yeri =Bilgi[40];
        String rf_nc_ko_ilce =Bilgi[41];
        String rf_nc_ko_il =Bilgi[42];
        String lu_medeni_hal =Bilgi[43];
        String ch_ayrilis_nedeni =Bilgi[44];
      
        
         if(!"".equals(ch_ayrilis_nedeni) ) ch_ayrilis_nedeni =ch_ayrilis_nedeni.replaceAll("[']+","");             
        
        String lisans_universite =Bilgi[45];
        String yukseklisans_universite =Bilgi[46];
        String lisans_bolum =Bilgi[47];
        String yukseklisans_bolum =Bilgi[48];
        String kan_grubu =Bilgi[49];
        String intcode =Bilgi[50];
        String ogrenim_durumu =Bilgi[51];
        String ol_universite_adi =Bilgi[52];
        String ol_bolum =Bilgi[53];
        String cep_telefonu =Bilgi[54];
      
      

      
 // String updateQueryString= new String( "UPDATE mdl_personel SET  obj_nufus  ='"+obj_nufus +"', tc_kimlik_no  ='"+tc_kimlik_no +"', obj_kimlik  ='"+obj_kimlik +"', isim  ='"+isim +"', soyisim  ='"+soyisim +"', scl_calyer1  ='"+scl_calyer1 +"', calisma_yeri  ='"+calisma_yeri +"', obj_dprtmn1  ='"+obj_dprtmn1 +"', departman  ='"+departman +"', obj_grv1  ='"+obj_grv1 +"', gorev  ='"+gorev +"', obj_bolge1  ='"+obj_bolge1 +"', blgkod  ='"+blgkod +"', bolge  ='"+bolge +"', obj_brm1  ='"+obj_brm1 +"', brmkod  ='"+brmkod +"', birim  ='"+birim +"', calisma_durumu  ='"+calisma_durumu +"', enson_ise_giris_tarihi  ='"+enson_ise_giris_tarihi +"', scl_cinsiyet  ='"+scl_cinsiyet +"', cinsiyet  ='"+cinsiyet +"', ise_son_giris_tarih  ='"+ise_son_giris_tarih +"', isten_son_cikis_tarih  ='"+isten_son_cikis_tarih +"', pers_kayit_giris_trh  ='"+pers_kayit_giris_trh +"', pers_kayit_duzeltme_trh  ='"+pers_kayit_duzeltme_trh +"', kisi_kayit_giris_trh  ='"+kisi_kayit_giris_trh +"', kisi_kayit_duzeltme_trh  ='"+kisi_kayit_duzeltme_trh +"', nufus_kayit_giris_trh  ='"+nufus_kayit_giris_trh +"', nufus_kayit_duzeltme_trh  ='"+nufus_kayit_duzeltme_trh +"', bolge_kayit_giris_trh  ='"+bolge_kayit_giris_trh +"', bolge_kayit_duzeltme_trh  ='"+bolge_kayit_duzeltme_trh +"', birim_kayit_giris_trh  ='"+birim_kayit_giris_trh +"', birim_kayit_duzeltme_trh  ='"+birim_kayit_duzeltme_trh +"', isegircik_kayit_giris_trh  ='"+isegircik_kayit_giris_trh +"', isegircik_kayit_duzeltme_trh  ='"+isegircik_kayit_duzeltme_trh +"', obj_iletisim  ='"+obj_iletisim +"', sirket_mail  ='"+sirket_mail +"', last_upd_date  ='"+last_upd_date +"', dt_dogum_tarihi  ='"+dt_dogum_tarihi +"', ch_dogum_yeri  ='"+ch_dogum_yeri +"', rf_nc_ko_ilce  ='"+rf_nc_ko_ilce +"', rf_nc_ko_il  ='"+rf_nc_ko_il +"', lu_medeni_hal  ='"+lu_medeni_hal +"', ch_ayrilis_nedeni  ='"+ch_ayrilis_nedeni +"', lisans_universite  ='"+lisans_universite +"', yukseklisans_universite  ='"+yukseklisans_universite +"', lisans_bolum  ='"+lisans_bolum +"', yukseklisans_bolum  ='"+yukseklisans_bolum +"', kan_grubu  ='"+kan_grubu +"', intcode  ='"+intcode +"', ogrenim_durumu  ='"+ogrenim_durumu +"', ol_universite_adi  ='"+ol_universite_adi +"', ol_bolum  ='"+ol_bolum +"', cep_telefonu  ='"+cep_telefonu +"' where personel_kodu='"+personel_kodu+"'");     
  
  System.out.println("UPDATE mdl_personel SET  obj_nufus  ='"+obj_nufus +"', tc_kimlik_no  ='"+tc_kimlik_no +"', obj_kimlik  ='"+obj_kimlik +"', isim  ='"+isim +"', soyisim  ='"+soyisim +"', scl_calyer1  ='"+scl_calyer1 +"', calisma_yeri  ='"+calisma_yeri +"', obj_dprtmn1  ='"+obj_dprtmn1 +"', departman  ='"+departman +"', obj_grv1  ='"+obj_grv1 +"', gorev  ='"+gorev +"', obj_bolge1  ='"+obj_bolge1 +"', blgkod  ='"+blgkod +"', bolge  ='"+bolge +"', obj_brm1  ='"+obj_brm1 +"', brmkod  ='"+brmkod +"', birim  ='"+birim +"', calisma_durumu  ='"+calisma_durumu +"', enson_ise_giris_tarihi  ='"+enson_ise_giris_tarihi +"', scl_cinsiyet  ='"+scl_cinsiyet +"', cinsiyet  ='"+cinsiyet +"', ise_son_giris_tarih  ='"+ise_son_giris_tarih +"', isten_son_cikis_tarih  ='"+isten_son_cikis_tarih +"', pers_kayit_giris_trh  ='"+pers_kayit_giris_trh +"', pers_kayit_duzeltme_trh  ='"+pers_kayit_duzeltme_trh +"', kisi_kayit_giris_trh  ='"+kisi_kayit_giris_trh +"', kisi_kayit_duzeltme_trh  ='"+kisi_kayit_duzeltme_trh +"', nufus_kayit_giris_trh  ='"+nufus_kayit_giris_trh +"', nufus_kayit_duzeltme_trh  ='"+nufus_kayit_duzeltme_trh +"', bolge_kayit_giris_trh  ='"+bolge_kayit_giris_trh +"', bolge_kayit_duzeltme_trh  ='"+bolge_kayit_duzeltme_trh +"', birim_kayit_giris_trh  ='"+birim_kayit_giris_trh +"', birim_kayit_duzeltme_trh  ='"+birim_kayit_duzeltme_trh +"', isegircik_kayit_giris_trh  ='"+isegircik_kayit_giris_trh +"', isegircik_kayit_duzeltme_trh  ='"+isegircik_kayit_duzeltme_trh +"', obj_iletisim  ='"+obj_iletisim +"', sirket_mail  ='"+sirket_mail +"', last_upd_date  ='"+last_upd_date +"', dt_dogum_tarihi  ='"+dt_dogum_tarihi +"', ch_dogum_yeri  ='"+ch_dogum_yeri +"', rf_nc_ko_ilce  ='"+rf_nc_ko_ilce +"', rf_nc_ko_il  ='"+rf_nc_ko_il +"', lu_medeni_hal  ='"+lu_medeni_hal +"', ch_ayrilis_nedeni  ='"+ch_ayrilis_nedeni +"', lisans_universite  ='"+lisans_universite +"', yukseklisans_universite  ='"+yukseklisans_universite +"', lisans_bolum  ='"+lisans_bolum +"', yukseklisans_bolum  ='"+yukseklisans_bolum +"', kan_grubu  ='"+kan_grubu +"', intcode  ='"+intcode +"', ogrenim_durumu  ='"+ogrenim_durumu +"', ol_universite_adi  ='"+ol_universite_adi +"', ol_bolum  ='"+ol_bolum +"', cep_telefonu  ='"+cep_telefonu +"' where personel_kodu='"+personel_kodu+"'");
   try{
       
   
    st.executeUpdate("UPDATE mdl_personel SET  obj_nufus  ='"+obj_nufus +"', tc_kimlik_no  ='"+tc_kimlik_no +"', obj_kimlik  ='"+obj_kimlik +"', isim  ='"+isim +"', soyisim  ='"+soyisim +"', scl_calyer1  ='"+scl_calyer1 +"', calisma_yeri  ='"+calisma_yeri +"', obj_dprtmn1  ='"+obj_dprtmn1 +"', departman  ='"+departman +"', obj_grv1  ='"+obj_grv1 +"', gorev  ='"+gorev +"', obj_bolge1  ='"+obj_bolge1 +"', blgkod  ='"+blgkod +"', bolge  ='"+bolge +"', obj_brm1  ='"+obj_brm1 +"', brmkod  ='"+brmkod +"', birim  ='"+birim +"', calisma_durumu  ='"+calisma_durumu +"', enson_ise_giris_tarihi  ='"+enson_ise_giris_tarihi +"', scl_cinsiyet  ='"+scl_cinsiyet +"', cinsiyet  ='"+cinsiyet +"', ise_son_giris_tarih  ='"+ise_son_giris_tarih +"', isten_son_cikis_tarih  ='"+isten_son_cikis_tarih +"', pers_kayit_giris_trh  ='"+pers_kayit_giris_trh +"', pers_kayit_duzeltme_trh  ='"+pers_kayit_duzeltme_trh +"', kisi_kayit_giris_trh  ='"+kisi_kayit_giris_trh +"', kisi_kayit_duzeltme_trh  ='"+kisi_kayit_duzeltme_trh +"', nufus_kayit_giris_trh  ='"+nufus_kayit_giris_trh +"', nufus_kayit_duzeltme_trh  ='"+nufus_kayit_duzeltme_trh +"', bolge_kayit_giris_trh  ='"+bolge_kayit_giris_trh +"', bolge_kayit_duzeltme_trh  ='"+bolge_kayit_duzeltme_trh +"', birim_kayit_giris_trh  ='"+birim_kayit_giris_trh +"', birim_kayit_duzeltme_trh  ='"+birim_kayit_duzeltme_trh +"', isegircik_kayit_giris_trh  ='"+isegircik_kayit_giris_trh +"', isegircik_kayit_duzeltme_trh  ='"+isegircik_kayit_duzeltme_trh +"', obj_iletisim  ='"+obj_iletisim +"', sirket_mail  ='"+sirket_mail +"', last_upd_date  ='"+last_upd_date +"', dt_dogum_tarihi  ='"+dt_dogum_tarihi +"', ch_dogum_yeri  ='"+ch_dogum_yeri +"', rf_nc_ko_ilce  ='"+rf_nc_ko_ilce +"', rf_nc_ko_il  ='"+rf_nc_ko_il +"', lu_medeni_hal  ='"+lu_medeni_hal +"', ch_ayrilis_nedeni  ='"+ch_ayrilis_nedeni +"', lisans_universite  ='"+lisans_universite +"', yukseklisans_universite  ='"+yukseklisans_universite +"', lisans_bolum  ='"+lisans_bolum +"', yukseklisans_bolum  ='"+yukseklisans_bolum +"', kan_grubu  ='"+kan_grubu +"', intcode  ='"+intcode +"', ogrenim_durumu  ='"+ogrenim_durumu +"', ol_universite_adi  ='"+ol_universite_adi +"', ol_bolum  ='"+ol_bolum +"', cep_telefonu  ='"+cep_telefonu +"' where personel_kodu='"+personel_kodu+"'"); 
  

   
   }catch(Exception e){
            
       System.out.println( personel_kodu+ "<= Güncellenemedi => " + e.getMessage() );

            }
  
  
    }


}



