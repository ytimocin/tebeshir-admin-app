<%-- 
    Document   : socialNetworkEdit
    Created on : Jan 5, 2014, 6:14:50 PM
    Author     : yetkin.timocin
--%>

<%@page import="com.tebeshir.admin.app.classes.SocialNetwork"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>tebeshir admin - social networks - social network edit</title>
    </head>
    <%
        int soNetID = 0;
        if (request.getParameter("soNetID") == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("socialNetworks.jsp");
            dispatcher.forward(request, response);
        } else {
            soNetID = Integer.valueOf(request.getParameter("soNetID"));
        }
        
        System.out.println("SoNetID: " + soNetID);

        SocialNetwork curSoNet = new SocialNetwork();
        curSoNet = curSoNet.getSocialNetworkByID(soNetID);
        String status = (curSoNet.getStatus() == 1) ? "active" : "passive";
    %>
    <body>
    <center>
        <br/>
        <h1><a href="index.jsp">tebeshir admin</a> - <a href="socialNetworks.jsp">social networks</a> - social network edit</h1>
        <br/>
        <br/>
        <h2></h2>
        <table border="1">
            <tr>
                <td>Social Network ID</td>
                <td>Social Network Name</td>
                <td>Social Network Status</td>
                <td>Social Network Logo</td>
            </tr>
            <tr style="text-align: center">
                <td><%=curSoNet.getSocialNetworkID()%></td>
                <td><input type="text" name="soNetNameEdit" value="<%=curSoNet.getSocialNetworkName()%>"/></td>
                <td><%=status%></td>
                <td><img src="<%=curSoNet.getImageLocation()%>"/><br/>image upload</td>
            </tr>
        </table>
        <br/>
        <br/>
    </center>
</body>
</html>
