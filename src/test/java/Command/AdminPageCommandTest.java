package Command;

import DAO.DAOException;
import DAO.impl.PatientDao;
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
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class AdminPageCommandTest {
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
    Doctor doctor;

    //OTHER//
    String sort = "doctor_name";



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

        //OTHER SETTINGS FOR TEST//


        doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setDoctorName("DoctorTest");
        doctor.setDoctorSurname("DoctorTest");
        doctor.setCategory(3);
        doctor.setLogin("doctor");
        doctor.setPassword("doctor");
        doctor.setRole(1);
        doctor.setCountOfPatients(2);

    }


    @Test
    void executeTest() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("currentUser")).thenReturn(doctor);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("page")).thenReturn("1");
        when(req.getParameter("sort")).thenReturn(null);
        when(req.getSession()).thenReturn(session);

        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.GET_ALL_DOCTOR_LIMIT)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true);
        when(rsOne.next()).thenReturn(false);
        when(rsOne.getInt("doctor_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("doctor_name")).thenReturn(doctor.getDoctorName());
        when(rsOne.getString("doctor_surname")).thenReturn(doctor.getDoctorSurname());
        when(rsOne.getInt("category_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("login")).thenReturn(doctor.getLogin());
        when(rsOne.getString("password")).thenReturn(doctor.getPassword());
        when(rsOne.getInt("role_id")).thenReturn(doctor.getRole().getTitleId());
        when(rsOne.getInt("countofpatients")).thenReturn(doctor.getCountOfPatients());



        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.COUNT_OF_DOCTOR)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true);
        when(rsTwo.getInt(1)).thenReturn(1);


        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.GET_COUNT_PATIENT_BY_DOCTOR_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true);
        when(rsTwo.getInt(1)).thenReturn(1);

        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.UPDATE_COUNT_PATIENT_BY_DOCTOR_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(false);

        AdminPageCommand adminPageCommand = new AdminPageCommand();


        String actual = adminPageCommand.execute(req, resp);
        String expected = "admin.jsp";
        assertEquals(actual, expected);

    }

    @Test
    void executeWithSortTest() throws SQLException, DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("currentUser")).thenReturn(doctor);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("page")).thenReturn("1");
        when(req.getParameter("sort")).thenReturn(sort);
        when(req.getSession()).thenReturn(session);

        String query = AttributFinal.SORT_DOCTOR + sort + AttributFinal.LIMIT_DOCTOR;


        dsStatic.when(() -> ConnectionPool.getDataSource().getConnection()).thenReturn(dataSourceMock);
        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(query)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true);
        when(rsOne.next()).thenReturn(false);
        when(rsOne.getInt("doctor_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("doctor_name")).thenReturn(doctor.getDoctorName());
        when(rsOne.getString("doctor_surname")).thenReturn(doctor.getDoctorSurname());
        when(rsOne.getInt("category_id")).thenReturn(doctor.getDoctorId());
        when(rsOne.getString("login")).thenReturn(doctor.getLogin());
        when(rsOne.getString("password")).thenReturn(doctor.getPassword());
        when(rsOne.getInt("role_id")).thenReturn(doctor.getRole().getTitleId());
        when(rsOne.getInt("countofpatients")).thenReturn(doctor.getCountOfPatients());



        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.COUNT_OF_DOCTOR)).thenReturn(psTwo);
        when(psTwo.executeQuery()).thenReturn(rsTwo);
        when(rsTwo.next()).thenReturn(true);
        when(rsTwo.getInt(1)).thenReturn(1);


        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.GET_COUNT_PATIENT_BY_DOCTOR_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true);
        when(rsTwo.getInt(1)).thenReturn(1);

        when(dataSourceMock.getConnection()).thenReturn(con);
        when(con.prepareStatement(AttributFinal.UPDATE_COUNT_PATIENT_BY_DOCTOR_ID)).thenReturn(psOne);
        when(psOne.executeQuery()).thenReturn(rsOne);
        when(rsOne.next()).thenReturn(true).thenReturn(false);

        AdminPageCommand adminPageCommand = new AdminPageCommand();


        String actual = adminPageCommand.execute(req, resp);
        String expected = "admin.jsp";
        assertEquals(actual, expected);

    }

    @AfterEach
     void tearDown() {
        dsStatic.close();
    }

}