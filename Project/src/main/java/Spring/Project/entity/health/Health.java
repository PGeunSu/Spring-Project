package Spring.Project.entity.health;

import Spring.Project.dto.health.HealthFormDto;
import Spring.Project.entity.base.BaseEntity;
import Spring.Project.status.HealthStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "health")
public class Health extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "healthId")
    private Long id;
    private String foodNm; //식품명
    private int carbohydrate; //탄수화물
    private int protein; //단백질
    private int fat; //지방
    private int calorie; //칼로리

    //구성원이 탄수화물, 단백질, 지방인지 구분하기 위한 수단
    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    public void updateHealth(HealthFormDto healthFormDto){
        this.foodNm = healthFormDto.getFoodNm();
        this.carbohydrate = healthFormDto.getCarbohydrate();
        this.protein = healthFormDto.getProtein();
        this.fat = healthFormDto.getFat();
        this.calorie = healthFormDto.getCalorie();
    }







}
