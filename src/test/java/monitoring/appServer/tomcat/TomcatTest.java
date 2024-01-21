package monitoring.appServer.tomcat;

import monitoring.common.State;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TomcatTest {

    @Test
    public void testGetTomcat(){
        Tomcat tomcat = new Tomcat();
        tomcat.setState(State.RUNNING);

        assertAll(() -> assertEquals(State.RUNNING, tomcat.getState()));
    }

    @Test
    void toString_ShouldReturnCorrectFormat() {

        Tomcat tomcat = new Tomcat(State.RUNNING);
        String tomcatString = tomcat.toString();

        String expectedString = "Tomcat(state=RUNNING)";
        assertEquals(expectedString, tomcatString, "The toString method should return the correct string representation of Tomcat object.");
    }

}