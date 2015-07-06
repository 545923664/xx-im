//package com.jzwl.common.aspectj.token;
//
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//import com.jzwl.common.aspectj.token.Token;
//import com.jzwl.common.constant.GlobalConstant;
//import com.jzwl.common.session.SessionHolder;
//
///**
// * 防止重复提交的token切点校验方法类
// * 
// * @author zhang guo yu
// * @version 1.0.0
// * @since 2015-02-01
// * */
//@Aspect
//@Component
//public class TokenAspectj {
//	@Before("@annotation(token)")
//	public void before(JoinPoint jp, Token token) {
//		Object[] obj = jp.getArgs();
//		HttpServletRequest request = (HttpServletRequest) obj[0];
//		// HttpServletResponse response = (HttpServletResponse) obj[1];
//		if (token.save()) {
//			SessionHolder.setValueToSession("token", UUID.randomUUID()
//					.toString());
//		}
//		if (token.remove()) {
//			if (isRepeatSubmit(request)) {
//				try {
//					request.setAttribute(GlobalConstant.Global_SUBMIT,
//							GlobalConstant.Global_SUBMIT_FAILURE);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				request.setAttribute(GlobalConstant.Global_SUBMIT,
//						GlobalConstant.Global_SUBMIT_SUCCESS);
//			}
//			request.getSession(false).removeAttribute("token");
//		}
//	}
//
//	private boolean isRepeatSubmit(HttpServletRequest request) {
//		String serverToken = (String) SessionHolder
//				.getValueFromSession("token");
//		if (serverToken == null) {
//			return true;
//		}
//		String clinetToken = request.getParameter("token");
//		if (clinetToken == null) {
//			return true;
//		}
//		if (!serverToken.equals(clinetToken)) {
//			return true;
//		}
//		return false;
//	}
//}
