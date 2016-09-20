package com.cloudsea.photo.datacache;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudsea.photo.dto.MenuDto;
import com.cloudsea.photo.entity.Gallery;
import com.cloudsea.photo.entity.Icon;
import com.cloudsea.photo.module.pic.service.PictrueService;
import com.cloudsea.photo.module.user.service.MenuService;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {
	
	private static Map<String, LinkedHashMap<Long, Object>> dataMap = new HashMap<String, LinkedHashMap<Long, Object>>();

	public static String MENU_TYPE = "1";
	public static String GALLERY_TYPE = "2";
	public static String ICON_TYPE = "3";
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	PictrueService pictrueService;

	@Override
	public void loadAllCache(){
		loadMenu();
		loadIcon();
		loadGallery();
	}

	@Override
	public void loadMenu(){
		List<MenuDto> list = menuService.getAllMenus();
		loadData(MENU_TYPE, list, "getId");
	}

	@Override
	public void loadGallery() {
		List<Gallery> list = pictrueService.getAllGallerys();
		loadData(GALLERY_TYPE, list, "getMenuId");
	}
	
	@Override
	public void loadIcon(){
		List<Icon> list = pictrueService.getAllIcons();
		loadData(ICON_TYPE, list, "getId");
	}
	
	@Override
	public Object getCacheObject(String type, Long key){
		LinkedHashMap<Long, Object> map = dataMap.get(type);
		if (map == null || map.isEmpty()){
			loadByType(type);
		}
		map = dataMap.get(type);
		if (map == null || map.isEmpty()){
			return null;
		}
		return map.get(key);
	}
	
	@Override
	public List<Object> getCacheCollection(String type) {
		LinkedHashMap<Long, Object> map = dataMap.get(type);
		if (map == null || map.isEmpty()){
			loadByType(type);
		}
		map = dataMap.get(type);
		if (map == null || map.isEmpty()){
			return null;
		}
		List<Object> mlist = new ArrayList<Object>();
		mlist.addAll(map.values());
		return mlist;
	}
	
	@Override
	public List<Object> getAllMenus() {
		LinkedHashMap<Long, Object> map = dataMap.get(MENU_TYPE);
		if (map == null || map.isEmpty()){
			loadMenu();
			map = dataMap.get(MENU_TYPE);
		}
		if (map == null || map.isEmpty()){
			return null;
		}
		List<Object> mlist = new ArrayList<Object>();
		mlist.addAll(map.values());
		return mlist;
	}
	
	/**
	 * 用list里的数据替换原有的dataMap内的数据
	 * @param type
	 * @param list
	 * @param getKeyMethodName
	 */
	private void loadData(String type, List<?> list, String getKeyMethodName){
		if (list == null || list.isEmpty()){
			return;
		}
		
		LinkedHashMap<Long, Object> linkMap = dataMap.get(type);
		if (linkMap == null){
			linkMap = new LinkedHashMap<Long, Object>();
			dataMap.put(type, linkMap);
		} else if (linkMap.size() > 0) {
			linkMap.clear();
		}
		
		for (Object obj : list){
			try {
				Method method = obj.getClass().getMethod(getKeyMethodName);
				Long id = (Long) method.invoke(obj);
				linkMap.put(id, obj);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

	private void loadByType(String type) {
		if (MENU_TYPE.equals(type)){				
			loadMenu();
		} else if (GALLERY_TYPE.equals(type)){				
			loadGallery();
		} else if (ICON_TYPE.equals(type)){				
			loadIcon();
		}
	}

}
