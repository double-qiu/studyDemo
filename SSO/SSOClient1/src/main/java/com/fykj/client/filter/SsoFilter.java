/**  
 * Project Name:SSOClient1
 * File Name:SsoClientServlet.java  
 * Package Name:com.fykj.client.servlet
 * Date:2016年12月14日16:42:34
 * Copyright (c) 2016, FengYunTec All Rights Reserved.  
 */
package com.fykj.client.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;*/

/**
 * ClassName: SsoFilter  
 * 拦截filter
 * @author DOUBLE
 * @version
 */
public class SsoFilter implements Filter {
    private FilterConfig filterConfig = null;
    private String cookieName="fykj";
    private String SSOServiceURL= "http://127.0.0.1:8080/SSOAuth";
    private String SSOLoginPage= "http://127.0.0.1:8080/SSOAuth/login.jsp";
   
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        cookieName = filterConfig.getInitParameter("cookieName");
        SSOServiceURL = filterConfig.getInitParameter("SSOServiceURL");
        SSOLoginPage = filterConfig.getInitParameter("SSOLoginPage");
    }

    /**
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     * @author DOUBLE
     */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;
	        String result="failed";
	        String url = request.getRequestURL().toString();
	        String qstring = request.getQueryString();
	        if (qstring == null) qstring ="";
	        //检查http请求的head是否有需要的cookie
	        String cookieValue ="";
	        Cookie[] diskCookies = request.getCookies();
	        if (diskCookies != null) {
	            for (int i = 0; i < diskCookies.length; i++) {
	                if(diskCookies[i].getName().equals(cookieName)){
	                    cookieValue = diskCookies[i].getValue();
	                    //如果找到了相应的cookie则效验其有效性
	                    result = SSOService(cookieValue);
	                }
	            }
	        }
	      //效验失败或没有找到cookie，则需要登录
	        if (result.equals("failed")) { 
	            response.sendRedirect(SSOLoginPage+"?goto="+url);
	        } else if (qstring.indexOf("logout") > 1) {
	        	//logout服务
	            logoutService(cookieValue);
	            response.sendRedirect(SSOLoginPage+"?goto="+url);
	        } else {
	        	//效验成功
	            request.setAttribute("SSOUser",result);
	            Throwable problem = null;
	            try {
	                chain.doFilter(req, res);
	            } catch(Throwable t) {
	                problem = t;
	                t.printStackTrace();
	            }      
	            if (problem != null) {
	                if (problem instanceof ServletException) throw (ServletException)problem;
	                if (problem instanceof IOException) throw (IOException)problem;
	                sendProcessingError(problem, res);
	            }
	        }  
		
	}

	private void sendProcessingError(Throwable problem, ServletResponse res) {
		// TODO Auto-generated method stub
		
	}

	private String SSOService(String cookievalue) throws IOException {
		return cookievalue;
		/* String authAction = "?action=authcookie&cookiename=";
        HttpClient httpclient = new HttpClient();
        GetMethod httpget = new GetMethod(SSOServiceURL+authAction+cookievalue);
        try { 
            httpclient.executeMethod(httpget);
            String result = httpget.getResponseBodyAsString();
            return result;
        } finally {
            httpget.releaseConnection();
        }*/
    }
   
    private void logoutService(String cookievalue) throws IOException {
    	/*  String authAction = "?action=logout&cookiename=";
        HttpClient httpclient = new HttpClient();
        GetMethod httpget = new GetMethod(SSOServiceURL+authAction+cookievalue);
        try {
            httpclient.executeMethod(httpget);
            httpget.getResponseBodyAsString();
        } finally {
            httpget.releaseConnection();
        }*/
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	} 

}
