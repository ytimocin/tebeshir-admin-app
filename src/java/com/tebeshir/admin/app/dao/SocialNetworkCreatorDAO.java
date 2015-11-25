/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tebeshir.admin.app.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Vector;

/**
 *
 * @author yetkin.timocin
 */
public class SocialNetworkCreatorDAO {

    private CallableStatement callStmt;

    public int addSocialNetwork(String soNetName, int soNetStatus, String soNetLogoDir, String soNetLogoDirForDB) {
        int result = 0;
        try {
            Connection dbConn = PostgresConnection.getConnection();
            callStmt = dbConn.prepareCall("{? = call alex.pkgTebeshirAdmin.addSocialNetwork(?, ?, ?)}");
            callStmt.registerOutParameter(1, java.sql.Types.NUMERIC);
            callStmt.setString(2, soNetName);
            callStmt.setInt(3, soNetStatus);
            callStmt.setString(4, soNetLogoDirForDB);
            callStmt.execute();
            result = callStmt.getBigDecimal(1).intValue();
            callStmt.close();
            PostgresConnection.closeConnection(dbConn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
