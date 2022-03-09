package snis.rbm.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptSHA256 {
  
    
    //SHA-256    
    public static String getSHA256(String str) {
        String rtnSHA = "";
        
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
            sh.update(str.getBytes()); 
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer(); 
            
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            rtnSHA = sb.toString();
            
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace(); 
            rtnSHA = null; 
        }
        return rtnSHA;
    }
    
    public static String getEncryptSHA256(String a_origin){
        String encryptedSHA256 = "";
        MessageDigest md = null;
        
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(a_origin.getBytes(), 0, a_origin.length());
            encryptedSHA256 = new BigInteger(1, md.digest()).toString(16); 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return encryptedSHA256;
    }
    
}