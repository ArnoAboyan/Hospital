package Command;

import DAO.DAOException;
import DAO.impl.AppointmentDao;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Appointment;
import entitys.Doctor;
import entitys.HospitalCard;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class HospitalCardListTest {
    // mock connection to DB//
    MockedStatic<ConnectionPool> dsStatic = mockStatic(ConnectionPool.class);
    static DataSource dataSourceMock;
    Connection con;

    PreparedStatement psOne;
    ResultSet rsOne;
    PreparedStatement psTwo;
    ResultSet rsTwo;
    PreparedStatement psThree;
    ResultSet rsThree;

    //ENTITYS//
    List<Appointment> appointmentList = new ArrayList<>();
    Appointment appointment = new Appointment();
    Doctor doctorNurse;
    Doctor doctor;

    HospitalCard hospitalCard;

    //MOCK CLASSES//
    AppointmentDao appointmentDao = mock(AppointmentDao.class);
    AppointmentListCommand appointmentListCommandMock = mock(AppointmentListCommand.class);


    //OTHER//
    String sort = "doctorid";


    @BeforeEach
    void setUp(){
        //CONFIG MOCK CONNECTION TO DB//

        con = mock(Connection.class);
        dataSourceMock = mock(DataSource.class);


        // PREPARED STATMENT //
        psOne = mock(PreparedStatement.class);
        rsOne = mock(ResultSet.class);
        psTwo = mock(PreparedStatement.class);
        rsTwo = mock(ResultSet.class);
        psThree = mock(PreparedStatement.class);
        rsThree = mock(ResultSet.class);
        //OTHER SETTINGS FOR TEST//

        doctorNurse = new Doctor();
        doctorNurse.setDoctorId(4);
        doctorNurse.setLogin("nurse");
        doctorNurse.setPassword("nurse");
        doctorNurse.setRole(3);

        doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setDoctorName("DoctorTest");
        doctor.setDoctorSurname("DoctorTest");
        doctor.setCategory(3);
        doctor.setLogin("doctor");
        doctor.setPassword("doctor");
        doctor.setRole(1);
        doctor.setCountOfPatients(2);



        hospitalCard = new HospitalCard();
        hospitalCard.setHospitalCardId(5);
        hospitalCard.setDoctorId(6);
        hospitalCard.setPatientId(7);
        hospitalCard.setDiagnosis("diagnosis test");
        hospitalCard.setProcedures("procedure test");
        hospitalCard.setMedications("medications test");
        hospitalCard.setOperations("operations test");



        String datetime = "2023-11-09 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        appointment = new Appointment();
        appointment.setAppointmentId(1);
        appointment.setDoctorId(1);
        appointment.setPatientId(1);
        appointment.setAppointmentData(LocalDateTime.parse(datetime, formatter));

        appointmentList.add(appointment);


    }

    @Test
    void executeTestWithOutSort() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("currentUser")).thenReturn(doctorNurse);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("page")).thenReturn("1");
        when(req.getParameter("sort")).thenReturn(null);
        when(req.getSession()).thenReturn(session);

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.GET_ALL_DOCTOR)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true);
        when(rsOne.next()).thenReturn(false);
        when(rsOne.getInt("doctor_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("doctor_name")).thenReturn(doctor.getDoctorName());
        when(rsOne.getString("doctor_surname")).thenReturn(doctor.getDoctorSurname());
        when(rsOne.getInt("category_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("login")).thenReturn(doctor.getLogin());
        when(rsOne.getString("password")).thenReturn(doctor.getPassword());
        when(rsOne.getInt("role_id")).thenReturn(doctor.getRole().getTitleId());
        when(rsOne.getInt("countofpatients")).thenReturn(doctor.getCountOfPatients());



        when(con.prepareStatement(AttributFinal.GET_ALL_HOSPITALCARD_LIMIT)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true);
        when(rsTwo.next()).thenReturn(false);
        when(rsOne.getInt("hospitalcard_id")).thenReturn(hospitalCard.getHospitalCardId());
        when(rsOne.getInt("patientid")).thenReturn(hospitalCard.getPatientId());
        when(rsOne.getInt("doctorid")).thenReturn(hospitalCard.getDoctorId());
        when(rsOne.getString("diagnosis")).thenReturn(hospitalCard.getDiagnosis());
        when(rsOne.getString("procedures")).thenReturn(hospitalCard.getProcedures());
        when(rsOne.getString("medications")).thenReturn(hospitalCard.getMedications());
        when(rsOne.getString("operations")).thenReturn(hospitalCard.getOperations());


        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.COUNT_OF_HOSPITALCARD)).thenReturn(psThree);
        when(psThree.executeQuery()).thenReturn(rsThree);
        when(rsThree.next()).thenReturn(true);
        when(rsThree.getInt(1)).thenReturn(1);


        HospitalCardList hospitalCardList = new HospitalCardList();
        String actual = hospitalCardList.execute(req, resp);
        String expected = "hospitalcardlist.jsp";
        assertEquals(actual, expected);

    }





    @Test
    void executeWithSortTest() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("currentUser")).thenReturn(doctorNurse);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("page")).thenReturn("1");
        when(req.getParameter("sort")).thenReturn(sort);
        when(req.getSession()).thenReturn(session);


        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.GET_ALL_DOCTOR)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true);
        when(rsOne.next()).thenReturn(false);
        when(rsOne.getInt("doctor_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("doctor_name")).thenReturn(doctor.getDoctorName());
        when(rsOne.getString("doctor_surname")).thenReturn(doctor.getDoctorSurname());
        when(rsOne.getInt("category_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("login")).thenReturn(doctor.getLogin());
        when(rsOne.getString("password")).thenReturn(doctor.getPassword());
        when(rsOne.getInt("role_id")).thenReturn(doctor.getRole().getTitleId());
        when(rsOne.getInt("countofpatients")).thenReturn(doctor.getCountOfPatients());


        String query = AttributFinal.SORT_HOSPITALCARD + sort + AttributFinal.LIMIT_HOSPITALCARD;

        when(con.prepareStatement(query)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true);
        when(rsTwo.next()).thenReturn(false);
        when(rsOne.getInt("hospitalcard_id")).thenReturn(hospitalCard.getHospitalCardId());
        when(rsOne.getInt("patientid")).thenReturn(hospitalCard.getPatientId());
        when(rsOne.getInt("doctorid")).thenReturn(hospitalCard.getDoctorId());
        when(rsOne.getString("diagnosis")).thenReturn(hospitalCard.getDiagnosis());
        when(rsOne.getString("procedures")).thenReturn(hospitalCard.getProcedures());
        when(rsOne.getString("medications")).thenReturn(hospitalCard.getMedications());
        when(rsOne.getString("operations")).thenReturn(hospitalCard.getOperations());


        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.COUNT_OF_HOSPITALCARD)).thenReturn(psThree);
        when(psThree.executeQuery()).thenReturn(rsThree);
        when(rsThree.next()).thenReturn(true);
        when(rsThree.getInt(1)).thenReturn(1);


        HospitalCardList hospitalCardList = new HospitalCardList();
        String actual = hospitalCardList.execute(req, resp);
        String expected = "hospitalcardlist.jsp";
        assertEquals(actual, expected);
    }

    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }
}