package me.wirries.demo.springtransaction.ui;

import me.wirries.demo.springtransaction.model.Sample;
import me.wirries.demo.springtransaction.repo.SampleRepository;
import me.wirries.demo.springtransaction.service.SampleService;
import me.wirries.demo.springtransaction.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Default controller for this demo.
 * The controller handles all interactions with the UI.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
@Controller
public class SampleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    private static final String VIEW_NAME = "welcome";

    private final SampleService service;
    private final SampleRepository repository;

    @Autowired
    public SampleController(SampleService service, SampleRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    /**
     * Open the start page.
     *
     * @param model Model for the view
     * @return name of the view
     */
    @GetMapping("/")
    public String welcome(Model model) {
        LOGGER.debug("Entering welcome view");
        final Iterable<Sample> samples = repository.findAll();

        final List<Sample> list = new ArrayList<>();
        samples.forEach(list::add);

        model.addAttribute("samples", list);

        return VIEW_NAME;
    }

    /**
     * Delete the existing data and generate 25 new sample records.
     *
     * @return Success message
     */
    @PostMapping("/reset")
    @ResponseBody
    public String reset() {
        LOGGER.info("Resetting data ...");

        repository.deleteAll();
        long count = repository.count();
        LOGGER.debug("Count after delete: " + count);

        for (int i = 0; i < 10; i++) {
            repository.save(createSample(i));
        }

        count = repository.count();
        LOGGER.debug("Count after insert: " + count);

        LOGGER.info("Data reset complete.");
        return "Data reset complete";
    }

    /**
     * This method update the first sample record at the beginning of the list.
     *
     * @return Success message
     */
    @PostMapping("/update")
    @ResponseBody
    public String update() {
        LOGGER.info("Updating the first sample record ...");

        final Sample firstSample = repository.findTopByOrderByColInt();
        firstSample.setColString("Updating first record");
        repository.save(firstSample);

        LOGGER.info("Record updated.");
        return "Record updated.";
    }

    /**
     * This method updates multiple records and at least with one error / exception.
     *
     * @return Error message
     */
    @PostMapping("/updateMultiple")
    @ResponseBody
    public String updateMultiple() {
        LOGGER.info("Updating sample records ...");

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

        final long count = repository.count();
        LOGGER.debug("Count after update: " + count);

        LOGGER.info("Records updated.");
        return "Records updated.";
    }

    /**
     * This method updates multiple records with a transaction. If an error occurs,
     * the last record is updated with the exception text.
     *
     * @return Success message
     */
    @PostMapping("/updateTransactional")
    @ResponseBody
    public String updateTransactional() {
        LOGGER.info("Updating with transaction ...");

        final Sample firstSample = repository.findTopByOrderByColInt();
        firstSample.setColString("Update before the transaction");
        repository.save(firstSample);

        try {
            service.update();
            LOGGER.info("Update successful");
            return "Update successfully";
        } catch (ServiceException e) {
            LOGGER.error("An exception occurs during update with transaction", e);
            final Sample lastSample = repository.findTopByOrderByColIntDesc();
            lastSample.setColString("Exception: " + e.getMessage());
            repository.save(lastSample);
            return "Update failed";
        }
    }

    /**
     * This method return an error (500) after 5s.
     *
     * @param response Response for the client
     * @return Fail message
     */
    @PostMapping("/fail")
    @ResponseBody
    public String fail(HttpServletResponse response) {
        LOGGER.info("Calling fail ... wait 5 seconds ...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOGGER.error("Failed to sleep", e);
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        LOGGER.info("Fail completed.");
        return "This action always fail.";
    }

    /**
     * Create a new Sample record.
     *
     * @param i Int
     * @return new Sample
     */
    private Sample createSample(int i) {
        Sample sample = new Sample();
        sample.setColUnique("Unique-" + i);
        sample.setColString("String-" + i);
        sample.setColInt(i);
        sample.setColDate(new Date());
        return sample;
    }

}
