/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.dao;

import com.claro.clarosmsschedule.db.DbConnection;
import com.claro.clarosmsschedule.db.DbCredential;

/**
 *
 * @author aifre
 */
public class DbConnectionDao {

    /**
     * *
     * Db Connection.
     */
    private DbConnection dbConnection;

    /**
     * *
     * Db Credential.
     */
    private final DbCredential credential;

    /**
     * *
     * Public construuctor.
     *
     * @param credential
     */
    public DbConnectionDao(DbCredential credential) {
        this.credential = credential;
    }

    /**
     * *
     * Method used to get Db connection object.
     *
     * @return
     */
    public DbConnection getDbConnection() {
        if (this.dbConnection == null) {
            this.dbConnection = new DbConnection(this.credential);
        }
        return this.dbConnection;
    }

    /**
     * *
     * Method used to Set Db Connection object.
     *
     * @param dbConnection
     */
    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
