<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
var findCartNum = function(){
	var logUser = "<s:property value='#session.userInfo'/>";
	if(logUser == null || logUser == ""){
		$("#J_MiniCartNum").html("0");
	}else{
		$.ajax({
	        type: "POST",
	        async: true,
	        url: "findCartNum",
	        dataType: "text",
	        success: function(data){
	       	 if(data!='error'){
	       		 $("#J_MiniCartNum").html(data);
	       	 }else{
	       		$("#J_MiniCartNum").html("数量加载失败"); 
	       	 }
	       },
	       beforeSend: function(){},
	       complete: function(){},
	       error: function(){}
	    });
	}
};

$(document).ready(function(){
	findCartNum();
});
</script>

<div class="header_top"><!--header_top-->
	<div class="container">
		<div class="row">
			<div class="col-sm-5">
				<div class="contactinfo">
					<ul class="nav nav-pills">
						<li><a><i class="fa fa-phone"></i> <s:text name="yiqin.phone" /></a></li>
						<li><a><i class="fa fa-envelope"></i> <s:text name="yiqin.mail" /></a></li>
					</ul>
				</div>
			</div>
			<div class="col-sm-7">
				<div class="shop-menu pull-right">
					<ul class="nav navbar-nav" id="shop_header_ul">
						<li><a class="shop_header" id="top_account"><i class="fa fa-user"></i> <s:text name="shop.header.top.accout" /></a></li>
						<s:if test="#session.userStatStatus==true">
						<li><a class="shop_header" id="top_stat"><i class="fa fa-bar-chart"></i> 统计图表</a></li>
						</s:if>
						<li><a class="shop_header" id="top_quick_shopping"><i class="fa fa-heart"></i> <s:text name="shop.header.top.quick.shopping" /></a></li>
						<li><a class="shop_header" id="top_code_shopping"><i class="fa fa-qrcode"></i> <s:text name="shop.header.top.code.shopping" /></a></li>
						<li>
							<a class="shop_header" id="top_cart"><i class="fa fa-shopping-cart"></i> <s:text name="shop.header.top.cart" />
								<strong id="J_MiniCartNum" class="h"></strong>
							</a>
						</li>
						<s:if test="#session.userInfo==null">
						<li><a class="shop_header" id="top_login"><i class="fa fa-sign-in"></i> <s:text name="shop.header.top.login" /></a></li>
						<li><a class="shop_header" id="top_register"><i class="fa fa-lock"></i> <s:text name="shop.header.top.register" /></a></li>
						
						</s:if>
						<s:else>
						<li><a class="shop_header" id="top_logout"><i class="fa fa-sign-out"></i> <s:text name="shop.header.top.logout" /></a></li>
						</s:else>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div><!--/header_top-->
