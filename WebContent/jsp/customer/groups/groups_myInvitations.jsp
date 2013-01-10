<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Gewinngemeinschaften</title>
<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/css.css" />" />
</head>
<body>

<div id="top">
	<div id="top_content">
			<jsp:include page="../../head/head.jsp" />
		
		<div class="top_navi">
				<c:url value="customerTipManagement" var="url">
					<c:param name="uid" value="${currentUser.identifier}" />
				</c:url>
				<section><a href ="${url}">Tippverwaltung</a></section>

				<section>Gewinngemeinschaften</section>							
		</div>	
		
		
		<div class="sub_navi">
			<section><a href ="newGroup">Erstellen</a></section>
			<section><a href ="">Meine Einladungen</a> (${invCount})</section>
			<section><a href ="showApplications">Meine Bewerbungen (${applCount})</a></section>
			<section><a href="myGroups">Gruppen</a></section>
		</div>	
	</div>
</div>

<div id="middle">
	<div class="main_content_full">
		<div class="current_content" >
			<br>
			<h4 align="center">Einladungen<sup style="text-shadow: aqua;font-size: x-small;">${currentUser.identifier}</sup></h4>
			
			<div align="center"><a href ="returnToCustomerGroups">Abbrechen</a></div>
			
			<table>
				<tr>
					<th>Gruppe</th>
					<th>Status</th>
					<th>ID</th>
					<th>Aktion</th>
				</tr>
				<c:forEach items="${myInvList}" var="ilItem" varStatus="status">
					<c:if test="${ilItem.getState().toString()=='Unhandled'}">
						<tr>
							<td>${ilItem.getGroup().getName()}</td>
							<td>${ilItem.getState().toString()}</td>
							<td>${status.index}</td>
							<td>
								<c:url value="acceptInvitation" var="url1">
									<c:param name="uid" value="${currentUser.identifier}" />
									<c:param name="invId" value="${status.index}" />
								</c:url>
								<c:url value="refuseInvitation" var="url2">
									<c:param name="uid" value="${currentUser.identifier}" />
									<c:param name="invId" value="${status.index}" />
								</c:url>
								<section><a href ="${url1}">OK</a> <a href ="${url2}">X</a></section>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
			
			
		</div>	

		</div>		
	</div>
	


<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>


</body>
</html>