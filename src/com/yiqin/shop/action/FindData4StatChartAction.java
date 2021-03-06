package com.yiqin.shop.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Category;
import com.yiqin.service.ProductManager;
import com.yiqin.service.ShoppingManager;
import com.yiqin.util.Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 查询统计图所需数据
 * 
 * @author bai
 *
 */
public class FindData4StatChartAction extends ActionSupport {

	private static final long serialVersionUID = 7895594013293121083L;
	
	private String beginMonth;
	private String endMonth;
	private String category;
	private ShoppingManager shoppingManager;
	private ProductManager productManager;
	
	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public ProductManager getProductManager() {
		return productManager;
	}



	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}



	public ShoppingManager getShoppingManager() {
		return shoppingManager;
	}



	public void setShoppingManager(ShoppingManager shoppingManager) {
		this.shoppingManager = shoppingManager;
	}



	public String getBeginMonth() {
		return beginMonth;
	}



	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}



	public String getEndMonth() {
		return endMonth;
	}



	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}



	public void getChartData(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String userId = Util.getLoginUser(request.getSession()).getId();
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if(Util.isEmpty(beginMonth) || Util.isEmpty(endMonth)){
				result = "108";
				out.print(result);
				return;
			}
			Date begin = null;
			Date end = null;
			try {
				begin = DateUtils.parseDate(beginMonth, "yyyyMM");
				end = DateUtils.parseDate(endMonth, "yyyyMM");
			} catch (Exception e) {
				result = "601";//日期格式错误
				out.print(result);
				return;
			}
			
			if(end.before(begin)){
				result = "602";//开始时间不能大于结束时间
				out.print(result);
				return;
			}
			Calendar beginC = Calendar.getInstance();
			beginC.setTime(begin);
			beginC.add(Calendar.YEAR, +1);
			
			if(beginC.getTime().before(end)){
				result = "603";//查询时间不能超过了一年
				out.print(result);
				return;
			}
			if(Util.isEmpty(category)){
				result = "604";//分类id为空
				out.print(result);
				return;
			}
			
			JSONArray ja = shoppingManager.getChartData(userId, beginMonth ,endMonth,category);
			
			if(beginMonth.equals(endMonth)){
				JSONArray req = new JSONArray();
				for (Object object : ja) {
					JSONObject jo = JSONObject.fromObject(object);
					String name = "";
					if(jo.getString("id").equals("-1000")){
						name= "备注";
					}else{
						List<Category> clist = productManager.findCategoryList(jo.getString("id"));
						if(clist == null || clist.size()==0){
							continue;
						}
						name = clist.get(0).getName();
					}
					JSONObject jo1 = new JSONObject();
					jo1.accumulate("name", name);
					jo1.accumulate("value", jo.getDouble("value"));
					req.add(jo1);
				}
				out.print(req.toString());
			}else{
				out.print(ja.toString());
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
	}
	
	
}
