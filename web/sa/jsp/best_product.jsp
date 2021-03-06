<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var best_pd = {
	conf : {
		option : '<option></option>',
		tr : '<tr></tr>',
		td : '<td></td>',
		i_check : '<button type="button" class="btn btn-default btn-select-item btn-circle btn-sm"><i class="fa fa-check"></i></button>',
		i_remove : '<i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="<s:text name='msg.pro.remove'></s:text>"></i>',
	},
	initCategory : function() {
		best_pd.loadFirstCategory();
		best_pd.initSubmitCategory();
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
						var $op = $(best_pd.conf.option);
						$op.val(val.id).html(val.name);
						$firstCategorySelect.append($op);
					});
					$firstCategory.find('option:first').attr('selected', true);
					best_pd.loadCategory('second-category', $firstCategory.find('option:selected').val());
					$('.category-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	best_pd.hideItem();
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
	           		best_pd.hideCategory(type);
	           		$categoryDiv.append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
		           	best_pd.hideCategory(type);
		           	$categoryDiv.append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.category' /></s:param></s:text></span>");
	           	 } else {
	           		$categoryDiv.find('span').remove();
	           		var $categoryDivSelect = $categoryDiv.find('select');
					$.each(data, function(i, val) {
						var $op = $(best_pd.conf.option);
						$op.val(val.id).html(val.name);
						$categoryDivSelect.append($op);
					});
					$categoryDivSelect.find('option:first').attr('selected', true);
					if (type == 'second-category') {
						best_pd.loadCategory('third-category', $('.second-category').find('option:selected').val());
					}
					$('.category-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	best_pd.hideItem();
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
	hideItem : function() {
		$('.item-hr').addClass('display-off');
		$('.item-section .item-panel').addClass('display-off');
		$('.item-section .item-panel').parent().find('span').remove();
	},
	changeCategory : function() {
		$('.first-category').on('change', function() {
			var selectCategoryId = $(this).find('option:selected').val();
			$('.second-category select, .third-category select').empty();
			best_pd.loadCategory('second-category', selectCategoryId);
		});
		$('.second-category').on('change', function() {
			var selectCategoryId = $(this).find('option:selected').val();
			$('.third-category select').empty();
			best_pd.loadCategory('third-category', selectCategoryId);
		});
	},
	initSubmitCategory : function() {
		$('.btn-category-submit').on('click', function() {
			best_pd.loadProductList();
		});
	},
	loadProductList : function() {
		var catetoryId = $('.third-category select').find('option:selected').val();
		var userId = $('.user-list select').find('option:selected').val();
		if (catetoryId && isNaN(catetoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}
		$('.btn-select-all').removeClass('btn-info').addClass('btn-default');
		var $itemHR = $('.item-hr');
		var $itemDiv = $('.item-section');
		$.ajax({
        	type: "post",
			url: "editBestProduct_getItems",
			data: 'cId='+catetoryId + "&userId=" + userId,
			dataType: "json",
			success: function(data) {
				if (data=='1') {
					$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
				} else if (data=='2') {
					$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.item' /></s:param></s:text></span>");
				} else if (data=='4') {
					$itemDiv.find('.item-panel').parent().append("<span><s:text name='msg.fail.do'><s:param><s:text name='msg.param.query' /></s:param></s:text></span>");
				} else {
					best_pd.editItemsTbl(data);
				}
            },
            beforeSend: function() {
            	$itemHR.removeClass('display-off');
           		$itemDiv.find('span').remove();
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
	editItemsTbl : function(data) {
		var $itemDiv = $('.item-section');
   		var $tbody = $itemDiv.find('tbody');
   		var cnt = 0;
		$.each(data, function(i, val) {
			var $tr = $(best_pd.conf.tr).addClass('tr_'+val.productId);
			var _id = $(best_pd.conf.td).html(val.productId);
			var _name = $(best_pd.conf.td).html(val.productName);
			var $iCheck = $(best_pd.conf.i_check);
			if (val.check == 'on') {
				$iCheck.removeClass('btn-default').addClass('btn-info');
				cnt++;
			}
			$iCheck.attr('idx', val.productId).on('click', function() {
				if ($(this).hasClass('btn-default')) {
					$(this).removeClass('btn-default').addClass('btn-info');
					if ($('.btn-select-item.btn-default').length == 0) {
						$('.btn-select-all').removeClass('btn-default').addClass('btn-info');
					}
				} else {
					$(this).removeClass('btn-info').addClass('btn-default');
					$('.btn-select-all').removeClass('btn-info').addClass('btn-default');
				}
			});
			var _setting = $(best_pd.conf.td).append($iCheck);

			$tr.append(_setting).append(_id).append(_name);

			$tbody.append($tr);
		});
		if (data.length == cnt) {
			$('.btn-select-all').removeClass('btn-default').addClass('btn-info');
		}
		$itemDiv.find('.item-panel').removeClass('display-off');
	},
	select_all : function() {
		$('.btn-select-all').click(function() {
			if ($(this).hasClass('btn-default')) {
				$('.btn-circle').removeClass('btn-default').addClass('btn-info');
			} else {
				$('.btn-circle').removeClass('btn-info').addClass('btn-default');
			}
		});
	},
	hideBestPd : function() {
		$('.best-product-hr').addClass('display-off');
		$('.best-product-section .best-product-panel').addClass('display-off');
		$('.best-product-section .best-product-panel').parent().find('span').remove();
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
						var $op = $(best_pd.conf.option);
						$op.val(val.id).html(val.name);
						$userListSelect.append($op);
					});
					$userList.find('option:first').attr('selected', true);
					$('.user-select').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	best_pd.hideBestPd();
            	$userList.prepend($(com_conf.loading_icon));
        	},
            complete: function() {
            	$userList.find('span').remove();
            }
		});
	},
	saveBestProduct : function() {
		$('.btn-add-best-pd').on('click', function() {
			var catetoryId = $('.third-category select').find('option:selected').val();
			var userId = $('.user-list select').find('option:selected').val();;
			var ids = '', names = '';
			$('.btn-select-item.btn-info').each(function() {
				var id = $(this).attr('idx');
				ids = ',' + id + ids;
				names = ',' + $(this).parent().next().next().html() + names;
			});
			if (ids.length > 0) {
				ids = ids.substring(1);
				names = names.substring(1);
			}
			var $itemDiv = $('.item-section');
			var $bdDiv = $('.best-product-section');
			$.ajax({
	        	type: "post",
				url: "editBestProduct_saveBestProduct",
				data: 'cId='+catetoryId + '&pIds=' + ids + '&userId=' + userId,
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
   		            $itemDiv.find('.panel-heading').append($loadingTextIcon);
	        	},
	            complete: function() {
	            	var key = '.bd_' + catetoryId;
	            	var $bd = $(key);
	            	if ($bd.length > 0) {
	            		$bd.find('td').eq(2).html(names);
	            	} else {
						var $tr = $(best_pd.conf.tr).addClass('bd_' + catetoryId);
						var _id = $(best_pd.conf.td).html(catetoryId);
						var _cName = $(best_pd.conf.td).html($('.third-category select').find('option:selected').text());
						var _pName = $(best_pd.conf.td).html(names);
						var _setting  = $(best_pd.conf.td);
   						var $iRemove = $(best_pd.conf.i_remove);
   						$iRemove.attr('idx', catetoryId).on('click', function() {
   							best_pd.removeItem(this);
   						});
						$tr.append(_id).append(_cName).append(_pName).append(_setting.append($iRemove));
						var $tbody = $bdDiv.find('tbody');
						$tbody.append($tr);
						if ($bdDiv.find('span').length > 0) {
							$bdDiv.find('span').remove();
							$bdDiv.find('.best-product-panel').show();
						}
	            	}
	            	$itemDiv.find('span').remove();
	            }
			});
		});
	},
	loadBestProduct : function() {
		$('.btn-user-submit').on('click', function() {
			var userId = $('.user-list select').find('option:selected').val();
			var $bpDiv = $('.best-product-section');
	   		var $tbody = $bpDiv.find('tbody');
			$.ajax({
	        	type: "post",
				url: "editBestProduct_getBestProductList",
				data: '&userId=' + userId,
				dataType: "json",
				success: function(data) {
					if (data=='2') {
						$bpDiv.find('.best-product-panel').parent().append("<span><s:text name='msg.no.item'><s:param><s:text name='msg.param.best.product' /></s:param></s:text></span>");
					} else {
				   		$.each(data[0], function(key, val) {
							var $tr = $(best_pd.conf.tr).addClass('bd_'+key);
							var _id = $(best_pd.conf.td).html(key);
							var _cName = $(best_pd.conf.td).html(val[0]);
							var _pName = $(best_pd.conf.td).html(val[1]);
							var _setting  = $(best_pd.conf.td);
       						var $iRemove = $(best_pd.conf.i_remove);
       						$iRemove.attr('idx', key).on('click', function() {
       							best_pd.removeItem(this);
       						});
							
							$tr.append(_id).append(_cName).append(_pName).append(_setting.append($iRemove));
							$tbody.append($tr);
						});

						$bpDiv.find('.best-product-panel').removeClass('display-off');
					}
	            },
	            beforeSend: function() {
	            	$('.best-product-hr').removeClass('display-off');
   	           		$bpDiv.find('span').remove();
   	           		$bpDiv.find('.best-product-panel').addClass('display-off');
   	           		$tbody.empty();
   	            	var $loadingIcon = $(com_conf.loading_icon);
   	            	$bpDiv.prepend($loadingIcon);
	        	},
	            complete: function() {
	            	$bpDiv.find('.fa-refresh').parent().remove();
	            }
			});
		});
	},
	initRemoveAll : function() {
		$('.btn-remove-all-item').on('click', function() {
			var userId = $('.user-list select').find('option:selected').val();
			best_pd.removeAll(userId);
		});
	},
	removeAll : function(userId) {
		var $bdHR = $('.best-product-hr');
		var $bdDiv = $('.best-product-section');

       	bootbox.confirm({
       	    size: 'small',
       	    message: "<s:text name='msg.alert.remove.item'><s:param><s:text name='msg.param.bd.all.item' /></s:param></s:text>",
       	 	locale: 'zh_CN',
       	    callback: function(result){
       	    	if (result) {
       				$.ajax({
       		            type: "post",
       		            url: "editBestProduct_removeAll",
       		            data: 'userId='+userId,
       		            dataType: "json",
       		            success: function(data) {
       			           	if (data=='1') {
       			           		alert("<s:text name='msg.err.param'></s:text>");
       			           	} else if (data=='3') {
       				           	alert("<s:text name='msg.err.db'></s:text>");
       			           	} else {
            		            alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       			           		$bdHR.addClass('display-off');
            		           	$bdDiv.find('.best-product-panel').addClass('display-off');
            		           	var $tbody = $bdDiv.find('tbody');
            		           	$tbody.empty();
       			           	}
       		            },
       		            beforeSend: function() {
       		            	var $loadingTextIcon = $(com_conf.loading_text_icon);
       		            	$bdDiv.find('.panel-heading').append($loadingTextIcon);
       		        	},
       		        	error: function() {
       		        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       		        	},
       		        	complete: function() {
        		           	$bdDiv.find('span').remove();
       		        	}
       				});
       	    	}
       	    }
       	})
	},
	removeItem : function(obj) {
		var $bdHR = $('.best-product-hr');
		var $bdDiv = $('.best-product-section');
		var $trDiv = $('.bd_' + $(obj).attr('idx'));console.log('tr : '+$trDiv.length);
		var userId = $('.user-list select').find('option:selected').val();;
		var categoryId = $trDiv.find('td').eq(0).html();
		var categoryName = $trDiv.find('td').eq(1).html();
		var alertMsg = "<s:text name='msg.alert.remove.item'><s:param><s:text name='msg.param.bd.category.' /></s:param></s:text>";
		alertMsg = alertMsg.replace(/CategoryName/, categoryName);

       	bootbox.confirm({
       	    size: 'small',
       	    message: alertMsg, 
       	 	locale: 'zh_CN',
       	    callback: function(result){
       	    	if (result) {
       				$.ajax({
       		            type: "post",
       		            url: "editBestProduct_removeItem",
       		            data: 'cId=' + categoryId + '&userId=' + userId,
       		            dataType: "json",
       		            success: function(data) {
							if (data=='1') {
								alert("<s:text name='msg.err.param'></s:text>");
							} else if (data=='3') {
								alert("<s:text name='msg.err.db'></s:text>");
							} else {
							   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
				           		$trDiv.remove();
				           		var $tbody = $bdDiv.find('tbody');
				           		var trCnt = $tbody.children().length;
				           		if (trCnt == 0) {
					           		$bdHR.addClass('display-off');
							  		$bdDiv.find('.best-product-panel').addClass('display-off');
				           		}
							}
       		            },
       		            beforeSend: function() {
       		            	var $loadingTextIcon = $(com_conf.loading_text_icon);
       		            	$bdDiv.find('.panel-heading').append($loadingTextIcon);
       		        	},
       		        	error: function() {
       		        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       		        	},
       		        	complete: function() {
        		        	$bdDiv.find('span').remove();
       		        	}
       				});
       	    	}
       	    }
       	})
	},
	transformerSubmit : function() {
		$('.btn-transformer').on('click', function() {
			var userId = $('.user-list select').find('option:selected').val();
			var $bpDiv = $('.best-product-section');
	   		var $tbody = $bpDiv.find('tbody');

	   		$.ajax({
	        	type: "post",
				url: "editBestProduct_transformer",
				data: '&userId=' + userId,
				dataType: "json",
				success: function(data) {
					if (data=='1') {
						alert("error param, userId : " + userId);
					} else if (data=='100') {
				   		alert("transformer success");
					} else {
						alert("error cause by : " + data);
					}
	            },
			});
		});
	}
};

