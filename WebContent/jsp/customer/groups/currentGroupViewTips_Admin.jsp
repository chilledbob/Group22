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
			<section><a href ="showInvitations">Meine Einladungen (${invCount})</a></section>
			<section><a href ="showApplications">Meine Bewerbungen (${applCount})</a></section>
			<section>Gruppen</section>
		</div>	
	</div>
</div>
	
<div id="middle">
	<div class="main_content_full">
		<div class="current_content">
		
			<br>
			<h4 align="center">Tipps<sup style="text-shadow: aqua;font-size: x-small;">${currentGroup.name} | Admin</sup></h4>
			
			<div align="center">
				<a href ="currentGroupViewAdmin">Mitglieder (${currentGroup.getGroupMembers().size()})</a>
				<a href ="currentGroupViewTips_Admin">Tipps</a>
				<a href ="currentGroupViewApplications">Bewerbungen (${groupApplCount})</a>
				<a href ="currentGroupViewInvitations">Einladungen (${groupInvCount})</a>    
				<a href ="myGroups">Abbrechen</a>
			</div>	
			<br>		
			
			<c:url value="new_weeklyLotto_GroupTip" var="url6">
				<c:param name="groupName" value="${currentGroup.name}" />
				<c:param name="uid" value="${currentUser.identifier }" />
			</c:url>
			<c:url value="new_dailyLotto_GroupTip" var="url7">
				<c:param name="groupName" value="${currentGroup.name}" />
				<c:param name="uid" value="${currentUser.identifier }" />
			</c:url>
			<c:url value="new_toto_GroupTip" var="url8">
				<c:param name="groupName" value="${currentGroup.name}" />
				<c:param name="uid" value="${currentUser.identifier }" />
			</c:url>
			<div align="center">
				<a href="${url6 }">neuen 6 aus 49 Tip erstellen</a>
				<a href="${url7 }">neuen Nummernlottotip erstellen</a>
				<a href="${url8 }">neuen Tototip erstellen</a>
			</div>
			<p/>
			<h4 align="center">- aktuelle Gruppentips -</h4>
			<table>
				<tr>
					<td>
						Spiel
					</td>
					<td>
						Ziehungsdatum
					</td>
					<td>
						Aktion
					</td>
				</tr>
				<c:forEach var="wSTT" items="${weeklySTTList}">
				<tr>
					<td>
						wöchentliche Zahlenlotto
					</td>
					<td>
						${wSTT.getDraw().getPlanedEvaluationDate() }
					</td>
					<td>
						<c:url value="LottoGroupTipChangeAdmin" var="url">
							<c:param name="uid" value="${currentCustomer.identifier }" />
							<c:param name="groupName" value="${currentGroup.getName() }" />
							<c:param name="tip" value="${wSTT.getId() }" />
						</c:url>
						<a href="${url }">bearbeiten</a>
					</td>
				</tr>
				</c:forEach>
				<c:forEach var="dSTT" items="${dailySTTList}">
				<tr>
					<td>
						tägliche Nummernlotto
					</td>
					<td>
						${dSTT.getDraw().getPlanedEvaluationDate() }
					</td>
					<td>
						<c:url value="LottoGroupTipChangeAdmin" var="url">
							<c:param name="uid" value="${currentCustomer.identifier }" />
							<c:param name="groupName" value="${currentGroup.getName() }" />
							<c:param name="tip" value="${wSTT.getId() }" />
						</c:url>
						<a href="${url }">bearbeiten</a>
					</td>
				</tr>
				</c:forEach>
				<c:forEach var="tSTT" items="${totoSTTList}">
				<tr>
					<td>
						Fußballtoto
					</td>
					<td>
						${tSTT.getDraw().getPlanedEvaluationDate() }
					</td>
					<td>
						<c:url value="LottoGroupTipChangeAdmin" var="url">
							<c:param name="uid" value="${currentCustomer.identifier }" />
							<c:param name="groupName" value="${currentGroup.getName() }" />
							<c:param name="tip" value="${wSTT.getId() }" />
						</c:url>
						<a href="${url }">bearbeiten</a>
					</td>
				</tr>
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