<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Banking Customer Load</title>
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
			<c:url value="editUser" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Account</a></section>
			<section>Banking</section>
		</div>	
		
		<div class="sub_navi" >
			<br>
		</div>	
	</div>
</div>
		
<div id="middle">

	<div class="main_content">
		<div class="current_content">
		<p style="text-align:left;"> Konto Aufladen</p>
    <br>BLZ : ${currentUser.getBankAccount().getRealAccountData().getBankCode()}
    <br>KtNr.: ${currentUser.getBankAccount().getRealAccountData().getAccountNumber()}
    <br>Kontostand : ${currentUser.getBankAccount().getCredit()}
    <br><br>
    	<c:url value="loadingBankAccount" var="url">
			<c:param name="uid" value="${currentUser.identifier}" />
		</c:url>
		<section><a href ="${url}">aufladen</a></section>
		<c:url value="chargingBankAccount" var="url">
			<c:param name="uid" value="${currentUser.identifier}" />
		</c:url>
		<section><a href ="${url}">abbuchen</a></section>
	
	</div>
</div>

<div class="footer">
		<p>Copyright SuperLotterie ©</p>
</div>




</body>
</html>