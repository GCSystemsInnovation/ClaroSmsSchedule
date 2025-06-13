/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author aifre
 */
public class DbConnection {

    /**
     * *
     * url Db Connection.
     */
    private final String url;

    /**
     * *
     * username Db connection.
     */
    private final String username;

    /**
     * *
     * password Db connection.
     */
    private final String password;

    /**
     * *
     * Default Logger.
     */
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(DbConnection.class);

    /**
     * *
     * Default date formatter.
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault());

    /**
     * *
     * Public oonstructor.
     *
     * @param credential
     */
    public DbConnection(DbCredential credential) {
        this.url = credential.url;
        this.username = credential.username;
        this.password = credential.password;
    }

    /**
     * *
     * Public oonstructor.
     *
     * @param url
     * @param username
     * @param password
     */
    public DbConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * *
     * Method used to get db connection.
     *
     * @return
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public Connection connect() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            OracleDataSource ods = new OracleDataSource();
            ods.setUser(this.username);
            ods.setPassword(this.password);
            ods.setURL(this.url);
            return ods.getConnection();
        } catch (SQLException e) {
            LOGGER.error(DbConnection.class.getName(), formatter.format(new Date()), e);
            throw e;
        }
    }
}
