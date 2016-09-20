package com.cloudsea.photo.utils.commonutils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


/**
 * @author zhangxiaorong
 * 2014-1-26
 */
public class StringUtil {
	
	
	public static boolean isEmpty(String string){
		if (null == string || "".equals(string.trim()))
			return true;
		else
			return false;
	}

	
	public static boolean isUpperCase(String string){
		char[] cs = string.toCharArray();
		for (char c :cs){
			if (c < 65 || c > 90)
				return false;
		}
		return true;
	}	
	
	public static boolean isLowerCase(String string){
		char[] cs = string.toCharArray();
		for (char c :cs){
			if (c < 97 || c > 122)
				return false;
		}
		return true;
	}
	
	public static String replaceFirstByIndex(String val, String regex, String replacement, int index){
		String beforeStr = val.substring(0, index);
		String afterStr = val.substring(index);
		afterStr = afterStr.replaceFirst(regex, replacement);
		return beforeStr + afterStr;
	}
	
	public static Object parseStringByType(String value, Class<?> type){
		Object obj = null;
    	if (type == Byte.class || type == byte.class){
    		obj = Byte.parseByte(value);
    	} else if (type == Short.class || type == short.class){
    		obj = Short.parseShort(value);
    	} else if (type == Integer.class || type == int.class){
    		obj = Integer.parseInt(value);
    	} else if (type == Long.class || type == long.class){
    		obj = Long.parseLong(value);
    	} else if (type == Float.class || type == float.class){
    		obj = Float.parseFloat(value);
    	} else if (type == Double.class || type == double.class){
    		obj = Double.parseDouble(value);
    	} else if (type == Character.class || type == char.class){
    		obj = value.charAt(0);
    	} else if (type == Boolean.class || type == boolean.class){
    		obj = Boolean.parseBoolean(value);
    	} else if (type == String.class){
    		obj = value;
    	} else if (type == StringBuffer.class){
    		obj = new StringBuffer(value);
    	} else if (type == StringBuilder.class){
    		obj = new StringBuilder(value);
    	} else if (type == BigInteger.class){
    		obj = new BigInteger(value);
    	} else if (type == BigDecimal.class){
    		obj = new BigDecimal(value);
    	} 
    	return obj;
	}
	

	/**
	 * 提取所有是字母或者是数字的子字符串集合
	 * @param string
	 * @return
	 */
	public static List<String> superSplit(String string){
		return extractString(string, "[0-9a-zA-Z]");
	}

	/**
	 * 随机生成指定长度的字母与数字组合的字符串
	 * @param length	表示生成字符串的长度  
	 * @return
	 */
	public static String getRandomLetterAndNumber(int length) {
		return getRandomString("abcdefghijklmnopqrstuvwxyz0123456789", length);
	}
	
	
	/**
	 * 用stringSrc的元素，随机生成指定长度的字符串
	 * @param stringSrc	生成的字符串来源
	 * @param length	表示生成字符串的长度  
	 * @return
	 */
	public static String getRandomString(String stringSrc, int length) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(stringSrc.length());
			sb.append(stringSrc.charAt(number));
		}
		return sb.toString();
	}
	
	
	/**
	 * 
	 * @param string	按一定规则，提取所有符合规则的子字符串集合
	 * @param regex	正则表达式
	 * @return
	 */
	public static List<String> extractString(String string, String regex){
		
		List<String> list = new ArrayList<String>();
		int start = 0;
		
		for (int i = 0; i < string.length(); i++){
			if (!String.valueOf(string.charAt(i)).matches(regex)){
				if (start < i){
					String subStr = string.substring(start, i);
					list.add(subStr);
				}
				start = i + 1;
			}
		}
		
		if (start < string.length())
			list.add(string.substring(start, string.length()));
		
		return list;
	}
	
	
	/**
	 * 用字符数组中的所有元素，列举所有不同的子字符数组
	 * @param charArray	字符数组源
	 * @param getLength	规定列举的子字符数组长度
	 * @return
	 */
	public static Set<char[]> getAllSuberCharArray(char[] charArray, int getLength){
		Set<char[]> oldArraySet = new HashSet<char[]>();
		Set<char[]> newArraySet = new HashSet<char[]>();
		
		char[] beginArray = new char[getLength];
		for (int i = 0; i < getLength; i++)
			beginArray[i] = '\u0000';
			
		oldArraySet.add(beginArray);
		
		for (int i = 0; i < getLength; i++){
			for (char[] oldArray : oldArraySet){
				for (int j = 0; j < charArray.length; j++){
					
					char[] newArray = Arrays.copyOf(oldArray, oldArray.length);
					newArray[i] = charArray[j];
					
					if (isNoneElementRepeat(newArray))
						newArraySet.add(newArray);
				}
			}
			oldArraySet = newArraySet;
			newArraySet = new HashSet<char[]>();
		}
		
		return oldArraySet;
	}

	
	public static boolean isNoneElementRepeat(char[] charArray){
		for (int i = 0; i < charArray.length - 1; i++)
			for (int j = i + 1; j < charArray.length; j++)
				if (charArray[i] != '\u0000')
					if (charArray[i] == charArray[j])
						return false;
		return true;
	}
	
	
	
	public static void main(String[] s){
//		char[] chs = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'};
//		System.out.println(getAllSuberCharArray(chs, 6).size());
//		
//		String ss = "12|245";
//		System.out.println(ss.split("|").length);
		
		ThreadLocal<String> t = new ThreadLocal<String>();
		t.set("1");
		t.set("2");
		System.out.println(t.get());
		
		ThreadLocal<String> t2 = new ThreadLocal<String>();
		t2.set("a");
		System.out.println(t.get());
		System.out.println(t2.get());
		System.out.println(new ThreadLocal<String>().get());
	}

}
