/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.claro.clarosmsschedule.connection;

/**
 *
 * @author aifre
 */
public interface INotificationStateMessageHandler {

    /**
     * *
     * Method used to Notify state message.
     *
     * @param smppResponseDto
     */
    public void NotifyStateMessage(SmppResponseDto smppResponseDto);

}
