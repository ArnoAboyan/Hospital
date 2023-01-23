package Command;

import Command.Command;
import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;
import entitys.Doctor;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

public class PatientListCommand implements Command {
    private static final Logger logger = Logger.getLogger(PatientListCommand.class.getName());


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {

//check role
        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        if(user==null){
            return "login.jsp";
        }

        String role = user.getRole().getTitle();

        if(!role.equalsIgnoreCase("admin")) {
            return "error.jsp";
        }

        DoctorDao doctorDaoList = new DoctorDao();
        List<Doctor> doctorList = doctorDaoList.getDoctorByRole(2);
        req.setAttribute("allDoctors", doctorList);

        String sort = req.getParameter("sort");
        logger.info("get " + sort);
        PatientDao patientDao = new PatientDao();
        if (sort == null) {
            logger.info("sort in if");
            return executeWithOutSort(req, patientDao);
        } else {
            logger.info("sort in else");
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
                logger.warning("list Room = null");
            throw new CommandException("Error can`t get patients");
        } else {
            req.getSession().setAttribute("allPatients", patientList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            return "patientlist.jsp";
        }
    }


    private String executeWithSort(HttpServletRequest req, PatientDao patientDao, String sort) throws DAOException, CommandException {
        DoctorDao doctorDaoList = new DoctorDao();
        List<Doctor> doctorList = doctorDaoList.getDoctorByRole(2);
        req.setAttribute("allDoctors", doctorList);



        String page = req.getParameter("page");
//        logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Patient> patientList = patientDao.getAllWithLimitAndOrderBy(i, 5, sort);
        System.out.println(patientList);
        int countPage = (int) Math.ceil((double)patientDao.getCountPatient()/5);
        System.out.println(countPage);
//        logger.info("countPage =  " + countPage);
        if (patientList == null) {
//            logger.error("patientList = null");
            throw new CommandException("Error we can get patients");
        } else {
//            logger.info(patientList);
            req.getSession().setAttribute("allPatients", patientList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            req.setAttribute("sort", sort);
            return "patientlist.jsp";
        }
    }




}
