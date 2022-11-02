<%@ page import="java.util.Date" %>
<%@ page import="java.util.Random" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Demo JSP</title>
</head>
<body>
    <h1>This is demo.jsp</h1>
    <a href="${pageContext.request.contextPath}/">Home</a>

    <!-- Reference static file (image) from webapp directory. -->
    <img src="${pageContext.request.contextPath}/home.png" alt="House" height="50px" width="50px">

    <p>The time is <%=new Date().toString()%></p>

    <%
        // You can use java code and HTML together in a JSP.
        Random random = new Random();
        if (random.nextInt(10) % 2 == 0)
        {
        %>
            <h2>true</h2>
        <%
        }
        else
        {
        %>
            <h2>false</h2>
        <%
        }
    %>

</body>
</html>
