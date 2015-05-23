package com.igumnov.common;

import com.igumnov.common.orm.Transaction;
import com.igumnov.common.reflection.ReflectionException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.sql.*;
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
        Connection con = null;
        try {
            con = ds.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, ddlTableName, null);
            if (tables.next()) {


            } else {



            }

            tables.close();

        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
            }

        }

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

    public static Transaction beginTransaction() throws SQLException {
        return new Transaction(ds.getConnection());
    }



    public Object update(Object obj) throws IllegalAccessException, SQLException {
        Transaction tx = null;
        try {
            tx = ORM.beginTransaction();
            return tx.update(obj);

        } finally {
            if (tx != null) {
                tx.commit();
            }
        }

    }


    public Object insert(Object obj) throws IllegalAccessException, SQLException, ReflectionException {
        Transaction tx = null;
        try {
            tx = ORM.beginTransaction();
            return tx.insert(obj);

        } finally {
            if (tx != null) {
                tx.commit();
            }
        }

    }

    public ArrayList<Object> findBy(String where, Class classObject, Object... params) throws SQLException, IllegalAccessException, InstantiationException, ReflectionException {
        Transaction tx = null;
        try {
            tx = ORM.beginTransaction();
            return tx.findBy(where,classObject,params);

        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
    }

    public Object findOne(Class className, Object primaryKey) throws SQLException, ReflectionException, InstantiationException, IllegalAccessException {
        Transaction tx = null;
        try {
            tx = ORM.beginTransaction();
            return tx.findOne(className,primaryKey);

        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
    }

    public int deleteBy(String where, Class classObject, Object... params) throws SQLException {
        Transaction tx = null;
        try {
            tx = ORM.beginTransaction();
            return tx.deleteBy(where,classObject,params);

        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
    }

    public int delete(Object obj) throws IllegalAccessException, SQLException {
        Transaction tx = null;
        try {
            tx = ORM.beginTransaction();
            return delete(obj);

        } finally {
            if (tx != null) {
                tx.commit();
            }
        }
    }

    public ArrayList<Object> findAll(Class classObject) throws SQLException, ReflectionException, InstantiationException, IllegalAccessException {

        Transaction tx = null;
        try {
            tx = ORM.beginTransaction();
            return tx.findAll(classObject);

        } finally {
            if (tx != null) {
                tx.commit();
            }
        }

    }



}
