package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;;
import entitys.Doctor;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class PatientListByDoctor implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {


        // show patient by doctor ID
        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        if(user==null){
            return "index.jsp";
        }

        String role = user.getRole().getTitle();

        if (role.equalsIgnoreCase("admin")) {
            String sort = req.getParameter("sort");
//        logger.info("get " + sort);
            PatientDao patientDao = new PatientDao();
            int doctorid = Integer.parseInt(req.getParameter("patientsfordoctorid"));
            if (sort == null) {
//            logger.info("sort in if");
                return executeWithOutSort(req, patientDao , doctorid);
            } else {
//            logger.info("sort in else");
                return executeWithSort(req, patientDao, sort , doctorid);
            }
        }else if (role.equalsIgnoreCase("doctor")){
            String sort = req.getParameter("sort");
//        logger.info("get " + sort);
            PatientDao patientDao = new PatientDao();
            int doctorid = Integer.parseInt(req.getParameter("patientsfordoctorid"));
            if (sort == null) {
//            logger.info("sort in if");
                return executeWithOutSort(req, patientDao , doctorid);
            } else {
//            logger.info("sort in else");
                return executeWithSort(req, patientDao, sort , doctorid);
            }
        }else return "error.jsp";

    }


    public String executeWithOutSort(HttpServletRequest req, PatientDao patientDao , int doctorid ) throws DAOException, CommandException {
        DoctorDao getDoctor = new DoctorDao();
       Doctor doctor =  getDoctor.getByID(doctorid);


        String page = req.getParameter("page");
//            logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Patient> patientList = patientDao.getAllWithLimitById(i, 5, doctorid);
        System.out.println(patientList);
        int countPage = (int) Math.ceil((double)patientDao.getCountPatientById(doctorid)/5);
        int numberOfPatients = patientDao.getCountPatientById(doctorid);
        System.out.println(countPage);
//            logger.info("countPage =  " + countPage);
        if (patientList == null) {
//                logger.error("lpatientList = null");
            throw new CommandException("Error can`t get patients");
        } else {
            req.setAttribute("patientsbydoctor", patientList);
            req.setAttribute("doctorid", doctorid);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            req.setAttribute("doctor", doctor);
            req.setAttribute("numberOfPatients", numberOfPatients);
            Doctor user = (Doctor) req.getSession().getAttribute("currentUser");
            String role = user.getRole().getTitle();

            if (role.equalsIgnoreCase("admin")){
                return "patientlistbydoctoradmin.jsp";
            }else if(role.equalsIgnoreCase("doctor")){
            return "patientlistbydoctor.jsp";
            }else return "error.jsp";
        }
    }


    private String executeWithSort(HttpServletRequest req, PatientDao patientDao, String sort ,int doctorid) throws DAOException, CommandException {
        DoctorDao getDoctor = new DoctorDao();
        Doctor doctor =  getDoctor.getByID(doctorid);


        String page = req.getParameter("page");
//        logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Patient> patientList = patientDao.getAllWithLimitAndOrderById(i, 5, sort , doctorid);
        System.out.println(patientList);
        int countPage = (int) Math.ceil((double)patientDao.getCountPatientById(doctorid)/5);
        int numberOfPatients = patientDao.getCountPatientById(doctorid);
        System.out.println("numbers of patient" + " " + numberOfPatients);
        System.out.println("count of page" + " " + countPage);
//        logger.info("countPage =  " + countPage);
        if (patientList == null) {
//            logger.error("patientList = null");
            throw new CommandException("Error we can get patients");
        } else {
//            logger.info(listRooms);
            req.setAttribute("patientsbydoctor", patientList);
            req.setAttribute("doctorid", doctorid);
            req.setAttribute("countPage", countPage);
            req.setAttribute("page", page);
            req.setAttribute("sort", sort);
            req.setAttribute("doctor", doctor);
            req.setAttribute("numberOfPatients", numberOfPatients);
            Doctor user = (Doctor) req.getSession().getAttribute("currentUser");
            String role = user.getRole().getTitle();

            if (role.equalsIgnoreCase("admin")){
                return "patientlistbydoctoradmin.jsp";
            }else if(role.equalsIgnoreCase("doctor")){
                return "patientlistbydoctor.jsp";
            }else return "error.jsp";
        }
    }




}
