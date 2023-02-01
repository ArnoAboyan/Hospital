package Command;

import DAO.DAOException;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Doctor;
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
import static org.mockito.Mockito.when;

class DeleteDoctorCommandTest {
//    mock connection to DB//
    MockedStatic<ConnectionPool> dsStatic = mockStatic(ConnectionPool.class);
    static DataSource dataSourceMock;
    Connection con;
    PreparedStatement psOne;
    ResultSet rsOne;
    PreparedStatement psTwo;
    ResultSet rsTwo;

    Doctor doctor;


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



        doctor = new Doctor();
        doctor.setDoctorId(2);


    }

    @Test
    void executeTest() throws SQLException, DAOException, CommandException {

        HttpSession session = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getSession()).thenReturn(session);


        when(req.getParameter("deletedoctor")).thenReturn("2");

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);

        when(con.prepareStatement(AttributFinal.CHECK_DOCTOR_AVAILABILITY_BY_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(true);
        when(rsOne.getInt("doctor_id")).thenReturn(2);


        when(con.prepareStatement(AttributFinal.DELETEDOCTOR)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true).thenReturn(false);

        DeleteDoctorCommand deleteDoctorCommand = new DeleteDoctorCommand();

        String actual = deleteDoctorCommand.execute(req, resp);
        String expected = "controller?command=adminpagecommand&page=1";
        assertEquals(actual, expected);

    }


    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }
}