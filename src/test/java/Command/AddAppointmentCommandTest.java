package Command;

import DAO.DAOException;
import DAO.impl.AppointmentDao;
import entitys.Appointment;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddAppointmentCommandTest {
    AppointmentDao appointmentDaoMock = mock(AppointmentDao.class);


    Appointment appointment;
    int doctorid;
    int patientid;
    String datetimeMock;

    @BeforeEach
    void setUp() {
        datetimeMock = "2023-01-31T18:03";

        String datetime = "2023-11-09 10:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        appointment = new Appointment();
        appointment.setDoctorId(22);
        appointment.setPatientId(9);
        appointment.setAppointmentData(LocalDateTime.parse(datetime, formatter));

//
//       int doctorid = 22;
//       int patientid = 18;

    }

    @Test
   public void executeTest() throws DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getSession()).thenReturn(session);

        when(req.getParameter("doctor")).thenReturn("22");
        when(req.getParameter("patientid")).thenReturn("9");
        when(req.getParameter("appointmentdata")).thenReturn(datetimeMock);



        when(appointmentDaoMock.create(appointment)).thenReturn(true);

        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand();

        String actual = addAppointmentCommand.execute(req, resp);
        String expected = "controller?command=patientlistcommand&page=1";
        assertEquals(actual, expected);
    }

}