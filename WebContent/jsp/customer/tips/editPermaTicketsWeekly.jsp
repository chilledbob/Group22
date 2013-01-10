<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
<% int j = 1; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tippverwaltung</title>
<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/css.css" />" />
</head>
<body>

<div id="top">
	<div id="top_content">
			<jsp:include page="../../head/head.jsp" />
		
		<div class="top_navi">
				<section>Tippverwaltung</section>
				
				<c:url value="customerGroups" var="url">
					<c:param name="uid" value="${currentUser.identifier}" />
				</c:url>
				<section><a href ="${url}">Gewinngemeinschaften</a></section>							
		</div>	
		
		<div class="sub_navi">
			<c:url value="customerTips" var="url">
				<c:param name="uid" value="${currentUser.identifier }"/>
			</c:url>
			<section><a href="${url }">meine Tipps</a></section>
			
			<c:url value="customerNumeral" var="url">
			<c:param name="uid" value="${currentUser.identifier}" />	
			</c:url>
			<section><a href ="${url}">Ziffernlotto</a></section>
			
			<c:url value="customerLotto" var="url">
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">6aus49</a></section>
			<c:url value="customerToto" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href="${url }">FussballToto</a></section>
		</div>	
	</div>
</div>

	
<div id="middle">
	<div class="main_content_full">

				<div class="current_content">
			
			<h2>Einzeltipschein ändern</h2>
	<p style="color:red;">${failComment }</p>
	<c:url value="customerChangeTipWLPTip" var="url">
		<c:param name="uid" value="${currentUser.identifier }"/>
		<c:param name="PTTid" value="${permaTT.getId() }" />
	</c:url>
	<form action="${url }" method="post">
	<h4>Tip ändern</h4>
	<table>
	<tr>
	<% for(int k = 1; k < 7;k++){ %>
		<td>Zahl <%= k %></td>
	<%} %>
		<td>Zusatzzahl</td>
	</tr>
	<tr>
	<c:forEach var="i" items="${permaTT.getTip()}">
		<td>
			<input name="Zahl<%= j %>" type="text" value="${i }" size="2">
			<% j++;%>
		</td>
	</c:forEach>
	</tr>
	</table>
	<button class="btn">Änderungen abschicken</button>
	</form>
	
	<h4>Einsatz erhöhen</h4>
	<c:url value="customerNewPTT" var="url">
		<c:param name="uid" value="${currentUser.identifier }"/>
		<c:param name="PTTid" value="${permaTT.getId() }" />
	</c:url>
	<form action="${url }" method="post">
		Um Ihren Einsatz zu erhöhen erwerben Sie weitere Tipscheine für diesen Tip.<br>
		Jeder weitere Tipschein kostet ${permaTT.getPerTicketPaidPurchasePrice() } Euro.<br>
		<input type="text" id="anzahl" name="anzahl" size="2" onchange="javascript:alert('Sie wollen '+document.getElementById('anzahl').value+' Tipscheine kaufen.')"> 
		Bitte Anzahl der Tipscheine eintragen.<br><br>
		<button class="btn">abschicken</button>
	</form>
	
		</div>
	</div>
	

</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>

</body>
</html>