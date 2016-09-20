package com.cloudsea.photo.module.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudsea.common.entity.User;
import com.cloudsea.photo.module.user.service.LoginService;

@Controller
@RequestMapping("/user")
public class LoginAction {
	
	@Autowired 
	LoginService loginService;
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
    public String admin(User user, HttpServletRequest request){
		return "html/login.html";
    }
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(User user, HttpServletRequest request){
		if (loginService.verify(user)){
			request.getSession().setAttribute("currentUser", user);
			return "html/photoMgr.html";
		} else {
	        return "html/error.html";
		}
    }
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpServletRequest request){
		request.getSession().removeAttribute("currentUser");
		return "html/login.html";
    }
	
	@RequestMapping(value="/loginStatus", method=RequestMethod.GET)
    public void loginStatus(HttpServletRequest request, HttpServletResponse resp) throws IOException{
		Object currentUser = request.getSession().getAttribute("currentUser");
		if (currentUser == null){
			resp.getWriter().print("false");
		} else {
			resp.getWriter().print("true");
		}
    }
	
	@RequestMapping(value="/login2", method=RequestMethod.GET)
    public String login2(){
		
        return "html/error.html";
    }
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
    public String main(){
        return "html/main.html";
    }
	
	@RequestMapping(value="/main2", method=RequestMethod.GET)
    public String main2(){
        return "html/main2.html";
    }


}
