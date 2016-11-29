package org.tangjl.demo.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tangjl.demo.shiro.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tangjialin on 2016-01-09 0009.
 */
@Controller
@RequestMapping
public class IndexController {
	@RequestMapping("/login/login")
	@ResponseBody
	public String index(User user, HttpServletRequest request, HttpServletResponse response) {
		String data = "{\"status\":true}";
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
			try {
//				token.setRememberMe(true);
				subject.login(token);
				// 在web应用中,subject.getSession()和request.getSession()作用一样
				// session也一样,放入subject.getSession()和request.getSession()中的对象是共享的
				// 但是为了兼容性,推荐使用subject.getSession()
				org.apache.shiro.session.Session session = subject.getSession(false);
				user.setUsername("admin".equals(user.getAccount()) ? "系统管理员" : "访客");
				session.setAttribute("USER", user);
//				HttpSession httpSession = request.getSession(false);
//				System.out.println(httpSession.getAttribute("USER"));
			} catch (Exception e) {
				data = "{\"status\":false}";
				e.printStackTrace();
			}
		}
		return data;
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "index";
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) { return "redirect:/index"; }
		request.getServletContext().setAttribute("CONTEXT_PATH", request.getContextPath());
		return "login/login";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		request.setAttribute("userName", subject.getPrincipal());
		if (subject.isAuthenticated()) {
			subject.logout();
		}
		return "login/logout";
	}

	@RequestMapping("/error")
	public String error(HttpServletRequest request, HttpServletResponse response) {
		return "error/error";
	}

	@RequestMapping("/admin")
	public String admin(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("adminView", true);
		return "index";
	}

	@RequestMapping("/noAuth")
	public String noAuth(HttpServletRequest request, HttpServletResponse response) {
		return "index";
	}

}
