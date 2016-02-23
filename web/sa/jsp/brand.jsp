<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">
var pd_brand = {
	conf : {
		option : '<option></option>',
		tr : '<tr></tr>',
		td : '<td></td>',
		i_remove : '<i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="<s:text name='msg.brand.remove'></s:text>"></i>',
		i_edit : '<i class="fa fa-cog fa-2 cursor-pointer" style="color:#337ab7" title="<s:text name='msg.brand.edit'></s:text>"></i>',
		roles : '<s:property value="#session.yiqin_sa_user_roles" />',
	},
	loadBrandList : function() {
		var $brandDiv = $('.brand-section');
		$.ajax({
            type: "post",
            url: "editBrand_getList",
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		$brandDiv.find('.brand-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
		           	$brandDiv.find('.brand-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.brand' /></s:param></s:text></span>");
	           	 } else {
	           		pd_brand.editBrandTbl(data);
	           	 }
            },
            beforeSend: function() {
           		$brandDiv.find('span').remove();
           		$brandDiv.find('.brand-panel').addClass('display-off');
           		var $tbody = $brandDiv.find('tbody');
           		$tbody.empty();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$brandDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$brandDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	editBrandTbl : function(data) {
		var $brandDiv = $('.brand-section');
   		var $tbody = $brandDiv.find('tbody');
		$.each(data, function(i, val) {
			var $tr = $(pd_brand.conf.tr).addClass('tr_'+val.id);
			var _id = $(pd_brand.conf.td).html(val.id);
			var _nameEn = $(pd_brand.conf.td).html(val.nameEn);
			var _nameCn = $(pd_brand.conf.td).html(val.nameCn);

			$tr.append(_id).append(_nameEn).append(_nameCn);

			var roles = pd_brand.conf.roles;
			var $iRemove = '';
// 			if (roles.indexOf('13103') > -1) {
// 				$iRemove = $(pd_brand.conf.i_remove);
// 				$iRemove.on('click', function() {
// 					pd_brand.removeBrand(val.id);
// 				});
// 			}
			var $iEdit = '';
			if (roles.indexOf('13104') > -1) {
				$iEdit = $(pd_brand.conf.i_edit);
				$iEdit.on('click', function() {
					pd_brand.modifyBrand(val.id);
				});
			}
			if ($iRemove.length > 0 || $iEdit.length > 0) {
				var _setting = $(pd_brand.conf.td).append($iRemove).append(" ").append($iEdit);
				$tr.append(_setting);
			}

			$tbody.append($tr);
		});
		$brandDiv.find('.brand-panel').removeClass('display-off');
	},
	removeBrand : function(attrId, attrName) {
		if (attrId && isNaN(attrId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}

		var $brandDiv = $('.brand-section');
		var alertMsg = "<s:text name='msg.alert.remove.item'><s:param><s:text name='msg.param.attr.' /></s:param></s:text>";
		alertMsg = alertMsg.replace(/attrName/, attrName);

       	bootbox.confirm({
       	    size: 'small',
       	    message: alertMsg, 
       	 	locale: 'zh_CN',
       	    callback: function(result){
       	    	if (result) {
       				$.ajax({
       		            type: "post",
       		            url: "editBrand_removeBrand",
       		            data: 'attrId='+attrId,
       		            dataType: "json",
       		            success: function(data) {
							if (data=='1') {
								alert("<s:text name='msg.err.param'></s:text>");
							} else if (data=='3') {
								alert("<s:text name='msg.err.db'></s:text>");
							} else {
							   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
				           		var $tbody = $brandDiv.find('tbody');
				           		$tbody.find('.tr_'+attrId).remove();
				           		var trCnt = $tbody.children().length;
				           		if (trCnt == 0) {
							  		$brandDiv.find('.brand-panel').addClass('display-off');
				           		}
							}
       		            },
       		            beforeSend: function() {
       		            	var $loadingTextIcon = $(com_conf.loading_text_icon);
       		            	$brandDiv.find('.panel-heading').append($loadingTextIcon);
       		        	},
       		        	error: function() {
       		        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       		        	},
       		        	complete: function() {
        		        	$brandDiv.find('span').remove();
       		        	}
       				});
       	    	}
       	    }
       	})
	},
	initAddBrand : function() {
		$('.btn-add-brand').on('click', function() {
			pd_brand.addBrand();
		});
	},
	addBrand : function() {
		bootbox.dialog({
            title: "<s:text name='sa.pd.brand.lbl.add' />",
            message: 
	            '<div class="row">' +
	            	'<div class="col-md-12">' +
	            		'<form class="form-horizontal brand-form" method="post">' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="brandNameEn">英文名称</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="brandNameEn" name="brand.nameEn" type="text" placeholder="英文名称" class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="brandNameCn">中文名称</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="brandNameCn" name="brand.nameCn" type="text" placeholder="中文名称" class="form-control input-md">' +
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
                        $('.brand-form')[0].reset();
                        return false;
                    }
                },
                success: {
                    label: "<s:text name='sa.btn.save' />",
                    className: "btn-success",
                    callback: function () {
                    	var options = {
                		    url : 'editBrand_saveBrand',
                		    dataType : 'json',
                		    beforeSubmit : function() {
              	            	var $loadingTextIcon = $(com_conf.loading_text_icon);
                		    	$('.bootbox.modal .modal-footer').prepend($loadingTextIcon);
                		    },
                		    success : function(data) {
	              				if (data=='1') {
	              					alert("<s:text name='msg.err.param'></s:text>");
	              				} else if (data=='3') {
	              					alert("<s:text name='msg.err.db'></s:text>");
	              				} else {
	              					var $brandDiv = $('.brand-section');
	              			   		var $tbody = $brandDiv.find('tbody');
	
	              			   		var $tr = $(pd_brand.conf.tr).addClass('tr_'+data.id);
	           						var _id = $(pd_brand.conf.td).html(data.id);
	           						var _nameEn = $(pd_brand.conf.td).html(data.nameEn);
	           						var _nameCn = $(pd_brand.conf.td).html(data.nameCn);
	           						$tr.append(_id).append(_nameEn).append(_nameCn);

	           						var roles = pd_brand.conf.roles;
	           						var $iRemove = '';
	           						if (roles.indexOf('13103') > -1) {
		           						$iRemove = $(pd_brand.conf.i_remove);
		           						$iRemove.on('click', function() {
		           							pd_brand.removeBrand(data.id);
		           						});
	           						}
	           						var $iEdit = '';
	           						if (roles.indexOf('13104') > -1) {
		           						$iEdit = $(pd_brand.conf.i_edit);
		           						$iEdit.on('click', function() {
		           							pd_brand.modifyBrand(data.id);
		           						});
	           						}
	           						if ($iRemove.length > 0 || $iEdit.length > 0) {
		           						var _setting = $(pd_brand.conf.td).append($iRemove).append(" ").append($iEdit);
		           						$tr.append(_setting);
	           						}
	
	           						$tbody.append($tr);
	
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
	              		$('.brand-form').ajaxSubmit(options);
	              		return false;
                    }
                }
            }
        });
	},
	modifyBrand : function(id) {
		var $tr = $('.tr_'+id);
		var $tdArr = $tr.children();
		bootbox.dialog({
            title: "<s:text name='sa.pd.brand.lbl.edit' />",
            message: 
	            '<div class="row">' +
	            	'<div class="col-md-12">' +
	            		'<form class="form-horizontal brand-form" method="post">' +
	            			'<input name="brand.id" type="hidden" value="' +$tdArr.eq(0).html() + '" class="form-control input-md">' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="brandNameEn">英文名称</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="brandNameEn" name="brand.nameEn" type="text" value="' + $tdArr.eq(1).html() + '" placeholder="英文名称" class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="brandNameCn">中文名称</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="brandNameCn" name="brand.nameCn" type="text" value="' + $tdArr.eq(2).html() + '" placeholder="中文名称" class="form-control input-md">' +
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
                        $('.brand-form')[0].reset();
                        return false;
                    }
                },
                success: {
                    label: "<s:text name='sa.btn.modify' />",
                    className: "btn-success",
                    callback: function () {
                    	var options = {
                		    url : 'editBrand_updateBrand',
                		    dataType : 'json',
                		    beforeSubmit : function() {
              	            	var $loadingTextIcon = $(com_conf.loading_text_icon);
                		    	$('.bootbox.modal .modal-footer').prepend($loadingTextIcon);
                		    },
                		    success : function(data) {
	              				if (data=='1') {
	              					alert("<s:text name='msg.err.param'></s:text>");
	              				} else if (data=='3') {
	              					alert("<s:text name='msg.err.db'></s:text>");
	              				} else {
	              					var $brandDiv = $('.brand-section');
	              			   		var $tbody = $brandDiv.find('tbody');
	
	              			   		var $tr = $tbody.find('.tr_'+data.id);
	           						var _id = $(pd_brand.conf.td).html(data.id);
	           						var _nameEn = $(pd_brand.conf.td).html(data.nameEn);
	           						var _nameCn = $(pd_brand.conf.td).html(data.nameCn);
	           						$tr.empty().append(_id).append(_nameEn).append(_nameCn);

	           						var roles = pd_brand.conf.roles;
	           						var $iRemove = '';
	           						if (roles.indexOf('13103') > -1) {
		           						$iRemove = $(pd_brand.conf.i_remove);
		           						$iRemove.on('click', function() {
		           							pd_brand.removeBrand(data.id);
		           						});
	           						}
	           						var $iEdit = '';
	           						if (roles.indexOf('13104') > -1) {
		           						$iEdit = $(pd_brand.conf.i_edit);
		           						$iEdit.on('click', function() {
		           							pd_brand.modifyBrand(data.id);
		           						});
	           						}

	           						if ($iRemove.length > 0 || $iEdit.length > 0) {
		           						var _setting = $(pd_brand.conf.td).append($iRemove).append(" ").append($iEdit);
		           						$tr.append(_setting);
	           						}
	
	              				   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.modify' /></s:param></s:text>");
	              				}
              	        		$('.bootbox.modal .modal-footer').find('span').remove();
              	        		$('.bootbox.modal').modal('hide');
                		    },
              	        	error: function() {
              	        		$('.bootbox.modal .modal-footer').find('span').remove();
              	        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.modify' /></s:param></s:text>");
              	        	}
                		};
	              		$('.brand-form').ajaxSubmit(options);
	              		return false;
                    }
                }
            }
        });
	}
}
$(document).ready(function() {
	pd_brand.loadBrandList();
	pd_brand.initAddBrand();

});
</script>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><s:text name="sa.pd.brand.title" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <div class="row brand-section">
                <div class="col-lg-12">
                    <div class="panel panel-default brand-panel display-off">
                        <div class="panel-heading">
                            <s:text name="sa.pd.brand.list.title" />
                			<s:if test="%{#roles.indexOf('13302')>-1}">
                            <button type="button" class="btn btn-link btn-add-brand"><s:text name="sa.pd.brand.btn.add" /></button>
                            </s:if>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover brand-table">
                                    <thead>
                                        <tr>
                                            <th>id</th>
                                            <th>英文名称</th>
                                            <th>中文名称</th>
                							<s:if test="%{#roles.indexOf('13103')>-1 || #roles.indexOf('13104')>-1}">
                                            <th>设置</th>
                                            </s:if>
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
