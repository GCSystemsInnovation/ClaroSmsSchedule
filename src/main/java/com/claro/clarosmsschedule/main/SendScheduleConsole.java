/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.main;

import com.claro.clarosmsschedule.connection.SmppCredential;
import com.claro.clarosmsschedule.db.DbCredential;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author aifre
 */
public class SendScheduleConsole {

    /**
     * *
     *
     */
    private static Thread thread;

    /**
     * *
     *
     */
    private static SendScheduler sendScheduler;

    /**
     * *
     * Default Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(SendScheduleConsole.class);

    /**
     * *
     * Default date formatter.
     */
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault());

    /**
     * *
     * Main Method.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            IChangeStateProcess changeStateProcess;
            changeStateProcess = (boolean state) -> {
                if (state && thread != null) {
                    thread.interrupt();
                    thread = null;
                }
            };
            String userDir = System.getProperty("user.dir");
            String settingsPath = userDir + File.separator + "settings.properties";
            String settingsFilePath = System.getProperty("settingsFilePath", settingsPath);
            File settingsFile = new File(settingsFilePath);
            if (!settingsFile.exists()) {
                LOGGER.error("Error: El archivo de configuracino existe en la ruta: {}", new Object[]{settingsFilePath});
                return;
            }
            System.out.println("Usando archivo de configuracien: " + settingsFilePath);
            LOGGER.info("{} PROPERTIES LOAD", new Object[]{formatter.format(new Date())});
            Properties properties = new Properties();
            try (InputStream input = new FileInputStream(settingsFilePath)) {
                properties.load(input);
            }
            SmppCredential smppCredential = new SmppCredential();
            smppCredential.ipAddress = properties.getProperty("smpp.ipAddress", "127.0.0.1");
            smppCredential.port = Integer.parseInt(properties.getProperty("smpp.port", "0"));
            smppCredential.password = properties.getProperty("smpp.password", "");
            smppCredential.systemId = properties.getProperty("smpp.systemId", "");
            smppCredential.systemType = properties.getProperty("smpp.systemType", "");
            Map<String, DbCredential> credentials = new HashMap<>();
            DbCredential dbCredentialInhBroad = new DbCredential();
            dbCredentialInhBroad.url = properties.getProperty("database.svr_smoqa.url", "");
            dbCredentialInhBroad.username = properties.getProperty("database.svr_smoqa.user", "");
            dbCredentialInhBroad.password = properties.getProperty("database.svr_smoqa.password", "");
            credentials.put("INH_BROAD_KEY", dbCredentialInhBroad);
            String dbName = properties.getProperty("database.svr_smoqa.name");
            if (dbName == null || dbName.isEmpty()) {
                LOGGER.error("Error: THE DATABASE NAME WASN'T LOADED database.svr_smoqa.name");
                return;
            }
            LOGGER.info("{} LOADING PROPERTIES DATABASE {}", new Object[]{formatter.format(new Date()), dbName});
            DbCredential dbCredentialHernanMz = new DbCredential();
            dbCredentialHernanMz.url = properties.getProperty("database.datarpqa.url", "");
            dbCredentialHernanMz.username = properties.getProperty("database.datarpqa.user", "");
            dbCredentialHernanMz.password = properties.getProperty("database.datarpqa.password", "");
            credentials.put("HERNANMZ_KEY", dbCredentialHernanMz);
            dbName = properties.getProperty("database.datarpqa.name");
            if (dbName == null || dbName.isEmpty()) {
                LOGGER.error("Error: THE DATABASE NAME WASN'T LOADED database.datarpqa.name");
                return;
            }
            LOGGER.info("{} LOADING PROPERTIES DATABASE {}", new Object[]{formatter.format(new Date()), dbName});
            setSendScheduler(new SendScheduler(changeStateProcess, smppCredential, credentials));
            getThread().start();
        } catch (IOException e) {
            LOGGER.error("{} ERROR READING THE PROPERTIES FILE: {}", new Object[]{formatter.format(new Date()), e.getMessage()});
        } catch (Exception e) {
            LOGGER.error("{} UNEXPECTED ERROR: {}", new Object[]{formatter.format(new Date()), e.getMessage(), e});
        }
    }

    public static Thread getThread() {
        thread = new Thread(getSendScheduler());
        return thread;
    }

    public static void setThread(Thread thread) {
        SendScheduleConsole.thread = thread;
    }

    public static SendScheduler getSendScheduler() {
        return sendScheduler;
    }

    public static void setSendScheduler(SendScheduler sendScheduler) {
        SendScheduleConsole.sendScheduler = sendScheduler;
    }
}
