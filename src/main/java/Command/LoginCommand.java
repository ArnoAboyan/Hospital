package Command;


import DAO.DAOException;
import DAO.impl.DoctorDao;
import entitys.Doctor;
import entitys.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class LoginCommand implements Command {

static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        String login = req.getParameter("login");
       logger.info("login ==>" + login);
        DoctorDao doctorDao = new DoctorDao();
        Doctor doctor = doctorDao.getByLogin(login);
//        logger.debug(user);

        if (doctor.getLogin()==null){
//            logger.error("user = null");
            throw new CommandException("Can not find user by Login");
        }
        else{
//            logger.debug("get doctor not null");
            String passwordInPut = req.getParameter("password");
//            logger.debug("passwordInPut ==>" + passwordInPut);
            String originalPassword = doctor.getPassword();
//            logger.debug("originalPassword ==>" + originalPassword);

            if (passwordInPut.equals(originalPassword)){
                req.getSession().setAttribute("currentUser", doctor);

//
                Role role = doctor.getRole();
//                logger.info("LoginComand get user with role = " + role.getTitle());

                if(role.getTitle().equalsIgnoreCase("admin")) {
                    return "controller?command=adminpagecommand&page=1";
                }
                else if (role.getTitle().equalsIgnoreCase("doctor")) {
                    return "controller?command=patientlistbydoctor&page=1&patientsfordoctorid=" + doctor.getDoctorId();
                }
                else if (role.getTitle().equalsIgnoreCase("nurse")) {
                    return "controller?command=hospitalcardlist&page=1"; }

            } else{
                throw new CommandException("password wrong");
            }
        }
        return "error.jsp";
    }
}
