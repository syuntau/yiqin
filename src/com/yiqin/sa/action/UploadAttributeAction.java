package com.yiqin.sa.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	private String attributeList;

	public String getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(String attributeList) {
		this.attributeList = attributeList;
	}

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
			
			if (Util.isEmpty(attributeFileFileName)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				OutputStream output = null;
		        InputStream input = null;
		        String path = ServletActionContext.getServletContext().getRealPath(UtilKeys.UPLOAD_PATH);
		        String fileName = new StringBuilder().append(path).append("\\").append(attributeFileFileName).toString();
				System.out.println("###################### fileName : " + fileName);
		        try{
		            output = new FileOutputStream(fileName);  
		            //建立一个1k大小的缓冲区
		            byte[] bs = new byte[1024];

		            //将上传过来的文件输出到output中
		            input = new FileInputStream(attributeFile);
		            int length = 0;
		            //length=input.read(bs)这句话中，length=-1代表了读到文件结尾
		            while ((length=input.read(bs))>0){
		                output.write(bs, 0, length);
		            }
		        }catch(Exception e) {
		        	e.printStackTrace();
		        }finally{
		            input.close();
		            output.close();
		        }

		        File file = new File(fileName);
				List<List<Object>> list = ExcelUtil.readExcel(file);
				System.out.println(list);
				List<Attribute> attributeList = new ArrayList<Attribute>();
				if (Util.isNotEmpty(list) && list.size() > 1) {
					list = list.subList(1, list.size());
					for (List<Object> obj : list) {
						Attribute attribute = new Attribute();
						attribute.setNameId(String.valueOf(obj.get(0)));
						attribute.setName(String.valueOf(obj.get(1)));
						String val = String.valueOf(obj.get(2));
						val = UtilKeys.BLANK_HASH.equals(val) ? UtilKeys.BLANK : val;
						attribute.setValue(val);
						attribute.setCategoryId(Integer.parseInt(String.valueOf(obj.get(3))));
						attribute.setFilter(Byte.parseByte(String.valueOf(obj.get(4))));
						attribute.setFilterType(Byte.parseByte(String.valueOf(obj.get(5))));
						String showVal = String.valueOf(obj.get(6));
						showVal = UtilKeys.BLANK_HASH.equals(showVal) ? UtilKeys.BLANK : showVal;
						attribute.setShowValue(showVal);
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
