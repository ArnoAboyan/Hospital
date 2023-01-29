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
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class AddAppointmentCommand implements Command {

    static final Logger logger = Logger.getLogger(AddAppointmentCommand.class);

// ADD APPOINTMENT BY DOCTOR AND PATIENT ID
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        logger.info("Execute ==> AddAppointmentCommand...");
        Appointment appointment = new Appointment();

        LocalDateTime nowDateTime = LocalDateTime.now();

        int doctorID = Integer.parseInt(req.getParameter("doctor"));
        int patientId = Integer.parseInt(req.getParameter("patientid"));
        String appointmentdate = (req.getParameter("appointmentdata"));
        System.out.println(appointmentdate);

        if (appointmentdate == null) {
            logger.error("Problem with searching  appointmentdate " + appointmentdate);
            throw new CommandException("Problem with date => " + appointmentdate);
        } else {
            LocalDateTime actualdate = LocalDateTime.parse(appointmentdate);


            if (doctorID > 0) {
                appointment.setDoctorId(doctorID);
            } else {
                logger.error("Problem with searching  doctor " + doctorID);
                throw new CommandException("Problem with searching  doctor");
            }

            if (patientId > 0) {
                appointment.setPatientId(patientId);
            } else {
                logger.error("Problem with searching  patient " + patientId);
                throw new CommandException("Problem with searching  patient");
            }
            //DATE VERIFICATION//
            if (!actualdate.isBefore(nowDateTime)) {
                appointment.setAppointmentData(actualdate);
            } else {
                logger.error("The chosen time is already in the past " + actualdate);
                throw new CommandException("The chosen time is already in the past " + actualdate);
            }


            AppointmentDao appointmentDao = new AppointmentDao();
            appointmentDao.create(appointment);

            logger.info("New appointment create => " + appointment);


            return "controller?command=patientlistcommand&page=1";

        }
    }
}