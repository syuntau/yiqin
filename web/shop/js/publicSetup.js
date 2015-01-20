$(document).ajaxComplete(function(event, XHR) {
	var resText = XHR.responseText;
	if (resText != "" && resText == "notLoginError") {
		alert("您没有登录或登录超时，请先登录！");
		postFormToLogin();
	}
});

var postFormToLogin = function(){
	 var form = $('<form></form>');  
     form.attr('action', "/shop/index.action");  
     form.attr('method', 'post');  
     form.attr('target', '_self');
     var my_input = $('<input type="text" name="home" />');
     my_input.attr('value', "top_login");  
     form.append(my_input);  
     form.submit();  
     return false;  
}
