package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.model.ToDo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oToDoDaoTest {
    private Sql2oToDoDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString  = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        dao = new Sql2oToDoDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addToDoSetsID() throws Exception {
        ToDo todo = getToDo();
        int originalToDoId = todo.getId();

        dao.add(todo);

        assertNotEquals(originalToDoId, todo.getId());
    }

    @Test
    public void addedToDoAreReturnedFromFindAll() throws Exception {
        ToDo todo = getToDo();

        dao.add(todo);

        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void addedToDoAreFoundByFindById() throws Exception {
        ToDo todo = getToDo();

        dao.add(todo);

        ToDo foundToDo = dao.findById(todo.getId());

        assertEquals(todo, foundToDo);
    }

    @Test
    public void ToDoIsDeletedFromDB() throws Exception {
        ToDo todo = getToDo();

        int id = todo.getId();

        ToDo toDoId = dao.findById(id);

        dao.add(todo);

        dao.delete(todo);

        assertNull(toDoId);
    }

    @Test
    public void ToDoIsUpdated() throws Exception {
        ToDo todo = getToDo();

        dao.add(todo);

        int id = todo.getId();

        todo.setName("Updated Test");

        todo.setCompleted(true);

        dao.update(todo);

        ToDo updated = dao.findById(id);

        assertEquals(todo, updated);
    }

    private ToDo getToDo() {
        return new ToDo("Test", false);
    }

    @Test
    public void noToDosReturnsEmptyList() throws Exception {
        assertEquals(0, dao.findAll().size());
    }
}