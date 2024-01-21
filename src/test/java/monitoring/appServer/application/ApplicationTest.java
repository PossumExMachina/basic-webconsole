package monitoring.appServer.application;

import monitoring.common.State;
import monitoring.serverResources.memory.Memory;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    public void testGetApplication(){
        Application application = new Application();
        application.setName("app1");
        application.setState(State.RUNNING);


        assertAll("app1",
                () -> assertEquals("app1", application.getName()),
                () -> assertEquals(State.RUNNING, application.getState()));
    }

    @Test
    void toString_ShouldReturnCorrectFormat() {

        Application application = new Application("App1", State.RUNNING);
        String appString = application.toString();

        String expectedString = "Application(name=App1, state=RUNNING)";
        assertEquals(expectedString, appString, "The toString method should return the correct string representation of Memory object.");
    }
}