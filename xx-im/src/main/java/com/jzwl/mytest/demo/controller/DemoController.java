package com.jzwl.mytest.demo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jzwl.base.controller.BaseController;
import com.jzwl.base.dal.strategy.IDynamicDataSource;
import com.jzwl.base.service.BaseWriteService;
import com.jzwl.base.service.RedisService;
import com.jzwl.common.aspectj.token.Token;
import com.jzwl.common.constant.AjaxStatusConstant;
import com.jzwl.common.constant.BusinessConstant;
import com.jzwl.common.constant.GlobalConstant;
import com.jzwl.common.constant.MongodbConstant;
import com.jzwl.common.constant.OperationConstant;
import com.jzwl.common.id.IdFactory;
import com.jzwl.common.init.infoholder.SystemInfoInitAndHolder;
import com.jzwl.common.jms.MessageSender;
import com.jzwl.common.log.SystemLog;
import com.jzwl.mytest.demo.pojo.DemoPojo;
import com.jzwl.mytest.demo.service.DemoService;

/**
 * 范例Controller
 * 
 * @ClassName: DemoController
 * @description: 通过看这个例子，让大家更快掌握这个框架
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {

	/********************一眼能看明白意思的引用，不需要注释。否则请一定添加注释*********************/
	@Autowired
	private BaseWriteService baseWriteService;//基类写service
	
	@Autowired
	private IDynamicDataSource dynamicDataSource;//用于进行数据源的切换
	
	@Autowired
	private DemoService demoService;//demo的service
	
//	@Autowired
	private MongoTemplate mongoTemplate;//mongodb操作模板
	
	@Autowired
	private RedisService cacheUtil;//缓存工具类
	
	@Autowired
	private SystemLog systemLog;//日志工具类
	
