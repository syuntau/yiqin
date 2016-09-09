package com.yiqin.sa.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.Category;
import com.yiqin.service.ProductManager;
import com.yiqin.util.ExcelUtil;
import com.yiqin.util.ProductExcelUtil;
import com.yiqin.util.Util;

/**
 * 导出订单信息
 * 
 * @author LiuJun
 * 
 */
public class ExportProductExcelAction extends ActionSupport {
	
	private static final long serialVersionUID = 424035033536530973L;
	
	private ProductManager productManager;
	private String categoryId;

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if (Util.isEmpty(categoryId)) {
				return null;
			}
			int iCategoryId = -1;
			try {
				iCategoryId = Integer.parseInt(categoryId);
			} catch (Exception e) {
				return null;
			}
			List<Attribute> attrList = productManager.findAttributeByCategoryId(iCategoryId);
			
			ProductExcelUtil util = new ProductExcelUtil();
			util.addHeader(attrList);

			List<String> attrIdList = new ArrayList<String>();
			for(Attribute attr : attrList) {
				attrIdList.add(attr.getNameId());
			}

			Map<String, Map<String, String>> productMap = productManager.findProductAllInfo(categoryId, false);
			if (productMap != null) {
				util.addRows(productMap, attrIdList);
			} else {
				return null;
			}
			
			List<Category> catList = productManager.findCategoryList(categoryId);
			Category cat = catList.get(0);
			StringBuilder sb = new StringBuilder();
			String name = cat.getName();
			name = name.replaceAll("[/]", "");
			sb.append(categoryId).append("-").append(name);
			ExcelUtil.download(response, util.getWorkbook(), sb.toString());
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
