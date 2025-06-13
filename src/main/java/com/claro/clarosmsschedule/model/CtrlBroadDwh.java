/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author aifre
 */
public class CtrlBroadDwh implements Serializable {

    /**
     * *
     * Serial verion UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * *
     * Execution Date.
     */
    private String fechaEjecucion;

    /**
     * *
     * Created date.
     */
    private String fechaInsercion;

    /**
     * *
     * State..
     */
    private BigDecimal estado;

    /**
     * *
     * Modified Date.
     */
    private String fechaActualizacion;

    /**
     * *
     * public Constructor.
     */
    public CtrlBroadDwh() {
    }

    public CtrlBroadDwh(String fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    /**
     * *
     * Method used to get execution date.
     *
     * @return
     */
    public String getFechaEjecucion() {
        return fechaEjecucion;
    }

    /**
     * *
     * Method used to set execution date.
     *
     * @param fechaEjecucion
     */
    public void setFechaEjecucion(String fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    /**
     * *
     * Mehtod used to get created date.
     *
     * @return
     */
    public String getFechaInsercion() {
        return fechaInsercion;
    }

    /**
     * *
     * Method used to created date.
     *
     * @param fechaInsercion
     */
    public void setFechaInsercion(String fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    /**
     * *
     * Method used to get state.
     *
     * @return
     */
    public BigDecimal getEstado() {
        return estado;
    }

    /**
     * *
     * Method used to set state.
     *
     * @param estado
     */
    public void setEstado(BigDecimal estado) {
        this.estado = estado;
    }

    /**
     * *
     * Method used to get modified date.
     *
     * @return
     */
    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * *
     * Method used to set modified date.
     *
     * @param fechaActualizacion
     */
    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

}
