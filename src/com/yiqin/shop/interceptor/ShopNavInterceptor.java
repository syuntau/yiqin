package com.yiqin.shop.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yiqin.util.UtilKeys;

public class ShopNavInterceptor extends AbstractInterceptor {

	/** serialVersionUID */
	private static final long serialVersionUID = 1213162266700453807L;

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		System.out.println("@@@@@@@@@@ Action："+ai.getAction().getClass().getName());
		System.out.println("@@@@@@@@@@ Struts2 中配置的Action："+ai.getProxy().getActionName());
		System.out.println("@@@@@@@@@@ 调用的方法："+ai.getProxy().getMethod());
		Map<String, Object> session = ai.getInvocationContext().getSession();
		Map<String, Object> paras = ai.getInvocationContext().getParameters();
		Object navigation = paras.get(UtilKeys.PARA_NAV);
		if (navigation == null) {
			HttpServletRequest request = (HttpServletRequest) ai.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
			navigation = request.getAttribute(UtilKeys.REQ_SHOP_NAV);
			if (navigation != null) {
				session.put(UtilKeys.SE_SHOP_NAV, navigation);
			}
		} else {
			if (navigation instanceof String[]) {
				String[] navigationArr = (String[]) navigation;
				String nav = navigationArr[0];
				session.put(UtilKeys.SE_SHOP_ORIGNAL_NAV, session.get(UtilKeys.SE_SHOP_NAV));
				session.put(UtilKeys.SE_SHOP_NAV, nav);
			}
		}
		return ai.invoke();
	}

}
