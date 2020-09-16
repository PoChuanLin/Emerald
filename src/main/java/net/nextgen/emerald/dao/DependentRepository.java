package net.nextgen.emerald.dao;

import net.nextgen.emerald.vo.Dependent;
import net.nextgen.emerald.vo.Enrollee;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public interface DependentRepository extends JpaRepository<Dependent, Long> {

    List<Dependent> findByEnrolleeId(long id);

    List<Dependent> deleteByEnrollee(Enrollee enrollee);
    List<Dependent> deleteByEnrolleeId(long id);
}
