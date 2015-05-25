package com.igumnov.common;

import com.igumnov.common.dependency.DependencyException;
import com.igumnov.common.orm.DDLHistory;
import com.igumnov.common.orm.Transaction;
import com.igumnov.common.reflection.ReflectionException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.sql.*;
import java.util.ArrayList;

public class ORM {
    private static BasicDataSource ds;

    public static void connectionPool(String driverClass, String url, String user, String password, int minPoolSize, int maxPoolSize) throws DependencyException {
        ds = new BasicDataSource();
        ds.setDriverClassName(driverClass);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setInitialSize(minPoolSize);
        ds.setMaxTotal(maxPoolSize);
        Dependency.bind("dataSource", ds);

    }

    public static void applyDDL(String sqlFolder) throws java.sql.SQLException, IOException, ReflectionException, IllegalAccessException, InstantiationException {
        Connection con = null;
        int i=1;
        ResultSet tables=null;
        try {
            con = ds.getConnection();
            DatabaseMetaData dbm = con.getMetaData();
            tables = dbm.getTables(null, null, "DDLHISTORY", null);
            if (tables.next()) {
                ArrayList<Object> ret = findBy("true order by id limit 1", DDLHistory.class);
                i = 1 + ((DDLHistory)ret.get(0)).getId();
            } else {

                Statement stmt=null;
                try {
                    stmt = con.createStatement();
                    stmt.execute("CREATE TABLE DDLHistory (id INT PRIMARY KEY, applyDate DATE)");
                } finally {
                    if(stmt!=null) {
                        try {
                            stmt.close();
                        } catch (Exception e) {}
                    }
                }


            }

            tables.close();

        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
            }
            try {
                if (tables != null) tables.close();
            } catch (Exception e) {
            }

        }

        try {
            for (; true; i++) {
                Connection c=ds.getConnection();
                try {
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
                    DDLHistory ddl = new DDLHistory();
                    ddl.setId(i);
                    ddl.setApplyDate(new java.util.Date());
                    insert(ddl);
                } finally {
                    try {
                        if (c != null) {
                            c.setAutoCommit(true);
                            c.close();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (NoSuchFileException e) {
            //It is last sql file
        }

    }

    public static Transaction beginTransaction() throws SQLException {
        return new Transaction(ds.getConnection());
    }



    public static Object update(Object obj) throws IllegalAccessException, SQLException {
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


    public static Object insert(Object obj) throws IllegalAccessException, SQLException, ReflectionException {
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

    public static ArrayList<Object> findBy(String where, Class classObject, Object... params) throws SQLException, IllegalAccessException, InstantiationException, ReflectionException {
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

    public static Object findOne(Class className, Object primaryKey) throws SQLException, ReflectionException, InstantiationException, IllegalAccessException {
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

    public static int deleteBy(String where, Class classObject, Object... params) throws SQLException {
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

    public static int delete(Object obj) throws IllegalAccessException, SQLException {
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

    public static ArrayList<Object> findAll(Class classObject) throws SQLException, ReflectionException, InstantiationException, IllegalAccessException {

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
