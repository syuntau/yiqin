<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">
var sys_pd_index = {
	conf : {
		option : '<option></option>',
		tr : '<tr></tr>',
		td : '<td></td>',
		no_result_span : '<span class="no-result-span"><s:text name="msg.no.item"><s:param><s:text name="msg.param.product.index.list" /></s:param></s:text> <button type="button" class="btn btn-link btn-add-pd-index" onclick="sys_pd_index.addProductToList()"><s:text name="sa.pd.index.btn.add" /></button></span>',
		i_remove : '<i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="<s:text name='msg.pd.index.list.remove'></s:text>"></i>',
	},
	loadProductList : function() {
		var $pdIndexDiv = $('.pd-index-section');
		$.ajax({
            type: "post",
            url: "editPDIndexList_getList",
            dataType: "json",
            success: function(data) {
	           	 if (data=='2') {
		           	$pdIndexDiv.find('.pd-index-panel').parent().append(sys_pd_index.conf.no_result_span);
	           	 } else {
	           		sys_pd_index.editProductListTbl(data);
	           	 }
            },
            beforeSend: function() {
           		$pdIndexDiv.find('span').remove();
           		$pdIndexDiv.find('.pd-index-panel').addClass('display-off');
           		var $tbody = $pdIndexDiv.find('tbody');
           		$tbody.empty();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$pdIndexDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$pdIndexDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	editProductListTbl : function(data) {
		var $pdIndexDiv = $('.pd-index-section');
   		var $tbody = $pdIndexDiv.find('tbody');
		$.each(data, function(i, val) {
			var $tr = $(sys_pd_index.conf.tr).addClass('tr_'+val.productId);
			var _id = $(sys_pd_index.conf.td).html(val.productId);
			var _name = $(sys_pd_index.conf.td).html(val.productName);

			$tr.append(_id).append(_name);

			var $iRemove = $(sys_pd_index.conf.i_remove);
			$iRemove.on('click', function() {
				sys_pd_index.removeProductFromList(val.productId, val.productName);
			});
			var _setting = $(sys_pd_index.conf.td).append($iRemove);
			$tr.append(_setting);

			$tbody.append($tr);
		});
		$pdIndexDiv.find('.pd-index-panel').removeClass('display-off');
	},
	removeProductFromList : function(itemId, itemName) {
		if (!itemId && isNaN(itemId) && !itemName) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}

		var $pdIndexDiv = $('.pd-index-section');
		var alertMsg = "<s:text name='msg.alert.remove.item'><s:param><s:text name='msg.param.item.' /></s:param></s:text>";
		alertMsg = alertMsg.replace(/itemName/, itemName);

       	bootbox.confirm({
       	    size: 'small',
       	    message: alertMsg, 
       	 	locale: 'zh_CN',
       	    callback: function(result){
       	    	if (result) {
       				$.ajax({
       		            type: "post",
       		            url: "editPDIndexList_removeProduct",
       		            data: 'productId='+itemId,
       		            dataType: "json",
       		            success: function(data) {
							if (data=='1') {
								alert("<s:text name='msg.err.param'></s:text>");
							} else if (data=='2') {
								$pdIndexDiv.find('.pd-index-panel').addClass('display-off');
								$pdIndexDiv.find('.pd-index-panel').parent().append(sys_pd_index.conf.no_result_span);
							} else if (data=='3') {
								alert("<s:text name='msg.err.db'></s:text>");
							} else {
							   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
				           		var $tbody = $pdIndexDiv.find('tbody');
				           		$tbody.find('.tr_'+itemId).remove();
				           		var trCnt = $tbody.children().length;
				           		if (trCnt == 0) {
									$pdIndexDiv.find('.pd-index-panel').addClass('display-off');
									$pdIndexDiv.find('.pd-index-panel').parent().append(sys_pd_index.conf.no_result_span);
				           		}
							}
       		            },
       		            beforeSend: function() {
       		            	var $loadingTextIcon = $(com_conf.loading_text_icon);
       		            	$pdIndexDiv.find('.panel-heading').append($loadingTextIcon);
       		        	},
       		        	error: function() {
       		        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       		        	},
       		        	complete: function() {
        		        	$pdIndexDiv.find('.panel-heading span').remove();
       		        	}
       				});
       	    	}
       	    }
       	})
	},
	initAddProduct : function() {
		$('.btn-add-pd-index').on('click', function() {
			sys_pd_index.addProductToList();
		});
	},
	addProductToList : function() {
		bootbox.dialog({
            title: "<s:text name='sa.pd.index.btn.add' />",
            message: 
	            '<div class="row">' +
	            	'<div class="col-md-12">' +
	            		'<form class="form-horizontal pd-index-form" method="post">' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="pdIndexId">商品 ID</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="pdIndexId" name="productId" type="text" placeholder="请输入商品ID" class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            		'</form>' +
	            	'</div>' +
	            '</div>',
            buttons: {
                reset: {
                    label: "<s:text name='sa.btn.reset' />",
                    className: "btn-default",
                    callback: function () {
                        $('.pd-index-form')[0].reset();
                        return false;
                    }
                },
                success: {
                    label: "<s:text name='sa.btn.save' />",
                    className: "btn-success",
                    callback: function () {
                    	var options = {
                		    url : 'editPDIndexList_saveProduct',
                		    dataType : 'json',
                		    beforeSubmit : function() {
              	            	var $loadingTextIcon = $(com_conf.loading_text_icon);
                		    	$('.bootbox.modal .modal-footer').prepend($loadingTextIcon);
                		    },
                		    success : function(data) {
	              				if (data=='1') {
	              					alert("<s:text name='msg.err.param'></s:text>");
	              				} else if (data=='2') {
	              					alert("<s:text name='msg.no.item'><s:param><s:text name='msg.param.item' /></s:param></s:text>");
	              				} else if (data=='3') {
	              					alert("<s:text name='msg.err.db'></s:text>");
	              				} else {
	              					var $pdIndexDiv = $('.pd-index-section');
	              			   		var $tbody = $pdIndexDiv.find('tbody');
	
	              			   		var $tr = $(sys_pd_index.conf.tr).addClass('tr_'+data.productId);
	           						var _id = $(sys_pd_index.conf.td).html(data.productId);
	           						var _name = $(sys_pd_index.conf.td).html(data.productName);
	           						$tr.append(_id).append(_name);

	           						var $iRemove = $(sys_pd_index.conf.i_remove);
	           						$iRemove.on('click', function() {
	           							sys_pd_index.removeProductFromList(data.productId, data.productName);
	           						});
	           						var _setting = $(sys_pd_index.conf.td).append($iRemove);
	           						$tr.append(_setting);
	
	           						$tbody.append($tr);
	           						$('.no-result-span').remove();
	           						$pdIndexDiv.find('.pd-index-panel').removeClass('display-off');
	
	              				   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.save' /></s:param></s:text>");
	              				}
              	        		$('.bootbox.modal .modal-footer').find('span').remove();
              	        		$('.bootbox.modal').modal('hide');
                		    },
              	        	error: function() {
              	        		$('.bootbox.modal .modal-footer').find('span').remove();
              	        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.save' /></s:param></s:text>");
              	        	}
                		};
	              		$('.pd-index-form').ajaxSubmit(options);
	              		return false;
                    }
                }
            }
        });
	},
}
$(document).ready(function() {
	sys_pd_index.loadProductList();
	sys_pd_index.initAddProduct();

});
</script>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><s:text name="sa.sys.product.index.list" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <div class="row pd-index-section">
                <div class="col-lg-12">
                    <div class="panel panel-default pd-index-panel display-off">
                        <div class="panel-heading">
                            <s:text name="sa.pd.index.list.title" />
                            <button type="button" class="btn btn-link btn-add-pd-index"><s:text name="sa.pd.index.btn.add" /></button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover pd-index-table">
                                    <thead>
                                        <tr>
                                            <th>商品 Id</th>
                                            <th>商品名称</th>
                                            <th>设置</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->
