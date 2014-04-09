<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/include.jsp"%>
<jsp:useBean id="achievements" type="java.util.List" scope="request"/>
<c:if test="${not empty achievements}">
    <c:forEach items="${achievements}" var="ab">
        <c:set var="a" value="${ab.achievement}"/>
        <div class="achievement attentionComment">
            <a href="#" onclick="BS.Util.show('hideProgress'); BS.User.setBooleanProperty('${ab.hidePropertyKey}', true, { afterComplete: function() { $('grantedAchievements').refresh(); } }); return false;" class="hideAchievement">Hide <forms:progressRing id="hideProgress" style="display: none; margin-left: 0.5em; float: none;"/></a>
            Congratulations! You've just unlocked <strong title="<c:out value='${a.description}'/>"><c:if test="${not empty a.iconClassNames}"><i class="${a.iconClassNames}"></i></c:if> <c:out value="${a.name}"/></strong> achievement!
        </div>
    </c:forEach>
</c:if>