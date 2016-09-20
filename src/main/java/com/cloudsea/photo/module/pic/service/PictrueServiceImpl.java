package com.cloudsea.photo.module.pic.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;   
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudsea.common.ImageResizeUtil;
import com.cloudsea.common.dto.DataTableReqData;
import com.cloudsea.common.dto.Result;
import com.cloudsea.common.dto.DataResult;
import com.cloudsea.photo.datacache.CacheService;
import com.cloudsea.photo.datacache.CacheServiceImpl;
import com.cloudsea.photo.entity.Gallery;
import com.cloudsea.photo.entity.Icon;
import com.cloudsea.photo.module.pic.dao.GalleryDao;
import com.cloudsea.photo.module.pic.dao.IconDao;
import com.cloudsea.photo.module.pic.dao.IconMapper;
import com.cloudsea.photo.utils.commonutils.FileUtil;
import com.cloudsea.photo.utils.commonutils.MyDateUtil;

@Service
public class PictrueServiceImpl implements PictrueService {
	
	private Logger logger = Logger.getLogger(this.getClass());

    @Value("${PIC_DISK_PRE_PATH}")
	private String PIC_DISK_PRE_PATH;

    @Value("${PRIC_WEB_PRE_PATH}")
	private String PRIC_WEB_PRE_PATH;

    @Value("${REDUCE_PHOTO_WIDTH}")
	private String RESIZE_WIDTH;
    
	@Autowired 
	IconDao iconDao;
	
	@Autowired 
	IconMapper iconMapper;

	@Autowired 
	GalleryDao galleryDao;
	
	@Autowired 
	CacheService cacheService;

	@Override
	public List<Icon> getAllIcons() {
		List<Icon> list = iconDao.getAllIcons();
		combinSrc(list);
		return list;
	}

	@Override
	public List<Gallery> getAllGallerys() {
		List<Gallery> list = galleryDao.getAllGallerys();
		return list;
	}
	
	@Override
	public DataResult<Gallery> getGalleryPictrues(Long menuId, Integer page) {
		logger.info("进入方法：getGalleryPictrues");
		if (menuId == null){
			DataResult.failure("未知菜单");
		}
//		List<Gallery> galleryList = galleryDao.getGalleryByMenuId(menuId);
//		
//		if (galleryList == null || galleryList.size() != 1){
//			logger.debug("没有找到此菜单下的图片,menuId=" + menuId);
//			DataResult.fail("没有找到此菜单下的图片");
//		}
//		
//		Gallery gallery = galleryList.get(0);
		
		//从缓存中取gallery
		Gallery gallery = (Gallery) cacheService.getCacheObject(CacheServiceImpl.GALLERY_TYPE, menuId);
		if (gallery == null){
			logger.debug("没有找到此菜单下的图片,menuId=" + menuId);
			DataResult.fail("没有找到此菜单下的图片");
		}
		
//		DataTableReqData<Gallery> dataTableReqData = new DataTableReqData<Gallery>();
//		Integer loadNum = gallery.getLoadNum() == null ? 50 : gallery.getLoadNum();
//		dataTableReqData.setStart((page - 1) * loadNum);
//		dataTableReqData.setLength(loadNum);
//		dataTableReqData.setObject(gallery);
//		List<Icon> iconList = iconDao.getIconByPage(dataTableReqData);
		
		//从缓存中取icon
		List<Object> list = cacheService.getCacheCollection(CacheServiceImpl.ICON_TYPE);
		List<Icon> iconList = new ArrayList<Icon>();
		List<Icon> returnList = new ArrayList<Icon>();
		
		//取出对面菜单的所有图片
		for (int i = 0; i < list.size(); i++){
			Icon icon = (Icon) list.get(i);
			if (menuId == icon.getMenuId()){
				iconList.add((Icon) list.get(i));
			}
		}
		
		//按上传时间从近到远排序
		Collections.sort(iconList, new Comparator<Icon>(){
			public int compare(Icon o1, Icon o2) {
				if (o1.getCreateTime() == null || o2.getCreateTime() == null){
					return 0;
				} else if (o1.getCreateTime().after(o2.getCreateTime())){
					return 1;
				} else {					
					return -1;
				}
			}
		});
		
		//取start到end区间的图片
		Integer loadNum = gallery.getLoadNum() == null ? 50 : gallery.getLoadNum();
		int start = (page - 1) * loadNum;//元素包含
		int end = page * loadNum;//元素不包含
		for (int i = start; i < end && i < iconList.size(); i++){
			returnList.add((Icon) iconList.get(i));
		}
		
		gallery.setIconList(returnList);
		
		return DataResult.success(gallery);
	}

