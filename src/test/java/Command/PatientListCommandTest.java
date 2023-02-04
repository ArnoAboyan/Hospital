package Command;

import DAO.DAOException;
import DAO.impl.AppointmentDao;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class PatientListCommandTest {
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
    Doctor doctorAdmin;
    Doctor doctorDoctor;
    Doctor doctor;

    Patient patient;

    String dateofbirth;

    HospitalCard hospitalCard;

    //MOCK CLASSES//
    AppointmentDao appointmentDao = mock(AppointmentDao.class);
    AppointmentListCommand appointmentListCommandMock = mock(AppointmentListCommand.class);


    //OTHER//
    String sort = "patient_name";


    @BeforeEach
    void setUp (){
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

        doctorAdmin = new Doctor();
        doctorAdmin.setDoctorId(4);
        doctorAdmin.setLogin("admin");
        doctorAdmin.setPassword("admin");
        doctorAdmin.setRole(1);

        doctorDoctor = new Doctor();
        doctorDoctor.setDoctorId(5);
        doctorDoctor.setLogin("doctor");
        doctorDoctor.setPassword("doctor");
        doctorDoctor.setRole(2);

        doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setDoctorName("DoctorTest");
        doctor.setDoctorSurname("DoctorTest");
        doctor.setCategory(3);
        doctor.setLogin("doctor");
        doctor.setPassword("doctor");
        doctor.setRole(1);
        doctor.setCountOfPatients(2);


        dateofbirth = "2021-11-09";
        patient = new Patient();
        patient.setPatientId(3);
        patient.setPatientName("patientTest");
        patient.setPatientSurname("patientTest");
        patient.setPatientDateOfBirth(Date.valueOf(dateofbirth));
        patient.setPatientGender("male");


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
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(session.getAttribute("currentUser")).thenReturn(doctorAdmin);
        when(req.getSession()).thenReturn(session);

        when(req.getParameter("page")).thenReturn("1");
        when(req.getParameter("sort")).thenReturn(null);

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.GET_DOCTOR_BY_ROLE)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(false);
        when(rsOne.getInt("doctor_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("doctor_name")).thenReturn(doctor.getDoctorName());
        when(rsOne.getString("doctor_surname")).thenReturn(doctor.getDoctorSurname());
        when(rsOne.getInt("category_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("login")).thenReturn(doctor.getLogin());
        when(rsOne.getString("password")).thenReturn(doctor.getPassword());
        when(rsOne.getInt("role_id")).thenReturn(doctor.getRole().getTitleId());
        when(rsOne.getInt("countofpatients")).thenReturn(doctor.getCountOfPatients());

        when(con.prepareStatement(AttributFinal.GET_ALL_PATIENT_LIMIT)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true).thenReturn(false);
        when(rsTwo.getInt("patient_id")).thenReturn(patient.getPatientId());
        when(rsTwo.getString("patient_name")).thenReturn(patient.getPatientName());
        when(rsTwo.getString("patient_surname")).thenReturn(patient.getPatientSurname());
        when(rsTwo.getString("patient_date_of_birth")).thenReturn(String.valueOf(patient.getPatientDateOfBirth()));
        when(rsTwo.getString("gender")).thenReturn(patient.getPatientGender());
        when(rsTwo.getString("phone")).thenReturn(patient.getPatientPhoneString());


        when(con.prepareStatement(AttributFinal.COUNT_OF_PATIENT)).thenReturn(psThree);
        when(psThree.executeQuery()).thenReturn(rsThree);
        when(rsThree.next()).thenReturn(true).thenReturn(false);
        when(rsThree.getInt(1)).thenReturn(1);

        PatientListCommand patientListCommand = new PatientListCommand();

        String actual = patientListCommand.execute(req, resp);
        String expected = "patientlist.jsp";
        assertEquals(actual, expected);
    }

    @Test
    void executeWithSortTest() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("currentUser")).thenReturn(doctorAdmin);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("page")).thenReturn("1");
        when(req.getParameter("sort")).thenReturn(sort);
        when(req.getSession()).thenReturn(session);

        when(req.getParameter("patientsfordoctorid")).thenReturn("3");

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);

        when(con.prepareStatement(AttributFinal.GET_DOCTOR_BY_ROLE)).thenReturn(psThree);
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

        String query = AttributFinal.SORT_PATIENT + sort + AttributFinal.LIMIT_PATIENT;

        when(con.prepareStatement(query)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(false);
        when(rsOne.getInt("patient_id")).thenReturn(patient.getPatientId());
        when(rsOne.getString("patient_name")).thenReturn(patient.getPatientName());
        when(rsOne.getString("patient_surname")).thenReturn(patient.getPatientSurname());
        when(rsOne.getString("patient_date_of_birth")).thenReturn(String.valueOf(patient.getPatientDateOfBirth()));
        when(rsOne.getString("gender")).thenReturn(patient.getPatientGender());
        when(rsOne.getString("phone")).thenReturn(patient.getPatientPhoneString());


        when(con.prepareStatement(AttributFinal.COUNT_OF_PATIENT)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true).thenReturn(false);
        when(rsTwo.getInt(1)).thenReturn(1);;

        PatientListCommand patientListCommand = new PatientListCommand();


        String actual = patientListCommand.execute(req, resp);
        String expected = "patientlist.jsp";
        assertEquals(actual, expected);
    }


    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }
}