package com.yiqin.shop.interceptor;

import java.util.Map;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yiqin.util.UtilKeys;

public class ShopNavInterceptor extends AbstractInterceptor {

	/** serialVersionUID */
	private static final long serialVersionUID = 1213162266700453807L;

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		Map<String, Object> session = ai.getInvocationContext().getSession();
		Map<String, Object> paras = ai.getInvocationContext().getParameters();
		Object navigation = paras.get(UtilKeys.PARA_NAV);
		if (navigation instanceof String[]) {
			String[] navigationArr = (String[]) navigation;
			String nav = navigationArr[0];
			session.put(UtilKeys.SE_SHOP_NAV, nav);
		}
		return ai.invoke();
	}

}
