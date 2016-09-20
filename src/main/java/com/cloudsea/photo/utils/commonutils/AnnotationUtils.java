package com.cloudsea.photo.utils.commonutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author zhangxiaorong 2014-3-26
 */
public class AnnotationUtils {

	static String[] origMethodArray = new String[] { "equals", "toString", "hashCode", "annotationType" };

	public static Map<String, Object> parseClassAnnotationToMap(Class<?> clazz,
			Class<? extends Annotation> annotationClass) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Annotation annotation = clazz.getAnnotation(annotationClass);
		if (null == annotation)
			return null;

		Method[] methods = annotation.getClass().getDeclaredMethods();
		Map<String, Object> map = new HashMap<String, Object>();

		out: for (Method method : methods) {
			String methodName = method.getName();
			for (String str : origMethodArray) {
				if (methodName.equals(str))
					continue out;
			}

			Object obj = method.invoke(annotation, new Object[] {});
			map.put(methodName, obj);
		}

		return map;
	}

	public static JSONObject parseClassAnnotationToJson(Class<?> clazz, Class<? extends Annotation> annotationClass)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Annotation annotation = clazz.getAnnotation(annotationClass);
		if (null == annotation)
			return null;

		Method[] methods = annotation.getClass().getDeclaredMethods();
		JSONObject jsonObj = new JSONObject();

		out: for (Method method : methods) {
			String methodName = method.getName();
			for (String str : origMethodArray) {
				if (methodName.equals(str))
					continue out;
			}

			Object obj = method.invoke(annotation, new Object[] {});
			jsonObj.put(methodName, obj);
		}

		return jsonObj;
	}

	public static Map<String, Object> parseMethodAnnotationToMap(Method method,
			Class<? extends Annotation> annotationClass) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Annotation annotation = method.getAnnotation(annotationClass);
		if (null == annotation)
			return null;

		Method[] methods = annotation.getClass().getDeclaredMethods();
		Map<String, Object> map = new HashMap<String, Object>();

		out: for (Method m : methods) {
			String methodName = m.getName();
			for (String str : origMethodArray) {
				if (methodName.equals(str))
					continue out;
			}

			Object obj = m.invoke(annotation, new Object[] {});
			map.put(methodName, obj);
		}

		return map;
	}

}
