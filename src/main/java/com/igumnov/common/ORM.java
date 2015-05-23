package com.igumnov.common;

import com.igumnov.common.orm.Transaction;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        try {
            for (int i = 1; true; i++) {
                final Connection c = ds.getConnection();
                c.setAutoCommit(false);
                File.readLines(sqlFolder + "/" + i + ".sql").forEach((line) -> {
                    Statement stmt = null;
                    try {
                        stmt = c.createStatement();
                        stmt.execute(line);

                    } catch (SQLException e) {
                        // TODO Add log warning
                        e.printStackTrace();
                    } finally {
                        try {
                            if (stmt != null) stmt.close();
                        } catch (Exception e) {
                        }
                    }
                });
                c.commit();
                c.setAutoCommit(true);
                try {
                    if (c != null) c.close();
                } catch (Exception e) {
                }
            }
        } catch (NoSuchFileException e) {
            //It is last sql file
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

    public static Transaction beginTransaction() throws SQLException {
        return new Transaction(ds.getConnection());
    }

    public static ArrayList<Object> select(String query, Class objectClass) {
        return null;
    }
}
