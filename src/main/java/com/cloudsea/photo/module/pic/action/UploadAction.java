package com.cloudsea.photo.module.pic.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudsea.common.dto.DataTableReqData;
import com.cloudsea.common.dto.Result;
import com.cloudsea.common.dto.DataResult;
import com.cloudsea.photo.entity.Gallery;
import com.cloudsea.photo.entity.Icon;
import com.cloudsea.photo.frame.BaseAction;
import com.cloudsea.photo.module.pic.service.PictrueService;
import com.cloudsea.photo.utils.commonutils.Json;

@Controller
@RequestMapping("/pic")
public class UploadAction extends BaseAction{
    
    @Autowired
    PictrueService pictrueService;
    
    @RequestMapping(value="/toManagePhoto", method=RequestMethod.GET)
    public String toManagePhoto(){
    	return "html/photoMgr.html";
    }

	@RequestMapping(value = "/getPicByMenuId", method = RequestMethod.GET)
	public void getPicByMenuId(Long menuId, int page, HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		DataResult<Gallery> result = pictrueService.getGalleryPictrues(menuId, page);
		String str = Json.toJson(result);
		resp.setContentType("text/html; charset=utf-8");
		String jsonpCallback = req.getParameter("callback");//客户端请求参数  
		resp.getWriter().print(jsonpCallback + "(" + str + ")");
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void uploadPic(@RequestParam("files") MultipartFile[] files, Long galleryId, Long menuId,
			HttpServletRequest request, HttpServletResponse resp, ModelMap model)
			throws IllegalStateException, IOException {
		
		DataResult<List<Result>> result = pictrueService.uploadPic(files, galleryId, menuId);
		resp.setContentType("text/html; charset=utf-8");
		String str = Json.toJson(result);
		resp.getWriter().print(str);
	}

	@RequestMapping(value = "/getIconDatas", method = RequestMethod.GET)
	public void getIconDatas(int start, int length, Long menuId, String enable, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		try {
			DataTableReqData<Icon> dataTableReqData = new DataTableReqData<Icon>();
			Icon icon = new Icon();
			icon.setMenuId(menuId);
			icon.setEnable(enable);
			dataTableReqData.setStart(start);
			dataTableReqData.setLength(length);
			dataTableReqData.setObject(icon);
			DataResult<List<Icon>> result = pictrueService.getIconDatas(dataTableReqData);
			String str = Json.toJson(result);
			resp.setContentType("text/html; charset=utf-8");
			resp.getWriter().print(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/updateIcon", method=RequestMethod.GET)
    public void updateIcon(Icon icon, HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Result result = pictrueService.updateIcon(icon);
		String str = Json.toJson(result);
		resp.setContentType("text/html; charset=utf-8");
		String jsonpCallback = req.getParameter("callback");//客户端请求参数  
		resp.getWriter().print(jsonpCallback + "(" + str + ")");
    }

	@RequestMapping(value="/deleteIcon", method=RequestMethod.GET)
    public void deleteIcon(Integer id, HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Result result = pictrueService.deleteIcon(id);
		String str = Json.toJson(result);
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().print(str);
    }
	


	@RequestMapping(value="/countIconBymenuId", method=RequestMethod.GET)
    public void countIconBymenuId(Long menuId, HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Result result = pictrueService.countIconBymenuId(menuId);
		String str = Json.toJson(result);
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().print(str);
    }
}
