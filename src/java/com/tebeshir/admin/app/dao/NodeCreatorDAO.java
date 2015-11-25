/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tebeshir.admin.app.dao;

import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Vector;

/**
 *
 * @author yetkin.timocin
 */
public class NodeCreatorDAO {

    private CallableStatement callStmt;

    public int createNodes(Vector<String> nodeNames, Vector<String> imageDirectories) {
        int result = 0;
        try (Connection dbConn = PostgresConnection.getConnection()) {
            callStmt = dbConn.prepareCall("{? = call alex.pkgTebeshirAdmin.addBoards(?, ?)}");
            callStmt.registerOutParameter(1, java.sql.Types.NUMERIC);
            Object[] nodeNamesObjectArray = nodeNames.toArray();
            java.sql.Array nodeNamesSqlArray = dbConn.createArrayOf("varchar", nodeNamesObjectArray);
            callStmt.setArray(2, nodeNamesSqlArray);
            Object[] imageDirectoriesObjectArray = imageDirectories.toArray();
            java.sql.Array imageDirectoriesSqlArray = dbConn.createArrayOf("varchar", imageDirectoriesObjectArray);
            callStmt.setArray(3, imageDirectoriesSqlArray);
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
