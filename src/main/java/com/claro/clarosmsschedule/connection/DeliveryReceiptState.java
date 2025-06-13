/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.claro.clarosmsschedule.connection;

/**
 *
 * @author aifre
 */
public enum DeliveryReceiptState {

    /**
     * *
     * Ok - Message Acceptable
     */
    ESME_ROK(0, "Ok - Message Acceptable"),
    /**
     * *
     * Invalid Message Length
     */
    ESME_RINVMSGLEN(1, "Invalid Message Length"),
    /**
     * *
     * Invalid Command Length
     */
    ESME_RINVCMDLEN(2, "Invalid Command Length"),
    /**
     * *
     * Invalid Command ID.
     */
    ESME_RINVCMDID(3, "Invalid Command ID"),
    /**
     * *
     * Invalid bind status.
     */
    ESME_RINVBNDSTS(4, "Invalid bind status"),
    /**
     * *
     * Bind attempted when already bound.
     */
    ESME_RALYBND(5, "Bind attempted when already bound"),
    /**
     * *
     * Invalid priority flag.
     */
    ESME_RINVPRTFLG(6, "Invalid priority flag"),
    /**
     * *
     * Invalid registered-delivery flag.
     */
    ESME_RINVREGDLVFLG(7, "Invalid registered-delivery flag"),
    /**
     * *
     * SMSC system error.
     */
    ESME_RSYSERR(8, "SMSC system error"),
    /**
     * *
     * Invalid source address.
     */
    ESME_RINVSRCADR(9, "Invalid source address"),
    /**
     * *
     * Invalid destination address-
     */
    ESME_RINVDSTADR(11, "Invalid destination address"),
    /**
     * *
     * Invalid message-id
     */
    ESME_RINVMSGID(12, "Invalid message-id"),
    /**
     * *
     * Couldn't resolve.Ask admin to add.
     */
    NOT_FOUND(000, "Couldn't resolve.Ask admin to add.");

    /**
     * *
     * Delivery Receip State value.
     */
    private final int value;

    /**
     * *
     * Delivery Receip State description.
     */
    private final String description;

    /**
     * *
     * Public constructor that initialize delivery receip states.
     *
     * @param value
     * @param description
     */
    DeliveryReceiptState(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * *
     * Method used to get decription delivery receip state.
     *
     * @param value
     * @return
     */
    public static DeliveryReceiptState getDescription(int value) {
        for (DeliveryReceiptState item : values()) {
            if (item.value() == value) {
                return item;
            }
        }
        return NOT_FOUND;
    }

    /**
     * *
     * Method used to get value delivery receip state.
     *
     * @return
     */
    public int value() {
        return value;
    }

    /**
     * *
     * Method used to get description delivery receip state.
     *
     * @return
     */
    public String description() {
        return description;
    }

}
