package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.HospitalCardDao;
import entitys.Doctor;
import entitys.HospitalCard;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class HospitalCardList implements Command {


    static final Logger logger = Logger.getLogger(HospitalCardList.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException, SQLException {
        logger.info("Execute ==> HospitalCardList...");

       //check role
        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        if (user == null) {
            return "login.jsp";
        }

        String role = user.getRole().getTitle();

        if (!role.equalsIgnoreCase("nurse")) {
            logger.error("Check role " + role + " FALSE");
            return "error.jsp";
        }

        logger.info("Check role " + role + " CORRECT");

        String sort = req.getParameter("sort");
            logger.info("get " + sort);
        HospitalCardDao hospitalCardDao = new HospitalCardDao();
        if (sort == null) {
            logger.info("execute without sort");
            return executeWithOutSort(req, hospitalCardDao);
        } else {
              logger.info("execute with sort");
            return executeWithSort(req, hospitalCardDao, sort);
        }
    }


    public String executeWithOutSort(HttpServletRequest req, HospitalCardDao hospitalCardDao) throws
            DAOException, CommandException {
        DoctorDao doctorDaoList = new DoctorDao();
        List<Doctor> doctorList = doctorDaoList.getAll();
        req.setAttribute("allDoctors", doctorList);

        String page = req.getParameter("page");
            logger.info("get page " + page);
        int i = Integer.parseInt(page);
        List<HospitalCard> hospitalCard = hospitalCardDao.getAllWithLimit(i, 5);
        System.out.println(hospitalCard);

        int countPage = (int) Math.ceil((double) hospitalCardDao.getCountHospitalCard() / 5);
        System.out.println(countPage);
            logger.info("countPage =  " + countPage);
        if (hospitalCard == null) {
           logger.error("list hospitalCard = null");
            throw new CommandException("Can`t get hospitalCard");
        } else {
            req.getSession().setAttribute("allHospitalCards", hospitalCard);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            logger.info("req.setAttributes => Correct");
            return "hospitalcardlist.jsp";
        }
    }


    private String executeWithSort(HttpServletRequest req, HospitalCardDao hospitalCardDao, String sort) throws
            DAOException, CommandException {
        DoctorDao doctorDaoList = new DoctorDao();
        List<Doctor> doctorList = doctorDaoList.getAll();
        req.setAttribute("allDoctors", doctorList);


        String page = req.getParameter("page");
        logger.info("get page" + page);
        int i = Integer.parseInt(page);
        List<HospitalCard> hospitalCard = hospitalCardDao.getAllWithLimitAndOrderBy(i, 5, sort);
        System.out.println(hospitalCard);
        int countPage = (int) Math.ceil((double) hospitalCardDao.getCountHospitalCard() / 5);
        System.out.println(countPage);
        logger.info("countPage =  " + countPage);
        if (hospitalCard == null) {
            logger.error("hospitalCard = null");
            throw new CommandException("Can`t get hospitalCard");
        } else {
            logger.info(hospitalCard);
            req.getSession().setAttribute("allHospitalCards", hospitalCard);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            req.setAttribute("sort", sort);
            logger.info("req.setAttributes => Correct");
            return "hospitalcardlist.jsp";
        }
    }
}

