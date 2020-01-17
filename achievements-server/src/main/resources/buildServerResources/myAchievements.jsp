<%@ page import="org.jetbrains.buildserver.achievements.impl.AchievementProperties" %>
<%@include file="/include.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ufn" uri="/WEB-INF/functions/user" %>
<%--
  ~ Copyright 2000-2020 JetBrains s.r.o.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<jsp:useBean id="grantedAchievements" type="java.util.List" scope="request"/>
<jsp:useBean id="availableAchievements" type="java.util.List" scope="request"/>
<jsp:useBean id="allAchievementsMap" type="java.util.Map" scope="request"/>
<jsp:useBean id="achievementsEnabled" type="java.lang.Boolean" scope="request"/>
<c:set var="enabledProperty" value="<%=AchievementProperties.getAchievementsEnabledPropertyKey().getKey()%>"/>
<script type="text/javascript">
    BS.Achievements = {
        setEnabled: function(enabled) {
            BS.User.setProperty('${enabledProperty}', enabled, {
                afterComplete: function() { BS.reload(true); }
            });
        }
    }
</script>

<c:if test="${achievementsEnabled}">
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

    <p><input type="button" class="btn btn_mini" value="Disable my achievements" onclick="BS.Achievements.setEnabled(false)"/></p>
</c:if>

<c:if test="${not achievementsEnabled}">
    <p><input type="button" class="btn btn_mini" value="Enable my achievements" onclick="BS.Achievements.setEnabled(true)"/></p>
</c:if>