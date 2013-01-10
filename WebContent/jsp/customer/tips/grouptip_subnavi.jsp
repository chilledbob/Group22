<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>


			<section><a href ="newGroup">Erstellen</a></section>
			<section><a href ="showInvitations">Meine Einladungen (${invCount})</a></section>
			<section><a href ="showApplications">Meine Bewerbungen (${applCount})</a></section>
			<c:url value="currentGroupView" var="url">
				<c:param name="groupName" value="${currentGroup.name }" />
				<c:param name="uid" value="${currentUser.identifier }"/>
			</c:url>
			<section><a href="${url }">${currentGroup.name} : Admin</a></section>
			<section>${drawType }</section>