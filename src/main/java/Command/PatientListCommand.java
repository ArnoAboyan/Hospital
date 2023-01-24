package Command;

import Command.Command;
import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;
import entitys.Doctor;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class PatientListCommand implements Command {
    static final org.apache.log4j.Logger logger = Logger.getLogger(PatientListCommand.class);

       //show all patient with sort and pagination for admin//
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        logger.info("Execute ==> PatientListCommand...");


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

        DoctorDao doctorDaoList = new DoctorDao();
        List<Doctor> doctorList = doctorDaoList.getDoctorByRole(2);
        req.setAttribute("allDoctors", doctorList);

        String sort = req.getParameter("sort");
        logger.info("get " + sort);
        PatientDao patientDao = new PatientDao();
        if (sort == null) {
            logger.info("execute without sort");
            return executeWithOutSort(req, patientDao);
        } else {
            logger.info("execute with sort");
            return executeWithSort(req, patientDao, sort);
        }
    }


    public String executeWithOutSort(HttpServletRequest req, PatientDao patientDao) throws DAOException, CommandException {
        DoctorDao doctorDaoList = new DoctorDao();
        List<Doctor> doctorList = doctorDaoList.getDoctorByRole(2);
        req.setAttribute("allDoctors", doctorList);

        String page = req.getParameter("page");
            logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Patient> patientList = patientDao.getAllWithLimit(i, 5);
        System.out.println(patientList);

        int countPage = (int) Math.ceil((double)patientDao.getCountPatient()/5);
        System.out.println(countPage);
            logger.info("countPage =  " + countPage);
        if (patientList == null) {
                logger.error("patientList = null");
            throw new CommandException("Can`t get patients");
        } else {
            req.getSession().setAttribute("allPatients", patientList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            logger.info("req.setAttributes => Correct");
            return "patientlist.jsp";
        }
    }


    private String executeWithSort(HttpServletRequest req, PatientDao patientDao, String sort) throws DAOException, CommandException {
        DoctorDao doctorDaoList = new DoctorDao();
        List<Doctor> doctorList = doctorDaoList.getDoctorByRole(2);
        req.setAttribute("allDoctors", doctorList);



        String page = req.getParameter("page");
        logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Patient> patientList = patientDao.getAllWithLimitAndOrderBy(i, 5, sort);
        System.out.println(patientList);
        int countPage = (int) Math.ceil((double)patientDao.getCountPatient()/5);
        System.out.println(countPage);
        logger.info("countPage =  " + countPage);
        if (patientList == null) {
            logger.error("patientList = null");
            throw new CommandException("Can`t get patients");
        } else {
            req.getSession().setAttribute("allPatients", patientList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            req.setAttribute("sort", sort);
            logger.info("req.setAttributes => Correct");
            return "patientlist.jsp";
        }
    }




}
