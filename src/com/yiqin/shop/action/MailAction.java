package com.yiqin.shop.action;

import java.util.Date;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.User;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Configuration;
import com.yiqin.util.MailUtil;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

/**
 * 获取邮箱验证码
 * 
 * @author liujun
 *
 */
public class MailAction extends ActionSupport {

	private static final long serialVersionUID = 9022071250128088877L;

	// 邮件类型
	private String mailType;

	private UserManager userManager;

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		User user = Util.getLoginUser(session);
		String userID = user.getId();
		try {
			String toEmail = user.getEmail();
			// 邮箱是否已经验证
			if(!"vem".equals(mailType)){
				UserConf userConf = userManager.findUserConfInfo(userID,"email_verify");
				if(userConf == null || "00".equals(userConf.getValue())){
					result = "4";
					response.getWriter().print(result);
					return null;
				}
			}
						
			// 发件人名称
			String senderName = Configuration.getProperty(UtilKeys.SEND_NAME_EMAIL);
			// 发件人地址
			String fromEmail = Configuration.getProperty(UtilKeys.FROM_EMAIL);
			// 发件密码
			String fromPwd = Configuration.getProperty(UtilKeys.FROM_EMAIL_PASSWORD);
			// 邮件服务器
			String smtpServer = Configuration.getProperty(UtilKeys.MAIL_SERVER_HOST);
			
			// 邮件主题
			String emailTitle = "";
			if("mpw".equals(mailType)){
				emailTitle = Configuration.getProperty(UtilKeys.MODIFY_PWD_TITLE);
			}else if("mem".equals(mailType)){
				emailTitle = Configuration.getProperty(UtilKeys.MODIFY_EMAIL_TITLE);
			}else if("vem".equals(mailType)){
				emailTitle = Configuration.getProperty(UtilKeys.VERIFICATION_EMAIL_TITLE);
			}
			//验证码生成
			String verificationCode = Util.verificationCodeProcess();
			//存储验证码过期时间3分钟后过期
			String vcodekey = userID+"_code_"+verificationCode;
			int expTime = Integer.valueOf(Configuration.getProperty(UtilKeys.VERIFY_CODE_EXPIRED_TIME_MINUTE));
			session.setAttribute(vcodekey, Util.addMinute(new Date(), expTime));
			// 邮件内容
			String emailContent = getHtmlString(mailType,userID,verificationCode);

			MailUtil mail = null;
			Long sizelimit = Long.valueOf(Configuration.getProperty(UtilKeys.EMAIL_SIZE));
			Long mailsize = 0L;
			
			mail = new MailUtil(smtpServer, fromEmail, senderName, fromEmail, fromPwd, toEmail, emailTitle, emailContent);
			mailsize = Long.valueOf(emailContent.getBytes("UTF-8").length);

			// 邮件大小判断
			if (mailsize > sizelimit) {
				result = "1";
				response.getWriter().print(result);
				return null;
			} else {
				mail.sendMessage();
				result = "2";
				response.getWriter().print(result);
				return null;
			}
		} catch (AddressException ae) {
			ae.printStackTrace();
			result = "3";
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "3";
			response.getWriter().print(result);
			return null;
		} 
	}

	private String getHtmlString(String mailType, String userID, String verificationCode) {
		String doMessage = "";
		StringBuilder content = new StringBuilder();
		if ("mpw".equals(mailType)) {
			doMessage = "密码修改操作";
		} else if ("mem".equals(mailType)) {
			doMessage = "邮箱修改操作";
		} else if ("vem".equals(mailType)) {
			doMessage = "邮箱验证操作";
		}
		content.append("提醒您：<br/>").append(userID).append("您正在进行").append(doMessage).append("，验证码为：").append(verificationCode);
		return content.toString();
	}
}
