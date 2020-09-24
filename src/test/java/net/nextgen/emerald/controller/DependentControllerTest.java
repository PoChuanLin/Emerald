package net.nextgen.emerald.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import net.nextgen.emerald.service.DependentService;

@WebMvcTest(controllers = DependentController.class)
public class DependentControllerTest {

    @Inject
    private MockMvc mockMvc;
    @Inject
    private ObjectMapper objectMapper;

    @MockBean
    private DependentService dependentService;

    // note the format for dob LocalDate
    @Test
    void testInputParsing() throws Exception {
        mockMvc.perform(post("/enrollees/{id}/dependents", 33L)
                .contentType("application/json")
                .param("name", "nobody")
                .param("dob", "2020-02-03"))
                .andExpect(status().isOk());
    }

    @Test
    void verifyInputSerialization() throws Exception {
        String dependent = """
            {"name":"D29C", "dob":"2000-05-23", "enrollee":{"id":29 ,"name":"void", "activation":true, "dob":"2000-08-23"}}
            """;
        mockMvc.perform(post("/dependents")
                .contentType("application/json")
                .content(dependent))
                .andExpect(status().isOk());
    }

    // note: dependent's parent enrollee - only need ID
    @Test
    void verifyInputValidation0() throws Exception {
        String dependent = """
            {"name":"D29C", "dob":"2000-05-23", "enrollee":{"id":29}}
            """;
        mockMvc.perform(post("/dependents")
                .contentType("application/json")
                .content(dependent))
                .andExpect(status().isOk());
    }

    // case: dependent missing enrollee
    @Test
    void verifyInputValidation1() throws Exception {
        String dependent = """
            {"name":"D29C", "dob":"2000-05-23"}
            """;
        mockMvc.perform(post("/dependents")
                .contentType("application/json")
                .content(dependent))
                .andExpect(status().isBadRequest());
    }

    // case: dependent missing dob
    @Test
    void verifyInputValidation2() throws Exception {
        String dependent = """
            {"name":"D29C", "enrollee":{"id":29}}
            """;
        mockMvc.perform(post("/dependents")
                .contentType("application/json")
                .content(dependent))
                .andExpect(status().isBadRequest());
    }
}
