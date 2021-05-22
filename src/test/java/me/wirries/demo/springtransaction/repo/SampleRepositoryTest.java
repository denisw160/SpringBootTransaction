package me.wirries.demo.springtransaction.repo;

import me.wirries.demo.springtransaction.AbstractDatabaseTests;
import me.wirries.demo.springtransaction.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link SampleRepository}.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
class SampleRepositoryTest extends AbstractDatabaseTests {

    @Autowired
    private SampleRepository repository;

    @BeforeEach
    void setUp() {
        createTestData();
    }

    @Test
    void audit() throws InterruptedException {
        // Create new entry
        Sample sample = new Sample();
        sample.setColUnique("audit");

        // Audit information not exists
        assertNull(sample.getCreatedAt());
        assertNull(sample.getUpdatedAt());

        // Save entity
        repository.save(sample);
        flush();

        // Audit information added
        assertNotNull(sample.getCreatedAt());
        assertNotNull(sample.getUpdatedAt());

        // Check last updated
        final long createdAtMills = sample.getCreatedAt().getTime();
        final long updatedAtMills = sample.getUpdatedAt().getTime();
        Thread.sleep(1000); // Wait 100 ms
        assertEquals(createdAtMills, sample.getCreatedAt().getTime());
        assertEquals(updatedAtMills, sample.getUpdatedAt().getTime());

        sample.setColInt(1);
        repository.save(sample);
        flush();

        assertEquals(createdAtMills, sample.getCreatedAt().getTime());
        assertTrue(updatedAtMills < sample.getUpdatedAt().getTime());

        // Test all entities
        for (Sample s : repository.findAll()) {
            assertNotNull(s.getUuid());
            assertNotNull(s.getCreatedAt());
            assertNotNull(s.getUpdatedAt());
        }
    }

    @Test
    void findAllByColUnique() {
        List<Sample> list = repository.findAllByColUnique("Unique-99");
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("String-99", list.get(0).getColString());

        list = repository.findAllByColUnique("Unique-XX");
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    void findTopByColIntOrderByColInt() {
        final Sample sample = repository.findTopByOrderByColInt();
        assertNotNull(sample);
        assertEquals(0, sample.getColInt());
    }

    @Test
    void findTopByColIntOrderByColIntDesc() {
        final Sample sample = repository.findTopByOrderByColIntDesc();
        assertNotNull(sample);
        assertEquals(99, sample.getColInt());
    }

}
