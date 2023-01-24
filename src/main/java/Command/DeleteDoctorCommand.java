package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import entitys.Doctor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DeleteDoctorCommand implements Command {

    static final Logger logger = Logger.getLogger(DeleteDoctorCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        logger.info("Execute ==> DeleteDoctorCommand...");

        int doctorid;
        DoctorDao doctorDao = new DoctorDao();


        //validate input parameters
        if (req.getParameter("deletedoctor") != null) {
            doctorid = Integer.parseInt(req.getParameter("deletedoctor"));
            logger.info("Get doctorid => " + doctorid);
        }else throw new NullPointerException("Problem with searching doctor");


        //validate exist and delete
        if (doctorDao.isExistById(doctorid)) {
            doctorDao.delete(doctorid);
        }else {
            logger.error("Delete doctor => False");
            throw new CommandException("Problem when deleting doctor= " + doctorid);
        }

        return "controller?command=adminpagecommand&page=1";
    }
}
