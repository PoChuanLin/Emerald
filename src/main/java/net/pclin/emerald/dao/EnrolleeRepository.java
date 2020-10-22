package net.pclin.emerald.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import net.pclin.emerald.vo.Enrollee;

/** Repository for Enrollee objects
    For method details, refer to
    https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
 */
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
}
