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
public class InhFactEnvioSmsBprep implements Serializable {

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
     * Cellphone number.
     */
    private String nMin;

    /**
     * *
     * Send date.
     */
    private String fechaEnvio;

    /**
     * *
     * Sms state.
     */
    private String estadoSms;

    /**
     * *
     * Sms code.
     */
    private BigInteger codigoSms;

    /**
     * *
     * Retry count.
     */
    private BigInteger intentos;

    /**
     * *
     * Unavailable month.
     */
    private String mesDesact;

    /**
     * *
     * Identifier asociated at cellphone number.
     */
    private BigInteger nCoId;

    /**
     * *
     * Modified date.
     */
    private String fechaMod;

    /**
     * *
     * Smsc Receiver Id.
     */
    private String cscSmsc;

    /**
     * *
     * Modified user.
     */
    private String usermod;

    /**
     * *
     * Tecnology code.
     */
    private BigInteger codTecnologia;

    /**
     * *
     * Segment.
     */
    private BigInteger segmento;

    /**
     * *
     * Segment Last.
     */
    private BigInteger segmentoFinal;

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
     * Method used tu set sequence id.
     *
     * @param idSequencia
     */
    public void setIdSequencia(BigDecimal idSequencia) {
        this.idSequencia = idSequencia;
    }

    /**
     * *
     * Method used to get cellphone number.
     *
     * @return
     */
    public String getNMin() {
        return nMin;
    }

    /**
     * *
     * Method used to set cellphone number.
     *
     * @param nMin
     */
    public void setNMin(String nMin) {
        this.nMin = nMin;
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
     * Method used to get sms status.
     *
     * @return
     */
    public String getEstadoSms() {
        return estadoSms;
    }

    /**
     * *
     * Method used to set sms status.
     *
     * @param estadoSms
     */
    public void setEstadoSms(String estadoSms) {
        this.estadoSms = estadoSms;
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
     * Method used to get retry count.
     *
     * @return
     */
    public BigInteger getIntentos() {
        return intentos;
    }

    /**
     * *
     * Method used to set retry count.
     *
     * @param intentos
     */
    public void setIntentos(BigInteger intentos) {
        this.intentos = intentos;
    }

    /**
     * *
     * Method used to get Unavailable month.
     *
     * @return
     */
    public String getMesDesact() {
        return mesDesact;
    }

    /**
     * *
     * Method used to set Unavailable month.
     *
     * @param mesDesact
     */
    public void setMesDesact(String mesDesact) {
        this.mesDesact = mesDesact;
    }

    /**
     * *
     * Method used to get Identifier asociated at cellphone number.
     *
     * @return
     */
    public BigInteger getNCoId() {
        return nCoId;
    }

    /**
     * *
     * Method used to set Identifier asociated at cellphone number.
     *
     * @param nCoId
     */
    public void setNCoId(BigInteger nCoId) {
        this.nCoId = nCoId;
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

    /**
     * *
     * Method used to set modified date.
     *
     * @param fechaMod
     */
    public void setFechaMod(String fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getCscSmsc() {
        return cscSmsc;
    }

    /**
     * *
     * Method used to set cscSmsc.
     *
     * @param cscSmsc
     */
    public void setCscSmsc(String cscSmsc) {
        this.cscSmsc = cscSmsc;
    }

    /**
     * *
     * Method used to get modified user.
     *
     * @return
     */
    public String getUsermod() {
        return usermod;
    }

    /**
     * *
     * Method used to set modified user.
     *
     * @param usermod
     */
    public void setUsermod(String usermod) {
        this.usermod = usermod;
    }

    /**
     * *
     * Method used to get technology code.
     *
     * @return
     */
    public BigInteger getCodTecnologia() {
        return codTecnologia;
    }

    /**
     * *
     * Method used to set technology code.
     *
     * @param codTecnologia
     */
    public void setCodTecnologia(BigInteger codTecnologia) {
        this.codTecnologia = codTecnologia;
    }

    /**
     * *
     * Method used to get segment.
     *
     * @return
     */
    public BigInteger getSegmento() {
        return segmento;
    }

    /**
     * *
     * Method used to set segment.
     *
     * @param segmento
     */
    public void setSegmento(BigInteger segmento) {
        this.segmento = segmento;
    }

    /**
     * *
     * Method used to get segment final.
     *
     * @return
     */
    public BigInteger getSegmentoFinal() {
        return segmentoFinal;
    }

    /**
     * *
     * Method used to set segment final.
     *
     * @param segmentoFinal
     */
    public void setSegmentoFinal(BigInteger segmentoFinal) {
        this.segmentoFinal = segmentoFinal;
    }

}
