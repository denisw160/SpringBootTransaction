package me.wirries.demo.springtransaction;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;

/**
 * This is the base class for all tests for the application.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
@SpringBootTest
@ActiveProfiles({"test", "noJobs"})
@ExtendWith(SpringExtension.class)
public abstract class AbstractApplicationTests {

    /**
     * Now.
     */
    protected static final Date now = new Date();

    /**
     * Return the date before the given days.
     *
     * @param days days
     * @return the date before the days
     */
    public static Date getDateBefore(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH, -1 * days);
        return cal.getTime();
    }

}
