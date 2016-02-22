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
	loadAttributeList : function() {
		var catetoryId = $('.third-category select').find('option:selected').val();
		if (catetoryId && isNaN(catetoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}
		var $attrHR = $('.attr-hr');
		var $brandDiv = $('.brand-section');
		$.ajax({
            type: "post",
            url: "getAttribute_getList",
            data: 'cId='+catetoryId,
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		$brandDiv.find('.brand-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
					$('.upload-attr').removeClass('display-off');
		           	$brandDiv.find('.brand-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.attribute' /></s:param></s:text></span>");
	           	 } else {
	           		pd_brand.editAttrTbl(data);
	           	 }
            },
            beforeSend: function() {
            	$attrHR.removeClass('display-off');
           		$brandDiv.find('span').remove();
				$('.upload-attr').addClass('display-off');
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
	editAttrTbl : function(data) {
		var $brandDiv = $('.brand-section');
   		var $tbody = $brandDiv.find('tbody');
		$.each(data, function(i, val) {
			var $tr = $(pd_brand.conf.tr).addClass('tr_'+val.id);
			var _id = $(pd_brand.conf.td).html(val.id);
			var _nameId = $(pd_brand.conf.td).html(val.nameId);
			var _name = $(pd_brand.conf.td).html(val.name);
			var _value = $(pd_brand.conf.td).html(val.value);
			var _categoryId = $(pd_brand.conf.td).html(val.categoryId);
			var _filter = $(pd_brand.conf.td).html(val.filter);
			var _filterType = $(pd_brand.conf.td).html(val.filterType);
			var _showValue = $(pd_brand.conf.td).html(val.showValue);
			var _sort = $(pd_brand.conf.td).html(val.sort);

			$tr.append(_id).append(_nameId).append(_name).append(_value)
				.append(_categoryId).append(_filter).append(_filterType)
				.append(_showValue).append(_sort);

			var roles = pd_brand.conf.roles;
			var $iRemove = '';
			if (roles.indexOf('13103') > -1) {
				$iRemove = $(pd_brand.conf.i_remove);
				$iRemove.on('click', function() {
					pd_brand.removeAttr(val.id, val.name);
				});
			}
			var $iEdit = '';
			if (roles.indexOf('13104') > -1) {
				$iEdit = $(pd_brand.conf.i_edit);
				$iEdit.on('click', function() {
					pd_brand.modifyAttr(val.id);
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
	removeAttr : function(attrId, attrName) {
		if (attrId && isNaN(attrId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}

		var $attrHR = $('.attr-hr');
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
       		            url: "editAttribute_removeAttr",
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
					           		$attrHR.addClass('display-off');
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
	initAddAttr : function() {
		$('.btn-add-brand').on('click', function() {
			pd_brand.addAttr();
		});
	},
	addAttr : function() {
		bootbox.dialog({
            title: "<s:text name='sa.pd.brand.lbl.add' />",
            message: 
	            '<div class="row">' +
	            	'<div class="col-md-12">' +
	            		'<form class="form-horizontal attr-form" method="post">' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="attrNameId">name id</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrNameId" name="attr.nameId" type="text" placeholder="name id" class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="attrName">name</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrName" name="attr.name" type="text" placeholder="name..." class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="attrValue">value</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrValue" name="attr.value" type="text" placeholder="value" class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrCategoryId" name="attr.categoryId" type="hidden" value="' + $('.third-category select').find('option:selected').val() + '"class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="filter">filter</label>' +
	            				'<div class="col-md-7">' +
	            					'<div class="radio">' +
	            						'<label class="radio-inline" for="attrFilter0">' +
	            							'<input type="radio" name="attr.filter" id="attrFilter0" value="0" checked="checked"> 非筛选项' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrFilter1">' +
	            							'<input type="radio" name="attr.filter" id="attrFilter1" value="1"> 筛选项' +
	            						'</label>' +
	            					'</div>' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group" style="display:none">' +
	            				'<label class="col-md-3 control-label" for="filter">filter type</label>' +
	            				'<div class="col-md-7">' +
	            					'<div class="radio">' +
	            						'<label class="radio-inline" for="attrFilterType0">' +
	            							'<input type="radio" name="attr.filterType" id="attrFilterType0" value="0" checked="checked"> 无类型' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrFilterType1">' +
	            							'<input type="radio" name="attr.filterType" id="attrFilterType1" value="1"> 组合型' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrFilterType2">' +
	            							'<input type="radio" name="attr.filterType" id="attrFilterType2" value="2"> 价格型' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrFilterType3">' +
	            							'<input type="radio" name="attr.filterType" id="attrFilterType3" value="3"> 连续型' +
	            						'</label>' +
	            					'</div>' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="attrShowValue">show value</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrShowValue" name="attr.showValue" type="text" placeholder="show value" class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="sort">sort</label>' +
	            				'<div class="col-md-7">' +
	            					'<div class="radio">' +
	            						'<label class="radio-inline" for="attrSort0">' +
	            							'<input type="radio" name="attr.sort" id="attrSort0" value="0" checked="checked"> 非排序项' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrSort1">' +
	            							'<input type="radio" name="attr.sort" id="attrSort1" value="1"> 排序项' +
	            						'</label>' +
	            					'</div>' +
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
                        $('.attr-form')[0].reset();
                        return false;
                    }
                },
                success: {
                    label: "<s:text name='sa.btn.save' />",
                    className: "btn-success",
                    callback: function () {
                    	var options = {
                		    url : 'editAttribute_saveAttr',
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
	           						var _nameId = $(pd_brand.conf.td).html(data.nameId);
	           						var _name = $(pd_brand.conf.td).html(data.name);
	           						var _value = $(pd_brand.conf.td).html(data.value);
	           						var _categoryId = $(pd_brand.conf.td).html(data.categoryId);
	           						var _filter = $(pd_brand.conf.td).html(data.filter);
	           						var _filterType = $(pd_brand.conf.td).html(data.filterType);
	           						var _showValue = $(pd_brand.conf.td).html(data.showValue);
	           						var _sort = $(pd_brand.conf.td).html(data.sort);
	           						$tr.append(_id).append(_nameId).append(_name).append(_value)
	           							.append(_categoryId).append(_filter).append(_filterType)
	           							.append(_showValue).append(_sort);

	           						var roles = pd_brand.conf.roles;
	           						var $iRemove = '';
	           						if (roles.indexOf('13103') > -1) {
		           						$iRemove = $(pd_brand.conf.i_remove);
		           						$iRemove.on('click', function() {
		           							pd_brand.removeAttr(data.id, data.name);
		           						});
	           						}
	           						var $iEdit = '';
	           						if (roles.indexOf('13104') > -1) {
		           						$iEdit = $(pd_brand.conf.i_edit);
		           						$iEdit.on('click', function() {
		           							pd_brand.modifyAttr(data.id);
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
	              		$('.attr-form').ajaxSubmit(options);
	              		return false;
                    }
                }
            }
        });
	},
	modifyAttr : function(id) {
		var $tr = $('.tr_'+id);
		var $tdArr = $tr.children();
		bootbox.dialog({
            title: "<s:text name='sa.pd.brand.lbl.edit' />",
            message: 
	            '<div class="row">' +
	            	'<div class="col-md-12">' +
	            		'<form class="form-horizontal attr-form" method="post">' +
	            			'<input name="attr.id" type="hidden" value="' +$tdArr.eq(0).html() + '" class="form-control input-md">' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="attrNameId">name id</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrNameId" name="attr.nameId" type="text" value="' + $tdArr.eq(1).html() + '" placeholder="name id" class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="attrName">name</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrName" name="attr.name" type="text" value="' + $tdArr.eq(2).html() + '" placeholder="name..." class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="attrValue">value</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrValue" name="attr.value" type="text"value="' + $tdArr.eq(3).html() + '" placeholder="value" class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrCategoryId" name="attr.categoryId" type="hidden" value="' + $tdArr.eq(4).html() + '"class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="filter">filter</label>' +
	            				'<div class="col-md-7">' +
	            					'<div class="radio">' +
	            						'<label class="radio-inline" for="attrFilter0">' +
	            							'<input type="radio" name="attr.filter" id="attrFilter0" value="0"> 非筛选项' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrFilter1">' +
	            							'<input type="radio" name="attr.filter" id="attrFilter1" value="1"> 筛选项' +
	            						'</label>' +
	            					'</div>' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="filter">filter type</label>' +
	            				'<div class="col-md-7">' +
	            					'<div class="radio">' +
	            						'<label class="radio-inline" for="attrFilterType0">' +
	            							'<input type="radio" name="attr.filterType" id="attrFilterType0" value="0"> 无类型' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrFilterType1">' +
	            							'<input type="radio" name="attr.filterType" id="attrFilterType1" value="1"> 组合型' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrFilterType2">' +
	            							'<input type="radio" name="attr.filterType" id="attrFilterType2" value="2"> 价格型' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrFilterType3">' +
	            							'<input type="radio" name="attr.filterType" id="attrFilterType3" value="3"> 连续型' +
	            						'</label>' +
	            					'</div>' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="attrShowValue">show value</label>' +
	            				'<div class="col-md-7">' +
	            					'<input id="attrShowValue" name="attr.showValue" value="' + $tdArr.eq(7).html() + '" type="text" placeholder="show value" class="form-control input-md">' +
	            				'</div>' +
	            			'</div>' +
	            			'<div class="form-group">' +
	            				'<label class="col-md-3 control-label" for="sort">sort</label>' +
	            				'<div class="col-md-7">' +
	            					'<div class="radio">' +
	            						'<label class="radio-inline" for="attrSort0">' +
	            							'<input type="radio" name="attr.sort" id="attrSort0" value="0" checked="checked"> 非排序项' +
	            						'</label>' +
	            						'<label class="radio-inline" for="attrSort1">' +
	            							'<input type="radio" name="attr.sort" id="attrSort1" value="1"> 排序项' +
	            						'</label>' +
	            					'</div>' +
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
                        $('.attr-form')[0].reset();
                        return false;
                    }
                },
                success: {
                    label: "<s:text name='sa.btn.modify' />",
                    className: "btn-success",
                    callback: function () {
                    	var options = {
                		    url : 'editAttribute_updateAttr',
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
	           						var _nameId = $(pd_brand.conf.td).html(data.nameId);
	           						var _name = $(pd_brand.conf.td).html(data.name);
	           						var _value = $(pd_brand.conf.td).html(data.value);
	           						var _categoryId = $(pd_brand.conf.td).html(data.categoryId);
	           						var _filter = $(pd_brand.conf.td).html(data.filter);
	           						var _filterType = $(pd_brand.conf.td).html(data.filterType);
	           						var _showValue = $(pd_brand.conf.td).html(data.showValue);
	           						var _sort = $(pd_brand.conf.td).html(data.sort);
	           						$tr.empty().append(_id).append(_nameId).append(_name).append(_value)
	           							.append(_categoryId).append(_filter).append(_filterType)
	           							.append(_showValue).append(_sort);

	           						var roles = pd_brand.conf.roles;
	           						var $iRemove = '';
	           						if (roles.indexOf('13103') > -1) {
		           						$iRemove = $(pd_brand.conf.i_remove);
		           						$iRemove.on('click', function() {
		           							pd_brand.removeAttr(data.id, data.name);
		           						});
	           						}
	           						var $iEdit = '';
	           						if (roles.indexOf('13104') > -1) {
		           						$iEdit = $(pd_brand.conf.i_edit);
		           						$iEdit.on('click', function() {
		           							pd_brand.modifyAttr(data.id);
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
	              		$('.attr-form').ajaxSubmit(options);
	              		return false;
                    }
                }
            }
        }).on('shown.bs.modal', function() {
        	$("#attrFilter" + $tdArr.eq(5).html()).attr("checked",true);
        	$("#attrFilterType" + $tdArr.eq(6).html()).attr("checked",true);
        	$("#attrSort" + $tdArr.eq(8).html()).attr("checked",true);
        });
	}
}
$(document).ready(function() {
	pd_brand.loadAttributeList();
	pd_brand.initAddAttr();

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
