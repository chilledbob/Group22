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
<title>tip_lotto</title>
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

			<section>6aus49</section>
			
			<c:url value="customerToto" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">FussballToto</a></section>
		</div>	
	</div>
</div>

	
<div id="middle">
	<div class="main_content">
				<div class="current_content">
				
				
<c:choose>
	<c:when test="${confirm}">
		<h1>Sie haben erfolgreich folgende Zahlen gewählt:</h1>
		<table border="1" cellspacing="1" cellpadding="1">
			<tr> 
				<td>Ihre Zahlen sind :</td>
				<c:forEach var="e" items="${zahlen}" varStatus="status">
					<td>${e}</td>
				</c:forEach>
				
			</tr>
		</table>
		
		<br>
		<c:url value="customerLotto" var="url">
		<c:param name="uid" value="${currentUser.identifier}" />
		</c:url>
		<section><a href="${url}">neuer Tipp</a>
		</section>
		
		<br>
		<c:url value="customerTipManagement" var="url">
			<c:param name="uid" value="${currentUser.identifier}" />
		</c:url>
		<section><a href="${url}">zur Übersicht</a></section>
	
	
	
	</c:when>
	<c:otherwise>
		<h3>Erstellen Sie einen Tip für die kommende Ziehung ${drawtime.getPlanedEvaluationDate() }</h3>
		<table border="1" cellspacing="1" cellpadding="1">
		<tr> 
		<td>
			<c:url value="LottoZahl" var="url">
				<c:param name="Zahl" value="1" />
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url }">1</a>	
		</td>
	
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="2" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">2</a>
		</td>
			
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="3" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">3</a>
		</td>
			
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="4" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">4</a>
		</td>
			
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="5" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">5</a>
		</td>
			
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="6" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">6</a>
		</td>
			
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="7" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">7</a>
		</td>
	</tr>
	<tr>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="8" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">8</a>	</td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="9" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">9</a>	</td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="10" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">10</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="11" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">11</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="12" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">12</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="13" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">13</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="14" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">14</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="15" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">15</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="16" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">16</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="17" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">17</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="18" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">18</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="19" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">19</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="20" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">20</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="21" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">21</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="22" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">22</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="23" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">23</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="24" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">24</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="25" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">25</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="26" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">26</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="27" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">27</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="28" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">28</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="29" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">29</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="30" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">30</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="31" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">31</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="32" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">32</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="33" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">33</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="34" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">34</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="35" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">35</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="36" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">36</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="37" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">37</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="38" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">38</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="39" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">39</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="40" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">40</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="41" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">41</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="42" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">42</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="43" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">43</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="44" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">44</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="45" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">45</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="46" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">46</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="47" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">47</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="48" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">48</a></td>
		<td><c:url value="LottoZahl" var="url">
			<c:param name="Zahl" value="49" />
			<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<a href ="${url}">49</a></td>
	</tr>
	</table>
	<br>${failureComment}
	<table border="1" cellspacing="1" cellpadding="1">
		<tr> 
			<td>Ihre Zahlen sind :</td>
				<c:forEach var="e" items="${zahlen}" varStatus="status">
					<td>${e}</td>
				</c:forEach>
		</tr>
	</table>
	<c:url value="LottoConfirm" var="url">
		<c:param name="uid" value="${currentUser.identifier}" />
	</c:url>
	<form id="form" action="${url }" method="post">
		<fieldset style="border:0px;">
						<label for="tipType">Bitte wählen Sie die Art des Tipscheins:</label><br>
						<input type="radio" name="tipType" value="single" checked> Einzeltip<br>
						<input type="radio" name="tipType" value="month">Dauertip für einen Monat <br>
						<input type="radio" name="tipType" value="halfyear">Dauertip für ein halbes Jahr <br>
						<input type="radio" name="tipType" value="year">Dauertip für ein Jahr
		</fieldset>
		<div class="button1"><button class="btn">Tipschein erstellen</button></div>
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
			Klicken Sie auf eine Zahl um diese zu Tippen. 
			Ihre getippten Zahlen werden unter dem Tippfeld angezeigt.
			Um eine Zahl zu entfernen wählen Sie diese noch einmal im Tippfeld aus.
			Viel Glück.
		</div>
	</div>
</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>


</body>
</html>