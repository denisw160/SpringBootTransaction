<%-- Additional information --%>
<div class="row pb-2">
    <div class="col-12">
        <h2>Additional information</h2>
        <p class="fs-6">
            In this section you found the used backend components.
        </p>
    </div>
</div>

<%-- SampleRepository --%>
<div class="row pb-2">
    <div class="col-12">
        <h3>SampleRepository</h3>
        <p class="fs-6">
            The repository is a <code>CrudRepository</code> from Spring Data and is used for interacting
            with the database. The repository is <code>@Transactional</code>.
        </p>

        <div id="collapseSampleRepository" class="collapse">
            <pre><code class="language-java rounded">
    /**
     * This repository handles the {@link Sample}.
     *
     * @author denisw
     * @version 1.0
     * @since 22.05.2021
     */
    @Transactional
    public interface SampleRepository extends CrudRepository&lt;Sample, String&gt; {

        /**
         * Find all {@link Sample} by the given colUnique value.
         *
         * @param colUnique colUnique
         * @return List of matching {@link Sample}s
         */
        List&lt;Sample&gt; findAllByColUnique(String colUnique);

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
            </code></pre>
        </div>

        <div class="mb-2 d-flex justify-content-end">
            <button class="btn btn-secondary" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseSampleRepository" aria-expanded="false"
                    aria-controls="collapseSampleRepository">
                <i class="fas fa-code"></i>
                Source Code
            </button>
        </div>
    </div>
</div>

<%-- SampleService --%>
<div class="row pb-2">
    <div class="col-12">
        <h3>SampleRepository</h3>
        <p class="fs-6">
            The repository is a <code>CrudRepository</code> from Spring Data and is used for interacting
            with the database. The repository is <code>@Transactional</code>.
        </p>

        <div id="collapseSampleService" class="collapse">
            <pre><code class="language-java rounded">
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
        @Transactional(rollbackOn = {ServiceException.class}) // &lt;-- rollbackOn is required for execute rollback
        public void update() throws ServiceException {
            LOGGER.info(&quot;Updating sample records within a transaction ...&quot;);

            try {
                final Iterable&lt;Sample&gt; samples = repository.findAll();
                final List&lt;Sample&gt; list = new ArrayList&lt;&gt;();
                samples.forEach(list::add);

                int i = 0;
                Sample lastUpdate = null;
                for (Sample s : list) {
                    if (lastUpdate != null &amp;&amp; (i + 1) == list.size()) {
                        s.setColUnique(lastUpdate.getColUnique());
                    }

                    s.setColString(&quot;Updated without transaction&quot;);
                    repository.save(s);
                    lastUpdate = s;
                    i++;
                }

                em.flush(); // Flush is required for sync memory with database to raise the error
                // Alternatively execute a select statement on table, e.g. repository.count()

                LOGGER.info(&quot;Records updated.&quot;);
            } catch (Exception e) {
                LOGGER.debug(&quot;Expected exception occurs and will raised&quot;);
                throw new ServiceException(&quot;Error during flush within the transaction&quot;, e);
            }
        }

    }
            </code></pre>
        </div>

        <div class="mb-2 d-flex justify-content-end">
            <button class="btn btn-secondary" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseSampleService" aria-expanded="false"
                    aria-controls="collapseSampleService">
                <i class="fas fa-code"></i>
                Source Code
            </button>
        </div>
    </div>
</div>
