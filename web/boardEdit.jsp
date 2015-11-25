<%-- 
    Document   : boardEdit
    Created on : Jan 4, 2014, 2:30:02 PM
    Author     : yetkin.timocin
--%>

<%@page import="java.util.Vector"%>
<%@page import="com.tebeshir.admin.app.classes.Board"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>tebeshir admin - edit board</title>        
        <link href="styles/style.css" rel="stylesheet" type="text/css"/>
        <link href="styles/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="styles/plugins.css" rel="stylesheet" type="text/css"/>
        <link href="styles/style-responsive.css" rel="stylesheet" type="text/css"/>        
        <link href="styles/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="styles/style-metronic.css" rel="stylesheet" type="text/css"/>
    </head>
    <%
        int boardID = 0;
        if (request.getParameter("boardID") == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("boardDetails.jsp");
            dispatcher.forward(request, response);
        } else {
            boardID = Integer.valueOf(request.getParameter("boardID"));
        }
        System.out.println("boardID: " + boardID);
        Board board2Bedited = new Board();
        board2Bedited = board2Bedited.getCurrentBoardDetails(boardID);
    %>
    <body style="background-color: #ffffff">

    <center>
        <br/>
        <h1><a href="index.jsp">tebeshir admin</a> - <a href="boardDetails.jsp">board details</a> - edit board</h1>
        <br/>
        <br/>
        <h2><%=board2Bedited.getBoardName()%></h2>
    </center>
    <div class="row">
        <div class="col-md-12">
            <jsp:include page="boardPreview.jsp?boardID=<%=boardID%>" flush="true"/>
        </div>
    </div>
</body>
</html>
