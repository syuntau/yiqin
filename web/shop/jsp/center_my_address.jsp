<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
<div class="center-right padding-right">
	<section id="address_list">
		<div style="margin-bottom:10px">
			<a class="btn btn-default check_out"  style="vertical-align: middle;display:inline-block;" href="javaScript:;">新增收货地址</a>
			<span style="vertical-align: middle;">您已创建<s:property value="#request.address_list.size()"/>个收货地址，最多可创建10个</span>
		</div>
		<div class="container">
			<s:if test="#request.address_list==null||#request.address_list.size()==0">
				没有任何收货地址信息，请添加！
			</s:if>
			<s:else>
				<s:iterator value="#request.address_list" var="addlist">
					<div class="row">
						<div class="col-sm-6" style="width:90%">
							<div class="chose_area">
								<ul class="user_option">
									<li style="margin-bottom:10px;">
										<label>收货人员：</label>
										<span><s:property value="#addlist.value"/></span>
									</li>
									<li style="margin-bottom:10px;">
										<label>联系电话：</label>
										<span><s:property value="#addlist.value"/></span>
									</li>
									<li style="margin-bottom:10px;">
										<label>收货地址：</label>
										<span><s:property value="#addlist.value"/></span>
									</li>
								</ul>
								<div style="margin-left:800px;">
									<a  href="javaScript:;">设置为默认</a>
									<a  href="javaScript:;">编辑</a>
								</div>
							</div>
						</div>
					</div>
				</s:iterator>
			</s:else>
		</div>
	</section>
</div>