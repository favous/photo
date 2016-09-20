package com.cloudsea.common.util;

public class ClassUtil {


	/**
	 * claz是否等于rightClass或者是rightClass的子集(实现或者承继关系)
	 * @param claz
	 * @param rightClass
	 * @return
	 */
	public static boolean isRightOrSubClass(Class<?> claz, Class<?> rightClass) {
		if (claz == null)
			return false;
			
		if (claz.getSuperclass() == null && claz.getInterfaces().length == 0)
			return false;
		
		if (claz.getSuperclass() != null && claz.getSuperclass() == rightClass)
			return true;
		
		if (claz.getInterfaces().length > 0 && containNeededInterface(claz.getInterfaces(), rightClass))
			return true;

		else {
			 boolean result = isRightOrSubClass(claz.getSuperclass(), rightClass) ;
			 
			 if (result)
				 return true;
			 
			 if (claz.getInterfaces().length > 0)
				 for (Class<?> c : claz.getInterfaces()){
					 result = isRightOrSubClass(c, rightClass) ;
					 if (result)
						 return result;
				 }
			 
			 return false;
		}
	}
	
	/**
	 * 
	 * @param interfaces
	 * @param rightClass
	 * @return
	 */
	public static boolean containNeededInterface(Class<?>[] interfaces, Class<?> rightClass) {
		for (int i = 0; i < interfaces.length; i++)
			if (interfaces[i] == rightClass)
				return true;
		return false;
	}
	
	public static void main(String[] args) {
		
	}

}
