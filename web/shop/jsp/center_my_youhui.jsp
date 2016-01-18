<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
var loadYouHui = function() {
	var userId = '<s:property value="#session.userInfo.id"/>';
	$.ajax({
		type : "post",
		url : "findYouHui",
		data : 'userId=' + userId,
		dataType : "json",
		success : function(data) {
			if (data == '1') {
				alert("<s:text name='msg.err.param'></s:text>");
			} else if (data == '2') {
				$('#youhuizhengce_span').html('暂无优惠');
			} else {
				$('#youhuizhengce_span').html(data.value);
			}
		},
		beforeSend : function() {
			$('#youhuizhengce_span').html("加载中...");
		},
		complete : function() {
			$("#my_set_youhui").css('color', '#fdb45e');
		}
	});
};

$(document).ready(function() {
	loadYouHui();
});
</script>
<div class="center-right padding-right">
	<div class="row" id="my_info_list_normal">
		<div class="col-sm-12">
			<h5><b>您的优惠政策</b></h5>
			<div class="chose_area">
				<ul class="user_option">
					<li style="margin-bottom:10px;">
						<span id="youhuizhengce_span"></span>
					</li>
				</ul>
			</div>
		</div>
	</div> 
</div>
