<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/include.jsp"%>
<jsp:useBean id="myAchievements" type="java.util.List" scope="request"/>
<div id="grantedAchievements"></div>

<script type="text/javascript">
    var updater = new BS.PeriodicalUpdater('grantedAchievements', window['base_uri'] + "/grantedAchievements.html", {
        frequency: 90,
        evalScripts: true,
        method: 'get'
    });

    $('grantedAchievements').refresh = function() {
        BS.ajaxUpdater('grantedAchievements', window['base_uri'] + "/grantedAchievements.html", { method: 'get' });
    };

    $j('#bodyWrapper').on("click", function() {
        BS.ajaxRequest(window['base_uri'] + "/grantedAchievements.html?userAction=true");
    });
</script>

<c:if test="${not empty myAchievements}">
    <c:url var="myachievementsLink" value="/profile.html?tab=teamcity-achievements"/>
    <script type="text/javascript">
        $j('#myachievements').empty();
        $j('#userPanel #sp_span_usernamePopup').prepend('<span><a id="myachievements" href="${myachievementsLink}"></a></span>');
        <c:forEach items="${myAchievements}" var="a">
        $j('#myachievements').append('<i class="${a.iconClassNames}" title="${a.name}"></i>');
        </c:forEach>
    </script>
</c:if>
