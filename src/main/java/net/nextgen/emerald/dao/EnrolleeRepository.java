package net.nextgen.emerald.dao;

import net.nextgen.emerald.vo.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
}
