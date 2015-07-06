package com.jzwl.mobile.config.contoller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jzwl.base.controller.BaseController;
import com.jzwl.base.service.RedisService;
import com.jzwl.common.page.PageObject;
import com.jzwl.mobile.config.service.DiagramService;
@Controller
@RequestMapping("/mobileConfig")//url
public class MobileConfig extends BaseController {
	@Autowired
	private DiagramService diagramService;
	
	@Autowired
	private RedisService cacheUtil;
	
	private PageObject pageObject = new PageObject();
	
	public PageObject getPageObject() {
		return pageObject;
	}

	public void setPageObject(PageObject pageObject) {
		this.pageObject = pageObject;
	}

	private Map<String,Object> resultMap = new HashMap<String,Object>();
	
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	//diagram初始化启动图
	@RequestMapping("/diagram")//url
	public ModelAndView diagram() throws UnsupportedEncodingException{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/mobile/diagramPage");//设置JSP页面路径
		
		return mv;//返回
		
	}
	
	//diagram添加启动图
	@RequestMapping(value = "/addDiagram",method = RequestMethod.POST)
	@ResponseBody
	public boolean addStudentInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> params=new HashMap<String,Object>();
		dealParameters(request,params);
//		params.put("id", idFactory.nextId());
		boolean result=diagramService.addDiagramInfo(params);
		return result;
	}
	
	////diagram删除启动图
	@RequestMapping(value = "/delDiagramInfo",method = RequestMethod.POST)
	public void delStudentInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> params=new HashMap<String,Object>();
		dealParameters(request,params);
		diagramService.delDiagramInfo(params);
	}
	
	//diagram修改启动图
	@RequestMapping(value = "/updateDiagram",method = RequestMethod.POST)
	@ResponseBody
	public boolean updateStudentInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> params=new HashMap<String,Object>();
		dealParameters(request,params);
