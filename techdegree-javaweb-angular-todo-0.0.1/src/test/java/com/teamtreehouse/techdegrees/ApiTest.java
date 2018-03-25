package com.teamtreehouse.techdegrees;

import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oToDoDao;
import com.teamtreehouse.techdegrees.dao.ToDoDao;
import com.teamtreehouse.techdegrees.model.ToDo;
import com.teamtreehouse.techdegrees.testing.ApiClient;
import com.teamtreehouse.techdegrees.testing.ApiResponse;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ApiTest {

    public static final String PORT = "4568";
    public static final String TEST_DATASOURCE = "jdbc:h2:mem:testing";
    private Connection conn;
    private ApiClient client;
    private Gson gson;
    private Sql2oToDoDao toDoDao;

    @BeforeClass
    public static void startServer() {
        String[] args = {PORT, TEST_DATASOURCE};
        Api.main(args);
    }

    @AfterClass
    public static void stopServer() {
        Spark.stop();
    }

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o = new Sql2o(TEST_DATASOURCE + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'", "", "");
        toDoDao = new Sql2oToDoDao(sql2o);
        conn = sql2o.open();
        client = new ApiClient("http://localhost:" + PORT);
        gson = new Gson();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingToDoReturnsCreatedStatus() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("name", "Test");
        values.put("completed", true);

        ApiResponse res = client.request("POST", "/api/v1/todos", gson.toJson(values));

        assertEquals(201, res.getStatus());

    }

    @Test
    public void ToDosCanBeAccessedById() throws Exception{
        ToDo todo = getToDo();
        toDoDao.add(todo);

        ApiResponse res = client.request("GET", "/api/v1/todos/" + todo.getId());
        ToDo retrieved = toDoDao.findById(todo.getId());

        assertEquals(todo, retrieved);
    }

    @Test
    public void allToDosCanByRetrieved() throws Exception{
        ToDo todo1 = new ToDo("Test1", false);
        ToDo todo2 = new ToDo("Test2", true);

        toDoDao.add(todo1);
        toDoDao.add(todo2);

        ApiResponse res = client.request("GET", "/api/v1/todos");
        ToDo[] todos = gson.fromJson(res.getBody(), ToDo[].class);

        assertEquals(2, todos.length);

    }

    @Test
    public void deletingToDoReturnsNoContentStatus() throws Exception {
        ToDo todo = getToDo();

        toDoDao.add(todo);

        ApiResponse res = client.request("DELETE", "/api/v1/todos/" + todo.getId());

        assertEquals(204, res.getStatus());

    }

    @Test
    public void updatingTodoReturnsOKStatus() throws Exception{
        ToDo todo = getToDo();

        toDoDao.add(todo);

        Map<String, Object> values = new HashMap<>();
        values.put("name", "Test");
        values.put("completed", true);

        ApiResponse res = client.request("PUT", "/api/v1/todos/" + todo.getId(), gson.toJson(values));

        assertEquals(200, res.getStatus());

    }

    private ToDo getToDo() {
        return new ToDo("Test", false);
    }
}