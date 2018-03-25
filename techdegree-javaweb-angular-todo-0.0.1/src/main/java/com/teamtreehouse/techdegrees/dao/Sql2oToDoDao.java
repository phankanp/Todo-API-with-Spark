package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.ToDo;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oToDoDao implements ToDoDao {
    private final Sql2o sql2o;

    public Sql2oToDoDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(ToDo todo) throws DaoException {
        String sql = "INSERT INTO todos(name, completed) VALUES(:name, :completed)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(todo)
                    .executeUpdate()
                    .getKey();
            todo.setId(id);
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem adding Todo");
        }
    }

    @Override
    public void delete(ToDo todo) throws DaoException {
        String sql = "DELETE FROM todos WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", todo.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem deleting the Todo");
        }
    }

    @Override
    public void update(ToDo todo) throws DaoException {
        String sql = "UPDATE todos SET name = :name, completed = :completed WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .bind(todo)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem updating Todo");
        }
    }

    @Override
    public ToDo findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("Select * FROM todos WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(ToDo.class);
        }

    }

    @Override
    public List<ToDo> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM todos")
                    .executeAndFetch(ToDo.class);
        }
    }
}
