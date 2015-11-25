<%-- 
    Document   : socialNetworks
    Created on : Jan 5, 2014, 2:05:35 PM
    Author     : yetkin.timocin
--%>

<%@page import="java.util.Vector"%>
<%@page import="com.tebeshir.admin.app.classes.SocialNetwork"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>tebeshir admin - social networks</title>
    </head>
    <%
        SocialNetwork sNet = new SocialNetwork();
        Vector<SocialNetwork> allSoNet = new Vector<SocialNetwork>();
        allSoNet = sNet.allSocialNetworks();

    %>
    <body>
    <center>
        <br/>
        <h1><a href="index.jsp">tebeshir admin</a> - social networks</h1>
        <br/>
        <br/>
        <table border="1">
            <tr>
                <td>Social Network ID</td>
                <td>Social Network Name</td>
                <td>Social Network Status</td>
                <td>Social Network Logo</td>
                <td>Social Network Edit</td>
            </tr>
            <%
                for (int i = 0; i < allSoNet.size(); i++) {
                    String status = (allSoNet.get(i).getStatus() == 1) ? "active" : "passive";
                    System.out.println("imageLocation: " + allSoNet.get(i).getImageLocation());
            %>
            <tr style="text-align: center">
                <td><%=allSoNet.get(i).getSocialNetworkID()%></td>
                <td><%=allSoNet.get(i).getSocialNetworkName()%></td>
                <td><%=status%></td>
                <td><img src="<%=allSoNet.get(i).getImageLocation()%>"/></td>
                <td>
                    <a href="socialNetworkEdit.jsp?soNetID=<%=allSoNet.get(i).getSocialNetworkID()%>">
                        <img src="images/tebeshir_admin_board_edit.gif">
                    </a>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <br/>
        <br/>
        <h2>New Social Network</h2>
        <form method="post" action="NewSocialNetworkServlet" enctype="multipart/form-data">
            <table border="1">
                <tr style="font-weight: bolder">
                    <td>Social Network ID - </td>
                    <td>Social Network Name - </td>
                    <td>Social Network Status - </td>
                    <td>Social Network Logo</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td style="font-size: xx-small">will be defined by the system</td>
                    <td><input type="text" name="socialNetworkName" value="Ex:Facebook"/></td>
                    <td>
                        <input type="radio" name="socialNetworkStatus" value="1">Active
                        <br/>
                        <input type="radio" name="socialNetworkStatus" value="0">Passive
                    </td>
                    <td><p style="font-size: xx-small">must be 30x30</p>
                        <br/>
                        <input type="file" name="socialNetworkLogo"/>
                    </td>
                    <td><input type="submit" name="save"/></td>
                </tr>
            </table>
        </form>
    </center>
</body>
</html>
