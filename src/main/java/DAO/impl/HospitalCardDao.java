package DAO.impl;

import DAO.DAOException;
import DAO.EntityDAO;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Doctor;
import entitys.HospitalCard;
import entitys.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HospitalCardDao implements EntityDAO<Integer, HospitalCard> {


    @Override
    public boolean create(HospitalCard hospitalCard) throws DAOException {


            if (!checkAvailable(hospitalCard.getPatientId(), hospitalCard.getDoctorId())) {

                try (Connection connection = ConnectionPool.getDataSource().getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.ADDHOSPITALCARD);) {

                    preparedStatement.setInt(1, hospitalCard.getPatientId());
                    preparedStatement.setInt(2, hospitalCard.getDoctorId());
                    preparedStatement.setString(3, hospitalCard.getDiagnosis());
                    preparedStatement.setString(4, hospitalCard.getProcedures());
                    preparedStatement.setString(5, hospitalCard.getMedications());
                    preparedStatement.setString(6, hospitalCard.getOperations());

                    System.out.println(preparedStatement);
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }   update(hospitalCard);

        return true;
    }

    @Override
    public HospitalCard getByID(Integer integer) {
        HospitalCard hospitalCard = new HospitalCard();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_HOSPITALCARD_BY_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            hospitalCard.setHospitalCardId(resultSet.getInt("hospitalcard_id"));
            hospitalCard.setPatientId(resultSet.getInt("patientid"));
            hospitalCard.setDoctorId(resultSet.getInt("doctorid"));
            hospitalCard.setDiagnosis(resultSet.getString("diagnosis"));
            hospitalCard.setProcedures(resultSet.getString("procedures"));
            hospitalCard.setMedications(resultSet.getString("medications"));
            hospitalCard.setOperations(resultSet.getString("operations"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return hospitalCard;
    }

    @Override
    public boolean update(HospitalCard hospitalCard) {
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.UPDATE_HOSPITAL_CARD);) {

            preparedStatement.setString(1, hospitalCard.getDiagnosis());
            preparedStatement.setString(2, hospitalCard.getProcedures());
            preparedStatement.setString(3, hospitalCard.getMedications());
            preparedStatement.setString(4, hospitalCard.getOperations());
            preparedStatement.setInt(5, hospitalCard.getPatientId());
            preparedStatement.setInt(6, hospitalCard.getDoctorId());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void delete(Integer entity) {

    }

    @Override
    public List<HospitalCard> getAll() throws DAOException {
        return null;
    }

    public HospitalCard getByPatientID(Integer integer) throws DAOException {
                HospitalCard hospitalCard = new HospitalCard();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_HOSPITALCARD_BY_PATIENT_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                hospitalCard.setHospitalCardId(resultSet.getInt("hospitalcard_id"));
                hospitalCard.setPatientId(resultSet.getInt("patientid"));
                hospitalCard.setDoctorId(resultSet.getInt("doctorid"));
                hospitalCard.setDiagnosis(resultSet.getString("diagnosis"));
                hospitalCard.setProcedures(resultSet.getString("procedures"));
                hospitalCard.setMedications(resultSet.getString("medications"));
                hospitalCard.setOperations(resultSet.getString("operations"));
            }

            System.out.println(hospitalCard);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return hospitalCard;
    }


    public Boolean checkAvailable(Integer patient, Integer doctor) throws DAOException {

        boolean check = false;
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.CHECK_HOSPITALCARD_AVALABILE)) {

            preparedStatement.setInt(1, patient);
            preparedStatement.setInt(2, doctor);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                check = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    public List<HospitalCard> getAllWithLimit(int start, int count) throws DAOException {
        start = start - 1;
        if (start != 0) {
            start = start * count;
        }

        List<HospitalCard> hospitalCardlist = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.GET_ALL_HOSPITALCARD_LIMIT)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();
            ;
            while (resultSet.next()) {
                HospitalCard hospitalCard = new HospitalCard();
                hospitalCard.setHospitalCardId(resultSet.getInt("hospitalcard_id"));
                hospitalCard.setPatientId(resultSet.getInt("patientid"));
                hospitalCard.setDoctorId(resultSet.getInt("doctorid"));
                hospitalCard.setDiagnosis(resultSet.getString("diagnosis"));
                hospitalCard.setProcedures(resultSet.getString("procedures"));
                hospitalCard.setMedications(resultSet.getString("medications"));
                hospitalCard.setOperations(resultSet.getString("operations"));

                hospitalCardlist.add(hospitalCard);
            }

        } catch (SQLException e) {
            throw new DAOException("Can not get all Hospital. ", e);
        }
        return hospitalCardlist;
    }


    public List<HospitalCard> getAllWithLimitAndOrderBy(int start, int count, String sort) throws DAOException {
        start = start - 1;
        if (start != 0) {
            start = start * count;
        }
        List<HospitalCard> hospitalCardlist = new ArrayList<>();
        String query = AttributFinal.SORT_HOSPITALCARD + sort + AttributFinal.LIMIT_HOSPITALCARD;

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                HospitalCard hospitalCard = new HospitalCard();
                hospitalCard.setHospitalCardId(resultSet.getInt("hospitalcard_id"));
                hospitalCard.setPatientId(resultSet.getInt("patientid"));
                hospitalCard.setDoctorId(resultSet.getInt("doctorid"));
                hospitalCard.setDiagnosis(resultSet.getString("diagnosis"));
                hospitalCard.setProcedures(resultSet.getString("procedures"));
                hospitalCard.setMedications(resultSet.getString("medications"));
                hospitalCard.setOperations(resultSet.getString("operations"));

                hospitalCardlist.add(hospitalCard);
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get Hospital. ", e);
        }
        return hospitalCardlist;
    }

    public int getCountHospitalCard() throws DAOException {
        int result = 0;



        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AttributFinal.COUNT_OF_HOSPITALCARD)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getInt(1);

            }

        } catch (SQLException e) {
            throw new DAOException("Can not get count Hospital");
        }

        return result;
    }


}