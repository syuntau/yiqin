<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="/struts-tags" prefix="s"%>

<section id="do_action">	
	<div class="container">
		<div class="heading">
			<h3><b><s:text name="cart.check.title"></s:text></b></h3>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.receiver.information"></s:text></b></h5>
				<div class="chose_area">
					<ul class="user_info">
						<li>
							李三三 13812341234
						</li>
					</ul>
					<ul class="user_info">
						<li>
							北京 朝阳区 建国路91号金地中心B座**层****室 ****有限公司
						</li>
					</ul>
					<a class="btn btn-default update" href="">修改</a>
				</div>
			</div>
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.zhifu"></s:text></b></h5>
				<div class="chose_area">
					<ul class="user_info">
						<li>
							货到付款
						</li>
					</ul>
					<ul class="user_info">
						<li>
							依勤送货
						</li>
					</ul>
					<a class="btn btn-default update" href="">修改</a>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.fapiao"></s:text></b></h5>
				<div class="chose_area">
					<ul class="user_info">
						<li>
							普通发票 北京****有限公司  明细
						</li>
					</ul>
					<a class="btn btn-default update" href="">修改</a>
				</div>
			</div>
			<div class="col-sm-6">
				<h5><b><s:text name="cart.check.product.list"></s:text></b></h5>
				<div class="total_area">
					<ul>
						<li>高级圆珠笔 <span>2 × 5 = 10</span></li>
						<li>高级圆珠笔 <span>1 × 200 = 200</span></li>
						<li>总价 <span>210</span></li>
					</ul>
						<a class="btn btn-default check_out" href="">提交订单</a>
				</div>
			</div>
		</div>
		<!-- 
		<div class="row">
			<div class="col-sm-6">
				<div class="chose_area">
					<ul class="user_option">
						<li>
							<input type="checkbox">
							<label>Use Coupon Code</label>
						</li>
						<li>
							<input type="checkbox">
							<label>Use Gift Voucher</label>
						</li>
						<li>
							<input type="checkbox">
							<label>Estimate Shipping & Taxes</label>
						</li>
					</ul>
					<ul class="user_info">
						<li class="single_field">
							<label>Country:</label>
							<select>
								<option>United States</option>
								<option>Bangladesh</option>
								<option>UK</option>
								<option>India</option>
								<option>Pakistan</option>
								<option>Ucrane</option>
								<option>Canada</option>
								<option>Dubai</option>
							</select>
							
						</li>
						<li class="single_field">
							<label>Region / State:</label>
							<select>
								<option>Select</option>
								<option>Dhaka</option>
								<option>London</option>
								<option>Dillih</option>
								<option>Lahore</option>
								<option>Alaska</option>
								<option>Canada</option>
								<option>Dubai</option>
							</select>
						
						</li>
						<li class="single_field zip-field">
							<label>Zip Code:</label>
							<input type="text">
						</li>
					</ul>
					<a class="btn btn-default update" href="">Get Quotes</a>
					<a class="btn btn-default check_out" href="">Continue</a>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="total_area">
					<ul>
						<li>Cart Sub Total <span>$59</span></li>
						<li>Eco Tax <span>$2</span></li>
						<li>Shipping Cost <span>Free</span></li>
						<li>Total <span>$61</span></li>
					</ul>
						<a class="btn btn-default update" href="">Update</a>
						<a class="btn btn-default check_out" href="">Check Out</a>
				</div>
			</div>
		</div> 
		-->
	</div>
</section>