package net.nextgen.emerald.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import net.nextgen.emerald.service.EnrolleeService;

@WebMvcTest(controllers = EnrolleeController.class)
public class EnrolleeControllerTest {

    @Inject
    private MockMvc mockMvc;
    @Inject
    private ObjectMapper objectMapper;

    @MockBean
    private EnrolleeService enrolleeService;

    // parsing Enrollee, note the phone is optional
    @Test
    void testInputParsing() throws Exception {
        String enrollee = """
                {"name":"Foo27", "activation":true, "dob":"2000-03-27"}
                """;

        mockMvc.perform(post("/enrollees")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().isOk());
    }

    @Test
    void testInputValidation() throws Exception {
        String enrollee = """
                {"name":"Foo27", "dob":"2000-03-27"}
                """;

        mockMvc.perform(post("/enrollees")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().isBadRequest());
    }
}
