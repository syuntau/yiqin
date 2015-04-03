<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var cart_check_temp = {
	check_li : '<li></li>',
	check_input_radio :'<input type="radio"/>',
	check_label : '<label></label>',
	check_strong : '<strong></strong>',
	check_span : '<span></span>',
	check_shou : '&nbsp;&nbsp;&nbsp;&nbsp;收',
	check_defType : '&nbsp;&nbsp;&nbsp;&nbsp;默认地址',
	saveOrUpdate : "",
	
	check_from : '<form method="post" action="submitOrder"></form>',
	check_input_hidden : '<input type="hidden"/>',
};

var yiqin_cart_check = function(){
	var REX=/^[\s\u3000]*|[\s\u3000]*$/g;
	
	var action = {
		submitOrder : function(){
			if(confirm("请确认订单信息是否正确，是否确认提交订单？")){
				var addressAttr = $("input:checked[name=accept_info]").val(),
					zhifu = $("input:checked[name=zhi_fu]").val(),
					peisong = $("input:checked[name=pei_song]").val(),  
					fapiaotaitou = $("input[name=fapiaotaitou]").val().replace(REX, ""),
					fapiaomingxi = $("input[name=fapiaomingxi]").val().replace(REX, ""),
					productIds = "<s:property value='#request.submit_ProductIds'/>",
					$check_from = $(cart_check_temp.check_from);
					
				var $check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','addressAttr').val(addressAttr));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','zhifu').val(zhifu));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','peisong').val(peisong));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','fapiaotaitou').val(fapiaotaitou));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','fapiaomingxi').val(fapiaomingxi));
					$check_input_hidden = $(cart_check_temp.check_input_hidden);
					$check_from.append($check_input_hidden.attr('name','productIds').val(productIds));
					$check_from.submit();
			}
		},
			
		findUserAddress : function(){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findUserConf",
	             dataType: "json",
	             success: function(data){
	            	appendToAddress(data);
                 },
                 beforeSend: function(){},
                 complete: function(){},
                 error: function(){}
	         });
		},
		
		editAddressByAttr : function(attribute){
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "findUserConf",
	             data : "attribute="+attribute,
	             dataType: "json",
	             success: function(data){
	            	var $modal = $("#EditReceiveInfo");
	            	if(data=='1'){
	            		alert("本条地址信息已被删除，请刷新页面！");
	            	}else if(data=='2'){
	            		alert("加载地址信息失败，请稍后再试！");
	            	}else{
	            		var userName = data.value.split("_receive_")[0],
	       					telephone = data.value.split("_receive_")[1],
	       					address = data.value.split("_receive_")[2];
	            		$modal.find("input[name=receive_name]").val(userName);
	            		$modal.find("input[name=receive_tel]").val(telephone);
	            		$modal.find("input[name=receive_area]").val(address);
	            		$modal.data("receive_aType",data.attribute);
	            		if(data.attribute=="address_def"){
	            			$modal.find("input[name=receive_default]").prop("checked",true);
	            		}
	            	}
	             },
	             beforeSend: function(){},
	             complete: function(){},
	             error: function(){}
	         });
		},
		
		saveOrUpdateAddress : function(){
			var dataParm = checkReceiveAddress();
			if(!dataParm){
				return false;
			}
			dataParm = dataParm + "&saveOrUpdate="+cart_check_temp.saveOrUpdate;
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "saveOrUpdateAddress",
	             data : dataParm,
	             dataType: "json",
	             success: function(data){
	            	 if(data=='1'){
	            		 $("#receive_error").html("信息填写不正确，请再次填写！");
	            		 return false;
	            	 }else if(data=='2'){
	            		 $("#receive_error").html("保存失败，请稍后重试！");
	            		 return false;
	            	 }else if(data=='3'){
	            		 yiqin_cart_check.findUserAddress();
	            		 return false;
	            	 }else if(data=='4'){
	            		 $("#receive_error").html("地址数量超出最大数量10个");
	            		 return false;
	            	 }
	             },
	             beforeSend: function(){},
	             complete: function(){
	            	 $('#EditReceiveInfo').modal('hide');
	             },
	             error: function(){}
	         });
		},
		
		deleteUserAddress : function(attribute){
			if(confirm("确认要删除选择的地址信息吗？")){
				$.ajax({
		             type: "POST",
		             async: true,
		             url: "deleteUserAddress",
		             data : "attribute="+attribute,
		             dataType: "json",
		             success: function(data){
		            	if(data=='1'){
		            		alert("本条收货信息不存在，请再次确认！");
		            	}else if(data=='2'){
		            		alert("删除失败，请稍后再试！");
		            	}else if(data=='3'){
		            		yiqin_cart_check.findUserAddress();
		            	}
		             },
		             beforeSend: function(){},
		             complete: function(){},
		             error: function(){}
		         });
			}
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
			$("#address_add").click(function(){
				cart_check_temp.saveOrUpdate = "save";
				emptyReceiveModal();
			});
		if(data == '1'){
			$user_acc_info.append($check_li);
			$check_li.append("您还没有配送地址信息，请点击添加按钮添加");
			$("#address_update").remove();
			$("#address_delete").remove();
       	}else if(data == '2'){
       		$user_acc_info.append($check_li);
			$check_li.append("配送地址信息加载失败，请刷新页面再试");
       	}else{
       		if(data.length>=10){
       			$("#address_add").remove();
       		}
			$("#address_update").click(function(){
				cart_check_temp.saveOrUpdate = "update";
				emptyReceiveModal();
				var type = $("input:checked[name=accept_info]").val();
				yiqin_cart_check.editAddressByAttr(type);
			});
			$("#address_delete").click(function(){
				var type = $("input:checked[name=accept_info]").val();
				yiqin_cart_check.deleteUserAddress(type);
			});
			var isDef = false;
			$.each(data, function(i,tempVal){
				if(tempVal.attribute=="address_def"){
					isDef = true;
				}
			});
       		$.each(data, function(n,val){
       			var $check_li = $(cart_check_temp.check_li),
       				$check_input_radio = $(cart_check_temp.check_input_radio),
       				$check_label = $(cart_check_temp.check_label),
       				$check_strong = $(cart_check_temp.check_strong),
       				$check_span = $(cart_check_temp.check_span),
       				check_shou = cart_check_temp.check_shou,
       				check_defType = cart_check_temp.check_defType,
       				userName = val.value.split("_receive_")[0],
       				telephone = val.value.split("_receive_")[1],
       				address = val.value.split("_receive_")[2];
       			
       			$check_li.attr('id',val.attribute).append($check_input_radio);
       			$check_input_radio.attr('name','accept_info').val(val.attribute);
       			if(isDef){
       				if(val.attribute=="address_def"){
       					$check_input_radio.attr("checked",true);
       				}
       			}else{
       				if(n==0){
       					$check_input_radio.attr("checked",true);
       				}
       			}
       			$check_li.append($check_label.append($check_strong).append(check_shou).append($check_span));
       			$check_strong.append(userName).attr('title',userName);
       			$check_span.append(telephone).css("margin-left","50px");
       			
       			$check_span = $(cart_check_temp.check_span);
       			$check_label.append($check_span);
       			$check_span.append(address).css("margin-left","100px");
       			if(val.attribute=="address_def"){
       				$check_span.append(check_defType);
       			}
       			
       			$user_acc_info.append($check_li);
       		});
       	}
	};
	
	var checkReceiveAddress = function(){
		var $modal = $("#EditReceiveInfo"),
			userName = $modal.find("input[name=receive_name]").val().replace(REX, ""),
			telephone = $modal.find("input[name=receive_tel]").val().replace(REX, ""),
			address = $modal.find("input[name=receive_area]").val().replace(REX, ""),
			defObj = $modal.find("input[name=receive_default]"),
			oldAttribute = $modal.data("receive_aType"),
			attribute = "";
		if(userName==""){
			$modal.find("input[name=receive_name]").focus();
			$("#receive_error").html("收货人员必须填写");
			return false;
		}
		if(telephone==""){
			$modal.find("input[name=receive_tel]").focus();
			$("#receive_error").html("联系电话必须填写");
			return false;
		}
		if(address==""){
			$modal.find("input[name=receive_area]").focus();
			$("#receive_error").html("收货地址必须填写");
			return false;
		}
		if(!checkShouHuoUser(userName)){
			$modal.find("input[name=receive_name]").focus();
			$("#receive_error").html("收货人员不能包含特殊字符");
			return false;
		}
		if (!checkPhone(telephone)) {
			$modal.find("input[name=receive_tel]").focus();
			$("#receive_error").html("联系电话格式错误，请重新输入");
			return false;
		}
		if(defObj.is(':checked')){
			attribute = "address_def";
		}
		return "attribute="+attribute+"&address="+address+"&telephone="+telephone+"&userName="+userName+"&oldAttribute="+oldAttribute;
	};
	
	var checkPhone = function(value) {
		var REX_PHONE = /^[1][0-9]{10}$/;
		var REX_DIANH = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		if (REX_PHONE.test(value) || REX_DIANH.test(value)) {
			return true;
		}
		return false;
	};
	
	var checkShouHuoUser = function(value){
		var NAMEEXP = /^[a-zA-Z\u4e00-\u9fa5- ]*$/;
		if (NAMEEXP.test(value)) {
			return true;
		}
		return false;
	};
	
	var emptyReceiveModal = function(){
		var $modal = $("#EditReceiveInfo");
		$("#receive_error").html("");
		$modal.find("input[name=receive_name]").val("");
		$modal.find("input[name=receive_tel]").val("");
		$modal.find("input[name=receive_area]").val("");
		$modal.find("input[name=receive_default]").prop("checked",false);
		$modal.data("receive_aType","");
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
					<ul class="user_option" id="user_acc_info"></ul>
					<button class="btn btn-default update" id="address_add" data-toggle="modal" data-target="#EditReceiveInfo">添加</button>
					<button class="btn btn-default update" id="address_update" data-toggle="modal" data-target="#EditReceiveInfo">修改</button>
					<button class="btn btn-default update" id="address_delete">删除</button>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.zhifu"></s:text></b></h5>
				<div class="chose_area">
					<ul class="user_option">
						<li>
							<input type="radio" name="zhi_fu" value="1" checked="checked">
							<label>货到付款</label>
						</li>
						<li>
							<input type="radio" name="zhi_fu" value="2">
							<label>公司转账</label>
						</li>
						<li>
							<input type="radio" name="zhi_fu" value="3">
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
							<input type="radio" name="pei_song" value="1" checked="checked">
							<label>依勤送货</label>
						</li>
						<li>
							<input type="radio" name="pei_song" value="2">
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
							<input type="text" name="fapiaotaitou" value="个人">
						</li>
						<li>
							<label>发票明细：</label>
							<input type="text" name="fapiaomingxi" value="办公用品">
						</li>
					</ul>
				</div>
			</div>
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.product.list"></s:text></b></h5>
				<div class="total_area">
					<ul></ul>
					<a class="btn btn-default check_out" href="javaScript:yiqin_cart_check.submitOrder();">提交订单</a>
				</div>
			</div>
		</div> 
	</div>
	
	<!-- alert modal -->
	<div class="modal" id="EditReceiveInfo" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
		             <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		             <h5 class="modal-title"><strong>填写收货信息</strong></h5>
	         	</div>
	         	<div class="modal-body">
					<ul>
						<li>
							<label>收货人员：</label>
							<input type="text" name="receive_name">
						</li>
						<li>
							<label>联系电话：</label>
							<input type="text" name="receive_tel">
						</li>
						<li>
							<label>收货地址：</label>
							<input type="text" name="receive_area">
						</li>
						<li>
							<label>是否设置为默认地址 </label>
							<input type="checkbox" name="receive_default" value="address_def">
						</li>
						<li>
							<span style="color:red" id="receive_error"></span>
						</li>
					</ul>
	         	</div>
	         	<div class="modal-footer">
					<a class="btn btn-default check_out" href="javaScript:yiqin_cart_check.saveOrUpdateAddress();">保存</a>
				</div>
			</div>
		</div>
	</div>
</section>