<%-- 
    Document   : indexProcess
    Created on : Dec 11, 2013, 6:55:17 PM
    Author     : yetkin.timocin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            out.println(request.getParameter("p_scnt"));
            out.println(request.getParameter("nodePhoto"));
        %>
    </body>
</html>
