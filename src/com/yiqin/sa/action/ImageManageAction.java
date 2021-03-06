package com.yiqin.sa.action;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ImageManageAction extends ActionSupport {
	private static final long serialVersionUID = 1074040371036374358L;
	private String productId;
	private List<File> imageFile;
//	private String imageFileFileName;
//	private String deleteImageFileName;

	public List<File> getImageFile() {
		return imageFile;
	}

	public void setImageFile(List<File> imageFile) {
		this.imageFile = imageFile;
	}

	public String searchImage() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			this.productId = request.getParameter("productId");
			String aId = request.getParameter("aId");
			if (Util.isEmpty(productId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
			} else {
				String fileName = request.getSession().getServletContext()
		                .getRealPath("/")
		                + "img/";
				
				File file = new File(fileName);
				List<String> fileList = new ArrayList<String>();  
				  
		        if (!file.isDirectory()) {  
		            System.out.println(file.getAbsolutePath());  
		            fileList.add(file.getAbsolutePath());  
		        } else {  
		            File[] directoryList = file.listFiles(new FileFilter() {  
		                public boolean accept(File file) {  
		                    if (file.isFile() && file.getName().contains(productId)) {  
		                        return true;  
		                    } else {  
		                        return false;  
		                    }  
		                }  
		            });  
		            for (int i = 0; i < directoryList.length; i++) {  
		            	fileList.add("/img/"+directoryList[i].getName());  
		            }  
		        }  
				
				JSONArray ja =JSONArray.fromObject(fileList);
				result = ja.toString();
				out.print(result);
			}
		} catch (Exception e) {
			System.out.println("error in EditUserAction.modifyPass");
			e.printStackTrace();
		}
		return null;
	}
	
	public String uploadImage() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			String imageFileFileName = request.getParameter("imageFileFileName");
			System.out.println("###################### imageFileFileName : " + imageFileFileName);
			
			if (Util.isEmpty(imageFileFileName) || !(imageFileFileName.endsWith("jpg") || imageFileFileName.endsWith("png"))) {
				result = UtilKeys.CODE_ERR_PARAM;
				JSONObject jb = new JSONObject();
				jb.accumulate("req", result);
				out.print(jb.toString());
				return null;
			} else {
				String[] ss=imageFileFileName.split(",");
				for (int i = 0; i < ss.length; i++) {
					String fileFileName=ss[i].trim();
					OutputStream output = null;
			        InputStream input = null;
			        
			        if (Util.isEmpty(fileFileName)) {
			        	continue;
			        }
			        String fileName = request.getSession().getServletContext()
			                .getRealPath("/")
			                + "img/"+fileFileName.trim();
			        
			        File filee = new File(fileName);
			        if(filee.exists()){
			        	filee.delete();
			        }
			        
					System.out.println("###################### fileName : " + fileName);
			        try{
			            output = new FileOutputStream(fileName);  
			            //建立一个1k大小的缓冲区
			            byte[] bs = new byte[1024];

			            //将上传过来的文件输出到output中
			            input = new FileInputStream(imageFile.get(i));
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
				}
				

				
		        result = UtilKeys.CODE_SUCCESS;
		        JSONObject jb = new JSONObject();
				jb.accumulate("req", result);
				out.print(jb.toString());
				return null;
			}
		} catch (IOException e1) {
			System.out.println("io error in UploadItemAction.uploadItems");
			e1.printStackTrace();
			return null;
		}
	}
	public String deleteImage() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			String imageFileFileName = request.getParameter("imageFileFileName");
			String deleteImageFileName = request.getParameter("deleteImageFileName");
			System.out.println("###################### imageFileFileName : " + imageFileFileName);
			
			if (Util.isEmpty(deleteImageFileName)) {
				result = UtilKeys.CODE_ERR_PARAM;
				JSONObject jb = new JSONObject();
				jb.accumulate("req", result);
				out.print(jb.toString());
				return null;
			} else {
		        
		        String fileName = request.getSession().getServletContext()
		                .getRealPath("/")
		                + "img/"+deleteImageFileName;
		        
		        File filee = new File(fileName);
		        if(filee.exists()){
		        	filee.delete();
		        }
				
		        JSONObject jb = new JSONObject();
				jb.accumulate("req", "100");
				out.print(jb.toString());
				return null;
			}
		} catch (IOException e1) {
			System.out.println("io error in UploadItemAction.uploadItems");
			e1.printStackTrace();
			return null;
		}
	}
	

}
