/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author aifre
 */
public class SmsUserDto implements Serializable {

    /**
     * *
     * InhFactEnvioSmsBprep Id.
     */
    private BigDecimal inhFactEnvioSmsBprep_Id;

    /**
     * *
     * Cellphone number.
     */
    private String nMin;

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
     * InhFactEnvioSmsDesc Id.
     */
    private BigDecimal InhFactEnvioSmsDesc_Id;

    /**
     * *
     * Identifier asociated at cellphone number.
     */
    private BigDecimal coId;

    /**
     * *
     * Retry number.
     */
    private BigInteger retry;

    /**
     * *
     * Method used to get Identifier asociated at cellphone number.
     *
     * @return
     */
    public BigDecimal getCoId() {
        return coId;
    }

    /**
     * *
     * Method used to set Identifier asociated at cellphone number.
     *
     * @param coId
     */
    public void setCoId(BigDecimal coId) {
        this.coId = coId;
    }

    /**
     * *
     * Method used to get InhFactEnvioSmsDesc_Id.
     *
     * @return
     */
    public BigDecimal getInhFactEnvioSmsDesc_Id() {
        return InhFactEnvioSmsDesc_Id;
    }

    /**
     * *
     * Method used to set InhFactEnvioSmsDesc_Id.
     *
     * @param InhFactEnvioSmsDesc_Id
     */
    public void setInhFactEnvioSmsDesc_Id(BigDecimal InhFactEnvioSmsDesc_Id) {
        this.InhFactEnvioSmsDesc_Id = InhFactEnvioSmsDesc_Id;
    }

    /**
     * *
     * Method used to get InhFactEnvioSmsBprep_Id.
     *
     * @return
     */
    public BigDecimal getInhFactEnvioSmsBprep_Id() {
        return inhFactEnvioSmsBprep_Id;
    }

    /**
     * *
     * Method used to set InhFactEnvioSmsBprep_Id.
     *
     * @param inhFactEnvioSmsBprep_Id
     */
    public void setInhFactEnvioSmsBprep_Id(BigDecimal inhFactEnvioSmsBprep_Id) {
        this.inhFactEnvioSmsBprep_Id = inhFactEnvioSmsBprep_Id;
    }

    /**
     * *
     * Method used to get cellphone number.
     *
     * @return
     */
    public String getnMin() {
        return nMin;
    }

    /**
     * *
     * Method used to set cellphone number.
     *
     * @param nMin
     */
    public void setnMin(String nMin) {
        this.nMin = nMin;
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
     * Method used to get sms decription.
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
     * Method used to get retry number.
     *
     * @return
     */
    public BigInteger getRetry() {
        return retry;
    }

    /**
     * *
     * Method used to set retry number.
     *
     * @param retry
     */
    public void setRetry(BigInteger retry) {
        this.retry = retry;
    }
}
