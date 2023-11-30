package monitoring.tomcat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

