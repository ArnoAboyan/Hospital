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
import java.io.IOException;
import java.sql.SQLException;

//FRONT CONTROLLER
@WebServlet("/controller")
public class Controller extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
//        logger.info("front controller method get have character encoding" + req.());
        String commandName = req.getParameter("command");
//        logger.debug("command ==>" + commandName);

        Command command = CommandContainer.getCommand(commandName);
        String address = "error.jsp";
        try {
            address = command.execute(req, resp);
        } catch (Exception e) {
            String back = req.getHeader("Referer");
//           logger.info("Controller get backlink= " + back);
            req.getSession().setAttribute("back", back);

            req.setAttribute("exception", e.getMessage());
        }

        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //set encoding
        req.setCharacterEncoding("UTF-8");
//        logger.info("front controller method post have character encoding" + req.());

        String commandName = req.getParameter("command");

        Command command = CommandContainer.getCommand(commandName);
        String address = "error.jsp";
        try {
            address = command.execute(req, resp);
        } catch (CommandException | DAOException e) {
            //get link back
            String back = req.getHeader("Referer");
//            logger.info("Controller get backlink= " + back);
            req.getSession().setAttribute("back", back);

            //logger.info("Controller update backlink= " + substring);
            req.getSession().setAttribute("exception", e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher(address).forward(req, resp);
    }
}
