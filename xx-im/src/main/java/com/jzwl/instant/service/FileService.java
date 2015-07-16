package com.jzwl.instant.service;

import javax.servlet.http.HttpServletRequest;

import com.jzwl.base.service.MongoService;

public interface FileService {

	public String upload(MongoService mongoService, HttpServletRequest request,
			String username, String fileName);

}
