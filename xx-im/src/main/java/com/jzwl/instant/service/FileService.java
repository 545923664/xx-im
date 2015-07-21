package com.jzwl.instant.service;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public interface FileService {

	public String upload(HttpServletRequest request, String username,
			String fileName);

	public String uploadPic(HttpServletRequest request, String username,
			String fileName);

	public Set<String> uploadPics(HttpServletRequest request, String username);

}
