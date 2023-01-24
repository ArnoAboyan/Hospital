package Contriller;

import Command.Command;
import Command.CommandContainer;
import Command.CommandException;
import DAO.DAOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

//FRONT CONTROLLER
@WebServlet("/controller")
public class Controller extends HttpServlet {
    static final org.apache.log4j.Logger logger = Logger.getLogger(Controller.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Execute ==> Controller doGet...");

        req.setCharacterEncoding("UTF-8");;
        String commandName = req.getParameter("command");
        logger.info("command ==>" + commandName);

        Command command = CommandContainer.getCommand(commandName);
        String address = "error.jsp";
        try {
            address = command.execute(req, resp);
        } catch (Exception e) {
            String back = req.getHeader("Referer");
           logger.info("Controller get backlink= " + back);
            req.getSession().setAttribute("back", back);

            req.setAttribute("exception", e.getMessage());
        }

        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Execute ==> Controller doPost...");
        //set encoding
        req.setCharacterEncoding("UTF-8");

        String commandName = req.getParameter("command");

        Command command = CommandContainer.getCommand(commandName);
        String address = "error.jsp";
        try {
            address = command.execute(req, resp);
        } catch (CommandException | DAOException e) {
            //get link back
            String back = req.getHeader("Referer");
            logger.info("Controller get backlink= " + back);
            req.getSession().setAttribute("back", back);
            req.getSession().setAttribute("exception", e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher(address).forward(req, resp);
    }
}
