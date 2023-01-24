package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DeletePatientCommand implements Command {

    static final Logger logger = Logger.getLogger(DeletePatientCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        logger.info("Execute ==> DeletePatientCommand...");
        int patientid;

        PatientDao patientDao = new PatientDao();

        //validate input parameters
        if (req.getParameter("deletepatient") != null) {
            patientid = Integer.parseInt(req.getParameter("deletepatient"));
            logger.info("Get patientid => " + patientid);
        }else throw new NullPointerException("Problem with searching  patient");


        //validate exist and delete
        if (patientDao.isExistById(patientid)) {
            patientDao.delete(patientid);
        }else {
            logger.error("Delete patient => False");
            throw new CommandException("Problem when deleting patient= " + patientid);
        }



        return "controller?command=patientlistcommand&page=1";
    }
}
