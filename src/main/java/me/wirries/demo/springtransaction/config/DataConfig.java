package me.wirries.demo.springtransaction.config;

import me.wirries.demo.springtransaction.model.Sample;
import me.wirries.demo.springtransaction.repo.SampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * This component configures the initial test data, if no data is available.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
@Component
public class DataConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataConfig.class);

    private final SampleRepository repository;

    @Autowired
    public DataConfig(SampleRepository repository) {
        this.repository = repository;
    }

    /**
     * Start with the initialization and setup when the application is ready.
     */
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {
        if (repository.count() == 0) {
            LOGGER.warn("No sample data exists - generate new data");
            setupSamples();
        }
    }

    private void setupSamples() {
        for (int i = 0; i < 25; i++) {
            Sample sample = new Sample();
            sample.setColUnique("Unique-" + i);
            sample.setColString("String-" + i);
            sample.setColInt(i);
            sample.setColDate(new Date());
            repository.save(sample);
        }
    }

}
