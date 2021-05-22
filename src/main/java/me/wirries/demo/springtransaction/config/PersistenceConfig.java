package me.wirries.demo.springtransaction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration for the persistence layer.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
public class PersistenceConfig {

}
