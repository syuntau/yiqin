<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>  
<script type="text/javascript">
var user_sa = {
	conf : {
		cls_warning : 'alert-warning',
		cls_done : 'alert-success',
		cls_danger : 'alert-danger',
	},
	tpl : {
		tip : '<div class="alert" role="alert" style="padding-top:6px;padding-bottom:6px;padding-left:15px;padding-right:15px;display:inline;"><strong></strong></div>'
	},
	init : function() {
		$('.modify-pass').click(function() {
			user_sa.modify_pass();
		});
	},
	test_pass : function(str) {
		var reg = /[0-9A-Za-z]{4,8}/g;
		return reg.test(str);
	},
	modify_pass : function() {
		var id = '<s:property value="#session.yiqin_sa_user.id"/>';
		var oldPass = $('.old-pass').val();
		var newPass = $('.new-pass').val();
		var confirmNewPass = $('.confirm-new-pass').val();
		var $tip = $(user_sa.tpl.tip);
		var $msg = $('.msg-info');
		if (oldPass == "" || newPass == "" || confirmNewPass == "") {
			$tip.addClass(user_sa.conf.cls_warning).find('strong').html("<s:text name='msg.no.pass'/>");
			$msg.append($tip);
			$tip.fadeOut(3000, function() {
				$(this).remove();
			});
			return ;
		} else {console.log("oldPass : "+oldPass+", newPass:"+newPass+", confirmPass : "+confirmNewPass);
			if (newPass != confirmNewPass) {
				$tip.addClass(user_sa.conf.cls_warning).find('strong').html("<s:text name='msg.err.confirm.pass'/>");
				$msg.append($tip);
				$tip.fadeOut(3000, function() {
					$(this).remove();
				});
				return ;
			} else {
				if (!user_sa.test_pass(newPass)) {
					$tip.addClass(user_sa.conf.cls_warning).find('strong').html("<s:text name='msg.err.format.pass'/>");
					$msg.append($tip);
					$tip.fadeOut(3000, function() {
						$(this).remove();
					});
					return ;
				}
			}
		}
		$.ajax({
            type: "post",
            url: "editUser_modifyPass",
            data: "id=" + id +  "&oldPass=" + oldPass + "&newPass=" + newPass,
            dataType: "json",
            success: function(data) {
	           	 if (data=='1') {
	     			$tip.addClass(user_sa.conf.cls_warning).find('strong').html("<s:text name='msg.err.param'/>");
	           	 } else if (data=='5') {
		     			$tip.addClass(user_sa.conf.cls_warning).find('strong').html("<s:text name='msg.err.old.pass'/>");
	           	 } else if (data=='100') {
		     			$tip.addClass(user_sa.conf.cls_warning).find('strong').html("<s:text name='msg.suc.do'><s:param><s:text name='msg.param.modify' /></s:param></s:text>");
		    			$('.pass-item').val('');
	           	 } else {
		     			$tip.addClass(user_sa.conf.cls_warning).find('strong').html("<s:text name='msg.fail.do'><s:param><s:text name='msg.param.modify' /></s:param></s:text>");
	           	 }
            },
            beforeSend: function() {
            	$msg.prepend($(com_conf.loading_text_icon));
        	},
            complete: function() {
            	$msg.find('.fa-refresh').parent().remove();
	    		$msg.append($tip);
    			$tip.fadeOut(3000, function() {
    				$(this).remove();
    			});
            }
		});
	}		
};
$(document).ready(function() {
	user_sa.init();
});
</script>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><s:text name="sa.header.user.sa" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading"><s:text name="sa.user.lbl.sa" /><span class="msg-info" style="margin-left:50px;"></span></div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form role="form">
                                        <div class="form-group">
                                            <label><s:text name="sa.user.lbl.old.password" /></label>
                                            <input type="password" class="form-control old-pass pass-item">
                                            <p class="help-block"></p>
                                        </div>
                                        <div class="form-group">
                                            <label><s:text name="sa.user.lbl.new.password" /></label>
                                            <input type="password" class="form-control new-pass pass-item" maxlength="8">
                                            <p class="help-block"><s:text name="sa.user.lbl.pass" /></p>
                                        </div>
                                        <div class="form-group">
                                            <label><s:text name="sa.user.lbl.confirm.new.password" /></label>
                                            <input type="password" class="form-control confirm-new-pass pass-item" maxlength="8">
                                            <p class="help-block"><s:text name="sa.user.lbl.confirm.pass" /></p>
                                        </div>
                                        <button type="button" class="btn btn-default modify-pass"><s:text name="sa.btn.modify" /></button>
                                        <button type="reset" class="btn btn-default"><s:text name="sa.btn.reset" /></button>
                                    </form>
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                            </div>
                            <!-- /.row (nested) -->
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
