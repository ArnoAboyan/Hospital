package Command;



import DAO.DAOException;
import DAO.impl.AppointmentDao;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;
import entitys.Appointment;
import entitys.Doctor;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class AddAppointmentCommand implements Command {

// ADD APPOINTMENT BY DOCTOR AND PATIENT ID
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        int doctorID = Integer.parseInt(req.getParameter("doctor"));
        int patientId = Integer.parseInt(req.getParameter("patientid"));
        String appointmentdate = (req.getParameter("appointmentdata"));
        System.out.println(appointmentdate);

        Appointment appointment = new Appointment();

        if (doctorID > 0) {
            appointment.setDoctorId(doctorID);
        } else throw new CommandException("The entered name is not correct");
        if (patientId > 0) {
            appointment.setPatientId(patientId);
        } else throw new CommandException("The entered name is not correct");
        if (appointmentdate != null) {
            appointment.setAppointmentData(LocalDateTime.parse(appointmentdate));
        } else throw new CommandException("The entered name is not correct");


        System.out.println(appointment);

        AppointmentDao appointmentDao = new AppointmentDao();
        appointmentDao.create(appointment);


        return "controller?command=patientlistcommand&page=1";

    }
}