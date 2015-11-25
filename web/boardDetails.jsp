<%-- 
    Document   : boardDetails
    Created on : Dec 30, 2013, 10:45:27 AM
    Author     : yetkin.timocin
--%>

<%@page import="java.util.Vector"%>
<%@page import="com.tebeshir.admin.app.classes.Board"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>tebeshir admin</title>
    </head>
    <%
        Board tempBoard = new Board();
        Vector<Board> allBoards = new Vector<Board>();
        allBoards = tempBoard.getAllBoards();

        for (int i = 0; i < allBoards.size(); i++) {
            System.out.println(allBoards.get(i).getBoardName());
        }
    %>
    <body>
    <center>
        <br/>
        <h1><a href="index.jsp">tebeshir admin</a> - board details</h1>
        <br/>
        <br/>
        <table style="border: none">
            <%
                for (int i = 0; i < allBoards.size(); i++) {
            %>
            <tr>
                <td>
                    <%
                        for (int j = 0; j < allBoards.get(i).getBoardsParents().size(); j++) {
                    %>
                <td><%=allBoards.get(i).getBoardsParents().get(j).getBoardName()%></td>
                <%
                    if (j != allBoards.get(i).getBoardsParents().size() - 1) {
                %>
                <td> - </td>
                <%                                }
                    }
                %>
                <td>
                    <a href="boardEdit.jsp?boardID=<%=allBoards.get(i).getBoardID()%>">
                        <input type="image" src="images/tebeshir_admin_board_edit.gif"/>
                    </a>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </center>
</body>
</html>
