package com.cloudsea.photo.module.pic.dao;

import com.cloudsea.photo.entity.Icon;

public interface IconMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Icon record);

    int insertSelective(Icon record);

    Icon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Icon record);

    int updateByPrimaryKey(Icon record);
}