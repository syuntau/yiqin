<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>  
		
	<div class="footer-widget">
		<div class="container">
			<div class="row">
				<div class="col-sm-8">
					<div class="pull-left col-sm-8">
						<div class="single-widget">
							<h2><s:text name="shop.footer.widget.about.yiqin"></s:text></h2>
							<ul class="nav nav-pills nav-stacked">
<%-- 								<li><a href="javaScript:footerTo('footer_about');"><s:text name="shop.footer.widget.yiqin.present"></s:text></a></li> --%>
								<li><a href="javaScript:footerTo('footer_about');"><s:text name="shop.footer.widget.about.yiqin.info"/></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="single-widget">
						<h2><s:text name="shop.footer.widget.service" /></h2>
						<ul class="nav nav-pills nav-stacked">
							<li><a href=""><s:text name="shop.footer.widget.service.online.help" /></a></li>
							<li><a href="javaScript:footerTo('footer_us');"><s:text name="shop.footer.widget.service.contact.us" /></a></li>
							<li><a href=""><s:text name="shop.footer.widget.service.order.status" /></a></li>
							<li><a href=""><s:text name="shop.footer.widget.service.faq" /></a></li>
						</ul>
					</div>
				</div>
				<div class="col-sm-2">
					<div class="single-widget">
						<h2><s:text name="shop.footer.widget.policies" /></h2>
						<ul class="nav nav-pills nav-stacked">
							<li><a href=""><s:text name="shop.footer.widget.policies.terms.of.use" /></a></li>
							<li><a href="javaScript:footerTo('footer_return');"><s:text name="shop.footer.widget.policies.refund.policy" /></a></li>
<%-- 							<li><a href=""><s:text name="shop.footer.widget.policies.billing.system"></s:text></a></li> --%>
						</ul>
					</div>
				</div>
<!-- 				<div class="col-sm-3"> -->
<!-- 					<div class="single-widget"> -->
<%-- 						<h2><s:text name="shop.footer.widget.subscription" /></h2> --%>
<!-- 						<form action="#" class="searchform"> -->
<!-- 							<input type="text" placeholder="Your email address" /> -->
<!-- 							<button type="submit" class="btn btn-default"><i class="fa fa-arrow-circle-o-right"></i></button> -->
<%-- 							<p><s:text name="shop.footer.widget.subscription.info" /></p> --%>
<!-- 						</form> -->
<!-- 					</div> -->
<!-- 				</div> -->
				
			</div>
		</div>
	</div>
