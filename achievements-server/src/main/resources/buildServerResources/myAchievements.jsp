<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/include.jsp"%>
<jsp:useBean id="grantedAchievements" type="java.util.List" scope="request"/>
<jsp:useBean id="availableAchievements" type="java.util.List" scope="request"/>
<p>You unlocked <strong>${fn:length(grantedAchievements)}</strong> achievement<bs:s val="${fn:length(grantedAchievements)}"/>.</p>

<c:if test="${not empty grantedAchievements}">
    <table>
        <c:forEach items="${grantedAchievements}" var="a">
            <tr>
                <td style="width: 20%"><strong><c:if test="${not empty a.iconClassNames}"><i class="${a.iconClassNames}"></i></c:if> <c:out value="${a.name}"/></strong></td>
                <td>- <c:out value='${a.description}'/></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${not empty availableAchievements}">
    <p><a href="#" onclick="BS.Util.show('availableAchievements'); BS.Util.hide(this); return false;">View available achievements &raquo;</a></p>

    <table style="display:none" id="availableAchievements">
        <c:forEach items="${availableAchievements}" var="a">
            <tr>
                <td style="width: 20%"><strong><c:if test="${not empty a.iconClassNames}"><i class="${a.iconClassNames}"></i></c:if> <c:out value="${a.name}"/></strong></td>
                <td>- <c:out value='${a.description}'/></td>
            </tr>
        </c:forEach>
    </table>
</c:if>