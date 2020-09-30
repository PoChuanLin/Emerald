package net.nextgen.emerald.service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.nextgen.emerald.vo.Enrollee;
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
    void testRead_GoodPath() throws Exception {
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

    @Test
    @DatabaseSetup("createDependent.xml")
    void testUpdate() throws Exception {
        // when
        Dependent change = new Dependent
                ("BarNew", LocalDate.parse("2000-07-04"), new Enrollee("roller", true, LocalDate.parse("1999-12-25")));
        dependentService.update(66L, change);
        // then
        Dependent dependent = dependentService.read(66L);
        assertEquals("BarNew", dependent.getName());
        assertEquals(LocalDate.parse("2000-07-04"), dependent.getDob());
    }

    @Test
    @DatabaseSetup("createDependent.xml")
    void testDelete() throws Exception {
        // given
        Dependent existing = dependentService.read(61L);
        assertNotNull (existing);
        // when
        dependentService.delete(61L);
        // then
        Exception exception = assertThrows(DependentNotFoundException.class, () -> {
            dependentService.read(61L);
        });
    }

    @Test
    @DatabaseSetup("createDependent.xml")
    void testDeleteByEnrolleeId() throws Exception {
        // given
        List<Dependent> dependents = dependentService.findByEnrolleeId(2L);
        assertEquals(3, dependents.size());
        // when
        dependentService.deleteByEnrolleeId(2L);
        // then
        List<Dependent> left = dependentService.findByEnrolleeId(2L);
        assertEquals(0, left.size());
    }
}
