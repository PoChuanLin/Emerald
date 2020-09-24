package net.nextgen.emerald.service;

import javax.inject.Inject;
import java.time.LocalDate;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import net.nextgen.emerald.vo.Dependent;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class DependentServiceTest {

    @Inject
    private DependentService dependentService;

    // create Dependent - good path
    @Test
    @DatabaseSetup("createDependent.xml")
    void testCreate() throws Exception {
        Dependent dependent = dependentService.create("dep20", LocalDate.now(), 3);
        // newly created Dependent
        assertNotNull(dependent);
        assertEquals("dep20", dependent.getName());
        // associated Enrollee
        assertNotNull(dependent.getEnrollee());
        assertEquals("Foo3", dependent.getEnrollee().getName());
    }

    // create Dependent - bad Enrollee ID
    @Test
    @DatabaseSetup("createDependent.xml")
    void testCreate_wrongEnrolleeID() throws Exception {
        Exception exception = assertThrows(EnrolleeNotFoundException.class, () -> {
            dependentService.create("dep20", LocalDate.now(), 99);
        });

        String expectedMessage = "Could not find enrollee 99";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // read Dependent - good path
    @Test
    @DatabaseSetup("createDependent.xml")
    void testRead() throws Exception {
        Dependent dependent = dependentService.read(66L);
        // fetched Dependent
        assertNotNull(dependent);
        assertEquals("Bar6", dependent.getName());
        // fetched Dependent's Enrollee
        assertNotNull(dependent.getEnrollee());
        assertEquals("Foo3", dependent.getEnrollee().getName());
    }

    // read Dependent - wrong ID
    @Test
    @DatabaseSetup("createDependent.xml")
    void testRead_wrongID() throws Exception {
        Exception exception = assertThrows(DependentNotFoundException.class, () -> {
           dependentService.read(99L);
        });

        String expectedMessage = "Could not find dependent 99";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
