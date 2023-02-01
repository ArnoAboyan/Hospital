package Command;

import DAO.DAOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogoutCommandTest {

    @Test
    void executeTest() throws DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getSession()).thenReturn(session);

        LogoutCommand logoutCommand = new LogoutCommand();
        String actual = logoutCommand.execute(req, resp);
        String expected = "index.jsp";
        assertEquals(actual, expected);
    }
}