package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;
import entitys.Doctor;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class AdminPageCommand implements Command{



    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException, SQLException {

//check role
        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        if(user==null){
            return "login.jsp";
        }

        String role = user.getRole().getTitle();

        if(!role.equalsIgnoreCase("admin")) {
            return "error.jsp";
        }



        String sort = req.getParameter("sort");
//        logger.info("get " + sort);
        DoctorDao doctorDao = new DoctorDao();
        if (sort == null) {
//            logger.info("sort in if");
            return executeWithOutSort(req, doctorDao);
        } else {
//            logger.info("sort in else");
            return executeWithSort(req, doctorDao, sort);
        }
    }


    public String executeWithOutSort(HttpServletRequest req, DoctorDao doctorDao) throws DAOException, CommandException, SQLException {
        int countOfPAtients ;


        String page = req.getParameter("page");
//            logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Doctor> doctorList = doctorDao.getAllWithLimit(i, 5);
        System.out.println(doctorList);
        int countPage = (int) Math.ceil((double)doctorDao.getCountDoctor()/5);
//            logger.info("countPage =  " + countPage);

        if (doctorList == null) {
//                logger.error("list admin = null");
            throw new CommandException("Error can`t get patients");
        } else {
            for (Doctor doctor : doctorList) {
                try {
                    countOfPAtients = doctorDao.getCountOfPatientsByDoctor(doctor.getDoctorId());
                    doctorDao.updateCountOfPatients(countOfPAtients,doctor.getDoctorId());
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            }

            req.getSession().setAttribute("allDoctors", doctorList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            return "admin.jsp";
        }
    }


    private String executeWithSort(HttpServletRequest req, DoctorDao doctorDao, String sort) throws DAOException, CommandException, SQLException {
        int countOfPAtients ;

        String page = req.getParameter("page");
//        logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Doctor> doctorList = doctorDao.getAllWithLimitAndOrderBy(i, 5, sort);
        System.out.println(doctorList);
        int countPage = (int) Math.ceil((double)doctorDao.getCountDoctor()/5);
        System.out.println(countPage);
//        logger.info("countPage =  " + countPage);
        if (doctorList == null) {
//            logger.error("list admin = null");
            throw new CommandException("Error we can get patients");
        } else {
            for (Doctor doctor : doctorList) {
                try {
                    countOfPAtients = doctorDao.getCountOfPatientsByDoctor(doctor.getDoctorId());
                    doctorDao.updateCountOfPatients(countOfPAtients,doctor.getDoctorId());
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            }
//            logger.info(doctorList);
            req.getSession().setAttribute("allDoctors", doctorList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            req.setAttribute("sort", sort);
            return "admin.jsp";
        }
    }

}