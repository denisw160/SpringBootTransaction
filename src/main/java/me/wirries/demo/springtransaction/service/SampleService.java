package me.wirries.demo.springtransaction.service;

import me.wirries.demo.springtransaction.model.Sample;
import me.wirries.demo.springtransaction.repo.SampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * This service updates the database within a transaction.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
@Service
public class SampleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleService.class);

    @PersistenceContext
    private EntityManager em;

    private final SampleRepository repository;

    @Autowired
    public SampleService(SampleRepository repository) {
        this.repository = repository;
    }

    /**
     * This methods updates multiple records in a transaction.
     * If an error occurs, the Exception is thrown.
     *
     * @throws ServiceException Error during update
     */
    @Transactional(rollbackOn = {ServiceException.class}) // <-- rollbackOn is required for execute rollback
    public void update() throws ServiceException {
        LOGGER.info("Updating sample records within a transaction ...");

        try {
            final Iterable<Sample> samples = repository.findAll();
            final List<Sample> list = new ArrayList<>();
            samples.forEach(list::add);

            int i = 0;
            Sample lastUpdate = null;
            for (Sample s : list) {
                if (lastUpdate != null && (i + 1) == list.size()) {
                    s.setColUnique(lastUpdate.getColUnique());
                }

                s.setColString("Updated without transaction");
                repository.save(s);
                lastUpdate = s;
                i++;
            }

            em.flush(); // Flush is required for sync memory with database to raise the error
            // Alternatively execute a select statement on table, e.g. repository.count()

            LOGGER.info("Records updated.");
        } catch (Exception e) {
            LOGGER.debug("Expected exception occurs and will raised");
            throw new ServiceException("Error during flush within the transaction", e);
        }
    }

}
