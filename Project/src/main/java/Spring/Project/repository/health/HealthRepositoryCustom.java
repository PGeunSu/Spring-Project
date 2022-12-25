package Spring.Project.repository.health;

import Spring.Project.dto.health.HealthSearchDto;
import Spring.Project.entity.health.Health;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HealthRepositoryCustom {

    Page<Health> getHealthListPage(HealthSearchDto healthSearchDto, Pageable pageable);
}
