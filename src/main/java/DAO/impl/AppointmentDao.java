package DAO.impl;

import Command.PatientListByDoctor;
import Command.UpdatePatientCommand;
import DAO.DAOException;
import DAO.EntityDAO;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Appointment;
import entitys.Doctor;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AppointmentDao implements EntityDAO<Integer ,Appointment> {

    static final Logger logger = Logger.getLogger(PatientListByDoctor.class);
    @Override
    public boolean create(Appointment appointment) {
        logger.info("Start create method...");
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.ADDDAPPOINTMENT);) {

            preparedStatement.setInt(1, appointment.getDoctorId());
            preparedStatement.setInt(2, appointment.getPatientId());
            Timestamp timestamp = Timestamp.valueOf(appointment.getAppointmentData());
            preparedStatement.setTimestamp(3, timestamp);

            preparedStatement.executeUpdate();
            logger.info("create method => CORRECT");
        } catch (SQLException e) {
            logger.error("create method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Appointment getByID(Integer integer) {
        return null;
    }

    @Override
    public boolean update(Appointment appointment) {
        return false;
    }

    @Override
    public void delete(Integer appointmentid) {
        logger.info("Start delete method...");



        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.DELETEAPPOINTMENT);) {
            preparedStatement.setInt(1, appointmentid);

            preparedStatement.executeUpdate();

            logger.info("delete method => CORRECT");
        } catch (SQLException e) {
            logger.error("delete method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<Appointment> getAll() throws DAOException {
        logger.info("Start getAll method...");
        List<Appointment> appointmentList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_ALL_APPOINTMENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                appointment.setDoctorId(resultSet.getInt("doctor_id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setAppointmentData(Timestamp.valueOf(resultSet.getString("appointments_data")).toLocalDateTime());


                appointmentList.add(appointment);
                logger.info("getAll method => CORRECT");
            }
        } catch (SQLException e) {
            logger.error("getAll method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }
        return appointmentList;
    }

    public List<Appointment> getAppointmentByDoctorId(Integer integer) throws DAOException {
        logger.info("Start getAppointmentByDoctorId method...");

        List<Appointment> appointmentList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_APPOINTMENT_BY_DOCTOR_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                appointment.setDoctorId(resultSet.getInt("doctor_id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setAppointmentData(Timestamp.valueOf(resultSet.getString("appointments_data")).toLocalDateTime());


                appointmentList.add(appointment);
                logger.info("getAppointmentByDoctorId method => CORRECT");
            }
        } catch (SQLException e) {
            logger.error("getAppointmentByDoctorId method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }
        return appointmentList;

    }
    public List<Appointment> getAppointmentByPatientAndDoctorId(Integer doctor, Integer patient) throws DAOException {
        logger.info("Start getAppointmentByPatientAndDoctorId method...");
        List<Appointment> appointmentList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_APPOINTMENT_BY_DOCTOR_AND_PATIENT_ID)) {
            preparedStatement.setInt(1, doctor);
            preparedStatement.setInt(2, patient);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                appointment.setDoctorId(resultSet.getInt("doctor_id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setAppointmentData(Timestamp.valueOf(resultSet.getString("appointments_data")).toLocalDateTime());


                appointmentList.add(appointment);
                logger.info("getAppointmentByPatientAndDoctorId method => CORRECT");
            }
            } catch (SQLException e) {
            logger.error("getAppointmentByPatientAndDoctorId method => FALSE " + e.getMessage());
            throw new RuntimeException(e);
        }
        return appointmentList;
    }

    public List<Appointment> getAllWithLimit(int start, int count) throws DAOException{
        logger.info("Start getAllWithLimit method...");
        start = start - 1;
        if (start != 0) {
            start = start * count;
        }

        List<Appointment> appointmentList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_ALL_APPOINTMENT_LIMIT)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                appointment.setDoctorId(resultSet.getInt("doctor_id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setAppointmentData(Timestamp.valueOf(resultSet.getString("appointments_data")).toLocalDateTime());

                appointmentList.add(appointment);
                logger.info("getAllWithLimit method => CORRECT");
            }

        } catch (SQLException e) {
            logger.error("getAllWithLimit method => FALSE " + e.getMessage());
            throw new DAOException("Can not get all appointments. ", e);
        }
        return appointmentList;
    }

    public List<Appointment> getAllWithLimitAndOrderBy(int start, int count, String sort) throws DAOException {
        logger.info("Start getAllWithLimitAndOrderBy method...");
        start = start - 1;
        if (start != 0) {
            start = start * count;
        }
        List<Appointment> appointmentList = new ArrayList<>();
        String query = AttributFinal.SORT_APPOINTMENT + sort + AttributFinal.LIMIT_APPOINTMENT;

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                appointment.setDoctorId(resultSet.getInt("doctor_id"));
                appointment.setPatientId(resultSet.getInt("patient_id"));
                appointment.setAppointmentData(Timestamp.valueOf(resultSet.getString("appointments_data")).toLocalDateTime());

                appointmentList.add(appointment);
                logger.info("getAllWithLimitAndOrderBy method => CORRECT");
            }
        } catch (SQLException e) {
            logger.error("getAllWithLimitAndOrderBy method => FALSE " + e.getMessage());
            throw new DAOException("Can not get appointments ", e);
        }
        return appointmentList;
    }

    public int getCountPatient() throws DAOException{
        logger.info("Start getCountPatient method...");
        int result = 0;



        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.COUNT_OF_APPOINTMENT)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);
                logger.info("getCountPatient method => CORRECT");
            }

        } catch (SQLException e) {
            logger.error("getCountPatient method => FALSE " + e.getMessage());
            throw new DAOException("Can not get count appointments");
        }

        return result;
    }

    public boolean isExist(int appointmentId) throws DAOException {
        logger.info("Start isExist method...");

        Appointment appointment = new Appointment();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.CHECK_APPOINTMENT_AVAILABILITY_BY_ID);) {
            preparedStatement.setInt(1, appointmentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                appointment.setAppointmentId(resultSet.getInt("appointment_id"));
            }

            if (appointment.getAppointmentId() != null) {
                logger.info("Find appointment by id " + appointmentId + " => TRUE ");
                logger.info("isExist method => CORRECT");
                return true;
            } else {
                logger.info("Find appointment by id " + appointmentId + " => FALSE ");
                return false;
            }


        } catch (SQLException e) {
            logger.error("isExist method => FALSE " + e.getMessage());
            throw new DAOException(e);
        }

    }




}
