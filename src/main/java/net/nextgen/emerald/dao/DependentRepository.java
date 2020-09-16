package net.nextgen.emerald.dao;

import net.nextgen.emerald.vo.Dependent;
import net.nextgen.emerald.vo.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DependentRepository extends JpaRepository<Dependent, Long> {

    List<Dependent> deleteByEnrollee(Enrollee enrollee);
}
