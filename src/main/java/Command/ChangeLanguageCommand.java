package Command;

import DAO.DAOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeLanguageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {

        String pathInfo = req.getQueryString();
        String result = "http://localhost:8080" + pathInfo;
        req.setAttribute("currentAddressPage", result);
//        logger.info("servletPath = " + result);


        return "changeLocale.jsp";
    }

}
