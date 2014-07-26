<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/include.jsp"%>
<jsp:useBean id="newAchievements" type="java.util.List" scope="request"/>
<c:if test="${not empty newAchievements}">
    <c:set var="achievementsList">
        <c:forEach items="${newAchievements}" var="ab" varStatus="pos">
            <c:set var="a" value="${ab.achievement}"/>
            <a href="<c:url value='/profile.html?tab=teamcity-achievements'/>"><strong title="<c:out value='${a.description}'/>"><c:if test="${not empty a.iconClassNames}"><i class="${a.iconClassNames}"></i></c:if> <c:out value="${a.name}"/></strong></a><c:if test="${not pos.last}">, </c:if>
        </c:forEach>
    </c:set>
    <c:set var="hideKeys"><c:forEach items="${newAchievements}" var="ab" varStatus="pos">'${ab.hidePropertyKey}'<c:if test="${not pos.last}">, </c:if></c:forEach></c:set>
    <div class="achievements" onclick="BS.Achievements.close(${hideKeys})">
        <a class="hideAchievement" href="javascript://" onclick="return false;">x</a>
        Congratulations! You've just unlocked ${achievementsList} achievement<bs:s val="${fn:length(newAchievements)}"/>.
    </div>
    <script type="text/javascript">
        BS.Achievements = {
            close: function() {
                var toClose = arguments.length;
                if (toClose == 0) return;
                var props = arguments;

                window.setTimeout(function() {
                    for (var i=0; i < props.length; i++) {
                        BS.User.setBooleanProperty(props[i], true, {
                            afterComplete: function() { toClose--; }
                        });
                    }
                }, 0);

                var waitFunc = function() {
                    if (toClose == 0) {
                        $('grantedAchievements').refresh();
                        return true;
                    }
                    return false;
                };
                var tid = window.setInterval(function() {
                    if (waitFunc()) {
                        window.clearInterval(tid);
                    }
                }, 10);
            }
        }
    </script>

</c:if>
