package monitoring.appServer.application;

import monitoring.common.State;
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
}