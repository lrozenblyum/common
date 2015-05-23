package com.igumnov.common;


import com.igumnov.common.orm.Transaction;
import com.igumnov.common.reflection.ReflectionException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class ORMTest {

    @Before
    public void before() {
        java.io.File d = new java.io.File("tmp");
        if (!d.exists()) {
            d.mkdir();
        } else {
            Folder.deleteWithContent("tmp");
            d.mkdir();
        }
    }

    @Test
    public void testORM() throws IOException, SQLException, IllegalAccessException, ReflectionException, InstantiationException {
        new java.io.File("tmp/sql_folder").mkdir();

        ORM.connectionPool("org.h2.Driver", "jdbc:h2:mem:test", "SA", "", 10, 30);

        File.appendLine("CREATE TABLE ObjectDTO (id BIGINT AUTO_INCREMENT PRIMARY KEY)", "tmp/sql_folder/1.sql");
        File.appendLine("ALTER TABLE ObjectDTO ADD name VARCHAR(255)", "tmp/sql_folder/1.sql");
        File.appendLine("ALTER TABLE ObjectDTO ADD SALARY INT", "tmp/sql_folder/2.sql");

        ORM.applyDDL("tmp/sql_folder", "ddl_history_table_name");


        //Transaction mode
        Transaction tx = null;
        try {
            tx = ORM.beginTransaction();
            ObjectDTO obj = new ObjectDTO();
            obj.setName("aaa");
            obj.setSalary(111);
            obj = (ObjectDTO) tx.insert(obj);
            ObjectDTO object1 = (ObjectDTO) tx.findOne(ObjectDTO.class, new Long(1));
            assertEquals(object1.getName(), "aaa");
            obj.setName("ccc");
            obj.setSalary(333);
            obj = (ObjectDTO) tx.update(obj);
            ObjectDTO object2 = (ObjectDTO) tx.findOne(ObjectDTO.class, new Long(1));
            assertEquals(object2.getName(), "ccc");
        } finally {
            if (tx != null) {
                tx.commit(); 
            }
        }


        try {
            tx = ORM.beginTransaction();
            ArrayList<Object> objectsFind = tx.findBy("id > ?", ObjectDTO.class, new Long(0));
            assertEquals(objectsFind.size(), 1);
            int affectedRecords = tx.deleteBy("id > ?", ObjectDTO.class, new Long(0));
            assertEquals(affectedRecords,1);
            ArrayList<Object> objectsFind2 = tx.findBy("id > ?", ObjectDTO.class, new Long(0));
            assertEquals(objectsFind2.size(), 0);

        } finally {
            if (tx != null) {
                tx.commit(); 
            }
        }


        try {
            tx = ORM.beginTransaction();
            ObjectDTO o = new ObjectDTO();
            o.setName("2");
            o.setSalary(0);
            o = (ObjectDTO) tx.insert(o);
            ArrayList<Object> objectsFind = tx.findAll(ObjectDTO.class);
            assertEquals(objectsFind.size(), 1);
            int affectedRecords = tx.delete(o);
            assertEquals(affectedRecords,1);
            ArrayList<Object> objectsFind2 = tx.findBy("id > ?", ObjectDTO.class, new Long(0));
            assertEquals(objectsFind2.size(), 0);

        } finally {
            if (tx != null) {
                tx.commit(); 
            }
        }


        try {
            tx = ORM.beginTransaction();
            ObjectDTO o = new ObjectDTO();
            o.setName("2");
            o.setSalary(0);
            o = (ObjectDTO) tx.insert(o);
            ArrayList<Object> objectsFind = tx.findAll(ObjectDTO.class);
            assertEquals(objectsFind.size(), 1);


        } finally {
            if (tx != null) {
                tx.rollback();
            }
        }

        try {
            tx = ORM.beginTransaction();
            ArrayList<Object> objectsFind = tx.findAll(ObjectDTO.class);
            assertEquals(objectsFind.size(), 0);


        } finally {
            if (tx != null) {
                tx.commit();
            }
        }



    }


}
