package Spring.Project.repository.health;

import Spring.Project.dto.health.HealthSearchDto;
import Spring.Project.entity.health.Health;
import Spring.Project.entity.health.QHealth;
import Spring.Project.status.HealthStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class HealthRepositoryCustomImpl implements HealthRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public HealthRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchStatusEq(HealthStatus healthStatus) {
        return healthStatus == null ? null : QHealth.health.healthStatus.eq(healthStatus);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if (StringUtils.equals("foodNm", searchBy)) {
            return QHealth.health.foodNm.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Health> getHealthListPage(HealthSearchDto healthSearchDto, Pageable pageable){

        List<Health> content = queryFactory
                .selectFrom(QHealth.health)
                .where(searchStatusEq(healthSearchDto.getHealthStatus()), // ',' 는 and 조건으로 인식
                        searchByLike(healthSearchDto.getSearchBy(),
                                healthSearchDto.getSearchQuery()))
                .orderBy(QHealth.health.id.desc())
                .offset(pageable.getOffset()) // 데이터를 가져올 시작 인덱스 지정
                .limit(pageable.getPageSize()) // 한번에 가져올 최대 개수
                .fetch();

        long total = queryFactory.select(Wildcard.count).from(QHealth.health)
                .where(searchStatusEq(healthSearchDto.getHealthStatus()),
                        searchByLike(healthSearchDto.getSearchBy(), healthSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }




}
