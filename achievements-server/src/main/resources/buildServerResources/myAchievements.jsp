<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/include.jsp"%>
<jsp:useBean id="achievements" type="java.util.List" scope="request"/>
<p>You unlocked <strong>${fn:length(achievements)}</strong> achievement<bs:s val="${fn:length(achievements)}"/></p>

<c:if test="${not empty achievements}">
    <table>
        <c:forEach items="${achievements}" var="ab">
            <c:set var="a" value="${ab.achievement}"/>
            <tr>
                <td style="width: 20%"><strong><c:if test="${not empty a.iconClassNames}"><i class="${a.iconClassNames}"></i></c:if> <c:out value="${a.name}"/></strong></td>
                <td>- <c:out value='${a.description}'/></td>
            </tr>
        </c:forEach>
    </table>
</c:if>