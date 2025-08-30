package com.claro.clarosmsschedule.main;

import com.claro.clarosmsschedule.utils.Utils;
import com.claro.clarosmsschedule.connection.SmppConnection;
import com.claro.clarosmsschedule.connection.SmppCredential;
import com.claro.clarosmsschedule.dao.CtrlBroadDwhDao;
import com.claro.clarosmsschedule.dao.InhFactEnvioSmsBprepDao;
import com.claro.clarosmsschedule.db.DbCredential;
import com.claro.clarosmsschedule.dto.ApplicationSetting;
import com.claro.clarosmsschedule.model.CtrlBroadDwh;
import com.claro.clarosmsschedule.model.InhFactEnvioSmsBprep;
import com.claro.clarosmsschedule.dto.SmsUserDto;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author aifre
 */
public class SendScheduler implements Runnable {

    /**
     * *
     * True if process is alive.
     */
    public volatile boolean isAlive = false;

    /**
     * *
     * Default Logger.
     */
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(SendScheduler.class);

    /**
     * *
     * Default date formatter.
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault());

    /**
     * *
     * ctrlBroadDwh Dao.
     */
    private CtrlBroadDwhDao ctrlBroadDwhDao;

    /**
     * *
     * envio Sms Bprep Dao.
     */
    private InhFactEnvioSmsBprepDao envioSmsBprepDao;

    /**
     * *
     * envio Sms Desc Jpa Controller.
     */
    private SmppConnection smppConnection;

    /**
     * *
     *
     */
    private final IChangeStateProcess changeStateProcess;

    /**
     * *
     * _appApplicationSetting.
     */
    private final ApplicationSetting appApplicationSetting;

    /**
     * *
     * FILE_MESSAGE_PROCESSED.
     */
    private static final String FILE_MESSAGE_PROCESSED = "last_processed.txt";

    /**
     * *
     * Public constructor.
     *
     * @param changeStateProcess
     * @param smppCredential Smpp credentials.
     * @param dbCredentials Database credentials.
     * @param appApplicationSetting .
     */
    public SendScheduler(IChangeStateProcess changeStateProcess, ApplicationSetting appApplicationSetting,
            SmppCredential smppCredential, Map<String, DbCredential> dbCredentials) {
        this.changeStateProcess = changeStateProcess;
        this.appApplicationSetting = appApplicationSetting;
        this.getCtrlBroadDwhDao(dbCredentials.get(Utils.DB_CONNECTION_INH_BROAD_KEY));
        this.getInhFactEnvioSmsBprepDao(dbCredentials.get(Utils.DB_CONNECTION_HERNANMZ_KEY));
        this.getSmppConnection(smppCredential);
    }

