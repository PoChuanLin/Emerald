package net.nextgen.emerald.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import net.nextgen.emerald.vo.Dependent;
import net.nextgen.emerald.vo.Enrollee;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class EnrolleeServiceTest {

    @Inject
    private EnrolleeService enrolleeService;
    @Inject
    private DependentService dependentService;

    @Test
    @DatabaseSetup("createDependent.xml")
    void testUpdate() throws Exception {
        // given
        Enrollee enrollee = enrolleeService.read(5L);
        assertEquals ("Foo5", enrollee.getName());
        assertTrue (enrollee.getActivation());
        assertEquals (LocalDate.parse("2020-02-05"), enrollee.getDob());

        // when
        Enrollee change = new Enrollee("good_name", false, LocalDate.parse("1999-12-25"));
        enrolleeService.update(5L, change);

        // then
        enrollee = enrolleeService.read(5L);
        assertEquals ("good_name", enrollee.getName());
        assertFalse (enrollee.getActivation());
        assertEquals (LocalDate.parse("1999-12-25"), enrollee.getDob());
    }

    /** Deleting an enrollee will also remove all associated dependents.
     *
     * @throws Exception
     */
    @Test
    @DatabaseSetup("createDependent.xml")
    void testDelete() throws Exception {
        // given
        List<Dependent> dependentsPrior = dependentService.findByEnrolleeId(3L);
        assertEquals(3, dependentsPrior.size());
        Enrollee enrollee = enrolleeService.read(3L);
        assertNotNull(enrollee);

        // when
        enrolleeService.delete(3L);

        // then
        List<Dependent> dependentsThen = dependentService.findByEnrolleeId(3L);
        assertTrue (dependentsThen.isEmpty());
        Exception exception = assertThrows (EnrolleeNotFoundException.class, () -> {
            enrolleeService.read(3L);
        });
    }
}