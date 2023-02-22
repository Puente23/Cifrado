package com.josuepuente.cifrado;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;
public class filechooser {
    public static String encryptSMS(String secretKeyString,
                                    String msgContentString, String cifrado) {
        try {
            byte[] returnArray;
            // generate AES secret key from user
            Key key = generateKey(secretKeyString, cifrado);
            // specify the cipher algorithm using AES
            Cipher c = Cipher.getInstance(cifrado);
            // specify the encryption mode
            c.init(Cipher.ENCRYPT_MODE, key);
            // encrypt
            returnArray = c.doFinal(msgContentString.getBytes());
            return Base64.encodeToString(returnArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            byte[] returnArray = null;
            return Base64.encodeToString(returnArray, Base64.DEFAULT);
        }
    }
    public static byte[] decryptSMS(String secretKeyString, byte[] encryptedMsg, String cifrado)
            throws Exception {
            // generate AES secret key from the user input secret key
            Key key = generateKey(secretKeyString,cifrado);
            // get the cipher algorithm for AEs
            byte[] decValue= null;
            try {
                Cipher c = Cipher.getInstance(cifrado);
                // specify the decryption mode
                c.init(Cipher.DECRYPT_MODE, key);
                // decrypt the message
                decValue = c.doFinal(encryptedMsg);
            }catch (Exception e){

            }
            return decValue;
    }
    private static Key generateKey(String secretKeyString, String cifrado) throws Exception {
        // generate AES secret key from a String
        Key key = new SecretKeySpec(secretKeyString.getBytes(), cifrado);
        return key;
    }
}