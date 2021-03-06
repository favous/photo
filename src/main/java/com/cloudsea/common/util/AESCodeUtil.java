package com.cloudsea.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author zhangXiaoRong
 * 
 */
public class AESCodeUtil {

	public static void main( String[] args) {
		System.out.println(encode("requestPath","123"));
		System.out.println(encode("saveUserInfo","123"));
		System.out.println(encode("requestType","123"));
		System.out.println(encode("stringReturnString","123"));
		System.out.println(decode("C34EDF73536B16E34153840D45B8BFBD","123"));
		System.out.println(decode("228330ED5C4802265E05C1E85E7CAC34","123"));
		System.out.println(decode("B6D4F15CDDF1065177EE82DD77630CCE","123"));
		System.out.println(decode("0C21D256B2836B5F07AAC96548FC53BDDFCD7EDB6B6A89DB0DE3FEAE30537C18","123"));
////		String str=System.getProperty("user.home");
////		System.out.println(str);
//		
//		String EncodedStr = encode("abc","123", "gbk");
//		System.out.println(EncodedStr);
//		
//		String result = decode(EncodedStr,"123", "gbk");
//		System.out.println(result);		
//		
////		InputStream in = StreamUtil.getInputStream("F:\\picture\\123123.jpg");
////		InputStream inen = encryptInputStream(in, "123");
////		InputStream deen = decryptInputStream(inen, "123");
////
////		File ff = new File("F:\\picture\\123321.jpg");
////		try {
////			ff.createNewFile();
////			StreamUtil.bufferedReadAndWriteByte(deen, StreamUtil.getOutputStream(ff));
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
	}

	/**
	 * 转成字节码，AES加密，再转成字符串
	 * @param charset 是Charset描述 , 包括"utf-8", "gbk", "ISO-8859-1"等
	 */
	public static String encode( String strContent, String password, String charset) {  
        
		if (strContent == null)
			return null;

		if ("".equals(strContent.trim()))
			return strContent;
		
		if(charset==null || "".equals(charset.trim()))
			charset = "utf-8";
        try {
        	byte[] content = strContent.getBytes(charset);
        	byte[] bytes = encrypt(content, password);
			return parseByte2HexStr(bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	
	/**
	 * 用utf-8转成字节码，AES加密，再转成字符串
	 */
	public static String encode( String strContent, String password) {  
		
		return encode( strContent, password, "utf-8");
	}
	
	
	/**
	 * 转成字节码，AES解密，再转成字符串
	 * @param charset 是Charset描述 , 包括"utf-8", "gbk", "ISO-8859-1"等
	 */
	public static String decode( String EncodedStr, String password, String charset) {  

		if (EncodedStr == null)
			return null;

		if ("".equals(EncodedStr.trim()))
			return EncodedStr;
		
		if(charset==null || "".equals(charset.trim()))
			charset = "utf-8";
		try {
			byte[] content = parseHexStr2Byte(EncodedStr);
			byte[] bytes = decrypt(content, password);
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 转成字节码，AES解密，再用utf-8转成字符串
	 */
	public static String decode( String EncodedStr, String password ) {  
		
		return decode( EncodedStr, password, "utf-8");
	}

		
	/**
	 *  二进制的加密
	 */
	public static byte[] encrypt ( byte[] byteContent, String password ) {  
		
		if ( byteContent == null || byteContent.length == 0 || password == null || "".equals(password.trim()) )
			return null;
		
		try {             
            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
            kgen.init(128, new SecureRandom(password.getBytes()));  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(byteContent);  
            return result; // 加密  
    } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
    } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
    } catch (InvalidKeyException e) {  
            e.printStackTrace();  
    } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
    } catch (BadPaddingException e) {  
            e.printStackTrace();  
    }  
    return null; 
	}

	/**
	 *  二进制的解密
	 */
	public static byte[] decrypt( byte[] content, String password) {  

		if ( content == null || content.length == 0 || password == null || "".equals(password.trim()) )
			return null;
		
		try {  
            KeyGenerator kgen = KeyGenerator.getInstance("AES");  
            kgen.init(128, new SecureRandom(password.getBytes()));  
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(content);  
            return result; // 加密  
	   } catch (NoSuchAlgorithmException e) {  
	           e.printStackTrace();  
	   } catch (NoSuchPaddingException e) {  
	           e.printStackTrace();  
	   } catch (InvalidKeyException e) {  
	           e.printStackTrace();  
	   } catch (IllegalBlockSizeException e) {  
	           e.printStackTrace();  
	   } catch (BadPaddingException e) {  
	           e.printStackTrace();  
	   }  
	   return null; 
	}

	
	/**
	 * 加密方法，把字节码转换成字符串
	 */
	public static String parseByte2HexStr(byte buf[]) {  
		
		if(buf == null || buf.length == 0)
			return null;
		
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
	}
	
	/**
	 * 加密方法，把字符串转换成字节码
	 */
	public static byte[] parseHexStr2Byte( String hexStr) {  

		if(hexStr == null || "".equals(hexStr.trim()))
			return null;
		
        if (hexStr.length() < 1)  
                return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
	}
	
	
	public static InputStream encryptInputStream(InputStream in, String key){
		
		byte[] bytes = StreamUtil.bufferedReadToByteArray(in);
		byte[] newBytes = encrypt(bytes, key);
		ByteArrayInputStream bin = new ByteArrayInputStream(newBytes);
		
		return new BufferedInputStream(bin);
	}

	
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	public static InputStream decryptInputStream(InputStream in, String key){
		
		byte[] bytes = StreamUtil.bufferedReadToByteArray(in);
		byte[] newBytes = decrypt(bytes, key);
		ByteArrayInputStream bin = new ByteArrayInputStream(newBytes);
		
		return new BufferedInputStream(bin);
	}


}
