package utc.edu.thesis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ThesisApplicationTests {
    @Autowired
    BCryptPasswordEncoder b;

    @Test
    void contextLoads() {
        System.out.println(b.encode("123456")); ;
    }

}
