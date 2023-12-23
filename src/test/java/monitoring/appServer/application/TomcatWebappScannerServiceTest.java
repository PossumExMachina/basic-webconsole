package monitoring.appServer.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TomcatWebappScannerServiceTest {


    @Test
    void IfOneAppInWebapps_ThenReturnOneApp(){
        String[] directories = {"app1"};
    }
}