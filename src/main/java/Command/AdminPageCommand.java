package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;
import entitys.Doctor;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class AdminPageCommand implements Command{

    static final Logger logger = Logger.getLogger(AdminPageCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException, SQLException {
        logger.info("Execute ==> AdminPageCommand...");
//check role
        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        if(user==null){
            return "login.jsp";
        }

        String role = user.getRole().getTitle();

        if(!role.equalsIgnoreCase("admin")) {
            logger.error("Check role " + role + " FALSE");
            return "error.jsp";
        }

        logger.info("Check role " + role + " CORRECT");

        String sort = req.getParameter("sort");
        logger.info("get " + sort);
        DoctorDao doctorDao = new DoctorDao();
        if (sort == null) {
            logger.info("execute without sort");
            return executeWithOutSort(req, doctorDao);
        } else {
            logger.info("execute with sort");
            return executeWithSort(req, doctorDao, sort);
        }
    }

//execute without sort//
    public String executeWithOutSort(HttpServletRequest req, DoctorDao doctorDao) throws DAOException, CommandException, SQLException {
        int countOfPAtients ;


        String page = req.getParameter("page");
            logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Doctor> doctorList = doctorDao.getAllWithLimit(i, 5);
        int countPage = (int) Math.ceil((double)doctorDao.getCountDoctor()/5);
            logger.info("countPage =  " + countPage);

        if (doctorList == null) {
                logger.error("list doctors = null");
            throw new CommandException("Can`t get patients");
        } else {
            for (Doctor doctor : doctorList) {
                try {
                    countOfPAtients = doctorDao.getCountOfPatientsByDoctor(doctor.getDoctorId());
                    doctorDao.updateCountOfPatients(countOfPAtients,doctor.getDoctorId());
                    logger.info("Get count of patient by doctor => Correct" );
                } catch (DAOException e) {
                    logger.error("Can`t get patient by doctor " + e.getMessage());
                    throw new RuntimeException(e);

                }
            }

            req.getSession().setAttribute("allDoctors", doctorList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            logger.info("req.setAttributes => Correct");
            return "admin.jsp";
        }
    }

//execute with sort//
    public String executeWithSort(HttpServletRequest req, DoctorDao doctorDao, String sort) throws DAOException, CommandException, SQLException {
        int countOfPAtients ;

        String page = req.getParameter("page");
        logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Doctor> doctorList = doctorDao.getAllWithLimitAndOrderBy(i, 5, sort);
        System.out.println(doctorList);
        int countPage = (int) Math.ceil((double)doctorDao.getCountDoctor()/5);
        System.out.println(countPage);
        logger.info("countPage =  " + countPage);
        if (doctorList == null) {
            logger.error("list admin = null");
            throw new CommandException("Can get patients");
        } else {
            for (Doctor doctor : doctorList) {
                try {
                    countOfPAtients = doctorDao.getCountOfPatientsByDoctor(doctor.getDoctorId());
                    doctorDao.updateCountOfPatients(countOfPAtients,doctor.getDoctorId());
                    logger.info("Get count of patient by doctor => Correct" );
                } catch (DAOException e) {
                    logger.error("Can`t get patient by doctor " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }

            req.getSession().setAttribute("allDoctors", doctorList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            req.setAttribute("sort", sort);
            logger.info("req.setAttributes => Correct");
            return "admin.jsp";
        }
    }

}