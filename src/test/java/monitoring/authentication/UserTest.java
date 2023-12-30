package monitoring.authentication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {





    @Test
    public void testGetUser(){
        User user = new User();
        user.setUsername("jana");
        user.setPassword("password");
        user.setId(1L);
        user.setUserType(Role.ADMIN);


        assertAll("jana",
                () -> assertEquals("jana", user.getUsername()),
                () -> assertEquals("password", user.getPassword()),
                () -> assertEquals(1L, user.getId()),
                () -> assertEquals(Role.ADMIN, user.getUserType()));
    }
}