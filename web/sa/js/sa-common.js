/**
 * 通用js函数
 */
//ajax请求全局未登录校验
$(document).ajaxComplete(function(event, XHR) {
	var resText = XHR.responseText;
	if (resText != "" && resText == "notLoginError") {
		alert("您没有登录或登录超时，请先登录！");
		sa_common.postFormToLogin();
	}
});

var sa_common = function(){
	var action = {
		//top状态提交设置
		postFormToLogin	: function(){
			 var form = $('<form method="post"></form>');  
		     form.attr('action', "/sa/toLogin");  
		     form.attr('target', '_self');
		     $('body').append(form);
		     form.submit();
		     return false;  
		}
	}
	return action;
}();
