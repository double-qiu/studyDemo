package org.tangjl.demo.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * @author Garfield on 2016-01-09 0009.
 */
public class UserRealm extends AuthorizingRealm {
	public UserRealm() {
		System.out.println(getClass());
	}

	/**
	 * 权限操作
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String key = "AUTHORIZATION_INFO";
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(false);
		SimpleAuthorizationInfo authorizationInfo = (SimpleAuthorizationInfo) session.getAttribute(key);
		if (!subject.isAuthenticated() || authorizationInfo == null) {
			String username = (String) principals.getPrimaryPrincipal();
			if (!"admin".equals(username)) return null;
			System.out.println(username + ":" + principals.getRealmNames());
			authorizationInfo = new SimpleAuthorizationInfo();
			authorizationInfo.addRole("admin"); // 添加角色名
			authorizationInfo.addStringPermission("INSERT"); // 添加权限名

//			/* 添加多个角色名 */
//			java.util.Set<java.lang.String> roles = null;
//			authorizationInfo.addRoles(roles);
//			/* 添加多个权限名 */
//			java.util.Set<java.lang.String> permissions = null;
//			authorizationInfo.addStringPermissions(permissions); // 添加权限名
			session.setAttribute(key, authorizationInfo);
		}
		return authorizationInfo;
	}

	/**
	 * 身份验证操作
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//		String account = (String) token.getPrincipal(); // 这种方式获取用户名也可以
		String account = upToken.getUsername();
		String password = String.valueOf(upToken.getPassword());
		if (account == null || password == null) { throw new AccountException("用户不存在"); }
		if (!account.equals(password)) { throw new AuthenticationException("用户名或密码错误"); }
		// SimpleAuthenticationInfo里面会比较new UsernamePasswordToken("account", "password");的值,不相同会抛出异常
		return new SimpleAuthenticationInfo(account, password, getClass().getName());
	}
}
