package com.cloudsea.photo.utils.commonutils;

public class MathUtil {
	
	
	public static boolean isZeroValue(Object value){
		if ((value.getClass() == byte.class || value.getClass() == Byte.class) && Integer.parseInt(String.valueOf(value)) == 0){
			return true;
		}
		if ((value.getClass() == short.class || value.getClass() == Short.class) && Integer.parseInt(String.valueOf(value)) == 0){
			return true;
		}
		if ((value.getClass() == int.class || value.getClass() == Integer.class) && Integer.parseInt(String.valueOf(value)) == 0){
			return true;
		}
		if ((value.getClass() == long.class || value.getClass() == Long.class) && Long.parseLong(String.valueOf(value)) == 0){
			return true;
		}
		if ((value.getClass() == float.class || value.getClass() == Float.class) && Float.parseFloat(String.valueOf(value)) == 0){
			return true;
		}
		if ((value.getClass() == double.class || value.getClass() == Double.class) && Double.parseDouble(String.valueOf(value)) == 0){
			return true;
		} else
			return false;
	}
	

}
