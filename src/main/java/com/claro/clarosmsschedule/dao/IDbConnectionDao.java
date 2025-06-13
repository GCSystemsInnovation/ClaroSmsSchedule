/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.claro.clarosmsschedule.dao;

import java.util.List;

/**
 *
 * @author aifre
 * @param <T>
 */
public interface IDbConnectionDao<T> {

    /**
     * *
     * Method used to get all entity element.
     *
     * @return
     */
    List<T> getAll();

    /**
     * *
     * Method used to get entity by Id.
     *
     * @param id
     * @return
     */
    T getById(int id);

    /**
     * *
     * Method used to add new entity.
     *
     * @param entity
     */
    void add(T entity);

    /**
     * *
     * Method used to update entity.
     *
     * @param entity
     */
    void update(T entity);

    /**
     * *
     * Method used to delete entity by Id.
     *
     * @param id
     */
    void delete(int id);

}
