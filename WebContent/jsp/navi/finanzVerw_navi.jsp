<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>

			<c:choose>
				<c:when test="${sub_navi_name!='Statistiken'}"> 
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="sub_navi" value="../navi/finanzVerw_navi.jsp" />
						<c:param name="top_navi_name" value="Finanzverwaltung" />
						<c:param name="sub_navi_name" value="Statistiken" />
						<c:param name="content" value="../logic/finanzStatistiken.jsp" />
						<c:param name="content_active" value="true" />
					</c:url>
					<a href ="${url}">Statistiken</a>
				</c:when>
				<c:when test="${sub_navi_name=='Statistiken'}"> 
					Statistiken
				</c:when>
			</c:choose>
			
			<c:choose>
				<c:when test="${sub_navi_name!='6aus49'}"> 
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="sub_navi" value="../navi/finanzVerw_navi.jsp" />
						<c:param name="top_navi_name" value="Finanzverwaltung" />
						<c:param name="sub_navi_name" value="6aus49" />
						<c:param name="content" value="../logic/6aus49_Stat.jsp" />
						<c:param name="content_active" value="true" />
					</c:url>
					<a href ="${url}">6aus49</a>
				</c:when>
				<c:when test="${sub_navi_name=='6aus49'}"> 
					6aus49
				</c:when>
			</c:choose>

			
			<c:choose>
				<c:when test="${sub_navi_name!='Nummernlotto'}"> 
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="sub_navi" value="../navi/finanzVerw_navi.jsp" />
						<c:param name="top_navi_name" value="Finanzverwaltung" />
						<c:param name="sub_navi_name" value="Nummernlotto" />
						<c:param name="content" value="../logic/nummernlotto_Stat.jsp" />
						<c:param name="content_active" value="true" />
					</c:url>
					<a href ="${url}">Nummernlotto</a>
				</c:when>
				<c:when test="${sub_navi_name=='Nummernlotto'}"> 
					Nummernlotto
				</c:when>
			</c:choose>
			
			<c:choose>
				<c:when test="${sub_navi_name!='FuﬂballToto'}"> 
					<c:url value="employeeNavigation" var="url">
						<c:param name="uid" value="${currentUser.identifier}" />
						<c:param name="sub_navi" value="../navi/finanzVerw_navi.jsp" />
						<c:param name="top_navi_name" value="Finanzverwaltung" />
						<c:param name="sub_navi_name" value="FuﬂballToto" />
						<c:param name="content" value="../logic/fussballToto_Stat.jsp" />
						<c:param name="content_active" value="true" />
					</c:url>
					<a href ="${url}">FuﬂballToto</a>
				</c:when>
				<c:when test="${sub_navi_name=='FuﬂballToto'}"> 
					FuﬂballToto
				</c:when>
			</c:choose>
