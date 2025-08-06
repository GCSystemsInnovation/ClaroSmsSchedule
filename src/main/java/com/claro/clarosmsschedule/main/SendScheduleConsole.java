/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.main;

import com.claro.clarosmsschedule.adapter.FilePropertiesReaderAdapter;
import com.claro.clarosmsschedule.adapter.IFileReaderAdapter;
import com.claro.clarosmsschedule.connection.SmppCredential;
import com.claro.clarosmsschedule.db.DbCredential;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author aifre
 */
public class SendScheduleConsole implements Runnable {

    /**
     * *
     * Sms Thread.
     */
    private static Thread smsThread;

    /**
     * *
     * Monitoring Thread.
     */
    private static Thread monitoringThread;

    /**
     * *
     * send Scheduler Manager.
     */
    private static SendScheduler sendScheduler;

    /**
     * *
     * _timeout.
     */
    private static long timeout = 0;

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
     * _changeStateProcess.
     */
    private final IChangeStateProcess _changeStateProcess;

    /**
     * *
     * _smppCredential.
     */
    private final SmppCredential _smppCredential;

    /**
     * *
     * _credentials.
     */
    private final Map<String, DbCredential> _credentials;

    /**
     * *
     *
     * @param changeStateProcess
     * @param smppCredential
     * @param credentials
     */
    private SendScheduleConsole(IChangeStateProcess changeStateProcess, SmppCredential smppCredential, Map<String, DbCredential> credentials) {
        this._changeStateProcess = changeStateProcess;
        this._credentials = credentials;
        this._smppCredential = smppCredential;
    }

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
                if (state && SendScheduleConsole.smsThread != null) {
                    SendScheduleConsole.smsThread.interrupt();
                    SendScheduleConsole.smsThread = null;
                }
            };

            IFileReaderAdapter fileReaderAdapter = new FilePropertiesReaderAdapter();
            final SmppCredential smppCredential = fileReaderAdapter.fillSmppCredentialFromProperties();

            DbCredential dbCredentialInhBroad = fileReaderAdapter.fillDbCredentialInhBroadFromProperties();
            DbCredential dbCredentialHernanMz = fileReaderAdapter.fillDbCredentialHernanMzFromProperties();
            if (dbCredentialInhBroad != null && dbCredentialHernanMz != null) {
                final Map<String, DbCredential> credentials = new HashMap<>();
                credentials.put("INH_BROAD_KEY", dbCredentialInhBroad);
                credentials.put("HERNANMZ_KEY", dbCredentialHernanMz);

                SendScheduleConsole.timeout = TimeUnit.MINUTES.toMillis(smppCredential.timeout);
                SendScheduleConsole.monitoringThread = new Thread(new SendScheduleConsole(changeStateProcess, smppCredential, credentials));
                SendScheduleConsole.monitoringThread.start();
            }
        } catch (Exception e) {
            LOGGER.error("{} UNEXPECTED ERROR: {}", new Object[]{formatter.format(new Date()), e.getMessage(), e});
        }
    }

    /**
     * *
     * getSendScheduler.
     *
     * @return
     */
    public static SendScheduler getSendScheduler() {
        return sendScheduler;
    }

    /**
     * *
     * setSendScheduler.
     *
     * @param sendScheduler
     */
    public static void setSendScheduler(SendScheduler sendScheduler) {
        SendScheduleConsole.sendScheduler = sendScheduler;
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                LocalTime localTime = LocalTime.now();
                if (localTime.getHour() < this._smppCredential.startHour || (localTime.getHour() > this._smppCredential.endHour
                        && SendScheduleConsole.smsThread == null)) {

                    LOGGER.info("SendScheduleConsole__Run ---------------TIEMPO DE ESPERA---------------");
                    wait(SendScheduleConsole.timeout);

                } else if (localTime.getHour() >= this._smppCredential.startHour && localTime.getHour() < this._smppCredential.endHour
                        && SendScheduleConsole.smsThread == null) {

                    LOGGER.info("SendScheduleConsole__Run ---------------ARRANCÓ---------------");
                    SendScheduleConsole.smsThread = new Thread(new SendScheduler(this._changeStateProcess, this._smppCredential, this._credentials));
                    SendScheduleConsole.smsThread.start();
                    
                } else if (localTime.getHour() > this._smppCredential.endHour
                        && SendScheduleConsole.smsThread != null) {

                    LOGGER.info("SendScheduleConsole__Run ---------------TERMINÓ----------------");
                    this._changeStateProcess.ChangeStateProcess(true);
                }
            } catch (InterruptedException e) {
                LOGGER.error("{} SendScheduleConsole__Run UNEXPECTED ERROR: {}", new Object[]{formatter.format(new Date()), e.getMessage(), e});
            }
        }
    }
}
