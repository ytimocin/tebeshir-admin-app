package com.tebeshir.admin.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class PostgresConnection {

    public static Connection getConnection() throws InstantiationException, IllegalAccessException, SQLException {
        Connection dbConn = null;
        Calendar now = Calendar.getInstance();

        try {
            Class.forName("org.postgresql.Driver").newInstance();
            dbConn = DriverManager.getConnection("jdbc:postgresql://78.47.182.14:5444/tebeshir", "enterprisedb", "enterprisedb");
        } catch (ClassNotFoundException e) {
            System.out.println("This is something you have not add in postgresql library to classpath!");
            e.printStackTrace(System.err);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("Current full date time is : "
                    + (now.get(Calendar.MONTH) + 1)
                    + "-"
                    + now.get(Calendar.DATE)
                    + "-"
                    + now.get(Calendar.YEAR)
                    + " "
                    + now.get(Calendar.HOUR_OF_DAY)
                    + ":"
                    + now.get(Calendar.MINUTE)
                    + ":"
                    + now.get(Calendar.SECOND)
                    + "."
                    + now.get(Calendar.MILLISECOND));
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            ex.printStackTrace(System.err);
        }
        return dbConn;
    }

    protected static void rollBack(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }

    public static void commit(Connection conn) {
        try {
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
}
