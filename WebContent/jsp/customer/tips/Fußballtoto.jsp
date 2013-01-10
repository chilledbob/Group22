<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
<% int k = 0; int h = 7; %>
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
	<c:url value="GroupToto" var="url">
	<c:param name="uid" value="${currentUser.identifier}" />
	<c:param name="groupName" value="${currentGroup.getName() }"></c:param>
		</c:url>
		<section><a href="${url}">neuer Tipp</a></section>
	
	</c:when>
	<c:when test="${confirm1}">
	<h3>Wählen Sie einen Spieltag der aktuellen Saison 2012/2013</h3>
	<table border="1" cellspacing="1" cellpadding="1">
	<tr> 
	<c:forEach items="${totoList }" var="eva">
	<% k++; %>
	<c:if test="${!eva.isEvaluated() }">
		<td>
			<c:url value="GroupMatches" var="url">
			<c:param name="TotoEvaID" value="${eva.getId() }" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.getName() }" />
			</c:url>
			<a href ="${url}">Spieltag <%= k %></a>
		<% if((k % h) == 0){%>
		<%="<tr></tr>" %>
		<%} %>
		</td>
	</c:if>

	</c:forEach>
		</tr>
		</table>	
	</c:when>
	<c:otherwise>
	
	<h3>Erstellen Sie einen Gruppentip für den ${spieltag }. Spieltag</h3>
		<c:url var="url" value="TotoConfirmGroup">
			<c:param name="GroupOrderID" value="${eva.getId() }" />
			<c:param name="uid" value="${currentUser.identifier }" />
			<c:param name="groupName" value="${currentGroup.getName() }" />
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
			<button class="btn" >Gruppentip abschicken</button>
		</form>
	</c:otherwise>  
	
</c:choose>