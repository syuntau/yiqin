<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">
function loginCheck(evt) {
	var errmsg = $("loginError");
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
}

function checkItemRequired(item) {
	var REX=/^[\s\u3000]*|[\s\u3000]*$/g;
	var obj = document.getElementById(item);
	if (obj != null) {
		var temp = obj.value.replace(REX, "");
		document.getElementById(item).value = temp;
		if (temp == null || temp.length == 0) {
			return false;
		}
	}
	return true;
}
</script>
	<section id="form"><!--form-->
		<div class="container">
			<div class="row">
				<div class="col-sm-4 col-sm-offset-1">
					<div class="login-form"><!--login form-->
						<h2>Login to your account</h2>
						<form action="login.action" method="post" name="loginForm">
							<input type="text" name="name" id="login_name_id" placeholder="Name" value="${param.name}"/>
							<input type="password" name="password" id="login_password_id" placeholder="Password"/>
							<span id="loginError" style="COLOR: red;text-align: left;">${requestScope.loginError}</span><br>
							<span>
								<input type="checkbox" class="checkbox"> 
								Keep me signed in
							</span>
							<button type="submit" class="btn btn-default" onclick="loginCheck();return false;">Login</button>
						</form>
					</div><!--/login form-->
				</div>
				<div class="col-sm-1">
					<h2 class="or">OR</h2>
				</div>
				<div class="col-sm-4">
					<div class="signup-form"><!--sign up form-->
						<h2>New User Signup!</h2>
						<form action="#">
							<input type="text" placeholder="Name" name="name" value=""/>
							<input type="password" placeholder="Password" name="password" value=""/>
							<input type="password" placeholder="Confirm Password" name="confirmPwd" value=""/>
							<input type="email" placeholder="Email Address" name="email" value=""/>
							<input type="text" placeholder="Telephone" name="telephone" value=""/>
							<button type="submit" class="btn btn-default">Signup</button>
						</form>
					</div><!--/sign up form-->
				</div>
			</div>
		</div>
	</section><!--/form-->
