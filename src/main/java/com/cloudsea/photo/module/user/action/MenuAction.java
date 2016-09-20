package com.cloudsea.photo.module.user.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudsea.common.dto.DataResult;
import com.cloudsea.common.dto.Result;
import com.cloudsea.photo.datacache.CacheService;
import com.cloudsea.photo.dto.MenuDto;
import com.cloudsea.photo.module.user.service.MenuService;
import com.cloudsea.photo.utils.commonutils.Json;

@Controller
@RequestMapping("/menu")
public class MenuAction {
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	CacheService cacheService;
	
	@RequestMapping(value="/toManageMenu", method=RequestMethod.GET)
    public String toManageMenu(){
        return "html/menuMgr.html";
    }
	
	@RequestMapping(value = "/getMenuItems", method = RequestMethod.GET)
	public void getMenuItems(HttpServletRequest req, Integer userNo, HttpServletResponse resp) throws IOException {
		List<?> list = cacheService.getAllMenus();
		DataResult<List<?>> result = DataResult.success(list);
		String str = Json.toJson(result);
		resp.setContentType("text/html; charset=utf-8");
		String jsonpCallback = req.getParameter("callback");//客户端请求参数  
		resp.getWriter().print(jsonpCallback + "(" + str + ")");
	}

	@RequestMapping(value = "/getMenuItemList", method = RequestMethod.GET)
	public void getMenuItemList(HttpServletRequest req, Integer userNo, HttpServletResponse resp) throws IOException {
		List<?> list = cacheService.getAllMenus();
		DataResult<List<?>> result = DataResult.success(list);
		String str = Json.toJson(result);
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().print(str);
	}

	@RequestMapping(value = "/loadSubPage", method = RequestMethod.GET)
	public String loadSubPage(String url) throws IOException {
		if (url != null && !url.equals("")){
			return url;			
		} else {
	        return "html/error.html";
		}
	}
	
	@RequestMapping(value = "/addMenuItem", method = RequestMethod.GET)
	public void addMenuItem(HttpServletRequest req, MenuDto menuDto, Integer userNo, HttpServletResponse resp)
			throws IOException {
		menuDto.setName(URLDecoder.decode(menuDto.getName(), "UTF-8"));
		Result result = menuService.addMenuItem(menuDto);
		String str = Json.toJson(result);
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().print(str);
	}

	@RequestMapping(value = "/updateMenuItem", method = RequestMethod.GET)
	public void updateMenuItem(HttpServletRequest req, MenuDto menuDto, Integer userNo, HttpServletResponse resp)
			throws IOException {
		menuDto.setName(URLDecoder.decode(menuDto.getName(), "UTF-8"));
		Result result = menuService.updateMenuItem(menuDto);
		String str = Json.toJson(result);
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().print(str);
	}

	@RequestMapping(value = "/deleteMenuItem", method = RequestMethod.GET)
	public void deleteMenuItem(HttpServletRequest req, Integer id, Integer userNo, HttpServletResponse resp)
			throws IOException {
		
		Result result = menuService.deleteMenuItem(id);
		String str = Json.toJson(result);
		resp.setContentType("text/html; charset=utf-8");
		resp.getWriter().print(str);
	}

}
