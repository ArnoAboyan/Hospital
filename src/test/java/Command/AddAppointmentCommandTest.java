package Command;

import DAO.DAOException;
import DAO.impl.AppointmentDao;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Appointment;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddAppointmentCommandTest {
    AppointmentDao appointmentDaoMock = mock(AppointmentDao.class);

//    mock connection to DB//

    MockedStatic<ConnectionPool> dsStatic = mockStatic(ConnectionPool.class);
    static DataSource dataSourceMock;
    Connection con;
    PreparedStatement psOne;
    ResultSet rsOne;
    PreparedStatement psTwo;
    ResultSet rsTwo;

    Appointment appointment;
//    int doctorid;
//    int patientid;
    String datetimeMock;

    @BeforeEach
    void setUp() {

        //CONFIG MOCK CONNECTION TO DB//

        con = mock(Connection.class);
        dataSourceMock = mock(DataSource.class);


        // PREPARED STATMENT //
        psOne = mock(PreparedStatement.class);
        rsOne = mock(ResultSet.class);
        psTwo = mock(PreparedStatement.class);
        rsTwo = mock(ResultSet.class);



        datetimeMock = "2023-01-31T18:03";

        String datetime = "2023-11-09 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        appointment = new Appointment();
        appointment.setDoctorId(22);
        appointment.setPatientId(9);
        appointment.setAppointmentData(LocalDateTime.parse(datetime, formatter));



    }

    @Test
   public void executeTest() throws DAOException, CommandException, SQLException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getSession()).thenReturn(session);

        when(req.getParameter("doctor")).thenReturn("22");
        when(req.getParameter("patientid")).thenReturn("9");
        when(req.getParameter("appointmentdata")).thenReturn(datetimeMock);



        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.ADDDAPPOINTMENT)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsTwo.next()).thenReturn(true).thenReturn(false);

        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand();

        String actual = addAppointmentCommand.execute(req, resp);
        String expected = "controller?command=patientlistcommand&page=1";
        assertEquals(actual, expected);
    }
    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }
}