<%-- Scenario 1 --%>
<div class="row pb-3 mb-5 border-bottom">
    <div class="col-12">
        <h2>Scenario 1: Update an element</h2>
        <p class="fs-6">
            Update one sample record at the start of the current records. Use no transaction (except for the
            repository). The expected result is at the start of the table.
        </p>

        <div id="collapseScenario1" class="collapse">
            <pre><code class="language-java rounded">
    /**
     * This method update the first sample record at the beginning of the list.
     *
     * @return Success message
     */
    @PostMapping(&quot;/update&quot;)
    @ResponseBody
    public String update() {
        LOGGER.info(&quot;Updating the first sample record ...&quot;);

        final Sample firstSample = repository.findTopByOrderByColInt();
        firstSample.setColString(&quot;Updating first name&quot;);
        repository.save(firstSample);

        LOGGER.info(&quot;Record updated.&quot;);
        return &quot;Record updated.&quot;;
    }
            </code></pre>
        </div>

        <div class="mt-1 px-3 d-flex justify-content-end">
            <button class="btn btn-primary" type="button" onclick="ExecuteAction.execute('/update')">
                <i class="fas fa-play"></i> Run
            </button>

            <button class="btn btn-secondary ms-1" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseScenario1" aria-expanded="false"
                    aria-controls="collapseScenario1">
                <i class="fas fa-code"></i> Source Code
            </button>
        </div>
    </div>
</div>

<%-- Scenario 2 --%>
<div class="row pb-3 mb-5 border-bottom">
    <div class="col-12">
        <h2>Scenario 2: Update multiple element without transaction</h2>
        <p class="fs-6">
            Update one by one the sample records. Use no transaction (except for the repository).
            The last records is invalid and throws an exception. Without a transaction the first updates
            are stored / persist in the database, the last one not. So the data is now not completed processed
            and possible corrupt.
        </p>

        <div id="collapseScenario2" class="collapse">
            <pre><code class="language-java rounded">
    /**
     * This method updates multiple records and at least with one error / exception.
     *
     * @return Error message
     */
    @PostMapping(&quot;/updateMultiple&quot;)
    @ResponseBody
    public String updateMultiple() {
        LOGGER.info(&quot;Updating sample records ...&quot;);

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

        LOGGER.info(&quot;Records updated.&quot;);
        return &quot;Records updated.&quot;;
    }
        </code></pre>
        </div>

        <div class="mt-1 px-3 d-flex justify-content-end">
            <button class="btn btn-primary" type="button" onclick="ExecuteAction.execute('/updateMultiple')">
                <i class="fas fa-play"></i> Run
            </button>

            <button class="btn btn-secondary ms-1" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseScenario2" aria-expanded="false"
                    aria-controls="collapseScenario2">
                <i class="fas fa-code"></i> Source Code
            </button>
        </div>
    </div>
</div>

<%-- Scenario 3 --%>
<div class="row pb-3 mb-5 border-bottom">
    <div class="col-12">
        <h2>Scenario 3: Update multiple element with a transaction</h2>
        <p class="fs-6">
            Update one by one the sample records. Use a transaction around the update.
            The last records is invalid and throws an exception. The first records is updated before the transaction
            begins. Within the transaction all changes are reverted and only the last record is
            updated with the error message.
        </p>

        <div id="collapseScenario3" class="collapse">
            <pre><code class="language-java rounded">
    /**
     * This method updates multiple records with a transaction. If an error occurs,
     * the last record is updated with the exception text.
     *
     * @return Success message
     */
    @PostMapping(&quot;/updateTransactional&quot;)
    @ResponseBody
    public String updateTransactional() {
        LOGGER.info(&quot;Updating with transaction ...&quot;);

        final Sample firstSample = repository.findTopByOrderByColInt();
        firstSample.setColString(&quot;Update before the transaction&quot;);
        repository.save(firstSample);

        try {
            service.update();
            LOGGER.info(&quot;Update successful&quot;);
            return &quot;Update successfully&quot;;
        } catch (ServiceException e) {
            LOGGER.error(&quot;An exception occurs during update with transaction&quot;, e);
            final Sample lastSample = repository.findTopByOrderByColIntDesc();
            lastSample.setColString(&quot;Exception: &quot; + e.getMessage());
            repository.save(lastSample);
            return &quot;Update failed&quot;;
        }
    }
        </code></pre>
        </div>

        <div class="mt-1 px-3 d-flex justify-content-end">
            <button class="btn btn-primary" type="button" onclick="ExecuteAction.execute('/updateTransactional')">
                <i class="fas fa-play"></i> Run
            </button>

            <button class="btn btn-secondary ms-1" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseScenario3" aria-expanded="false"
                    aria-controls="collapseScenario3">
                <i class="fas fa-code"></i> Source Code
            </button>
        </div>
    </div>
</div>
