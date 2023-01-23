package Command;

import DAO.DAOException;
import DAO.impl.PatientDao;
import entitys.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Date;

public class UpdatePatientCommand implements Command{


    //update patient information
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        int patientid = Integer.parseInt(req.getParameter("patientid"));
        Patient patient = new Patient();


        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phone = req.getParameter("phone");
        Date birthday = Date.valueOf(req.getParameter("birthday"));
        String gender = req.getParameter("gender");

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


        System.out.println(patient);

        PatientDao patientDao = new PatientDao();
        patientDao.updatePatientbyId(patient, patientid);

        return "controller?command=patientlistcommand&page=1";
    }
}
