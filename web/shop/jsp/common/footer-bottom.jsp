<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>  

	<div class="footer-bottom">
		<div class="container">
			<div class="row">
				<p class="pull-left">
					<s:text name="shop.footer.bottom.copyright" />&nbsp;&nbsp;&nbsp;&nbsp;
					<a href='https://beian.miit.gov.cn' target='_blank'><s:text name="yiqin.record" /></a>
				</p>
				<p class="pull-right footer-bottom-right-side">
				</p>
			</div>
		</div>
	</div>

	<div class="yq-side">
		<ul>
		<s:if test="#session.userInfo==null">
		  <li id=""><a class="go-to-page" idx="top_login"><div class="sidebox yq-side-line-50" style="border-top: solid 4px #FE980F"><i class="fa fa-sign-in fa-2x"></i><span><s:text name="shop.right.shortcut.login" /></span></div></a></li>
		</s:if>
		<s:else>
		  <li><a class="go-to-page" idx="top_account"><div class="sidebox yq-side-line-22" style="border-top: solid 4px #FE980F"><i class="fa fa-user fa-2x"></i><span><s:text name="shop.right.shortcut.account" /></span></div></a></li>
		</s:else>
		<s:if test="#session.se_shop_nav_best_product=='best_product_nav'">
		  <li><a class="go-to-page" idx="top_home"><div class="sidebox yq-side-line-22"><i class="fa fa-qrcode fa-2x"></i><span><s:text name="shop.right.shortcut.all.product" /></span></div></a></li>
		</s:if>
		<s:else>
		  <li><a class="go-to-page" idx="top_quick_shopping"><div class="sidebox yq-side-line-22"><i class="fa fa-heart fa-2x"></i><span><s:text name="shop.right.shortcut.quick.shopping" /></span></div></a></li>
		</s:else>
		  <li><a class="go-to-page" idx="top_cart"><div class="sidebox yq-side-line-50"><i class="fa fa-shopping-cart fa-2x"></i><span><s:text name="shop.right.shortcut.jiesuan" /></span></div></a></li>
		  <li style="border:none;"><a href="javascript:goTop();"><div class="sidebox yq-side-line-50"><i class="fa fa-angle-up fa-2x"></i><span><s:text name="shop.right.shortcut.to.top" /></span></div></a></li>
		</ul>
	</div>
	
<script type="text/javascript">
function goTop(){
	$('html,body').animate({'scrollTop':0},300);
}
</script>