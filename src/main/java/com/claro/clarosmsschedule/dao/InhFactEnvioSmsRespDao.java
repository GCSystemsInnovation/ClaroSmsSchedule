/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.dao;

import com.claro.clarosmsschedule.db.DbCredential;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author aifre
 */
public class InhFactEnvioSmsRespDao extends DbConnectionDao {

    /**
     * *
     * Default Logger.
     */
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(InhFactEnvioSmsRespDao.class);

    /**
     * *
     * Default date formatter.
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault());

    /**
     * *
     * Public constructor.
     *
     * @param credential
     */
    public InhFactEnvioSmsRespDao(DbCredential credential) {
        super(credential);
    }

}
