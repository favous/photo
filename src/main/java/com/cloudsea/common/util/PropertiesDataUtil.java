package com.cloudsea.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;



/**
 * 
 * @author zhangxiaorong
 *
 *同一个项目不同的目录下的资源的名字都不能相同
 */
public class PropertiesDataUtil {
	
	private static final String PROJECT_PATH = PropertiesDataUtil.class.getResource("/").getPath();
	private static final Map<String, Map<String, String>> CONFIG_MAP = new HashMap<String, Map<String, String>>();
	 private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("configData"); 
	
	private PropertiesDataUtil(){}
	
	
	/**
	 * 
	 * @param fileName 	properties文件名，不要用后缀
	 * @param key 		properties中的key
	 * @return    		properties中的value
	 */
	public static String getValue(String fileName, String key){
		
		/*System.out.println("==========="+projectPath);
		System.out.println("----------"+PropertiesDataCenter.class.getResource("/"));
		Map<String, String> map = configMap.get(fileName);
		if (map == null || map.size() == 0)
			loadMapByFileName(projectPath, fileName);
		
		map = configMap.get(fileName);
		if (map != null)
			return map.get(key);
		
		return null;*/
		return RESOURCE_BUNDLE.getString(key);
	}

/**
 * @param fileName 	properties文件名，不要用后缀
 * @return			properties中的键值对Map集合
 * @throws Exception 
 */
	public static Map<String, String> getDataMap(String fileName) throws Exception{
		
		Map<String, String> map = CONFIG_MAP.get(fileName);
		
		if (map == null || map.size() == 0)
			loadMapByFileName(PROJECT_PATH, fileName);
		
		return CONFIG_MAP.get(fileName);
	}
	
	
	private static Map<String, String> loadMapByFileName(String projectPath, String fileName) throws Exception{
		
		
		if (projectPath.endsWith(".jar") || projectPath.endsWith(".jar/") 
				|| projectPath.endsWith(".jar\\")){
			
			JarFile file = new JarFile(new File(projectPath));
			
			try {
				Enumeration<JarEntry> enumer = file.entries();
				
				while( enumer.hasMoreElements()){
					JarEntry entry = enumer.nextElement();
					
					if (entry.getName().equals(fileName + ".properties")){
						InputStream is = file.getInputStream(entry);
						Map<String, String> dataMap = StreamUtil.loadPropertiesToMap(is);
						CONFIG_MAP.put(fileName, dataMap);
						is.close();
						return dataMap;
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				file.close();
			}
			return null;
			
		} else{
			
			File file = new File(projectPath);
			String[] filePaths = file.list();
			
			for (int i = 0; filePaths != null && i < filePaths.length; i++){
				
				String fullPath = projectPath + filePaths[i];
				if (new File(fullPath).isFile()){
					
					if (filePaths[i].equals(fileName + ".properties")){
						InputStream in = StreamUtil.getInputStream(fullPath);
						Map<String, String> dataMap = StreamUtil.loadPropertiesToMap(in);
						CONFIG_MAP.put(fileName, dataMap);
						return dataMap;
					}
					
				} else
					loadMapByFileName(fullPath, fileName);
			}
			
			return null;
		}
	}
	

}
