<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
function toIndexPage(pageIndex){
	
}
</script>

<div class="search-01">
	<input id="ip_keyword" name="" type="text" class="s-itxt" value="商品名称、商品编号、订单编号" onfocus="if (this.value==this.defaultValue) this.value=''" onblur="if (this.value=='') this.value=this.defaultValue" onkeydown="javascript:if(event.keyCode==13) OrderSearch('ip_keyword');">
    <a href="javascript:;" class="btn-13" onclick="OrderSearch('ip_keyword')" clstag="click|keycount|orderinfo|search">查 询</a>
</div>
<div class="center-right padding-right">
	<section id="cart_items">
		<div>
			<div class="table-responsive cart_info" style="margin-bottom:10px;">
				<table class="table table-condensed">
					<thead>
						<tr class="cart_menu">
							<td class="image" width="40%">订单信息</td>
							<td width="10%" style="text-align:center;">单价</td>
							<td width="8%" style="text-align:center;">数量</td>
							<td width="10%" style="text-align:center;">实付款</td>
							<td width="15%" style="text-align:center;">
								<select id="date">
									<option value="1" selected="selected">最近三个月</option>
									<option value="2">今年内</option>
									<option value="2014">2014年</option>
									<option value="2013">2013年</option>
									<option value="2012">2012年</option>
									<option value="3">2012年以前</option>
								</select>
							</td>
							<td width="13%" style="text-align:center;">
								<select id="state">
									<option value="10" selected="selected">全部状态</option>
									<option value="1">等待付款</option>
									<option value="2">等待收货</option>
									<option value="3">已完成</option>
									<option value="0">已取消</option>
								</select>
						    </td>
						</tr>
					</thead>
					<s:if test="page.results==null">
						没有符合条件的订单信息
					</s:if>
					<s:else>
						<s:iterator value="page.results" var="order">
							<tbody>
								<tr style="background-color: #F0F0E9;">
									<td colspan="5">
										<div class="summary" style="margin-left:20px">
											 <span>订单号：<s:property value="#order.id"/></span>
											 <span style="margin-left:50px">收货人：<s:property value="#order.name"/></span>
										 </div>
									</td>
									<td colspan="1">
										<a href=""><i class="fa fa-times"></i></a>
									</td>
								</tr>
								<s:iterator value="#order.productList" var="product" status="st">
									 <tr>
										<td class="cart_product">
											<img src="<s:property value="#product.imgUrl"/>" width="110px" style="vertical-align:middle;float: left;margin-bottom:5px"/>
											<h4><a href=""><s:property value="#product.productName"/></a></h4>
											<p>商品ID: <s:property value="#product.productId"/></p>
										</td>
										<td class="cart_price" style="text-align:center;">
											<p><s:property value="#product.price"/></p>
										</td>
										<td style="text-align:center;">
											<p><s:property value="#product.count"/></p>
										</td>
										<s:if test="#st.index==0">
											<td style="text-align:center;">
												<p><s:property value='#order.zongjia'/></p>
											</td>
											<td style="text-align:center;">
												<p><s:property value='#order.crateDate'/></p>
											</td>
											<td style="text-align:center;">
												<p><s:property value='#order.status'/></p>
											</td>
										</s:if>
										<s:else>
											<td colspan="3"></td>
										</s:else>
									</tr>
								</s:iterator>
							</tbody>
						</s:iterator>
					</s:else>
				</table>
			</div>
			<div style="text-align: center;width: 99%">
				<jsp:include page="/shop/jsp/common/page.jsp"></jsp:include>
			</div>
		</div>
	</section>
</div>