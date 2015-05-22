package com.igumnov.common;

import com.igumnov.common.orm.Transaction;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ORM {
    private static BasicDataSource ds;

    public static void connectionPool(String driverClass, String url, String user, String password, int minPoolSize, int maxPoolSize) {
        ds = new BasicDataSource();
        ds.setDriverClassName(driverClass);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setInitialSize(minPoolSize);
        ds.setMaxTotal(maxPoolSize);

    }

    public static void applyDDL(String sqlFolder, String ddlTableName) throws java.sql.SQLException, IOException {
        Connection c = null;
        Statement stmt = null;
        Statement stmt2 = null;
        try {
            c = ds.getConnection();
            stmt = c.createStatement();
            stmt.execute(File.readAllToString(sqlFolder + "/1.sql"));
            stmt2 = c.createStatement();
            stmt2.execute(File.readAllToString(sqlFolder + "/2.sql"));
        } finally {
            try { if (stmt != null) stmt.close(); } catch(Exception e) { }
            try { if (stmt2 != null) stmt.close(); } catch(Exception e) { }
            try { if (c != null) c.close(); } catch(Exception e) { }
        }


    }

    public static Object insert(Object obj) {
        return null;
    }

    public static Object update(Object obj) {
        return null;
    }

    public static ArrayList<Object> findBy(String where, Class objectClass) {
        return null;
    }

    public static Object findOne(Long aLong, Class objectClass) {
        return null;
    }

    public static Transaction beginTransaction() {
        return null;
    }

    public static ArrayList<Object> select(String query, Class objectClass) {
        return null;
    }
}