	@Override
	public DataResult<List<Result>> uploadPic(MultipartFile[] files, Long galleryId, Long menuId) {
		
		DataResult<List<Result>> result = null;
		
		if (files.length == 0) {
			result = DataResult.failure("上传失败");
			
		} else {
			
			List<Gallery> glist = galleryDao.getGalleryByMenuId(menuId);
			if (glist == null || glist.size() != 1) {
				result = DataResult.failure("上传失败");
			}
			
			List<Result> resultList = new ArrayList<Result>();
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String fileName = file.getOriginalFilename();
					try {
						//fileName = ChineseUtil.toPinyin(fileName);
						fileName = MyDateUtil.formatDateToString(new Date(), "yyyyMMdd") + "_" + fileName;
						
						File dirFile = new File(PIC_DISK_PRE_PATH + "photo/");
						if (!dirFile.exists()){
							dirFile.mkdirs();
						}
						
						int index = 1;
						File destFile = null;
						while ((destFile = new File(PIC_DISK_PRE_PATH + "photo/", fileName)).exists()){
							int ind = fileName.lastIndexOf(".");
							fileName = fileName.substring(0, ind) + "(" + index +")" + fileName.substring(ind);
							index++;
						}

						//上传原图
						file.transferTo(destFile);
						
						//压缩图片
						String pictureSrc = PIC_DISK_PRE_PATH + "photo/" + fileName;
						String iconSrc = PIC_DISK_PRE_PATH + "icon/" + fileName;
						ImageResizeUtil.resizeByWithToFile(new FileInputStream(pictureSrc), Integer.parseInt(RESIZE_WIDTH), iconSrc);
						
						Icon icon = new Icon();
						icon.setPictureSrc("photo/" + fileName);
						icon.setSrc("icon/" + fileName);
						icon.setMenuId(menuId);
						icon.setEnable("0");
						icon.setCreateTime(new Date());
						icon.setUpdateTime(new Date());
						icon.setGalleryId(glist.get(0).getId());
						iconMapper.insertSelective(icon);
						
						Result re = Result.succ(fileName, "上传成功");
						resultList.add(re);
						
					} catch (Exception e) {
						logger.error("上传失败，文件名为：" + fileName);
						Result re = Result.fail("上传失败，文件名为：" + fileName);
						resultList.add(re);
						e.printStackTrace();
					}
				}
			}
			result = DataResult.success(resultList, "上传成功");
		}
		
		cacheService.loadIcon();
		return result;
	}

	@Override
	public DataResult<List<Icon>> getIconDatas(DataTableReqData<Icon> dataTableReqData) {
		List<Icon> list = iconDao.getIconByPage(dataTableReqData);
		combinSrc(list);
		return DataResult.success(list);
	}

	@Override
	public Result updateIcon(Icon icon) {
		int num = iconMapper.updateByPrimaryKeySelective(icon);
		if (num > 0){
			cacheService.loadIcon();
			return Result.succ("更新图片成功");
		} else {
			return Result.fail("更新图片失败");
		}
	}

	@Override
	public Result countIconBymenuId(Long menuId) {
		int num = iconDao.countIconBymenuId(menuId);
		return Result.succ(num);
	}

	@Override
	public Result deleteIcon(Integer id) {
		Icon icon = iconMapper.selectByPrimaryKey(id);
		if (icon == null){
			return Result.fail("删除图片失败");
		}
		FileUtil.deleteFile(new File(PIC_DISK_PRE_PATH + icon.getPictureSrc()));
		FileUtil.deleteFile(new File(PIC_DISK_PRE_PATH + icon.getSrc()));
		iconMapper.deleteByPrimaryKey(id);
		cacheService.loadIcon();
		return Result.succ("删除图片成功");
	}

	private void combinSrc(List<Icon> iconList) {
		if (iconList != null && !iconList.isEmpty()){
			for (Icon icon :iconList){
				icon.setPictureSrc(PRIC_WEB_PRE_PATH + icon.getPictureSrc());
				icon.setSrc(PRIC_WEB_PRE_PATH + icon.getSrc());
			}
		}
	}

}
