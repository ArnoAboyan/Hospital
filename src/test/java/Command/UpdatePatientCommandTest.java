package Command;

import DAO.DAOException;
import DAO.impl.PatientDao;
import Util.AttributFinal;
import Util.ConnectionPool;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class UpdatePatientCommandTest {
    // mock connection to DB//
    MockedStatic<ConnectionPool> dsStatic = mockStatic(ConnectionPool.class);
    static DataSource dataSourceMock;
    Connection con;
    PreparedStatement psOne;
    ResultSet rsOne;
    PreparedStatement psTwo;
    ResultSet rsTwo;


    //ENTITYS//

    Patient patient;

    //MOCK CLASSES//
    PatientDao patientDao;

    //OTHER//
    String dateofbirth;


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

        //OTHER SETTINGS FOR TEST//
        patientDao = mock(PatientDao.class);

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

        when(req.getSession()).thenReturn(session);

        when(req.getParameter("patientid")).thenReturn(String.valueOf(patient.getPatientId()));
        when(req.getParameter("name")).thenReturn(patient.getPatientName());
        when(req.getParameter("surname")).thenReturn(patient.getPatientSurname());
        when(req.getParameter("phone")).thenReturn("80994455566");
        when(req.getParameter("birthday")).thenReturn(dateofbirth);
        when(req.getParameter("gender")).thenReturn(patient.getPatientGender());


        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.CHECK_PATIENT_AVAILABILITY_BY_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(true);
        when(rsOne.getInt("patient_id")).thenReturn(3);

        when(con.prepareStatement(AttributFinal.UPDATE_PATIENT_BY_ID)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true).thenReturn(false);

        UpdatePatientCommand updatePatientCommand = new UpdatePatientCommand();

        String actual = updatePatientCommand.execute(req, resp);
        String expected = "controller?command=patientlistcommand&page=1";
        assertEquals(actual, expected);


    }

    @AfterEach
    public void tearDown() {
        dsStatic.close();
    }

}