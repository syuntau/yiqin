<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var customer_manage = {
	conf : {
		option : '<option></option>',
	},
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
						var $op = $(customer_manage.conf.option);
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
            }
		});
	},
	addZheKou : function() {
		var userId = $('.user-list select').find('option:selected').val();
		var zheKou = $('.customer-zhekou').val();
		$.ajax({
        	type: "post",
			url: "editCustomer_addZheKou",
			data: 'zheKou='+zheKou+ '&userId=' + userId,
			dataType: "json",
			success: function(data) {
				if (data=='1') {
					alert("<s:text name='msg.err.param'></s:text>");
				} else if (data=='3') {
					alert("<s:text name='msg.err.db'></s:text>");
				} else {
					alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.save' /></s:param></s:text>");
				}
            },
            beforeSend: function() {
	            var $loadingTextIcon = $(com_conf.loading_text_icon);
	            $('.zhekou-form').append($loadingTextIcon);
        	},
            complete: function() {
            	$('.zhekou-form').find('span').remove();
            }
		});
	},
	loadZheKou : function() {
		var userId = $('.user-list select').find('option:selected').val();
		$.ajax({
        	type: "post",
			url: "editCustomer_getZK",
			data: 'userId=' + userId,
			dataType: "json",
			success: function(data) {
				if (data=='1') {
					alert("<s:text name='msg.err.param'></s:text>");
				} else if (data=='2') {
					$('.customer-zhekou').val(1);
				} else {
					$('.customer-zhekou').val(data.value);
				}
            },
            beforeSend: function() {
	            var $loadingTextIcon = $(com_conf.loading_text_icon);
		        $('.zhekou-form').append($loadingTextIcon);
        	},
            complete: function() {
            	$('.zhekou-form').find('span').remove();
            }
		});
	},
	addYouHui : function() {
		var userId = $('.user-list select').find('option:selected').val();
		var youHui = $('.customer-youhui').val();
		if (youHui == "") {
			youHui = 'empty';
		}
		$.ajax({
        	type: "post",
			url: "editCustomer_addYouHui",
			data: 'youHuiZhengCe='+youHui+ '&userId=' + userId,
			dataType: "json",
			success: function(data) {
				if (data=='1') {
					alert("<s:text name='msg.err.param'></s:text>");
				} else if (data=='3') {
					alert("<s:text name='msg.err.db'></s:text>");
				} else {
					alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.save' /></s:param></s:text>");
				}
            },
            beforeSend: function() {
	            var $loadingTextIcon = $(com_conf.loading_text_icon);
	            $('.youhui-form').append($loadingTextIcon);
        	},
            complete: function() {
            	$('.youhui-form').find('span').remove();
            }
		});
	},
	loadYouHui : function() {
		var userId = $('.user-list select').find('option:selected').val();
		$.ajax({
        	type: "post",
			url: "editCustomer_getYouHui",
			data: 'userId=' + userId,
			dataType: "json",
			success: function(data) {
				if (data=='1') {
					alert("<s:text name='msg.err.param'></s:text>");
				} else if (data=='2') {
					$('.customer-youhui').val('');
				} else {
					var value = data.value;
					value = value.replace(/<br>/g, '\n');
					$('.customer-youhui').val(value);
				}
            },
            beforeSend: function() {
	            var $loadingTextIcon = $(com_conf.loading_text_icon);
		        $('.youhui-form').append($loadingTextIcon);
        	},
            complete: function() {
            	$('.youhui-form').find('span').remove();
            }
		});
	},
	check_customer : function() {
		$('.btn-user-submit').on('click', function() {
			$('.customer-hr').removeClass('display-off');
			$('.customer-section').removeClass('display-off');
			customer_manage.loadZheKou();
			customer_manage.loadYouHui();
			customer_manage.loadPwd();
			getStatStatus();
		});
	},
	loadPwd : function() {
		var $pwdDiv = $('.customer-pwd-div');
		if ($pwdDiv.length == 0) {
			return ;
		}
		var userId = $('.user-list select').find('option:selected').val();
		$.ajax({
        	type: "post",
			url: "editCustomer_getPwd",
			data: 'userId=' + userId,
			dataType: "json",
			success: function(data) {
				if (data=='1') {
					alert("<s:text name='msg.err.param'></s:text>");
				} else if (data=='2') {
					$('.customer-pwd-div').hide();
				} else {
					$('.customer-pwd-div').show();
					$('.customer-pwd-div span').html(data.pwd);
				}
            },
            beforeSend: function() { },
            complete: function() { }
		});
	},
	removeUser : function() {
		if ($('.btn-clear-user').length == 0) {
			return ;
		}
		$('.btn-clear-user').on('click', function() {
			var $userList = $('.user-list');
			var userId = $('.user-list select').find('option:selected').val();
			var alertMsg = "<s:text name='msg.alert.remove.user' />";
			alertMsg = alertMsg.replace(/UserId/, userId);
			
			bootbox.confirm({
	       	    size: 'small',
	       	    message: alertMsg, 
	       	 	locale: 'zh_CN',
	       	    callback: function(result){
	       	    	if (result) {
	       				$.ajax({
	       		            type: "post",
	       		            url: "editCustomer_removeUser",
	       		            data: 'userId='+userId,
	       		            dataType: "json",
	       		            success: function(data) {
								if (data=='1') {
									alert("<s:text name='msg.err.param'></s:text>");
								} else if (data=='4') {
									alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
								} else {
								   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
								   	window.location.href = "sa/dispatcher?para_nav=customer-manage";
								}
	       		            },
	       		            beforeSend: function() {
	       		            	$userList.prepend($(com_conf.loading_icon));
	       		        	},
	       		        	error: function() {
	       		        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
	       		        	},
	       		        	complete: function() {
	       		        		$userList.find('span').remove();
	       		        	}
	       				});
	       	    	}
	       	    }
	       	})
		});
	}
};

