package com.cloudsea.photo.module.pic.dao;

import java.util.List;

import com.cloudsea.common.dto.DataTableReqData;
import com.cloudsea.photo.entity.Icon;

public interface IconDao {

	List<Icon> getIconByMenuId(Long id);

	Integer insert(Icon icon);

	List<Icon> getIconByPage(DataTableReqData<?> dataTableReqData);

	int countIconBymenuId(Long menuId);

	List<Icon> getAllIcons();

}
