package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;;
import entitys.Doctor;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class PatientListByDoctor implements Command {

    static final Logger logger = Logger.getLogger(PatientListByDoctor.class);

    //GET PATIENT LIST BY PATIENT ID//
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        logger.info("Execute ==> HospitalCardList...");

        // show patient by doctor ID//
        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        if(user==null){
            return "index.jsp";
        }

        String role = user.getRole().getTitle();

        if (role.equalsIgnoreCase("admin")) {
            logger.info("Check role => " + role);
            String sort = req.getParameter("sort");
        logger.info("get " + sort);
            PatientDao patientDao = new PatientDao();
            int doctorid = Integer.parseInt(req.getParameter("patientsfordoctorid"));
            if (sort == null) {
                logger.info("execute without sort");
                return executeWithOutSort(req, patientDao , doctorid);
            } else {
                logger.info("execute with sort");
                return executeWithSort(req, patientDao, sort , doctorid);
            }
        }else if (role.equalsIgnoreCase("doctor")){
            logger.info("Check role => " + role);
            String sort = req.getParameter("sort");
            PatientDao patientDao = new PatientDao();
            int doctorid = Integer.parseInt(req.getParameter("patientsfordoctorid"));
            if (sort == null) {
                logger.info("execute without sort");
                return executeWithOutSort(req, patientDao , doctorid);
            } else {
                logger.info("execute with sort");
                return executeWithSort(req, patientDao, sort , doctorid);
            }
        }else {
            logger.error("Check role " + role + " FALSE");
            return "error.jsp";
        }

    }


    public String executeWithOutSort(HttpServletRequest req, PatientDao patientDao , int doctorid ) throws DAOException, CommandException {
        DoctorDao getDoctor = new DoctorDao();
       Doctor doctor =  getDoctor.getByID(doctorid);


        String page = req.getParameter("page");
            logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Patient> patientList = patientDao.getAllWithLimitById(i, 5, doctorid);
        System.out.println(patientList);
        int countPage = (int) Math.ceil((double)patientDao.getCountPatientById(doctorid)/5);
        int numberOfPatients = patientDao.getCountPatientById(doctorid);
        System.out.println(countPage);
            logger.info("countPage =  " + countPage);
        if (patientList == null) {
                logger.error("patientList = null");
            throw new CommandException("Can`t get patients");
        } else {
            req.setAttribute("patientsbydoctor", patientList);
            req.setAttribute("doctorid", doctorid);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            req.setAttribute("doctor", doctor);
            req.setAttribute("numberOfPatients", numberOfPatients);
            Doctor user = (Doctor) req.getSession().getAttribute("currentUser");
            String role = user.getRole().getTitle();
            logger.info("req.setAttributes => Correct");

            if (role.equalsIgnoreCase("admin")){
                logger.info("Start patient list for => Admin");
                return "patientlistbydoctoradmin.jsp";
            }else if(role.equalsIgnoreCase("doctor")){
                logger.info("Start patient list for => Doctor");
            return "patientlistbydoctor.jsp";
            }else return "error.jsp";
        }
    }


    private String executeWithSort(HttpServletRequest req, PatientDao patientDao, String sort ,int doctorid) throws DAOException, CommandException {
        DoctorDao getDoctor = new DoctorDao();
        Doctor doctor =  getDoctor.getByID(doctorid);


        String page = req.getParameter("page");
        logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Patient> patientList = patientDao.getAllWithLimitAndOrderById(i, 5, sort , doctorid);
        System.out.println(patientList);
        int countPage = (int) Math.ceil((double)patientDao.getCountPatientById(doctorid)/5);
        int numberOfPatients = patientDao.getCountPatientById(doctorid);
        System.out.println("numbers of patient" + " " + numberOfPatients);
        System.out.println("count of page" + " " + countPage);
        logger.info("countPage =  " + countPage);
        if (patientList == null) {
           logger.error("patientList = null");
            throw new CommandException("Can`t get patients");
        } else {
            req.setAttribute("patientsbydoctor", patientList);
            req.setAttribute("doctorid", doctorid);
            req.setAttribute("countPage", countPage);
            req.setAttribute("page", page);
            req.setAttribute("sort", sort);
            req.setAttribute("doctor", doctor);
            req.setAttribute("numberOfPatients", numberOfPatients);
            Doctor user = (Doctor) req.getSession().getAttribute("currentUser");
            String role = user.getRole().getTitle();
            logger.info("req.setAttributes => Correct");
            if (role.equalsIgnoreCase("admin")){
                logger.info("Start patient list with sort for => Admin");
                return "patientlistbydoctoradmin.jsp";
            }else if(role.equalsIgnoreCase("doctor")){
                logger.info("Start patient list with sort for => Doctor");
                return "patientlistbydoctor.jsp";
            }else return "error.jsp";
        }
    }




}
