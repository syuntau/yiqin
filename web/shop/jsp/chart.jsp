<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">月份选择  <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li> 
      </ul>
      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
      
      
      
      
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">Link</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div id="container" style="height: 500px">
	<div class="spinner">
	  <div class="rect1"></div>
	  <div class="rect2"></div>
	  <div class="rect3"></div>
	  <div class="rect4"></div>
	  <div class="rect5"></div>
	</div>
</div>

<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
<script type="text/javascript">

 



var loadCharts = function (){
	var titleText = "测试";
	var titleSubtext = "测试数据";
	$.ajax({
		type : "post",
		dataType : "json",
		url : 'findData4StatChart',
		data : {
			beginMonth : '201601',
			endMonth : '201611',
		},
		success : function(data) {
			
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
	
	loadCharts();
});
</script>
