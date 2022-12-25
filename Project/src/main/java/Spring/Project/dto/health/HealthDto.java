package Spring.Project.dto.health;

import Spring.Project.status.HealthStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
public class HealthDto {

    private Long id;
    private String foodNm; //식품명
    private int carbohydrate; //탄수화물
    private int protein; //단백질
    private int fat; //지방
    private int calorie; //칼로리
    private HealthStatus healthStatus;
}
