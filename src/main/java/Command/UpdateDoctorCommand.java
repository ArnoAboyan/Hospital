package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import entitys.Category;
import entitys.Doctor;
import entitys.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import javax.enterprise.inject.New;
import javax.print.Doc;
import java.sql.SQLException;

public class UpdateDoctorCommand implements Command {

    static final org.apache.log4j.Logger logger = Logger.getLogger(UpdateDoctorCommand.class);

    //update doctor and nurse information//
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException, SQLException {
        logger.info("Execute ==> UpdateDoctorCommand...");

        int doctorid = Integer.parseInt(req.getParameter("doctorid"));
        Doctor doctor = new Doctor();
        DoctorDao doctorDao = new DoctorDao();


        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        int categoryID = Category.getIDByName(req.getParameter("category"));
        int roleID = Role.getIDByName(req.getParameter("role"));


        // all english and Cyrillic letters mor from 2 to 20//

        if (name.matches("[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}")) {
            doctor.setDoctorName(name);
        } else throw new CommandException("The entered name is not correct");

// all english and Cyrillic letters mor from 2 to 20//
        if (surname.matches("[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}")) {
            doctor.setDoctorSurname(surname);
        } else throw new CommandException("The entered surname is not correct");

// all english letters and numbers mor from 2 to 20//
        if (login.matches("[A-Za-z]{3,20}")) {
            doctor.setLogin(login);
        } else throw new CommandException("The entered login is not correct");

// all english letters and numbers mor from 2 to 20//
        if (password.matches("[a-zA-Z0-9]{3,20}")) {
            doctor.setPassword(password);
        } else throw new CommandException("The entered password is not correct");

//validate not 0
        if (categoryID > 0 && categoryID <= 8) {
            doctor.setCategory(categoryID);
        } else throw new CommandException("The entered category is not correct");

//validate not 0
        if (roleID > 1 && roleID <= 3) {
            doctor.setRole(roleID);
        } else throw new CommandException("The entered role is not correct");


        //check exist doctor in DB//
      if(doctorDao.isExistById(doctorid)){
          doctorDao.updateDoctorById(doctor, doctorid);
          logger.info("Doctor update => " + doctor);
      }else throw new CommandException("Don`t find doctor with id => " + doctorid);


      return "controller?command=adminpagecommand&page=1";
    }
}

