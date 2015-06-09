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
import org.springframework.dao.DataAccessException;
import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Product;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.ExcelUtil;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class UploadItemAction extends ActionSupport {

	private static final long serialVersionUID = 5231287208800534978L;
	private File itemFile;
	private String itemFileFileName;
	private ProductManager productManager;

	public File getItemFile() {
		return itemFile;
	}

	public void setItemFile(File itemFile) {
		this.itemFile = itemFile;
	}

	public String getItemFileFileName() {
		return itemFileFileName;
	}

	public void setItemFileFileName(String itemFileFileName) {
		this.itemFileFileName = itemFileFileName;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String uploadItems() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			System.out.println("###################### itemFileFileName : " + itemFileFileName);
			
			if (Util.isEmpty(itemFileFileName)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				OutputStream output = null;
		        InputStream input = null;
		        String path = ServletActionContext.getServletContext().getRealPath(UtilKeys.UPLOAD_PATH);
		        String fileName = new StringBuilder().append(path).append(UtilKeys.FILE_SEPARATOR).append(itemFileFileName).toString();
				System.out.println("###################### fileName : " + fileName);
		        try{
		            output = new FileOutputStream(fileName);  
		            //建立一个1k大小的缓冲区
		            byte[] bs = new byte[1024];

		            //将上传过来的文件输出到output中
		            input = new FileInputStream(itemFile);
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
				List<Product> pList = new ArrayList<Product>();
				if (Util.isNotEmpty(list) && list.size() > 1) {
					List<Object> header = list.get(1);
					String categoryId = String.valueOf(header.get(0));
					header = header.subList(1, header.size());
					list = list.subList(2, list.size());
					StringBuilder sb = new StringBuilder();
					for (List<Object> attrList : list) {
						String productId = String.valueOf(attrList.get(0));
						sb.append(",").append("'").append(productId).append("'");
						attrList = attrList.subList(1, attrList.size());
						int i = 0;
						for (Object attr : attrList) {
							if (UtilKeys.BLANK_HASH.equals(attr)) {
								i++;
								continue;
							}
							Product item = new Product();
							item.setProductId(productId);
							item.setAttributeId(Integer.parseInt(String.valueOf(header.get(i++))));
							item.setValue(String.valueOf(attr));
							pList.add(item);
						}
					}

					try {
						productManager.deleteProducts(sb.substring(1));
						productManager.saveProduct(pList);
					} catch(DataAccessException dbe) {
						System.out.println("error in UploadItemAction.uploadItems for db exception");
						dbe.printStackTrace();
						out.print(UtilKeys.CODE_ERR_DB);
						return null;
					}

					List<ProductView> productlist = productManager.findProductInfo(categoryId);
					if (Util.isEmpty(productlist)) {
						result = UtilKeys.CODE_NO_RESULT;
					} else {
						Util.sortProductView(productlist);
						JSONArray jsonArray = JSONArray.fromObject(productlist);
						result = jsonArray.toString();
					}
				} else {
					result = UtilKeys.CODE_NO_RESULT;
				}
				out.print(result);
				return null;
			}
		} catch (IOException e1) {
			System.out.println("io error in UploadItemAction.uploadItems");
			e1.printStackTrace();
			return null;
		}
	}
}
