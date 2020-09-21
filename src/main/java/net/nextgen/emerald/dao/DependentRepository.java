package net.nextgen.emerald.dao;

import java.util.List;

import net.nextgen.emerald.vo.Dependent;
import net.nextgen.emerald.vo.Enrollee;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public interface DependentRepository extends JpaRepository<Dependent, Long> {

    // fetch all Dependents associated with an Enrollee, given the Enrollee's ID.
    List<Dependent> findByEnrolleeId(long id);

    // deleting all Dependents associated with an Enrollee.
    List<Dependent> deleteByEnrollee(Enrollee enrollee);
    // deleting all Dependents associated with an Enrollee, given the Enrollee's ID.
    List<Dependent> deleteByEnrolleeId(long id);
}
