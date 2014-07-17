<%@include file="/include.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="grantedAchievements" type="java.util.List" scope="request"/>
<jsp:useBean id="availableAchievements" type="java.util.List" scope="request"/>
<jsp:useBean id="allAchievementsMap" type="java.util.Map" scope="request"/>
<p>You unlocked <strong>${fn:length(grantedAchievements)}</strong> achievement<bs:s val="${fn:length(grantedAchievements)}"/>.</p>

<c:if test="${not empty grantedAchievements}">
    <table class="achievementsTable">
        <c:forEach items="${grantedAchievements}" var="a">
            <c:set var="awardedUsers" value="${allAchievementsMap[a]}"/>
            <tr>
                <td class="achievementName"><strong><c:if test="${not empty a.iconClassNames}"><i class="${a.iconClassNames}"></i></c:if> <c:out value="${a.name}"/></strong></td>
                <td class="description">- <c:out value='${a.description}'/></td>
                <td>${fn:length(awardedUsers)} awarded</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${not empty availableAchievements}">
    <p><a href="#" onclick="BS.Util.show('availableAchievements'); BS.Util.hide(this); return false;">View available achievements &raquo;</a></p>

    <table style="display:none" id="availableAchievements" class="achievementsTable">
        <c:forEach items="${availableAchievements}" var="a">
            <c:set var="awardedUsers" value="${allAchievementsMap[a]}"/>
            <tr>
                <td class="achievementName"><strong><c:if test="${not empty a.iconClassNames}"><i class="${a.iconClassNames}"></i></c:if> <c:out value="${a.name}"/></strong></td>
                <td class="description">- <c:out value='${a.description}'/></td>
                <td>${fn:length(awardedUsers)} awarded</td>
            </tr>
        </c:forEach>
    </table>
</c:if>