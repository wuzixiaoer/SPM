package com.buptsse.spm.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buptsse.spm.domain.User;
import com.buptsse.spm.service.IUserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author BUPT-TC
 * @date 2015年10月21日 上午9:22:50
 * @description 实现登录页面逻辑
 * @modify	BUPT-TC 
 * @modifyDate 2015年10月24日 上午11:30:50
 */

public class LoginAction extends ActionSupport
{
	private static Logger LOG = LoggerFactory.getLogger(LoginAction.class);
	private User user;
	public static List<User> loginUsers = new ArrayList();
	
	@Resource
	private IUserService userService;
	
	public boolean isLogin(String userName){
           for(int i=0;i<loginUsers.size();i++){
        	    User existUser = loginUsers.get(i);
        	    if(existUser.getUserName().equals(userName)){
        	        return true;
        	    }
           }
           return false;
	}
	/**
	 * 
	 * @description 实现登入功能 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String login()
	{ 
		LOG.error("username:" + user.getUserName());
		
		try
		{
			User tempuser = new User();
			tempuser = userService.findUser(user.getUserName(),user.getPassword());
		    boolean result = isLogin(tempuser.getUserName());
		    if(result){
		    	return "fail";
		    }
		    else{
				Map session = (Map) ActionContext.getContext().getSession();
				session.put("user", tempuser);
				loginUsers.add(tempuser);
	            return "success";
		    }			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		LOG.error("开始保存数据");
		return "success";
	}
	
	@SuppressWarnings("rawtypes")
	public String logout()
	{
		System.out.println("*********进入登出功能**********");
		try
		{
			Map session = (Map) ActionContext.getContext().getSession();
			String userName = ((User)session.get("user")).getUserName();
			for(int i=0; i<loginUsers.size(); i++){
				User existUser = loginUsers.get(i);
				if(existUser.getUserName().equals(userName)){
					loginUsers.remove(i);
				}
			}
			session.clear();
			return "success";			
		} catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}	
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
