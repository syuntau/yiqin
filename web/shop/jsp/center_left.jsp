<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var initCenterClick = function(){
	$("#my_order_left").click(function(){
		window.location.href = "findOrderList";
	});
	
	
}

$(document).ready(function(){
	initCenterClick();
});
</script>

<div class="center-left">
	<div class="left-sidebar">
		<h2>订单中心</h2>
		<div class="brands-name">
			<ul class="nav nav-pills nav-stacked">
				<li><a href="javaScript:;" id="my_order_left">我的订单</a></li>
			</ul>
		</div>
	</div>

	<div class="brands_products">
		<h2>设置</h2>
		<div class="brands-name">
			<ul class="nav nav-pills nav-stacked">
				<li><a href="">个人信息</a></li>
				<li><a href="">收货地址</a></li>
			</ul>
		</div>
	</div>
</div>
