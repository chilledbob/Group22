<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>

hallo ich bin die Nummernlotto :)

<c:choose>
	<c:when test="${confirm}">	
				
	<h1>Sie haben erfolgreich folgende Zahlen gewählt:</h1>
	<table border="1" cellspacing="1" cellpadding="1">
	<tr> 
	<th>Ihre Zahlen sind :</th>
		<c:forEach var="e" items="${zahlenliste}" varStatus="status">
		<td>${e}</td>
		</c:forEach>
		</tr>
		</table>
		
	<c:url value="customerNumeral" var="url">
		<c:param name="uid" value="${currentUser.identifier}" />
	</c:url>
		<section><a href="${url}">neuer Tipp</a></section>
	
	</c:when>
	
	<c:otherwise>  
		<h1>Bitte 10 Ziffern eintragen, von 1 bis 9</h1>


        <div class="inputForm">
            
            <table border="1" cellspacing="1" cellpadding="1">
	<tr> 
		<td>
            <c:url value="Zahl" var="url">
				<c:param name="Zahl" value="1" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">1</a>	
           </td>
            <td>
            <c:url value="Zahl" var="url">
				<c:param name="Zahl" value="2" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">2</a>	
           </td><td>
            <c:url value="Zahl" var="url">
				<c:param name="Zahl" value="3" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">3</a>	
           </td><td>
            <c:url value="Zahl" var="url">
				<c:param name="Zahl" value="4" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">4</a>	
           </td><td>
            <c:url value="Zahl" var="url">
				<c:param name="Zahl" value="5" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">5</a>	
           </td>
          <td>
            <c:url value="Zahl" var="url">
				<c:param name="Zahl" value="6" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">6</a>	
           </td><td>
            <c:url value="Zahl" var="url">
				<c:param name="Zahl" value="7" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">7</a>	
           </td><td>
            <c:url value="Zahl" var="url">
				<c:param name="Zahl" value="8" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">8</a>	
           </td><td>
            <c:url value="Zahl" var="url">
				<c:param name="Zahl" value="9" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">9</a>	
           </td>
           </tr>
           </table>
            <br />
          <table border="1" cellspacing="1" cellpadding="1">
		<tr> 
			<td>Ihre Zahlen sind :</td>
				<c:forEach var="e" items="${zahlenliste}" varStatus="status">
					<td>${e}</td>
				</c:forEach>
		</tr>
		</table>
		<br>${failureComment}
        	<c:url value="ZahlenConfirmSingle" var="url">
           		<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Einzeltipschein</a></section>
			
			<c:url value="ZahlenConfirmPermaMonth" var="url">
           		<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Dauertipschein Monat</a></section>
			
			
			<c:url value="ZahlenConfirmPermaHalf" var="url">
           		<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Dauertipschein Halbes Jahr</a></section>
			
			<c:url value="ZahlenConfirmPermaYear" var="url">
           		<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Dauertipschein Jahr</a></section>
	</div>
   </c:otherwise>
</c:choose>