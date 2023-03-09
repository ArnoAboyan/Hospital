package DAO.impl;

import DAO.DAOException;
import DAO.EntityDAO;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Patient;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDao implements EntityDAO<Integer, Patient> {

    static final Logger logger = Logger.getLogger(PatientDao.class);

    @Override
    public boolean create(Patient patient) {
        logger.info("Start create method...");

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.ADDPATIENT);) {

            preparedStatement.setString(1, patient.getPatientName());
            preparedStatement.setString(2, patient.getPatientSurname());
            preparedStatement.setDate(3, patient.getPatientDateOfBirth());
            preparedStatement.setString(4, patient.getPatientGender());
//            preparedStatement.setInt(5, patient.getPatientPhone());
            preparedStatement.setLong(5, patient.getPatientPhone());

            preparedStatement.executeUpdate();
            logger.info("create method => CORRECT");
        } catch (SQLException e) {
            logger.error("create method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Patient getByID(Integer integer) {
        logger.info("Start getByID method...");
        Patient patient = new Patient();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_PATIENT_BY_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            patient.setPatientId(resultSet.getInt("patient_id"));
            patient.setPatientName(resultSet.getString("patient_name"));
            patient.setPatientSurname(resultSet.getString("patient_surname"));
            patient.setPatientDateOfBirth(resultSet.getDate("patient_date_of_birth"));
            patient.setPatientGender(resultSet.getString("gender"));
            patient.setPatientPhone(resultSet.getLong("phone"));

            logger.info("getByID method => CORRECT");
        } catch (SQLException e) {
            logger.error("getByID method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }

        return patient;

    }

    @Override
    public boolean update(Patient patient) {
        return false;
    }

    public void updatePatientbyId(Patient patient, int patientid) {
        logger.info("Start updatePatientbyId method...");
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.UPDATE_PATIENT_BY_ID);) {

            preparedStatement.setString(1, patient.getPatientName());
            preparedStatement.setString(2, patient.getPatientSurname());
            preparedStatement.setDate(3, patient.getPatientDateOfBirth());
            preparedStatement.setString(4, patient.getPatientGender());
            preparedStatement.setLong(5, patient.getPatientPhone());
//            preparedStatement.setInt(5, patient.getPatientPhone());
            preparedStatement.setInt(6, patientid);

            preparedStatement.executeUpdate();
            logger.info("updatePatientbyId method => CORRECT");
        } catch (SQLException e) {
            logger.error("updatePatientbyId method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean delete(Integer patientid) {
        logger.info("Start delete method...");

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.DELETEPATIENT);) {
            preparedStatement.setInt(1, patientid);

            preparedStatement.executeUpdate();
            logger.info("delete method => CORRECT");
            return true;
        } catch (SQLException e) {
            logger.error("delete method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Patient> getAll() throws DAOException {
        logger.info("Start getAll method...");
        List<Patient> patientList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_ALL_PATIENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setPatientName(resultSet.getString("patient_name"));
                patient.setPatientSurname(resultSet.getString("patient_surname"));
                patient.setPatientDateOfBirth(resultSet.getDate("patient_date_of_birth"));
                patient.setPatientGender(resultSet.getString("gender"));
                patient.setPatientPhone(resultSet.getLong("phone"));

                patientList.add(patient);

            }
            logger.info("getAll method => CORRECT");
        } catch (SQLException e) {
            logger.error("getAll method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }
        return patientList;
    }

    public List<Patient> getPatientsByDoctorId(Integer integer) throws DAOException {
        logger.info("Start getPatientsByDoctorId method...");
        List<Patient> patientList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_PATIENT_BY_DOCTOR_ID)) {
            preparedStatement.setInt(1, integer);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setPatientName(resultSet.getString("patient_name"));
                patient.setPatientSurname(resultSet.getString("patient_surname"));
                patient.setPatientDateOfBirth(resultSet.getDate("patient_date_of_birth"));
                patient.setPatientGender(resultSet.getString("gender"));
                patient.setPatientPhone(resultSet.getLong("phone"));

                patientList.add(patient);

            }
            logger.info("getPatientsByDoctorId method => CORRECT");
        } catch (SQLException e) {
            logger.error("getPatientsByDoctorId method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }
        return patientList;

    }

    public List<Patient> getAllWithLimit(int start, int count) throws DAOException {
        logger.info("Start getAllWithLimit method...");
        start = start - 1;
        if (start != 0) {
            start = start * count;
        }

        List<Patient> patientList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_ALL_PATIENT_LIMIT)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            ;
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setPatientName(resultSet.getString("patient_name"));
                patient.setPatientSurname(resultSet.getString("patient_surname"));
                patient.setPatientDateOfBirth(resultSet.getDate("patient_date_of_birth"));
                patient.setPatientGender(resultSet.getString("gender"));
                patient.setPatientPhone(resultSet.getLong("phone"));

                patientList.add(patient);
            }
            logger.info("getAllWithLimit method => CORRECT");
        } catch (SQLException e) {
            logger.error("getAllWithLimit method => FALSE " + e.getMessage());
            throw new DAOException("Can not get all Room. ", e);
        }
        return patientList;
    }

    public int getCountPatient() throws DAOException {
        logger.info("Start getCountPatient method...");
        int result = 0;


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.COUNT_OF_PATIENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);

            }
            logger.info("getCountPatient method => CORRECT");
        } catch (SQLException e) {
            logger.error("getCountPatient method => FALSE " + e.getMessage());
            throw new DAOException("Can not get count Patient");
        }

        return result;
    }

    public List<Patient> getAllWithLimitAndOrderBy(int start, int count, String sort) throws DAOException {
        logger.info("Start getAllWithLimitAndOrderBy method...");
        start = start - 1;
        if (start != 0) {
            start = start * count;
        }
        List<Patient> patientList = new ArrayList<>();
        String query = AttributFinal.SORT_PATIENT + sort + AttributFinal.LIMIT_PATIENT;

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setPatientName(resultSet.getString("patient_name"));
                patient.setPatientSurname(resultSet.getString("patient_surname"));
                patient.setPatientDateOfBirth(resultSet.getDate("patient_date_of_birth"));
                patient.setPatientGender(resultSet.getString("gender"));
                patient.setPatientPhone(resultSet.getLong("phone"));

                patientList.add(patient);
            }
            logger.info("getAllWithLimitAndOrderBy method => CORRECT");
        } catch (SQLException e) {
            logger.error("getAllWithLimitAndOrderBy method => FALSE " + e.getMessage());
            throw new DAOException("Can not get room. ", e);
        }
        return patientList;
    }

    public List<Patient> getAllWithLimitById(int start, int count, int doctorid) throws DAOException {
        logger.info("Start getAllWithLimitById method...");
        start = start - 1;
        if (start != 0) {
            start = start * count;
        }


        List<Patient> patientList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_ALL_PATIENT_LIMIT_BY_ID)) {
            preparedStatement.setInt(1, doctorid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setPatientName(resultSet.getString("patient_name"));
                patient.setPatientSurname(resultSet.getString("patient_surname"));
                patient.setPatientDateOfBirth(resultSet.getDate("patient_date_of_birth"));
                patient.setPatientGender(resultSet.getString("gender"));
                patient.setPatientPhone(resultSet.getLong("phone"));

                patientList.add(patient);
            }
            logger.info("getAllWithLimitById method => CORRECT");
        } catch (SQLException e) {
            logger.error("getAllWithLimitById method => FALSE " + e.getMessage());
            throw new DAOException("Can not get all Room. ", e);
        }
        return patientList;
    }

    public int getCountPatientById(int doctorid) throws DAOException {
        logger.info("Start getCountPatientById method...");
        int result = 0;


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.COUNT_OF_PATIENT_BY_ID)) {
            preparedStatement.setInt(1, doctorid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            logger.info("getCountPatientById method => CORRECT");
        } catch (SQLException e) {
            logger.error("getCountPatientById method => FALSE " + e.getMessage());
            throw new DAOException("Can not get count Patient");
        }

        return result;
    }

    public List<Patient> getAllWithLimitAndOrderById(int start, int count, String sort, int doctorid) throws DAOException {
        logger.info("Start getAllWithLimitAndOrderById method...");
        start = start - 1;
        if (start != 0) {
            start = start * count;
        }

        List<Patient> patientList = new ArrayList<>();
        String query = AttributFinal.SORT_PATIENT_BY_ID + sort + AttributFinal.LIMIT_PATIENT_BY_ID;

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorid);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, count);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setPatientName(resultSet.getString("patient_name"));
                patient.setPatientSurname(resultSet.getString("patient_surname"));
                patient.setPatientDateOfBirth(resultSet.getDate("patient_date_of_birth"));
                patient.setPatientGender(resultSet.getString("gender"));
                patient.setPatientPhone(resultSet.getLong("phone"));

                patientList.add(patient);
            }
            logger.info("getAllWithLimitAndOrderById method => CORRECT");
        } catch (SQLException e) {
            logger.error("getAllWithLimitAndOrderById method => FALSE " + e.getMessage());
            throw new DAOException("Can not get room. ", e);
        }
        return patientList;
    }

    //method check phone is exist
    public boolean isExistPhoneNumber(String phone) throws DAOException {
        logger.info("Start isExistPhoneNumber method...");


        Patient patient = new Patient();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.CHECK_PATIENT_AVAILABILITY_BY_PHONE);) {
            preparedStatement.setString(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patient.setPatientPhone(resultSet.getLong("phone"));
            }


            if (patient.getPatientPhone() != null) {
                //if exist
                logger.info("Find patient by phone number " + phone + " => TRUE ");
                logger.info("isExistPhoneNumber method => CORRECT");
                return true;
            } else {
                //if not exist
                logger.info("Find patient by phone number " + phone + " => FALSE ");
                return false;
            }

        } catch (SQLException e) {
            logger.error("isExistPhoneNumber method => FALSE " + e.getMessage());
            throw new DAOException(e);
        }
    }

    public boolean isExistById(int patientid) throws DAOException {
        logger.info("Start isExistById method...");
        Patient patient = new Patient();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.CHECK_PATIENT_AVAILABILITY_BY_ID);) {
            preparedStatement.setInt(1, patientid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                patient.setPatientId(resultSet.getInt("patient_id"));
            }

            if (patient.getPatientId() > 0) {
                //if exist
                logger.info("Find patient by patientid " + patientid + " => TRUE ");
                logger.info("isExistById method => CORRECT");
                return true;
            } else {
                //if not exist
                logger.info("Find patient by patientid " + patientid + " => FALSE ");
                return false;
            }

        } catch (SQLException e) {
            logger.error("isExistById method => FALSE " + e.getMessage());
//            logger.error("Can not do isExistEmail, SQLException = " + e.getMessage());
            throw new DAOException(e);
        }
    }
}

