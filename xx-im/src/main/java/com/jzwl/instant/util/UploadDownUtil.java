package com.jzwl.instant.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jzwl.base.service.MongoService;
import com.jzwl.instant.pojo.ChatFileBean;
import com.mongodb.DBObject;

/**
 * <code>UploadDownUtil.java<code/>
 * <p>
 * 功能：文件上传下载工具类
 * <p>
 * Copyright 神州锐达 2014 All right reserved.
 * 
 * @Author: Brain.Hou,2014年3月7日上午9:16:18
 */
public class UploadDownUtil {

	public static Logger log = LoggerFactory.getLogger(UploadDownUtil.class);

	/**
	 * 文件上传
	 * 
	 * 
	 * @param request
	 * @param username
	 * @param fileName
	 * @param fileType
	 * @return file upload db id
	 */
	public static String fileUpload(MongoService mongoService,
			HttpServletRequest request, String username, String fileName,
			String fileType) {
		String filename = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

			// 获得文件：
			MultipartFile file = multipartRequest.getFile("file");
			if (null != file) {
				System.out.println(file);

				// 获得输入流：
				InputStream input = file.getInputStream();

				BufferedInputStream in = new BufferedInputStream(input);

				String AppPath = getAppPath(request);

				File serverFileDir = new File(AppPath);
				if (!serverFileDir.exists()) {
					serverFileDir.mkdir();
				}

				filename = username + "_" + fileName;
				String[] temArr = fileName.split("\\.");
				// String expandFlag = temArr[temArr.length - 1];
				String alreadyFileId = temArr[0];

				FileOutputStream fo = new FileOutputStream(AppPath + filename);
				log.info("will upload" + AppPath + filename);
				BufferedOutputStream out = new BufferedOutputStream(fo);

				byte[] buf = new byte[4 * 1024];
				int len = in.read(buf);// 读文件，将读到的内容放入到buf数组中，返回的是读到的长度
				while (len != -1) {
					out.write(buf, 0, len);
					len = in.read(buf);
				}
				out.close();
				fo.close();
				in.close();
				input.close();

				ChatFileBean fileBean = new ChatFileBean();

				if (null != alreadyFileId && alreadyFileId.length() > 0) {
					fileBean.setId(alreadyFileId);
				} else {
					fileBean.setId(Util.getUUID());
				}
				fileBean.setFileName(filename);
				fileBean.setRealPath(AppPath + filename);
				fileBean.setUploadDate(Util.getCurrDate());
				fileBean.setUploadUserName(username);

				mongoService.save(IC.mongodb_fileinfo, fileBean);

				return fileBean.getId();

			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// user username and filename download the file
	public static boolean fileDownLoad4name(HttpServletResponse response,
			HttpServletRequest request, String username, String fileName) {
		boolean flag = false;
		try {
			String AppPath = getAppPath(request);
			System.out.println(AppPath);
			log.info("down load file" + AppPath);

			File file = new File(AppPath + username + "_" + fileName);
			if (null != file) {
				InputStream in = new FileInputStream(file);
				byte[] buf = new byte[4 * 1024];
				int len = in.read(buf);// 读文件，将读到的内容放入到buf数组中，返回的是读到的长度
				OutputStream out = response.getOutputStream();
				while (len != -1) {
					out.write(buf, 0, len);
					len = in.read(buf);
				}
				out.flush();
				out.close();
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
	}

	// // user uploadFileId download the file
	public static boolean fileDownLoad4fid(MongoService mongoService,
			HttpServletResponse response, HttpServletRequest request,
			String username, String uploadFileId) {
		boolean flag = false;
		try {

			if (null != uploadFileId) {
				// uploadFileId

				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("id", uploadFileId);

				List<DBObject> list = mongoService.findList(
						IC.mongodb_fileinfo, cond);

				if (null != list && list.size() > 0) {
					DBObject obj = list.get(0);

					if (null != obj.get("realPath")) {
						String realPath = obj.get("realPath").toString();
						log.info("realPath" + realPath);
						File file = new File(realPath);
						if (null != file) {
							InputStream in = new FileInputStream(file);
							byte[] buf = new byte[4 * 1024];
							int len = in.read(buf);// 读文件，将读到的内容放入到buf数组中，返回的是读到的长度
							OutputStream out = response.getOutputStream();
							while (len != -1) {
								out.write(buf, 0, len);
								len = in.read(buf);
							}
							out.flush();
							out.close();
							flag = true;
						}
						return flag;
					}

				}

			} else {
				return flag;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return flag;

	}

	public static String getAppPath(HttpServletRequest request) {
		String AppPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "/upload/";
		// String AppPath2 = request.getRealPath("") + "/upload/";
		return AppPath;
	}

}
