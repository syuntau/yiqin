<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="#">Link</a></li>
      </ul>
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

var loadCharts = function (titleText,titleSubtext,data){
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
	        data: data.legend
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
	            data:data.series
	        }
	    ]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	}
}

$(document).ready(function(){
	$('head > title').html('依勤 - 统计图');
	var data = [];
	data.legend = ['办公耗材','rose2','rose3','rose4','rose5','rose6','rose7','rose8'];
	data.series = [
	                {value:50, name:'办公耗材'},
	                {value:5, name:'rose2'},
	                {value:15, name:'rose3'},
	                {value:25, name:'rose4'},
	                {value:20, name:'rose5'}, 
	                {value:35, name:'rose6'},
	                {value:30, name:'rose7'},
	                {value:40, name:'rose8'}
	            ];
	setTimeout(loadCharts('测试','数据测试',data),30000);//延时3秒 
	
// 	$.ajax({
// 		type : "post",
// 		dataType : "json",
// 		url : 'sns5Kol4FBDetailDoc.do',
// 		data : {
// 			docId : docid,
// 		},
// 		success : function(data) {
// 		},
// 		beforeSend : function() {
// 		},
// 		complete : function (){
// 		},
// 		error : function(XMLHttpRequest, textStatus, errorThrown) {
// 			onAjaxErrorAction(XMLHttpRequest, textStatus, errorThrown);
// 		}
// 	});
	
	
	
});
</script>
