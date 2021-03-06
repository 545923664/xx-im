package com.jzwl.instant.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.ChatFileBean;
import com.jzwl.instant.service.FileService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;
import com.jzwl.instant.util.Util;

@Component
public class FileServiceImpl implements FileService {

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	/**
	 * 上传聊天文件
	 */
	public String upload(HttpServletRequest request, String username,
			String fileName) {

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

			// 获得文件：
			MultipartFile file = multipartRequest.getFile("file");

			if (null != file) {
				// 获得输入流：
				InputStream input = file.getInputStream();

				BufferedInputStream in = new BufferedInputStream(input);

				String AppPath = getAppPath(request);

				File serverFileDir = new File(AppPath);

				if (!serverFileDir.exists()) {
					serverFileDir.mkdir();
				}

				String uuid = Util.getUUID();

				String saveFileName = uuid + "_" + fileName;

				FileOutputStream fo = new FileOutputStream(AppPath
						+ saveFileName);

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

				fileBean.setId(uuid);

				fileBean.setFileName(saveFileName);

				/**
				 * 
				 */
				fileBean.setAccessUrl(getSaveDir() + saveFileName);

				fileBean.setRealPath(AppPath + saveFileName);
				fileBean.setUploadDate(Util.getCurrDate());
				fileBean.setUploadUserName(username);

				mongoService.save(IC.mongodb_fileinfo, fileBean);

				return fileBean.getAccessUrl();

			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			L.out("request stream is not find file field");
			return null;
		}

	}

	/**
	 * 上传图片
	 */
	public String uploadPic(HttpServletRequest request, String username,
			String fileName) {

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

			// 获得文件：
			MultipartFile file = multipartRequest.getFile("file");

			if (null != file) {
				// 获得输入流：
				InputStream input = file.getInputStream();

				BufferedInputStream in = new BufferedInputStream(input);

				String AppPath = getAppPath(request);

				File serverFileDir = new File(AppPath);

				if (!serverFileDir.exists()) {
					serverFileDir.mkdir();
				}

				String uuid = Util.getUUID();

				String saveFileName = uuid + "_" + fileName;

				FileOutputStream fo = new FileOutputStream(AppPath
						+ saveFileName);

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

				//
				String accessUrl = getSaveDir() + saveFileName;

				return accessUrl;

			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			L.out("request stream is not find file field");
			return null;
		}

	}

	/**
	 * 上传多图片
	 */
	public Set<String> uploadPics(HttpServletRequest request, String username) {

		Set<String> uploadUrls = new HashSet<String>();

		try {

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

			List<MultipartFile> fileList = multipartRequest.getFiles("file");

			if (null != fileList && fileList.size() > 0) {

				for (MultipartFile file : fileList) {

					if (null != file) {
						// 获得输入流：
						InputStream input = file.getInputStream();

						BufferedInputStream in = new BufferedInputStream(input);

						String AppPath = getAppPath(request);

						File serverFileDir = new File(AppPath);

						if (!serverFileDir.exists()) {
							serverFileDir.mkdir();
						}

						String uuid = Util.getUUID();

						String saveFileName = uuid + "_"
								+ file.getOriginalFilename();

						FileOutputStream fo = new FileOutputStream(AppPath
								+ saveFileName);

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

						//
						String accessUrl = getSaveDir() + saveFileName;

						uploadUrls.add(accessUrl);

					}
				}
			}

			return uploadUrls;

		} catch (Exception e) {
			e.printStackTrace();
			L.out("request stream is not find file field");
			return uploadUrls;
		}

	}

	public String getSaveDir() {
		return "/upload/";
	}

	public String getAppPath(HttpServletRequest request) {
		String AppPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ getSaveDir();
		return AppPath;
	}

}
