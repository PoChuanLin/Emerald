package net.nextgen.emerald.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import net.nextgen.emerald.service.DependentService;

/**  Tests for Web Controller tier - only.
 *   Service tier is mocked.
 */
@WebMvcTest(controllers = DependentController.class)
public class DependentControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private DependentService dependentService;

    /* POST /dependents
       content required, enrollee field only requires id */

    @Test
    void testPostDependents_HappyPath() throws Exception {
        String dependent = """
            {"name":"family first", "dob":"2000-05-23", "enrollee":{"id":29 ,"name":"good parent", "activation":true, "dob":"2000-08-23"}}
            """;
        mockMvc.perform(post("/dependents")
                .contentType("application/json")
                .content(dependent))
                .andExpect(status().isCreated());
    }

    @Test
    void testPostDependents_MissingEnrollee() throws Exception {
        String dependent = """
            {"name":"family first", "dob":"2000-05-23"}
            """;
        mockMvc.perform(post("/dependents")
                .contentType("application/json")
                .content(dependent))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostDependents_MissingDob() throws Exception {
        String dependent = """
            {"name":"family first", "enrollee":{"id":29}}
            """;
        mockMvc.perform(post("/dependents")
                .contentType("application/json")
                .content(dependent))
                .andExpect(status().isBadRequest());
    }

    /* POST /enrollees/{enrollee_id}/dependents
       dependent name & dob as request parameters
     */

    @Test
    void testPostEnrolleesIdDependents_HappyPath() throws Exception {
        mockMvc.perform(post("/enrollees/{enrollee_id}/dependents", 33L)
                .param("name", "family first")
                .param("dob", "2020-02-03"))
                .andExpect(status().isCreated());
    }

    @Test
    void testPostEnrolleesIdDependents_MissingDob() throws Exception {
        mockMvc.perform(post("/enrollees/{enrollee_id}/dependents", 33L)
                .param("name", "family first"))
                .andExpect(status().isBadRequest());
    }

    /* GET /dependents
         no content
     */

    @Test
    void testGetDependents_GoodPath() throws Exception {
        mockMvc.perform(get("/dependents"))
                .andExpect(status().isOk());
    }

    /* GET /dependents/{id}
         no content
     */

    @Test
    void testGetDependentsId_GoodPath() throws Exception {
        mockMvc.perform(get("/dependents/{id}", 66L))
                .andExpect(status().isOk());
    }

    /* GET /enrollees/{enrollee_id}/dependents
         no content
     */

    @Test
    void testGetEnrolleesIdDependents_GoodPath() throws Exception {
        mockMvc.perform(get("/enrollees/{enrollee_id}/dependents", 33L))
                .andExpect(status().isOk());
    }

    /* PUT /dependents/{id}
         update dependent by id as path parameter.
         both id & enrollee fields in content are ignored in update.
     */

    @Test
    void testPutDependentsId_GoodPath() throws Exception {
        String dependent = """
            {"name":"family first", "dob":"2000-05-23", "enrollee":{"id":3 ,"name":"void", "activation":true, "dob":"2000-08-23"}}
            """;
        mockMvc.perform(put("/dependents/{id}", 33L)
                .contentType("application/json")
                .content(dependent))
                .andExpect(status().isOk());
    }

    /* DELETE /dependents/{id}
     */

    @Test
    void testDeleteDependentsId_HappyPath() throws Exception {
        mockMvc.perform(delete("/dependents/{id}", 99L))
                .andExpect(status().isAccepted());
    }

    /* DELETE /enrollees/{enrollee_id}/dependents
     */

    @Test
    void testDeleteEnrolleeIdDependents_HappyPath() throws Exception {
        mockMvc.perform(delete("/enrollees/{enrollee_id}/dependents", 33L))
                .andExpect(status().isAccepted());
    }
}
