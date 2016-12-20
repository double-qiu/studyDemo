/**  
 * Project Name:SSOAuth
 * File Name:AuthServlet.java  
 * Package Name:com.fykj.auth.servlet
 * Date:2016年12月14日16:22:19
 * Copyright (c) 2016, FengYunTec All Rights Reserved.  
 */
package com.fykj.auth.servlet;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: AuthServlet  
 * 模拟验证服务操作
 * @author DOUBLE
 * @version
 */
@SuppressWarnings("serial")
public class AuthServlet extends HttpServlet {
	
	private static ConcurrentMap<String, String> accounts;
	String CookieName;
	String DomainName;
	
	/**
	 * Creates a new instance of AuthServlet.  
	 */
	public AuthServlet() {
		super();
	}
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

		DomainName = request.getSession().getServletContext().getInitParameter("DomainName");
		CookieName = request.getSession().getServletContext().getInitParameter("CookieName");
		String location = request.getContextPath() + "/login.jsp";
		String username = request.getParameter("username");
		String userpassword = request.getParameter("userpassword");
		String key = accounts.get(username);
		if (key == null) {
			response.sendRedirect(location);
		} else {
			if (key.equals(userpassword)) {
				String gotoURL = request.getParameter("goto");
				String sessionId = request.getSession().getId();
				Cookie cookie = new Cookie(CookieName, sessionId);
				cookie.setMaxAge(200);
				cookie.setPath("/");
				response.addCookie(cookie);
				if (gotoURL != null) {
					response.sendRedirect(gotoURL);
				} else {
					response.sendRedirect(location);
				}
			} else {
				response.sendRedirect(location);
			}
		}
	}
	/**
	 * @param config
	 * @throws ServletException
	 * @author DOUBLE
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		accounts = new ConcurrentHashMap<String, String>();
		accounts.put("admin", "admin");//默认验证用户
	}
}