getStatStatus = function(){
	
	var userId = $('.user-list select').find('option:selected').val();
	$.ajax({
    	type: "get",
		url: "sa/getUserChartStatus?userId="+userId,
		dataType: "json",
		success: function(data) {
			if(data == "true" || data == true){
				/* $('.user-stat-status-select').find('option').removeAttr('selected'); */
				$('.user-stat-status-select option:nth-child(1)').attr("selected",true);
			}else{
				/* $('.user-stat-status-select').find('option').removeAttr('selected'); */
				$('.user-stat-status-select option:nth-child(2)').attr("selected",true);
			}
        },
        beforeSend: function() {
            var $loadingTextIcon = $(com_conf.loading_text_icon);
            $('.stat-form').append($loadingTextIcon);
    	},
        complete: function() {
        	$('.stat-form').find('span').remove();
        }
	});
	
	
	
}

saveStatStatus = function(){
	
	var userId = $('.user-list select').find('option:selected').val();
	var status = $('.user-stat-status-select').find('option:selected').val();
	$.ajax({
    	type: "get",
		url: "sa/saveUserChartStatus?userId="+userId+"&status="+status,
		dataType: "json",
		success: function(data) {
			if(data=='true'|| data==true){
				alert('保存成功！');
			}else if(data == 701 || data == "701" ){
				
				$('.user-stat-status-select').find('option').removeAttr('selected');
				
				$('.user-stat-status-select > option:nth-child(2)').attr("selected",true);
				
				alert('用户暂无统计数据，无法保存！');
			}else{
				alert('保存失败！');
			}
        },
        beforeSend: function() {
            var $loadingTextIcon = $(com_conf.loading_text_icon);
            $('.stat-form').append($loadingTextIcon);
    	},
        complete: function() {
        	$('.stat-form').find('span').remove();
        }
	});
}

