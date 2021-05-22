package me.wirries.demo.springtransaction;

import me.wirries.demo.springtransaction.model.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Base test class for all database related tests.
 *
 * @author denisw
 * @version 1.0
 */
@Transactional
public abstract class AbstractDatabaseTests extends AbstractApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatabaseTests.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * Creates test data.
     */
    protected void createTestData() {
        // Clean database
        remove(Sample.class);
        assertEquals(0, count(Sample.class));

        // Build test data
        for (int i = 0; i < 100; i++) {
            Sample sample = new Sample();
            sample.setColUnique("Unique-" + i);
            sample.setColString("String-" + i);
            sample.setColInt(i);
            sample.setColDate(getDateBefore(i));
            store(sample);
        }
        flush();

        // Ensure data exists
        assertEquals(100, count(Sample.class));
    }

    /**
     * Returns the entity manager for this session.
     *
     * @return the javax.persistence.EntityManager
     */
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Return the count of all records of the given entity.
     *
     * @param entity count for this entity
     * @return count
     */
    public <T> long count(Class<T> entity) {
        TypedQuery<Long> query = em.createQuery("select count(*) from " + entity.getName(), Long.class);
        Long count = query.getSingleResult();
        return (count != null) ? count : 0;
    }

    /**
     * Return the count of all records of the given entity.
     *
     * @param entity     count for this entity, alias for this entity is e
     * @param condition  where-condition for this entity (parameters are ?0, ?1, ...)
     * @param parameters (optional) parameters in the where condition
     * @return count
     */
    public <T> long count(Class<T> entity, String condition, Object... parameters) {
        TypedQuery<Long> query = em.createQuery("select count(*) from "
                + entity.getName() + " e where " + condition, Long.class);

        int i = 0;
        for (Object p : parameters) {
            query.setParameter(i, p);
            i++;
        }

        Long count = query.getSingleResult();
        return (count != null) ? count : 0;
    }

    /**
     * Remove the data from the given entity.
     *
     * @param entity Name of the entity
     */
    public <T> void remove(Class<T> entity) {
        Query query = em.createQuery("delete from " + entity.getName());
        int update = query.executeUpdate();
        LOGGER.debug("Deleted {}  from {}", update, entity);
    }

    /**
     * Remove the given entity in the database.
     *
     * @param entity Entity for remove
     */
    public void remove(Object entity) {
        em.remove(entity);
        LOGGER.debug("Removed {}", entity);
    }

    /**
     * Stores the given entity in the database.
     *
     * @param entity Entity for persist
     */
    public void store(Object entity) {
        em.persist(entity);
        LOGGER.debug("Stored {}", entity);
    }

    /**
     * Execute an update statement.
     *
     * @param qlStatement Statement for execute update, where-condition can use
     *                    parameters like this ?0, ?1, ...
     * @param parameters  (optional) parameters in the where condition
     * @return count of updated rows
     */
    public long update(String qlStatement, Object... parameters) {
        Query query = em.createQuery(qlStatement);
        int i = 0;
        for (Object p : parameters) {
            query.setParameter(i, p);
            i++;
        }

        return query.executeUpdate();
    }

    /**
     * Return the list of the records for the given entity.
     *
     * @param entity entity for this query
     * @return list of the entity
     */
    public <T> List<T> list(Class<T> entity) {
        TypedQuery<T> query = em.createQuery("select e from "
                + entity.getName() + " e", entity);
        return query.getResultList();
    }

    /**
     * Return the list of the records for the given entity.
     *
     * @param entity     entity for this query, alias for this entity is e
     * @param condition  where-condition for this entity (parameters are ?0, ?1, ...)
     * @param parameters (optional) parameters in the where condition
     * @return list of the entity
     */
    public <T> List<T> list(Class<T> entity, String condition, Object... parameters) {
        TypedQuery<T> query = em.createQuery("select e from "
                + entity.getName() + " e where " + condition, entity);

        int i = 0;
        for (Object p : parameters) {
            query.setParameter(i, p);
            i++;
        }

        return query.getResultList();
    }

    /**
     * Return a single result, if exists. If no result, null is returned. If
     * multiple results, the first result is returned.
     *
     * @param entity     entity for this query, alias for this entity is e
     * @param condition  where-condition for this entity (parameters are ?0, ?1, ...)
     * @param parameters (optional) parameters in the where condition
     * @return single result or null
     */
    public <T> T single(Class<T> entity, String condition, Object... parameters) {
        final List<T> list = list(entity, condition, parameters);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * Flush EntityManager.
     */
    public void flush() {
        em.flush();
        LOGGER.debug("EntityManager flushed");
    }

}

