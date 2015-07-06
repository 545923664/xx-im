//package com.jzwl.mytest.login.controller;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//import com.jzwl.base.controller.BaseController;
//import com.jzwl.base.dal.strategy.IDynamicDataSource;
//import com.jzwl.common.aspectj.token.Token;
//import com.jzwl.common.constant.GlobalConstant;
//import com.jzwl.common.session.SessionHolder;
//import com.jzwl.mytest.login.service.LoginService;
//
///**
// * 登录，登出controller，用来进行系统的登入登出，以及越权访问的跳转
// * 
// * @ClassName: LoginController
// * @author: zhang guo yu
// * @date: 2015-1-21 下午2:54:43
// * @version: 1.0.0
// */
//@Controller
//public class LoginController extends BaseController {
//
//	@Autowired
//	private LoginService loginService;//login的service
//	
//	@Autowired
//	private IDynamicDataSource dynamicDataSource;//用于进行数据源的切换
//
//	/**
//	 * 显示登录页
//	 * */
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) { 
//		return new ModelAndView("/login");
//	}
//
//	/**
//	 * 用户登出
//	 */
//	@RequestMapping("/logout")
//	public String logout(HttpServletRequest request,HttpServletResponse response) {
//		Subject subject = SecurityUtils.getSubject();
//		if (subject.isAuthenticated() || subject.getSession() != null) {
//			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
//		}
//		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
//	}
//
//	/**
//	 * 用户输入用户名和密码以后的验证和初始化
//	 */
//	@Token(save = true)
//	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
//	public ModelAndView dologin(HttpServletRequest request,HttpServletResponse response) {
//		String userName = request.getParameter("userName");
//		String passWord = request.getParameter("passWord");
//		try {
//			dynamicDataSource.balanceReadDataSource(null, null);
//			Map map = loginService.getUserInfoByUserName(userName);
//			if (map != null && map.get("username").equals(userName) && map.get("password").equals(passWord)) {
//				// 如果登陆成功，构建用户合法令牌
//				UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
//				token.setRememberMe(false);
//				Subject subject = SecurityUtils.getSubject();
//				subject.login(token);
//				//保存用户session
//				SessionHolder.setValueToSession(GlobalConstant.Global_SESSION_USER, map);
//			} else {
//				// 登录失败
//				return new ModelAndView("redirect:/login");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ModelAndView("redirect:/login");
//		}
//		return new ModelAndView("/index");
//	}
//
//	/**
//	 * 用户意图访问他没有权限访问的资源的时候的系统处理
//	 */
//	@RequestMapping(value = "/noright")
//	public ModelAndView noright(HttpServletRequest request,HttpServletResponse response) {
//		return new ModelAndView("/noright");
//	}
//}
