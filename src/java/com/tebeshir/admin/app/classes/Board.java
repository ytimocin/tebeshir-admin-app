/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tebeshir.admin.app.classes;

import com.tebeshir.admin.app.dao.PostgresConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yetkin.timocin
 */
public class Board {

    private int boardID;
    private String boardName;
    private String boardImageLocation;
    private int parentBoardID;
    private int boardType;
    private int boardDepth;
    private int boardStatus;
    private LinkedList<Board> boardsParents;
    private LinkedList<Board> boardsChildren;
    private LinkedList<SocialNetwork> boardSocialNetworkLinks;
    private CallableStatement callStmt;

    public static void main(String args[]) {
        try {
            Vector<Board> allBoards = new Vector<Board>();
            Board tempBoard = new Board();
            allBoards = tempBoard.getAllBoards();
            for (int i = 0; i < allBoards.size(); i++) {
                for (int j = 0; j < allBoards.get(i).getBoardsChildren().size(); j++) {
                    System.out.println(allBoards.get(i).getBoardName());
                    System.out.println(allBoards.get(i).getBoardsParents().get(j).getBoardName());
                    System.out.println(allBoards.get(i).getBoardsChildren().get(j).getBoardName());
                }
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Board(int boardID, String boardName, String boardImageLocation) {
        this.setBoardID(boardID);
        this.setBoardName(boardName);
        this.setBoardImageLocation(boardImageLocation);
    }

    public Board() {
    }

    public Vector<Board> getAllBoards() throws InstantiationException, IllegalAccessException, SQLException, Throwable {
        Vector<Board> allActiveBoards = new Vector<Board>();
        try (Connection dbConn = PostgresConnection.getConnection()) {
            dbConn.setAutoCommit(false);
            callStmt = dbConn.prepareCall("{? = call alex.pkgBoardOperations.getAllActiveBoards() }");
            callStmt.registerOutParameter(1, java.sql.Types.OTHER);
            callStmt.execute();
            try (ResultSet rs = (ResultSet) callStmt.getObject(1)) {
                while (rs.next()) {
                    Board currentBoard = new Board();
                    LinkedList<Board> currentBoardsParents = new LinkedList<Board>();
                    LinkedList<Board> currentBoardsChildren = new LinkedList<Board>();
                    currentBoard.setBoardID(rs.getInt("boardID"));
                    currentBoard.setBoardName(rs.getString("boardName"));
                    currentBoard.setParentBoardID(rs.getInt("parentBoardID"));
                    currentBoard.setBoardType(rs.getInt("boardType"));
                    currentBoard.setBoardDepth(rs.getInt("boardDepth"));
                    currentBoard.setBoardStatus(rs.getInt("boardStatus"));
                    currentBoardsParents = Board.compute(currentBoard.getBoardID(), currentBoardsParents);
                    currentBoard.setBoardsParents(currentBoardsParents);
                    currentBoardsChildren = Board.getChildren(currentBoard.getBoardID(), currentBoardsChildren);
                    currentBoard.setBoardsChildren(currentBoardsChildren);
                    allActiveBoards.add(currentBoard);
                }
                dbConn.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            callStmt.close();
            PostgresConnection.closeConnection(dbConn);
        }
        return allActiveBoards;
    }

    public static LinkedList<Board> compute(int boardId, LinkedList<Board> _initalList) throws InstantiationException, IllegalAccessException, SQLException {
        CallableStatement callStmt = null;
        ResultSet rs = null;
        Connection dbConn = PostgresConnection.getConnection();

        if (_initalList == null) {
            _initalList = new LinkedList<Board>();
        }

        try {
            dbConn.setAutoCommit(false);
            callStmt = dbConn.prepareCall("{? = call alex.pkgBoardOperations.getBoardDetails(?)}");
            callStmt.registerOutParameter(1, java.sql.Types.OTHER);
            callStmt.setInt(2, boardId);
            callStmt.execute();
            rs = (ResultSet) callStmt.getObject(1);

            if (rs.next()) {
                final int parentBoardId = rs.getInt("parentBoardID");
                final String boardName = rs.getString("boardName");
                final String imageLocation = rs.getString("imageLocation");
                if (parentBoardId != 0) {
                    compute(parentBoardId, _initalList);
                }
                _initalList.add(new Board(boardId, boardName, imageLocation));
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            PostgresConnection.closeResultSet(rs);
            PostgresConnection.closeStatement(callStmt);
            PostgresConnection.commit(dbConn);
            PostgresConnection.closeConnection(dbConn);
        }

        return _initalList;
    }

    /**
     *
     * @param boardId
     * @param _initalList
     * @return
     */
    public static LinkedList<Board> getChildren(int boardId, LinkedList<Board> _initalList) {
        Connection dbConn = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        if (_initalList == null) {
            _initalList = new LinkedList<Board>();
        }

        try {
            dbConn = PostgresConnection.getConnection();
            dbConn.setAutoCommit(false);
            callStmt = dbConn.prepareCall("{? = call alex.pkgBoardOperations.getBoardsChildren(?)}");
            callStmt.registerOutParameter(1, java.sql.Types.OTHER);
            callStmt.setInt(2, boardId);
            callStmt.execute();
            rs = (ResultSet) callStmt.getObject(1);
            while (rs.next()) {
                _initalList.add(new Board(rs.getInt("boardID"), rs.getString("boardName"), rs.getString("imageLocation")));
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            PostgresConnection.closeResultSet(rs);
            PostgresConnection.closeStatement(callStmt);
            PostgresConnection.commit(dbConn);
            PostgresConnection.closeConnection(dbConn);
        }

        return _initalList;
    }

    private LinkedList<SocialNetwork> boardsSocialNetworkLinks(int boardID) {
        LinkedList<SocialNetwork> boardsSocialNetworkLinks = new LinkedList<>();
        ResultSet rs = null;
        Connection dbConn = null;
        try {
            dbConn = PostgresConnection.getConnection();
            dbConn.setAutoCommit(false);
            callStmt = dbConn.prepareCall("{? = call alex.pkgTebeshirAdmin.boardsSocialNetworkLinks(?)}");
            callStmt.registerOutParameter(1, java.sql.Types.OTHER);
            callStmt.setInt(2, boardID);
            callStmt.execute();
            rs = (ResultSet) callStmt.getObject(1);
            while (rs.next()) {
                SocialNetwork tempSoNet = new SocialNetwork();
                tempSoNet.setSocialNetworkID(rs.getInt("socialNetworkWebSiteID"));
                tempSoNet.setSocialNetworkLinkForABoard(rs.getString("link"));
                tempSoNet.setIsVisible(rs.getInt("isVisible"));
                tempSoNet.setSocialNetworkName(rs.getString("socialNetworkName"));
                tempSoNet.setImageLocation(rs.getString("logoLocation"));
                tempSoNet.setStatus(rs.getInt("status"));
                boardsSocialNetworkLinks.add(tempSoNet);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            PostgresConnection.closeResultSet(rs);
            PostgresConnection.closeStatement(callStmt);
            PostgresConnection.commit(dbConn);
            PostgresConnection.closeConnection(dbConn);
        }
        return boardsSocialNetworkLinks;
    }

    public Board getCurrentBoardDetails(int boardID) throws InstantiationException, IllegalAccessException, SQLException {
        Board currentBoard = new Board();
        ResultSet rs = null;
        Connection dbConn = PostgresConnection.getConnection();
        try {
            dbConn.setAutoCommit(false);
            callStmt = dbConn.prepareCall("{? = call alex.pkgBoardOperations.getBoardDetails(?)}");
            callStmt.registerOutParameter(1, java.sql.Types.OTHER);
            callStmt.setInt(2, boardID);
            callStmt.execute();
            rs = (ResultSet) callStmt.getObject(1);
            while (rs.next()) {
                currentBoard.setBoardName(rs.getString("boardName"));
                currentBoard.setParentBoardID(rs.getInt("parentBoardID"));
                currentBoard.setBoardType(rs.getInt("boardType"));
                currentBoard.setBoardDepth(rs.getInt("boardDepth"));
                currentBoard.setBoardStatus(rs.getInt("boardStatus"));
                currentBoard.setBoardImageLocation(rs.getString("imageLocation"));
                LinkedList<Board> currentBoardsParents = new LinkedList<>();
                LinkedList<Board> currentBoardsChildren = new LinkedList<>();
                currentBoard.setBoardsParents(Board.compute(currentBoard.getBoardID(), currentBoardsParents));
                currentBoard.setBoardsParents(Board.getChildren(currentBoard.getBoardID(), currentBoardsChildren));
                currentBoard.setBoardSocialNetworkLinks(currentBoard.boardsSocialNetworkLinks(currentBoard.getBoardID()));
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            PostgresConnection.closeResultSet(rs);
            PostgresConnection.closeStatement(callStmt);
            PostgresConnection.commit(dbConn);
            PostgresConnection.closeConnection(dbConn);
        }
        return currentBoard;
    }

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardImageLocation() {
        return boardImageLocation;
    }

    public void setBoardImageLocation(String boardImageLocation) {
        this.boardImageLocation = boardImageLocation;
    }

    public int getParentBoardID() {
        return parentBoardID;
    }

    public void setParentBoardID(int parentBoardID) {
        this.parentBoardID = parentBoardID;
    }

    public int getBoardType() {
        return boardType;
    }

    public void setBoardType(int boardType) {
        this.boardType = boardType;
    }

    public int getBoardDepth() {
        return boardDepth;
    }

    public void setBoardDepth(int boardDepth) {
        this.boardDepth = boardDepth;
    }

    public int getBoardStatus() {
        return boardStatus;
    }

    public void setBoardStatus(int boardStatus) {
        this.boardStatus = boardStatus;
    }

    public CallableStatement getCallStmt() {
        return callStmt;
    }

    public void setCallStmt(CallableStatement callStmt) {
        this.callStmt = callStmt;
    }

    public LinkedList<Board> getBoardsParents() {
        return boardsParents;
    }

    public void setBoardsParents(LinkedList<Board> boardsParents) {
        this.boardsParents = boardsParents;
    }

    public LinkedList<Board> getBoardsChildren() {
        return boardsChildren;
    }

    public void setBoardsChildren(LinkedList<Board> boardsChildren) {
        this.boardsChildren = boardsChildren;
    }

    public LinkedList<SocialNetwork> getBoardSocialNetworkLinks() {
        return boardSocialNetworkLinks;
    }

    public void setBoardSocialNetworkLinks(LinkedList<SocialNetwork> boardSocialNetworkLinks) {
        this.boardSocialNetworkLinks = boardSocialNetworkLinks;
    }
}
