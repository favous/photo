package com.cloudsea.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ReflectUtil {
	
	/**
	 * 从指定类中，包括其父类中，查找类中指定属性
	 * @param claz
	 * @param list
	 */
	public static Field getFieldWithParent(Class<?> claz, String name){
		Field[] fields = claz.getDeclaredFields();
		for (Field f : fields)
			if (f.getName().equals(name))
				return f;
		
		claz = claz.getSuperclass();
		if (claz != null)
			return getFieldWithParent(claz, name);
		else
			return null;
	}
	
	/**
	 * 获取所有属性，包括父类中的
	 * @param claz
	 * @param list
	 */
	public static void getAllFieldWithParent(Class<?> claz, List<Field> list){
		Field[] fields = claz.getDeclaredFields();
		for (Field f : fields)
			list.add(f);
		
		claz = claz.getSuperclass();
		if (claz != null)
			getAllFieldWithParent(claz, list);
	}
	
	/**
	 * 获取所有属性，包括父类中的
	 * @param claz
	 * @param list
	 */
	public static void getAllFieldWithParent(Class<?> claz, Map<Field, Class<?>> map){
		Field[] fields = claz.getDeclaredFields();
		for (Field f : fields)
			map.put(f, claz);
		
		claz = claz.getSuperclass();
		if (claz != null)
			getAllFieldWithParent(claz, map);
	}
	
	/**
	 * 获取所有方法，包括父类中的
	 * @param claz
	 * @param list
	 */
	public static Method getMethodWithParent(Class<?> claz, String name){
		Method[] methods = claz.getDeclaredMethods();
		for (Method m : methods)
			if (m.getName().equals(name))
				return m;
		
		claz = claz.getSuperclass();
		if (claz != null)
			return getMethodWithParent(claz, name);
		else
			return null;
	}
	
	/**
	 * 获取所有方法，包括父类中的
	 * @param claz
	 * @param list
	 */
	public static void getAllMethodWithParent(Class<?> claz, List<Method> list){
		Method[] methods = claz.getDeclaredMethods();
		for (Method m : methods)
			list.add(m);
		
		claz = claz.getSuperclass();
		if (claz != null)
			getAllMethodWithParent(claz, list);
	}
	
	/**
	 * 获取属性上的泛型
	 * @param field
	 * @return
	 */
	public static Class<?> getFieldParameterizedType(Field field){
		return getFieldParameterizedType(field, 0);
	}
	
	/**
	 * 获取属性上的泛型
	 * @param field
	 * @return
	 */
	public static Class<?> getFieldParameterizedType(Field field, int index){
		Type type = field.getGenericType();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type[] types = parameterizedType.getActualTypeArguments();  
	    Class<?> claz =  (Class<?>) types[index];
	    return claz;
	}
	
	/**
	 * 获取cls父类的泛型
	 * @param cls
	 * @return
	 */
	public static Class<?> getSuperclassParameterizedType(Class<?> cls){
        return getSuperclassParameterizedType(cls, 0);
	}
	
	
	/**
	 * 获取cls父类的泛型
	 * @param cls
	 * @return
	 */
	public static Class<?> getSuperclassParameterizedType(Class<?> cls, int index){
		Type type = cls.getGenericSuperclass();
		
		if (type == null)
			return null;
		
		ParameterizedType type1 = (ParameterizedType) type;  
        Type[] types = type1.getActualTypeArguments();  
        Class<?> claz =  (Class<?>) types[index];
        return claz;
	}

	/**
	 * 数组泛型
	 * @param array
	 * @return
	 */
	public static Class<?> getArrayElementType(Class<?> claz){
		Class<?> type = claz.getComponentType();
		return type;
	}


}
