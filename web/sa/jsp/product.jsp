<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var pro_att = {
	conf : {
		option : '<option></option>',
		tr : '<tr></tr>',
		td : '<td></td>',
		i_remove : '<i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="<s:text name='msg.pro.remove'></s:text>"></i>',
		i_edit : '<i class="fa fa-cog fa-2 cursor-pointer" style="color:#337ab7" title="<s:text name='msg.pro.edit'></s:text>"></i>',
	},
	initCategory : function() {
		pro_att.loadFirstCategory();
		pro_att.initSubmitCategory();
		pro_att.initUploadSubmit();
		pro_att.initRemoveAllAttr();
		pro_att.initAddAttr();
	},
	loadFirstCategory : function() {
		var $firstCategory = $('.first-category');
		$.ajax({
            type: "post",
            url: "getCategoryList_getFirst",
            dataType: "json",
            success: function(data) {
	           	 if (data=='2') {
	           		$firstCategory.html("<s:text name='msg.no.item'><s:param><s:text name='msg.param.category' /></s:param></s:text>");
	           	 } else {
	           		var $firstCategorySelect = $firstCategory.find('select');
					$.each(data, function(i, val) {
						var $op = $(pro_att.conf.option);
						$op.val(val.id).html(val.name);
						$firstCategorySelect.append($op);
					});
					$firstCategory.find('option:first').attr('selected', true);
					pro_att.loadCategory('second-category', $firstCategory.find('option:selected').val());
					$('.category-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	pro_att.hideAttr();
            	$firstCategory.prepend($(com_conf.loading_icon));
        	},
            complete: function() {
            	$firstCategory.find('span').remove();
            }
		});
	},
	loadCategory : function(type, catetoryId) {
		var $categoryDiv = $('.' + type);
		$.ajax({
            type: "post",
            url: "getCategoryList_getList",
            data: 'cId='+catetoryId,
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		pro_att.hideCategory(type);
	           		$categoryDiv.append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
		           	pro_att.hideCategory(type);
		           	$categoryDiv.append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.category' /></s:param></s:text></span>");
	           	 } else {
	           		$categoryDiv.find('span').remove();
	           		var $categoryDivSelect = $categoryDiv.find('select');
					$.each(data, function(i, val) {
						var $op = $(pro_att.conf.option);
						$op.val(val.id).html(val.name);
						$categoryDivSelect.append($op);
					});
					$categoryDivSelect.find('option:first').attr('selected', true);
					if (type == 'second-category') {
						pro_att.loadCategory('third-category', $('.second-category').find('option:selected').val());
					}
					$('.category-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	pro_att.hideAttr();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$categoryDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$categoryDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	hideCategory : function(type) {
		if (type == 'second-category') {
       		$('.second-category select, .third-category select').addClass('display-off');
       		$('.btn-category-submit').addClass('display-off');
		} else if (type == 'third-category') {
       		$('.third-category select').addClass('display-off');
       		$('.btn-category-submit').addClass('display-off');
		}
	},
	hideAttr : function() {
		$('.item-hr').addClass('display-off');
		$('.upload-item').addClass('display-off');
		$('.item-section .item-panel').addClass('display-off');
		$('.item-section .item-panel').parent().find('span').remove();
	},
	changeCategory : function() {
		$('.first-category').on('change', function() {
			var selectCategoryId = $(this).find('option:selected').val();
			$('.second-category select, .third-category select').empty();
			pro_att.loadCategory('second-category', selectCategoryId);
		});
		$('.second-category').on('change', function() {
			var selectCategoryId = $(this).find('option:selected').val();
			$('.third-category select').empty();
			pro_att.loadCategory('third-category', selectCategoryId);
		});
	},
	initSubmitCategory : function() {
		$('.btn-category-submit').on('click', function() {
			pro_att.loadProductList();
		})
	},
	loadProductList : function() {
		var catetoryId = $('.third-category select').find('option:selected').val();
		if (catetoryId && isNaN(catetoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}
		var $itemHR = $('.item-hr');
		var $itemDiv = $('.item-section');
		$.ajax({
        	type: "post",
			url: "editProduct_getItems",
			data: 'cId='+catetoryId,
			dataType: "json",
			success: function(data) {
				if (data=='1') {
					$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
				} else if (data=='2') {
					$('.upload-item').removeClass('display-off');
					$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.item' /></s:param></s:text></span>");
				} else if (data=='4') {
					$('.upload-item').removeClass('display-off');
					$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.fail.do'><s:param><s:text name='msg.param.query' /></s:param></s:text></span>");
				} else {
					pro_att.editItemsTbl(data);
				}
            },
            beforeSend: function() {
            	$itemHR.removeClass('display-off');
           		$itemDiv.find('span').remove();
				$('.upload-item').addClass('display-off');
           		$itemDiv.find('.item-panel').addClass('display-off');
           		var $tbody = $itemDiv.find('tbody');
           		$tbody.empty();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$itemDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$itemDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	initUploadSubmit : function() {
		$('.btn-upload').on('click', function() {
			pro_att.uploadProduct();
		});
	},
	uploadProduct : function() {
		var catetoryId = $('.third-category select').find('option:selected').val();
		if (catetoryId && isNaN(catetoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}
		
		var $itemHR = $('.item-hr');
		var $itemDiv = $('.item-section');

    	$itemHR.removeClass('display-off');
   		$itemDiv.find('span').remove();
		$('.upload-item').addClass('display-off');
   		$itemDiv.find('.item-panel').addClass('display-off');
   		var $tbody = $itemDiv.find('tbody');
   		$tbody.empty();
    	var $loadingIcon = $(com_conf.loading_icon);
    	$itemDiv.prepend($loadingIcon);
    	
		$.ajaxFileUpload({
            url: "uploadAttribute",
            fileElementId: 'attributeFile',
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
					$('.upload-item').removeClass('display-off');
		           	$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.item' /></s:param></s:text></span>");
	           	 } else {
	           		pro_att.editItemsTbl(data);
	           	}
	           	$itemDiv.find('.fa-refresh').parent().remove();
            },
            error : function(data,status,e) {
            	alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.upload' /></s:param></s:text>");
	           	$itemDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	editItemsTbl : function(data) {
		var $itemDiv = $('.item-section');
   		var $tbody = $itemDiv.find('tbody');
		$.each(data, function(i, val) {
			var $tr = $(pro_att.conf.tr).addClass('tr_'+val.productId);
			var _id = $(pro_att.conf.td).html(val.productId);
			var _name = $(pro_att.conf.td).html(val.productName);
			var $iRemove = $(pro_att.conf.i_remove);
			$iRemove.on('click', function() {
				pro_att.removeAttr(val.productId, val.name);
			});
			var $iEdit = $(pro_att.conf.i_edit);
			$iEdit.on('click', function() {
				pro_att.modifyAttr(val.productId);
			});
			var _setting = $(pro_att.conf.td).append($iRemove).append(" ").append($iEdit);

			$tr.append(_id).append(_name).append(_setting);

			$tbody.append($tr);
		});
		$itemDiv.find('.item-panel').removeClass('display-off');
	},
	initRemoveAllAttr : function() {
		$('.btn-remove-all-attr').on('click', function() {
			pro_att.removeAllAttr();
		});
	},
	removeAllAttr : function() {
		var catetoryId = $('.third-category select').find('option:selected').val();
		if (catetoryId && isNaN(catetoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}

		var $itemHR = $('.item-hr');
		var $itemDiv = $('.item-section');

       	bootbox.confirm({
       	    size: 'small',
       	    message: "<s:text name='msg.alert.remove.item'><s:param><s:text name='msg.param.all.item' /></s:param></s:text>",
       	 	locale: 'zh_CN',
       	    callback: function(result){
       	    	if (result) {
       				$.ajax({
       		            type: "post",
       		            url: "editProduct_removeAll",
       		            data: 'categoryId='+catetoryId,
       		            dataType: "json",
       		            success: function(data) {
       			           	if (data=='1') {
       			           		alert("<s:text name='msg.err.param'></s:text>");
       			           	} else if (data=='3') {
       				           	alert("<s:text name='msg.err.db'></s:text>");
       			           	} else {
            		            alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       			           		$itemHR.addClass('display-off');
            		           	$itemDiv.find('.item-panel').addClass('display-off');
            		           	var $tbody = $itemDiv.find('tbody');
            		           	$tbody.empty();
       			           	}
       		            },
       		            beforeSend: function() {
       		            	var $loadingTextIcon = $(com_conf.loading_text_icon);
       		            	$itemDiv.find('.panel-heading').append($loadingTextIcon);
       		        	},
       		        	error: function() {
       		        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       		        	},
       		        	complete: function() {
        		           	$itemDiv.find('span').remove();
       		        	}
       				});
       	    	}
       	    }
       	})
	},
	removeAttr : function(attrId, attrName) {
		if (attrId && isNaN(attrId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}

		var $itemHR = $('.item-hr');
		var $itemDiv = $('.item-section');
		var alertMsg = "<s:text name='msg.alert.remove.item'><s:param><s:text name='msg.param.item.' /></s:param></s:text>";
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
				           		var $tbody = $itemDiv.find('tbody');
				           		$tbody.find('.tr_'+attrId).remove();
				           		var trCnt = $tbody.children().length;
				           		if (trCnt == 0) {
					           		$itemHR.addClass('display-off');
							  		$itemDiv.find('.item-panel').addClass('display-off');
				           		}
							}
       		            },
       		            beforeSend: function() {
       		            	var $loadingTextIcon = $(com_conf.loading_text_icon);
       		            	$itemDiv.find('.panel-heading').append($loadingTextIcon);
       		        	},
       		        	error: function() {
       		        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       		        	},
       		        	complete: function() {
        		        	$itemDiv.find('span').remove();
       		        	}
       				});
       	    	}
       	    }
       	})
	},
	initAddAttr : function() {
		$('.btn-add-attr').on('click', function() {
			pro_att.addAttr();
		});
	},
	addAttr : function() {
		bootbox.dialog({
            title: "<s:text name='sa.pd.item.lbl.add' />",
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
	            			'<div class="form-group">' +
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
	              					var $itemDiv = $('.item-section');
	              			   		var $tbody = $itemDiv.find('tbody');
	
	              			   		var $tr = $(pro_att.conf.tr).addClass('tr_'+data.id);
	           						var _id = $(pro_att.conf.td).html(data.id);
	           						var _nameId = $(pro_att.conf.td).html(data.nameId);
	           						var _name = $(pro_att.conf.td).html(data.name);
	           						var _value = $(pro_att.conf.td).html(data.value);
	           						var _categoryId = $(pro_att.conf.td).html(data.categoryId);
	           						var _filter = $(pro_att.conf.td).html(data.filter);
	           						var _filterType = $(pro_att.conf.td).html(data.filterType);
	           						var _showValue = $(pro_att.conf.td).html(data.showValue);
	           						var _sort = $(pro_att.conf.td).html(data.sort);
	           						var $iRemove = $(pro_att.conf.i_remove);
	           						$iRemove.on('click', function() {
	           							pro_att.removeAttr(data.id, data.name);
	           						});
	           						var $iEdit = $(pro_att.conf.i_edit);
	           						$iEdit.on('click', function() {
	           							pro_att.modifyAttr(data.id);
	           						});
	           						var _setting = $(pro_att.conf.td).append($iRemove).append(" ").append($iEdit);
	
	           						$tr.append(_id).append(_nameId).append(_name).append(_value)
	           							.append(_categoryId).append(_filter).append(_filterType)
	           							.append(_showValue).append(_sort).append(_setting);
	
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
		var $itemDiv = $('.item-section');
		
		$.ajax({
            type: "post",
            url: "editProduct_getItem",
            data: 'pId='+id,
            dataType: "json",
            success: function(data) {
				if (data=='1') {
					alert("<s:text name='msg.err.param'></s:text>");
				} else if (data=='2') {
					alert("<s:text name='msg.no.item'><s:param><s:text name='msg.param.item' /></s:param></s:text>");
				} else if (data=='4') {
					alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.query' /></s:param></s:text>");
				} else {
					var $tr = $('.tr_'+id);
					var $tdArr = $tr.children();
					var formItem = "";
					$.each(data[0], function(i, val) {console.log(" i : " + i + ", val : " + val[0] + val[1] + val[2]);
// 						var item = 
// 						'<input name="pList["' + i + '"].id" type="hidden" value="' + val. + '" class="form-control input-md">' +
//             			'<div class="form-group">' +
// 	        				'<label class="col-md-3 control-label" for="attrNameId">name id</label>' +
// 	        				'<div class="col-md-7">' +
// 	        					'<input id="attrNameId" name="pList[i].nameId" type="text" value="' + $tdArr.eq(1).html() + '" placeholder="name id" class="form-control input-md">' +
// 	        				'</div>' +
// 	        			'</div>';
	        			
					});
					
					bootbox.dialog({
			            title: "<s:text name='sa.pd.item.lbl.edit' /><span style='font-size:14px'>（" + $tdArr.eq(0).html() +  "：" + $tdArr.eq(1).html() + "）</span>",
			            message: 
				            '<div class="row">' +
				            	'<div class="col-md-12">' +
				            		'<form class="form-horizontal attr-form" method="post">' +
				            			'<input name="pList[i].id" type="hidden" value="' +$tdArr.eq(0).html() + '" class="form-control input-md">' +
				            			'<div class="form-group">' +
				            				'<label class="col-md-3 control-label" for="attrNameId">name id</label>' +
				            				'<div class="col-md-7">' +
				            					'<input id="attrNameId" name="pList[i].nameId" type="text" value="' + $tdArr.eq(1).html() + '" placeholder="name id" class="form-control input-md">' +
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
				              					var $itemDiv = $('.item-section');
				              			   		var $tbody = $itemDiv.find('tbody');
				
				              			   		var $tr = $tbody.find('.tr_'+data.id);
				           						var _id = $(pro_att.conf.td).html(data.id);
				           						var _nameId = $(pro_att.conf.td).html(data.nameId);
				           						var _name = $(pro_att.conf.td).html(data.name);
				           						var _value = $(pro_att.conf.td).html(data.value);
				           						var _categoryId = $(pro_att.conf.td).html(data.categoryId);
				           						var _filter = $(pro_att.conf.td).html(data.filter);
				           						var _filterType = $(pro_att.conf.td).html(data.filterType);
				           						var _showValue = $(pro_att.conf.td).html(data.showValue);
				           						var _sort = $(pro_att.conf.td).html(data.sort);
				           						var $iRemove = $(pro_att.conf.i_remove);
				           						$iRemove.on('click', function() {
				           							pro_att.removeAttr(data.id, data.name);
				           						});
				           						var $iEdit = $(pro_att.conf.i_edit);
				           						$iEdit.on('click', function() {
				           							pro_att.modifyAttr(data.id);
				           						});
				           						var _setting = $(pro_att.conf.td).append($iRemove).append(" ").append($iEdit);
				
				           						$tr.empty().append(_id).append(_nameId).append(_name).append(_value)
				           							.append(_categoryId).append(_filter).append(_filterType)
				           							.append(_showValue).append(_sort).append(_setting);
				
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
            },
            beforeSend: function() {
            	var $loadingTextIcon = $(com_conf.loading_text_icon);
            	$itemDiv.find('.panel-heading').append($loadingTextIcon);
        	},
        	error: function() {
        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
        	},
        	complete: function() {
        		$itemDiv.find('span').remove();
        	}
		});
	}
}
$(document).ready(function() {
	pro_att.initCategory();
	pro_att.changeCategory();
});
</script>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><s:text name="sa.pd.item.title" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <form role="form" class="form-inline category-form" enctype="multipart/form-data">
                        <div class="form-group category-list first-category">
                            <label class="category-select  display-off"><s:text name="sa.pd.lbl.select.category" /></label>
                            <select class="form-control category-select display-off"></select>
                        </div>
                        <div class="form-group category-list second-category">
                            <select class="form-control category-select display-off"></select>
                        </div>
                        <div class="form-group category-list third-category">
                            <select class="form-control category-select display-off"></select>
                        </div>
                        
                        <button type="button" class="btn btn-info btn-category-submit category-select display-off"><s:text name="sa.btn.query" /></button>
                        <div class="form-group" style="padding-left:20px">
                            <input type="file" id="attributeFile" name="attributeFile" class="upload-item-file upload-item display-off">
                        </div>
                        <button type="button" class="btn btn-info btn-upload upload-item display-off"><s:text name="sa.btn.upload" /></button>
                    </form>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <hr class="item-hr display-off">
            
            <div class="row item-section">
                <div class="col-lg-12">
                    <div class="panel panel-default item-panel display-off">
                        <div class="panel-heading">
                            <s:text name="sa.pd.item.list.title" />
                            <button type="button" class="btn btn-link btn-remove-all-attr"><s:text name="sa.pd.item.btn.remove.all" /></button>
                            <button type="button" class="btn btn-link btn-add-attr"><s:text name="sa.pd.item.btn.add" /></button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover attr-table">
                                    <thead>
                                        <tr>
                                            <th>Product Id</th>
                                            <th>Product Name</th>
                                            <th>setting</th>
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
