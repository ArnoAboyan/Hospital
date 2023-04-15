package Command;

import DAO.DAOException;
import DAO.impl.HospitalCardDao;
import entitys.Doctor;
import entitys.HospitalCard;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ChangeHospitalCardCommand implements Command {

    static final Logger logger = Logger.getLogger(ChangeHospitalCardCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        logger.info("Execute ==> ChangeHospitalCardCommand...");

        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        //check role
        if (user == null) {
            return "index.jsp";
        }

        String role = user.getRole().getTitle();

        if (role.equalsIgnoreCase("nurse")) {
            logger.info("Check role => " + role);
            createHospitalCard(req);
            return "controller?command=hospitalcardbyid";
        } else if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("doctor")) {
            logger.info("Check role => " + role);
            createHospitalCard(req);
            return "controller?command=hospitalcardbypatientid";
        }else
            logger.error("Check role " + role + " FALSE");
            return "error.jsp";

    }

    public void createHospitalCard(HttpServletRequest req) throws DAOException {


        int doctorid = Integer.parseInt(req.getParameter("doctorid"));
        int patientid = Integer.parseInt(req.getParameter("patientid"));
        String procedures = req.getParameter("procedures");
        String medications = req.getParameter("medications");
        String operations = req.getParameter("operations");
        String diagnosis = req.getParameter("diagnosis");

        HospitalCard hospitalCard = null ;

        //Check correct input date//
        if (doctorid > 0 && patientid > 0){
            hospitalCard = new HospitalCard(patientid, doctorid, diagnosis, procedures, medications, operations);
    } else {
            logger.error("Collect hospitalcard => FALSE");
            throw new NullPointerException();
        }


        HospitalCardDao hospitalCardDao = new HospitalCardDao();
        hospitalCardDao.create(hospitalCard);

        logger.info("Change hospitalCard => Correct");

    }

}