    /**
     *
     */
    @Override
    public synchronized void run() {
        try {
            LOGGER.info("{} STARTED PROCESS", formatter.format(new Date()));
            this.isAlive = true;
            int pagination = 0;
            while (this.isAlive) {
                CtrlBroadDwh schedule = this.ctrlBroadDwhDao.getMaxSchedule();
                if (schedule != null) {
                    LOGGER.info("{} PROCESS INITIATED BY THE EXECUTION DATE: {}", formatter.format(new Date()),
                            schedule.getFechaEjecucion());

                    List<SmsUserDto> userList;
                    String last = Utils.readLastMessageIdProcessed(FILE_MESSAGE_PROCESSED);
                    int index = (last != null && !last.isEmpty()) ? Integer.parseInt(last) + 1 : 0;
                    if (index > 0) {
                        userList = this.envioSmsBprepDao.getUserListFromIndex(schedule.getFechaEjecucion(),
                                pagination, appApplicationSetting.pagination, index);
                    } else {
                        userList = this.envioSmsBprepDao.getUserList(schedule.getFechaEjecucion(),
                                pagination, appApplicationSetting.pagination);
                    }

                    if (userList != null && !userList.isEmpty()) {
                        LOGGER.info(" ------PAGE RANGE-------:{} - {}", pagination, appApplicationSetting.pagination);
                        for (SmsUserDto smsUserDto : userList) {
                            String messageId = "1";//this.smppConnection.broadcastMessage(smsUserDto.getDescSms(),
                                    //smsUserDto.getnMin());
                            wait(100);
                            if (messageId != null && !messageId.isEmpty()) {
                                InhFactEnvioSmsBprep bprep = new InhFactEnvioSmsBprep();
                                bprep.setFechaEnvio(formatter.format(new Date()));
                                bprep.setIntentos(smsUserDto.getRetry().add(BigInteger.valueOf(1)));
                                bprep.setIdSequencia(smsUserDto.getInhFactEnvioSmsBprep_Id());
                                bprep.setEstadoSms(ESmsState.SENT_SMS.getNumber());
                                bprep.setCscSmsc(messageId);
                                boolean userUpdated = this.envioSmsBprepDao.updateUser(bprep);
                                if (userUpdated) {
                                    LOGGER.info("{} MESSAGE SENT TO NUMBER: {}", formatter.format(new Date()),
                                            smsUserDto.getnMin());
                                }

                                String sequenceId = smsUserDto.getInhFactEnvioSmsBprep_Id().toString();
                                Utils.saveLastMessageIdProcessed(FILE_MESSAGE_PROCESSED, sequenceId);
                                LOGGER.info("{} SAVE LAST PROCESSED", formatter.format(new Date()));

                            } else {
                                LOGGER.info("{} MESSAGE WASN´T SEND", formatter.format(new Date()));
                                LOGGER.info("{} PROCESS SUCCESSFULL", formatter.format(new Date()));
                                Utils.saveLastMessageIdProcessed(FILE_MESSAGE_PROCESSED, "");
                                LOGGER.info("{} CLEAN LAST PROCESSED", formatter.format(new Date()));
                            }
                        }

                        if (schedule.getEstado() == null) {
                            schedule.setEstado(BigDecimal.ZERO);
                        }
                        schedule.setFechaActualizacion((new Date()).toString());
                        schedule.setEstado(schedule.getEstado().add(BigDecimal.ONE));
                        boolean updated = this.ctrlBroadDwhDao.updateScheduler(schedule);
                        if (updated) {
                            LOGGER.info("{} SCHEDULE FOR RUN DATE UPDATED: {}", formatter.format(new Date()),
                                    schedule.getFechaEjecucion());
                        }

                        pagination = pagination + this.appApplicationSetting.pagination;
                    } else {
                        this.isAlive = false;
                        LOGGER.info("{} MESSAGE WASN´T SEND", formatter.format(new Date()));
                        LOGGER.info("{} PROCESS SUCCESSFULL", formatter.format(new Date()));
                        Utils.saveLastMessageIdProcessed(FILE_MESSAGE_PROCESSED, "");
                        LOGGER.info("{} CLEAN LAST PROCESSED", formatter.format(new Date()));
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(SendScheduler.class.getName(), formatter.format(new Date()), ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SendScheduler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.isAlive = false;
            this.changeStateProcess.ChangeStateProcess(true);
        }
    }

    /**
     * *
     *
     * @param smppCredential
     * @return
     */
    private SmppConnection getSmppConnection(SmppCredential smppCredential) {
        if (this.smppConnection == null) {
            this.smppConnection = new SmppConnection(smppCredential.ipAddress, smppCredential.userName,
                    smppCredential.password, smppCredential.port, smppCredential.systemId, smppCredential.systemType);
        }

        return this.smppConnection;
    }

    /**
     * *
     *
     * @param hernanmzCredentials
     * @return
     */
    private InhFactEnvioSmsBprepDao getInhFactEnvioSmsBprepDao(DbCredential hernanmzCredentials) {
        if (this.envioSmsBprepDao == null) {
            this.envioSmsBprepDao = new InhFactEnvioSmsBprepDao(hernanmzCredentials);
        }
        return this.envioSmsBprepDao;
    }

    /**
     * *
     *
     * @param hernanmzCredentials
     * @return
     */
    private CtrlBroadDwhDao getCtrlBroadDwhDao(DbCredential inhBroadCredentials) {
        if (this.ctrlBroadDwhDao == null) {
            this.ctrlBroadDwhDao = new CtrlBroadDwhDao(inhBroadCredentials);
        }
        return this.ctrlBroadDwhDao;
    }
}
