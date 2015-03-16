<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var cart_check_temp = {
	check_li : '<li></li>',
	check_input_radio :'<input type="radio"/>',
	check_label : '<label></label>',
	check_strong : '<strong></strong>',
	check_span : '<span></span>',
	check_shou : '&nbsp;&nbsp;&nbsp;&nbsp;收',
};

var yiqin_cart_check = function(){
	var action = {
		findUserAddress : function(){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findUserConf.action",
	             dataType: "json",
	             success: function(data){
	            	appendToAddress(data);
                },
                beforeSend: function(){},
                complete: function(){},
                error: function(){}
	         });
		},
		
		totalSelCartInfo : function(){
			var selCart = "<s:property value='#request.settlement_products'/>";
			if(selCart != null && selCart != ""){
				var $totalUl = $(".total_area ul"),
					totalPrice = 0;
				$totalUl.empty();
				
				<s:iterator value="#request.settlement_products">
					var $check_li = $(cart_check_temp.check_li),
    					$check_span = $(cart_check_temp.check_span),
    					toPrice = "<s:property value='count'/>"*"<s:property value='price'/>";
					
					$totalUl.append($check_li.append("<s:property value='productName'/>"));
					$check_li.append($check_span.append("<s:property value='count'/>×<s:property value='price'/> = "+toPrice));
					totalPrice += parseInt(toPrice);
				</s:iterator>
				
				var $check_li = $(cart_check_temp.check_li),
    				$check_span = $(cart_check_temp.check_span);
				$totalUl.append($check_li.append("总价"));
				$check_li.append($check_span.append(totalPrice+" 元"));
			}
		},
	};
	
	var appendToAddress = function(data){
		var $user_acc_info = $("#user_acc_info"),
			$check_li = $(cart_check_temp.check_li);
			$user_acc_info.empty();
		if(data == '1'){
			$user_acc_info.append($check_li);
			$check_li.append("您还没有配送地址信息，请点击添加按钮添加");
       	}else if(data == '2'){
       		$user_acc_info.append($check_li);
			$check_li.append("配送地址信息加载失败，请刷新页面再试");
       	}else{
       		$.each(data, function(n,val){
       			var $check_li = $(cart_check_temp.check_li),
       				$check_input_radio = $(cart_check_temp.check_input_radio),
       				$check_label = $(cart_check_temp.check_label),
       				$check_strong = $(cart_check_temp.check_strong),
       				$check_span = $(cart_check_temp.check_span),
       				$check_shou = $(cart_check_temp.check_shou);
       			
       			$check_li.attr('id',val.attribute).append($check_input_radio);
       			$check_input_radio.attr('name','accept_info').val(val.attribute);
       			$check_li.append($check_label.append($check_strong).append($check_shou).append($check_span));
       			$check_strong.append(val.userId).attr('title',val.userId);
       			$check_span.append(val.telephone).css("margin-left","50px");
       			
       			$check_span = $(cart_check_temp.check_span);
       			$check_label.append($check_span);
       			$check_span.append(val.value).css("margin-left","100px");
       			
       			$user_acc_info.append($check_li);
       		});
       	}
		
	};
	
	return action;
}();

$(document).ready(function(){
	yiqin_cart_check.findUserAddress();
	yiqin_cart_check.totalSelCartInfo();
});
</script>

<section id="do_action">
	<div class="container">
		<div class="heading">
			<h3><b><s:text name="cart.check.title"></s:text></b></h3>
		</div>
		<div class="row">
			<div class="col-sm-100">
				<h5><b><s:text name="cart.check.receiver.information"></s:text></b></h5>
				<div class="chose_area">
					<ul class="user_option" id="user_acc_info">
<!-- 						<li> -->
<!-- 							<input type="radio" name="accept_info"> -->
<!-- 							<label> -->
<%-- 								<strong title="李三三">李三三</strong>&nbsp;&nbsp;&nbsp;&nbsp;收 --%>
<%-- 								<span style="margin-left:50px;">13501005894</span> --%>
<%-- 							 	<span style="margin-left:100px;">北京 朝阳区 建国路91号金地中心B座**层****室 ****有限公司</span> --%>
<!-- 						 	</label> -->
<!-- 						</li> -->
					</ul>
					<a class="btn btn-default update" id="address_add" href="javaScript:void(0);">修改</a>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.zhifu"></s:text></b></h5>
				<div class="chose_area">
					<ul class="user_option">
						<li>
							<input type="radio" name="zhi_fu" value="zhifu_1" checked="checked">
							<label>货到付款</label>
						</li>
						<li>
							<input type="radio" name="zhi_fu" value="zhifu_2">
							<label>公司转账</label>
						</li>
						<li>
							<input type="radio" name="zhi_fu" value="zhifu_3">
							<label>邮局汇款</label>
						</li>
					</ul>
				</div>
			</div>
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.peisong"></s:text></b></h5>
				<div class="chose_area">
					<ul class="user_option">
						<li>
							<input type="radio" name="pei_song" value="peisong_1" checked="checked">
							<label>依勤送货</label>
						</li>
						<li>
							<input type="radio" name="pei_song" value="peisong_2">
							<label>上门自提</label>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.fapiao"></s:text></b></h5>
				<div class="chose_area">
					<ul class="user_option">
						<li>
							<label>发票类型：</label>
							<span>普通发票</span>
						</li>
						<li>
							<label>发票抬头：</label>
							<span>北京XXXXX有限公司</span>
						</li>
						<li>
							<label>发票内容：</label>
							<span>办公用品</span>
						</li>
					</ul>
					<a class="btn btn-default update" href="">修改</a>
				</div>
			</div>
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.product.list"></s:text></b></h5>
				<div class="total_area">
					<ul></ul>
					<a class="btn btn-default check_out" href="">提交订单</a>
				</div>
			</div>
		</div> 
	</div>
</section>