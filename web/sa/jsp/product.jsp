<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var pd_item = {
	conf : {
		option : '<option></option>',
		tr : '<tr></tr>',
		td : '<td></td>',
		i_remove : '<i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="<s:text name='msg.pro.remove'></s:text>"></i>',
		i_edit : '<i class="fa fa-cog fa-2 cursor-pointer" style="color:#337ab7" title="<s:text name='msg.pro.edit'></s:text>"></i>',
		roles : '<s:property value="#session.yiqin_sa_user_roles" />',
	},
	initCategory : function() {
		pd_item.loadFirstCategory();
		pd_item.initSubmitCategory();
		pd_item.initUploadSubmit();
		pd_item.initRemoveAllItem();
		pd_item.initAddItem();
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
						var $op = $(pd_item.conf.option);
						$op.val(val.id).html(val.name);
						$firstCategorySelect.append($op);
					});
					$firstCategory.find('option:first').attr('selected', true);
					pd_item.loadCategory('second-category', $firstCategory.find('option:selected').val());
					$('.category-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	pd_item.hideItem();
            	$firstCategory.prepend($(com_conf.loading_icon));
        	},
            complete: function() {
            	$firstCategory.find('span').remove();
            }
		});
	},
	loadCategory : function(type, categoryId) {
		var $categoryDiv = $('.' + type);
		$.ajax({
            type: "post",
            url: "getCategoryList_getList",
            data: 'cId='+categoryId,
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		pd_item.hideCategory(type);
	           		$categoryDiv.append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
		           	pd_item.hideCategory(type);
		           	$categoryDiv.append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.category' /></s:param></s:text></span>");
	           	 } else {
	           		$categoryDiv.find('span').remove();
	           		var $categoryDivSelect = $categoryDiv.find('select');
					$.each(data, function(i, val) {
						var $op = $(pd_item.conf.option);
						$op.val(val.id).html(val.name);
						$categoryDivSelect.append($op);
					});
					$categoryDivSelect.find('option:first').attr('selected', true);
					if (type == 'second-category') {
						pd_item.loadCategory('third-category', $('.second-category').find('option:selected').val());
					}
					$('.category-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	pd_item.hideItem();
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
       		$('.btn-download-product-submit').addClass('display-off');
		} else if (type == 'third-category') {
       		$('.third-category select').addClass('display-off');
       		$('.btn-category-submit').addClass('display-off');
       		$('.btn-download-product-submit').addClass('display-off');
		}
	},
	hideItem : function() {
		$('.item-hr').addClass('display-off');
// 		$('.upload-item').addClass('display-off');
		$('.item-section .item-panel').addClass('display-off');
		$('.item-section .item-panel').parent().find('span').remove();
	},
	changeCategory : function() {
		$('.first-category').on('change', function() {
			var selectCategoryId = $(this).find('option:selected').val();
			$('.second-category select, .third-category select').empty();
			pd_item.loadCategory('second-category', selectCategoryId);
			$('.btn-download-product-submit').addClass('display-off');
		});
		$('.second-category').on('change', function() {
			var selectCategoryId = $(this).find('option:selected').val();
			$('.third-category select').empty();
			pd_item.loadCategory('third-category', selectCategoryId);
			$('.btn-download-product-submit').addClass('display-off');
		});
		$('.third-category').on('change', function() {
			$('.btn-download-product-submit').addClass('display-off');
		});
	},
	initSubmitCategory : function() {
		$('.btn-category-submit').on('click', function() {
			pd_item.loadProductList();
		})
		$('.btn-download-product-submit').on('click', function() {
			pd_item.downloadProducts();
		});
	},
	downloadProducts : function() {
		var categoryId = $('.third-category select').find('option:selected').val();
		if (categoryId && isNaN(categoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}
		window.location.href = "exportProductExcel?categoryId=" + categoryId;
	},
	loadProductList : function() {
		$('.btn-download-product-submit').addClass('display-off');
		var categoryId = $('.third-category select').find('option:selected').val();
		if (categoryId && isNaN(categoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}
		var $itemHR = $('.item-hr');
		var $itemDiv = $('.item-section');
		$.ajax({
        	type: "post",
			url: "editProduct_getItems",
			data: 'cId='+categoryId,
			dataType: "json",
			success: function(data) {
				if (data=='1') {
					$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
				} else if (data=='2') {
// 					$('.upload-item').removeClass('display-off');
					$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.item' /></s:param></s:text></span>");
				} else if (data=='4') {
// 					$('.upload-item').removeClass('display-off');
					$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.fail.do'><s:param><s:text name='msg.param.query' /></s:param></s:text></span>");
				} else {
					pd_item.editItemsTbl(data);
					$('.btn-download-product-submit').removeClass('display-off');
				}
            },
            beforeSend: function() {
            	$itemHR.removeClass('display-off');
           		$itemDiv.find('span').remove();
// 				$('.upload-item').addClass('display-off');
           		$itemDiv.find('.item-panel').addClass('display-off');
           		var $tbody = $itemDiv.find('tbody');
           		$tbody.empty();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$itemDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$itemDiv.find('.fa-refresh').parent().remove();
            	$('.upload-item').removeClass('display-off');
            }
		});
	},
	initUploadSubmit : function() {
		$('.btn-upload').on('click', function() {
			pd_item.uploadProduct();
		});
	},
	uploadProduct : function() {
		var categoryId = $('.third-category select').find('option:selected').val();
		if (categoryId && isNaN(categoryId)) {
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
            url: "uploadItem",
            fileElementId: 'itemFile',
            additionalData: {cId : categoryId},
            dataType: "json",
            success: function(data) {
	           	if (data=='1') {
	           		$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
	           	} else if (data=='2') {
// 					$('.upload-item').removeClass('display-off');
		           	$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.item' /></s:param></s:text></span>");
	           	} else if (data=='3') {
// 					$('.upload-item').removeClass('display-off');
			        $itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.err.db'></s:text></span>");
		        } else {
	           		pd_item.editItemsTbl(data);
	           	}
	           	$itemDiv.find('.fa-refresh').parent().remove();
				$('.upload-item').removeClass('display-off');
            },
            error : function(data,status,e) {
            	alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.upload' /></s:param></s:text>");
	           	$itemDiv.find('.fa-refresh').parent().remove();
	           	$('.upload-item').removeClass('display-off');
            }
		});
	},
	editItemsTbl : function(data) {
		var $itemDiv = $('.item-section');
   		var $tbody = $itemDiv.find('tbody');
		$.each(data, function(i, val) {
			var $tr = $(pd_item.conf.tr).addClass('tr_'+val.productId);
			var _id = $(pd_item.conf.td).html(val.productId);
			var _name = $(pd_item.conf.td).html(val.productName);
			$tr.append(_id).append(_name);

			var roles = pd_item.conf.roles;
			var $iRemove = '';
			if (roles.indexOf('13203') > -1) {
				$iRemove = $(pd_item.conf.i_remove);
				$iRemove.on('click', function() {
					pd_item.removeItem(val.productId, val.productName);
				});
			}
			var $iEdit = '';
			if (roles.indexOf('13204') > -1) {
				$iEdit = $(pd_item.conf.i_edit);
				$iEdit.on('click', function() {
					pd_item.modifyItem(val.productId);
				});
			}
			
			if ($iRemove.length > 0 || $iEdit.length > 0) {
				var _setting = $(pd_item.conf.td).append($iRemove).append(" ").append($iEdit);
				$tr.append(_setting);
			}

			$tbody.append($tr);
		});
		$itemDiv.find('.item-panel').removeClass('display-off');
	},
	initRemoveAllItem : function() {
		$('.btn-remove-all-item').on('click', function() {
			pd_item.removeAllItem();
		});
	},
	removeAllItem : function() {
		var categoryId = $('.third-category select').find('option:selected').val();
		if (categoryId && isNaN(categoryId)) {
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
       		            data: 'cId='+categoryId,
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
	removeItem : function(itemId, itemName) {
		if (itemId && isNaN(itemId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}

		var $itemHR = $('.item-hr');
		var $itemDiv = $('.item-section');
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
       		            url: "editProduct_removeItem",
       		            data: 'pId='+itemId,
       		            dataType: "json",
       		            success: function(data) {
							if (data=='1') {
								alert("<s:text name='msg.err.param'></s:text>");
							} else if (data=='3') {
								alert("<s:text name='msg.err.db'></s:text>");
							} else {
							   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
				           		var $tbody = $itemDiv.find('tbody');
				           		$tbody.find('.tr_'+itemId).remove();
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
	initAddItem : function() {
		$('.btn-add-item').on('click', function() {
			pd_item.addItem();
		});
	},
	addItem : function() {
		var categoryId = $('.third-category select').find('option:selected').val();
		if (categoryId && isNaN(categoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}
		var $itemDiv = $('.item-section');

		$.ajax({
            type: "post",
            url: "getAttribute_getList",
            data: 'cId='+categoryId,
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		$attrDiv.find('.attr-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
					$('.upload-attr').removeClass('display-off');
		           	$attrDiv.find('.attr-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.attribute' /></s:param></s:text></span>");
	           	 } else {
					var formItem = "";
	         		$.each(data, function(i, val) {
						var item = 
						'<input name="products[' + i + '].productId" type="hidden" class="item-product-id">' +
						'<input name="products[' + i + '].attributeId" type="hidden" value="' + val.id + '" >' +
            			'<div class="form-group">' +
	        				'<label class="col-md-3 control-label" for="' + val.nameId + '">' + val.name + '</label>' +
	        				'<div class="col-md-7">' +
	        					'<input id="' + val.nameId + '" name="products[' + i + '].value" type="text" placeholder="' + val.name + '" class="form-control input-md">' +
	        				'</div>' +
	        			'</div>';
	        			formItem += item;
	         		});
	         		bootbox.dialog({
	                    title: "<s:text name='sa.pd.item.lbl.add' /><span style='font-size:14px'>（Product Id : " + categoryId + "<input type='text' id='addProductId'>）</span>",
	                    message: 
	        	            '<div class="row">' +
	        	            	'<div class="col-md-12">' +
	        	            		'<form class="form-horizontal item-form" method="post">' + formItem + '</form>' +
	        	            	'</div>' +
	        	            '</div>',
	                    buttons: {
	                        reset: {
	                            label: "<s:text name='sa.btn.reset' />",
	                            className: "btn-default",
	                            callback: function () {
	                                $('.item-form')[0].reset();
	                                return false;
	                            }
	                        },
	                        success: {
	                            label: "<s:text name='sa.btn.save' />",
	                            className: "btn-success",
	                            callback: function () {
	                            	var pId = $('#addProductId').val();
	                            	if (pId == "" || pId.length != 4) {
	                            		alert("<s:text name='msg.err.wrong.product.id' />");
	                            		return false;
	                            	}
	                            	pId = categoryId + pId;
	                            	$('.item-product-id').val(pId);
	                            	var options = {
	                        		    url : 'editProduct_saveProduct',
	                        		    dataType : 'json',
	                        		    data : {pId : pId},
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
	        	
	        	              			   		var $tr = $(pd_item.conf.tr).addClass('tr_'+data.productId);
	        	           						var _id = $(pd_item.conf.td).html(data.productId);
	        	           						var _name = $(pd_item.conf.td).html(data.productName);
	        	           						$tr.append(_id).append(_name);

	        	           						var roles = pd_item.conf.roles;
	        	           						var $iRemove = '';
	        	           						if (roles.indexOf('13203') > -1) {
		        	           						$iRemove = $(pd_item.conf.i_remove);
		        	           						$iRemove.on('click', function() {
		        	           							pd_item.removeItem(data.productId, data.productName);
		        	           						});
	        	           						}
	        	           						var $iEdit = '';
	        	           						if (roles.indexOf('13204') > -1) {
		        	           						$iEdit = $(pd_item.conf.i_edit);
		        	           						$iEdit.on('click', function() {
		        	           							pd_item.modifyItem(data.productId);
		        	           						});
	        	           						}

	        	           						if ($iRemove.length > 0 || $iEdit.length > 0) {
		        	           						var _setting = $(pd_item.conf.td).append($iRemove).append(" ").append($iEdit);
		        	           						$tr.append(_id).append(_name).append(_setting);
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
	        	              		$('.item-form').ajaxSubmit(options);
	        	              		return false;
	                            }
	                        }
	                    }
	                });
	           	 }
            },
            beforeSend: function() {
            	var $loadingTextIcon = $(com_conf.loading_text_icon);
            	$itemDiv.find('.panel-heading').append($loadingTextIcon);
        	},
        	error: function() {
        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.modify' /></s:param></s:text>");
        	},
        	complete: function() {
        		$itemDiv.find('span').remove();
        	}
		});
	},
	modifyItem : function(id) {
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
					var idx= 0;
					$.each(data[0], function(i, val) {
						var item = "";
						var tId = '<input name="products[' + idx + '].id" type="hidden" value="' + val[2] + '" >';
						if (val[2] != "0") {
							item += tId;
						}
						item +=
						'<input name="products[' + idx + '].productId" type="hidden" value="' + id + '" >' +
						'<input name="products[' + idx + '].attributeId" type="hidden" value="' + i + '" >' +
            			'<div class="form-group">' +
	        				'<label class="col-md-3 control-label" for="' + i + '">' + val[0] + '</label>' +
	        				'<div class="col-md-7">' +
	        					'<input id="' + i + '" name="products[' + idx + '].value" type="text" value="' + val[1] + '" placeholder="' + val[0] + '" class="form-control input-md">' +
	        				'</div>' +
	        			'</div>';
	        			formItem += item;
	        			++idx;
					});
					
					bootbox.dialog({
			            title: "<s:text name='sa.pd.item.lbl.edit' /><span style='font-size:14px'>（" + $tdArr.eq(0).html() +  "：" + $tdArr.eq(1).html() + "）</span>",
			            message: 
				            '<div class="row">' +
				            	'<div class="col-md-12">' +
				            		'<form class="form-horizontal item-form" method="post">' + formItem + '</form>' +
				            	'</div>' +
				            '</div>',
			            buttons: {
			                reset: {
			                    label: "<s:text name='sa.btn.reset' />",
			                    className: "btn-default",
			                    callback: function () {
			                        $('.item-form')[0].reset();
			                        return false;
			                    }
			                },
			                success: {
			                    label: "<s:text name='sa.btn.modify' />",
			                    className: "btn-success",
			                    callback: function () {
			                    	var options = {
			                		    url : 'editProduct_saveProduct',
			                		    dataType : 'json',
			                		    data : {pId : id},
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

				              					var _id = $(pd_item.conf.td).html(data.productId);
				              					var _name = $(pd_item.conf.td).html(data.productName);
				              					$tr.empty().append(_id).append(_name);

	        	           						var roles = pd_item.conf.roles;
	        	           						var $iRemove = '';
	        	           						if (roles.indexOf('13203') > -1) {
					           						$iRemove = $(pd_item.conf.i_remove);
					           						$iRemove.on('click', function() {
					           							pd_item.removeItem(data.productId, data.productName);
					           						});
	        	           						}
	        	           						var $iEdit = '';
	        	           						if (roles.indexOf('13204') > -1) {
					           						$iEdit = $(pd_item.conf.i_edit);
					           						$iEdit.on('click', function() {
					           							pd_item.modifyItem(data.productId);
					           						});
	        	           						}

	        	           						if ($iRemove.length > 0 || $iEdit.length > 0) {
		        	           						var _setting = $(pd_item.conf.td).append($iRemove).append(" ").append($iEdit);
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
				              		$('.item-form').ajaxSubmit(options);
				              		return false;
			                    }
			                }
			            }
			        });
				}
            },
            beforeSend: function() {
            	var $loadingTextIcon = $(com_conf.loading_text_icon);
            	$itemDiv.find('.panel-heading').append($loadingTextIcon);
        	},
        	error: function() {
        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.modify' /></s:param></s:text>");
        	},
        	complete: function() {
        		$itemDiv.find('span').remove();
        	}
		});
	}
}
$(document).ready(function() {
	pd_item.initCategory();
	pd_item.changeCategory();
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
                        <s:if test="%{#roles.indexOf('13205')>-1}">
                        <button type="button" class="btn btn-info btn-download-product-submit display-off"><s:text name="sa.btn.download" /></button>
                        </s:if>
                        <div class="form-group" style="padding-left:20px">
                            <input type="file" id="itemFile" name="itemFile" class="upload-item-file upload-item ">
                        </div>
                        <button type="button" class="btn btn-info btn-upload upload-item "><s:text name="sa.btn.upload" /></button>
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
                			<s:if test="%{#roles.indexOf('13201')>-1}">
                            <button type="button" class="btn btn-link btn-remove-all-item"><s:text name="sa.pd.item.btn.remove.all" /></button>
                            </s:if>
                			<s:if test="%{#roles.indexOf('13202')>-1}">
                            <button type="button" class="btn btn-link btn-add-item"><s:text name="sa.pd.item.btn.add" /></button>
                            </s:if>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover attr-table">
                                    <thead>
                                        <tr>
                                            <th>Product Id</th>
                                            <th>Product Name</th>
                							<s:if test="%{#roles.indexOf('13203')>-1 || #roles.indexOf('13204')>-1}">
                                            <th>setting</th>
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
