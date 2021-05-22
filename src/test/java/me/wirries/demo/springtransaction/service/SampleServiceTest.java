package me.wirries.demo.springtransaction.service;

import me.wirries.demo.springtransaction.AbstractDatabaseTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testcase for {@link SampleService}.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
class SampleServiceTest extends AbstractDatabaseTests {

    @Autowired
    private SampleService service;

    @BeforeEach
    void setUp() {
        createTestData();
    }

    @Test
    void testUpdate() {
        assertThrows(ServiceException.class, () -> service.update());
    }

}
