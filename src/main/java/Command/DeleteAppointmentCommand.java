package Command;

import DAO.DAOException;
import DAO.impl.AppointmentDao;
import DAO.impl.DoctorDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DeleteAppointmentCommand implements Command {

    static final Logger logger = Logger.getLogger(DeleteAppointmentCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        logger.info("Execute ==> DeleteAppointmentCommand...");

        int appointmentId;

        AppointmentDao appointmentDao = new AppointmentDao();

        //validate input parameters
        if (req.getParameter("appointmentid") != null) {
             appointmentId = Integer.parseInt(req.getParameter("appointmentid"));
             logger.info("Get appointmenId => " + appointmentId);
        }else {
            throw new NullPointerException("Problem with searching appointment");
        }



        //validate exist appointment and delete
        if (appointmentDao.isExist(appointmentId)) {
            appointmentDao.delete(appointmentId);
            logger.info("Delete appointment => Correct");
        }else {
            logger.error("Delete appointment => False");
            throw new CommandException("Problem when deleting appointment= " + appointmentId);
        }



        return "controller?command=appointmentpagecommand&page=1";


    }
}
