package monitoring.tomcat;

import monitoring.appServer.tomcat.TomcatStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class TomcatStatusTest {

    @Test
    public void testDefaultConstructor() {
        TomcatStatus tomcatStatus = new TomcatStatus();
        assertNotNull(tomcatStatus, "TomcatStatus instance should not be null");
    }


        @Test
        public void testTomcatStatusIsRunning() {
            // Act
            TomcatStatus tomcatStatus = new TomcatStatus(true);

            // Assert
            assertTrue(tomcatStatus.isTomcatRunning());
        }

        @Test
        public void testTomcatStatusIsNotRunning(){
            // Act
            TomcatStatus tomcatStatus = new TomcatStatus(false);

            // Assert
            assertFalse(tomcatStatus.isTomcatRunning());
        }

    }

