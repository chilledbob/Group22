<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>

			<c:choose>
				<c:when test="${sub_navi_name!='Gewinngemeinschaften'}"> 
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="sub_navi" value="../navi/mitgliederVerw_navi.jsp" />
						<c:param name="top_navi_name" value="Mitgliederverwaltung" />
						<c:param name="sub_navi_name" value="Gewinngemeinschaften" />
						<c:param name="content" value="../logic/gewinnGemeinschaften.jsp" />
						<c:param name="content_active" value="true" />
					</c:url>
					<a href ="${url}">Gewinngemeinschaften</a>
				</c:when>
				<c:when test="${sub_navi_name=='Gewinngemeinschaften'}"> 
					Gewinngemeinschaften
				</c:when>
			</c:choose>
			
			<c:choose>
				<c:when test="${sub_navi_name!='Mitglieder'}"> 
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="sub_navi" value="../navi/mitgliederVerw_navi.jsp" />
						<c:param name="top_navi_name" value="Mitgliederverwaltung" />
						<c:param name="sub_navi_name" value="Mitglieder" />
						<c:param name="content" value="../logic/mitglieder.jsp" />
						<c:param name="content_active" value="true" />
					</c:url>
					<a href ="${url}">Mitglieder</a>
				</c:when>
				<c:when test="${sub_navi_name=='Mitglieder'}"> 
					Mitglieder
				</c:when>
			</c:choose>

			
			<c:choose>
				<c:when test="${sub_navi_name!='Änderungsanträge'}"> 
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="sub_navi" value="../navi/mitgliederVerw_navi.jsp" />
						<c:param name="top_navi_name" value="Mitgliederverwaltung" />
						<c:param name="sub_navi_name" value="Änderungsanträge" />
						<c:param name="content" value="../logic/aenderungsantraege.jsp" />
						<c:param name="content_active" value="true" />
					</c:url>
					<a href ="${url}">Änderungsanträge</a>
				</c:when>
				<c:when test="${sub_navi_name=='Änderungsanträge'}"> 
					Änderungsanträge
				</c:when>
			</c:choose>
