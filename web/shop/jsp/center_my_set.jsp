<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var yiqin_my_set = function(){
	var REX=/^[\s\u3000]*|[\s\u3000]*$/g;
	
	var action = {
		mySetInit : function(){
			$("#my_set_info").css('color','#fdb45e');
			
			//yiqin_my_set.findEmailIsVerify();
		},
		
		findEmailIsVerify : function(){
			$.ajax({
			    type: "POST",
			    async: true,
			    url: "findUserConf",
			    data : "attribute=email_verify",
			    dataType: "json",
			    success: function(data){
				   	var $typeSpan = $("#email_verify_type");
				   	$typeSpan.empty();
				   	if(data=='2'){
				   		$typeSpan.append("&nbsp;&nbsp;&nbsp;获取验证信息失败");
				   	}else{
				   		if(data.value=='11'){
				   			$typeSpan.append("&nbsp;&nbsp;&nbsp;已验证");
				   		}else{
				   			$typeSpan.append("<span class='span-mark'>&nbsp;&nbsp;&nbsp;未验证&nbsp;&nbsp;&nbsp;</span>");
				   			$typeSpan.append('<a href="" data-toggle="modal" data-target="#email_verify_alert">去验证</a>');
				   		}
				   	}
			    },
			    beforeSend: function(){},
			    complete: function(){},
			    error: function(){}
			});
		},
		
		sendEmailVerifyCode : function(mailType){
			$("#get_code_"+mailType).hide();
			$("#receive_error_"+mailType).html("");
			$("#except_time_"+mailType).html("验证码发送中...");
			$.ajax({
			    type: "POST",
			    async: true,
			    url: "toMailCode",
			    data : "mailType="+mailType,
			    dataType: "json",
			    success: function(data){
				   	if(data=='1'){
				   		$("#get_code_"+mailType).show();
				   		$("#except_time_"+mailType).html("");
				   		$("#receive_error_"+mailType).append("获取验证码失败，请稍后重试");
				   	}else if(data=='3'){
				   		$("#get_code_"+mailType).show();
				   		$("#except_time_"+mailType).html("");
				   		$("#receive_error_"+mailType).append("获取验证码失败，请稍后重试");
				   	}else if(data=='2'){
				   		timer = setInterval("countDown(\'"+mailType+"\')",1000);
				   	}else if(data=='4'){
				   		$("#except_time_"+mailType).html("");
				   		$("#receive_error_"+mailType).append("获取验证码邮箱未验证，请先验证邮箱");
				   	}
			    },
			    beforeSend: function(){},
			    complete: function(){},
			    error: function(){}
			});
		},
		
		modifyUserInfo : function(ptype){
			$("#receive_error_"+ptype).html("");
			var dataParam = checkModifyParam(ptype);
			if(!dataParam){
				return;
			}
			$.ajax({
			    type: "POST",
			    async: true,
			    url: "modifyUserInfo",
			    data : dataParam,
			    dataType: "text",
			    success: function(data){
			    	if(ptype=="mpw"){
			    		if(data=='1'){
					   		$("#receive_error_"+ptype).append("获取验证码邮箱未验证，请先验证邮箱");
					   	}else if(data=='2'){
					   		$("#receive_error_"+ptype).append("新密码不能为空");
					   	}else if(data=='3'){
					   		$("#receive_error_"+ptype).append("输入的验证码错误，请重新输入");
					   	}else if(data=='4'){
					   		$("#receive_error_"+ptype).append("输入的验证码已过期，请重新获取验证码");
					   	}else if(data=='5'){
					   		$("#receive_error_"+ptype).append("新密码与确认新密码不一致，请重新输入");
					   	}else if(data=='9'){
					   		$("#receive_error_"+ptype).append("修改密码失败，请稍后再试");
					   	}else{
					   		alert("密码修改成功");
					   		yiqin_public_js.toTilesAction("info", "/toCenterMySet");
					   	}
			    	}else if(ptype=="mem"){
			    		if(data=='1'){
					   		$("#receive_error_"+ptype).append("获取验证码邮箱未验证，请先验证邮箱");
					   	}else if(data=='2'){
					   		$("#receive_error_"+ptype).append("新邮箱地址不能为空");
					   	}else if(data=='3'){
					   		$("#receive_error_"+ptype).append("输入的验证码错误，请重新输入");
					   	}else if(data=='4'){
					   		$("#receive_error_"+ptype).append("输入的验证码已过期，请重新获取验证码");
					   	}else if(data=='9'){
					   		$("#receive_error_"+ptype).append("修改邮箱失败，请稍后再试");
					   	}else{
					   		alert("邮箱修改成功");
					   		yiqin_public_js.toTilesAction("info", "/toCenterMySet");
					   	}
			    	}else if(ptype=="vem"){
			    		if(data=='1'){
					   		$("#receive_error_"+ptype).append("邮箱已验证");
					   	}else if(data=='3'){
					   		$("#receive_error_"+ptype).append("输入的验证码错误，请重新输入");
					   	}else if(data=='4'){
					   		$("#receive_error_"+ptype).append("输入的验证码已过期，请重新获取验证码");
					   	}else if(data=='9'){
					   		$("#receive_error_"+ptype).append("邮箱验证失败，请稍后再试");
					   	}else{
					   		alert("邮箱验证成功");
					   		yiqin_public_js.toTilesAction("info", "/toCenterMySet");
					   	}
			    	}else if(ptype=="normal"){
			    		if(data=='1'){
					   		$("#receive_error_"+ptype).append("移动手机不能为空");
					   	}else if(data=='2'){
					   		$("#receive_error_"+ptype).append("企业用户，公司地址不能为空");
					   	}else if(data=='9'){
					   		$("#receive_error_"+ptype).append("修改用户信息失败，请稍后再试");
					   	}else{
					   		alert("修改信息成功");
					   		yiqin_public_js.toTilesAction("info", "/toCenterMySet");
					   	}
				   	}
			    },
			    beforeSend: function(){},
			    complete: function(){},
			    error: function(){}
			});
		},
		
	};
	
	var checkModifyParam = function(ptype){
		var msg = "";
		if(ptype=="mpw"){
			var $modal = $("#modify_pwd_alert"),
			password = $modal.find("input[name=password]").val(),
			confirmPwd = $modal.find("input[name=confirmPwd]").val();
			//verification_code = $modal.find("input[name=verification_code]").val().replace(REX, "");
			if (password == "") {
				$modal.find("input[name='password']").focus();
				msg = "新密码不能为空";
				$("#receive_error_mpw").html(msg);
				return false;
			}
			if (confirmPwd == "") {
				$modal.find("input[name='confirmPwd']").focus();
				msg = "确认新密码不能为空";
				$("#receive_error_mpw").html(msg);
				return false;
			}
// 			if (verification_code == "") {
// 				$modal.find("input[name='verification_code']").focus();
// 				msg = "邮箱验证码不能为空";
// 				$("#receive_error_mpw").html(msg);
// 				return false;
// 			}
			if (password != confirmPwd) {
				$modal.find("input[name='confirmPwd']").focus();
				msg = "确认新密码输入错误，请重新输入";
				$("#receive_error_mpw").html(msg);
				return false;
			}
			return "modifyType=mpw&password="+password+"&confirmPwd="+confirmPwd+"&verification_code="+verification_code;
		}else if(ptype=="mem"){
			var $modal = $("#modify_email_alert"),
			email = $modal.find("input[name=email]").val().replace(REX, "");
			//verification_code = $modal.find("input[name=verification_code]").val().replace(REX, "");
			if (email == "") {
				$modal.find("input[name='email']").focus();
				msg = "新邮箱地址不能为空";
				$("#receive_error_mem").html(msg);
				return false;
			}
// 			if (verification_code == "") {
// 				$modal.find("input[name='verification_code']").focus();
// 				msg = "邮箱验证码不能为空";
// 				$("#receive_error_mem").html(msg);
// 				return false;
// 			}
			if(!checkEmailFormat(email)){
				$modal.find("input[name='email']").focus();
				msg = "新邮箱地址格式不正确";
				$("#receive_error_mem").html(msg);
				return false;
			}
			return "modifyType=mem&email="+email+"&verification_code="+verification_code;
		}else if(ptype=="vem"){
			var $modal = $("#email_verify_alert"),
			verification_code = $modal.find("input[name=verification_code]").val().replace(REX, "");
			if (verification_code == "") {
				$modal.find("input[name='verification_code']").focus();
				msg = "邮箱验证码不能为空";
				$("#receive_error_vem").html(msg);
				return false;
			}
			return "modifyType=vem&verification_code="+verification_code;
		}else if(ptype=="normal"){
			var $modal = $("#my_info_list_normal"),
			name = "";//$modal.find("input[name=name]").val().replace(REX, ""),
			telephone = $modal.find("input[name=telephone]").val().replace(REX, ""),
			company = $modal.find("input[name=company]").val().replace(REX, ""),
			role_type = 1;//$("input:checked[name=role_type]").val();
			if (!checkName(name)) {
				$modal.find("input[name='name']").focus();
				msg = "真实姓名不能包含特殊字符";
				$("#receive_error_normal").html(msg);
				return false;
			}
			if (telephone == "") {
				$modal.find("input[name='telephone']").focus();
				msg = "移动电话号码不能为空";
				$("#receive_error_normal").html(msg);
				return false;
			}
			if (role_type == 2) {
				if(company == ""){
					$modal.find("input[name=company]").focus();
					msg = "企业用户类型，公司地址不能为空";
					$("#receive_error_normal").html(msg);
					return false;
				}
			}
			if (!checkPhone(telephone)) {
				$modal.find("input[name='telephone']").focus();
				msg = "手机号码格式错误，请重新输入";
				$("#receive_error_normal").html(msg);
				return false;
			}
			return "modifyType=normal&name="+name+"&telephone="+telephone+"&company="+company+"&role_type="+role_type;
		}
	};
	
	var checkEmailFormat = function(value) {
		var REX_EMAIL_FORMAT = /^([a-zA-Z0-9]+[-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/;
		if (value.length > 0 && !REX_EMAIL_FORMAT.test(value)) {
			return false;
		}
		return true;
	};

	var checkName = function(value) {
		var NAMEEXP = /^[a-zA-Z\u4e00-\u9fa5- ]*$/;
		if (value.length > 0 && !NAMEEXP.test(value)) {
			return false;
		}
		return true;
	};
	
	var checkPhone = function(value) {
		var REX_PHONE = /^[1][0-9]{10}$/;
		if (REX_PHONE.test(value)) {
			return true;
		}
		return false;
	};
	
	return action;
}();

var maxtime = "<s:text name='verify.code.expired.time.minute'/>"*60;
var timer = null;
function countDown(mailType){
	if(maxtime>=0){   
		seconds = maxtime;   
		msg = "邮箱验证码"+seconds+"秒后超时失效"; 
		document.getElementById("except_time_"+mailType).innerHTML=msg;
		--maxtime;   
	}else{
		clearInterval(timer);   
		$("#except_time_"+mailType).html("邮箱验证码已失效，请重新获取");
		$("#get_code_"+mailType).show();
	}   
};

$(document).ready(function(){
	yiqin_my_set.mySetInit();
});
</script>
<div class="center-right padding-right">
	<div class="row" id="my_info_list_normal">
		<div class="col-sm-12">
			<h5><b>基本信息</b></h5>
			<div class="chose_area">
				<ul class="user_option">
					<li style="margin-bottom:10px;">
						<label>登录账号：</label>
						<span><s:property value="#session.userInfo.id"/></span>
					</li>
					<li style="margin-bottom:10px;">
						<label>登录密码：</label>
						<span class='span-mark' style="font-size:14px;">互联网账号存在被盗风险，建议您定期更改密码。</span>
						<a data-toggle="modal" data-target="#modify_pwd_alert">修改</a>
					</li>
					<li style="margin-bottom:10px;">
						<label>公司名称：</label>
						<input type="text" style="width:300px;" name="company" value="<s:property value="#session.userInfo.company"/>">
<%-- 						<input type="text" name="name" value="<s:property value="#session.userInfo.name"/>"> --%>
					</li>
					<li style="margin-bottom:10px;">
						<label>移动电话：</label>
						<input type="text" name="telephone" value="<s:property value="#session.userInfo.mobile"/>">
					</li>
					<li style="margin-bottom:10px;">
						<label>邮箱地址：</label>
						<span><s:property value="#session.userInfo.email"/></span>
						<a data-toggle="modal" data-target="#modify_email_alert">修改</a>
<%-- 						<span class="ftx-03" id="email_verify_type"></span> --%>
					</li>
<!-- 					<li> -->
<!-- 						<label>公司地址：</label> -->
<%-- 						<input type="text" name="company" value="<s:property value="#session.userInfo.company"/>"> --%>
<!-- 					</li> -->
				</ul>
			</div>
			<a style="margin-left:50px;font-size:16px;width:100px;height:35px;margin-bottom: 10px" class="btn btn-default check_out" href="javaScript:yiqin_my_set.modifyUserInfo('normal');">提交</a>
			<span style="color:red" id="receive_error_normal"></span>
		</div>
<!-- 		<div class="col-sm-6"> -->
<!-- 			<h5><b>用户类型</b></h5> -->
<!-- 			<div class="chose_area"> -->
<!-- 				<ul class="user_option"> -->
<!-- 					<li> -->
<!-- 						<input type="radio" name="role_type" value="1" checked="checked"> -->
<!-- 						<label>个人用户</label> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<input type="radio" name="role_type" value="2"> -->
<!-- 						<label>企业用户</label> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
<!-- 			<ul class="user_option" style="margin-top: 30px;"> -->
<!-- 				<li> -->
<!-- 					<label>公司地址：</label> -->
<%-- 					<input type="text" name="company" value="<s:property value="#session.userInfo.company"/>"> --%>
<!-- 				</li> -->
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 		<a class="btn btn-default check_out" href="javaScript:yiqin_my_set.modifyUserInfo('normal');">提交</a> -->
<%-- 		<span style="color:red" id="receive_error_normal"></span> --%>
	</div> 
</div>

<!-- modify_pwd_alert modal -->
<div class="modal" id="modify_pwd_alert" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" >
			<div class="modal-header">
	             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	             <h5 class="modal-title"><strong>修改登录密码</strong></h5>
         	</div>
         	<div class="modal-body">
				<ul>
					<li>
						<label>新密码：</label>
						<input type="password" name="password"/>
					</li>
					<li>
						<label>确认新密码：</label>
						<input type="password" name="confirmPwd"/>
					</li>
<!-- 					<li> -->
<!-- 						<label>邮箱验证码：</label> -->
<!-- 						<input type="text" name="verification_code"/> -->
<!-- 						<a href="javaScript:yiqin_my_set.sendEmailVerifyCode('mpw');" id="get_code_mpw">获取验证码</a> -->
<%-- 						<span class='span-mark' id="except_time_mpw"></span> --%>
<!-- 					</li> -->
					<li>
						<span style="color:red" id="receive_error_mpw"></span>
					</li>
				</ul>
         	</div>
         	<div class="modal-footer">
				<a class="btn btn-default check_out" href="javaScript:yiqin_my_set.modifyUserInfo('mpw');">提交</a>
			</div>
		</div>
	</div>
</div>

<!-- modify_email_alert modal -->
<div class="modal" id="modify_email_alert" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" >
			<div class="modal-header">
	             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	             <h5 class="modal-title"><strong>修改邮箱信息</strong></h5>
         	</div>
         	<div class="modal-body">
				<ul>
					<li>
						<label>旧邮箱地址：</label>
						<span><s:property value="#session.userInfo.email"/></span>
					</li>
					<li>
						<label>新邮箱地址：</label>
						<input type="email" name="email"/>
					</li>
<!-- 					<li> -->
<!-- 						<label>邮箱验证码：</label> -->
<!-- 						<input type="text" name="verification_code"/> -->
<!-- 						<a href="javaScript:yiqin_my_set.sendEmailVerifyCode('mem');" id="get_code_mem">获取验证码</a> -->
<%-- 						<span class='span-mark' id="except_time_mem"></span> --%>
<!-- 					</li> -->
					<li>
						<span style="color:red" id="receive_error_mem"></span>
					</li>
				</ul>
         	</div>
         	<div class="modal-footer">
				<a class="btn btn-default check_out" href="javaScript:yiqin_my_set.modifyUserInfo('mem');">提交</a>
			</div>
		</div>
	</div>
</div>

<!-- email_verify_alert modal -->
<div class="modal" id="email_verify_alert" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" >
			<div class="modal-header">
	             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	             <h5 class="modal-title"><strong>邮箱有效验证</strong></h5>
         	</div>
         	<div class="modal-body">
				<ul>
					<li>
						<label>验证邮箱：</label>
						<span><s:property value="#session.userInfo.email"/></span>
					</li>
					<li>
						<label>邮箱验证码：</label>
						<input type="text" name="verification_code"/>
						<a href="javaScript:yiqin_my_set.sendEmailVerifyCode('vem');" id="get_code_vem">获取验证码</a>
						<span class='span-mark' id="except_time_vem"></span>
					</li>
					<li>
						<span style="color:red" id="receive_error_vem"></span>
					</li>
				</ul>
         	</div>
         	<div class="modal-footer">
				<a class="btn btn-default check_out" href="javaScript:yiqin_my_set.modifyUserInfo('vem');">验证</a>
			</div>
		</div>
	</div>
</div>