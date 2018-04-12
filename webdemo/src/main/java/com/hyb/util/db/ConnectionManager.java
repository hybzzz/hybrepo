package com.hyb.util.db;

import net.sf.log4jdbc.ConnectionSpy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by admin on 2018/4/8.
 */
public class ConnectionManager {
    private static ThreadLocal tl = new ThreadLocal();
    private static Connection conn = null;
    public static void BeginTrans(boolean beginTrans) throws Exception {
        if (tl.get() == null || ((Connection) tl.get()).isClosed()) {
            conn = DBConnection.getInstance().getConnection();
            if (beginTrans) {
                conn.setAutoCommit(false);
            }
            tl.set(conn);
        }
    }
    public static Connection getConnection() throws Exception {
        return (Connection) tl.get();
    }
    public static void close() throws SQLException {
        try {
            ((Connection) tl.get()).setAutoCommit(true);
        } catch (Exception e) {
        }
        ((Connection) tl.get()).close();
        tl.set(null);
    }
    public static void commit() throws SQLException {
        try {
            ((Connection) tl.get()).commit();
        } catch (Exception e) {
        }
        try {
            ((Connection) tl.get()).setAutoCommit(true);
        } catch (Exception e) {
        }
    }
    public static void rollback() throws SQLException {
        try {
            ((Connection) tl.get()).rollback();
        } catch (Exception e) {
        }
        try {
            ((Connection) tl.get()).setAutoCommit(true);
        } catch (Exception e) {
        }
    }
}
