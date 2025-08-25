package com.claro.clarosmsschedule.main;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
    private final CtrlBroadDwhDao ctrlBroadDwhDao;

    /**
     * *
     * envio Sms Bprep Dao.
     */
    private final InhFactEnvioSmsBprepDao envioSmsBprepDao;

    /**
     * *
     * envio Sms Desc Jpa Controller.
     */
    private final SmppConnection smppConnection;

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
     * Public constructor.
     *
     * @param changeStateProcess
     * @param smppCredential Smpp credentials.
     * @param dbCredentials Database credentials.
     * @param appApplicationSetting .
     */
    public SendScheduler(IChangeStateProcess changeStateProcess, ApplicationSetting appApplicationSetting, SmppCredential smppCredential, Map<String, DbCredential> dbCredentials) {
        this.changeStateProcess = changeStateProcess;
        this.appApplicationSetting = appApplicationSetting;
        this.ctrlBroadDwhDao = new CtrlBroadDwhDao(dbCredentials.get(Utils.DB_CONNECTION_INH_BROAD_KEY));
        this.envioSmsBprepDao = new InhFactEnvioSmsBprepDao(dbCredentials.get(Utils.DB_CONNECTION_HERNANMZ_KEY));
        this.smppConnection = new SmppConnection(smppCredential.ipAddress, smppCredential.userName,
                smppCredential.password, smppCredential.port, smppCredential.systemId, smppCredential.systemType);
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            LOGGER.info("{} STARTED PROCESS", formatter.format(new Date()));
            this.isAlive = true;
            int pagination = 0;
            while (this.isAlive) {
                CtrlBroadDwh schedule = this.ctrlBroadDwhDao.getMaxSchedule();
                if (schedule != null) {
                    LOGGER.info("{} PROCESS INITIATED BY THE EXECUTION DATE: {}", formatter.format(new Date()), schedule.getFechaEjecucion());
                    List<SmsUserDto> userList = this.envioSmsBprepDao.getUserList(schedule.getFechaEjecucion(), pagination, appApplicationSetting.pagination);
                    if (userList != null && !userList.isEmpty()) {
                        for (SmsUserDto smsUserDto : userList) {
                            String messageId = this.smppConnection.broadcastMessage(smsUserDto.getDescSms(), smsUserDto.getnMin());
                            InhFactEnvioSmsBprep bprep = new InhFactEnvioSmsBprep();
                            bprep.setFechaEnvio(formatter.format(new Date()));
                            bprep.setIntentos(smsUserDto.getRetry().add(BigInteger.valueOf(1)));
                            bprep.setIdSequencia(smsUserDto.getInhFactEnvioSmsBprep_Id());
                            bprep.setEstadoSms(messageId != null && !messageId.isEmpty() ? ESmsState.SENT_SMS.getNumber() : ESmsState.PENDING_SMS.getNumber());
                            bprep.setCscSmsc(messageId != null && !messageId.isEmpty() ? messageId : null);
                            boolean userUpdated = this.envioSmsBprepDao.updateUser(bprep);
                            if (userUpdated) {
                                LOGGER.info("{} MESSAGE SENT TO NUMBER: {}", formatter.format(new Date()), smsUserDto.getnMin());
                            }
                        }

                        if (schedule.getEstado() == null) {
                            schedule.setEstado(BigDecimal.ZERO);
                        }
                        schedule.setFechaActualizacion((new Date()).toString());
                        schedule.setEstado(schedule.getEstado().add(BigDecimal.ONE));
                        boolean updated = this.ctrlBroadDwhDao.updateScheduler(schedule);
                        if (updated) {
                            LOGGER.info("{} SCHEDULE FOR RUN DATE UPDATED: {}", formatter.format(new Date()), schedule.getFechaEjecucion());
                        }

                        pagination = pagination + this.appApplicationSetting.pagination;
                    }

                    this.isAlive = false;

                }
            }
        } catch (SQLException ex) {
            LOGGER.error(SendScheduler.class.getName(), formatter.format(new Date()), ex);
        } finally {
            this.isAlive = false;
            this.changeStateProcess.ChangeStateProcess(true);
        }
    }

}
