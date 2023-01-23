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

import java.util.List;

public class HospitalCardByPatientId implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        int patientId = Integer.parseInt(req.getParameter("patientid"));
        int doctorId = Integer.parseInt(req.getParameter("doctorid"));




        HospitalCardDao hospitalCardDao= new HospitalCardDao();
        AppointmentDao appointmentDao = new AppointmentDao();
       DoctorDao doctorDao = new DoctorDao();
        Doctor doctor = new Doctor();
        PatientDao patientDao = new PatientDao();
       Patient patient = null;
        HospitalCard hospitalCard = null;
        Appointment  appointment = null;
        List<Appointment> appointmentCardByPatientDoctorId = null;



        try {
         doctor = doctorDao.getByID(doctorId);
         patient = patientDao.getByID(patientId);
         hospitalCard = hospitalCardDao.getByPatientID(patientId);
         appointmentCardByPatientDoctorId = appointmentDao.getAppointmentByPatientAndDoctorId(doctorId, patientId);


            req.setAttribute("hospitalcard", hospitalCard);
            req.setAttribute("doctor", doctor);
            req.setAttribute("patient", patient);
            req.setAttribute("visitcard", appointmentCardByPatientDoctorId);


        } catch (DAOException e) {
           throw new RuntimeException(e);
        }
        return "hospitalcard.jsp";


    }
}
