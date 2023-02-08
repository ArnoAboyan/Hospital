package Command;

import DAO.DAOException;
import Util.AttributFinal;
import Util.ConnectionPool;
import entitys.Doctor;
import entitys.Patient;
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

class DeletePatientCommandTest {
    //    mock connection to DB//
    MockedStatic<ConnectionPool> dsStatic = mockStatic(ConnectionPool.class);
    static DataSource dataSourceMock;
    Connection con;
    PreparedStatement psOne;
    ResultSet rsOne;
    PreparedStatement psTwo;
    ResultSet rsTwo;

    Doctor doctor;
    Patient patient;

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



        patient = new Patient();
        patient.setPatientId(2);


        doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setDoctorName("DoctorTest");
        doctor.setDoctorSurname("DoctorTest");
        doctor.setCategory(3);
        doctor.setLogin("asdf");
        doctor.setPassword("password");
        doctor.setRole(1);
        doctor.setCountOfPatients(2);

    }


    @Test
    void executeTest() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(doctor);
        when(req.getParameter("deletepatient")).thenReturn("2");


        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);

        when(con.prepareStatement(AttributFinal.CHECK_PATIENT_AVAILABILITY_BY_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(true);
        when(rsOne.getInt("patient_id")).thenReturn(2);


        when(con.prepareStatement(AttributFinal.DELETEPATIENT)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true).thenReturn(false);

        DeletePatientCommand deletePatientCommand = new DeletePatientCommand();

        String actual = deletePatientCommand.execute(req, resp);
        String expected = "controller?command=patientlistcommand&page=1";
        assertEquals(actual, expected);

    }

    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }
}
