package Spring.Project.dto.health;

import Spring.Project.entity.health.Health;
import Spring.Project.status.HealthStatus;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter @Setter
public class HealthFormDto {

    private Long id;

    @NotBlank(message = "식품명을 입력해주세요")
    private String foodNm; //식품명

    @NotNull(message = "탄수화물 함량을 입력해주세요 ")
    @PositiveOrZero(message = "0이하로는 입력할 수 없습니다.")
    private Integer carbohydrate; //탄수화물
    @NotNull(message = "단백질 함량을 입력해주세요 ")
    @PositiveOrZero(message = "0이하로는 입력할 수 없습니다.")
    private Integer protein; //단백질
    @NotNull(message = "지방 함량을 입력해주세요 ")
    @PositiveOrZero(message = "0이하로는 입력할 수 없습니다.")
    private Integer fat; //지방
    @NotNull(message = "총 칼로리를 입력해주세요 ")
    @PositiveOrZero(message = "0이하로는 입력할 수 없습니다.")
    private Integer calorie; //칼로리
    private HealthStatus healthStatus;

    private static ModelMapper modelMapper = new ModelMapper();

    public Health createHealth(){
        return modelMapper.map(this, Health.class);
    }

    public static HealthFormDto of(Health health){
        return modelMapper.map(health, HealthFormDto.class);
    }

}
