package Command;

import DAO.DAOException;
import DAO.impl.AppointmentDao;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;
import entitys.Appointment;
import entitys.Doctor;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentListCommand implements Command{


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
        AppointmentDao appointmentDao = new AppointmentDao();
        if (sort == null) {
//            logger.info("sort in if");
            return executeWithOutSort(req, appointmentDao);
        } else {
//            logger.info("sort in else");
            return executeWithSort(req, appointmentDao, sort);
        }
    }


    public String executeWithOutSort(HttpServletRequest req, AppointmentDao appointmentDao) throws DAOException, CommandException, SQLException {

        String page = req.getParameter("page");
//            logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Appointment> appointmentList = appointmentDao.getAllWithLimit(i, 5);
        System.out.println(appointmentList);


        int countPage = (int) Math.ceil((double)appointmentDao.getCountPatient()/5);
        System.out.println(countPage);
//            logger.info("countPage =  " + countPage);
        if (appointmentList == null) {
//                logger.error("appointmentList = null");
            throw new CommandException("Error can`t get patients");
        } else {
            req.getSession().setAttribute("appointment", appointmentList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            return "appointmentlist.jsp";
        }
    }


    private String executeWithSort(HttpServletRequest req, AppointmentDao appointmentDao, String sort) throws DAOException, CommandException {

        String page = req.getParameter("page");
//        logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<Appointment> appointmentList = appointmentDao.getAllWithLimitAndOrderBy(i, 5, sort);
        System.out.println(appointmentList);
        int countPage = (int) Math.ceil((double)appointmentDao.getCountPatient()/5);
        System.out.println(countPage);
//        logger.info("countPage =  " + countPage);
        if (appointmentList == null) {
//            logger.error("appointmentList = null");
            throw new CommandException("Error we can get patients");
        } else {
//            logger.info(listRooms);
            req.getSession().setAttribute("appointment", appointmentList);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            req.setAttribute("sort", sort);
            return "appointmentlist.jsp";
        }
    }



}
