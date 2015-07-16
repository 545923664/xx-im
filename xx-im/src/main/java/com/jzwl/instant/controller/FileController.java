package com.jzwl.instant.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.instant.pojo.FormatJsonResult;
import com.jzwl.instant.service.impl.FileServiceImpl;
import com.jzwl.instant.util.JsonTool;

@Controller
@RequestMapping("/file")
public class FileController {

	Logger log = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private MongoService mongoService;
	@Autowired
	private FileServiceImpl fileServiceImpl;
	
	private Gson gson = new Gson();

	@RequestMapping(value = "/upload")
	public void chatFileUpload(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String username = request.getParameter("username");

			String fileName = request.getParameter("fileName");

			if (null != username && null != fileName) {
				String accessUrl = fileServiceImpl.upload(mongoService, request,
						username, fileName);

				if (null != accessUrl) {
					fjr = new FormatJsonResult(1, accessUrl, "t", null, null);
				} else {
					fjr = new FormatJsonResult(1, "上传失败", "t", null, null);
				}
			}else{
				fjr = new FormatJsonResult(0, "参数错误", "t", null, null);
			}

			JsonTool.printMsg(response, gson.toJson(fjr));

		} catch (Exception e) {

			fjr = new FormatJsonResult(0, e.getMessage(), "t", null, null);

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

}
