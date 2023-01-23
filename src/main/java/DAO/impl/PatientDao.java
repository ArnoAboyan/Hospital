package DAO.impl;

import DAO.DAOException;
import DAO.EntityDAO;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Doctor;
import entitys.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDao implements EntityDAO<Integer, Patient> {
    ;


    @Override
    public boolean create(Patient patient) {


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.ADDPATIENT);) {

            preparedStatement.setString(1, patient.getPatientName());
            preparedStatement.setString(2, patient.getPatientSurname());
            preparedStatement.setDate(3, patient.getPatientDateOfBirth());
            preparedStatement.setString(4, patient.getPatientGender());
            preparedStatement.setInt(5, patient.getPatientPhone());

            System.out.println(patient);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Patient getByID(Integer integer) {
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
            patient.setPatientPhone(resultSet.getInt("phone"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patient;

    }

    @Override
    public boolean update(Patient patient) {
        return false;
    }

    public void updatePatientbyId(Patient patient, int patientid) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.UPDATE_PATIENT_BY_ID);) {

            preparedStatement.setString(1, patient.getPatientName());
            preparedStatement.setString(2, patient.getPatientSurname());
            preparedStatement.setDate(3, patient.getPatientDateOfBirth());
            preparedStatement.setString(4, patient.getPatientGender());
            preparedStatement.setInt(5, patient.getPatientPhone());
            preparedStatement.setInt(6, patientid);

            System.out.println(patient);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void delete(Integer patientid) {


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.DELETEPATIENT);) {
            preparedStatement.setInt(1, patientid);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Patient> getAll() throws DAOException {

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
                patient.setPatientPhone(resultSet.getInt("phone"));

                patientList.add(patient);
                System.out.println(patient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patientList;
    }

    public List<Patient> getPatientsByDoctorId(Integer integer) throws DAOException {

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
                patient.setPatientPhone(resultSet.getInt("phone"));

                patientList.add(patient);

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return patientList;

    }

    public List<Patient> getAllWithLimit(int start, int count) throws DAOException {
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
                patient.setPatientPhone(resultSet.getInt("phone"));

                patientList.add(patient);
            }

        } catch (SQLException e) {
            throw new DAOException("Can not get all Room. ", e);
        }
        return patientList;
    }

    public int getCountPatient() throws DAOException {
        int result = 0;


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.COUNT_OF_PATIENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);

            }

        } catch (SQLException e) {
            throw new DAOException("Can not get count Patient");
        }

        return result;
    }

    public List<Patient> getAllWithLimitAndOrderBy(int start, int count, String sort) throws DAOException {
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
                patient.setPatientPhone(resultSet.getInt("phone"));

                patientList.add(patient);
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get room. ", e);
        }
        return patientList;
    }

    public List<Patient> getAllWithLimitById(int start, int count, int doctorid) throws DAOException {
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
                patient.setPatientPhone(resultSet.getInt("phone"));

                patientList.add(patient);
            }

        } catch (SQLException e) {
            throw new DAOException("Can not get all Room. ", e);
        }
        return patientList;
    }

    public int getCountPatientById(int doctorid) throws DAOException {
        int result = 0;


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.COUNT_OF_PATIENT_BY_ID)) {
            preparedStatement.setInt(1, doctorid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);
                System.out.println("result" + " " + result);
            }

        } catch (SQLException e) {
            throw new DAOException("Can not get count Patient");
        }

        return result;
    }

    public List<Patient> getAllWithLimitAndOrderById(int start, int count, String sort, int doctorid) throws DAOException {
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
                patient.setPatientPhone(resultSet.getInt("phone"));

                patientList.add(patient);
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get room. ", e);
        }
        return patientList;
    }

    public boolean isExistById(int patientid) throws DAOException {
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
                return true;
            } else {
                //if not exist
                return false;
            }

        } catch (SQLException e) {
//            logger.error("Can not do isExistEmail, SQLException = " + e.getMessage());
            throw new DAOException(e);
        }
    }
}

