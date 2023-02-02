package Command;

import DAO.DAOException;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Appointment;
import entitys.Doctor;
import entitys.HospitalCard;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class HospitalCardByPatientIdTest {
    //    mock connection to DB//
    MockedStatic<ConnectionPool> dsStatic = mockStatic(ConnectionPool.class);
    static DataSource dataSourceMock;
    Connection con;
    PreparedStatement psOne;
    ResultSet rsOne;
    PreparedStatement psTwo;
    ResultSet rsTwo;
    PreparedStatement psThree;
    ResultSet rsThree;
    PreparedStatement psFour;
    ResultSet rsFour;

    Doctor doctorNurse;
    Doctor doctor;
    Patient patient;
    Appointment appointment;
    HospitalCard hospitalCard;

    String datetimeMock;
    String dateofbirth;

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
        psFour = mock(PreparedStatement.class);
        rsFour = mock(ResultSet.class);


        doctorNurse = new Doctor();
        doctorNurse.setRole(3);

        hospitalCard = new HospitalCard();
        hospitalCard.setHospitalCardId(5);
        hospitalCard.setDoctorId(6);
        hospitalCard.setPatientId(7);
        hospitalCard.setDiagnosis("diagnosis test");
        hospitalCard.setProcedures("procedure test");
        hospitalCard.setMedications("medications test");
        hospitalCard.setOperations("operations test");



        datetimeMock = "2123-01-31T18:03";

        String datetime = "2023-11-09 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        appointment = new Appointment();
        appointment.setAppointmentId(2);
        appointment.setDoctorId(22);
        appointment.setPatientId(9);
        appointment.setAppointmentData(LocalDateTime.parse(datetime, formatter));

        doctor = new Doctor();
        doctor.setDoctorId(3);
        doctor.setDoctorName("DoctorTest");
        doctor.setDoctorSurname("DoctorTest");
        doctor.setLogin("DoctorTest");
        doctor.setPassword("DoctorTest");
        doctor.setCategory(2);
        doctor.setRole(2);


        dateofbirth = "2021-11-09";
        patient = new Patient();
        patient.setPatientId(3);
        patient.setPatientName("patientTest");
        patient.setPatientSurname("patientTest");
        patient.setPatientDateOfBirth(Date.valueOf(dateofbirth));
        patient.setPatientGender("male");


    }

    @Test
    void executeTest() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(session.getAttribute("currentUser")).thenReturn(doctorNurse);

        when(req.getSession()).thenReturn(session);

        when(req.getParameter("patientid")).thenReturn("4");
        when(req.getParameter("doctorid")).thenReturn("3");
        

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);


        when(con.prepareStatement(AttributFinal.GET_HOSPITALCARD_BY_PATIENT_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(false);
        when(rsOne.getInt("hospitalcard_id")).thenReturn(hospitalCard.getHospitalCardId());
        when(rsOne.getInt("patientid")).thenReturn(hospitalCard.getPatientId());
        when(rsOne.getInt("doctorid")).thenReturn(hospitalCard.getDoctorId());
        when(rsOne.getString("diagnosis")).thenReturn(hospitalCard.getDiagnosis());
        when(rsOne.getString("procedures")).thenReturn(hospitalCard.getProcedures());
        when(rsOne.getString("medications")).thenReturn(hospitalCard.getMedications());
        when(rsOne.getString("operations")).thenReturn(hospitalCard.getOperations());

        when(con.prepareStatement(AttributFinal.GET_APPOINTMENT_BY_DOCTOR_AND_PATIENT_ID)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true).thenReturn(false);
        when(rsTwo.getInt("appointment_id")).thenReturn(appointment.getAppointmentId());
        when(rsTwo.getInt("doctor_id")).thenReturn(appointment.getDoctorId());
        when(rsTwo.getInt("patient_id")).thenReturn(appointment.getPatientId());
        when(rsTwo.getString("appointments_data")).thenReturn("2023-01-13 19:37:00");


        when(con.prepareStatement(AttributFinal.GET_DOCTOR_BY_ID)).thenReturn(psThree);
        when(psThree.executeQuery()).thenReturn(rsThree);
        when(rsThree.next()).thenReturn(true).thenReturn(false);
        when(rsThree.getInt("doctor_id")).thenReturn(doctor.getDoctorId());
        when(rsThree.getString("doctor_name")).thenReturn(doctor.getDoctorName());
        when(rsThree.getString("doctor_surname")).thenReturn(doctor.getDoctorSurname());
        when(rsThree.getInt("category_id")).thenReturn(doctor.getDoctorId());
        when(rsThree.getString("login")).thenReturn(doctor.getLogin());
        when(rsThree.getString("password")).thenReturn(doctor.getPassword());
        when(rsThree.getInt("role_id")).thenReturn(doctor.getRole().getTitleId());
        when(rsThree.getInt("countofpatients")).thenReturn(doctor.getCountOfPatients());

        when(con.prepareStatement(AttributFinal.GET_PATIENT_BY_ID)).thenReturn(psFour);
        when(psFour.executeQuery()).thenReturn(rsFour);
        when(rsFour.next()).thenReturn(true).thenReturn(false);
        when(rsFour.getInt("patient_id")).thenReturn(patient.getPatientId());
        when(rsFour.getString("patient_name")).thenReturn(patient.getPatientName());
        when(rsFour.getString("patient_surname")).thenReturn(patient.getPatientSurname());
        when(rsFour.getString("patient_date_of_birth")).thenReturn(String.valueOf(patient.getPatientDateOfBirth()));
        when(rsFour.getString("gender")).thenReturn(patient.getPatientGender());
        when(rsFour.getString("phone")).thenReturn(patient.getPatientPhoneString());




        HospitalCardByPatientId hospitalCardByPatientId = new HospitalCardByPatientId();


        String actual = hospitalCardByPatientId.execute(req, resp);
        String expected = "hospitalcard.jsp";
        assertEquals(actual, expected);


    }


    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }

}