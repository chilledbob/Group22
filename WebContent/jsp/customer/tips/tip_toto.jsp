<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
<% int k = 1; int h = 7;%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			<c:param name="uid" value="${currentUser.identifier}" />	
			</c:url>
			<section><a href ="${url}">meine Tipps</a></section>
			
			<c:url value="customerNumeral" var="url">
			<c:param name="uid" value="${currentUser.identifier}" />	
			</c:url>
			<section><a href ="${url}">Ziffernlotto</a></section>
			
			<c:url value="customerLotto" var="url">
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">6aus49</a></section>

			<section>FussballToto</section>
		</div>	
	</div>
</div>

	
<div id="middle">
	<div class="main_content">

		<div class="current_content">
		
	<c:choose>
	<c:when test="${confirm}">
	
	<h1>Ihre Zusammenfassung</h1>
	
	 
	<table border="1">
	<th> SpielDatum</th>
	<th>Manschaft A</th>
	<th>Manschaft B</th>
	<th> Ihr Tip</th>
	<c:forEach var="e" items="${spielliste}" varStatus="status">
		<tr>
			<td>
				${e.getMatchDay()}
			</td>
			<td>
				${e.getHomeClubName()}
			</td>
			<td>
				${e.getVisitorClubName()}
			</td>
			<td>
				${tips.get(status.index)}
			</td>
		</tr>
	</c:forEach>
	
	</table>
	<c:url value="customerToto" var="url">
	<c:param name="uid" value="${currentUser.identifier}" />
		</c:url>
		<section><a href="${url}">neuer Tipp</a></section>
	
	</c:when>
	<c:when test="${confirm1}">
	<h3>Wählen Sie einen Spieltag der aktuellen Saison 2012/2013</h3>
	<table border="1" cellspacing="1" cellpadding="1">
	<tr> 
	<c:forEach items="${totoList }" var="eva">
		<td>
			<c:url value="Matches" var="url">
			<c:param name="TotoEvaID" value="${eva.getId() }" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">Spieltag <%= k %></a>	
		</td>
		<% k++;
		if((k % h) == 0){%>
		<%="<tr></tr>" %>
		<%} %>
	</c:forEach>
		</tr>
		</table>	
	</c:when>
	<c:otherwise>
	
	<h3>Erstellen Sie einen Tip für den ${spieltag }. Spieltag</h3>
		<c:url var="url" value="TotoConfirm">
			<c:param name="GroupOrderID" value="${eva.getId() }" />
			<c:param name="uid" value="${currentUser.identifier }" />
		</c:url>
<form action="${url }" method="post">
  <table border="1">
		<tr>
			<th> SpielID</th>
			<th>Manschaft A</th>
			<th>Manschaft B</th>
			<th> Ihr getipptes Ergebnis</th>
			
			
		</tr>
		<c:forEach var="e" items="${spielliste}" varStatus="status">
	
		
		<tr>
			<td>
				${e.getMatchDay()}
			</td>
			
			<td>
				${e.getHomeClubName()}
			</td>
			<td>
				${e.getVisitorClubName()}
			</td>
			<td>
			
				<label for="${status.index}"></label>
            	<input id="${status.index}" name="${status.index}" type="text" />
			
			
			</td>
			
			
			</tr>
		
 			</c:forEach>
	</table>
	0 (Heimmannschaft gewinnt), 1 (Gastmannschaft gewinnt) oder 2 (Unentschieden)
			<button class="btn" >Einzeltip abschicken</button>
		</form>
	</c:otherwise>  
	
</c:choose>
			
				</div>
				
	
	</div>
	
	<div class="side">

		<div id="side_calendar">
			<p>Anzeige der nächsten 5 Tipps</p>
		</div>
		
		<div id="side_tipps">
			<p>Kontostand anzeigen</p>
		</div>
	</div>

</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>


</body>
</html>