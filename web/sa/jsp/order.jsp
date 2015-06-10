<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var sa_conf = {
	option : '<option></option>',
	tr : '<tr></tr>',
	td : '<td></td>',
};

var sa_order = function(){
	var action = {
		load_user : function() {
			var $userList = $('.user-list');
			$.ajax({
	            type: "post",
	            url: "editBestProduct_getUserList",
	            dataType: "json",
	            success: function(data) {
		           	 if (data=='2') {
		           		$userList.html("<s:text name='msg.no.item'><s:param><s:text name='msg.param.user' /></s:param></s:text>");
		           	 } else {
		           		var $userListSelect = $userList.find('select');
						$.each(data, function(i, val) {
							var $op = $(sa_conf.option);
							$op.val(val.id).html(val.name);
							$userListSelect.append($op);
						});
						$userList.find('option:first').attr('selected', true);
						$('.user-select').removeClass('display-off');
		           	 }
	            },
	            beforeSend: function() {
	            	$userList.prepend($(com_conf.loading_icon));
	        	},
	            complete: function() {
	            	$userList.find('span').remove();
	            	var userId = "<s:property value='userId'/>";
	            	if(userId!=null && userId!=""){
	            		$userList.find('select').val(userId);
	            	}
	            }
			});
		},
		
		modifyBootBox : function(orderId,statusStr){
			bootbox.dialog({  
                message: "<span>订单号："+orderId+"</span><span style='margin-left:50px;'>当前状态："+statusStr+"</span>"
                		+"<div style='margin-top:20px;'>请选择要修改的订单状态：<select id='mod_sel_status'><option value='1'>等待付款</option><option value='2'>等待收货</option>"	
						+"<option value='3'>已完成</option><option value='0'>已取消</option></select></div>",  
                title: "订单状态修改", 
                buttons: {  
                    Cancel: {  
                        label: "取消",  
                        className: "btn-default",  
                    }
                    , OK: {  
                        label: "更新",  
                        className: "btn-primary",  
                        callback: function () {
                        	var status = $("#mod_sel_status").val();
                        	sa_order.updateOrderStatus(orderId, status);
                        }  
                    }  
                }  
            });  
		},
		
		updateOrderStatus : function(orderId,status){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "updateOrderStatus",
	             data : "userId=<s:property value='userId'/>&orderId="+orderId+"&status="+status,
	             dataType: "text",
	             success: function(data){
	            	if(data=='1'){
	            		alert("修改成功！");
	            	}else if(data=='2'){
	            		alert("订单状态修改失败！");
	            	}else if(data=='3'){
	            		alert("修改异常，请稍后再试！");
	            	}
	             },
	             beforeSend: function(){},
	             complete: function(data){
	            	 sa_order.orderSearch();
	             },
	             error: function(){}
	         });
		},
		
		initOrderCheck : function(){
			 var startTime = "<s:property value='startTime'/>";
			 $("input[name=startTime]").val(startTime);
			 
			 var endTime = "<s:property value='endTime'/>";
			 $("input[name=endTime]").val(endTime);
			 
			 $('#order_search_btn').on('click', function() {
				 sa_order.orderSearch();
			 });
			 
			 $('#order_export_btn').on('click', function() {
				 sa_order.exportOrderExcel();
			 });
		},
			
		orderSearch : function(){
			$("#order_search_btn").after($(com_conf.loading_icon));
			var userId= $("#user_select").val(),
				startTime = $("input[name=startTime]").val(),
				endTime = $("input[name=endTime]").val();
			window.location.href = "findSAOrderList?startTime="+startTime+"&endTime="+endTime+"&userId="+userId;
		},
		
		exportOrderExcel : function(){
			<s:if test="page.results==null">
				alert("没有要导出的订单数据");
				return;
			</s:if>
			var startTime = "<s:property value='startTime'/>",
				endTime = "<s:property value='endTime'/>",
				userId = "<s:property value='userId'/>";
			window.location.href = "exportOrderExcel?startTime="+startTime+"&endTime="+endTime+"&userId="+userId;
		},
	};
	
	var toIndexPage = function(pageIndex){
		var startTime = "<s:property value='startTime'/>",
			endTime = "<s:property value='endTime'/>",
			userId = "<s:property value='userId'/>";
		window.location.href = "findSAOrderList?startTime="+startTime+"&endTime="+endTime+"&userId="+userId+"&pageIndex="+pageIndex;
	};
	
	return action;	
}();

