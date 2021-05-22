package me.wirries.demo.springtransaction;

import me.wirries.demo.springtransaction.model.Sample;
import me.wirries.demo.springtransaction.repo.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * This is the base class for all web / controller tests.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
@AutoConfigureMockMvc
public abstract class AbstractWebTests extends AbstractApplicationTests {

    @Autowired
    private SampleRepository repository;

    /**
     * Creates test data.
     */
    protected void createTestData() {
        // Clean database
        repository.deleteAll();
        assertEquals(0, count());

        // Build test data
        for (int i = 0; i < 100; i++) {
            Sample sample = new Sample();
            sample.setColUnique("Unique-" + i);
            sample.setColString("String-" + i);
            sample.setColInt(i);
            sample.setColDate(getDateBefore(i));
            repository.save(sample);
        }

        // Ensure data exists
        assertEquals(100, count());
    }

    protected long count() {
        return repository.count();
    }

    protected List<Sample> list() {
        final Iterable<Sample> samples = repository.findAll();

        final List<Sample> list = new ArrayList<>();
        samples.forEach(list::add);

        list.sort(Comparator.comparing(Sample::getColInt));

        return list;
    }

}
