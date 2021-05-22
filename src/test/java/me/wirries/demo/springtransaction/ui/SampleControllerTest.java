package me.wirries.demo.springtransaction.ui;

import me.wirries.demo.springtransaction.AbstractWebTests;
import me.wirries.demo.springtransaction.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for {@link SampleController}.
 *
 * @author denisw
 * @version 1.0
 * @since 22.05.2021
 */
class SampleControllerTest extends AbstractWebTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        createTestData();
    }

    @Test
    void welcome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }

    @Test
    void reset() throws Exception {
        assertEquals(100, count());
        mockMvc.perform(post("/reset"))
                .andExpect(status().isOk());
        assertEquals(10, count());
    }

    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(post("/update"))
                .andExpect(status().isOk());

        final List<Sample> list = list();
        assertEquals("Updating first record", list.get(0).getColString());
    }

    @Test
    void updateMultiple() {
        assertEquals(100, count());
        assertThrows(Exception.class, () -> mockMvc.perform(post("/updateMultiple")));
    }

    @Test
    void updateTransactional() throws Exception {
        assertEquals(100, count());
        mockMvc.perform(post("/updateTransactional"))
                .andExpect(status().isOk())
                .andExpect(content().string("Update failed"));
        final List<Sample> list = list();
        assertEquals("Update before the transaction", list.get(0).getColString());
        assertEquals("Exception: Error during flush within the transaction", list.get(99).getColString());
        assertEquals(100, count());
    }

    @Test
    void fail() throws Exception {
        mockMvc.perform(post("/fail"))
                .andExpect(status().isInternalServerError());
    }

}
