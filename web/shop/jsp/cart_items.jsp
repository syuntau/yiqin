<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var cart_template = {
	cart_tr : '<tr></tr>',
	cart_td : '<td></td>',
	cart_h4 : '<h4></h4>',
	cart_a : '<a></a>',
	cart_i : '<i class="fa fa-times"></i>',
	cart_p : '<p></p>',
	cart_img : '<img/>',
	cart_div : '<div class="cart_quantity_button"></div>',
	cart_input : '<input class="cart_quantity_input" type="text" name="quantity" autocomplete="off" size="2"></input>',
};

var yiqin_cart_action = function(){
	var cart_action = {
		initCartInfo : function(){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findCartInfo.action",
	             dataType: "json",
	             success: function(data){
	            	 var $cart_info_list = $('#cart_info_list');
	            	 $cart_info_list.empty();
	            	 if(data=='1'){
	            		 $cart_info_list.append("购物车还没有心爱的商品哦，赶快去抢购吧！");
	            	 }else if(data=='2'){
	            		 $cart_info_list.append("购物车坏了，正在抢修哦，稍后再来");
	            	 }else{
	            		 appendInfoToCart(data);
	            	 }
                 },
                 beforeSend: function(){},
                 complete: function(){},
                 error: function(){}
	         });
		},
	};
	
	var appendInfoToCart = function(data){
		 var $cart_info_list = $('#cart_info_list');
		 $cart_info_list.empty();
		 $.each(data, function(i, val){
			 var $cart_tr = $(cart_template.cart_tr),
				$cart_td = $(cart_template.cart_td),
				$cart_h4 = $(cart_template.cart_h4),
				$cart_a = $(cart_template.cart_a),
				$cart_i = $(cart_template.cart_i),
				$cart_p = $(cart_template.cart_p),
				$cart_img = $(cart_template.cart_img),
			 	$cart_div = $(cart_template.cart_div),
			 	$cart_input = $(cart_template.cart_input);
			
			 $cart_td.attr('class',"cart_product");
			 $cart_td.append($cart_a.append($cart_img));
			 $cart_a.click(function(){
				 
			 });
			 $cart_img.arrt("src",val.imageUrl);
			 $cart_tr.append($cart_td);
			 
			 $cart_td = $(cart_template.cart_td),
			 $cart_a = $(cart_template.cart_a),
			 $cart_img = $(cart_template.cart_img);
			 $cart_td.attr('class',"cart_description");
			 $cart_td.append($cart_h4).append($cart_p);
			 $cart_h4.append($cart_a.append(val.productName));
			 $cart_p.append(val.productId);
 			 $cart_a.click(function(){
				 
			 });
 			$cart_tr.append($cart_td);
 			 
 			$cart_td = $(cart_template.cart_td),
 			$cart_p = $(cart_template.cart_p);
 			$cart_td.attr('class',"cart_price");
 			$cart_td.append($cart_p.append(val.price));
 			$cart_tr.append($cart_td);
 			
 			$cart_td = $(cart_template.cart_td),
 			$cart_a = $(cart_template.cart_a);
 			$cart_td.attr('class',"cart_quantity");
 			$cart_td.append($cart_div.append($cart_a).append($cart_input));
 			$cart_input.val(val.productNum);
 			$cart_a.attr('class',"cart_quantity_up").append(" + ");
 			$cart_a.click(function(){
				 
			});
 			$cart_a = $(cart_template.cart_a);
 			$cart_a.attr('class',"cart_quantity_down").append(" - ");
 			$cart_div.append($cart_a);
 			$cart_a.click(function(){
				 
			});
 			$cart_tr.append($cart_td);
 			
 			$cart_td = $(cart_template.cart_td),
 			$cart_p = $(cart_template.cart_p);
 			$cart_td.attr('class',"cart_total").append($cart_p);
 			$cart_p.attr('class',"cart_total_price").append(val.productNum*val.price);
 			$cart_tr.append($cart_td);
 			
 			$cart_td = $(cart_template.cart_td),
 			$cart_a = $(cart_template.cart_a);
 			$cart_td.attr('class',"cart_delete").append($cart_a.append($cart_i));
 			$cart_a.attr('class',"cart_quantity_delete");
 			$cart_a.click(function(){
				 
			});
 			$cart_tr.append($cart_td);
 			
 			$cart_info_list.append($cart_tr);
         });
	};
	
	
	
	
	
	
	
	return cart_action;
}();

$(document).ready(function() {
	yiqin_cart_action.initCartInfo();
});
</script>

<section id="cart_items">
	<div class="container">
		<div class="breadcrumbs">
			<ol class="breadcrumb">
			  <li><a href="#">Home</a></li>
			  <li class="active">Shopping Cart</li>
			</ol>
		</div>
		<div class="table-responsive cart_info">
			<table class="table table-condensed">
				<thead>
					<tr class="cart_menu">
						<td class="image">Item</td>
						<td class="description"></td>
						<td class="price">Price</td>
						<td class="quantity">Quantity</td>
						<td class="total">Total</td>
						<td></td>
					</tr>
				</thead>
				<tbody id="cart_info_list"></tbody>
			</table>
		</div>
	</div>
</section>