$(document).ready(function(){
	sa_order.load_user();
	sa_order.initOrderCheck();
});
</script>
        <!-- Navigation -->

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">订单管理</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12" style="margin-bottom:20px;">
                     <form role="form" class="form-inline user-form" style="float: left;">
                        <div class="form-group user-list">
                            <label class="user-select display-off"><s:text name="sa.pd.lbl.select.user" /></label>
                            <select class="form-control user-select display-off" id="user_select"></select>
                        </div>
                    </form>
                     <form role="form" class="form-inline user-form" style="float: left;margin-left:50px;">
                        <div class="form-group">
                            <label class="user-select display-off">选择时间</label>
                            <input type="text" class="form-control" name="startTime" onclick="WdatePicker()"></input>至
                            <input type="text" class="form-control" name="endTime"  onclick="WdatePicker()"></input>
                        </div>
                        <button type="button" class="btn btn-info btn-user-submit user-select display-off" id="order_search_btn"><s:text name="sa.btn.query" /></button>
                        
                        <button type="button" style="margin-left:130px;" class="btn btn-info btn-user-submit user-select display-off" id="order_export_btn">导出Excel</button>
                    </form>
                </div>
				<table class="table table-condensed">
					<thead>
						<tr class="cart_menu">
							<td class="image" width="32%">订单信息</td>
							<td width="10%" style="text-align:center;">单价</td>
							<td width="8%" style="text-align:center;">数量</td>
							<td width="10%" style="text-align:center;">实付款</td>
							<td width="15%" style="text-align:center;">订单时间</td>
							<td width="13%" style="text-align:center;">订单状态</td>
							<td width="8%" style="text-align:center;">订单修改</td>
						</tr>
					</thead>
					<s:if test="page.results==null">
						<tbody>
							<tr>
								<td colspan="7">
									没有符合条件的订单信息
								</td>
							</tr>
						</tbody>
					</s:if>
					<s:else>
					<s:iterator value="page.results" var="order">
						<tbody>
							<tr style="background-color: #F0F0E9;">
								<td colspan="7">
									<div class="summary" style="margin-left:20px">
										 <span>订单号：<s:property value="#order.id"/></span>
										 <span style="margin-left:50px">收货人：<s:property value="#order.name"/></span>
										 <span style="float:right;margin-right:30px;" onclick="sa_order.modifyBootBox(<s:property value='#order.id'/>,'<s:property value='#order.status'/>');">
										 	<i class="fa fa-cog fa-2 cursor-pointer" style="color:#337ab7" title="修改订单"></i>
										 </span>
									</div>
								</td>
							</tr>
							<s:iterator value="#order.productList" var="product" status="st">
								 <tr>
									<td class="cart_product">
										<a href="javaScript:;"><img onclick="yiqin_public_js.toTilesAction(<s:property value='#product.productId'/>, 'toProductDetails');" src="<s:property value="#product.imgUrl"/>" width="110px" style="vertical-align:middle;float: left;margin-bottom:5px"/></a>
										<h4><a href="javaScript:;" onclick="yiqin_public_js.toTilesAction(<s:property value='#product.productId'/>, 'toProductDetails');"><s:property value="#product.productName"/></a></h4>
										<p><s:text name="shop.product.details.productId"/><s:property value="#product.productId"/></p>
										<p><s:text name="cart.item.product.color"/><s:property value="#product.productInfo"/></p>
									</td>
									<td class="cart_price" style="text-align:center;">
										<p><s:property value="#product.zhekouPrice"/></p>
										<p style="font-size:14px;color:#FE980F">
											<s:text name="shop.product.label.zhekou"/>
											<s:property value="#order.zhekou"/>
										</p>
										<del><s:text name="shop.product.label.yuan.price"/>
											 <s:property value="#product.price" />
										</del>
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
										<td colspan="4"></td>
									</s:else>
								</tr>
							</s:iterator>
						</tbody>
					</s:iterator>
				</s:else>
			</table>
        </div>
    </div>
        <!-- /#page-wrapper -->
