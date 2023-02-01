package Command;

import DAO.DAOException;
import DAO.impl.AppointmentDao;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Appointment;
import entitys.Doctor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
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


class AppointmentListCommandTest {
// mock connection to DB//
    MockedStatic<ConnectionPool> dsStatic = mockStatic(ConnectionPool.class);
    static DataSource dataSourceMock;
    Connection con;

    PreparedStatement psOne;
    ResultSet rsOne;
    PreparedStatement psTwo;
    ResultSet rsTwo;

    //ENTITYS//
    List<Appointment> appointmentList = new ArrayList<>();
    Appointment appointment = new Appointment();
    Doctor doctor = new Doctor();

    //MOCK CLASSES//
    AppointmentDao appointmentDao = mock(AppointmentDao.class);
    AppointmentListCommand appointmentListCommandMock = mock(AppointmentListCommand.class);


    //OTHER//
    String sort = "appointments_data";



    @BeforeEach
    public void setUp() {

        //CONFIG MOCK CONNECTION TO DB//

        con = mock(Connection.class);
        dataSourceMock = mock(DataSource.class);


        // PREPARED STATMENT //
        psOne = mock(PreparedStatement.class);
        rsOne = mock(ResultSet.class);
        psTwo = mock(PreparedStatement.class);
        rsTwo = mock(ResultSet.class);

        //OTHER SETTINGS FOR TEST//


        doctor.setDoctorId(1);
        doctor.setLogin("asdf");
        doctor.setPassword("password");
        doctor.setRole(1);


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
    public void executeTestWithSortNull() throws SQLException, DAOException, CommandException {

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("currentUser")).thenReturn(doctor);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("page")).thenReturn("1");
        when(req.getParameter("sort")).thenReturn(null);
        when(req.getSession()).thenReturn(session);

//        dsStatic = mockStatic(ConnectionPool.class);

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.GET_ALL_APPOINTMENT_LIMIT)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true);
        when(rsOne.next()).thenReturn(false);
        when(rsOne.getInt("appointment_id")).thenReturn(appointment.getAppointmentId());
        when(rsOne.getInt("doctor_id")).thenReturn(appointment.getDoctorId());
        when(rsOne.getInt("patient_id")).thenReturn(appointment.getPatientId());
        when(rsOne.getString("appointments_data")).thenReturn(String.valueOf(appointment.getAppointmentData()));


        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.COUNT_OF_APPOINTMENT)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true);
        when(rsTwo.getInt(1)).thenReturn(1);

        AppointmentListCommand appointmentListCommand = new AppointmentListCommand();


        String actual = appointmentListCommand.execute(req, resp);
        String expected = "appointmentlist.jsp";
        assertEquals(actual, expected);

    }

    //    @Test
//    public void executeWithOutSortTest () throws SQLException, DAOException, CommandException {
//
//
//        HttpSession session = mock(HttpSession.class);
//        HttpServletRequest req = mock(HttpServletRequest.class);
//        when(req.getParameter("page")).thenReturn("1");
//        when(req.getParameter("sort")).thenReturn(null);
//
//
//        when(dataSourceMock.getConnection()).thenReturn(con);
//        when(con.prepareStatement(AttributFinal.GET_ALL_APPOINTMENT_LIMIT)).thenReturn(psOne);
//        when(psOne.executeQuery()).thenReturn(rsOne);
//        when(rsOne.next()).thenReturn(true);
//        when(rsOne.next()).thenReturn(false);
//        when(rsOne.getInt("appointment_id")).thenReturn(appointment.getAppointmentId());
//        when(rsOne.getInt("doctor_id")).thenReturn(appointment.getDoctorId());
//        when(rsOne.getInt("patient_id")).thenReturn(appointment.getPatientId());
//        when(rsOne.getString("appointments_data")).thenReturn(String.valueOf(appointment.getAppointmentData()));
//
//
//        when(dataSourceMock.getConnection()).thenReturn(con);
//        when(con.prepareStatement(AttributFinal.COUNT_OF_APPOINTMENT)).thenReturn(psTwo);
//        when(psTwo.executeQuery()).thenReturn(rsTwo);
//        when(rsTwo.next()).thenReturn(true);
//        when(rsTwo.getInt(1)).thenReturn(1);
//
//        when(req.getSession()).thenReturn(session);
//
//
//        AppointmentListCommand appointmentListCommand = new AppointmentListCommand();
//
//        String actual = appointmentListCommand.executeWithOutSort(req, appointmentDao);
//        String expected = "appointmentlist.jsp";
//        assertEquals(actual, expected);
//
//
//    }
//
    @Test
    public void executeWithSortTest() throws DAOException, CommandException, SQLException {


        HttpSession session = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getParameter("page")).thenReturn("1");
        when(req.getParameter("sort")).thenReturn(sort);

        String query = AttributFinal.SORT_APPOINTMENT + sort + AttributFinal.LIMIT_APPOINTMENT;

        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(query)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true);
        when(rsOne.next()).thenReturn(false);
        when(rsOne.getInt("appointment_id")).thenReturn(appointment.getAppointmentId());
        when(rsOne.getInt("doctor_id")).thenReturn(appointment.getDoctorId());
        when(rsOne.getInt("patient_id")).thenReturn(appointment.getPatientId());
        when(rsOne.getString("appointments_data")).thenReturn(String.valueOf(appointment.getAppointmentData()));


        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.COUNT_OF_APPOINTMENT)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true);
        when(rsTwo.getInt(1)).thenReturn(1);


        when(req.getSession()).thenReturn(session);


        AppointmentListCommand appointmentListCommand = new AppointmentListCommand();

        String actual = appointmentListCommand.executeWithSort(req, appointmentDao, sort);
        String expected = "appointmentlist.jsp";
        assertEquals(actual, expected);
    }

    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }
}