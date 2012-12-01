<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html>

<html>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/user_style.css" />" />
		<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/link_style.css" />" />
	</head>

<body>
	<div id="top">
		<div id="top_content">
			<div id="logo">
				<h1>LOTTERIE</h1>
			</div>
			<div class="login">
				<form method="post" action="login">
					<label for="uid">Name: </label>
					<input type="text" id="uid" name="uid" size="12" maxlength="16">
					<label for="password">Passwort: </label>
					<input type="password" id="password" name="password" size="12" maxlength="16">
					<button type="submit">login</button>
					oder   
					<a href ="<c:url value="/newUser/" />">registrieren</a>
				</form> 
			</div>
			
			
			
		</div>
	</div>
	
<div style="clear:all;"></div>		
<div id="middle">
	<div class="main_content">
		<div class="current_content">
		<h3 style="text-align:center;">Neuer Nutzer ${fail}</h3>
    <br>
    <br>
   		<div class="failComment">${comment}</div>
		<form id="form" action="makeUser" method="post" style="border:0px;"> 
			<fieldset style="border:0px;"> 	
			<table>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
					<td><label for="vame">Vorname</label></td>
					<td><input name="vname" id="vname"  type="text" size="30" maxlength="30" value="${vorname}"/></td> 
				</tr>
					<tr>
						<td><label for="name">Nachname</label></td>
						<td><input name="nname"  id="nname"  type="text" size="30" maxlength="30" value="${nachname}"/></td>
					</tr>
					<tr>
						<td><label for="uid">UserID</label></td>
						<td><input name="uid" id="uid" class="${uidFail}" type="text" size="30" maxlength="30" value="${uid}"/></td>
					</tr>
					<tr>
						<td><label for="password">Passwort</label></td>
						<td><input name="password" id="password"  type="password" size="30" maxlength="30" value="${password}"/></td>
					</tr>
					
					</table>
					
					<div class="button1"><button class="btn">abschicken</button></div>
		
				
				
			</fieldset> 
		</form> 
		
		<div class="button"><a href ="<c:url value="/" />">Cancel</a></div>	
	</div></div>
</div>
	</body>

</html>