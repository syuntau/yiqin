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
      
      
      
      
      <%-- <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">分类选择 <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
          </ul>
        </li>
      </ul> --%>
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
	var titleSubtext = "";
	$.ajax({
		type : "post",
		dataType : "json",
		url : 'findData4StatChart',
		data : {
			beginMonth : $('.statMonth').val(),
			endMonth : $('.endMonth').val(),
		},
		success : function(data) {
			if(data == null || data == ""){
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
				return;
			}
			
			if(data == 601 || data == "601" ){
				alert("日期格式错误！");
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
				return;
			}
			if(data == 602 || data == "602" ){
				alert("开始时间不能大于结束时间！");
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
				return;
			}
			if(data == 603 || data == "603" ){
				alert("查询时间不能超过了一年！");
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
				return;
			}
			if(data == 108 || data == "108" ){
				alert("日期为空！");
				$('#container').html('<div class="jumbotron"><p align="center">暂无数据！</p></div>');
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
});
</script>
