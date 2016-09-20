package com.cloudsea.photo.module.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudsea.common.dto.Result;
import com.cloudsea.photo.datacache.CacheService;
import com.cloudsea.photo.dto.MenuDto;
import com.cloudsea.photo.entity.Gallery;
import com.cloudsea.photo.entity.MenuItem;
import com.cloudsea.photo.module.pic.dao.GalleryDao;
import com.cloudsea.photo.module.pic.dao.GalleryMapper;
import com.cloudsea.photo.module.user.dao.MenuItemDao;
import com.cloudsea.photo.module.user.dao.MenuItemMapper;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuItemDao MenuDao;
	
	@Autowired
	MenuItemMapper menuItemMapper;
	
	@Autowired
	GalleryMapper galleryMapper;
	
	@Autowired
	GalleryDao galleryDao;
	
	@Autowired 
	CacheService cacheService;
	
	@Override
	public List<MenuDto> getAllMenus() {
		List<MenuDto> dtoList = new ArrayList<MenuDto>();
		List<MenuItem> list = MenuDao.getAllMenus();
		if (list != null && !list.isEmpty()){			
			for (MenuItem item : list){
				try {
					MenuDto menuDto = new MenuDto();
					BeanUtils.copyProperties(item, menuDto);
					List<Gallery> glist = galleryDao.getGalleryByMenuId(item.getId());
					if (glist != null && glist.size() == 1){
						Gallery gallery = glist.get(0);
						menuDto.setGallery(gallery);
					}
					dtoList.add(menuDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return dtoList;
	}

	@Override
	@Transactional
	public Result updateMenuItem(MenuDto menuDto) {
		try {
			MenuItem menuItem = new MenuItem();
			BeanUtils.copyProperties(menuDto, menuItem);
			menuItemMapper.updateByPrimaryKeySelective(menuItem);
			
			Gallery gallery = menuDto.getGallery();
			galleryMapper.updateByPrimaryKeySelective(gallery);
			
			cacheService.loadMenu();
			return Result.succ("更新目录成功");
			
		} catch (Exception e) {
			return Result.fail("更新目录失败");
		}
	}

	@Override
	public Result deleteMenuItem(Integer id) {
		int num = menuItemMapper.deleteByPrimaryKey(id);
		if (num > 0){
			galleryDao.deleteByMenuItemId(id);
			cacheService.loadMenu();
			return Result.succ("删除目录成功");
		} else {
			return Result.fail("删除目录失败");
		}
	}

	@Override
	@Transactional
	public Result addMenuItem(MenuDto menuDto) {
		try {
			MenuItem menuItem = new MenuItem();
			BeanUtils.copyProperties(menuDto, menuItem);
			Date now = new Date();
			menuItem.setCreateTime(now);
			menuItem.setUpdateTime(now);
			menuItem.setEnable("0");
			MenuDao.insertSelectiveAndGetId(menuItem);
			
			Gallery gallery = menuDto.getGallery();
			gallery.setCreateTime(now);
			gallery.setMenuId(menuItem.getId());
			galleryMapper.insertSelective(gallery);
			
			cacheService.loadMenu();
			cacheService.loadGallery();
			return Result.succ("添加目录成功");
		} catch (BeansException e) {
			System.out.println(e.getMessage());
			return Result.succ("添加目录失败");
		}
	}

}
