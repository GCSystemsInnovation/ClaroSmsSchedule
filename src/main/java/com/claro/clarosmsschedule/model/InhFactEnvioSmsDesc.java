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
public class InhFactEnvioSmsDesc implements Serializable {

    /**
     * *
     * Serial verion UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * *
     * Sequence Id.
     */
    private BigDecimal idSequencia;

    /**
     * *
     * Sms code.
     */
    private BigInteger codigoSms;

    /**
     * *
     * Sms Description.
     */
    private String descSms;

    /**
     * *
     * Modified date.
     */
    private String fechaMod;

    /**
     * *
     * Method used to get sequence id.
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
     * Method used to get sms code.
     *
     * @return
     */
    public BigInteger getCodigoSms() {
        return codigoSms;
    }

    /**
     * *
     * Method used to set sms code.
     *
     * @param codigoSms
     */
    public void setCodigoSms(BigInteger codigoSms) {
        this.codigoSms = codigoSms;
    }

    /**
     * *
     * Method used to get sms description.
     *
     * @return
     */
    public String getDescSms() {
        return descSms;
    }

    /**
     * *
     * Method used to set sms description.
     *
     * @param descSms
     */
    public void setDescSms(String descSms) {
        this.descSms = descSms;
    }

    /**
     * *
     * Method used to get modified date.
     *
     * @return
     */
    public String getFechaMod() {
        return fechaMod;
    }

    /***
     * Method used to set modified date.
     * @param fechaMod 
     */
    public void setFechaMod(String fechaMod) {
        this.fechaMod = fechaMod;
    }
}
