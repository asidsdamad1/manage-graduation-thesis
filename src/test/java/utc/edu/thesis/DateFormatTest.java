package utc.edu.thesis;

import org.junit.jupiter.api.Test;
import utc.edu.thesis.util.DateUtil;

public class DateFormatTest {

    @Test
    void contextLoads() {
        System.out.println(DateUtil.toLocalDate("12/12/2001"));
    }
}
