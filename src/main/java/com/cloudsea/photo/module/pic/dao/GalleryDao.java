package com.cloudsea.photo.module.pic.dao;

import java.util.List;

import com.cloudsea.photo.entity.Gallery;

public interface GalleryDao {

	List<Gallery> getGalleryByMenuId(Long menuId);
	
    int insertSelectiveAndGetId(Gallery gallery);

	int deleteByMenuItemId(Integer id);

	List<Gallery> getAllGallerys();

}
