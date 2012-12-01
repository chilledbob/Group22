<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="logo">
	<h1>LOTTERIE</h1>
</div>

<div class="login">
	<c:url value="editUser" var="url">
		<c:param name="uid" value="${currentUser.identifier}" />
	</c:url>
	<table style="text-align:right;">
		<th style="width:50px;"><a href ="${url}"> Account </a></th>
		<th style="width:50px;"><a href ="<c:url value="/logout/" />">Logout</a></th>
		<th style="width:150px;">${currentUser.vname} ${currentUser.nname}</th>
	</table>
</div>