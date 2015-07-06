package com.jzwl.mobile.config.contoller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jzwl.base.controller.BaseController;
@Controller
@RequestMapping("/mobileList")//url
public class MobileListController extends BaseController {
	
	@RequestMapping("/list")//url
	public ModelAndView querybannerdata(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/admin/mobileConfigList");//设置JSP页面路径
		
		return mv;//返回
	}
}
