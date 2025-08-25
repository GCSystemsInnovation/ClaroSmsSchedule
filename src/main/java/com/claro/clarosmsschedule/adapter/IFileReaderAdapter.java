/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.claro.clarosmsschedule.adapter;

import com.claro.clarosmsschedule.connection.SmppCredential;
import com.claro.clarosmsschedule.db.DbCredential;
import com.claro.clarosmsschedule.dto.ApplicationSetting;
import java.io.InputStream;

/**
 *
 * @author aifre
 */
public interface IFileReaderAdapter {

    /**
     * *
     * Method used to read to properties file
     *
     * @return
     */
    InputStream readPropertiesFile();

    /**
     * *
     * Method used to fill ApplicationSetting From Properties.
     * 
     * @return
     */
    ApplicationSetting fillApplicationSettingFromProperties();

    /**
     * *
     * Method used to fill SmppCredential From Properties.
     * 
     * @return
     */
    SmppCredential fillSmppCredentialFromProperties();

    /**
     * *
     * Method used to fill DbCredential InhBroad From Properties.
     *
     * @return
     */
    DbCredential fillDbCredentialInhBroadFromProperties();

    /**
     * *
     * Method used to fill DbCredential HernanMz From Properties.
     *
     * @return
     */
    DbCredential fillDbCredentialHernanMzFromProperties();

}
