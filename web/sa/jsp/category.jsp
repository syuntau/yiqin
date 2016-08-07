<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">
var pd_category = {
	conf : {
		option : '<option></option>',
		tr : '<tr></tr>',
		td : '<td></td>',
		i_remove : '<i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="<s:text name='msg.category.remove'></s:text>"></i>',
		i_edit : '<i class="fa fa-cog fa-2 cursor-pointer" style="color:#337ab7" title="<s:text name='msg.category.edit'></s:text>"></i>',
		roles : '<s:property value="#session.yiqin_sa_user_roles" />',
	},
	loadCategoryList : function() {
		var $categoryDiv = $('.category-section');
		$.ajax({
            type: "post",
            url: "editCategory_getList",
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		$categoryDiv.find('.category-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
		           	$categoryDiv.find('.category-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.category' /></s:param></s:text></span>");
	           	 } else {
	           		pd_category.editCategoryTbl(data);
	           	 }
            },
            beforeSend: function() {
           		$categoryDiv.find('span').remove();
           		$categoryDiv.find('.category-panel').addClass('display-off');
           		var $tbody = $categoryDiv.find('tbody');
           		$tbody.empty();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$categoryDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$categoryDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	editCategoryTbl : function(data) {
		var $categoryDiv = $('.category-section');
   		var $tbody = $categoryDiv.find('tbody');
		$.each(data, function(i, val) {
			var $tr = $(pd_category.conf.tr).addClass('tr_'+val.id);
			var _id = $(pd_category.conf.td).html(val.id);
			var _name = $(pd_category.conf.td).html(val.name);

			$tr.append(_id).append(_name);

			var roles = pd_category.conf.roles;
			var $iRemove = '';
// 			if (roles.indexOf('13403') > -1) {
// 				$iRemove = $(pd_category.conf.i_remove);
// 				$iRemove.on('click', function() {
// 					pd_category.removeCategory(val.id);
// 				});
// 			}
			var $iEdit = '';
			if (roles.indexOf('13404') > -1) {
				$iEdit = $(pd_category.conf.i_edit);
				$iEdit.on('click', function() {
					pd_category.modifyCategory(val.id);
				});
			}
			if ($iRemove.length > 0 || $iEdit.length > 0) {
				var _setting = $(pd_category.conf.td).append($iRemove).append(" ").append($iEdit);
				$tr.append(_setting);
			}

			$tbody.append($tr);
			if (val.subCategoryList && val.subCategoryList.length > 0) {
				pd_category.editCategoryTbl(val.subCategoryList);
			}
		});
		$categoryDiv.find('.category-panel').removeClass('display-off');
	},
	removeCategory : function(categoryId, attrName) {
		if (categoryId && isNaN(categoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}

		var $categoryDiv = $('.category-section');
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
       		            url: "editCategory_removeCategory",
       		            data: 'categoryId='+categoryId,
       		            dataType: "json",
       		            success: function(data) {
							if (data=='1') {
								alert("<s:text name='msg.err.param'></s:text>");
							} else if (data=='3') {
								alert("<s:text name='msg.err.db'></s:text>");
							} else {
							   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
				           		var $tbody = $categoryDiv.find('tbody');
				           		$tbody.find('.tr_'+categoryId).remove();
				           		var trCnt = $tbody.children().length;
				           		if (trCnt == 0) {
							  		$categoryDiv.find('.category-panel').addClass('display-off');
				           		}
							}
       		            },
       		            beforeSend: function() {
       		            	var $loadingTextIcon = $(com_conf.loading_text_icon);
       		            	$categoryDiv.find('.panel-heading').append($loadingTextIcon);
       		        	},
       		        	error: function() {
       		        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       		        	},
       		        	complete: function() {
        		        	$categoryDiv.find('span').remove();
       		        	}
       				});
       	    	}
       	    }
       	})
	},
	initAddCategory : function() {
		$('.btn-add-category').on('click', function() {
			pd_category.addCategory();
		});
	},
	addCategory : function() {
		bootbox.dialog({
            title: "<s:text name='sa.pd.category.lbl.add' />",
            message: 
	            '<div class="row">' +
	            	'<div class="col-md-12">' +
	            		'<form class="form-horizontal category-form" method="post">' +
	            			'<div class="form-group">' +
		        				'<label class="col-md-3 control-label" for="categoryId">分类 ID</label>' +
		        				'<div class="col-md-7">' +
		        					'<input id="categoryId" name="id" type="text" placeholder="分类 ID" class="form-control input-md">' +
		        				'</div>' +
		        			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="categoryName">分类名称</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="categoryName" name="name" type="text" placeholder="分类名称" class="form-control input-md">' +
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
                        $('.category-form')[0].reset();
                        return false;
                    }
                },
                success: {
                    label: "<s:text name='sa.btn.save' />",
                    className: "btn-success",
                    callback: function () {
                    	var options = {
                		    url : 'editCategory_saveCategory',
                		    dataType : 'json',
                		    beforeSubmit : function() {
              	            	var $loadingTextIcon = $(com_conf.loading_text_icon);
                		    	$('.bootbox.modal .modal-footer').prepend($loadingTextIcon);
                		    },
                		    success : function(data) {
	              				if (data=='1' || data=='4') {
	              					alert("<s:text name='msg.err.wrong.category.info'></s:text>");
	              				} else if (data=='3') {
	              					alert("<s:text name='msg.err.db'></s:text>");
	              				} else {
	              					var $categoryDiv = $('.category-section');
	              			   		var $tbody = $categoryDiv.find('tbody');
	
	              			   		var $tr = $(pd_category.conf.tr).addClass('tr_'+data.id);
	           						var _id = $(pd_category.conf.td).html(data.id);
	           						var _name = $(pd_category.conf.td).html(data.name);
	           						$tr.append(_id).append(_name);

	           						var roles = pd_category.conf.roles;
	           						var $iRemove = '';
// 	           						if (roles.indexOf('13403') > -1) {
// 		           						$iRemove = $(pd_category.conf.i_remove);
// 		           						$iRemove.on('click', function() {
// 		           							pd_category.removeCategory(data.id);
// 		           						});
// 	           						}
	           						var $iEdit = '';
	           						if (roles.indexOf('13404') > -1) {
		           						$iEdit = $(pd_category.conf.i_edit);
		           						$iEdit.on('click', function() {
		           							pd_category.modifyCategory(data.id);
		           						});
	           						}
	           						if ($iRemove.length > 0 || $iEdit.length > 0) {
		           						var _setting = $(pd_category.conf.td).append($iRemove).append(" ").append($iEdit);
		           						$tr.append(_setting);
	           						}
	
	           						$tbody.append($tr);
	           						$categoryDiv.find('.category-panel').removeClass('display-off');
	
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
	              		$('.category-form').ajaxSubmit(options);
	              		return false;
                    }
                }
            }
        });
	},
	modifyCategory : function(id) {
		var $tr = $('.tr_'+id);
		var $tdArr = $tr.children();
		bootbox.dialog({
            title: "<s:text name='sa.pd.category.lbl.edit' />",
            message: 
	            '<div class="row">' +
	            	'<div class="col-md-12">' +
	            		'<form class="form-horizontal category-form" method="post">' +
	            			'<input name="id" type="hidden" value="' +$tdArr.eq(0).html() + '" class="form-control input-md">' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="categoryName">分类名称</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="categoryName" name="name" type="text" value="' + $tdArr.eq(1).html() + '" placeholder="分类名称" class="form-control input-md">' +
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
                        $('.category-form')[0].reset();
                        return false;
                    }
                },
                success: {
                    label: "<s:text name='sa.btn.modify' />",
                    className: "btn-success",
                    callback: function () {
                    	var options = {
                		    url : 'editCategory_updateCategory',
                		    dataType : 'json',
                		    beforeSubmit : function() {
              	            	var $loadingTextIcon = $(com_conf.loading_text_icon);
                		    	$('.bootbox.modal .modal-footer').prepend($loadingTextIcon);
                		    },
                		    success : function(data) {
	              				if (data=='1') {
	              					alert("<s:text name='msg.err.param'></s:text>");
	              				} else if (data=='2') {
	              					alert("<s:text name='msg.err.no.category'></s:text>");
	              				} else if (data=='3') {
	              					alert("<s:text name='msg.err.db'></s:text>");
	              				} else {
	              					var $categoryDiv = $('.category-section');
	              			   		var $tbody = $categoryDiv.find('tbody');
	
	              			   		var $tr = $tbody.find('.tr_'+data.id);
	           						var _id = $(pd_category.conf.td).html(data.id);
	           						var _name = $(pd_category.conf.td).html(data.name);
	           						$tr.empty().append(_id).append(_name);

	           						var roles = pd_category.conf.roles;
	           						var $iRemove = '';
// 	           						if (roles.indexOf('13403') > -1) {
// 		           						$iRemove = $(pd_category.conf.i_remove);
// 		           						$iRemove.on('click', function() {
// 		           							pd_category.removeCategory(data.id);
// 		           						});
// 	           						}
	           						var $iEdit = '';
	           						if (roles.indexOf('13404') > -1) {
		           						$iEdit = $(pd_category.conf.i_edit);
		           						$iEdit.on('click', function() {
		           							pd_category.modifyCategory(data.id);
		           						});
	           						}

	           						if ($iRemove.length > 0 || $iEdit.length > 0) {
		           						var _setting = $(pd_category.conf.td).append($iRemove).append(" ").append($iEdit);
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
	              		$('.category-form').ajaxSubmit(options);
	              		return false;
                    }
                }
            }
        });
	}
}
$(document).ready(function() {
	pd_category.loadCategoryList();
	pd_category.initAddCategory();

});
</script>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><s:text name="sa.pd.category.title" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            
            <div class="row category-section">
                <div class="col-lg-12">
                    <div class="panel panel-default category-panel display-off">
                        <div class="panel-heading">
                            <s:text name="sa.pd.category.list.title" />
                			<s:if test="%{#roles.indexOf('13402')>-1}">
                            <button type="button" class="btn btn-link btn-add-category"><s:text name="sa.pd.category.btn.add" /></button>
                            </s:if>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover category-table">
                                    <thead>
                                        <tr>
                                            <th>id</th>
                                            <th>分类名称</th>
                							<s:if test="%{#roles.indexOf('13404')>-1}">
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
