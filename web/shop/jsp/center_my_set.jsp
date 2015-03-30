<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">

$(document).ready(function(){
	
});
</script>
<div class="center-right padding-right">
	<div class="row">
		<div class="col-sm-6">
			<h5><b>基本信息</b></h5>
			<div class="chose_area">
				<ul class="user_option">
					<li style="margin-bottom:10px;">
						<label>登录账号：</label>
						<span><s:property value="#session.userInfo.id"/></span>
					</li>
					<li style="margin-bottom:10px;">
						<label>登录密码：</label>
						<span style="color:#e4393c;font-size:8px">互联网账号存在被盗风险，建议您定期更改密码。</span>
						<a target="_blank" href="javaScript:;">修改</a>
					</li>
					<li style="margin-bottom:10px;">
						<label>真实姓名：</label>
						<input type="text" name="name" value="<s:property value="#session.userInfo.name"/>">
					</li>
					<li style="margin-bottom:10px;">
						<label>移动电话：</label>
						<input type="text" name="mobile" value="<s:property value="#session.userInfo.mobile"/>">
					</li>
					<li style="margin-bottom:10px;">
						<label>邮箱地址：</label>
						<span><s:property value="#session.userInfo.email"/></span>
						<a target="_blank" href="javaScript:;">修改</a>
						<span class="ftx-03">&nbsp;&nbsp;&nbsp;已验证</span>
					</li>
				</ul>
			</div>
		</div>
		<div class="col-sm-6">
			<h5><b>用户类型</b></h5>
			<div class="chose_area">
				<ul class="user_option">
					<li>
						<input type="radio" name="role_type" value="1" checked="checked">
						<label>个人用户</label>
					</li>
					<li>
						<input type="radio" name="role_type" value="2">
						<label>企业用户</label>
					</li>
				</ul>
			</div>
			<ul class="user_option" style="margin-top: 30px;">
				<li>
					<label>公司地址：</label>
					<input type="text" name="company" value="<s:property value="#session.userInfo.company"/>">
				</li>
			</ul>
		</div>
		<a class="btn btn-default check_out" href="javaScript:;">提交</a>
	</div> 
</div>