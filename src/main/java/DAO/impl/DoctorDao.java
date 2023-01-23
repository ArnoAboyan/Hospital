package DAO.impl;

import DAO.DAOException;
import DAO.EntityDAO;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Appointment;
import entitys.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DoctorDao implements EntityDAO<Integer, Doctor> {

//   private final ConnectionPool instance = ConnectionPool.getInstance();
//    public DoctorDao() throws DAOException {
//        this.instance = ConnectionPool.getInstance();
////        logger.info("Created OrderDAO and given Data Source");
//    }


    @Override
    public boolean create(Doctor doctor) {

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.ADDDOCTOR);) {
            System.out.println("STATEMENT PREPARED");
            preparedStatement.setString(1, doctor.getDoctorName());
            preparedStatement.setString(2, doctor.getDoctorSurname());
            preparedStatement.setInt(3, doctor.getCategory().getTitleId());
            preparedStatement.setString(4, doctor.getLogin());
            preparedStatement.setString(5, doctor.getPassword());
            preparedStatement.setInt(6, doctor.getRole().getTitleId());


            preparedStatement.executeUpdate();
            System.out.println("Create " + doctor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Doctor getByID(Integer integer) throws DAOException {
        Doctor doctor = new Doctor();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_DOCTOR_BY_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            doctor.setDoctorId(resultSet.getInt("doctor_id"));
            doctor.setLogin(resultSet.getString("login"));
            doctor.setPassword(resultSet.getString("password"));
            doctor.setRole(resultSet.getInt("role_id"));
            doctor.setDoctorName(resultSet.getString("doctor_name"));
            doctor.setDoctorSurname(resultSet.getString("doctor_surname"));
            doctor.setCategory(resultSet.getInt("category_id"));
            doctor.setCountOfPatients(resultSet.getInt("countofpatients"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return doctor;

    }

    @Override
    public boolean update(Doctor doctor) {
        return false;
    }

    public boolean updateDoctorById(Doctor doctor, int doctorid) {

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.UPDATE_DOCTOR_BY_ID);) {
            System.out.println("STATEMENT PREPARED");
            preparedStatement.setString(1, doctor.getDoctorName());
            preparedStatement.setString(2, doctor.getDoctorSurname());
            preparedStatement.setInt(3, doctor.getCategory().getTitleId());
            preparedStatement.setString(4, doctor.getLogin());
            preparedStatement.setString(5, doctor.getPassword());
            preparedStatement.setInt(6, doctor.getRole().getTitleId());
            preparedStatement.setInt(7, doctorid);


            preparedStatement.executeUpdate();
            System.out.println("Update " + doctor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    @Override
    public void delete(Integer doctorid) {

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.DELETEDOCTOR);) {
            preparedStatement.setInt(1, doctorid);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Doctor> getAll() throws DAOException {
        List<Doctor> doctorList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_ALL_DOCTOR)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(resultSet.getInt("doctor_id"));
                doctor.setDoctorName(resultSet.getString("doctor_name"));
                doctor.setDoctorSurname(resultSet.getString("doctor_surname"));
                doctor.setLogin(resultSet.getString("login"));
                doctor.setPassword(resultSet.getString("password"));
                doctor.setCategory(resultSet.getInt("category_id"));
                doctor.setRole(resultSet.getInt("role_id"));
                doctor.setCountOfPatients(resultSet.getInt("countofpatients"));

                doctorList.add(doctor);
                System.out.println(doctor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctorList;
    }

    public Doctor getByLogin(String login) throws DAOException {
        Doctor doctor = new Doctor();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_DOCTOR_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            doctor.setDoctorId(resultSet.getInt("doctor_id"));
            doctor.setLogin(resultSet.getString("login"));
            doctor.setPassword(resultSet.getString("password"));
            doctor.setRole(resultSet.getInt("role_id"));
            doctor.setDoctorName(resultSet.getString("doctor_name"));
            doctor.setDoctorSurname(resultSet.getString("doctor_surname"));
            doctor.setCategory(resultSet.getInt("category_id"));
            doctor.setCountOfPatients(resultSet.getInt("countofpatients"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return doctor;
    }

    public List<Doctor> getDoctorByRole(Integer roleId) throws DAOException {

        List<Doctor> doctorList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_DOCTOR_BY_ROLE)) {
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(resultSet.getInt("doctor_id"));
                doctor.setDoctorName(resultSet.getString("doctor_name"));
                doctor.setDoctorSurname(resultSet.getString("doctor_surname"));
                doctor.setLogin(resultSet.getString("login"));
                doctor.setPassword(resultSet.getString("password"));
                doctor.setCategory(resultSet.getInt("category_id"));
                doctor.setRole(resultSet.getInt("role_id"));
                doctor.setCountOfPatients(resultSet.getInt("countofpatients"));

                doctorList.add(doctor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctorList;
    }

    public List<Doctor> getAllWithLimitAndOrderBy(int start, int count, String sort) throws DAOException {
        start = start - 1;
        if (start != 0) {
            start = start * count;
        }
        List<Doctor> doctorList = new ArrayList<>();
        String query = AttributFinal.SORT_DOCTOR + sort + AttributFinal.LIMIT_DOCTOR;

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(resultSet.getInt("doctor_id"));
                doctor.setDoctorName(resultSet.getString("doctor_name"));
                doctor.setDoctorSurname(resultSet.getString("doctor_surname"));
                doctor.setLogin(resultSet.getString("login"));
                doctor.setPassword(resultSet.getString("password"));
                doctor.setCategory(resultSet.getInt("category_id"));
                doctor.setRole(resultSet.getInt("role_id"));
                doctor.setCountOfPatients(resultSet.getInt("countofpatients"));

                doctorList.add(doctor);

            }
        } catch (SQLException e) {
            throw new DAOException("Can not get doctor. ", e);
        }
        return doctorList;
    }

    public List<Doctor> getAllWithLimit(int start, int count) throws DAOException {

        start = start - 1;
        if (start != 0) {
            start = start * count;
        }

        List<Doctor> doctorList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getDataSource().getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_ALL_DOCTOR_LIMIT)) {

            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(resultSet.getInt("doctor_id"));
                doctor.setDoctorName(resultSet.getString("doctor_name"));
                doctor.setDoctorSurname(resultSet.getString("doctor_surname"));
                doctor.setLogin(resultSet.getString("login"));
                doctor.setPassword(resultSet.getString("password"));
                doctor.setCategory(resultSet.getInt("category_id"));
                doctor.setRole(resultSet.getInt("role_id"));
                doctor.setCountOfPatients(resultSet.getInt("countofpatients"));

                doctorList.add(doctor);
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get all Doctor. ", e);
        }
        return doctorList;
    }

    public Integer getCountDoctor() throws DAOException {
        int result = 0;


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.COUNT_OF_DOCTOR)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);

            }

        } catch (SQLException e) {
            throw new DAOException("Can not get count Patient");
        }

        return result;
    }

    public Integer getCountOfPatientsByDoctor(int doctorid) throws DAOException {
        int result = 0;


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_COUNT_PATIENT_BY_DOCTOR_ID)) {
            preparedStatement.setInt(1, doctorid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);

            }

        } catch (SQLException e) {
            throw new DAOException("Can not get count Patient");
        }

        return result;
    }

    public boolean updateCountOfPatients(int count, int doctorId) {


        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.UPDATE_COUNT_PATIENT_BY_DOCTOR_ID);) {
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, doctorId);


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    //method check email is exist
    public boolean isExistLogin(String login) throws DAOException {


        Doctor doctor = new Doctor();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.CHECK_DOCTOR_AVAILABILITY_BY_EMAIL);) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                doctor.setLogin(resultSet.getString("login"));
            }

            if (doctor.getLogin() != null) {
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

    public boolean isExistById(int doctorid) throws DAOException {
        Doctor doctor = new Doctor();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.CHECK_DOCTOR_AVAILABILITY_BY_ID);) {
            preparedStatement.setInt(1, doctorid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                doctor.setDoctorId(resultSet.getInt("doctor_id"));
            }

            if (doctor.getDoctorId() > 0) {
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
