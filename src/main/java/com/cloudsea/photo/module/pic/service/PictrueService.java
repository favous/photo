package com.cloudsea.photo.module.pic.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cloudsea.common.dto.DataTableReqData;
import com.cloudsea.common.dto.Result;
import com.cloudsea.common.dto.DataResult;
import com.cloudsea.photo.entity.Gallery;
import com.cloudsea.photo.entity.Icon;

public interface PictrueService {

	DataResult<Gallery> getGalleryPictrues(Long menuId, Integer page);

	DataResult<List<Result>> uploadPic(MultipartFile[] files, Long galleryId, Long menuId);

	DataResult<List<Icon>> getIconDatas(DataTableReqData<Icon> dataTableReqData);

	Result updateIcon(Icon icon);

	Result countIconBymenuId(Long menuId);

	Result deleteIcon(Integer id);

	List<Icon> getAllIcons();

	List<Gallery> getAllGallerys();

}
