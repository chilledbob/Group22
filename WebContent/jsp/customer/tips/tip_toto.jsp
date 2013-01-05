<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
    
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
	<th> SpielID</th>
	<th>Manschaft A</th>
	<th>Manschaft B</th>
	<th> Ihr Ergebnis</th>
	<c:forEach var="e" items="${spielliste}" varStatus="status">
		<tr>
			<td>
				${e.getMatchID()}
			</td>
			
			<td>
				${e.getNameTeam1()}
			</td>
			<td>
				${e.getNameTeam2()}
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
		<td>
			<c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="1" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">1</a>	
		</td>
				
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="2" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">2</a>	</td>
			
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="3" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">3</a>	</td>
			
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="4" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">4</a>	</td>
			
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="5" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">5</a>	</td>
			
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="6" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">6</a>	</td>
			
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="7" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">7</a>	</td>
	<tr>
	<tr>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="8" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">8</a>	</td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="9" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">9</a>	</td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="10" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">10</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="11" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">11</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="12" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">12</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="13" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">13</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="14" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">14</a></td>
	<tr>
	<tr>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="15" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">15</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="16" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">16</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="17" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">17</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="18" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">18</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="19" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">19</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="20" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">20</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="21" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">21</a></td>
	<tr>
	<tr>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="22" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">22</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="23" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">23</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="24" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">24</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="25" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">25</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="26" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">26</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="27" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">27</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="28" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">28</a></td>
	<tr>
	<tr>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="29" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">29</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="30" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">30</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="31" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">31</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="32" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">32</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="33" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">33</a></td>
		<td><c:url value="Matches" var="url">
			<c:param name="GroupOrderID" value="34" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">34</a></td>
		</tr>
		</table>	
	</c:when>
	<c:otherwise>
		<c:url var="url" value="TotoConfirm">
			<c:param name="GroupOrderID" value="${goid }" />
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
				${e.getMatchID()}
			</td>
			
			<td>
				${e.getNameTeam1()}
			</td>
			<td>
				${e.getNameTeam2()}
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