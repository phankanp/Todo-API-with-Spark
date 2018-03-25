package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.ToDo;

import java.util.List;

public interface ToDoDao {
    void add(ToDo todo) throws DaoException;

    void delete(ToDo todo) throws DaoException;

    void update(ToDo todo) throws DaoException;

    ToDo findById(int id);

    List<ToDo> findAll();
}
