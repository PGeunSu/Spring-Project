package Spring.Project.controller;

import Spring.Project.dto.health.HealthDto;
import Spring.Project.dto.health.HealthFormDto;
import Spring.Project.dto.health.HealthSearchDto;
import Spring.Project.entity.health.Health;
import Spring.Project.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/health")
public class healthController {

    private final HealthService healthService;

    @GetMapping("/new")
    public String healthForm(Model model){
        model.addAttribute("healthFormDto", new HealthFormDto());
        return "health/healthForm";
    }

    @PostMapping("/new")
    public String newHealth(@Valid HealthFormDto healthFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "health/healthForm";
        }

        try{
            healthService.saveHealth(healthFormDto);
        }catch (Exception e){
            model.addAttribute("errorMessage","상품 등록 중 에러가 발생하였습니다.");
            return "health/healthForm";
        }
        return "redirect:/";
    }

    @GetMapping("/{healthId}")
    public String getHealthIn(@PathVariable("healthId") Long healthId, Model model){
        try{
            HealthFormDto healthFormDto = healthService.getHealthId(healthId);
            model.addAttribute("healthFormDto" , healthFormDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 식품입니다.");
            model.addAttribute("healthFormDto", new HealthFormDto());
            return "health/healthForm";
        }
        return "health/healthForm";
    }

    @PostMapping("/{healthId}")
    public String healthUpdate(@Valid HealthFormDto healthFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "health/healthForm";
        }
        try{
            healthService.updateHealth(healthFormDto);
        }catch(Exception e){
            model.addAttribute("errorMessage","식단 수정 중 에러가 발생하였습니다.");
            return "health/healthForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = {"/healthList","healthList/{page}"})
    public String pageable(HealthSearchDto healthSearchDto, @PathVariable("page")Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 7);
        Page<Health> health = healthService.getHealthListPage(healthSearchDto,pageable);

        model.addAttribute("health",health);
        model.addAttribute("healthSearchDto", healthSearchDto);
        model.addAttribute("maxPage",10);

        return "health/healthList";
    }
}
