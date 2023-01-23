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

import java.sql.SQLException;
import java.util.List;

public class HospitalCardById implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException, SQLException {
        int hospitalCardId = Integer.parseInt(req.getParameter("hospitalCardId"));



        HospitalCardDao hospitalCardDao= new HospitalCardDao();
        AppointmentDao appointmentDao = new AppointmentDao();
        PatientDao patientDao = new PatientDao();
        DoctorDao doctorDao = new DoctorDao();

        int doctorId = hospitalCardDao.getByID(hospitalCardId).getDoctorId();
        int patientId = hospitalCardDao.getByID(hospitalCardId).getPatientId();
        Doctor doctor;
        Patient patient ;
        HospitalCard hospitalCard ;
        List<Appointment> appointmentCardByPatientDoctorId ;


        try {
            doctor = doctorDao.getByID(doctorId);
            patient = patientDao.getByID(patientId);
            hospitalCard = hospitalCardDao.getByID(hospitalCardId);
            appointmentCardByPatientDoctorId = appointmentDao.getAppointmentByPatientAndDoctorId(doctorId, patientId);


            req.setAttribute("hospitalcard", hospitalCard);
            req.setAttribute("doctor", doctor);
            req.setAttribute("patient", patient);
            req.setAttribute("visitcard", appointmentCardByPatientDoctorId);


        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return "hospitalcardfornurse.jsp";
    }
}
