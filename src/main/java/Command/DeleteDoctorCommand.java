package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import entitys.Doctor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteDoctorCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        int doctorid;
        DoctorDao doctorDao = new DoctorDao();


        //validate input parameters
        if (req.getParameter("deletedoctor") != null) {
            doctorid = Integer.parseInt(req.getParameter("deletedoctor"));
        }else throw new NullPointerException("Problem with searching doctor");


        //validate exist and delete
        if (doctorDao.isExistById(doctorid)) {
            doctorDao.delete(doctorid);
        }else throw new CommandException("Problem when deleting doctor= " + doctorid);

        return "controller?command=adminpagecommand&page=1";
    }
}
