package com.cloudsea.photo.utils.commonutils;

import java.util.ResourceBundle;

import com.cloudsea.photo.enums.LocaleEnum;

/** 
 * 读取国际化文件中定义的值 
 *  
 * @param key 
 * @return 
 */ 
public class ResourceBundleUtil {

	private static ResourceBundleUtil resourceUtils = new ResourceBundleUtil();
	
	private LocaleEnum localeEnum = LocaleEnum.CHINESE;
	
	private static final String RESOURCE_CLASSLOADER_PATH_PREFIX = "i18n_"; 
    private static String RESOURCE_CLASSLOADER_PATH_FULL; 
    private static ResourceBundle resourceBundle; 
	
	private ResourceBundleUtil() {
		refreshBundle();
	}
	
	public static ResourceBundleUtil getResourceUtils() {
		return resourceUtils;
	}
	
	public void setLocale(LocaleEnum localeEnum) {
		this.localeEnum = localeEnum;
		refreshBundle();
	}
	
	public LocaleEnum getLocale() {
		return this.localeEnum;
	}
	

    public static String getValue(String key) { 
        return resourceBundle.getString(key); 
    } 
    
    private void refreshBundle() { 
        RESOURCE_CLASSLOADER_PATH_FULL = RESOURCE_CLASSLOADER_PATH_PREFIX + localeEnum.getLocale().toString(); 
        resourceBundle = ResourceBundle.getBundle(RESOURCE_CLASSLOADER_PATH_FULL, localeEnum.getLocale()); 
    } 
}
