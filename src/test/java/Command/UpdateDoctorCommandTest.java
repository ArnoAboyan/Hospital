package Command;

import DAO.DAOException;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Category;
import entitys.Doctor;
import entitys.Role;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateDoctorCommandTest {
    // mock connection to DB//
    MockedStatic<ConnectionPool> dsStatic = mockStatic(ConnectionPool.class);
    static DataSource dataSourceMock;
    Connection con;
    PreparedStatement psOne;
    ResultSet rsOne;
    PreparedStatement psTwo;
    ResultSet rsTwo;


    //ENTITYS//

    Doctor doctor;

    //MOCK CLASSES//



    //OTHER//
    String sort = "appointments_data";

    @BeforeEach
    void SetUp(){
        //CONFIG MOCK CONNECTION TO DB//

        con = mock(Connection.class);
        dataSourceMock = mock(DataSource.class);


        // PREPARED STATMENT //
        psOne = mock(PreparedStatement.class);
        rsOne = mock(ResultSet.class);
        psTwo = mock(PreparedStatement.class);
        rsTwo = mock(ResultSet.class);

        //OTHER SETTINGS FOR TEST//

        doctor = new Doctor();
        doctor.setDoctorId(6);
        doctor.setDoctorName("DoctorTest");
        doctor.setDoctorSurname("DoctorTest");
        doctor.setLogin("DoctorTest");
        doctor.setPassword("DoctorTest");
        doctor.setCategory(2);
        doctor.setRole(2);

    }

    @Test
    void executeTest() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("doctorid")).thenReturn(String.valueOf(doctor.getDoctorId()));
        when(req.getParameter("name")).thenReturn(doctor.getDoctorName());
        when(req.getParameter("surname")).thenReturn(doctor.getDoctorSurname());
        when(req.getParameter("login")).thenReturn(doctor.getLogin());
        when(req.getParameter("password")).thenReturn(doctor.getPassword());
        when(req.getParameter("category")).thenReturn(doctor.getCategory().getTitle());
        when(req.getParameter("role")).thenReturn(doctor.getRole().getTitle());

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.CHECK_DOCTOR_AVAILABILITY_BY_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(true);
        when(rsOne.getInt("doctor_id")).thenReturn(6);

        when(con.prepareStatement(AttributFinal.UPDATE_DOCTOR_BY_ID)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true).thenReturn(false);

        UpdateDoctorCommand updateDoctorCommand = new UpdateDoctorCommand();

        String actual = updateDoctorCommand.execute(req, resp);
        String expected = "controller?command=adminpagecommand&page=1";
        assertEquals(actual, expected);

    }
    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }
}