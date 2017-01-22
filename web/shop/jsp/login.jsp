<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  

<script type="text/javascript">
var yiqin_login_action = function(){
	var REX=/^[\s\u3000]*|[\s\u3000]*$/g;
	
	var login_action = {
		loginCheck : function(evt){
			var errmsg = $("#loginError");
			errmsg.html("&nbsp;");
			if (!checkItemRequired("login_name_id")) {
				document.getElementById("login_name_id").focus();
				errmsg.html("请输入用户名");
				return false;
			}
			if (!checkItemRequired("login_password_id")) {
				document.getElementById("login_password_id").focus();
				errmsg.html("请输入密码");
				return false;
			}
			yiqin_public_js.setCookie("cookieUser",document.getElementById("login_name_id").value,30);
			loginForm.submit();
		},
		
		setCookieLoginUser : function(){
			var userId = yiqin_public_js.getCookie("cookieUser");
			if(userId != null && userId != ""){
				document.getElementById("login_name_id").value = userId;
			}
		},
		
		registerUser : function(){
			var options = {
				target: "",
				type: "POST",
				url:"register",
		        resetForm: true,
		        success: responseFunction
			};
	    	if (registerValidation()) {
	    		$('#registerFormId').ajaxSubmit(options);
	    	}
	    	return false;
	    }
	};
	
	var responseFunction = function(data) {
		var regForm = $("#registerFormId");
		if (data == 2) {
			regForm.find("input[name='userId']").focus();
			$("#registerError").html("注册项为必填项，不能为空");
			return;
		}
		if (data == 3) {
			regForm.find("input[name='confirmPwd']").focus();
			$("#registerError").html("确认密码输入错误，请重新输入");
			return;
		}
		if (data == 4) {
			regForm.find("input[name='userId']").focus();
			$("#registerError").html("用户名已被使用，请重新输入");
			return;
		}
		if (data == 5) {
			regForm.find("input[name='userId']").focus();
			$("#registerError").html("注册失败，请稍后再试");
			return;
		}
		if (data == 1) {
			alert("注册成功，请登录");
		}
	};

	var registerValidation = function() {
		$("#registerError").html("");
		var regForm = $("#registerFormId");
		var name = regForm.find("input[name='userId']").val().replace(REX, "");
		var password = regForm.find("input[name='password']").val().replace(REX, "");
		var confirmPwd = regForm.find("input[name='confirmPwd']").val().replace(REX, "");
		var email = regForm.find("input[name='email']").val().replace(REX,"");
		var telephone = regForm.find("input[name='mobile']").val().replace(REX, "");
		var msg = "注册项为必填项，不能为空";
		if (name == "") {
			regForm.find("input[name='userId']").focus();
			$("#registerError").html(msg);
			return false;
		}
		if (!checkName(name)) {
			regForm.find("input[name='userId']").focus();
			$("#registerError").html("用户名为4到16位字母数字或下划线组成");
			return false;
		}
		if (password == "") {
			regForm.find("input[name='password']").focus();
			$("#registerError").html(msg);
			return false;
		}
		if (confirmPwd == "") {
			regForm.find("input[name='confirmPwd']").focus();
			$("#registerError").html(msg);
			return false;
		}
		if (password != confirmPwd) {
			regForm.find("input[name='confirmPwd']").focus();
			$("#registerError").html("确认密码输入错误，请重新输入");
			return false;
		}
		if (email == "") {
			regForm.find("input[name='email']").focus();
			$("#registerError").html(msg);
			return false;
		}
		if (!checkEmailFormat(email)) {
			regForm.find("input[name='email']").focus();
			$("#registerError").html("邮箱格式错误，请重新输入");
			return false;
		}
		if (telephone == "") {
			regForm.find("input[name='mobile']").focus();
			$("#registerError").html(msg);
			return false;
		}
		if (!checkPhone(telephone)) {
			regForm.find("input[name='mobile']").focus();
			$("#registerError").html("手机号码格式错误，请重新输入");
			return false;
		}
		return true;
	};

	var checkEmailFormat = function(value) {
		var REX_EMAIL_FORMAT = /^([a-zA-Z0-9]+[-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/;
		if (value.length > 0 && !REX_EMAIL_FORMAT.test(value)) {
			return false;
		}
		return true;
	};

	var checkName = function(value) {
		var REX_NAME = /^([a-zA-Z0-9]{1}[a-zA-Z0-9_]{3,16})$/;
		if (value.length > 0 && !REX_NAME.test(value)) {
			return false;
		}
		return true;
	};

	var checkPhone = function(value) {
		var REX_PHONE = /^[1][0-9]{10}$/;
		if (value.length > 0 && !REX_PHONE.test(value)) {
			return false;
		}
		return true;
	};

	var checkItemRequired = function(item) {
		var obj = document.getElementById(item);
		if (obj != null) {
			var temp = obj.value.replace(REX, "");
			document.getElementById(item).value = temp;
			if (temp == null || temp.length == 0) {
				return false;
			}
		}
		return true;
	};

	return login_action;
}();

$(document).ready(function(){
	yiqin_login_action.setCookieLoginUser();
});
</script>
		<section id="form">
			<div class="container">
				<div id="loginImg" class="col-md-offset-3 col-sm-offset-2">
					<img alt="" src="/shop/images/home/login.jpg" width="265px">
				</div>
				<div id="loginbox" class="mainbox col-md-6 col-sm-8">                    
					<div class="panel white-alpha-90">
						<div class="panel-heading">
							<div class="panel-title"><h3 style="font-weight: 900;color: #40403E;">欢迎登录</h3></div>
						</div>     
						<div class="panel-body">
							<span id="loginError" style="COLOR: red;text-align: left;">${requestScope.loginError}&nbsp;</span>
							<form id="loginform" class="form-horizontal" role="form" action="login" method="post" name="loginForm">
								<div class="input-group">
									<span class="input-group-addon item-class"><i class="fa fa-user"></i></span>
									<input type="text" class="form-control item-class" maxlength="15" name="login_name" id="login_name_id" placeholder="<s:text name='login.user.name' />" value="${param.login_name}"/>                                        
								</div>
								<div class="input-group">
									<span class="input-group-addon item-class"><i class="fa fa-lock"></i></span>
									<input type="password" class="form-control item-class" maxlength="15" name="login_password" id="login_password_id" placeholder="<s:text name='login.user.password' />"/>
								</div>
								<div class="input-group col-xs-12 text-center login-action">
									<button type="submit" class="btn btn-default" onclick="yiqin_login_action.loginCheck();return false;"><s:text name="login.btn.login"></s:text></button>
								</div>
							</form>     
						</div>                     
					</div>  
				</div>
			</div>
		</section>


