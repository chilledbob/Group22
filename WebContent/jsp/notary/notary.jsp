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
<title>Registrierung</title>
<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/css.css" />" />
</head>
<body>

<div id="top">
	<div id="top_content">
			<jsp:include page="../head/head.jsp" />
		
		<div class="top_navi">
			<section>Ergebnisse für 6 aus 49</section>
		</div>	
		
		<div class="sub_navi" >
			<br>
		</div>	
	</div>
</div>
		
<div id="middle">

	<div class="main_content">
		<div class="current_content">
		<p style="text-align:center;">Ergebnise der letzten Ziehung ${fail}</p>
		<p style="text-align:left;">Bitte geben Sie die Daten ein.</p>
   		<div class="failComment">${failureComment}</div>
   		<h4>Ergebnisse für die Ziehung ${draw.planedEvaluationDate }</h4>
		<form id="form" action="WeeklyLottoDrawResult" method="post" style="border:0px;">
			<input name="drawID" id="drawID" type="text" value="${draw.getId() }"/>
			<fieldset style="border:0px;"> 	
			<table>
				<tr>
					<td><label for="vame">Zahl 1</label></td>
					<td><input name="number1" id="number1"  type="text" size="1" maxlength="2" value="0"/></td> 
				</tr>
					<tr>
						<td><label for="name">Zahl 2</label></td>
						<td><input name="number2"  id="number2"  type="text" size="1" maxlength="2" value="0"/></td>
					</tr>
					<tr>
						<td><label for="uid">Zahl 3</label></td>
						<td><input name="number3" id="number3" type="text" size="1" maxlength="2" value="0"/></td>
					</tr>
					<tr>
						<td><label for="password">Zahl 4</label></td>
						<td><input name="number4" id="number4"  type="text" size="1" maxlength="2" value="0"/></td>
					</tr>
					<tr>
						<td><label for="email">Zahl 5</label></td>
						<td><input name="number5" id="number" type="text" size="1" maxlength="2" value="0"/></td>
					</tr>
					<tr>
						<td><label for="street">Zahl 6</label></td>
						<td><input name="number6" id="number6" type="text" size="1" maxlength="2" value="0"/></td>
					</tr>
					<tr>
						<td><label for="street">Zusatzzahl</label></td>
						<td><input name="extraNumber" id="extraNumber" type="text" size="1" maxlength="2" value="0"/></td>
					</tr>
					<tr>
						<td><label for="street">Superzahl</label></td>
						<td><input name="superNumber" id="superNumber" type="text" size="1" maxlength="2" value="0"/></td>
					</tr>
				</table>
				<div class="button1"><button class="btn">abschicken</button></div>
			</fieldset> 
		</form> 
		
		<div class="button"><a href ="<c:url value="/" />">Cancel</a></div>	
	</div>
</div>
	

</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>




</body>
</html>