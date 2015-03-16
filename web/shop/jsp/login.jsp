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
			loginForm.submit();
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
		var REX_EMAIL_FORMAT = /^[\w-\.]+@[\w-]+(\.[\w-]+)+/;
		if (value.length > 0 && !REX_EMAIL_FORMAT.test(value)) {
			return false;
		}
		return true;
	};

	var checkName = function(value) {
		var REX_NAME = /^[0-9a-zA-Z_]{4,16}$/;
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
</script>
	<section id="form"><!--form-->
		<div class="container">
			<div class="row">
				<div class="col-sm-4 col-sm-offset-1">
					<div class="login-form"><!--login form-->
						<h2><s:text name="login.user.login.title"></s:text></h2>
						<form action="login" method="post" name="loginForm">
							<input type="text" name="login_name" id="login_name_id" placeholder="<s:text name='login.user.name' />" value="${param.login_name}"/>
							<input type="password" name="login_password" id="login_password_id" placeholder="<s:text name='login.user.password' />"/>
							<span id="loginError" style="COLOR: red;text-align: left;">${requestScope.loginError}</span><br>
							<span>
								<input type="checkbox" class="checkbox"> 
								<s:text name="login.keep.logining"></s:text>
							</span>
							<button type="submit" class="btn btn-default" onclick="yiqin_login_action.loginCheck();return false;"><s:text name="login.btn.login"></s:text></button>
						</form>
					</div><!--/login form-->
				</div>
				<div class="col-sm-1">
					<h2 class="or">OR</h2>
				</div>
				<div class="col-sm-4">
					<div class="signup-form"><!--sign up form-->
						<h2><s:text name="login.user.sing.up"></s:text></h2>
						<form action="" name="registerForm" method="post" id="registerFormId">
							<input type="text" placeholder="<s:text name='login.user.name' />" name="userId" value=""/>
							<input type="password" placeholder="<s:text name='login.user.password' />" name="password" value=""/>
							<input type="password" placeholder="<s:text name='login.user.confirm.password' />" name="confirmPwd" value=""/>
							<input type="email" placeholder="<s:text name='login.user.email' />" name="email" value=""/>
							<input type="text" placeholder="<s:text name='login.user.mobile' />" name="mobile" value=""/>
							<span id="registerError" style="COLOR: red;text-align: left;"></span>
							<button type="submit" class="btn btn-default" onclick="yiqin_login_action.registerUser();return false;"><s:text name="login.btn.sign.up"></s:text></button>
						</form>
					</div><!--/sign up form-->
				</div>
			</div>
		</div>
	</section><!--/form-->
