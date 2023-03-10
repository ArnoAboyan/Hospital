package Command;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import entitys.Category;
import entitys.Doctor;
import entitys.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class AddDoctorCommand implements Command {
    static final Logger logger = Logger.getLogger(AddDoctorCommand.class);
    //update doctor and nurse information
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {
        logger.info("Execute ==> AddDoctorCommand...");
            DoctorDao doctorDao = new DoctorDao();
        Doctor doctor = new Doctor();

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        int categoryID = Category.getIDByName(req.getParameter("category"));
        int roleID = Role.getIDByName(req.getParameter("role"));


        //CHECK EXIST DOCTOR IN DB WITH SAME LOGIN//

        if(doctorDao.isExistLogin(login)){
            throw new CommandException("User with the same login is exist! Please choose another login");
        }


        // all english and Cyrillic letters mor from 2 to 20
        if (name.matches("[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}")) {
                doctor.setDoctorName(name);
            }else throw new CommandException("The entered name is not correct");


        if (surname.matches("[-a-zA-Zа-яА-ЯЁёЇїІіҐґ]{2,20}")){
                doctor.setDoctorSurname(surname);
        }else throw  new CommandException("The entered surname is not correct");

        if (login.matches("[A-Za-z]{3,20}")){
                doctor.setLogin(login);
            }else throw  new CommandException("The entered login is not correct");

        if (password.matches("[a-zA-Z0-9]{3,20}")){
                doctor.setPassword(password);
            }else throw  new CommandException("The entered password is not correct");

        if (categoryID > 0 && categoryID <= 8 ){
                doctor.setCategory(categoryID);
        }else throw  new CommandException("The entered category is not correct");

        if (roleID > 1 && roleID <= 3 ){
                doctor.setRole(roleID);
            }else throw  new CommandException("The entered role is not correct");


        doctorDao.create(doctor);

        logger.info("New doctor create => " + doctor);


        return "controller?command=adminpagecommand&page=1";
    }
}
