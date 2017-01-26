<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a>月份选择:</a>
        </li> 
      </ul>
      <div class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <input type="text" class="form-control statMonth" placeholder="开始月份">
        </div>
        <div class="form-group">
          <input type="text" class="form-control endMonth" placeholder="结束月份">
        </div>
        <button class="btn btn-default monthSubmitBtn">提交</button>
      </div>
      
      
      
      
      <ul class="nav navbar-nav navbar-right">
      	<li><a>分类选择：</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle categoryNameA" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" idx ="1">办公用品<span class="caret"></span></a>
          <ul class="dropdown-menu categoryUl">
          	<li><a href="javascript:void(0)">办公用品</a></li>
            <li><a href="javascript:void(0)">办公设备</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div id="container" style="height: 500px">
	<div class="jumbotron">
	  <p align="center">暂无数据！</p>
	</div>
</div>

<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
<script type="text/javascript">



var loadCharts = function (){
	if($('.statMonth').val() == $('.endMonth').val()){
		var titleText = "分类统计";
	}else{
		var titleText = "月份统计";
	}
	if($('.categoryNameA').attr('idx') == "1"){
		titleSubtext = "办公用品";
	}else if($('.categoryNameA').attr('idx') == "2"){
		titleSubtext = "办公设备";
		
	}else{
		titleSubtext = "";
		
	}
	$.ajax({
		type : "post",
		dataType : "json",
		url : 'findData4StatChart',
		data : {
			beginMonth : $('.statMonth').val(),
			endMonth : $('.endMonth').val(),
			category : $('.categoryNameA').attr('idx')
		},
		success : function(data) {
			if(data == null || data == ""){
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
				return;
			}
			
			if(data == 601 || data == "601" ){
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
				alert("日期格式错误！");
				return;
			}
			if(data == 602 || data == "602" ){
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
				alert("开始时间不能大于结束时间！");
				return;
			}
			if(data == 603 || data == "603" ){
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
				alert("查询时间不能超过了一年！");
				return;
			}
			if(data == 108 || data == "108" ){
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
				alert("日期为空！");
				return;
			}
			var chartdata = [];
			chartdata.legend = [];
			
			$.each(data , function (key,val){
				chartdata.legend[key]= val.name;
			});
			chartdata.series = data;
			
			var dom = document.getElementById("container");
			var myChart = echarts.init(dom);
			var app = {};
			option = null;
			option = {
			    title : {
			        text: titleText,
			        subtext: titleSubtext,
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{b} : {c} ({d}%)"
			    },
			    legend: {
			        x : 'center',
			        y : 'bottom',
			        data: chartdata.legend
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {
			                show: true,
			                type: ['pie', 'funnel']
			            },
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:'',
			            type:'pie',
			            radius : [30, 160],
			            center : ['50%', '50%'],
			            roseType : 'area',
			            data:chartdata.series
			        }
			    ]
			};
			if (option && typeof option === "object") {
			    myChart.setOption(option, true);
			}
			
		},
		beforeSend : function() {
			$('#container').empty();
			$('#container').html(
					'<div class="spinner">'+
					  '<div class="rect1"></div>'+
					  '<div class="rect2"></div>'+
					  '<div class="rect3"></div>'+
					  '<div class="rect4"></div>'+
					  '<div class="rect5"></div>'+
					'</div>'	
			);
		},
		complete : function (){
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			onAjaxErrorAction(XMLHttpRequest, textStatus, errorThrown);
		}
	});
	
}

$(document).ready(function(){
	$('head > title').html('依勤 - 统计图');
	
	$('.monthSubmitBtn').off().on({
		'click':function(){
			loadCharts();
		}
	});
	$('.categoryUl li').off().on({
		'click' : function(){
			var index = $(".categoryUl li").index(this);
		    $('.categoryNameA').html($(this).find('a').html()+'<span class="caret"></span>').attr('idx',index+1);
		}
	});
	
	
	
	
});
</script>
