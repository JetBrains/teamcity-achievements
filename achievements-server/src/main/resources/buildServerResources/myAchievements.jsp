<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/include.jsp"%>
<jsp:useBean id="achievements" type="java.util.List" scope="request"/>
<c:if test="${not empty achievements}">
    <p>You unlocked <strong>${fn:length(achievements)}</strong> achievement<bs:s val="${fn:length(achievements)}"/></p>

    <c:forEach items="${achievements}" var="ab">
        <c:set var="a" value="${ab.achievement}"/>
        <div class="achievement">
            <strong title="<c:out value='${a.description}'/>"><c:if test="${not empty a.iconClassNames}"><i class="${a.iconClassNames}"></i></c:if> <c:out value="${a.name}"/></strong>
        </div>
    </c:forEach>
</c:if>