$(document).ready(function() {
	best_pd.initCategory();
	best_pd.changeCategory();
	best_pd.select_all();
	best_pd.load_user();
	best_pd.saveBestProduct();
	best_pd.loadBestProduct();
	best_pd.initRemoveAll();
});
</script>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><s:text name="sa.best.pd.title" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-6" >
                    <form role="form" class="form-inline user-form" >
                        <div class="form-group user-list">
                            <label class="user-select display-off"><s:text name="sa.pd.lbl.select.user" /></label>
                            <select class="form-control user-select display-off"></select>
                        </div>
                        
                        <button type="button" class="btn btn-info btn-user-submit user-select display-off"><s:text name="sa.btn.query" /></button>
                    </form>
		            <hr class="best-product-hr display-off">
		            <div class="row best-product-section">
		                <div class="col-lg-12">
		                    <div class="panel panel-default best-product-panel display-off">
		                        <div class="panel-heading">
		                            <s:text name="sa.best.pd.list.title" />
                           			 <button type="button" class="btn btn-link btn-remove-all-item"><s:text name="sa.pd.item.btn.remove.all" /></button>
		                        </div>
		                        <!-- /.panel-heading -->
		                        <div class="panel-body">
		                            <div class="table-responsive">
		                                <table class="table table-hover best-product-table">
		                                    <thead>
		                                        <tr>
		                                        	<th>分类</th>
		                                            <th>分类名</th>
		                                            <th>产品名</th>
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
                <div class="col-lg-6">
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
                    </form>
		            <hr class="item-hr display-off">
		            <div class="row item-section">
		                <div class="col-lg-12">
		                    <div class="panel panel-default item-panel display-off">
		                        <div class="panel-heading">
		                            <s:text name="sa.pd.item.list.title" />
		                            <button type="button" class="btn btn-link btn-add-best-pd">保存</button>
		                        </div>
		                        <!-- /.panel-heading -->
		                        <div class="panel-body">
		                            <div class="table-responsive">
		                                <table class="table table-hover attr-table">
		                                    <thead>
		                                        <tr>
		                                        	<th>
		                                        		<button type="button" class="btn btn-default btn-circle btn-sm btn-select-all"><i class="fa fa-check"></i></button>
		                                        	</th>
		                                            <th>Product Id</th>
		                                            <th>Product Name</th>
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
                <!-- /.col-lg-7 -->
            </div>
            <!-- /.row -->
            
        </div>
        <!-- /#page-wrapper -->