//	@Autowired
	private MessageSender messageSender;//消息队列发出类
	
	@Autowired
	private IdFactory idFactory;//ID生成器
	
	/**
	 * post方式交互，提交URL和参数，返回一个页面 参数从request中取,中文统一UTF-8格式 ，免乱码
	 * */
	@RequestMapping(value = "/myrequestpost", method = RequestMethod.POST)
	public ModelAndView myrequestpost(HttpServletRequest request, HttpServletResponse response) {
//		Map map = (Map) request.getSession().getAttribute(GlobalConstant.Global_SESSION_USER);
//		System.out.println("测试session：" + map.get("username"));//测试使用session中的信息
//		System.out.println(request.getSession().getId());//测试抓取jsessionid
//		System.out.println(idFactory.nextId());//测试ID生成
		ModelAndView mov = new ModelAndView();
		mov.setViewName("/demo/mynice");//  /demo/myrequestpost
//		mov.addObject("msg", "id为" + request.getParameter("name")); 
		return mov;
	}

	/**
	 * get方式交互，提交URL和参数，返回一个页面 参数从request中取，但是中文要记得处理一下，避免乱码
	 * */
	@RequestMapping(value = "/myrequestget", method = RequestMethod.GET)
	public ModelAndView myrequestget(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("---------------------------------------------");//测试cachefilter
		ModelAndView mov = new ModelAndView();
		mov.setViewName("/demo/mynice");
		String n = "";
		//try {
			//n = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
		//} catch (UnsupportedEncodingException e) {
		//	e.printStackTrace();
		//}
		//mov.addObject("msg", "id为" + n);
		System.out.println("-->" + SystemInfoInitAndHolder.getSystemInfo("demokeythree"));//取出初始化的系统信息
//		cacheUtil.put("myTestKey", "myTestValue456");//使用缓存-写
//		System.out.println(cacheUtil.keyIsExist("myTestKey"));//判断key值是否存在
//		System.out.println(cacheUtil.get("myTestKey"));//使用缓存-读
//		cacheUtil.remove("myTestKey");//删除相应key值
//		System.out.println(cacheUtil.keyIsExist("myTestKey"));//判断key值是否存在
		systemLog.debugLog(getClass(), "myTestDebugInfomation");//记录debug日志
		
		/*********************************操作mongodb start*************************************/
		DemoPojo demoPojo = new DemoPojo();
		demoPojo.setId(1110);
		demoPojo.setOperationType(OperationConstant.OPERATION_DEVANDTEST);
		demoPojo.setBusinessName(BusinessConstant.BUSINESS_DEMO);
		demoPojo.setDeleteFlag(MongodbConstant.MONGO_DELETEFLAG_ALIVE);
		demoPojo.setCreateUserName("admin");
		demoPojo.setUpdateUserName("admin");
		Calendar ca3 = Calendar.getInstance();
		ca3.set(2015, 1, 1,10,11);
		demoPojo.setCreateTime(ca3.getTime());
		demoPojo.setLastModifyTime(ca3.getTime());
		mongoTemplate.save(demoPojo);
		
		Calendar ca1 = Calendar.getInstance();
		ca1.set(2014, 10, 1,10,11);
		Calendar ca2 = Calendar.getInstance();
		ca2.set(2015, 10, 1,10,11);
		
		DemoPojo demoPojo2 = mongoTemplate.findOne(new Query(Criteria.where("lastModifyTime").gte(ca1.getTime()).lte(ca2.getTime())), DemoPojo.class);
		if(demoPojo2 != null){
			System.out.println(demoPojo2.getCreateUserName());
		}else{
			System.out.println(" demoPojo2 is null ");
		}
		/*********************************操作mongodb end*************************************/
		/*********************************操作消息队列 start*************************************/
//		Map jmsMap = new HashMap();
//		jmsMap.put("operationType", JmsConstant.JMS_ID);//这个属性是必须的，用来区分业务逻辑分支
//		jmsMap.put("mytest", "123456");
//		messageSender.sendMessage(jmsMap);
		/*********************************操作消息队列 end*************************************/
		return mov;
	}

	/**
	 * get + json方式交互，提交URL和参数，返回一个map，系统自动转成一个json字符串返回
	 * 参数从request中取，但是中文要记得处理一下，避免乱码
	 * */
	@Token(remove = true)
	@RequestMapping(value = "/mydempjson", method = RequestMethod.GET)
	public @ResponseBody Map mydempjson(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request.getAttribute(GlobalConstant.Global_SUBMIT).toString());
		Map map = new HashMap();
		map.put("ajax_status", AjaxStatusConstant.AJAX_STATUS_SUCCESS);//json串内，必须携带ajax_status字段，用来判定本次ajax操作是否成功
		map.put("keyStr1", "one");
		map.put("keyStr2", "two");
		String n = "";
		try {
			n = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");//或者在JS里进行UTF-8的转码更好
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.put("name", n);
		return map;
	}

	/**
	 * 提交条件，生成并下载excel文件
	 * 参数从request中取，但是中文要记得处理一下，避免乱码
	 * */
	@RequestMapping(value = "/mydownloadexcel", method = RequestMethod.GET)
	public void mydownloadexcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filename = "test.xls";// 设置下载时客户端Excel的名称
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + filename);
		OutputStream ouputStream = response.getOutputStream();
		ouputStream.flush();
		ouputStream.close();
	}
	
	/**
	 * 文件上传
	 * 也可以同时传进来其他参数，参数从request中取
	 * */
	@RequestMapping(value="/upload", method=RequestMethod.POST)  
    public String upload(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response) throws IOException{  
        //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件  
        for(MultipartFile myfile : myfiles){
            if(myfile.isEmpty()){  
                System.out.println("文件未上传");  
            }else{  
                System.out.println("文件长度: " + myfile.getSize());  
                System.out.println("文件类型: " + myfile.getContentType());  
                System.out.println("文件名称: " + myfile.getName());  
                System.out.println("文件原名: " + myfile.getOriginalFilename());  
                System.out.println("========================================");   
            }  
        }
        request.setAttribute("sendkey", "sendvalue");
        return "forward:/demo/redirectlist.html";  //注意，这里forward的方法，只能是post的，get会报错
    }  
	
	/**
	 * 跟上边方法之间直接redirect
	 * */
	@RequestMapping(value = "/redirectlist", method = RequestMethod.POST)
	public ModelAndView redirectlist(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(request.getAttribute("sendkey"));
		ModelAndView mov = new ModelAndView();
		mov.setViewName("/demo/mynice");
		mov.addObject("msg", "id为redirectlist");
		return mov;
	}
	
	/**
	 * 测试事务  && mongodb
	 * */
	@RequestMapping(value = "/myquesttransaction", method = RequestMethod.POST)
	public ModelAndView myquesttransaction(HttpServletRequest request, HttpServletResponse response)throws Exception {
		try{
			systemLog.debugLog(DemoController.class, "myTestDebuglog");
			//记录过程行为日志
			systemLog.infoLog(getClass(), "mytestUserName", "mytestUserAlias", BusinessConstant.BUSINESS_DEMO, OperationConstant.OPERATION_UPDATE, "info abc");
			dynamicDataSource.balanceWriteDataSource(null, null);//轮转切换数据源
			demoService.testTransaction();//执行demo业务方法
			List lt = baseWriteService.getRecordSet("select * from mytest", null);//查验业务方法结果
			if(lt != null && lt.size() > 0){
				System.out.println(lt.size());
			}
			
		}catch (Exception e) {
			//记录报错日志并抛出错误
			systemLog.errorLog(DemoController.class, "mytestUserName", "mytestUserAlias", BusinessConstant.BUSINESS_DEMO, OperationConstant.OPERATION_UPDATE, "error abc", e);
			throw new Exception(e);
		}
		ModelAndView mov = new ModelAndView();
		mov.setViewName("/demo/mynice");
		mov.addObject("msg", "事务名称为" + request.getParameter("name"));
		return mov;
	}
	
	/**
	 * 测试shiro登录前的跳转
	 * */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mov = new ModelAndView();
		mov.setViewName("/index");
		return mov;
	}

}
