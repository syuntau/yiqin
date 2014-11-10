<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="/struts-tags" prefix="s"%>

	<div class="panel-group category-products" id="accordian"><!--category-productsr-->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordian" href="#consumables">
						<span class="badge pull-right"><i class="fa fa-plus"></i></span>
						<s:text name="shop.index.left.product.consumables" />
					</a>
				</h4>
			</div>
			<div id="consumables" class="panel-collapse collapse">
				<div class="panel-body">
					<ul>
						<li><a href="#"><s:text name="shop.index.left.product.consumables.printer" /> </a></li>
						<li><a href="#"><s:text name="shop.index.left.product.consumables.copier" /> </a></li>
						<li><a href="#"><s:text name="shop.index.left.product.consumables.fax" /> </a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordian" href="#paper">
						<span class="badge pull-right"><i class="fa fa-plus"></i></span>
						<s:text name="shop.index.left.product.paper" />
					</a>
				</h4>
			</div>
			<div id="paper" class="panel-collapse collapse">
				<div class="panel-body">
					<ul>
						<li><a href="#"><s:text name="shop.index.left.product.paper.copy" /></a></li>
						<li><a href="#"><s:text name="shop.index.left.product.paper.print" /></a></li>
						<li><a href="#"><s:text name="shop.index.left.product.paper.art" /></a></li>
						<li><a href="#"><s:text name="shop.index.left.product.paper.other" /></a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title"><a href="#"><s:text name="shop.index.left.product.machines" /></a></h4>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title"><a href="#"><s:text name="shop.index.left.product.stationery" /></a></h4>
			</div>
		</div>
	</div><!--/category-products-->
