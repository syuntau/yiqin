package com.yiqin.shop.interceptor;

import java.io.PrintWriter;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 用来拦截用户在没有登录的情况下的一些误操作
 * 
 * @author LiuJun
 * 
 */
public class NotLoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() {
		super.init();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		// 获得请求类型
		String type = ServletActionContext.getRequest().getHeader("X-Requested-With");

		// 获取session中用户信息
		Map<String, Object> map = actionInvocation.getInvocationContext().getSession();
		Object user = map.get("userInfo");

		// 判断用户是否登录
		if (user != null) {

			// 调用下一个拦截器
			return actionInvocation.invoke();
		} else {
			// ajax请求
			if ("XMLHttpRequest".equalsIgnoreCase(type)) {
				PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
				printWriter.print("notLoginError");
				printWriter.flush();
				printWriter.close();
				return null;
			} else {
				// 普通http请求
				Map requestAttr = (Map) actionInvocation.getInvocationContext().get("request");
				requestAttr.put("notLoginError", "您没有登录或登录超时，请先登录！");
				return Action.LOGIN;
			}
		}
	}
}
