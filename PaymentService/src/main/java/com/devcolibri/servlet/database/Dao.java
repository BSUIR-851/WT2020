package com.devcolibri.servlet.database;

import java.util.ArrayList;

public interface Dao<T> {
    public ArrayList<T> selectAll();
    public T selectOneById(int id);
    public int insert(T obj);
    public int update(T obj);
    public int deleteById(int id);
}
