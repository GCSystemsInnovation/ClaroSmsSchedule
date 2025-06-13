/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.claro.clarosmsschedule.main;

/**
 *
 * @author aifre
 */
public enum ESmsState {

    PENDING_SMS("0"),
    SENT_SMS("90");

    private final String number;

    ESmsState(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }
}
