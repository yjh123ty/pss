<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highcharts Example</title>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript">
$(function () {
	//先发出json的请求，获取json数据，放入变量myData
	//然后在交个Highcharts处理饼图效果
	//request.getQueryString()   获取get请求后面所有值
	$.get("purchaseBillItem_json.action?${pageContext.request.queryString}",function(myData){
		$('#container').highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }
	        },
	        title: {
	            text: '3D饼图 '
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.name}'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '占有率',
	            data: myData
	        }]
	    });
		
	});
	
    
});
		</script>
	</head>
	<body>


<div id="container" style="height: 400px"></div>
	</body>
</html>