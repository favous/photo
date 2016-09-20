package com.cloudsea.photo.utils.commonutils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author zhangxiaorong
 * 2014-6-29
 */
public class Json {
	
	/**
	 * 参数如果是集合需要调理一下代码，目前集合和一般对象的转换是分开两个方法
	 * @param object
	 * @return
	 */
	public static String toJson(Object object){
		Json f = new Json(){
			public Field[] getDeclaredFields(Object t) {
				return getClassFieldsWithSuper(t.getClass());
			}
		};
		f.setFormateDepth(500);
		return f.toJSON(object).toString();
	}
	
	
	//map.key:hashCode 		map.value:jsonobj
	private Map<Integer, Object> hashCodeMap = new HashMap<Integer, Object>();
	
	//指定转换日期格式
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	//指定一些类型的值，用自定义转换方法
	private Map<Class<?>, FormateByUser> formateByUserMap = new HashMap<Class<?>, FormateByUser>();
	
	//哪些属性不转换
	private Map<String, Object> unFormateFields = new HashMap<String, Object>();
	
	
	//转换子属性深度
	private int formateDepth = 300;
	
	private int currentDepth = 0;

		
	public void setFormateDepth(int formateDepth) {
		this.formateDepth = formateDepth;
	}
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	public Map<String, Object> getUnFormateFields() {
		return unFormateFields;
	}
	public void setUnFormateFields(Map<String, Object> unFormateFields) {
		this.unFormateFields = unFormateFields;
	}
	public Map<Class<?>, FormateByUser> getFormateByUserMap() {
		return formateByUserMap;
	}
	public void setFormateByUserMap(Map<Class<?>, FormateByUser> formateByUserMap) {
		this.formateByUserMap = formateByUserMap;
	}
	

	public Object toJSON(Object t) {
		return toJSON(t, true);
	}

	//此方法可重写，默认获取的是对象本身所有的属性值，但不包括父对象属性的值
	public Field[] getDeclaredFields(Object t){
		return t.getClass().getDeclaredFields();
	}
	
