package net.nextgen.emerald;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import net.nextgen.emerald.dao.DependentRepository;

/** Integration Test - work through all layers
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class DependentApplicationIntegrationTest {

    @Inject
    private MockMvc mockMvc;
    @Inject
    private DependentRepository dependentRepository;

    @Test
    @DatabaseSetup("createEnrollee.xml")
    void testCreate_GoodPath() throws Exception {
        // Given
        long countBefore = dependentRepository.findByEnrolleeId(3).size();

        // When
        mockMvc.perform(post("/enrollees/{id}/dependents", 3L)
                .contentType("application/json")
                .param("name", "nobody")
                .param("dob", "2020-02-03"))
                .andExpect(status().isOk());

        // Then
        long countAfter = dependentRepository.findByEnrolleeId(3).size();
        assertEquals(countBefore + 1, countAfter);
    }

    @Test
    @DatabaseSetup("createEnrollee.xml")
    void testCreate_wrongEnrolleeID() throws Exception {
        mockMvc.perform(post("/enrollees/{id}/dependents", 99L)
                .contentType("application/json")
                .param("name", "nobody")
                .param("dob", "2020-02-03"))
                .andExpect(status().is4xxClientError());
    }
}
