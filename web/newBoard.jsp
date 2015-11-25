<%-- 
    Document   : test
    Created on : Dec 11, 2013, 6:14:05 PM
    Author     : yetkin.timocin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>tebeshir admin</title>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
        <script type="text/javascript">
            $(function() {
                var scntDiv = $('#p_scents');
                var i = $('#p_scents p').size() + 1;

                $('#addScnt').live('click', function() {
                    $('<p><label for="p_scnts"><input type="text" id="p_scnt" size="20" name="p_scnt_' + i + '" value="" placeholder="Input Value" /></label><input type="file" name="photo" size="50" /><a href="#" id="remScnt">Remove</a></p>').appendTo(scntDiv);
                    i++;
                    return false;
                });

                $('#remScnt').live('click', function() {
                    if (i > 2) {
                        $(this).parents('p').remove();
                        i--;
                    }
                    return false;
                });
            });
        </script>
    </head>
    <body>
    <center>
        <br/>
        <h1><a href="index.jsp">tebeshir admin</a> - new board</h1>
        <br/>
        <br/>
        <form method="post" action="NewBoardServlet" enctype="multipart/form-data">
            <div id="p_scents">
                <p>
                    <label for="p_scnts">
                        <input type="text" id="p_scnt" size="20" name="p_scnt" value="" placeholder="Input Value" />
                        <input type="file" name="nodePhoto" size="50" />
                    </label>
                </p>
            </div>
            <h2><a href="#" id="addScnt">Add Another Node</a></h2>
            <input type="submit" value="gönder"/>
        </form>
    </center>
</body>
</html>