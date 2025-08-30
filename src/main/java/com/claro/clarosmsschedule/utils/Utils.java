package com.claro.clarosmsschedule.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author aifre
 */
public class Utils {

    /**
     * *
     * Variable used to identify hernanmz connection.
     */
    public static final String DB_CONNECTION_HERNANMZ_KEY = "HERNANMZ_KEY";

    /**
     * *
     * Variable used to identify inh broad connection.
     */
    public static final String DB_CONNECTION_INH_BROAD_KEY = "INH_BROAD_KEY";

    /**
     * *
     * Default Logger.
     */
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Utils.class);

    /**
     * *
     * Default date formatter.
     */
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault());

    /**
     * *
     * Read Last MessageId Processed.
     *
     * @param path
     * @return
     */
    public static String readLastMessageIdProcessed(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return "";
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String valor = reader.readLine();
            return valor;
        } catch (IOException e) {
            LOGGER.error(Utils.class.getName(), formatter.format(new Date()), e);
        }

        return null;
    }

    /**
     * *
     * Save Last MessageId Processed.
     *
     * @param path
     * @param valor
     */
    public static void saveLastMessageIdProcessed(String path, String valor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(valor);
        } catch (IOException e) {
            LOGGER.error(Utils.class.getName(), formatter.format(new Date()), e);
        }
    }
}
