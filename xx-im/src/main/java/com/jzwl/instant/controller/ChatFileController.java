package com.jzwl.instant.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jzwl.base.service.MongoService;
import com.jzwl.instant.util.L;
import com.jzwl.instant.util.UploadDownUtil;

@Controller
@RequestMapping("/chat")
public class ChatFileController {

	Logger log = LoggerFactory.getLogger(ChatFileController.class);

	@Autowired
	private MongoService mongoService;

	@RequestMapping(value = "/fileUpload")
	public void chatFileUpload(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String username = request.getParameter("userInfo");

			L.out(username);

			username = URLDecoder.decode(username, "UTF-8");
			String fileName = request.getParameter("fileName");
			fileName = URLDecoder.decode(fileName, "UTF-8");

			String fileUploadId = UploadDownUtil.fileUpload(mongoService,
					request, username, fileName, "fileType");

			// ///////
			String uploadResult = "";
			if (null != fileUploadId) {
				uploadResult = fileUploadId;
			}

			PrintWriter out;
			out = response.getWriter();
			out.print(uploadResult);
			out.flush();
			out.close();

			// ///////

			log.error("has upload file <<" + fileUploadId);
		} catch (Exception e) {

			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(e.getMessage());
				out.flush();
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
		}
	}


	@RequestMapping(value = "/fileDownLoad")
	public void chatFileDownLoad(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		try {
			// user pk
			String username = request.getParameter("userInfo");
			// uploadFileId
			String fileName = request.getParameter("fileName");

			// boolean flag = UploadDownUtil.fileDownLoad(response, request,
			// username, fileName);
			boolean flag = UploadDownUtil.fileDownLoad4fid(mongoService,
					response, request, username, fileName);

			log.error("user file down" + username + "-" + fileName + "-" + flag);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
