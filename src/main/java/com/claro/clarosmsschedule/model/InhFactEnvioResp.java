/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author aifre
 */
public class InhFactEnvioResp implements Serializable {

    /**
     * *
     * Serial verion UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * *
     * sequence id.
     */
    private BigDecimal idSequencia;

    /**
     * *
     * send date.
     */
    private String fechaEnvio;

    /**
     * *
     * cscSmsc.
     */
    private String cscSmsc;

    /**
     * *
     * command status.
     */
    private BigInteger commandStatus;

    /**
     * *
     * date confirmation.
     */
    private String fechaConfirmacion;

    /**
     * *
     * error code.
     */
    private BigInteger errorCode;

    /**
     * *
     * Public constructor.
     */
    public InhFactEnvioResp() {
    }

    /**
     * *
     * Method used to get sequence Id.
     *
     * @return
     */
    public BigDecimal getIdSequencia() {
        return idSequencia;
    }

    /**
     * *
     * Method used to set sequence id.
     *
     * @param idSequencia
     */
    public void setIdSequencia(BigDecimal idSequencia) {
        this.idSequencia = idSequencia;
    }

    /**
     * *
     * Method used to get send date.
     *
     * @return
     */
    public String getFechaEnvio() {
        return fechaEnvio;
    }

    /**
     * *
     * Method used to set send date.
     *
     * @param fechaEnvio
     */
    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    /**
     * *
     * Method used to get csc smsc.
     *
     * @return
     */
    public String getCscSmsc() {
        return cscSmsc;
    }

    /**
     * *
     * Method used to set csc smsc.
     *
     * @param cscSmsc
     */
    public void setCscSmsc(String cscSmsc) {
        this.cscSmsc = cscSmsc;
    }

    /**
     * *
     * Method used to get command status.
     *
     * @return
     */
    public BigInteger getCommandStatus() {
        return commandStatus;
    }

    /**
     * *
     * Method used to set command status.
     *
     * @param commandStatus
     */
    public void setCommandStatus(BigInteger commandStatus) {
        this.commandStatus = commandStatus;
    }

    /**
     * *
     * Method used to get confirmation date.
     *
     * @return
     */
    public String getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    /**
     * *
     * Method used to set confirmation date.
     *
     * @param fechaConfirmacion
     */
    public void setFechaConfirmacion(String fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    /**
     * *
     * Method used to get error code.
     *
     * @return
     */
    public BigInteger getErrorCode() {
        return errorCode;
    }

    /**
     * *
     * Method used to set error code.
     *
     * @param errorCode
     */
    public void setErrorCode(BigInteger errorCode) {
        this.errorCode = errorCode;
    }

}
