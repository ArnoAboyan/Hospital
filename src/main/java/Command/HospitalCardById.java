package Command;

import DAO.DAOException;
import DAO.impl.AppointmentDao;
import DAO.impl.DoctorDao;
import DAO.impl.HospitalCardDao;
import DAO.impl.PatientDao;
import entitys.Appointment;
import entitys.Doctor;
import entitys.HospitalCard;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class HospitalCardById implements Command {
// GET HOSPITAL CARD BY HOSPITAL CARD ID FOR NURSES//
    static final Logger logger = Logger.getLogger(HospitalCardById.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException, SQLException {
        logger.info("Execute ==> HospitalCardById...");
        int hospitalCardId = Integer.parseInt(req.getParameter("hospitalCardId"));

        //check role
        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        if (user == null) {
            return "login.jsp";
        }

        String role = user.getRole().getTitle();

        if (!role.equalsIgnoreCase("nurse")) {
            logger.error("Check role " + role + " FALSE");
            return "error.jsp";
        }

        logger.info("Check role " + role + " CORRECT");

        HospitalCardDao hospitalCardDao= new HospitalCardDao();
        AppointmentDao appointmentDao = new AppointmentDao();
        PatientDao patientDao = new PatientDao();
        DoctorDao doctorDao = new DoctorDao();


        int doctorId = hospitalCardDao.getByID(hospitalCardId).getDoctorId();
        logger.info("Geting doctorId... " + doctorId + " successful");
        int patientId = hospitalCardDao.getByID(hospitalCardId).getPatientId();
        logger.info("Geting patientId... " + patientId + " successful");


        Doctor doctor;
        Patient patient ;
        HospitalCard hospitalCard ;
        List<Appointment> appointmentCardByPatientDoctorId ;


        //set attributes to HospitalCard//
        try {
            doctor = doctorDao.getByID(doctorId);
            patient = patientDao.getByID(patientId);
            hospitalCard = hospitalCardDao.getByID(hospitalCardId);
            appointmentCardByPatientDoctorId = appointmentDao.getAppointmentByPatientAndDoctorId(doctorId, patientId);


            req.setAttribute("hospitalcard", hospitalCard);
            req.setAttribute("doctor", doctor);
            req.setAttribute("patient", patient);
            req.setAttribute("visitcard", appointmentCardByPatientDoctorId);

            logger.info("req.setAttributes => Correct");
        } catch (DAOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return "hospitalcardfornurse.jsp";
    }
}
