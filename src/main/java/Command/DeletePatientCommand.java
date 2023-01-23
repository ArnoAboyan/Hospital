package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeletePatientCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        int patientid;

        PatientDao patientDao = new PatientDao();

        //validate input parameters
        if (req.getParameter("deletepatient") != null) {
            patientid = Integer.parseInt(req.getParameter("deletepatient"));
        }else throw new NullPointerException("Problem with searching  patient");


        //validate exist and delete
        if (patientDao.isExistById(patientid)) {
            patientDao.delete(patientid);
        }else throw new CommandException("Problem when deleting patient= " + patientid);



        return "controller?command=patientlistcommand&page=1";
    }
}
