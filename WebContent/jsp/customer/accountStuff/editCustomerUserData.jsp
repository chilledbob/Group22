<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Account</title>
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
			<c:url value="editUser" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<table>
				<tr>
				<th id="account"><a href ="${url}">Account</a></th>
				<th id="logout"><a href ="<c:url value="/logout/" /> ">Logout</a></th>
				<th id="namevorname">
				<section id="name-vorname"> ${currentUser.getMemberData().getFirstName()}  ${currentUser.getMemberData().getLastName()}</section>
				</th>
				</tr>
			</table>
	</div>
			
</div>
		
		<div class="top_navi">
				<c:url value="customerTipManagement" var="url">
					<c:param name="uid" value="${currentUser.identifier}" />
				</c:url>
				<section><a href ="${url}">Tippverwaltung</a></section>
				
				<c:url value="customerGroups" var="url">
					<c:param name="uid" value="${currentUser.identifier}" />
				</c:url>
				<section><a href ="${url}">Gewinngemeinschaften</a></section>
		</div>	
		
		<div class="sub_navi" >
			<section>Account</section>
			<section><a href ="bankingCustomer">Banking</a></section>
		</div>	
	</div>
</div>
		
<div id="middle">
	<div class="main_content_full">
		<div class="current_content">
		
			<h4 align="center">Nutzer Daten<sup style="text-shadow: aqua;font-size: x-small;">${currentUser.identifier}</sup></h4>
  
			<div align="center">
				<a href ="editUser">Nutzerdaten</a>
				<a href ="editCustomerPassword">Passwort</a>
				<a href ="editCustomerRealAccount">Kontodaten</a>  
				<a href ="cancelEditingCustomer">Abbrechen</a>
			</div>	
			<br>
			<div align="center">${comment}</div><br>	
    	
		<form id="form" action="changeCustomerUserData" method="post" style="border:0px;"> 
			<table>
				<tr>
					<td><label for="vame">Vorname</label></td>
					<td><input name="vname" id="vname"  type="text" size="30" maxlength="30" value="${currentUser.getMemberData().getFirstName()}"/></td> 
				</tr>
				<tr>
					<td><label for="name">Nachname</label></td>
					<td><input name="nname"  id="nname"  type="text" size="30" maxlength="30" value="${currentUser.memberData.getLastName()}"/></td>
				</tr>
				<tr>
					<td><label for="email">eMail</label></td>
					<td><input name="email" id="email" type="text" size="30" maxlength="30" value="${currentUser.memberData.getEMail()}"/></td>
				</tr>
				<tr>
					<td><label for="street">Strasse</label></td>
					<td><input name="street" id="street" type="text" size="30" maxlength="30" value="${currentUser.getMemberData().getAdress().getStreetName()}"/></td>
				</tr>
				<tr>
					<td><label for="hNumber">Hausnummer</label></td>
					<td><input name="hNumber" id="hNumber" type="text" size="30" maxlength="30" value="${currentUser.getMemberData().getAdress().getHouseNumber()}"/></td>
				</tr>
				<tr>
					<td><label for="plz">PLZ</label></td>
					<td><input name="plz" id="plz" type="text" size="30" maxlength="30" value="${currentUser.getMemberData().getAdress().getPostCode()}"/></td>
				</tr>
				<tr>
					<td><label for="city">Stadt</label></td>
					<td><input name="city" id="city" type="text" size="30" maxlength="30" value="${currentUser.getMemberData().getAdress().getTownName()}"/></td>
				</tr>
			</table>
			<input name="uid" id="uid" type="hidden" value="${currentUser.identifier}"/>
			
			<div align="center" class="button"><button class="btn">�ndern</button></div>
		</form> 
		
			
	</div>
</div>
	

</div>

<div class="footer">
		<p>Copyright SuperLotterie �</p>
</div>




</body>
</html>