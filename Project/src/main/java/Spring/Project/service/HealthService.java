package Spring.Project.service;

import Spring.Project.dto.health.HealthFormDto;
import Spring.Project.dto.health.HealthSearchDto;
import Spring.Project.entity.health.Health;
import Spring.Project.repository.health.HealthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class HealthService {

    private final HealthRepository healthRepository;

    public Long saveHealth(HealthFormDto healthFormDto) throws Exception{

        //식단 등록
        Health health = healthFormDto.createHealth();;
        healthRepository.save(health);

        return health.getId();
    }

    @Transactional(readOnly = true)
    public HealthFormDto getHealthId(Long healthId){

        Health health = healthRepository.findById(healthId).orElseThrow(EntityNotFoundException::new);
        HealthFormDto healthFormDto = HealthFormDto.of(health);

        return healthFormDto;
    }

    public Long updateHealth(HealthFormDto healthFormDto) throws Exception{

        //식단 수정
        Health health = healthRepository.findById(healthFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        health.updateHealth(healthFormDto);

        return health.getId();
    }

    @Transactional(readOnly = true)
    public Page<Health> getHealthListPage(HealthSearchDto healthSearchDto, Pageable pageable){
        return healthRepository.getHealthListPage(healthSearchDto, pageable);
    }



}
