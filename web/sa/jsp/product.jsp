<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">
var pro_att = {
	conf : {
		option : '<option></option>',
		tr : '<tr></tr>',
		td : '<td></td>',
		i_remove : '<i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="<s:text name='msg.attr.remove'></s:text>"></i>',
		i_edit : '<i class="fa fa-cog fa-2 cursor-pointer" style="color:#337ab7" title="<s:text name='msg.attr.edit'></s:text>"></i>',
	},
	initCategory : function() {
		pro_att.loadFirstCategory();
		pro_att.initSubmitCategory();
		pro_att.initUploadSubmit();
		pro_att.removeAllAttr();
	},
	loadFirstCategory : function() {
		var $firstCategory = $('.first-category');
		$.ajax({
            type: "post",
            url: "getCategoryList_getFirst",
            dataType: "json",
            success: function(data) {
	           	 if (data=='2') {
	           		$firstCategory.html("<s:text name='msg.no.item'><s:param name='msg.param.category'></s:param></s:text>");
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
		           	$categoryDiv.append("<span><s:text name='msg.no.item'><s:param name='msg.param.category'></s:param></s:text></span>");
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
		$('.attr-hr').addClass('display-off');
		$('.upload-attr').addClass('display-off');
		$('.attr-section .attr-panel').addClass('display-off');
		$('.attr-section .attr-panel').parent().find('span').remove();
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
			pro_att.loadAttributeList();
		})
	},
	loadAttributeList : function() {
		var catetoryId = $('.third-category select').find('option:selected').val();
		if (catetoryId && isNaN(catetoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}
		var $attrHR = $('.attr-hr');
		var $attrDiv = $('.attr-section');
		$.ajax({
            type: "post",
            url: "getAttribute_getList",
            data: 'cId='+catetoryId,
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		$attrDiv.find('.attr-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
					$('.upload-attr').removeClass('display-off');
		           	$attrDiv.find('.attr-panel').parent().append("<span><s:text name='msg.no.item'><s:param name='msg.param.attribute'></s:param></s:text></span>");
	           	 } else {
	           		pro_att.editAttrTbl(data);
	           	 }
            },
            beforeSend: function() {
            	$attrHR.removeClass('display-off');
           		$attrDiv.find('span').remove();
				$('.upload-attr').addClass('display-off');
           		$attrDiv.find('.attr-panel').addClass('display-off');
           		var $tbody = $attrDiv.find('tbody');
           		$tbody.empty();
            	var $loadingIcon = $(com_conf.loading_icon);
            	$attrDiv.prepend($loadingIcon);
        	},
            complete: function() {
            	$attrDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	initUploadSubmit : function() {
		$('.btn-upload').on('click', function() {
			pro_att.uploadAttribute();
		});
	},
	uploadAttribute : function() {
		var catetoryId = $('.third-category select').find('option:selected').val();
		if (catetoryId && isNaN(catetoryId)) {
			alert("<s:text name='msg.err.param'></s:text>");
			return ;
		}
		
		var $attrHR = $('.attr-hr');
		var $attrDiv = $('.attr-section');

    	$attrHR.removeClass('display-off');
   		$attrDiv.find('span').remove();
		$('.upload-attr').addClass('display-off');
   		$attrDiv.find('.attr-panel').addClass('display-off');
   		var $tbody = $attrDiv.find('tbody');
   		$tbody.empty();
    	var $loadingIcon = $(com_conf.loading_icon);
    	$attrDiv.prepend($loadingIcon);
    	
		$.ajaxFileUpload({
            url: "uploadAttribute",
            fileElementId: 'attributeFile',
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	           		$attrDiv.find('.attr-panel').parent().append("<span><s:text name='msg.err.param'></s:text></span>");
	           	 } else if (data=='2') {
					$('.upload-attr').removeClass('display-off');
		           	$attrDiv.find('.attr-panel').parent().append("<span><span><s:text name='msg.no.item'><s:param name='msg.param.attribute'></s:param></s:text></span>");
	           	 } else {
	           		pro_att.editAttrTbl(data);
	           	}
	           	$attrDiv.find('.fa-refresh').parent().remove();
            },
            error : function(data,status,e) {
            	alert("<s:text name='msg.err.upload'></s:text>");
	           	$attrDiv.find('.fa-refresh').parent().remove();
            }
		});
	},
	editAttrTbl : function(data) {
		var $attrDiv = $('.attr-section');
   		var $tbody = $attrDiv.find('tbody');
		$.each(data, function(i, val) {
			var $tr = $(pro_att.conf.tr).addClass('tr_'+val.id);
			var _id = $(pro_att.conf.td).html(val.id);
			var _nameId = $(pro_att.conf.td).html(val.nameId);
			var _name = $(pro_att.conf.td).html(val.name);
			var _value = $(pro_att.conf.td).html(val.value);
			var _categoryId = $(pro_att.conf.td).html(val.categoryId);
			var _filter = $(pro_att.conf.td).html(val.filter);
			var _filterType = $(pro_att.conf.td).html(val.filterType);
			var _showValue = $(pro_att.conf.td).html(val.showValue);
			var _sort = $(pro_att.conf.td).html(val.sort);
			var $iRemove = $(pro_att.conf.i_remove);
			$iRemove.on('click', function() {
				pro_att.removeAttr(val.id, val.name);
			});
			var $iEdit = $(pro_att.conf.i_edit);
			$iEdit.on('click', function() {
				alert("edit id : " + val.id);
			});
			var _setting = $(pro_att.conf.td).append($iRemove).append(" ").append($iEdit);

			$tr.append(_id).append(_nameId).append(_name).append(_value)
				.append(_categoryId).append(_filter).append(_filterType)
				.append(_showValue).append(_sort).append(_setting);

			$tbody.append($tr);
		});
		$attrDiv.find('.attr-panel').removeClass('display-off');
	},
	removeAllAttr : function() {
		$('.btn-remove-all-attr').on('click', function() {
			var catetoryId = $('.third-category select').find('option:selected').val();
			if (catetoryId && isNaN(catetoryId)) {
				alert("<span><s:text name='msg.err.param'></s:text>");
				return ;
			}

			var $attrHR = $('.attr-hr');
			var $attrDiv = $('.attr-section');

        	bootbox.confirm({
        	    size: 'small',
        	    message: "<s:text name='msg.alert.remove.item'><s:param><s:text name='msg.param.all.attr' /></s:param></s:text>", 
        	    callback: function(result){
        	    	if (result) {
        				$.ajax({
        		            type: "post",
        		            url: "editAttribute_removeAll",
        		            data: 'categoryId='+catetoryId,
        		            dataType: "json",
        		            success: function(data) {
        			           	if (data=='1') {
        			           		alert("<span><s:text name='msg.err.param'></s:text>");
        			           	} else if (data=='3') {
        				           	alert("<s:text name='msg.err.db'></s:text>");
        			           	} else {
             		            	alert("<s:text name='msg.suc.remove'></s:text>");
        			           		$attrHR.addClass('display-off');
             		           		$attrDiv.find('.attr-panel').addClass('display-off');
             		           		var $tbody = $attrDiv.find('tbody');
             		           		$tbody.empty();
        			           	}
        		            },
        		            beforeSend: function() {
        		            	var $loadingTextIcon = $(com_conf.loading_text_icon);
        		            	$attrDiv.find('.panel-heading').append($loadingTextIcon);
        		        	},
        		        	error: function() {
        		        		alert("<s:text name='msg.err.remove'></s:text>");
        		        	},
        		        	complete: function() {
         		           		$attrDiv.find('span').remove();
        		        	}
        				});
        	    	}
        	    }
        	})
		});
	},
	removeAttr : function(attrId, attrName) {
		if (attrId && isNaN(attrId)) {
			alert("<span><s:text name='msg.err.param'></s:text>");
			return ;
		}

		var $attrHR = $('.attr-hr');
		var $attrDiv = $('.attr-section');
		var alertMsg = "<s:text name='msg.alert.remove.item'><s:param><s:text name='msg.param.attr.' /></s:param></s:text>";
		alertMsg = alertMsg.replace(/attrName/, attrName);

       	bootbox.confirm({
       	    size: 'small',
       	    message: alertMsg, 
       	    callback: function(result){
       	    	if (result) {
       				$.ajax({
       		            type: "post",
       		            url: "editAttribute_removeAttr",
       		            data: 'attrId='+attrId,
       		            dataType: "json",
       		            success: function(data) {
							if (data=='1') {
								alert("<span><s:text name='msg.err.param'></s:text>");
							} else if (data=='3') {
								alert("<s:text name='msg.err.db'></s:text>");
							} else {
							   	alert("<s:text name='msg.suc.remove'></s:text>");
				           		var $tbody = $attrDiv.find('tbody');
				           		$tbody.find('.tr_'+attrId).remove();
				           		var trCnt = $tbody.children().length;
				           		if (trCnt == 0) {
					           		$attrHR.addClass('display-off');
							  		$attrDiv.find('.attr-panel').addClass('display-off');
				           		}
							}
       		            },
       		            beforeSend: function() {
       		            	var $loadingTextIcon = $(com_conf.loading_text_icon);
       		            	$attrDiv.find('.panel-heading').append($loadingTextIcon);
       		        	},
       		        	error: function() {
       		        		alert("<s:text name='msg.err.remove'></s:text>");
       		        	},
       		        	complete: function() {
        		        	$attrDiv.find('span').remove();
       		        	}
       				});
       	    	}
       	    }
       	})
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
                    <h3 class="page-header"><s:text name="sa.pd.attr.title" /></h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <form role="form" class="form-inline category-form" enctype="multipart/form-data">
                        <div class="form-group category-list first-category">
                            <label class="category-select  display-off"><s:text name="sa.pd.attr.lbl.select" /></label>
                            <select class="form-control category-select display-off"></select>
                        </div>
                        <div class="form-group category-list second-category">
                            <select class="form-control category-select display-off"></select>
                        </div>
                        <div class="form-group category-list third-category">
                            <select class="form-control category-select display-off"></select>
                        </div>
                        
                        <button type="button" class="btn btn-info btn-category-submit category-select display-off"><s:text name="sa.pd.attr.btn.query" /></button>
                        <div class="form-group" style="padding-left:20px">
                            <input type="file" id="attributeFile" name="attributeFile" class="upload-attr-file upload-attr display-off">
                        </div>
                        <button type="button" class="btn btn-info btn-upload upload-attr display-off"><s:text name="sa.pd.attr.btn.upload" /></button>
                    </form>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <hr class="attr-hr display-off">
            
            <div class="row attr-section">
                <div class="col-lg-12">
                    <div class="panel panel-default attr-panel display-off">
                        <div class="panel-heading">
                            <s:text name="sa.pd.attr.list.title" />
                            <button type="button" class="btn btn-link btn-remove-all-attr"><s:text name="sa.pd.attr.btn.remove.all" /></button>
                            <button type="button" class="btn btn-link btn-add-attr"><s:text name="sa.pd.attr.btn.add" /></button>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover attr-table">
                                    <thead>
                                        <tr>
                                            <th>id</th>
                                            <th>nameId</th>
                                            <th>name</th>
                                            <th>value</th>
                                            <th>categoryId</th>
                                            <th>filter</th>
                                            <th>filterType</th>
                                            <th>showValue</th>
                                            <th>sort</th>
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
