package me.wirries.demo.springtransaction;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

/**
 * This is the base class for all web / controller tests.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
@AutoConfigureMockMvc
public abstract class AbstractWebTests extends AbstractDatabaseTests {

}
