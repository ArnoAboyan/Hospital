package Command;

import DAO.DAOException;
import DAO.impl.HospitalCardDao;
import entitys.Doctor;
import entitys.HospitalCard;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeHospitalCardCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {


        Doctor user = (Doctor) req.getSession().getAttribute("currentUser");

        if (user == null) {
            return "login.jsp";
        }

        String role = user.getRole().getTitle();

        if (role.equalsIgnoreCase("nurse")) {
            createHospitalCard(req);
            return "controller?command=hospitalcardbyid";
        } else if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("doctor")) {
            createHospitalCard(req);
            return "controller?command=hospitalcardbypatientid";
        }else
            return "error.jsp";

    }

    private void createHospitalCard(HttpServletRequest req) throws DAOException {


        int doctorid = Integer.parseInt(req.getParameter("doctorid"));
        int patientid = Integer.parseInt(req.getParameter("patientid"));
        String procedures = req.getParameter("procedures");
        String medications = req.getParameter("medications");
        String operations = req.getParameter("operations");
        String diagnosis = req.getParameter("diagnosis");

        HospitalCard hospitalCard = new HospitalCard(patientid, doctorid, diagnosis, procedures, medications, operations);

        System.out.println(hospitalCard);


        HospitalCardDao hospitalCardDao = new HospitalCardDao();
        hospitalCardDao.create(hospitalCard);

    }

}
