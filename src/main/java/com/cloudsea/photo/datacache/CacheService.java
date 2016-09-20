package com.cloudsea.photo.datacache;

import java.util.List;

public interface CacheService {

	void loadMenu();

	void loadIcon();
	
	void loadGallery();

	List<?> getAllMenus();

	void loadAllCache();

	Object getCacheObject(String type, Long key);

	List<Object> getCacheCollection(String type);

}
