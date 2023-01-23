package Command;

import DAO.DAOException;
import DAO.impl.AppointmentDao;
import DAO.impl.DoctorDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteAppointmentCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        int appointmentId;

        AppointmentDao appointmentDao = new AppointmentDao();

        //validate input parameters
        if (req.getParameter("appointmentid") != null) {
             appointmentId = Integer.parseInt(req.getParameter("appointmentid"));
        }else throw new NullPointerException("Problem with searching appointment");



        //validate exist appointment and delete
        if (appointmentDao.isExist(appointmentId)) {
            appointmentDao.delete(appointmentId);
        }else throw new CommandException("Problem when deleting appointment= " + appointmentId);



        return "controller?command=appointmentpagecommand&page=1";


    }
}
