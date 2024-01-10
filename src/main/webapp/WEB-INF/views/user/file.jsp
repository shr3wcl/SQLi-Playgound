<%@ page import="org.apache.tomcat.util.codec.binary.Base64" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${rawData == null}">
    <c:import var="image" url="/resources/upload/${filename}"/>
</c:if>
<c:if test="${not empty rawData}">
    <c:set var="img" value=""/>
    <%
        byte[] imageData = (byte[]) request.getAttribute("rawData");
        if (imageData != null) {
            String base64Image = Base64.encodeBase64String(imageData);
            pageContext.setAttribute("img", base64Image);
        }
    %>
    <c:choose>
        <c:when test="${imageType eq 'false'}">
            ${img}
        </c:when>
        <c:otherwise>
            <img src="data:image/png;base64,${img}"/>
        </c:otherwise>
    </c:choose>

</c:if>
