/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.claro.clarosmsschedule.connection;

import java.util.Date;

/**
 *
 * @author aifre
 */
public class SmppResponseDto {

    /**
     * *
     * Message id.
     */
    public String messageId;

    /**
     * *
     * Command Status.
     */
    public int commandStatus;

    /**
     * *
     * Confirmation date.
     */
    public Date confirmationDate;

    /**
     * *
     * Error code.
     */
    public int errorCode;
}
