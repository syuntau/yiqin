<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="center-right padding-right">
	<section id="cart_items">
		<div>
			<div class="table-responsive cart_info">
				<table class="table table-condensed">
					<thead>
						<tr class="cart_menu">
							<td class="image">订单信息</td>
							<td class="description">收货人</td>
							<td class="price">单价</td>
							<td class="quantity">数量</td>
							<td class="total">实付款</td>
							<td class="total">交易状态</td>
						</tr>
					</thead>
					<tbody>
						<tr style="background-color: #F0F0E9;">
							<td colspan="5">
								<div class="summary" style="margin-left:20px">
									 <span title="2015-02-27 21:11">2015-02-27</span>
									 <span style="margin-left:50px">订单号：<em>10000002</em></span>
								 </div>
							</td>
							<td colspan="1">
								<a href=""><i class="fa fa-times"></i></a>
							</td>
						</tr>
						<tr>
							<td class="cart_product">
								<img src="/shop/images/home/gallery2.jpg" width="110px" style="vertical-align:middle;float: left;margin-bottom:5px"/>
								<h4><a href="">Colorblock Scuba Colorblock Scuba Colorblock Scuba</a></h4>
								<p>商品ID: 1089772</p>
							</td>
							<td class="cart_description">
								<p>张媛媛</p>
							</td>
							<td class="cart_price">
								<p>59.0</p>
							</td>
							<td class="cart_quantity">
								<p>2</p>
							</td>
							<td class="cart_total">
								<p>118.0</p>
							</td>
							<td class="cart_quantity">
								<p>交易成功</p>
							</td>
						</tr>
					</tbody>
					<tbody>
						<tr style="background-color: #F0F0E9;">
							<td colspan="5">
								<div class="summary" style="margin-left:20px">
									 <span title="2015-02-27 21:11">2015-02-27</span>
									 <span style="margin-left:50px">订单号：<em>10000002</em></span>
								 </div>
							</td>
							<td colspan="1">
								<a href=""><i class="fa fa-times"></i></a>
							</td>
						</tr>
						<tr>
							<td class="cart_product">
								<img src="/shop/images/home/gallery2.jpg" width="110px" style="vertical-align:middle;float: left;margin-bottom:5px"/>
								<h4><a href="">Colorblock Scuba Colorblock Scuba Colorblock Scuba</a></h4>
								<p>商品ID: 1089772</p>
							</td>
							<td class="cart_description">
								<p>张媛媛</p>
							</td>
							<td class="cart_price">
								<p>59.0</p>
							</td>
							<td class="cart_quantity">
								<p>2</p>
							</td>
							<td class="cart_total">
								<p>118.0</p>
							</td>
							<td class="cart_quantity">
								<p>交易成功</p>
							</td>
						</tr>
						<tr>
							<td class="cart_product">
								<img src="/shop/images/home/gallery2.jpg" width="110px" style="vertical-align:middle;float: left;margin-bottom:5px"/>
								<h4><a href="">Colorblock Scuba Colorblock Scuba Colorblock Scuba</a></h4>
								<p>商品ID: 1089772</p>
							</td>
							<td class="cart_description">
								<p>张媛媛</p>
							</td>
							<td class="cart_price">
								<p>59.0</p>
							</td>
							<td class="cart_quantity">
								<p>2</p>
							</td>
							<td class="cart_total">
								<p>118.0</p>
							</td>
							<td class="cart_quantity">
								<p>交易成功</p>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</section>
</div>
<script type="text/javascript">
	var result_info = "<s:property value='#request.submit_order_error'/>";
	if(result_info==1){
		alert("订单信息填写有误，请再次填写提交订单！");
	}else if(result_info==2){
		alert("订单提交失败，请稍后再试！");
	}
</script>