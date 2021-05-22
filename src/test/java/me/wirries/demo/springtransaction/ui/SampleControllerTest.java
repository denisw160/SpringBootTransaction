package me.wirries.demo.springtransaction.ui;

import me.wirries.demo.springtransaction.AbstractWebTests;
import me.wirries.demo.springtransaction.model.Sample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

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
        assertEquals(100, count(Sample.class));
        mockMvc.perform(post("/reset"))
                .andExpect(status().isOk());
        assertEquals(10, count(Sample.class));
    }

    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(post("/update"))
                .andExpect(status().isOk());
        assertEquals("Updating first record", single(Sample.class, "colInt=0").getColString());
    }

    @Test
    void updateMultiple() throws Exception {
        assertEquals(100, count(Sample.class));
        assertThrows(Exception.class, () -> mockMvc.perform(post("/updateMultiple")));
    }

    @Test
    void updateTransactional() throws Exception {
        assertEquals(100, count(Sample.class));
        mockMvc.perform(post("/updateTransactional"))
                .andExpect(status().isOk())
                .andExpect(content().string("Update failed"));
        assertEquals("Update before the transaction", single(Sample.class, "colInt=0").getColString());
        assertEquals("Exception: Error during flush within the transaction", single(Sample.class, "colInt=99").getColString());
    }

    @Test
    void fail() throws Exception {
        mockMvc.perform(post("/fail"))
                .andExpect(status().isInternalServerError());
    }

}
