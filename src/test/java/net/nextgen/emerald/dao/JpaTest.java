package net.nextgen.emerald.dao;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

@DataJpaTest
public class JpaTest {
    @Inject private DataSource dataSource;
    @Inject private JdbcTemplate jdbcTemplate;
    @Inject private EntityManager entityManager;
    @Inject private EnrolleeRepository enrolleeRepository;
    @Inject private DependentRepository dependentRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(enrolleeRepository).isNotNull();
        assertThat(dependentRepository).isNotNull();
    }
}