	private Object toJSON(Object t, boolean isbegin) {
		
		if (t == null)
			return null;
		
		if (isbegin == true)
			hashCodeMap.clear();
		
		if (existObject(t))
			return hashCodeMap.get(t.hashCode());
		
		JSONObject jsonObj = new JSONObject();
		hashCodeMap.put(t.hashCode(), jsonObj);
		
		try {
			
			if (isBaseType(t.getClass()))
				return t;
			
			else if (isTimestampType(t.getClass()))
				return dateFormat.format(((Timestamp)t).getTime());
			
			else if (isDateType(t.getClass()))
				return dateFormat.format(t);
			
			else if (List.class.isAssignableFrom(t.getClass())) {
				JSONArray jsonAarry = new JSONArray();
				List<?> list = (List<?>) t;
				if (list.size() > 0) {
					for (Object obj : list)
						jsonAarry.add(this.toJSON(obj)); 
				}
				return jsonAarry;
			} else if (Map.class.isAssignableFrom(t.getClass())){
				
				Map<?, ?> map = (Map<?, ?>) t;
				if (map.size() == 0)
					return jsonObj;
				for (Entry<?, ?> entry : map.entrySet()){
					Object fieldValue = entry.getValue();
					Object fieldName = entry.getKey();
						
					Object val = null;
					if (fieldValue != null) {					
						val = parseVal(fieldValue, fieldValue.getClass());
					}
					
					jsonObj.put(fieldName, val);
				}
				return jsonObj;
				
			} else{
				
				Field[] fields = getDeclaredFields(t);
				sortFields(fields);
				
				for (int i = 0; i < fields.length; i++){
					
					String fieldName = fields[i].getName();
					fields[i].setAccessible(true);
					Object fieldValue = fields[i].get(t);
					
					if (fieldValue == null)
						continue;
					
					Object val = parseVal(fieldValue, fields[i].getType());
					jsonObj.put(fieldName, val);
				}
			}
				
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	private Object parseVal(Object fieldValue, Class<?> type) throws Exception {
		
		for (Entry<Class<?>, FormateByUser> entry : formateByUserMap.entrySet())
			if (entry.getKey() == type)
				return entry.getValue().formate(fieldValue);
		
		if (isBaseType(type))
			return fieldValue;
		
		else if (isTimestampType(type))
			return dateFormat.format(((Timestamp)fieldValue).getTime());
		
		else if (isDateType(type))
			return dateFormat.format(fieldValue);
		
		else if (Map.class.isAssignableFrom(type)){
			JSONObject jsonObj = new JSONObject();
			Map<?, ?> map = (Map<?, ?>) fieldValue;
			if (map.size() == 0)
				return jsonObj;
			
			for (Entry<?, ?> entry : map.entrySet()){
				Object value = entry.getValue();
				Object fieldName = entry.getKey();
				
				Object obj = null;
				if (fieldValue != null && currentDepth < formateDepth){
					currentDepth++;
					obj = this.toJSON(value, false);
				}
				jsonObj.put(fieldName, obj);
				hashCodeMap.put(value.hashCode(), obj);
			}
			return jsonObj;
		}

		else if (Collection.class.isAssignableFrom(type)){
			
			if (existObject(fieldValue))
				return hashCodeMap.get(fieldValue.hashCode());
			
			Collection<?> col = (Collection<?>) fieldValue;
			
			if (col.size() == 0)
				return null;
				
			else {
				JSONArray jsonAarry = new JSONArray();
				
				Iterator<?> it = col.iterator();
				element:
					while (it.hasNext()){
						
						Object entity = it.next();
						if (existObject(entity)){
							jsonAarry.add(hashCodeMap.get(entity.hashCode()));
							continue element;
						}
						
						Object obj = null;
						if (currentDepth < formateDepth){
							currentDepth++;
							obj = this.toJSON(entity, false);
						}
						jsonAarry.add(obj);
						hashCodeMap.put(entity.hashCode(), obj);
					}
				
				hashCodeMap.put(col.hashCode(), jsonAarry);
				return jsonAarry;
			}
		}
		
		else {
			if (!existObject(fieldValue)){
				Object obj = null;
				if (currentDepth < formateDepth){
					currentDepth++;
					obj = this.toJSON(fieldValue, false);
				}
				hashCodeMap.put(fieldValue.hashCode(), obj);
				return obj;
			} else{
				return hashCodeMap.get(fieldValue.hashCode());
			}
		}
	}
	
	/**
	 * 把集合放在后面，把非集合属性放前面
	 * hashcodelist 是用来判断，不让整个Bean中的属性和子属性中，有重复的对象存入。
	 * 这样各层的属性值如果是同样的对象，就可以先执行放到上层属性中，而不会放到下层的集合中
	 */
	private static void sortFields(Field[] fields) {
		if (fields.length == 0)
			return;
		
		for (int i = 0; i < fields.length - 1; i++){
			if (Collection.class.isAssignableFrom(fields[i].getType())){
				Field temp = fields[i];
				for (int j = i + 1; j < fields.length; j++)
					fields[j-1] = fields[j];
				fields[fields.length-1] = temp;
			}
		}
	}

	private boolean existObject(Object obj) {
		
		if (obj == null)
			return true;
		for (Integer val :hashCodeMap.keySet())
			if (val.hashCode() == obj.hashCode())
				return true;
		return false;
	}

	private static boolean isDateType(Class<?> claz) {
		
		if(claz == null)
			return false;

		if (claz.getName().equals(java.sql.Date.class.getName()))
			return true;

		if (claz.getName().equals(java.util.Date.class.getName()))
			return true;
		
		return false;
	}
	
	private static boolean isTimestampType(Class<?> claz) {
		if(claz == null)
			return false;

		if (claz.getName().equals(java.sql.Timestamp.class.getName()))
			return true;
		
		return false;
	}

	private static Field[] getClassFieldsWithSuper(Class<?> clazz){
		LinkedHashMap<String, Field> fieldMap = new LinkedHashMap<String, Field>();
		for (Field field : clazz.getDeclaredFields()){
			fieldMap.put(field.getName(), field);
		}
		getSuperClassFields(clazz, fieldMap);
		Field[] fields = new Field[fieldMap.size()];
		int index = 0;
		for (Field field : fieldMap.values()){
			fields[index] = field;
			index++;
		}
		return fields;
	}

	private static void getSuperClassFields(Class<?> clazz, LinkedHashMap<String, Field> fieldMap){
		Class<?> superclass = clazz.getSuperclass();
		if (superclass != Object.class){
			for (Field field : superclass.getDeclaredFields()){
				String name = field.getName();
				if (!fieldMap.containsKey(name)){
					fieldMap.put(name, field);
				}
			}
			getSuperClassFields(superclass, fieldMap);
		}
	}

	private static boolean isBaseType(Class<?> o) {
		
		if(o == null)
			return true;
		
		String className = o.getName();
		if (className == null)
			return false;
		if (o.isPrimitive())
			return true;
		if (o == Byte.class)
			return true;
		if (o == Character.class)
			return true;
		if (o == Short.class)
			return true;
		if (o == Integer.class)
			return true;
		if (o == Long.class)
			return true;
		if (o == Float.class)
			return true;
		if (o == Double.class)
			return true;
		if (o == Boolean.class)
			return true;
		if (o == String.class)
			return true;
		if (o == StringBuffer.class)
			return true;
		if (o == StringBuilder.class)
			return true;
		if (o == BigInteger.class)
			return true;
		if (o == BigDecimal.class)
			return true;
		return false;
	}
	
	public interface FormateByUser{
		Object formate(Object obj) throws Exception;
	}
	
}
