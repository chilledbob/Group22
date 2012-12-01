<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>

<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/user_style.css" />" />
	<link rel="stylesheet" type="text/css" 	href="<c:url value="/res/css/link_style.css" />" />
	<title>Editieren</title>

</head>
<body>
<div id="top">
	<div id="top_content">
		<div id="logo">
			<h1>LOTTERIE</h1>
		</div>
		<div class="login">
			<c:url value="editUser" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<table style="text-align:right;">
				<th style="width:50px;"><a href ="<c:url value="/logout/" />">Logout</a></th>
				<th style="width:150px;">${currentUser.vname} ${currentUser.nname}</th>
			</table>
		</div>
		<h3 style="position:absolute; top:80px; left:200; color:white;">${scope} - Bereich : Account</h3>
		
		<div class="top_navi"></div>	

	</div>
</div>


<div style="clear:all;"></div>			
<div id="middle">
	<div class="main_content">

	<div class="current_content">
		<h3 style="text-align:center;">Account</h3> 
 	
    	<form id="form" 
    	<sp:hasCapability capabilityName="customer">action="customerChangeUser"</sp:hasCapability>
    	<sp:hasCapability capabilityName="stuffMember">action="changeUser"</sp:hasCapability>
    	 method="post" style="border:0px;margin-top:20px;"> 
			<fieldset style="border:0px;"> 
				<table>
					<tr>
						<th></th>
						<th></th>
					</tr>
					<tr></tr>
					<tr>
						<td><label for="vname">Vorname</label></td>
						<td><input name="vname" id="vname" type="text" size="30" maxlength="30" value=" ${currentUser.getVname()}"/></td> 
					</tr>
					<tr>
						<td><label for="nname">Nachname</label></td>
					<td><input name="nname" id="nname" type="text" size="30" maxlength="30" value=" ${currentUser.getNname()}"/></td>
					</tr>
					<tr>
						<td class="hidden"><input type="hidden" name="uid" value="${currentUser.identifier}"></td>
					</tr>
					<tr>
						<td class="hidden"><input type="hidden" name="scope" value="${scope}"></td>
					</tr>
					
					</table>
					<sp:hasCapability capabilityName="customer">
						<div class="button1"><button class="btn">Änderung beantragen</button></div>
					</sp:hasCapability>
					<sp:hasCapability capabilityName="stuffMember">
						<div class="button1"><button class="btn">Ändern</button></div>
					</sp:hasCapability>
					
				
			</fieldset> 
		</form> 
	
		
		<sp:hasCapability capabilityName="stuffMember">
			<c:url value="redirectStuff" var="url1">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="scope" value="${scope}" />
			</c:url>
			<div ><a href="${url1}">Zurück</a></div>
		</sp:hasCapability>
		
		<sp:hasCapability capabilityName="customer">
			<c:url value="redirectCustomer" var="url2">
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="scope" value="${scope}" />
			</c:url>
				<div class="button"><a href="${url2}">Zurück</a></div>
		</sp:hasCapability>
					
	</div>
</div>

</div>
</body>
</html>