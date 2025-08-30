package com.claro.clarosmsschedule.dao;

import com.claro.clarosmsschedule.db.DbCredential;
import com.claro.clarosmsschedule.model.InhFactEnvioSmsBprep;
import com.claro.clarosmsschedule.dto.SmsUserDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author aifre
 */
public class InhFactEnvioSmsBprepDao extends DbConnectionDao {

    /**
     * *
     * Default Logger.
     */
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(InhFactEnvioSmsBprepDao.class);

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
    public InhFactEnvioSmsBprepDao(DbCredential credential) {
        super(credential);
    }

    /**
     * *
     * Method used to get all entity element.
     *
     * @param mesDesact date in format AAAAMM.
     * @param startPagination start Pagination.
     * @param endPagination end Pagination..
     * @return
     * @throws java.sql.SQLException
     */
    public List<SmsUserDto> getUserList(String mesDesact, int startPagination, int endPagination) throws SQLException {
        Connection connection = null;
        List<SmsUserDto> smsUserDtos = new ArrayList<>();
        try {
            String query = "SELECT "
                    + "ifesb.ID_SEQUENCIA AS inhFactEnvioSmsBprep_Id, "
                    + "ifesb.N_MIN, "
                    + "ifesb.CODIGO_SMS, "
                    + "ifesd.DESC_SMS, "
                    + "ifesb.ID_SEQUENCIA AS InhFactEnvioSmsDesc_Id, "
                    + "ifesb.N_CO_ID, "
                    + "ifesb.INTENTOS "
                    + "FROM HERNANMZ.INH_FACT_ENVIO_SMS_BPREP ifesb "
                    + "INNER JOIN HERNANMZ.INH_FACT_ENVIO_SMS_DESC ifesd ON ifesb.CODIGO_SMS = ifesd.CODIGO_SMS "
                    + "WHERE ifesb.INTENTOS < 3	"
                    + "AND ifesb.MES_DESACT = ? "
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            connection = this.getDbConnection().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, mesDesact);
            preparedStatement.setInt(2, startPagination);
            preparedStatement.setInt(3, endPagination);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SmsUserDto smsUserDto = new SmsUserDto();
                smsUserDto.setInhFactEnvioSmsBprep_Id(resultSet.getBigDecimal(1));
                smsUserDto.setnMin(resultSet.getString(2));
                smsUserDto.setCodigoSms(resultSet.getBigDecimal(3).toBigInteger());
                smsUserDto.setDescSms(resultSet.getString(4));
                smsUserDto.setInhFactEnvioSmsDesc_Id(resultSet.getBigDecimal(5));
                smsUserDto.setCoId(resultSet.getBigDecimal(6));
                smsUserDto.setRetry(resultSet.getBigDecimal(7).toBigInteger());
                smsUserDtos.add(smsUserDto);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.error(InhFactEnvioSmsBprepDao.class.getName(), formatter.format(new java.util.Date()), ex);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        return smsUserDtos;
    }

    /**
     * *
     * Method used to get all entity element.
     *
     * @param mesDesact date in format AAAAMM.
     * @param startPagination start Pagination.
     * @param endPagination end Pagination.
     * @param index last index.
     * @return
     * @throws java.sql.SQLException
     */
    public List<SmsUserDto> getUserListFromIndex(String mesDesact, int startPagination, int endPagination, int index) throws SQLException {
        Connection connection = null;
        List<SmsUserDto> smsUserDtos = new ArrayList<>();
        try {
            String query = "SELECT "
                    + "ifesb.ID_SEQUENCIA AS inhFactEnvioSmsBprep_Id, "
                    + "ifesb.N_MIN, "
                    + "ifesb.CODIGO_SMS, "
                    + "ifesd.DESC_SMS, "
                    + "ifesb.ID_SEQUENCIA AS InhFactEnvioSmsDesc_Id, "
                    + "ifesb.N_CO_ID, "
                    + "ifesb.INTENTOS "
                    + "FROM HERNANMZ.INH_FACT_ENVIO_SMS_BPREP ifesb "
                    + "INNER JOIN HERNANMZ.INH_FACT_ENVIO_SMS_DESC ifesd ON ifesb.CODIGO_SMS = ifesd.CODIGO_SMS "
                    + "WHERE ifesb.INTENTOS < 3	"
                    + "AND ifesb.MES_DESACT = ? "
                    + "AND ifesb.ID_SEQUENCIA >= ? "
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            connection = this.getDbConnection().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, mesDesact);
            preparedStatement.setInt(2, index);
            preparedStatement.setInt(3, startPagination);
            preparedStatement.setInt(4, endPagination);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SmsUserDto smsUserDto = new SmsUserDto();
                smsUserDto.setInhFactEnvioSmsBprep_Id(resultSet.getBigDecimal(1));
                smsUserDto.setnMin(resultSet.getString(2));
                smsUserDto.setCodigoSms(resultSet.getBigDecimal(3).toBigInteger());
                smsUserDto.setDescSms(resultSet.getString(4));
                smsUserDto.setInhFactEnvioSmsDesc_Id(resultSet.getBigDecimal(5));
                smsUserDto.setCoId(resultSet.getBigDecimal(6));
                smsUserDto.setRetry(resultSet.getBigDecimal(7).toBigInteger());
                smsUserDtos.add(smsUserDto);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.error(InhFactEnvioSmsBprepDao.class.getName(), formatter.format(new java.util.Date()), ex);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        return smsUserDtos;
    }

    /**
     * *
     * Method used to update user data.
     *
     * @param envioSmsBprep.
     * @return
     * @throws java.sql.SQLException
     */
    public boolean updateUser(InhFactEnvioSmsBprep envioSmsBprep) throws SQLException {
        Connection connection = null;
        try {
            String query = "UPDATE HERNANMZ.INH_FACT_ENVIO_SMS_BPREP"
                    + " SET FECHA_ENVIO=?, ESTADO_SMS=?, INTENTOS=?, CSC_SMSC=?"
                    + " WHERE ID_SEQUENCIA = ?";
            connection = this.getDbConnection().connect();
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setString(2, envioSmsBprep.getEstadoSms());
            preparedStatement.setBigDecimal(3, new BigDecimal(envioSmsBprep.getIntentos()));
            preparedStatement.setString(4, envioSmsBprep.getCscSmsc());
            preparedStatement.setBigDecimal(5, envioSmsBprep.getIdSequencia());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.error(InhFactEnvioSmsBprepDao.class.getName(), formatter.format(new java.util.Date()), ex);
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return false;
    }

}
