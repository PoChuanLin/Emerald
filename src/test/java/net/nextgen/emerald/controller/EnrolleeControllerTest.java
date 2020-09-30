package net.nextgen.emerald.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import net.nextgen.emerald.service.EnrolleeService;

/**  Tests for Web Controller tier - only.
 *   Service tier is mocked.
 */
@WebMvcTest(controllers = EnrolleeController.class)
public class EnrolleeControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private EnrolleeService enrolleeService;

    /*  POST /enrollees
        content required, phone is optional  */

    @Test
    void testPostEnrollees_InputValidation_GoodPath() throws Exception {
        String enrollee = """
                {"name":"Foo27", "activation":true, "dob":"2000-03-27"}
                """;

        mockMvc.perform(post("/enrollees")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().isOk());
    }

    @Test
    void testPostEnrollees_InputValidation_WrongDobFormat() throws Exception {
        String enrollee = """
                {"name":"Foo27", "activation":true, "dob":"03/27/2000"}
                """;

        mockMvc.perform(post("/enrollees")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostEnrollees_InputValidation_MissingActivation() throws Exception {
        String enrollee = """
                {"name":"Foo27", "dob":"2000-03-27"}
                """;

        mockMvc.perform(post("/enrollees")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostEnrollees_InputValidation_MissingDoB() throws Exception {
        String enrollee = """
                {"name":"Foo27", "activation":true}
                """;
        mockMvc.perform(post("/enrollees")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostEnrollees_InputValidation_MissingName() throws Exception {
        String enrollee = """
                {"activation":true, "dob":"2000-03-27"}
                """;
        mockMvc.perform(post("/enrollees")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().isBadRequest());
    }

    /* GET /enrollees
       fetch all Enrollees, neither parameter nor content required */

    @Test
    void testGetEnrollees_GoodPath() throws Exception {
       mockMvc.perform(get("/enrollees"))
               .andExpect(status().isOk());
    }

    /* GET /enrollees/{id}
       fetch an Enrollee by ID : id as path parameter, content not required */

    @Test
    void testGetEnrolleesId_GoodPath() throws Exception {
        mockMvc.perform(get("/enrollees/999"))
                .andExpect(status().isOk());
    }

    /* PUT /enrollees/{id}
       update Enrollee by id as path parameter, id field in content is ignored.    */

    @Test
    void testPutEnrolleesId_GoodPath() throws Exception {
        String enrollee = """
                {"name":"Foo27", "activation":true, "dob":"2000-03-27"}
                """;

        mockMvc.perform(put("/enrollees/999")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().isOk());
    }

    /** ID for Enrollee to be updated must be in URL Path,
     *   id in content is ignored. */
    @Test
    void testPutEnrolleesId_MissingPathId() throws Exception {
        String enrollee = """
                {"id":999 , "name":"Foo27", "activation":true, "dob":"2000-03-27"}
                """;

        mockMvc.perform(put("/enrollees")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().is4xxClientError());
    }

    /*  DELETE /enrollees/{id}
        delete Enrollee by id as path parameter, no content required.  */

    @Test
    void testDeleteEnrolleesId_GoodPath() throws Exception {
        mockMvc.perform(delete("/enrollees/999"))
                .andExpect(status().isOk());
    }

    /** ID for Enrollee to be deleted must be in URL Path,
        content is ignored. */
    @Test
    void testDeleteEnrolleesId_MissingPathId() throws Exception {
        String enrollee = """
                {"id":999 , "name":"Foo27", "activation":true, "dob":"2000-03-27"}
                """;

        mockMvc.perform(delete("/enrollees")
                .contentType("application/json")
                .content(enrollee))
                .andExpect(status().is4xxClientError());
    }
}
