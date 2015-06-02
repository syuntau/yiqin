<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">
var user_admin = {
	msg : {
		btn_save : '<s:text name="sa.btn.save" />',
		btn_modify : '<s:text name="sa.btn.modify" />',
		lbl_create : '<s:text name="sa.user.lbl.create.admin"/>',
		lbl_edit : '<s:text name="sa.user.lbl.edit.admin" />',
	},
	conf : {
		cls_warning : 'alert-warning',
		cls_done : 'alert-success',
		cls_danger : 'alert-danger',
		admin_arr : [],
		tr : '<tr></tr>',
		td : '<td></td>',
		i_edit : '<i class="fa fa-cog fa-2 cursor-pointer" style="color:#337ab7" title="<s:text name='msg.admin.edit'></s:text>"></i>',
		i_remove : '<i class="fa fa-times fa-2 cursor-pointer" style="color:#c9302c" title="<s:text name='msg.admin.remove'></s:text>"></i>',
		span : '<span></span>',
	},
	tpl : {
		tip : '<div class="alert" role="alert" style="padding-top:6px;padding-bottom:6px;padding-left:15px;padding-right:15px;display:inline;"><strong></strong></div>'
	},
	init : function() {
		user_admin.load_user();
		user_admin.create_admin();
	},
	load_user : function() {
		var $adminList = $('.admin-list');
		$.ajax({
            type: "post",
            url: "editUser_findAdmin",
            dataType: "json",
            success: function(data) {
	           	 if (data=='2') {
	           		$adminList.find('.create-admin').after("<span style='margin-left:30px;'><s:text name='msg.no.item'><s:param><s:text name='msg.param.admin' /></s:param></s:text></span>");
	           	 } else {
	           		var $tbody = $adminList.find('tbody');
	        		$.each(data, function(i, val) {
	        			user_admin.conf.admin_arr[val.id] = val;
	        			var $tr = $(user_admin.conf.tr).addClass('tr_'+val.id);
	        			var _id = $(user_admin.conf.td).html(val.id);
	        			var _name = $(user_admin.conf.td).html(val.name);
	        			var $iEdit = $(user_admin.conf.i_edit);
	        			$iEdit.attr('idx', val.id).on('click', function() {
            				user_admin.modify_admin(val.id);
	        			});
	        			var $iRemove = $(user_admin.conf.i_remove);
	        			$iRemove.attr('idx', val.id).on('click', function() {
            				user_admin.remove_admin(val.id);
	        			});
	        			var _setting = $(user_admin.conf.td).append($iEdit).append("&nbsp;&nbsp;").append($iRemove);

	        			$tr.append(_id).append(_name).append(_setting);

	        			$tbody.append($tr);
	        		});
	        		$adminList.find('.panel').removeClass('display-off');
	           	 }
            },
            beforeSend: function() {
            	$adminList.prepend($(com_conf.loading_text_icon));
        	},
            complete: function() {
            	$adminList.find('.fa-refresh').parent().remove();
            }
		});
	},
	create_admin : function() {
		$('.create-admin').click(function() {
			bootbox.dialog({
	            title: '<s:text name="sa.user.lbl.create.admin"/><span style="font-size:14px;margin-left:20px;" class="msg-info"></span>',
	            message: 
                    '<div class="row">' +
		                '<div class="col-lg-12">' +
		                    '<form role="form" class="admin-form" method="post">' +
		                    	'<div class="form-group">' +
		                            '<label><s:text name="sa.user.lbl.user.id" /></label>' +
		                            '<input type="text" name="admin.id" class="form-control admin-id" maxlength="8">' +
		                            '<p class="help-block"><s:text name="sa.user.lbl.userId"/></p>' +
		                        '</div>' +
		                        '<div class="form-group">' +
		                            '<label><s:text name="sa.user.lbl.user.name" /></label>' +
		                            '<input type="text" name="admin.name" class="form-control admin-name" maxlength="20">' +
		                            '<p class="help-block"><s:text name="sa.user.lbl.userName"/></p>' +
		                        '</div>' +
		                        '<div class="form-group">' +
		                            '<label><s:text name="sa.user.lbl.password" /></label>' +
		                            '<input type="password" name="admin.password" class="form-control admin-pass" maxlength="8">' +
		                            '<p class="help-block"><s:text name="sa.user.lbl.pass" /></p>' +
		                        '</div>' +
		                        '<div class="form-group">' +
		                            '<label><s:text name="sa.user.lbl.confirm.password" /></label>' +
		                            '<input type="password" class="form-control admin-confirm-pass" maxlength="8">' +
		                            '<p class="help-block"><s:text name="sa.user.lbl.confirm.pass" /></p>' +
		                        '</div>' +
		                    '</form>' +
		                '</div>' +
		            '</div>',
	            buttons: {
	                reset: {
	                    label: "<s:text name='sa.btn.reset' />",
	                    className: "btn-default",
	                    callback: function () {
	                        $('.admin-form')[0].reset();
	                        return false;
	                    }
	                },
	                success: {
	                    label: "<s:text name='sa.btn.save' />",
	                    className: "btn-success",
	                    callback: function () {
	                    	if (user_admin.check_item()) {
		                    	var options = {
			                		    url : 'editUser_save',
			                		    dataType : 'json',
			                		    beforeSubmit : function() {
			                	            var $loadingTextIcon = $(com_conf.loading_text_icon);
			                		    	$('.msg-info').empty().append($loadingTextIcon);
			                		    },
			                		    success : function(data) {
			                  				if (data=='1') {
			                  					user_admin.alert_err("<s:text name='msg.err.param'></s:text>");
			                  				} else if (data=='4') {
			                  					user_admin.alert_err("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.save' /></s:param></s:text>");
			                  				} else {
			                  					user_admin.conf.admin_arr[data.id]=data;
			                  					var $adminList = $('.admin-list');
			                  			   		var $tbody = $adminList.find('tbody');

			                        			var $tr = $(user_admin.conf.tr).addClass('tr_'+data.id);
			                        			var _id = $(user_admin.conf.td).html(data.id);
			                        			var _name = $(user_admin.conf.td).html(data.name);
			                        			var $iEdit = $(user_admin.conf.i_edit);
			                        			$iEdit.attr('idx', data.id).on('click', function() {
			                        				user_admin.modify_admin(data.id);
			                        			});
			                        			var $iRemove = $(user_admin.conf.i_remove);
			                        			$iRemove.attr('idx', data.id).on('click', function() {
			                        				user_admin.remove_admin(data.id);
			                        			});
			                        			var _setting = $(user_admin.conf.td).append($iEdit).append("&nbsp;&nbsp;").append($iRemove);

			                        			$tr.append(_id).append(_name).append(_setting);

			                        			$tbody.append($tr);

				              				   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.save' /></s:param></s:text>");

				              	        		$('.bootbox.modal').modal('hide');
			                  				}
			                		    },
			                        	error: function() {
			                        		user_admin.alert_err("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.save' /></s:param></s:text>");
			                        	}
			                		};
			                  		$('.admin-form').ajaxSubmit(options);
			                  		return false;
	                    	} else {
	                    		return false;
	                    	}
	                    }
	                }
	            }
			});
		});
	},
	modify_admin : function(id) {
		var $admin = user_admin.conf.admin_arr[id];
		
		bootbox.dialog({
            title: '<s:text name="sa.user.lbl.edit.admin"/><span style="font-size:14px;margin-left:20px;" class="msg-info"></span>',
            message: 
                   '<div class="row">' +
	                '<div class="col-lg-12">' +
	                    '<form role="form" class="admin-form" method="post">' +
                        	'<input type="hidden" name="admin.id" value="' + id + '" class="admin-id">' +
	                        '<div class="form-group">' +
                                '<label><s:text name="sa.user.lbl.user.id" /> : ' + id + '</label>' +
                            '</div>' +
	                        '<div class="form-group">' +
	                            '<label><s:text name="sa.user.lbl.user.name" /></label>' +
	                            '<input type="text" name="admin.name" value="' + $admin.name + '" class="form-control admin-name" maxlength="20">' +
	                            '<p class="help-block"><s:text name="sa.user.lbl.userName"/></p>' +
	                        '</div>' +
	                        '<div class="form-group">' +
	                            '<label><s:text name="sa.user.lbl.password" /></label>' +
	                            '<input type="password" name="admin.password" value="' + $admin.password + '" class="form-control admin-pass" maxlength="8">' +
	                            '<p class="help-block"><s:text name="sa.user.lbl.pass" /></p>' +
	                        '</div>' +
	                        '<div class="form-group">' +
	                            '<label><s:text name="sa.user.lbl.confirm.password" /></label>' +
	                            '<input type="password" value="' + $admin.password + '" class="form-control admin-confirm-pass" maxlength="8">' +
	                            '<p class="help-block"><s:text name="sa.user.lbl.confirm.pass" /></p>' +
	                        '</div>' +
	                    '</form>' +
	                '</div>' +
	            '</div>',
            buttons: {
                reset: {
                    label: "<s:text name='sa.btn.reset' />",
                    className: "btn-default",
                    callback: function () {
                        $('.admin-form')[0].reset();
                        return false;
                    }
                },
                success: {
                    label: "<s:text name='sa.btn.save' />",
                    className: "btn-success",
                    callback: function () {
                    	if (user_admin.check_item()) {
	                    	var options = {
		                		    url : 'editUser_update',
		                		    dataType : 'json',
		                		    beforeSubmit : function() {
		                	            var $loadingTextIcon = $(com_conf.loading_text_icon);
		                		    	$('.msg-info').empty().append($loadingTextIcon);
		                		    },
		                		    success : function(data) {
		                  				if (data=='1') {
		                  					user_admin.alert_err("<s:text name='msg.err.param'></s:text>");
		                  				} else if (data=='4') {
		                  					user_admin.alert_err("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.modify' /></s:param></s:text>");
		                  				} else {
		                  					user_admin.conf.admin_arr[data.id]=data;
		                  					var $adminList = $('.admin-list');
		                  			   		var $tbody = $adminList.find('tbody');

		                        			var $tr = $tbody.find('.tr_'+data.id);
		                        			var _id = $(user_admin.conf.td).html(data.id);
		                        			var _name = $(user_admin.conf.td).html(data.name);
		                        			var $iEdit = $(user_admin.conf.i_edit);
		                        			$iEdit.attr('idx', data.id).on('click', function() {
		                        				user_admin.modify_admin(data.id);
		                        			});
		                        			var $iRemove = $(user_admin.conf.i_remove);
		                        			$iRemove.attr('idx', data.id).on('click', function() {
		                        				user_admin.remove_admin(data.id);
		                        			});
		                        			var _setting = $(user_admin.conf.td).append($iEdit).append("&nbsp;&nbsp;").append($iRemove);

		                        			$tr.empty().append(_id).append(_name).append(_setting);

			              				   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.modify' /></s:param></s:text>");

			              	        		$('.bootbox.modal').modal('hide');
		                  				}
		                		    },
		                        	error: function() {
		                        		user_admin.alert_err("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.modify' /></s:param></s:text>");
		                        	}
		                		};
		                  		$('.admin-form').ajaxSubmit(options);
		                  		return false;
                    	} else {
                    		return false;
                    	}
                    }
                }
            }
		});
	},
	remove_admin : function(id) {
		var alertMsg = "<s:text name='msg.alert.remove.item'><s:param><s:text name='msg.param.admin.' /></s:param></s:text>";
		alertMsg = alertMsg.replace(/admin/, id);
		var $adminList = $('.admin-list');

       	bootbox.confirm({
       	    size: 'small',
       	    message: alertMsg, 
       	 	locale: 'zh_CN',
       	    callback: function(result){
       	    	if (result) {
       				$.ajax({
       		            type: "post",
       		            url: "editUser_delete",
       		            data: 'id='+id,
       		            dataType: "json",
       		            success: function(data) {
							if (data=='1') {
              					user_admin.alert_err("<s:text name='msg.err.param'></s:text>");
							} else if (data=='4') {
								user_admin.alert_err("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
							} else {
							   	alert("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
              			   		var $tbody = $adminList.find('tbody');
				           		$tbody.find('.tr_'+id).remove();
				           		var trCnt = $tbody.children().length;
				           		if (trCnt == 0) {
					           		$adminList.find('.create-admin').after("<span style='margin-left:30px;'><s:text name='msg.no.item'><s:param><s:text name='msg.param.admin' /></s:param></s:text></span>");
		       		        		$adminList.find('.panel').addClass('display-off');
				           		}
							}
       		            },
       		            beforeSend: function() {
       		            	$adminList.find('.panel-heading').append($(com_conf.loading_text_icon));
       		        	},
       		        	error: function() {
       		        		alert("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.delete' /></s:param></s:text>");
       		        	},
       		        	complete: function() {
       		        		$adminList.find('.panel-heading span').remove();
       		        	}
       				});
       	    	}
       	    }
       	})
	},
	check_item : function() {
		$('.form-control').focus(function() {
			$(this).parent().removeClass('has-error');
		});
		var id = $('.admin-id').val();
		if (id == '') {
			$('.admin-id').parent().addClass('has-error');
			user_admin.alert_err('<s:text name="msg.err.blank"><s:param><s:text name="msg.param.id"/></s:param></s:text>');
			return false;
		}
		if (!user_admin.test_id(id)) {
			$('.admin-id').parent().addClass('has-error');
			user_admin.alert_err('<s:text name="msg.err.item"><s:param><s:text name="msg.param.id"/></s:param></s:text>');
			return false;
		}
		var name = $('.admin-name').val();
		if (name.length > 0 && !user_admin.test_name(name)) {
			$('.admin-name').parent().addClass('has-error');
			user_admin.alert_err('<s:text name="msg.err.item"><s:param><s:text name="msg.param.name"/></s:param></s:text>');
			return false;
		}
		var pass = $('.admin-pass').val();
		var confirmPass = $('.admin-confirm-pass').val();
		if (pass == '') {
			$('.admin-pass').parent().addClass('has-error');
			user_admin.alert_err('<s:text name="msg.err.blank"><s:param><s:text name="msg.param.pass"/></s:param></s:text>');
			return false;
		}
		if (pass != confirmPass) {console.log('111');
			$('.admin-confirm-pass').parent().addClass('has-error');console.log('222');
			user_admin.alert_err('<s:text name="msg.err.pass.confirm" />');console.log('333');
			return false;
		}
		if (!user_admin.test_pass(pass)) {
			$('.admin-pass').parent().addClass('has-error');
			user_admin.alert_err('<s:text name="msg.err.item"><s:param><s:text name="msg.param.pass"/></s:param></s:text>');
			return false;
		}
		return true;
	},
	alert_err : function(msg) {
		var $tip = $(user_admin.tpl.tip);
		var $msg = $('.msg-info');
		$tip.addClass(user_admin.conf.cls_danger).find('strong').html(msg);
		$msg.empty().append($tip);
		window.setTimeout(function() {
			$tip.fadeOut(300, function() {
				$(this).remove();
			});
		},5000);
	},
	alert_done : function(msg) {
		var $tip = $(user_admin.tpl.tip);
		var $msg = $('.msg-info');
		$tip.addClass(user_admin.conf.cls_done).find('strong').html(msg);
		$msg.empty().append($tip);
		window.setTimeout(function() {
			$tip.fadeOut(300, function() {
				$(this).remove();
			});
			$('.submit-admin .panel').hide();
			$('.submit-admin .create-admin').show();
		},5000);
	},
	test_id : function(id) {
		var reg = /[0-9A-Za-z-_]{4,8}/g;
		return reg.test(id);
	},
	test_name : function(name) {
		var length = name.replace(/[^\x00-\xff]/g,"mm").length;
		if (length > 20) {
			return false;
		}
		var reg = /[0-9A-Za-z-_\u4e00-\u9fa5]+/g;
		return reg.test(name);
	},
	test_pass : function(str) {
		var reg = /[0-9A-Za-z]{4,8}/g;
		return reg.test(str);
	},		
};
$(document).ready(function() {
	user_admin.init();
});
</script>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><s:text name="sa.header.user.admin" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
            	<div class="col-lg-12 admin-list">
            		<button class="create-admin btn btn-default"><s:text name="sa.user.lbl.create.admin"/></button><hr>
                    <div class="panel panel-default display-off">
                        <div class="panel-heading">
                            <s:text name="sa.user.lbl.admin.list" />
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover user-table">
                                    <thead>
                                        <tr>
                                            <th>用户 Id</th>
                                            <th>用户名</th>
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
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->
