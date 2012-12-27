<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.salespoint-framework.org/web/taglib" prefix="sp" %>

<c:choose>
	<c:when test="${confirm}">
		<h1>Sie haben erfolgreich folgende Zahlen gewählt:</h1>
		<table border="1" cellspacing="1" cellpadding="1">
			<tr> 
				<td>Ihre Zahlen sind :</td>
				<c:forEach var="e" items="${numbers}" varStatus="status">
					<td>${e}</td>
				</c:forEach>
				
			</tr>
		</table>
		
		<br>
		<c:url value="customerLotto" var="url">
		<c:param name="uid" value="${currentUser.identifier}" />
		</c:url>
		<section><a href="${url}">neuer Tipp</a>
		</section>
		
		<br>
		<c:url value="customerTipManagement" var="url">
			<c:param name="uid" value="${currentUser.identifier}" />
		</c:url>
		<section><a href="${url}">zur Übersicht</a></section>
	
	
	
	</c:when>
	<c:otherwise>
		<h3>Erstellen Sie einen Tip für die kommende Ziehung ${drawtime.getPlanedEvaluationDate() }</h3>
		<table border="1" cellspacing="1" cellpadding="1">
		<tr> 
		<td>
			<c:url value="LottoNumberSelectGroup" var="url">
				<c:param name="number" value="1" />
				<c:param name="uid" value="${currentUser.identifier}" />
				<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url }">1</a>	
		</td>
	
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="2" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">2</a>
		</td>
			
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="3" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">3</a>
		</td>
			
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="4" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">4</a>
		</td>
			
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="5" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">5</a>
		</td>
			
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="6" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">6</a>
		</td>
			
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="7" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">7</a>
		</td>
	</tr>
	<tr>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="8" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">8</a>	</td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="9" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">9</a>	</td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="10" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">10</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="11" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">11</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="12" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">12</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="13" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">13</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="14" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">14</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="15" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">15</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="16" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">16</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="17" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">17</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="18" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">18</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="19" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">19</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="20" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">20</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="21" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">21</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="22" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">22</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="23" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">23</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="24" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">24</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="25" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">25</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="26" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">26</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="27" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">27</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="28" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">28</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="29" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">29</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="30" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">30</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="31" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">31</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="32" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">32</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="33" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">33</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="34" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">34</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="35" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">35</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="36" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">36</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="37" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">37</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="38" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">38</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="39" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">39</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="40" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">40</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="41" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">41</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="42" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">42</a></td>
	</tr>
	<tr>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="43" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">43</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="44" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">44</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="45" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">45</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="46" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">46</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="47" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">47</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="48" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">48</a></td>
		<td><c:url value="LottoNumberSelectGroup" var="url">
			<c:param name="number" value="49" />
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
			</c:url>
			<a href ="${url}">49</a></td>
	</tr>
	</table>
	<br>${failureComment}
	<table border="1" cellspacing="1" cellpadding="1">
		<tr> 
			<td>Ihre Zahlen sind :</td>
				<c:forEach var="e" items="${numbers}" varStatus="status">
					<td>${e}</td>
				</c:forEach>
		</tr>
	</table>
		<c:url value="LottoGroupConfirm" var="url">
			<c:param name="uid" value="${currentUser.identifier}" />
			<c:param name="groupName" value="${currentGroup.name }" />
		</c:url>
		<section><a href ="${url}">jetzt Tippen</a></section>
	</c:otherwise>  
</c:choose>