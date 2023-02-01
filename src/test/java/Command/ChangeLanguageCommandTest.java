package Command;

import DAO.DAOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChangeLanguageCommandTest {



    @Test
    void execute() throws DAOException, CommandException {
        HttpSession session = mock(HttpSession.class);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getSession()).thenReturn(session);
        when(req.getQueryString()).thenReturn("Hospital_war_exploded/controller?command=adminpagecommand&page=1");


        ChangeLanguageCommand changeLanguageCommand = new ChangeLanguageCommand();
        String actual = changeLanguageCommand.execute(req, resp);
        String expected = "changeLocale.jsp";
        assertEquals(actual, expected);
    }
}