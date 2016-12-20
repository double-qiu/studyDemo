/**  
 * Project Name:SSOClient2
 * File Name:SsoClientServlet.java  
 * Package Name:com.fykj.client.servlet
 * Date:2016年12月14日16:42:34
 * Copyright (c) 2016, FengYunTec All Rights Reserved.  
 */
package com.fykj.client.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: SsoClientServlet  
 * 客户端模拟浏览器登陆行为
 * @author DOUBLE
 * @version
 */
@SuppressWarnings("serial")
public class SsoClientServlet extends HttpServlet  {
	
	/**
	 * @author DOUBLE
	 */
	public void destroy() {
		super.destroy();
	}
	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @author DOUBLE
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @author DOUBLE
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String SSOLoginPage =request.getSession().getServletContext().getInitParameter("SSOLoginPage");

		String CookieName =request.getSession().getServletContext().getInitParameter("CookieName");
		CookieName =CookieName.toLowerCase().trim();
		Cookie[] cookies=   request.getCookies();
		Cookie loginCookie =null;
		String cookiename ="";
		//TODO 目前只验证了cookieName ,后续根据业务新增
		if(cookies!=null){
		    for(Cookie cookie:cookies){
		        cookiename =cookie.getName().trim().toLowerCase();
		       if(CookieName.equals(cookiename)){
		           loginCookie =cookie;
		           response.sendRedirect("index.jsp");
		           break;
		       }
		    }
		}
		if(loginCookie==null){
		    String url =request.getRequestURL().toString();
		    response.sendRedirect(SSOLoginPage+"?goto="+url);
		}
	}
}
