
package Command;

import DAO.DAOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

//design pattern "Command"
public interface Command {

    String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, CommandException, SQLException;
}