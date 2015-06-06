<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
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

$(document).ready(function(){
	initCenterClick();
});
</script>

<div class="center-left-yiqin">
	<div class="left-sidebar">
		<h3>订单中心</h3>
		<div class="brands-name">
			<ul class="nav nav-pills nav-stacked">
				<li><a href="javaScript:;" id="my_order_left">我的订单</a></li>
			</ul>
		</div>
	</div>

	<div class="brands_products">
		<h3>设置</h3>
		<div class="brands-name">
			<ul class="nav nav-pills nav-stacked">
				<li><a href="javaScript:;" id="my_set_info">个人信息</a></li>
				<li><a href="javaScript:;" id="my_set_address">收货地址</a></li>
			</ul>
		</div>
	</div>
</div>
