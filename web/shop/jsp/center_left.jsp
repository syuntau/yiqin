<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var initCheckItem = function() {
	var item = '${requestScope.custom_set_param}';console.log("item : " + item);
	if (item && item.length > 0) {
		$('#my_set_' + item).css('color','#fdb45e');
	}
};
var initCenterClick = function(){
	$("#my_order_left").click(function(){
		window.location.href = "findOrderList";
	});
	
	$("#my_set_info").click(function(){
		yiqin_public_js.toTilesAction("info", "/toCenterMySet");
	});
	
	$("#my_set_address").click(function(){
		yiqin_public_js.toTilesAction("address", "/toCenterMySet");
	});
};

var checkYouHui = function() {
	var userId = '<s:property value="#session.userInfo.id"/>';
	$.ajax({
    	type: "post",
		url: "findYouHui",
		data: 'userId=' + userId,
		dataType: "json",
		success: function(data) {
			if (data != '2' && data != "1") {
				$('.customer-pro-menu ul').append('<li><a style="cursor: pointer" id="my_set_youhui">优惠政策</a></li>');
				
				$("#my_set_youhui").click(function(){
					yiqin_public_js.toTilesAction("youhui", "/toCenterMySet");
				});
			}
        },
        beforeSend: function() { },
        complete: function() { 
        	initCheckItem();
        }
	});
};

$(document).ready(function(){
	initCenterClick();
	checkYouHui();
});
</script>

<div class="center-left-yiqin">
	<div class="left-sidebar">
		<h3>订单中心</h3>
		<div class="brands-name">
			<ul class="nav nav-pills nav-stacked">
				<li><a style="cursor: pointer" id="my_order_left">我的订单</a></li>
			</ul>
		</div>
	</div>

	<div class="brands_products">
		<h3>设置</h3>
		<div class="brands-name customer-pro-menu">
			<ul class="nav nav-pills nav-stacked">
				<li><a style="cursor: pointer" id="my_set_info">用户信息</a></li>
				<li><a style="cursor: pointer" id="my_set_address">收货地址</a></li>
			</ul>
		</div>
	</div>
</div>
