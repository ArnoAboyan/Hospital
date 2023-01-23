package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.HospitalCardDao;
import entitys.Doctor;
import entitys.HospitalCard;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class HospitalCardList implements Command {
//    private static final Logger logger = Logger.getLogger(PatientListCommand.class.getName());


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException, SQLException {


//check role
        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        if (user == null) {
            return "login.jsp";
        }

        String role = user.getRole().getTitle();

        if (!role.equalsIgnoreCase("nurse")) {
            return "error.jsp";
        }

        String sort = req.getParameter("sort");
//            logger.info("get " + sort);
        HospitalCardDao hospitalCardDao = new HospitalCardDao();
        if (sort == null) {
//                logger.info("sort in if");
            return executeWithOutSort(req, hospitalCardDao);
        } else {
//                logger.info("sort in else");
            return executeWithSort(req, hospitalCardDao, sort);
        }
    }


    public String executeWithOutSort(HttpServletRequest req, HospitalCardDao hospitalCardDao) throws
            DAOException, CommandException {
        DoctorDao doctorDaoList = new DoctorDao();
        List<Doctor> doctorList = doctorDaoList.getAll();
        req.setAttribute("allDoctors", doctorList);

        String page = req.getParameter("page");
//            logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<HospitalCard> hospitalCard = hospitalCardDao.getAllWithLimit(i, 5);
        System.out.println(hospitalCard);

        int countPage = (int) Math.ceil((double) hospitalCardDao.getCountHospitalCard() / 5);
        System.out.println(countPage);
//            logger.info("countPage =  " + countPage);
        if (hospitalCard == null) {
//           logger.warning("list Room = null");
            throw new CommandException("Error can`t get patients");
        } else {
            req.getSession().setAttribute("allHospitalCards", hospitalCard);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            return "hospitalcardlist.jsp";
        }
    }


    private String executeWithSort(HttpServletRequest req, HospitalCardDao hospitalCardDao, String sort) throws
            DAOException, CommandException {
        DoctorDao doctorDaoList = new DoctorDao();
        List<Doctor> doctorList = doctorDaoList.getAll();
        req.setAttribute("allDoctors", doctorList);


        String page = req.getParameter("page");
//        logger.info("get " + page);
        int i = Integer.parseInt(page);
        List<HospitalCard> hospitalCard = hospitalCardDao.getAllWithLimitAndOrderBy(i, 5, sort);
        System.out.println(hospitalCard);
        int countPage = (int) Math.ceil((double) hospitalCardDao.getCountHospitalCard() / 5);
        System.out.println(countPage);
//        logger.info("countPage =  " + countPage);
        if (hospitalCard == null) {
//            logger.error("patientList = null");
            throw new CommandException("Error we can get patients");
        } else {
//            logger.info(patientList);
            req.getSession().setAttribute("allHospitalCards", hospitalCard);
            req.setAttribute("page", page);
            req.setAttribute("countPage", countPage);
            req.setAttribute("sort", sort);
            return "hospitalcardlist.jsp";
        }
    }
}

