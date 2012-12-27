<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>

			<c:url value="newGroup" var="url">
				<c:param name="uid" value="${currentUser.identifier}" />
			</c:url>
			<section><a href ="${url}">Erstellen</a></section>
			
			<c:url value="myGroups" var="url">
				<c:param name="uid" value="${currentUser.identifier }"/>
			</c:url>
			<section><a href="${url }">Gruppen</a></section>
			<c:url value="currentGroupView" var="url">
				<c:param name="groupName" value="${currentGroup.name }" />
				<c:param name="uid" value="${currentUser.identifier }"/>
			</c:url>
			<section><a href="${url }">${currentGroup.name} : Admin</a></section>
			
			<section>${drawType }</section>