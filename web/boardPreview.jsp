<%-- 
    Document   : boardPreview
    Created on : Jan 4, 2014, 7:12:16 PM
    Author     : yetkin.timocin
--%>

<%@page import="com.tebeshir.admin.app.classes.SocialNetwork"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.tebeshir.admin.app.classes.Board"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>tebeshir board preview</title>
        <link href="styles/style.css" rel="stylesheet" type="text/css"/>
        <link href="styles/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="styles/plugins.css" rel="stylesheet" type="text/css"/>
        <link href="styles/style-responsive.css" rel="stylesheet" type="text/css"/>        
        <link href="styles/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="styles/style-metronic.css" rel="stylesheet" type="text/css"/>
    </head>
    <%
        int boardID = Integer.valueOf(request.getParameter("boardID"));
        Board currentBoard = new Board();
        currentBoard = currentBoard.getCurrentBoardDetails(boardID);
        LinkedList<Board> boardBarList = new LinkedList<Board>();
        LinkedList<Board> boardsChildren = new LinkedList<Board>();
        LinkedList<SocialNetwork> boardsSocialNetworkLinks = new LinkedList<SocialNetwork>();
        boardBarList = currentBoard.getBoardsParents();
        boardsChildren = currentBoard.getBoardsChildren();
        boardsSocialNetworkLinks = currentBoard.getBoardSocialNetworkLinks();        

        Calendar now = GregorianCalendar.getInstance();

        // board details
        // 1. board tag
        // 2. school address1
        // 3. school address2
        // 4. school telephone number
        // 5. school fax
        // 6. school facebook
        // 7. school twitter
        // 8. school instagram
        // 9. school other social media platforms
        // 10. school followers
        // 11. board post number
        // 12. board followers
        // 13. board post count

        // table #1: boardSocial

    %>
    <body>

        <div class="profil" style="min-width:990px">

            <div class="title">
                <span style="display:inline-block">
                    <h3 class="page-title">
                        <span class="name-field"><%=currentBoard.getBoardName()%></span>	<small class="tagname-field">#<input type="text" name="boardTag"></small>
                    </h3>
                </span>
            </div>

            <div class="profile-field-content">
                <div class="tagprofile-utility" style="width: 289px;border-right: 1px solid #e5e5e5;border-color: #e5e5e5;margin-right: 29px;position: relative;display:inline-block">
                    <div class="pic" style="display:inline-block"><img src="<%=currentBoard.getBoardImageLocation()%>" alt="<%=currentBoard.getBoardName()%>" class="profile-image" style="width: 138px;height: 138px;"></div>
                    <div class="links" style="display:inline-block">
                        <a href="#" data-original-title="facebook" class="social-icon facebook"></a>
                        <a href="#" data-original-title="twitter" class="social-icon twitter"></a>
                        <a href="#" data-original-title="googleplus" class="social-icon googleplus"></a>                                                                                            
                        <a href="#" data-original-title="linkedin" class="social-icon linkedin"></a>
                    </div>
                </div>

                <div class="profile-info" style="display:inline-block; vertical-align:middle">

                    <!-- PANO BİLGİLERİ -->
                    <div class="title-location text-ellipsis">
                        <h4 class="occupation"><%=currentBoard.getBoardName()%></h4>
                    </div>
                    <ul class="location-website-fields" style="list-style:none;line-height:20px; padding-left:0">
                        <li>
                            <i class="fa fa-map-marker"></i>
                            <input type="text" name="boardDetail1"/><br />
                            <a class="location-link" href="#" style="color:#494949; padding-left:20px">
                                <input type="text" name="boardDetail2"/>
                            </a>
                        </li>

                        <li>
                            <i class="fa fa-laptop"></i>
                            <a target="_blank" class="header-link-color" href="http://www.ku.edu.tr/">
                                <input type="text" name="boardDetail3"/>
                            </a>
                        </li>

                        <li class="fields">
                            <i class="fa fa-phone"></i>
                            <input type="text" name="boardDetail4"/>
                        </li>
                    </ul>

                    <div id="social-icons">
                    </div> <!-- #social-icons -->

                </div>

                <!-- PANO BİLGİLERİ EN SAĞ FOLLOWER - POST COUNT -->
                <div class="user-stats" style="display: inline-block;vertical-align: middle;right: 25px;position: absolute;text-align: left;">
                    <ul style="list-style:none; line-height:27px; padding:0">
                        <li class="user-stat user-stat-color" style="border-bottom: 1px solid #e5e5e5;">
                            <i class="fa fa-group" style="padding-right:5px"></i>Takipçileri <a href="#" class="bold" style="float:right; padding-left:10px">208179</a>
                        </li>
                        <li class="user-stat user-stat-color" style="border-bottom: 1px solid #e5e5e5;">
                            <i class="fa fa-comments" style="padding-right:5px"></i>Post Sayısı <a href="#"  class="bold" style="float:right; padding-left:10px">14519</a>
                        </li>
                    </ul>
                    <span class="tiny-text" id="member-since" style="text-transform: uppercase;margin: 18px 0 -3px;display: block;color: #999;">Member Since: <span class="join-date">Oct 23, 2012</span></span>
                </div>
                <!-- PANO BİLGİLERİ EN SAĞ FOLLOWER - POST COUNT -->

            </div>
        </div>

        <ul class="page-breadcrumb breadcrumb">
            <i class="fa fa-globe" style="padding-right:5px"></i>

            <%

                for (int j = 0; j < boardBarList.size(); j++) {

            %>
            <li>
                <a href="home.jsp?board2Bvisited=<%=boardBarList.get(j).getBoardID()%>"><%=boardBarList.get(j).getBoardName()%></a> 
                <i class="fa fa-angle-right"></i>
            </li>
            <%
                }
            %>
            <li class="pull-right">
                <div id="dashboard-report-range" class="dashboard-date-range tooltips" data-placement="top" data-original-title="Change dashboard date range" style="display: block;">
                    <i class="fa fa-calendar"></i>
                    <span><%=now.getTime()%></span>
                </div>
            </li>
        </ul>

    </body>
</html>
