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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.print.Doc;
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

class ChangeHospitalCardCommandTest {
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
    Doctor doctorNurse;
    Doctor doctorDoctor;

    //MOCK CLASSES//



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
        psThree = mock(PreparedStatement.class);
        rsThree = mock(ResultSet.class);

        //OTHER SETTINGS FOR TEST//
        doctorNurse= new Doctor();
        doctorNurse.setRole(3);

        doctorDoctor= new Doctor();
        doctorDoctor.setRole(2);


    }

    @Test
    void executeTestForNurse() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("currentUser")).thenReturn(doctorNurse);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getSession()).thenReturn(session);


        when(req.getParameter("doctorid")).thenReturn("22");
        when(req.getParameter("patientid")).thenReturn("18");
        when(req.getParameter("procedures")).thenReturn("some procedures");
        when(req.getParameter("medications")).thenReturn("some medications");
        when(req.getParameter("operations")).thenReturn("some operations");
        when(req.getParameter("diagnosis")).thenReturn("some diagnosis");

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);


        when(con.prepareStatement(AttributFinal.CHECK_HOSPITALCARD_AVALABILE)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(false);

        when(con.prepareStatement(AttributFinal.UPDATE_HOSPITAL_CARD)).thenReturn(psThree);
        when(psThree.executeQuery()).thenReturn(rsThree);
        when(rsThree.next()).thenReturn(true).thenReturn(false);


        when(con.prepareStatement(AttributFinal.ADDHOSPITALCARD)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true);
        when(rsTwo.next()).thenReturn(false);
        when(rsTwo.getInt("appointment_id")).thenReturn(2);
        when(rsTwo.getInt("doctor_id")).thenReturn(22);
        when(rsTwo.getInt("patient_id")).thenReturn(33);
        when(rsTwo.getString("procedures")).thenReturn("some procedures");
        when(rsTwo.getString("medications")).thenReturn("some medications");
        when(rsTwo.getString("operations")).thenReturn("some operations");
        when(rsTwo.getString("diagnosis")).thenReturn("some diagnosis");



        ChangeHospitalCardCommand changeHospitalCardCommand = new ChangeHospitalCardCommand();


        String actual = changeHospitalCardCommand.execute(req, resp);
        String expected = "controller?command=hospitalcardbyid";
        assertEquals(actual, expected);
    }

    @Test
    void executeTestForDoctor() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("currentUser")).thenReturn(doctorDoctor);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getSession()).thenReturn(session);


        when(req.getParameter("doctorid")).thenReturn("22");
        when(req.getParameter("patientid")).thenReturn("18");
        when(req.getParameter("procedures")).thenReturn("some procedures");
        when(req.getParameter("medications")).thenReturn("some medications");
        when(req.getParameter("operations")).thenReturn("some operations");
        when(req.getParameter("diagnosis")).thenReturn("some diagnosis");

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);


        when(con.prepareStatement(AttributFinal.CHECK_HOSPITALCARD_AVALABILE)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(false);

        when(con.prepareStatement(AttributFinal.UPDATE_HOSPITAL_CARD)).thenReturn(psThree);
        when(psThree.executeQuery()).thenReturn(rsThree);
        when(rsThree.next()).thenReturn(true).thenReturn(false);


        when(con.prepareStatement(AttributFinal.ADDHOSPITALCARD)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true);
        when(rsTwo.next()).thenReturn(false);
        when(rsTwo.getInt("appointment_id")).thenReturn(2);
        when(rsTwo.getInt("doctor_id")).thenReturn(22);
        when(rsTwo.getInt("patient_id")).thenReturn(33);
        when(rsTwo.getString("procedures")).thenReturn("some procedures");
        when(rsTwo.getString("medications")).thenReturn("some medications");
        when(rsTwo.getString("operations")).thenReturn("some operations");
        when(rsTwo.getString("diagnosis")).thenReturn("some diagnosis");



        ChangeHospitalCardCommand changeHospitalCardCommand = new ChangeHospitalCardCommand();


        String actual = changeHospitalCardCommand.execute(req, resp);
        String expected = "controller?command=hospitalcardbypatientid";
        assertEquals(actual, expected);
    }


    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }
}