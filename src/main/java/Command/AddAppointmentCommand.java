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

        int doctorID = Integer.parseInt(req.getParameter("doctor"));
        int patientId = Integer.parseInt(req.getParameter("patientid"));
        String appointmentdate = (req.getParameter("appointmentdata"));
        System.out.println(appointmentdate);


        Appointment appointment = new Appointment();

        if (doctorID > 0) {
            appointment.setDoctorId(doctorID);
        } else {
            logger.error("Problem with searching  patient " + doctorID );
            throw new CommandException ("Problem with searching  doctor");
        }

        if (patientId > 0) {
            appointment.setPatientId(patientId);
        } else {
            logger.error("Problem with searching  patient " + patientId );
            throw new CommandException ("Problem with searching  patient");
        }
        if (appointmentdate != null) {
            appointment.setAppointmentData(LocalDateTime.parse(appointmentdate));
        } else {
            logger.error("Problem with searching  patient " + appointmentdate );
            throw new CommandException ("Problem with searching  appointment date");
        }


        AppointmentDao appointmentDao = new AppointmentDao();
        appointmentDao.create(appointment);

        logger.info("New appointment create => " + appointment);



        return "controller?command=patientlistcommand&page=1";

    }
}