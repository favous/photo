package com.cloudsea.photo.module.user.dao;

import com.cloudsea.photo.entity.MenuItem;

public interface MenuItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuItem record);

    int insertSelective(MenuItem record);

    MenuItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuItem record);

    int updateByPrimaryKey(MenuItem record);
}