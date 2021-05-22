package me.wirries.demo.springtransaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testcase for {@link SpringTransactionApplication}.
 */
class SpringTransactionApplicationTest extends AbstractApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void load() {
        assertNotNull(context);
    }

}
