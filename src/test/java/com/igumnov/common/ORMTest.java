package com.igumnov.common;


import com.igumnov.common.orm.Transaction;
import com.igumnov.common.reflection.ReflectionException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


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
    public void testORM() throws IOException, SQLException, IllegalAccessException, ReflectionException {
        new java.io.File("tmp/sql_folder").mkdir();

        ORM.connectionPool("org.h2.Driver", "jdbc:h2:mem:test", "SA", "", 10, 30);

        File.appendLine("CREATE TABLE objectdto (id BIGINT AUTO_INCREMENT PRIMARY KEY)", "tmp/sql_folder/1.sql");
        File.appendLine("ALTER TABLE objectdto ADD name VARCHAR(255)", "tmp/sql_folder/1.sql");
        File.appendLine("ALTER TABLE objectdto ADD SALARY INT", "tmp/sql_folder/2.sql");

        ORM.applyDDL("tmp/sql_folder", "ddl_history_table_name");


        //Transaction mode
        Transaction tx = ORM.beginTransaction();
        ObjectDTO obj = new ObjectDTO();
        obj.setName("aaa");
        obj.setSalary(111);
        obj = (ObjectDTO) tx.insert(obj);
        obj.setName("ccc");
        obj.setSalary(333);
        obj = (ObjectDTO)tx.update(obj);
        tx.commit(); // Or tx.rollback();


        // Autocommit mode
        obj.setName("bbb");
        obj.setSalary(222);
        obj = (ObjectDTO) ORM.update(obj);
        ArrayList<Object> objectsFind = ORM.findBy("id > 0", ObjectDTO.class);
        ArrayList<Object> objectsSelect = ORM.select("select id, name, salary from objectdto where id > 0", ObjectDTO.class);
        ObjectDTO one =  (ObjectDTO) ORM.findOne(new Long(1), ObjectDTO.class);

    }

/*
    ORM.connectionPool("org.h2.Driver", "jdbc:h2:~/test", "user", "password", 10, 30);

    // File "sql_folder/1.sql"
    //    create table objectdto (id Long Primary Key, name varchar(255));

    // File "sql_folder/2.sql"
    //    alter table objectdto add columns (salary INT);


    ORM.applyDDL("sql_folder", "ddl_history_table_name");

    // Autocommit mode
    ObjectDTO obj = new ObjectDTO();
    obj.setName("aaa");
    obj.setSalary(111);
    obj = ORM.insert(obj);
    obj.setName("bbb");
    obj.setSalary(222);
    obj = ORM.update(obj);
    ArrayList<ObjectDTO> objectsFind = (ArrayList<ObjectDTO>) ORM.findBy("id > 0", ObjectDTO.class);
    ArrayList<ObjectDTO> objectsSelect =  (ArrayList<ObjectDTO>) ORM.select("select id, name, salary from objectdto where id > 0", ObjectDTO.class);
    ObjectDTO one =  ORM.findOne(new Long(1), ObjectDTO.class);

    //Transaction mode
    Transaction tx = ORM.beginTransaction();
    obj.setName("ccc");
    obj.setSalary(333);
    obj = tx.update(obj);
    tx.commit(); // Or tx.rollback();
*/

}
