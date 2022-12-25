package Spring.Project.dto.health;

import Spring.Project.status.HealthStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HealthSearchDto {

    private HealthStatus healthStatus;
    private String searchBy;
    private String searchQuery = "";
}
