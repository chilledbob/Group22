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
		<div id="logologin">

	<div id="logo">
		<h1 id="L">L</h1>
		<img src="<c:url value="/res/img/treflepetit.jpg" />" alt="trefle" class ="trefle" id="trefle"/>
		<h1 id="tterie">tterie</h1>
	</div>
				
	<div class="login">
			<form method="post" action="login">
				<label for="uid">Name: </label>
					<input type="text" id="uid" name="uid" size="12" maxlength="16">
				<label for="password">Passwort: </label>
					<input type="password" id="password" name="password" size="12" maxlength="16">
				<button type="submit">login</button> 
			</form> 
	</div>
			
</div>
		
		<div class="top_navi">
			<section>Registrierung</section>
		</div>	
		
		<div class="sub_navi" >
			<br>
		</div>	
	</div>
</div>
		
<div id="middle">

	<div class="main_content">
		<div class="current_content">
		<p style="text-align:center;">Neuer Nutzer ${fail}</p>
		<p style="text-align:left;">Bitte geben Sie ihre Daten ein.</p>
   		<div class="failComment">${comment}</div>
		<form id="form" action="createUser" method="post" style="border:0px;"> 
			<fieldset style="border:0px;"> 	
			<table>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
					<td><label for="vame">Vorname</label></td>
					<td><input name="vname" id="vname"  type="text" size="30" maxlength="30" value="${vname}"/></td> 
				</tr>
					<tr>
						<td><label for="name">Nachname</label></td>
						<td><input name="nname"  id="nname"  type="text" size="30" maxlength="30" value="${nname}"/></td>
					</tr>
					<tr>
						<td><label for="uid">Nutzername</label></td>
						<td><input name="uid" id="uid" class="${uidFail}" type="text" size="30" maxlength="30" value="${uid}"/></td>
					</tr>
					<tr>
						<td><label for="password">Passwort</label></td>
						<td><input name="password" id="password"  type="password" size="30" maxlength="30" value="${password}"/></td>
					</tr>
					<tr>
						<td><label for="email">eMail</label></td>
						<td><input name="email" id="email" type="test" size="30" maxlength="30" value="${email}"/></td>
					</tr>
					<tr>
						<td><label for="street">Strasse</label></td>
						<td><input name="street" id="street" type="text" size="30" maxlength="30" value="${street}"/></td>
					</tr>
					<tr>
						<td><label for="hNumber">Hausnummer</label></td>
						<td><input name="hNumber" id="hNumber" type="text" size="30" maxlength="30" value="${hNumber}"/></td>
					</tr>
					<tr>
						<td><label for="plz">PLZ</label></td>
						<td><input name="plz" id="plz" type="text" size="30" maxlength="30" value="${plz}"/></td>
					</tr>
					<tr>
						<td><label for="city">Stadt</label></td>
						<td><input name="city" id="city" type="text" size="30" maxlength="30" value="${city}"/></td>
					</tr>
					<tr>
						<td><label for="bankCode">Bankleitzahl</label></td>
						<td><input name="bankCode" id="bankCode" type="text" size="30" maxlength="30" value="${bankCode}"/></td>
					</tr>
					<tr>
						<td><label for="accountNumber">Kontonummer</label></td>
						<td><input name="accountNumber" id="accountNumber" type="text" size="30" maxlength="30" value="${accountNumber}"/></td>
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