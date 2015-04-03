<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var address_list_temp = {
	list_div : '<div></div>',
	list_ul : '<ul class="user_option"></ul>',
	list_li : '<li style="margin-bottom:10px;"></li>',
	list_label : '<label></label>',
	list_span : '<span></span>',
	list_a : '<a class="address-list-abtn"></a>',
	saveOrUpdate : ""
};

var yiqin_my_address = function(){
	var REX=/^[\s\u3000]*|[\s\u3000]*$/g;
	
	var action = {
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
	            	var $modal = $("#address_list_alert");
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
			dataParm = dataParm + "&saveOrUpdate="+address_list_temp.saveOrUpdate;
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
	            		 yiqin_my_address.findUserAddress();
	            		 return false;
	            	 }else if(data=='4'){
	            		 $("#receive_error").html("地址数量超出最大数量10个");
	            		 return false;
	            	 }
	             },
	             beforeSend: function(){},
	             complete: function(){
	            	 $('#address_list_alert').modal('hide');
	             },
	             error: function(){}
	         });
		},
		
		defaultAddressSet : function(oldAttribute){
			var dataParm = "attribute=address_def&saveOrUpdate="+address_list_temp.saveOrUpdate+"&oldAttribute="+oldAttribute;
			$.ajax({
	             type: "POST",
	             async: true,
	             url: "saveOrUpdateAddress",
	             data : dataParm,
	             dataType: "json",
	             success: function(data){
	            	if(data=='2'){
	            		 alert("设置默认地址失败，请稍后重试！");
	            		 return false;
	            	 }else if(data=='3'){
	            		 yiqin_my_address.findUserAddress();
	            		 return false;
	            	 }
	             },
	             beforeSend: function(){},
	             complete: function(){},
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
		            		yiqin_my_address.findUserAddress();
		            	}
		             },
		             beforeSend: function(){},
		             complete: function(){},
		             error: function(){}
		         });
			}
		},
	};
	
	var appendToAddress = function(data){
		var $my_address_list = $("#my_address_list"),
			$add_list_btn = $("#add_list_btn");
			$my_address_list.empty();
		if(data == '1'){
			$add_list_btn.find("span").empty().append("您已创建<span class='ftx-02'>0</span>个收货地址，最多可创建<span class='ftx-02'>10</span>个");
			$my_address_list.append("您还没有收货地址信息，请添加！");
       	}else if(data == '2'){
       		$my_address_list.append("收货地址信息加载失败，请刷新页面再试");
       	}else{
       		if(data.length<10){
       			$add_list_btn.find("a").click(function(){
       				address_list_temp.saveOrUpdate = "save";
    				emptyReceiveModal();
       			});
       		}else{
       			$add_list_btn.find("a").removeAttr("data-toggle");
       			$add_list_btn.find("a").removeAttr("data-target");
       		}
       		$add_list_btn.find("span").empty().append("您已创建<span class='ftx-02'>"+data.length+"</span>个收货地址，最多可创建<span class='ftx-02'>10</span>个");
       		
       		$.each(data, function(n,val){
       			var $list_div = $(address_list_temp.list_div),
       				$list_ul = $(address_list_temp.list_ul),
       				$list_li = $(address_list_temp.list_li),
       				$list_label = $(address_list_temp.list_label),
       				$list_span = $(address_list_temp.list_span),
       				$list_a = $(address_list_temp.list_a),
       				userName = val.value.split("_receive_")[0],
       				telephone = val.value.split("_receive_")[1],
       				address = val.value.split("_receive_")[2];
       			
       			$list_div.attr('class',"row");
       			$list_div_col = $(address_list_temp.list_div);
       			$list_div_col.attr({"class":"col-sm-6","style":"width:90%"});
       			$list_div.append($list_div_col);
       			$list_div_area = $(address_list_temp.list_div);
       			$list_div_area.attr("class","chose_area");
       			$list_div_col.append($list_div_area);
       			
       			$list_div_area.append($list_ul.append($list_li.attr("style","margin-bottom:10px;")));
       			$list_li.append($list_label.append("收货人员：")).append($list_span.append(userName));
       			
       			$list_li = $(address_list_temp.list_li),
       			$list_label = $(address_list_temp.list_label),
       			$list_span = $(address_list_temp.list_span);
       			$list_li.attr("style","margin-bottom:10px;");
       			$list_li.append($list_label.append("联系电话：")).append($list_span.append(telephone));
       			$list_ul.append($list_li);
       			
       			$list_li = $(address_list_temp.list_li),
       			$list_label = $(address_list_temp.list_label),
       			$list_span = $(address_list_temp.list_span);
       			$list_li.attr("style","margin-bottom:10px;");
       			$list_li.append($list_label.append("收货地址：")).append($list_span.append(address));
       			$list_ul.append($list_li);
       			
       			$list_div_set = $(address_list_temp.list_div);
       			$list_div_set.attr("style","margin-left:800px;");
       			if(val.attribute=="address_def"){
       				$list_span = $(address_list_temp.list_span);
       				$list_div_set.append($list_span.append("默认地址").attr("class","ftx-04"));
       			}else{
       				$list_a = $(address_list_temp.list_a);
	       			$list_div_set.append($list_a.append("设置为默认"));
	       			$list_a.click(function(){
	       				address_list_temp.saveOrUpdate = "setdef";
						yiqin_my_address.defaultAddressSet(val.attribute);
	       			});
       			}
       			
       			$list_a = $(address_list_temp.list_a);
       			$list_div_set.append($list_a.append("编辑"));
       			$list_a.attr({"data-toggle":"modal","data-target":"#address_list_alert"});
				$list_a.click(function(){
					address_list_temp.saveOrUpdate = "update";
					emptyReceiveModal();
					yiqin_my_address.editAddressByAttr(val.attribute);
       			});
				
				$list_a = $(address_list_temp.list_a);
       			$list_div_set.append($list_a.append("删除"));
				$list_a.click(function(){
					yiqin_my_address.deleteUserAddress(val.attribute);
       			});
       			$list_div_area.append($list_div_set);
       			
       			$my_address_list.append($list_div);
       		});
       	}
	};
	
	var checkReceiveAddress = function(){
		var $modal = $("#address_list_alert"),
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
		var $modal = $("#address_list_alert");
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
	$("#my_set_address").css('color','#fdb45e');
	yiqin_my_address.findUserAddress();
});
</script>
<div class="center-right padding-right">
	<section id="address_list">
		<div style="margin-bottom:10px" id="add_list_btn">
			<a class="btn btn-default check_out" data-toggle="modal" data-target="#address_list_alert" style="vertical-align: middle;">新增收货地址</a>
			<span style="vertical-align: middle;"></span>
		</div>
		<div class="container" id="my_address_list"></div>
	</section>
	
	<!-- alert modal -->
	<div class="modal" id="address_list_alert" tabindex="-1" role="dialog" aria-hidden="true">
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
					<a class="btn btn-default check_out" href="javaScript:yiqin_my_address.saveOrUpdateAddress();">保存</a>
				</div>
			</div>
		</div>
	</div>
</div>