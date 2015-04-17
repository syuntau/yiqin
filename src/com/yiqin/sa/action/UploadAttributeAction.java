package com.yiqin.sa.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Attribute;
import com.yiqin.service.ProductManager;
import com.yiqin.util.ExcelUtil;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class UploadAttributeAction extends ActionSupport {

	private static final long serialVersionUID = 168934024332408535L;
	private File attributeFile;
	private String attributeFileFileName;
	private ProductManager productManager;

	public File getAttributeFile() {
		return attributeFile;
	}

	public void setAttributeFile(File attributeFile) {
		this.attributeFile = attributeFile;
	}

	public String getAttributeFileFileName() {
		return attributeFileFileName;
	}

	public void setAttributeFileFileName(String attributeFileFileName) {
		this.attributeFileFileName = attributeFileFileName;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String uploadAttribute() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			System.out.println("###################### attributeFileFileName : " + attributeFileFileName);
			File f = this.getAttributeFile();
			System.out.println("###################### tmp file : " + f);
			
			if (Util.isEmpty(attributeFileFileName)) {
				result = UtilKeys.CODE_ERR_PARAM;
				return null;
			} else {
//				File file = new File(attributeFileFileName);
				List<List<Object>> list = ExcelUtil.readExcel(getAttributeFile(), attributeFileFileName.substring(attributeFileFileName.indexOf(".") + 1));
				System.out.println(list);
				List<Attribute> attributeList = new ArrayList<Attribute>();
				if (Util.isNotEmpty(list) && list.size() > 1) {
					list = list.subList(1, list.size());
					for (List<Object> obj : list) {
						Attribute attribute = new Attribute();
						attribute.setNameId(String.valueOf(obj.get(0)));
						attribute.setName(String.valueOf(obj.get(1)));
						attribute.setValue(String.valueOf(obj.get(2)));
						attribute.setCategoryId(Integer.parseInt(String.valueOf(obj.get(3))));
						attribute.setFilter(Byte.parseByte(String.valueOf(obj.get(4))));
						attribute.setFilterType(Byte.parseByte(String.valueOf(obj.get(5))));
						attribute.setShowValue(String.valueOf(obj.get(6)));
						attribute.setSort(Byte.parseByte(String.valueOf(obj.get(7))));
						attributeList.add(attribute);
					}
				}
				
				productManager.saveAttribute(attributeList);

				JSONArray jsArray = JSONArray.fromObject(attributeList);
				result = jsArray.toString();
				out.print(result);
				return null;
			}
		} catch (IOException e1) {
			System.out.println("io error in UploadAttributeAction.uploadAttribute");
			e1.printStackTrace();
			return null;
		}
	}
}
