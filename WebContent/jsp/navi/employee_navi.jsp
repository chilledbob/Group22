<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>

			<c:choose>
				<c:when test="${top_navi_name!='Mitgliederverwaltung'}"> 
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="sub_navi" value="../navi/mitgliederVerw_navi.jsp" />
						<c:param name="sub_navi_name" value="" />
						<c:param name="content" value="" />
						<c:param name="content_active" value="false" />
						<c:param name="top_navi_name" value="Mitgliederverwaltung" />
					</c:url>
					<a href ="${url}">Mitgliederverwaltung</a>
				</c:when>
				<c:when test="${top_navi_name=='Mitgliederverwaltung'}"> 
					Mitgliederverwaltung
				</c:when>
			</c:choose>
				
			<c:choose>
				<c:when test="${top_navi_name!='Finanzverwaltung'}">
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="top_navi_name" value="Finanzverwaltung" />
						<c:param name="sub_navi" value="../navi/finanzVerw_navi.jsp" />
						<c:param name="sub_navi_name" value="" />
						<c:param name="content" value="" />
						<c:param name="content_active" value="false" />
					</c:url>
					<a href ="${url}">Finanzverwaltung</a>
				</c:when>
				<c:when test="${top_navi_name=='Finanzverwaltung'}">
					Finanzverwaltung
				</c:when>
			</c:choose>
			
			<sp:hasCapability capabilityName="manager">
			<c:choose>
				<c:when test="${top_navi_name!='Spielverwaltung'}">
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="top_navi_name" value="Spielverwaltung" />
						<c:param name="sub_navi" value="../navi/spielVerw_navi.jsp" />
						<c:param name="sub_navi_name" value="" />
						<c:param name="content" value="" />
						<c:param name="content_active" value="false" />						
					</c:url>
					<a href ="${url}">Spielverwaltung</a>
				</c:when>
				<c:when test="${top_navi_name=='Spielverwaltung'}">
					Spielverwaltung
				</c:when>
			</c:choose>
			</sp:hasCapability>
		