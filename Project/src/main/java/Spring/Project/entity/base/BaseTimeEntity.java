package Spring.Project.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass //공통 매핑 정보가 필요할 떄 사용하는 어노테이션 부모 클래스를 상속
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate //엔티티가 생성되어 저장할 때 시간자동 저장
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate //엔티티의 값을 변경할 때 시간을 자동 저장
    private LocalDateTime updateTime;
}
