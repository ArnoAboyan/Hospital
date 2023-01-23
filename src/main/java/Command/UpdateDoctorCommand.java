package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import entitys.Category;
import entitys.Doctor;
import entitys.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.enterprise.inject.New;
import javax.print.Doc;
import java.sql.SQLException;

public class UpdateDoctorCommand implements Command {



    //update doctor and nurse information
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException, SQLException {
        int doctorid = Integer.parseInt(req.getParameter("doctorid"));
        Doctor doctor = new Doctor();
        DoctorDao doctorDao = new DoctorDao();


            String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        int categoryID = Category.getIDByName(req.getParameter("category"));
        int roleID = Role.getIDByName(req.getParameter("role"));


        // all english and Cyrillic letters mor from 2 to 20
        if (name.matches("[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}")) {
            doctor.setDoctorName(name);
        } else throw new CommandException("The entered name is not correct");

        if (surname.matches("[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}")) {
            doctor.setDoctorSurname(surname);
        } else throw new CommandException("The entered surname is not correct");

        if (login.matches("[A-Za-z]{3,20}")) {
            doctor.setLogin(login);
        } else throw new CommandException("The entered login is not correct");

        if (password.matches("[a-zA-Z0-9]{3,20}")) {
            doctor.setPassword(password);
        } else throw new CommandException("The entered password is not correct");

        if (categoryID > 0 && categoryID <= 8) {
            doctor.setCategory(categoryID);
        } else throw new CommandException("The entered category is not correct");

        if (roleID > 1 && roleID <= 3) {
            doctor.setRole(roleID);
        } else throw new CommandException("The entered role is not correct");


        System.out.println(doctor);


        doctorDao.updateDoctorById(doctor, doctorid);


        return "controller?command=adminpagecommand&page=1";
    }
}

