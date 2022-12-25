package Spring.Project.repository.health;

import Spring.Project.entity.health.Health;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HealthRepository extends JpaRepository<Health, Long>,
        QuerydslPredicateExecutor<Health>, HealthRepositoryCustom{

}
