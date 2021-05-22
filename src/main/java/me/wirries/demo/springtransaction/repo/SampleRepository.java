package me.wirries.demo.springtransaction.repo;

import me.wirries.demo.springtransaction.model.Sample;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * This repository handles the {@link Sample}.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
@Transactional
public interface SampleRepository extends CrudRepository<Sample, String> {

    /**
     * Find all {@link Sample} by the given colUnique value.
     *
     * @param colUnique colUnique
     * @return List of matching {@link Sample}s
     */
    List<Sample> findAllByColUnique(String colUnique);

    /**
     * Find the first sample with the lowest colInt value.
     *
     * @return Sample record
     */
    Sample findTopByOrderByColInt();

    /**
     * Find the first sample with the biggest colInt value.
     *
     * @return Sample record
     */
    Sample findTopByOrderByColIntDesc();

}
