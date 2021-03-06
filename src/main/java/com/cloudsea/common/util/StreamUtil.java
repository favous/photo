package com.cloudsea.common.util;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author ZhangXiaoRong
 */
public class StreamUtil {
	
	
	public static InputStream getInputStream(String path){

		if(path == null || "".equals(path.trim()) )
			return null;
		
		try {
			return new FileInputStream(path);
		} catch (FileNotFoundException e) {
			return null;
		}
		
	}
	
	
	public static InputStream getInputStream(File file){

		if(file == null )
			return null;
		
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return null;
		}
		
	}

	
	public static OutputStream getOutputStream(String path){
		
		if(path == null || "".equals(path.trim()) )
			return null;
		
		try {
			return new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	
	public static OutputStream getOutputStream(File file){
		
		if(file == null )
			return null;
		
		try {
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	
	/**
	 * 以字节码方式，从一个流中读出来，再写到另一个流件中
	 * @param in 
	 * @param out 
	 * @param buff 缓冲大小
	 * @return 成功返回1，失败返回2
	 */
	public static int bufferedReadAndWriteByte( InputStream in, OutputStream out, int buff){

		if( in == null || out == null || buff < 1 )
			return 2;
		
		try {			
			BufferedInputStream bin = new BufferedInputStream(in);
			PrintStream ps = new PrintStream(out, true);

			byte[] b = new byte[buff]; 
			int len = -1;
 
			while((len = bin.read(b))>=0){ 
				ps.write(b , 0, len);
			}
			bin.close();
			ps.close();
			return 1;
			
		} catch (IOException e) {
			return 2;
		}
	}
	
	
	/**
	 * 以字节码方式，从一个流中读出来，再写到另一个流件中，默认设定1024字节的缓冲
	 * @param in 
	 * @param out 
	 * @return 成功返回1，失败返回2
	 */
	public static int bufferedReadAndWriteByte( InputStream in, OutputStream out){
		
		return bufferedReadAndWriteByte( in, out, 1024);
	}
	
	
	/**
	 * 以字符串方式，从一个流中读出来，再写到另一个流件中
	 * @param in 
	 * @param out 
	 * @param charset 字符编码方法，如utf-8
	 * @return 成功返回1，失败返回2
	 */
	public static int bufferedReadAndWriteString( InputStream in, OutputStream out, String charset){
		
		if( in == null || out == null)
			return 2;
		
		InputStreamReader inr = null;
		
		try {			
			if( charset ==null || "".equals(charset.trim()))
				inr = new InputStreamReader(in);
			else
				inr = new InputStreamReader(in, charset);		
			
			BufferedReader reader = new BufferedReader(inr);
			PrintWriter writer = new PrintWriter(out, true);
			
			String str = null;
			while( (str = reader.readLine()) != null )
				writer.println(str);

			reader.close();
			writer.close();
			return 1;
			
		} catch (IOException e) {
			return 2;
		}
	}
	
	
	/**
	 * 以字符串方式，从一个流中读出来，再写到另一个流件中
	 * @param in 
	 * @param out 
	 * @return 成功返回1，失败返回2
	 */
	public static int bufferedReadAndWriteString( InputStream in, OutputStream out){
		
		return bufferedReadAndWriteString( in, out, null);
	}
	
	
	/**
	 * 读流，返回byte[]
	 * @param in 
	 * @param buff 字节的缓冲大小
	 * @return byte[]
	 */
	public static byte[] bufferedReadToByteArray( InputStream in, int buff){
		
		if(in == null || buff < 1 )
			return null;
		
		List<byte[]> byteList = new ArrayList<byte[]>();
		
		try {			
			BufferedInputStream bin = new BufferedInputStream(in);
			byte[] b = new byte[buff]; 
			int len = -1;
			while((len = bin.read(b))>=0){
				byteList.add( Arrays.copyOf(b, len));
			}
			bin.close();
			bin.close();
			return conbineByteArray(byteList);
			
		} catch (IOException e) {
			return null;
		}	
	}
	
	
	/**
	 * 读流，返回byte[]，默认设定1024字节的缓冲
	 * @param in 
	 * @return byte[]
	 */
	public static byte[] bufferedReadToByteArray( InputStream in){
		
		return bufferedReadToByteArray( in, 1024);
	}

	
	/**
	 * 读流，返回byte[]
	 * @param in 
	 * @param buff 字节的缓冲大小
	 * @return byte[]
	 */
	public static byte[] bufferedReadToByteArray( String path, int buff){
		
		if(path == null || "".equals(path.trim()) || buff < 1 )
			return null;
		
		List<byte[]> byteList = new ArrayList<byte[]>();
		
		try {			
			BufferedInputStream bin = new BufferedInputStream( new FileInputStream(path));
			byte[] b = new byte[buff]; 
			int len = -1;
			while((len = bin.read(b))>=0){
				byteList.add( Arrays.copyOf(b, len));
			}
			bin.close();
			bin.close();
			return conbineByteArray(byteList);
			
		} catch (IOException e) {
			return null;
		}	
	}
	
	
	public static byte[] bufferedReadToByteArray( String path ){
		
		return bufferedReadToByteArray(path, 1024);
	}
	
	
	/**
	 * 读流，返回String
	 * @param in 
	 * @param charset 编码方法
	 * @return String 
	 */
	public static String bufferedReadToString( InputStream in,  String charset){
		
		if(in == null)
			return null;
					
		StringBuffer sb = new StringBuffer("");
		InputStreamReader inr = null;
		
		try {			
			if( charset ==null)
				inr = new InputStreamReader(in);
			else
				inr = new InputStreamReader(in, charset);
			
			BufferedReader reader = new BufferedReader(inr);
			String str = null;
			while( (str = reader.readLine()) != null )
				sb.append(str);
			
			reader.close();
			return sb.toString();
			
		} catch (IOException e) {
			return null;
		}
	}

	
	/**
	 * 读流，返回String
	 * @param in 
	 * @return String
	 */
	public static String bufferedReadToString( InputStream in){
		
		return bufferedReadToString( in, null);
	}
	
	
	/**
	 * 把字节数组写到指定流中
	 * @param out 
	 */
	public static void bufferedWriteByteArray( byte[] bytes ,OutputStream out){

		if( bytes == null || bytes.length ==0 || out == null )
			return;
		
		PrintStream ps = new PrintStream(out, true);
		ps.write(bytes , 0, bytes.length);
		ps.close();
	}
	
	
	/**
	 * 把字节数组写到指定位置
	 * @param out 
	 */
	public static void bufferedWriteByteArray( byte[] bytes ,String path){
		
		if( bytes == null || bytes.length ==0 || path == null || "".equals(path.trim()))
			return;
		
		try {
			OutputStream out = new FileOutputStream(path);
			bufferedWriteByteArray(bytes, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void bufferedWriteByteArray( byte[] bytes ,File file){
		
		if( bytes == null || bytes.length ==0 || file == null )
			return;
		
		try {
			OutputStream out = new FileOutputStream(file);
			bufferedWriteByteArray(bytes, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	
	/**
	 * 把字符串写到指定流中
	 * @param out 
	 */
	public static void bufferedWriteString( String str ,OutputStream out){
		
		if( str == null || "".equals(str.trim()) || out == null )
			return;
		
		PrintWriter writer = new PrintWriter(out, true);
		writer.write(str);
		writer.close();
	}
	
	
	/**
	 * 把字符串写到指定位置
	 * @param out 
	 */
	public static void bufferedWriteString( String str ,String path){

		if( str == null || "".equals(str.trim()) || path == null || "".equals(path))
			return;
		
		try {
			OutputStream out = new FileOutputStream(path);
			bufferedWriteString( str, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public static void bufferedWriteString( String str ,File file){

		if( str == null || "".equals(str.trim()) || file == null )
			return;
		
		try {
			OutputStream out = new FileOutputStream(file);
			bufferedWriteString( str, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
		
	/**
	 * 把List<byte[]>按List元素顺序组装成一个<byte[]
	 * @param list
	 * @return
	 */
	public static byte[] conbineByteArray( List<byte[]> list){
		
		int len = 0;
		for(int i = 0; i < list.size(); i++)
			for( int j = 0; j < list.get(i).length; j++)
				len++;
		byte[] b = new byte[len];		
				
		int index = 0;
		for(int i = 0; i < list.size(); i++)
			for( int j = 0; j < list.get(i).length; j++){
				b[index] = list.get(i)[j];
				index++;
			}
		
		return b;
	}
	
	
	public static Map<String, String> loadPropertiesToMap( InputStream in) throws Exception{
		
		Properties pro = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		
		pro.load(in);
		@SuppressWarnings("unchecked")
		Enumeration<String> e = (Enumeration<String>) pro.propertyNames();
		
		while( e.hasMoreElements()){
			String name = e.nextElement();
			
			if (StringUtils.isBlank(name))
				throw new Exception("Properties配置文件不正确，name不可以为空");
			
			if (map.get(name = name.trim()) != null)
				throw new Exception("Properties配置文件不正确，name不可以重复");
			
			map.put(name, pro.getProperty(name));
		}
		
		return map;
	}
	
	
	/**
	 * @param path 文件的完全路径
	 * @return 把properties加载成MAP返回
	 * @throws Exception 
	 */
	public static Map<String, String> loadPropertiesToMap(String path) throws Exception{
		InputStream in = StreamUtil.getInputStream(path);
		return loadPropertiesToMap(in);
	}

	
	public static void main(String[] args) {

	}
}



