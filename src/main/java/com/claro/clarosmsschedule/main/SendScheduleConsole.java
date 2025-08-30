/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.main;

import com.claro.clarosmsschedule.adapter.FilePropertiesReaderAdapter;
import com.claro.clarosmsschedule.adapter.IFileReaderAdapter;
import com.claro.clarosmsschedule.connection.SmppCredential;
import com.claro.clarosmsschedule.db.DbCredential;
import com.claro.clarosmsschedule.dto.ApplicationSetting;
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
     * _appApplicationSetting.
     */
    private ApplicationSetting _appApplicationSetting = null;

    /**
     * *
     * _credentials.
     */
    private final Map<String, DbCredential> _credentials;

    /**
     * *
     * startHour.
     */
    private final LocalTime startHour;

    /**
     * *
     * endHour.
     */
    private final LocalTime endHour;

    /**
     * *
     *
     * @param changeStateProcess
     * @param smppCredential
     * @param credentials
     * @param appApplicationSetting
     */
    private SendScheduleConsole(IChangeStateProcess changeStateProcess, ApplicationSetting appApplicationSetting, SmppCredential smppCredential, Map<String, DbCredential> credentials) {
        this._changeStateProcess = changeStateProcess;
        this._credentials = credentials;
        this._smppCredential = smppCredential;
        this._appApplicationSetting = appApplicationSetting;
        this.startHour = LocalTime.of(this._appApplicationSetting.startHour, 0);
        this.endHour = LocalTime.of(this._appApplicationSetting.endHour, 0);
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
            final ApplicationSetting applicationSetting = fileReaderAdapter.fillApplicationSettingFromProperties();

            SmppCredential smppCredential = fileReaderAdapter.fillSmppCredentialFromProperties();
            DbCredential dbCredentialInhBroad = fileReaderAdapter.fillDbCredentialInhBroadFromProperties();
            DbCredential dbCredentialHernanMz = fileReaderAdapter.fillDbCredentialHernanMzFromProperties();
            if (dbCredentialInhBroad != null && dbCredentialHernanMz != null) {
                final Map<String, DbCredential> credentials = new HashMap<>();
                credentials.put("INH_BROAD_KEY", dbCredentialInhBroad);
                credentials.put("HERNANMZ_KEY", dbCredentialHernanMz);

                SendScheduleConsole.timeout = TimeUnit.MINUTES.toMillis(applicationSetting.timeout);
                SendScheduleConsole.monitoringThread = new Thread(new SendScheduleConsole(changeStateProcess, applicationSetting, smppCredential, credentials));
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

    /**
     * *
     *
     */
    @Override
    public synchronized void run() {
        while (true) {
            try {

                validateApplicationStartDate();
                validateEndProccessDate();

                LOGGER.info("SendScheduleConsole__Run ---------------TIEMPO DE ESPERA---------------");
                wait(SendScheduleConsole.timeout);

            } catch (InterruptedException e) {
                LOGGER.error("{} SendScheduleConsole__Run UNEXPECTED ERROR: {}", new Object[]{formatter.format(new Date()), e.getMessage(), e});
            } finally {
                try {
                    LOGGER.info("SendScheduleConsole__Run ---------------TERMINÓ----------------");
                    this._changeStateProcess.ChangeStateProcess(true);
                    wait(100);
                } catch (InterruptedException ex) {
                    LOGGER.info("Run_Exception: {}", ex.getMessage());
                }
            }
        }
    }

    /**
     * *
     * validate Application Start Date.
     */
    private synchronized void validateApplicationStartDate() throws InterruptedException {
        if (this.isInRange() && SendScheduleConsole.smsThread == null) {

            wait(100);
            LOGGER.info("SendScheduleConsole__Run ---------------ARRANCÓ---------------");
            SendScheduler scheduler = new SendScheduler(this._changeStateProcess, this._appApplicationSetting, this._smppCredential, this._credentials);
            SendScheduleConsole.smsThread = new Thread(scheduler);
            SendScheduleConsole.smsThread.start();
        }
    }

    /**
     * *
     * validate End Proccess Date.
     */
    private synchronized void validateEndProccessDate() {
        if (!this.isInRange()
                && SendScheduleConsole.smsThread != null) {

            LOGGER.info("SendScheduleConsole__Run ---------------TERMINÓ----------------");
            this._changeStateProcess.ChangeStateProcess(true);
        }
    }

    /**
     * *
     *
     * @return
     */
    private boolean isInRange() {
        boolean isAvailable;
        LocalTime localTime = LocalTime.now();
        if (localTime.isBefore(this.endHour)) {
            // Turno diurno: 08:00 - 18:00
            isAvailable = !localTime.isBefore(this.startHour) && !localTime.isAfter(this.endHour);
        } else {
            // Turno nocturno que cruza medianoche: 19:00 - 08:00
            isAvailable = !localTime.isAfter(this.startHour) || !localTime.isBefore(this.endHour);
        }

        return isAvailable;
    }
}
