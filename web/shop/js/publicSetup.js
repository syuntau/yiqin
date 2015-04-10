/**
 * 通用js函数
 * @author LiuJun
 */
//ajax请求全局未登录校验
$(document).ajaxComplete(function(event, XHR) {
	var resText = XHR.responseText;
	if (resText != "" && resText == "notLoginError") {
		alert("您没有登录或登录超时，请先登录！");
		yiqin_public_js.postFormToTopAction("top_login");
	}
});

var tempForm ={
	form : '<form method="post"></form>',
	param_input : '<input type="hidden" name="paramVal" />'
};

var yiqin_public_js = function(){
	var action = {
		//top状态提交设置
		postFormToTopAction	: function(top_value){
			 var form = $('<form></form>');  
		     form.attr('action', "/shop/index");  
		     form.attr('method', 'post');  
		     form.attr('target', '_self');
		     var my_input = $('<input type="text" name="home" />');
		     my_input.attr('value', top_value);  
		     form.append(my_input);  
		     form.submit();  
		     return false;  
		},
		
		//跳转去tiles
		toTilesAction : function(param, url){
			var $form = $(tempForm.form),
				$param_input = $(tempForm.param_input);
			$form.attr('action', url).append($param_input.val(param));
			$('body').append($form);
			$form.submit();
			return false;
		},
		
		//添加到购物车
		addProductToCart : function(productId, num){
			if($('img[name=good_img]:eq(1)').is(':animated')){
				return;
			}
			$.ajax({
		        type: "POST",
		        async: true,
		        url: "addProductToCart",
		        data: "productId="+productId+"&num="+num,
		        dataType: "text",
		        success: function(data){
		       	 if(data=='none'){
		       		 alert("该商品不存在或已经下架！");
		       	 }else if(data=='error'){
		       		 alert("加入到购物车失败，请稍后再试！");
		       	 }else{
		       		 if(data != "notLoginError"){
		       			 moveToCartBox($("#"+productId).parent().find("img"));
			       		 $("#J_MiniCartNum").html(data);
		       		 }
		       	 }
		       },
		       beforeSend: function(){},
		       complete: function(){},
		       error: function(){}
		    });
		},
		
		//查询商品详细信息
		findProductInfo : function(productId){
			$.ajax({
		        type: "POST",
		        async: true,
		        url: "findProductInfo",
		        data: "productId="+productId,
		        dataType: "json",
		        success: function(data){
		       	 if(data=='1'){
		       		 alert("该商品不存在或已经下架！");
		       	 }else if(data=='2'){
		       		 alert("加载数据异常，请稍后再试！");
		       	 }else{
		       		 
		       	 }
		       },
		       beforeSend: function(){},
		       complete: function(){},
		       error: function(){}
		    });
		}
		
	};
	
	//添加到购物车动态效果
	var moveToCartBox = function(obj) {
		var good = $(obj),
			shop_car = $('#top_cart'),
			goodLeft = good.offset().left,
			goodTop = good.offset().top,
			carLeft = shop_car.offset().left,
			carTop = shop_car.offset().top;
		if($('img[name=good_img]:eq(1)').is(':animated')){
			return;
		}
		var copyGood = good.clone();
		$('body').append(copyGood);
		copyGood.css({position:'absolute',left:goodLeft,top:goodTop});
		copyGood.animate({width:20,height:20,left:carLeft,top:carTop,opacity:0.5},800,function(){
			copyGood.remove();
		});	
	};
	
	return action;
}();
