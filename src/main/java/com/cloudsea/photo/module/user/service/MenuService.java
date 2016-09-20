package com.cloudsea.photo.module.user.service;

import java.util.List;

import com.cloudsea.common.dto.Result;
import com.cloudsea.photo.dto.MenuDto;

public interface MenuService {

	List<MenuDto> getAllMenus();

	Result updateMenuItem(MenuDto menuDto);

	Result deleteMenuItem(Integer id);

	Result addMenuItem(MenuDto menuDto);
}
