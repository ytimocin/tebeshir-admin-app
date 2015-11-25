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
public class SocialNetwork {

    private int socialNetworkID;
    private String socialNetworkName;
    private int status;
    private String imageLocation;
    private String socialNetworkLinkForABoard;
    private int isVisible;

    public Vector<SocialNetwork> allSocialNetworks() {
        Vector<SocialNetwork> allSocialNetworks = new Vector<SocialNetwork>();
        try {
            Connection dbConn = PostgresConnection.getConnection();
            CallableStatement callStmt = null;
            dbConn.setAutoCommit(false);
            callStmt = dbConn.prepareCall("{? = call alex.pkgTebeshirAdmin.allSocialNetworks() }");
            callStmt.registerOutParameter(1, java.sql.Types.OTHER);
            callStmt.execute();
            ResultSet rs = (ResultSet) callStmt.getObject(1);
            while (rs.next()) {
                SocialNetwork tempSoNet = new SocialNetwork();
                tempSoNet.setSocialNetworkID(rs.getInt("socialNetworkWebSiteID"));
                tempSoNet.setSocialNetworkName(rs.getString("socialNetworkWebSiteName"));
                tempSoNet.setImageLocation(rs.getString("logoLocation"));
                tempSoNet.setStatus(rs.getInt("status"));
                allSocialNetworks.add(tempSoNet);
            }
            rs.close();
            dbConn.commit();
            callStmt.close();
            PostgresConnection.closeConnection(dbConn);

        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(SocialNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allSocialNetworks;
    }

    public SocialNetwork getSocialNetworkByID(int soNetID) {
        SocialNetwork tempSoNet = new SocialNetwork();
        try {
            Connection dbConn = PostgresConnection.getConnection();
            CallableStatement callStmt = null;
            dbConn.setAutoCommit(false);
            callStmt = dbConn.prepareCall("{? = call alex.pkgTebeshirAdmin.getSocialNetworkByID(?) }");
            callStmt.registerOutParameter(1, java.sql.Types.OTHER);
            callStmt.setInt(2, soNetID);
            callStmt.execute();
            ResultSet rs = (ResultSet) callStmt.getObject(1);
            while (rs.next()) {
                tempSoNet.setSocialNetworkID(soNetID);
                tempSoNet.setSocialNetworkName(rs.getString("socialNetworkWebSiteName"));
                tempSoNet.setImageLocation(rs.getString("logoLocation"));
                tempSoNet.setStatus(rs.getInt("status"));
            }
            rs.close();
            dbConn.commit();
            callStmt.close();
            PostgresConnection.closeConnection(dbConn);
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(SocialNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempSoNet;
    }

    public int getSocialNetworkID() {
        return socialNetworkID;
    }

    public void setSocialNetworkID(int socialNetworkID) {
        this.socialNetworkID = socialNetworkID;
    }

    public String getSocialNetworkName() {
        return socialNetworkName;
    }

    public void setSocialNetworkName(String socialNetworkName) {
        this.socialNetworkName = socialNetworkName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getSocialNetworkLinkForABoard() {
        return socialNetworkLinkForABoard;
    }

    public void setSocialNetworkLinkForABoard(String socialNetworkLinkForABoard) {
        this.socialNetworkLinkForABoard = socialNetworkLinkForABoard;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }
}
