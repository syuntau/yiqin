package com.yiqin.shop.interceptor;

import java.util.Map;

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
	public String intercept(ActionInvocation actionInvocation) {

		// 获取session中用户信息
		Map<String, Object> map = actionInvocation.getInvocationContext().getSession();
		Object user = map.get("userInfo");

		// 判断用户是否登录
		if (user != null) {

			// 调用下一个拦截器
			try {
				return actionInvocation.invoke();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 如果用户没有登录
			Map request = (Map) actionInvocation.getInvocationContext().get("request");
			request.put("notLoginError", "您没有登录或登录超时，请先登录！");
		}
		return Action.LOGIN;
	}
}
