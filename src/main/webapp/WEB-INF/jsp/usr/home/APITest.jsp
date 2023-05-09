<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="API Test"/>
<%@ include file="../common/head.jspf"%>
<script type="text/javascript">
	const API_key = '6PGhloeK4NmdQthSu9G78GfSPwzSOGaIATVBIerxxaTrJwUyF6aTh%2BRANuaKiM0OYtu4OAlM2aETDUdIdZhcLA%3D%3D';
	
	function getData(){
		const url = 'https://api.odcloud.kr/api/15111156/v1/uddi:21e6ef69-a16d-465e-8b42-d69f9adaeb41?page=1&perPage=10&serviceKey=' + API_key;
		
		$.get(url, {
			isAjax: 'Y',
		}, function(data) {
			$(".one").html(data.data[0].공연);
		}, 'json');
	}
	
	getData();
</script>

<span class="one" style="font-size: 200px;">하이</span>

<%@ include file="../common/foot.jspf"%>