//		params.put("id", idFactory.nextId());
		 return diagramService.updateDiagramInfo(params);
		
	}
	
	//bannerdata 轮转图信息
	@RequestMapping("/bannerdata")//url
	public ModelAndView bannerdata(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/mobile/bannerdataPage");//设置JSP页面路径
		return mv;//返回
	}
	
	@RequestMapping(value = "/queryBannerdata",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBannerdata(HttpServletRequest request, HttpServletResponse response){
		//Map<String,Object> ma = new HashMap<String,Object>();
		List<Map<String,Object>> celllsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);//
		pageObject = super.getBaseReadService().queryForPageList("select id,newsid,picurl,catid,catname from bannerdata",null,Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("rp").toString()));
		List<Map<String,Object>> dataList = pageObject.getDatasource();
		//putCache(dataList);
		for(Map<String,Object> m:dataList){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("cell", m);
			celllsit.add(cell);	
		}
		resultMap.put("total", pageObject.getTotalCount()); 
		resultMap.put("rows", celllsit);
		resultMap.put("page", pageObject.getCurrentPage());
		return resultMap; 
	}
	
	//bannerdata 添加轮转图
	@RequestMapping(value = "/addBannerdata",method = RequestMethod.POST)
	@ResponseBody
	public boolean addBannerdata(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> params=new HashMap<String,Object>();
		dealParameters(request,params);
//		params.put("id", idFactory.nextId());
		return diagramService.addBannerdata(params);
		
	}
	
	//bannerdata 删除轮转图
	@RequestMapping(value = "/delBannerdata",method = RequestMethod.POST)
	public void delBannerdata(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> params=new HashMap<String,Object>();
		dealParameters(request,params);
		diagramService.delBannerdata(params);
	}
	
	//diagram修改启动图
	@RequestMapping(value = "/updateBannerdata", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateBannerdata(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.updateBannerdata(params);

	}
	
	//appinfo 分享app文案信息
	@RequestMapping("/appinfo")//url
	public ModelAndView appinfo(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/mobile/mobileConfigPage");//设置JSP页面路径
		mv.addObject("actionName", "Appinfo");
		return mv;//返回
	}
	
	@RequestMapping(value = "/queryAppinfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAppinfo(HttpServletRequest request, HttpServletResponse response){
		//Map<String,Object> ma = new HashMap<String,Object>();
		List<Map<String,Object>> celllsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);//
		pageObject = super.getBaseReadService().queryForPageList("select id,name,value from appinfo",null,Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("rp").toString()));
		List<Map<String,Object>> dataList = pageObject.getDatasource();
		putCache(dataList);
		for(Map<String,Object> m:dataList){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("cell", m);
			celllsit.add(cell);	
		}
		resultMap.put("total", pageObject.getTotalCount()); 
		resultMap.put("rows", celllsit);
		resultMap.put("page", pageObject.getCurrentPage());
		return resultMap; 
	}
	
	//appinfo 添加分享app文案信息
	@RequestMapping(value = "/addAppinfo",method = RequestMethod.POST)
	@ResponseBody
	public boolean addappinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.addAppinfo(params);
	}
	
	//appinfo 删除分享app文案信息
	@RequestMapping(value = "/delAppinfo", method = RequestMethod.POST)
	public void delAppinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		diagramService.delAppinfo(params);
	}
	
	//appinfo 修改分享app文案信息
	@RequestMapping(value = "/updateAppinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateAppinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.updateAppinfo(params);

	}
	
	//shareinfo 活动配置信息
	@RequestMapping("/shareinfo")
	public ModelAndView shareinfo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/mobile/mobileConfigPage");//设置JSP页面路径
		mv.addObject("actionName", "Shareinfo");
		return mv;// 返回
	}
	
	@RequestMapping(value = "/queryShareinfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryShareinfo(HttpServletRequest request, HttpServletResponse response){
		//Map<String,Object> ma = new HashMap<String,Object>();
		List<Map<String,Object>> celllsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);//
		pageObject = super.getBaseReadService().queryForPageList("select id,name,value from shareinfo",null,Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("rp").toString()));
		List<Map<String,Object>> dataList = pageObject.getDatasource();
		putCache(dataList);
		for(Map<String,Object> m:dataList){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("cell", m);
			celllsit.add(cell);	
		}
		resultMap.put("total", pageObject.getTotalCount()); 
		resultMap.put("rows", celllsit);
		resultMap.put("page", pageObject.getCurrentPage());
		return resultMap; 
	}
	
	//shareinfo 添加活动配置信息
	@RequestMapping(value = "/addShareinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean addShareinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.addShareinfo(params);
	}	
	
	//shareinfo 删除活动配置信息
	@RequestMapping(value = "/delShareinfo", method = RequestMethod.POST)
	public void delShareinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		diagramService.delShareinfo(params);
	}
	
	//shareinfo 修改活动配置信息
	@RequestMapping(value = "/updateShareinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateShareinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.updateShareinfo(params);

	}
	
	//zxinfo 资讯分类信息
	@RequestMapping("/zxinfo")
	public ModelAndView zxinfo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/mobile/mobileConfigPage");//设置JSP页面路径
		mv.addObject("actionName", "Zxinfo");
		return mv;// 返回
	}
	
	@RequestMapping(value = "/queryZxinfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryZxinfo(HttpServletRequest request, HttpServletResponse response){
		//Map<String,Object> ma = new HashMap<String,Object>();
		List<Map<String,Object>> celllsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);//
		pageObject = super.getBaseReadService().queryForPageList("select id,name,value from zxinfo",null,Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("rp").toString()));
		List<Map<String,Object>> dataList = pageObject.getDatasource();
		putCache(dataList);
		for(Map<String,Object> m:dataList){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("cell", m);
			celllsit.add(cell);	
		}
		resultMap.put("total", pageObject.getTotalCount()); 
		resultMap.put("rows", celllsit);
		resultMap.put("page", pageObject.getCurrentPage());
		return resultMap; 
	}
	
	//zxinfo 添加资讯分类信息
	@RequestMapping(value = "/addZxinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean addZxinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.addZxinfo(params);
		
	}
	
	//zxinfo 删除资讯分类信息
	@RequestMapping(value = "/delZxinfo", method = RequestMethod.POST)
	public void delZxinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		diagramService.delZxinfo(params);
	}
	
	//zxinfo 修改资讯分类信息
	@RequestMapping(value = "/updateZxinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateZxinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.updateZxinfo(params);

	}
	
	//evaluationinfo 测评信息和激活升学卡信息
	@RequestMapping("/evaluationinfo")
	public ModelAndView evaluationinfo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/mobile/mobileConfigPage");//设置JSP页面路径
		mv.addObject("actionName", "Evaluationinfo");
		return mv;// 返回
	}
	
	
	@RequestMapping(value = "/queryEvaluationinfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryEvaluationinfo(HttpServletRequest request, HttpServletResponse response){
		//Map<String,Object> ma = new HashMap<String,Object>();
		List<Map<String,Object>> celllsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);//
		pageObject = super.getBaseReadService().queryForPageList("select id,name,value from evaluationinfo",null,Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("rp").toString()));
		List<Map<String,Object>> dataList = pageObject.getDatasource();
		putCache(dataList);
		for(Map<String,Object> m:dataList){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("cell", m);
			celllsit.add(cell);	
		}
		resultMap.put("total", pageObject.getTotalCount()); 
		resultMap.put("rows", celllsit);
		resultMap.put("page", pageObject.getCurrentPage());
		return resultMap; 
	}
	
	//evaluationinfo 添加测评信息和激活升学卡信息
	@RequestMapping(value = "/addEvaluationinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean addEvaluationinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.addEvaluationinfo(params);
		
	}
	
	//evaluationinfo 删除测评信息和激活升学卡信息
	@RequestMapping(value = "/delEvaluationinfo", method = RequestMethod.POST)
	public void delEvaluationinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		diagramService.delEvaluationinfo(params);
	}
	
	//evaluationinfo 修改测评信息和激活升学卡信息
	@RequestMapping(value = "/updateEvaluationinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateEvaluationinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.updateEvaluationinfo(params);

	}
	
	//cardtype app升学卡类型
	@RequestMapping("/cardtype")
	public ModelAndView cardtype() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/mobile/mobileConfigPage");//设置JSP页面路径
		mv.addObject("actionName", "Cardtype");
		return mv;// 返回
	}
	
	@RequestMapping(value = "/queryCardtype",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryCardtype(HttpServletRequest request, HttpServletResponse response){
		List<Map<String,Object>> celllsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);//
		pageObject = super.getBaseReadService().queryForPageList("select id,name,value from cardType",null,Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("rp").toString()));
		List<Map<String,Object>> dataList = pageObject.getDatasource();
		putCache(dataList);
		for(Map<String,Object> m:dataList){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("cell", m);
			celllsit.add(cell);	
		}
		resultMap.put("total", pageObject.getTotalCount()); 
		resultMap.put("rows", celllsit);
		resultMap.put("page", pageObject.getCurrentPage());
		return resultMap; 
	}
	
	//cardtype 添加app升学卡类型
	@RequestMapping(value = "/addCardtype", method = RequestMethod.POST)
	@ResponseBody
	public boolean addCardtype(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.addCardtype(params);
		
	}
	
	//cardtype 删除app升学卡类型
	@RequestMapping(value = "/delCardtype", method = RequestMethod.POST)
	public void delCardtype(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		diagramService.delCardtype(params);
	}
	
	//cardtype 修改app升学卡类型
	@RequestMapping(value = "/updateCardtype", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateCardtype(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.updateCardtype(params);

	}
	
	//freeinfo 用户需要扣费修改分数的时间设置
	@RequestMapping("/freeinfo")
	public ModelAndView freeinfo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/mobile/mobileConfigPage");//设置JSP页面路径
		mv.addObject("actionName", "Freeinfo");
		return mv;// 返回
	}
	
	@RequestMapping(value = "/queryFreeinfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryFreeinfo(HttpServletRequest request, HttpServletResponse response){
		List<Map<String,Object>> celllsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);//
		pageObject = super.getBaseReadService().queryForPageList("select id,name,value from freeinfo",null,Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("rp").toString()));
		List<Map<String,Object>> dataList = pageObject.getDatasource();
		putCache(dataList);
		for(Map<String,Object> m:dataList){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("cell", m);
			celllsit.add(cell);	
		}
		resultMap.put("total", pageObject.getTotalCount()); 
		resultMap.put("rows", celllsit);
		resultMap.put("page", pageObject.getCurrentPage());
		return resultMap; 
	}
	
	//freeinfo 添加用户需要扣费修改分数的时间设置
	@RequestMapping(value = "/addFreeinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean addFreeinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.addFreeinfo(params);
		
	}
	
	//freeinfo 删除用户需要扣费修改分数的时间设置
	@RequestMapping(value = "/delFreeinfo", method = RequestMethod.POST)
	public void delFreeinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		diagramService.delFreeinfo(params);
	}
	
	//freeinfo 修改用户需要扣费修改分数的时间设置
	@RequestMapping(value = "/updateFreeinfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateFreeinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.updateFreeinfo(params);

	}
	
	//versioninfo 版本信息
	@RequestMapping("/versioninfo")
	public ModelAndView versioninfo() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/mobile/mobileConfigPage");//设置JSP页面路径
		mv.addObject("actionName", "Versioninfo");
		return mv;// 返回
	}
	
	@RequestMapping(value = "/queryVersioninfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryVersioninfo(HttpServletRequest request, HttpServletResponse response){
		List<Map<String,Object>> celllsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);//
		pageObject = super.getBaseReadService().queryForPageList("select id,name,value from versioninfo",null,Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("rp").toString()));
		List<Map<String,Object>> dataList = pageObject.getDatasource();
		putCache(dataList);
		for(Map<String,Object> m:dataList){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("cell", m);
			celllsit.add(cell);	
		}
		resultMap.put("total", pageObject.getTotalCount()); 
		resultMap.put("rows", celllsit);
		resultMap.put("page", pageObject.getCurrentPage());
		return resultMap; 
	}
	
	//versioninfo 添加版本信息
	@RequestMapping(value = "/addVersioninfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean addVersioninfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.addVersioninfo(params);
	}
	
	//versioninfo 删除版本信息
	@RequestMapping(value = "/delVersioninfo", method = RequestMethod.POST)
	public void delVersioninfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		diagramService.delVersioninfo(params);
	}
	
	//versioninfo 修改 版本信息
	@RequestMapping(value = "/updateVersioninfo", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateVersioninfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);
		// params.put("id", idFactory.nextId());
		return diagramService.updateVersioninfo(params);

	}
	
	private void dealParameters(HttpServletRequest req,Map<String,Object> params){
		Enumeration<String> e=req.getParameterNames();
		while(e.hasMoreElements())
		{
			String str=e.nextElement();
			params.put(str, req.getParameter(str));
		}
	}
	
	private void putCache(List<Map<String,Object>> list){
		for (Map<String, Object> map : list) {

			String key = (String) map.get("name");
			String value = (String) map.get("value");
			String redis_result = cacheUtil.get(key);
			if(null==redis_result){
				cacheUtil.set(key, value, 600);
			}
			
		}
	}
	
	@RequestMapping(value = "/queryDiagram",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryDiagram(HttpServletRequest request, HttpServletResponse response){
		//Map<String,Object> ma = new HashMap<String,Object>();
		List<Map<String,Object>> celllsit = new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		dealParameters(request, params);//
		pageObject = super.getBaseReadService().queryForPageList("select id,zhname,nodename,name,value from activecardinfo",null,Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("rp").toString()));
		List<Map<String,Object>> dataList = pageObject.getDatasource();
		//putCache(dataList);
		for(Map<String,Object> m:dataList){
			Map<String,Object> cell = new HashMap<String,Object>();
			cell.put("cell", m);
			celllsit.add(cell);	
		}
		resultMap.put("total", pageObject.getTotalCount()); 
		resultMap.put("rows", celllsit);
		resultMap.put("page", pageObject.getCurrentPage());
		return resultMap; 
	}

	
}