statStatData = function(){
	var userId = $('.user-list select').find('option:selected').val();
	$.ajax({
    	type: "get",
		url: "sa/startStat?userId="+userId,
		dataType: "json",
		success: function(data) {
			if (data=='true' || data == true) {
				alert("统计完成！");
			} else if (data=='false' || data == false) {
				alert("统计发生错误！");
			} else {
				alert("<s:text name='msg.err.param'></s:text>");
			}
        },
        beforeSend: function() {
            var $loadingTextIcon = $(com_conf.loading_text_icon);
            $('.stat-form').append($loadingTextIcon);
    	},
        complete: function() {
        	$('.stat-form').find('span').remove();
        }
	});
	
	
	
	
}


$(document).ready(function() {
	customer_manage.load_user();
	customer_manage.removeUser();
	customer_manage.check_customer();
});
</script>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><s:text name="sa.header.customer.manage" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12" >
                    <form role="form" class="form-inline user-form" >
                        <div class="form-group user-list">
                            <label class="user-select display-off"><s:text name="sa.pd.lbl.select.user" /></label>
                            <select class="form-control user-select display-off"></select>
                        </div>
                        
                        <button type="button" class="btn btn-info btn-user-submit user-select display-off"><s:text name="sa.btn.query" /></button>
                        <s:if test="%{#roles.indexOf('12305')>-1}">
                        <button type="button" class="btn btn-danger btn-clear-user user-select display-off"><s:text name="sa.btn.clear" /></button>
                        </s:if>
                    </form>
		            <hr class="customer-hr display-off">
		            <div class="row customer-section display-off">
		                <div class="col-lg-12" style="margin: 10px 0px;">
		                    <form role="form" class="form-inline zhekou-form" >
		                        <div class="form-group">
	                                <label><s:text name="sa.customer.zhekou" /></label>
	                                <input type="text" class="form-control customer-zhekou" maxlength="5" width="100px" name="zhekou">
		                        </div>
		                        
                				<s:if test="%{#roles.indexOf('12301')>-1}">
		                        <button type="button" class="btn btn-info btn-customer-zhekou" onclick="customer_manage.addZheKou();"><s:text name="sa.btn.save" /></button>
		                        </s:if>
		                    </form>
		                </div>
		                <div class="col-lg-12" style="margin: 10px 0px;">
		                    <form role="form" class="form-inline youhui-form" >
		                        <div class="form-group">
	                                <label><s:text name="sa.customer.youhui" /></label>
	                                <textarea class="form-control customer-youhui" rows="6" cols="60" name="youHuiZhengCe"></textarea>
		                        </div>
		                        
                				<s:if test="%{#roles.indexOf('12302')>-1}">
		                        <button type="button" class="btn btn-info btn-customer-youhui" onclick="customer_manage.addYouHui();"><s:text name="sa.btn.save" /></button>
		                        </s:if>
		                    </form>
		                </div>
		                <s:if test="%{#roles.indexOf('12303')>-1}">
		                <div class="col-lg-12" style="margin: 10px 0px;">
	                        <div class="form-group customer-pwd-div">
                                <label><s:text name="sa.customer.pwd" /></label> <span style="color:#A94442;"></span>
	                        </div>
		                </div>
		                </s:if>
		                <s:if test="%{#roles.indexOf('12304')>-1}">
		                <div class="col-lg-12" style="margin: 10px 0px;">
		                    <form role="form" class="form-inline stat-form" >
		                        <div class="form-group">
	                                <label>用户统计</label>
	                                <!-- <input type="text" class="form-control customer-zhekou" maxlength="5" width="100px" name="zhekou"> -->
			                        <button type="button" class="btn btn-info btn-customer-zhekou" onclick="statStatData();">生成统计数据</button>
			                        &nbsp;&nbsp;&nbsp;&nbsp;统计数据前台显示：
			                        <select class="form-control user-stat-status-select">
			                        	<option value="true" >开启</option>
			                        	<option value="false" selected="selected">关闭</option>
			                        </select>
			                        <button type="button" class="btn btn-info btn-save-stat-status" onclick="saveStatStatus();">保存</button>
		                        </div>
		                    </form>
		                </div>
		                </s:if>
		                <!-- /.col-lg-12 -->
		            </div>
		            <!-- /.row -->
                </div>
            </div>
            <!-- /.row -->
            
        </div>
        <!-- /#page-wrapper -->
