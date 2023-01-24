package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;
import entitys.Category;
import entitys.Doctor;
import entitys.Patient;
import entitys.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.sql.Date;

public class AddPatientCommand implements Command{

    static final Logger logger = Logger.getLogger(AddPatientCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {

        logger.info("Execute ==> AddPatientCommand...");

        PatientDao patientDao = new PatientDao();
        Patient patient = new Patient();


        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phone = req.getParameter("phone");
        Date birthday = Date.valueOf(req.getParameter("birthday"));
        String gender = req.getParameter("gender");

        if (patientDao.isExistPhoneNumber(phone)){
            throw new CommandException("Patient with this phone number is exist! Please choose another " );
        }


        // all english and Cyrillic letters mor from 2 to 20
        if (name.matches("[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}")) {
            patient.setPatientName(name);
        } else throw new CommandException("The entered name is not correct");

        if (surname.matches("[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}")) {
            patient.setPatientSurname(surname);
        } else throw new CommandException("The entered surname is not correct");

        if (phone.matches("(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?\\d{3}\\d{4}")) {
            patient.setPatientPhone(Integer.parseInt(phone));
        } else throw new CommandException("The entered phone is not correct");

        if (birthday != null) {
            patient.setPatientDateOfBirth(birthday);
        } else throw new CommandException("The entered birthday is not correct");

        if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") ) {
            patient.setPatientGender(gender);
        } else throw new CommandException("The entered gender is not correct");




        patientDao.create(patient);


        logger.info("New patient create => " + patient);

        return "controller?command=patientlistcommand&page=1";
    }
}
