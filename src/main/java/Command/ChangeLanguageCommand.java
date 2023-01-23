package Command;

import DAO.DAOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ChangeLanguageCommand implements Command {
    static final Logger logger = Logger.getLogger(ChangeLanguageCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException {

        String pathInfo = req.getQueryString();
        String result = "http://localhost:8080" + pathInfo;
        req.setAttribute("currentAddressPage", result);
        logger.info("servletPath = " + result);


        return "changeLocale.jsp";
    }

}
