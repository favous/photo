package com.cloudsea.photo.module.user.dao;

import java.util.List;

import com.cloudsea.photo.entity.MenuItem;

public interface MenuItemDao {

	List<MenuItem> getAllMenus();

    int insertSelectiveAndGetId(MenuItem menuItem);
}
