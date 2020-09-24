package net.nextgen.emerald.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;
import java.util.List;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import net.nextgen.emerald.vo.Dependent;

/** use DBUnit Integration for the Spring Testing Framework
 *
 */
@DataJpaTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class DependentRepositoryTest {

    @Inject
    private DependentRepository dependentRepository;

    @Test
    @DatabaseSetup("createEnrollee.xml")
    void testDeleteByEnrolleeId() {

        // Dependent in DB
        long count = dependentRepository.count();
        assertEquals(9, count);

        // Dependent associated with a specific Enrollee
        List<Dependent> dependents = dependentRepository.findByEnrolleeId(2);
        assertEquals(3, dependents.size());

        // Delete Dependent associated with a Enrollee
        dependentRepository.deleteByEnrolleeId(2);
        List<Dependent> left = dependentRepository.findByEnrolleeId(2);
        assertEquals(0, left.size());
    }
}
