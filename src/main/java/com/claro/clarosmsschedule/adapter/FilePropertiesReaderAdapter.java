package com.claro.clarosmsschedule.adapter;

import com.claro.clarosmsschedule.connection.SmppCredential;
import com.claro.clarosmsschedule.db.DbCredential;
import com.claro.clarosmsschedule.dto.ApplicationSetting;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author aifre
 */
public class FilePropertiesReaderAdapter implements IFileReaderAdapter {

    /**
     * *
     * Default Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(FilePropertiesReaderAdapter.class);

    /**
     * *
     * Default date formatter.
     */
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault());

    /**
     * *
     *
     */
    public FilePropertiesReaderAdapter() {
    }

    /**
     * *
     * Method used to read to properties file
     *
     * @return
     */
    @Override
    public InputStream readPropertiesFile() {
        try {
            String userDir = System.getProperty("user.dir");
            String settingsPath = userDir + File.separator + "settings.properties";
            String settingsFilePath = System.getProperty("settingsFilePath", settingsPath);
            File settingsFile = new File(settingsFilePath);
            if (!settingsFile.exists()) {
                LOGGER.error("Error: El archivo de configuracino existe en la ruta: {}", new Object[]{settingsFilePath});
                return null;
            }

            return new FileInputStream(settingsFilePath);
        } catch (IOException e) {
            LOGGER.error("{}FilePropertiesReaderAdapter__readPropertiesFile UNEXPECTED ERROR: {}", new Object[]{formatter.format(new Date()), e.getMessage(), e});
        }
        return null;
    }

    /**
     * *
     * Method used to fill SmppCredential From Properties.
     *
     * @return
     */
    @Override
    public SmppCredential fillSmppCredentialFromProperties() {
        try {
            Properties properties = new Properties();
            try (InputStream input = this.readPropertiesFile()) {
                properties.load(input);
            }
            SmppCredential smppCredential = new SmppCredential();
            smppCredential.ipAddress = properties.getProperty("smpp.ipAddress", "127.0.0.1");
            smppCredential.port = Integer.parseInt(properties.getProperty("smpp.port", "0"));
            smppCredential.password = properties.getProperty("smpp.password", "");
            smppCredential.systemId = properties.getProperty("smpp.systemId", "");
            smppCredential.systemType = properties.getProperty("smpp.systemType", "");
            String dbName = properties.getProperty("database.svr_smoqa.name");
            if (dbName == null || dbName.isEmpty()) {
                LOGGER.error("FilePropertiesReaderAdapter__fillSmppCredentialFromProperties Error: THE DATABASE NAME WASN'T LOADED database.svr_smoqa.name");
                return null;
            }

            return smppCredential;

        } catch (IOException | NumberFormatException e) {
            LOGGER.error("{} FilePropertiesReaderAdapter__fillSmppCredentialFromProperties UNEXPECTED ERROR: {}", new Object[]{formatter.format(new Date()), e.getMessage(), e});
        }

        return null;
    }

    /**
     * *
     * Method used to fill DbCredential InhBroad From Properties.
     *
     * @return
     */
    @Override
    public DbCredential fillDbCredentialInhBroadFromProperties() {
        try {
            Properties properties = new Properties();
            try (InputStream input = this.readPropertiesFile()) {
                properties.load(input);
            }
            DbCredential dbCredentialInhBroad = new DbCredential();
            dbCredentialInhBroad.url = properties.getProperty("database.svr_smoqa.url", "");
            dbCredentialInhBroad.username = properties.getProperty("database.svr_smoqa.user", "");
            dbCredentialInhBroad.password = properties.getProperty("database.svr_smoqa.password", "");

            String dbName = properties.getProperty("database.datarpqa.name");
            if (dbName == null || dbName.isEmpty()) {
                LOGGER.error("FilePropertiesReaderAdapter__fillDbCredentialInhBroadFromProperties Error : THE DATABASE NAME WASN'T LOADED database.datarpqa.name");
            }

            return dbCredentialInhBroad;

        } catch (IOException | NumberFormatException e) {
            LOGGER.error("{} FilePropertiesReaderAdapter__fillDbCredentialInhBroadFromProperties UNEXPECTED ERROR: {}", new Object[]{formatter.format(new Date()), e.getMessage(), e});
        }

        return null;

    }

    /**
     * *
     * Method used to fill DbCredential HernanMz From Properties.
     *
     * @return
     */
    @Override
    public DbCredential fillDbCredentialHernanMzFromProperties() {
        try {
            Properties properties = new Properties();
            try (InputStream input = this.readPropertiesFile()) {
                properties.load(input);
            }
            DbCredential dbCredentialHernanMz = new DbCredential();
            dbCredentialHernanMz.url = properties.getProperty("database.datarpqa.url", "");
            dbCredentialHernanMz.username = properties.getProperty("database.datarpqa.user", "");
            dbCredentialHernanMz.password = properties.getProperty("database.datarpqa.password", "");
            String dbName = properties.getProperty("database.datarpqa.name");
            if (dbName == null || dbName.isEmpty()) {
                LOGGER.error("FilePropertiesReaderAdapter__fillDbCredentialHernanMzFromProperties Error: THE DATABASE NAME WASN'T LOADED database.datarpqa.name");
            }

            return dbCredentialHernanMz;

        } catch (IOException | NumberFormatException e) {
            LOGGER.error("{} FilePropertiesReaderAdapter__fillDbCredentialHernanMzFromProperties UNEXPECTED ERROR: {}", new Object[]{formatter.format(new Date()), e.getMessage(), e});
        }

        return null;

    }

    /**
     * *
     * Method used to fill ApplicationSetting From Properties.
     * 
     * @return
     */
    @Override
    public ApplicationSetting fillApplicationSettingFromProperties() {
        try {
            Properties properties = new Properties();
            try (InputStream input = this.readPropertiesFile()) {
                properties.load(input);
            }
            ApplicationSetting applicationSetting = new ApplicationSetting();
            applicationSetting.timeout = Integer.parseInt(properties.getProperty("application.timeout", "0"));
            applicationSetting.startHour = Integer.parseInt(properties.getProperty("application.start_hour", "0"));
            applicationSetting.endHour = Integer.parseInt(properties.getProperty("application.end_hour", "0"));
            applicationSetting.pagination = Integer.parseInt(properties.getProperty("application.pagination", "0"));
            String dbName = properties.getProperty("database.svr_smoqa.name");
            if (dbName == null || dbName.isEmpty()) {
                LOGGER.error("FilePropertiesReaderAdapter__fillApplicationSettingFromProperties Error: THE DATABASE NAME WASN'T LOADED database.svr_smoqa.name");
                return null;
            }

            return applicationSetting;

        } catch (IOException | NumberFormatException e) {
            LOGGER.error("{} FilePropertiesReaderAdapter__fillApplicationSettingFromProperties UNEXPECTED ERROR: {}", new Object[]{formatter.format(new Date()), e.getMessage(), e});
        }

        return null;        
    }

}
