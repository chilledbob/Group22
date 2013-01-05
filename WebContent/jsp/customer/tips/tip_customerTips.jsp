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
			<section>meine Tipps</section>
			
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
	<div class="main_content">

				<div class="current_content">
			
				
	
	<h1>Ihre Zusammenfassung</h1>
	Hier finden Sie eine Auflistung aller ihrer Tips.
	
	<h2>Einzeltipscheine</h2>
	 <table>
	 <tr>
	 	<td>
	 			<h4>Tipticketnr.</h4>
	 	</td>
	 	<td>
	 			<h4>Spielart</h4>
	 	</td>
	 	<td>
	 			<h4>Ziehungsdatum</h4>
	 	</td>
	 	<td>
	 			<h4>Aktion</h4>
	 	</td>
	</tr>
	<c:forEach var="wSTT" items="${weeklySTTList}">
		<tr>
			<td>
				${wSTT.getId()}
			</td>
			<td>
				${wSTT.getClass().getSimpleName() }
			</td>
			<td>
				${wSTT.getTip().getDraw().getPlanedEvaluationDate() }
			</td>
			<td>
				<c:url value="customerEditSTT" var="url">
					<c:param name="uid" value="${currentUser.identifier }" />
					<c:param name="STTid" value="${wSTT.getId() }" />
				</c:url>
				<a href="${url }">bearbeiten</a>
			</td>
		</tr>
	</c:forEach>
	<c:forEach var="tSTT" items="${totoSTTList}">
		<tr>
			<td>
				${tSTT.getId()}
			</td>
			<td>
				${tSTT.getClass().getSimpleName() }
			</td>
			<td>
				${tSTT.getTip().getDraw().getPlanedEvaluationDate() }
			</td>
			<td>
				<c:url value="customerEditSTT" var="url">
					<c:param name="uid" value="${currentUser.identifier }" />
					<c:param name="STTid" value="${tSTT.getId() }" />
				</c:url>
				<a href="${url }">bearbeiten</a>
			</td>
		</tr>
	</c:forEach>
	<c:forEach var="dSTT" items="${dailySTTList}">
		<tr>
			<td>
				${dSTT.getId()}
			</td>
			<td>
				${dSTT.getClass().getSimpleName() }
			</td>
			<td>
				${dSTT.getTip().getDraw().getPlanedEvaluationDate() }
			</td>
			<td>
				<c:url value="customerEditSTT" var="url">
					<c:param name="uid" value="${currentUser.identifier }" />
					<c:param name="STTid" value="${dSTT.getId() }" />
				</c:url>
				<a href="${url }">bearbeiten</a>
			</td>
		</tr>
	</c:forEach>
	</table>
	
		<h2>Dauertipscheine</h2>
	 <table>
	 <tr>
	 	<td>
	 			<h4>Tipticketnr.</h4>
	 	</td>
	 	<td>
	 			<h4>Spielart</h4>
	 	</td>
	 	<td>
	 			<h4>Tip</h4>
	 	</td>
	 	<td>
	 			<h4>Aktion</h4>
	 	</td>
	</tr>
	<c:forEach var="wPTT" items="${weeklyPTTList}">
		<tr>
			<td>
				${wPTT.getId()}
			</td>
			<td>
				wöchentliches Zahlenlotto
			</td>
			<td>
				<c:forEach items="${wPTT.getTip() }" var="i">
					${i }
				</c:forEach>
			</td>
			<td>
				<a href="customerEditPTT">bearbeiten</a>
			</td>
		</tr>
	</c:forEach>
	<c:forEach var="dPTT" items="${dailyPTTList}">
		<tr>
			<td>
				${dPTT.getId()}
			</td>
			<td>
				tägliches Nummernlotto
			</td>
			<td>
				<c:forEach items="${dPTT.getTip() }" var="i">
					${i }
				</c:forEach>
			</td>
			<td>
				<a href="customerEditPTT">bearbeiten</a>
			</td>
		</tr>
	</c:forEach>
	</table>
	
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