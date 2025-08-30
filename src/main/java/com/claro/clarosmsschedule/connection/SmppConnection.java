package com.claro.clarosmsschedule.connection;

import com.claro.clarosmsschedule.dao.InhFactEnvioSmsBprepDao;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.DataCodings;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.SubmitSmResult;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;

/**
 *
 * @author aifre
 */
public class SmppConnection {

    /**
     * *
     * Default Logger.
     */
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(InhFactEnvioSmsBprepDao.class);

    /**
     * *
     * Default date formatter.
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault());

    /**
     * *
     * Smpp Ip.
     */
    private final String smppIp;

    /**
     * *
     * Smpp Password.
     */
    private final String password;
    /**
     * *
     * Smpp service type.
     *
     * (NULL) Default “CMT" Cellular Messaging "CPT” Cellular Paging“ “VMN”
     * Voice Mail Notification “VMA” Voice Mail Alerting “WAP” Wireless
     * Application Protocol “USSD” Unstructured Supplementary Services Data
     */
    private final String SERVICE_TYPE;

    /**
     * *
     * Smpp Port.
     */
    private final int port;

    /**
     * *
     * System Id.
     */
    private final String systemId;

    /**
     * *
     * system Type.
     */
    private final String systemType;

    /**
     * *
     *
     */
    private static final TimeFormatter TIME_FORMATTER = new AbsoluteTimeFormatter();

    /**
     * *
     * Source number.
     */
    private final String sourceNumber = "11070";

    /**
     * *
     * Smpp Session.
     */
    private SMPPSession session;

    /**
     * *
     * Public constructor to initialize the connections params.
     *
     * @param smppIp
     * @param username
     * @param password
     * @param port
     * @param systemId
     * @param systemType
     */
    public SmppConnection(String smppIp, String username, String password, int port, String systemId, String systemType) {
        this.SERVICE_TYPE = "CMT";
        this.smppIp = smppIp;
        this.password = password;
        this.port = port;
        this.systemId = systemId;
        this.systemType = systemType;
    }

    /**
     * *
     * Method is used to send message.
     *
     * @param message Mesaage to be sent.
     * @param number Destination Number.
     * @return Message Id.
     */
    public String broadcastMessage(String message, String number) {
        LOGGER.info("Broadcasting sms");

        SubmitSmResult result;
        try {
            this.session = initSession();
            if (session != null) {
                try {

                    String systemIdResult = session.connectAndBind(this.smppIp, this.port, new BindParameter(BindType.BIND_TRX, this.systemId, this.password, this.systemType, TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null), 8000);
                    LOGGER.info("Connected with SMSC with system id {}", systemIdResult);

                    result = session.submitShortMessage(this.SERVICE_TYPE,
                            TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, this.sourceNumber,
                            TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, number,
                            new ESMClass(), (byte) 0, (byte) 1, TIME_FORMATTER.format(new Date()), null,
                            new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT), (byte) 0,
                            DataCodings.ZERO, (byte) 0, message.getBytes());

                    String messageId = result.getMessageId();
                    LOGGER.info("Messages submitted, result is {}", messageId);
                    return messageId;
                } catch (PDUException | ResponseTimeoutException | InvalidResponseException | NegativeResponseException | IOException | IllegalArgumentException e) {
                    LOGGER.error(SmppConnection.class.getName(), formatter.format(new java.util.Date()), e);
                }
            } else {
                LOGGER.error("Session creation failed with SMPP broker.");
            }
        } catch (Exception e) {
            LOGGER.error(SmppConnection.class.getName(), formatter.format(new java.util.Date()), e);
        } finally {
            this.closeSmppConnection();
        }

        return null;
    }

    /**
     * *
     * Method used to initialize smpp session.
     *
     * @return
     */
    private SMPPSession initSession() {
        if (this.session == null) {
            this.session = new SMPPSession();
            this.session.setTransactionTimer(8000);
        }
        return this.session;
    }

    /**
     * *
     *
     */
    private void closeSmppConnection() {
        try {
            if (this.session != null && this.session.getSessionState().isBound()) {
                this.session.unbindAndClose();
            } else {
                this.session.close(); // cierre forzado
            }
        } catch (Exception e) {
            System.err.println("Error inesperado al cerrar sesión SMPP: " + e.getMessage());
        }

    }
}
