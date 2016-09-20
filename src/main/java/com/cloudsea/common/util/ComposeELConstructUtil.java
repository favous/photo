package com.cloudsea.common.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ComposeELConstructUtil {
	
	/**
	 * 把roles[1].resources[0].name=cc这种结构的传参，翻译成实体类结构，并且完成传参
	 * 
	 * 实体类有空的构造方法 
	 * 要求是实体类的集合属性必须是基于Collection接口，且指定泛型，且已经初始化成空集合
	 * 
	 * @param obj
	 * @param paramMap
	 * @throws Exception 
	 */
	public static void composeFields(Object obj, Map<String, String> paramMap) throws Exception {
		
		Map<String, Object> composeMap = new HashMap<String, Object>();

		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			String key = entry.getKey().trim();
			if (key != null){
				String val = entry.getValue();
				if (key == null || key.trim().equals(""))
					continue;
				
				String composeKey = key;	//composeMap的Key，标识对象或者集合，是唯一的
				populateField(obj, composeMap, val, key, composeKey);
			}
		}
	}
	

	@SuppressWarnings("unchecked")
	private static void populateField(Object obj, Map<String, Object> composeMap, String val, 
			String key, String composeKey) throws Exception {
		
		String fieldName = composeKey;
		String prefix = key.substring(0, key.lastIndexOf(composeKey));

		if (composeKey.contains(".")) {		//表示有子属性
			
			fieldName = composeKey.substring(0, fieldName.indexOf("."));
			
			if (fieldName.contains("[") && fieldName.contains("]") 
					&& fieldName.indexOf("[") < fieldName.indexOf("]")){//表示是对多关系的集合
				
				String nodeName = fieldName.substring(0, fieldName.indexOf("["));
				
				Collection<Object> composeNode = (Collection<Object>) composeMap.get(prefix + nodeName); //集合对象
				Field nodeField = ReflectUtil.getFieldWithParent(obj.getClass(), nodeName);
				if (composeNode == null){
					nodeField.setAccessible(true);
					composeNode = (Collection<Object>) nodeField.get(obj);
					composeMap.put(prefix + nodeName, composeNode);
				}
				
				Object composeValue = composeMap.get(prefix + fieldName);//composeValue是集合的实体元素对象
				if (composeValue == null){
					Class<?> eletype = ReflectUtil.getFieldParameterizedType(nodeField);
					composeValue = eletype.newInstance();
					composeMap.put(prefix + fieldName, composeValue);
				}
				composeNode.add(composeValue);

				String composeAttr = composeKey.substring(composeKey.indexOf(".") + 1);
				populateField(composeValue, composeMap, val, key, composeAttr);				
				
			} else{		//表示是对一关系的对象
				
				Object composeValue = composeMap.get(prefix + fieldName); //值是实体对象
				if (composeValue == null){
					Field field = ReflectUtil.getFieldWithParent(obj.getClass(), fieldName);
					field.setAccessible(true);
					composeValue = field.get(obj);
					composeMap.put(prefix + fieldName, composeValue);
				}
				
				String composeAttr = composeKey.substring(composeKey.indexOf(".") + 1);
				populateField(composeValue, composeMap, val, key, composeAttr);
			}
			
		} else {		//表示没有子属性
			
			if (fieldName.contains("[") && fieldName.contains("]") 
					&& fieldName.indexOf("[") < fieldName.indexOf("]")){//表示此变量是表示没有子属性的集合
				
				String nodeName = fieldName.substring(0, fieldName.indexOf("["));
				Object composeNode = composeMap.get(prefix + nodeName); //集合对象
				Field nodeField = ReflectUtil.getFieldWithParent(obj.getClass(), nodeName);
				if (composeNode == null){
					
					if (nodeField.getType().isArray()){
						
					} else if (Collection.class.isAssignableFrom(nodeField.getType())){
						nodeField.setAccessible(true);
						Collection<Object> composeCollection = (Collection<Object>) nodeField.get(obj);
						composeMap.put(prefix + nodeName, composeCollection);
						composeCollection.add(val);
					}
				}
				else {
					if (nodeField.getType().isArray()){
						
					}
					else if (Collection.class.isAssignableFrom(nodeField.getType())){
						Collection<Object> composeCollection = (Collection<Object>) composeNode;
						composeCollection.add(val);
					}
				}
			} 
			else {			//表示此变量是简单数值类型
				Field field = ReflectUtil.getFieldWithParent(obj.getClass(), fieldName);
				field.setAccessible(true);
				Object value = TypeUtil.convertByTypeFormString(field.getType(), val);
				field.set(obj, value);
			}
		}
	}


//	public static void main(String[] args) throws Exception {
//		
//		System.out.println(Collection.class.isAssignableFrom(List.class));;
//		
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("userName", "zxr");
//		paramMap.put("password", "123");
////		paramMap.put("roles[0].aa[0]", "5656");
////		paramMap.put("roles[0].aa[1]", "5656");
//		paramMap.put("roles[0].roleNo", "8833");
//		paramMap.put("roles[0].roleName", "aa");
//		paramMap.put("roles[1].roleName", "8844");
//		paramMap.put("roles[1].roleName", "bb");
//		paramMap.put("roles[0].resources[0].resourceName", "cdc");
//		paramMap.put("roles[1].resources[0].resourceNo", "55");
//		paramMap.put("roles[1].resources[1].resourceName", "dd");
//		
//		com.cloudsea.sys.entity.User user = new com.cloudsea.sys.entity.User();
//		composeFields(user, paramMap);
//		System.out.println();
//
//	}
	

}
