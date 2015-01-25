$(document).ajaxComplete(function(event, XHR) {
	var resText = XHR.responseText;
	if (resText != "" && resText == "notLoginError") {
		alert("您没有登录或登录超时，请先登录！");
		postFormToTopAction("top_login");
	}
});

var postFormToTopAction = function(top_value){
	 var form = $('<form></form>');  
     form.attr('action', "/shop/index.action");  
     form.attr('method', 'post');  
     form.attr('target', '_self');
     var my_input = $('<input type="text" name="home" />');
     my_input.attr('value', top_value);  
     form.append(my_input);  
     form.submit();  
     return false;  
};
