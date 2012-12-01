<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Site</title>
</head>
<body>

<div id="top">
	<div id="top_content">
		<jsp:include page="../head/head.jsp" />
		<br>
		
		<div class="top_navi">
			<jsp:include page="../navi/customer_navi.jsp" />
				<br><br><br>
		</div>	
		
		<div class="sub_navi">
			<c:if test="${sub_navi_active}">
				<jsp:include page="${sub_navi}" />
			</c:if>
		</div>	
	</div>
</div>

<div style="clear:all;"></div>			
<div id="middle">
	<div class="main_content">

				<div class="current_content">
				-------------------------------------------
				<br><br>
					<c:if test="${content_active}">
						<jsp:include page="${content}" />
					</c:if>
				</div>
	</div>

</div>


</body>
</html>


