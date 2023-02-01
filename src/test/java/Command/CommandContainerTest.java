package Command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class CommandContainerTest {


    @ParameterizedTest
    @ValueSource( strings = {"login"})
    void getCommand(String commands) {



        Command actual = CommandContainer.getCommand("login");
        Command expected = CommandContainer.getCommand(commands);
        assertEquals(actual, expected);

    }
}