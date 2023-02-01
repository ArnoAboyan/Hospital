package Command;

import DAO.DAOException;
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

class DeleteAppointmentCommandTest {
//    mock connection to DB//

    MockedStatic<ConnectionPool> dsStatic = mockStatic(ConnectionPool.class);
    static DataSource dataSourceMock;
    Connection con;
    PreparedStatement psOne;
    ResultSet rsOne;
    PreparedStatement psTwo;
    ResultSet rsTwo;

    Appointment appointment;

    String datetimeMock;


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



        datetimeMock = "2123-01-31T18:03";

        String datetime = "2023-11-09 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        appointment = new Appointment();
        appointment.setAppointmentId(30);
        appointment.setDoctorId(22);
        appointment.setPatientId(9);
        appointment.setAppointmentData(LocalDateTime.parse(datetime, formatter));



    }

    @Test
    void executeTest() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getSession()).thenReturn(session);


        when(req.getParameter("appointmentid")).thenReturn(String.valueOf(appointment.getAppointmentId()));




        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);

        when(con.prepareStatement(AttributFinal.CHECK_APPOINTMENT_AVAILABILITY_BY_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(true);


        when(con.prepareStatement(AttributFinal.DELETEAPPOINTMENT)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true).thenReturn(false);

        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand();

        String actual = deleteAppointmentCommand.execute(req, resp);
        String expected = "controller?command=appointmentpagecommand&page=1";
        assertEquals(actual, expected);

    }


    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }
}

