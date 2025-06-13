package com.claro.clarosmsschedule.dao;

import com.claro.clarosmsschedule.db.DbCredential;
import com.claro.clarosmsschedule.model.CtrlBroadDwh;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author aifre
 */
public class CtrlBroadDwhDao extends DbConnectionDao {

    /**
     * *
     * Default Logger.
     */
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(CtrlBroadDwhDao.class);

    /**
     * *
     * Default date formatter.
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.getDefault());

    /**
     * *
     * Public constructor.
     *
     * @param credential
     */
    public CtrlBroadDwhDao(DbCredential credential) {
        super(credential);
    }

    /**
     * *
     * Method used to get all entity element.
     *
     * @return
     */
    public CtrlBroadDwh getMaxSchedule() throws SQLException {

        Connection connection = null;
        CtrlBroadDwh broadDwh = new CtrlBroadDwh();
        try {
            String query = "SELECT * "
                    + "FROM (SELECT FECHA_EJECUCION, FECHA_INSERCION, ESTADO, FECHA_ACTUALIZACION "
                    + "FROM INH_BROAD.CTRL_BROAD_DWH ORDER BY FECHA_INSERCION DESC) "
                    + "WHERE ROWNUM <= 1";
            connection = this.getDbConnection().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                broadDwh.setFechaEjecucion(resultSet.getString(1));
                broadDwh.setFechaInsercion(resultSet.getString(2));
                broadDwh.setEstado(resultSet.getBigDecimal(3));
                broadDwh.setFechaActualizacion(resultSet.getString(4));
            }
        } catch (ClassNotFoundException | NullPointerException | SQLException ex) {
            LOGGER.error(CtrlBroadDwhDao.class.getName(), formatter.format(new java.util.Date()), ex);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return broadDwh;
    }

    /**
     * *
     * Method used to update schedule data.
     *
     * @param ctrlBroadDwh.
     * @return
     */
    public boolean updateScheduler(CtrlBroadDwh ctrlBroadDwh) throws SQLException {
        Connection connection = null;
        try {
            String query = "UPDATE INH_BROAD.CTRL_BROAD_DWH"
                    + " SET ESTADO=?, FECHA_ACTUALIZACION=?"
                    + " WHERE FECHA_EJECUCION=?";
            connection = this.getDbConnection().connect();
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, ctrlBroadDwh.getEstado());
            preparedStatement.setDate(2, sqlDate);
            preparedStatement.setString(3, ctrlBroadDwh.getFechaEjecucion());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.error(CtrlBroadDwhDao.class.getName(), formatter.format(new java.util.Date()), ex);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return false;
    }

}
