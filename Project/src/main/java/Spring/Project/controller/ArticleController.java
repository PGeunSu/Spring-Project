package Spring.Project.controller;

import Spring.Project.dto.article.ArticleFormDto;
import Spring.Project.entity.article.Article;
import Spring.Project.repository.article.ArticleRepository;
import Spring.Project.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/article")
@Controller
@RequiredArgsConstructor
public class ArticleController {

     private final ArticleRepository articleRepository;

     private final ArticleService articleService;


     @GetMapping("/new")
    public String newArticleForm(Model model){
         model.addAttribute("articleFormDto",new ArticleFormDto());
         return "article/articleForm";
     }

     @PostMapping("/new")
    public String createArticle(@Valid ArticleFormDto articleFormDto, BindingResult bindingResult, Model model){

         if(bindingResult.hasErrors()){
             return "article/articleForm";
         }
         try{
            articleService.saveArticle(articleFormDto);
         }catch (Exception e){
             model.addAttribute("errorMessage","에러 발생");
             return "article/articleForm";
         }

         return "redirect:/article/index" ;
     }

     @GetMapping("/{articleId}")
    public String show(@PathVariable("articleId") Long id, Model model){

         try{
             Article articleEntity = articleRepository.findById(id).orElse(null);
             model.addAttribute("articleForm", articleEntity);
             System.out.println(id);
         }catch(Exception e){
             model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
             model.addAttribute("articleForm", new ArticleFormDto());
         }

         return "article/show";
     }

     @GetMapping("/index")
    public String index(Model model){
         List<Article> articleEntiyList = articleRepository.findAll();

         model.addAttribute("articleList",articleEntiyList);

         return "article/index";
     }
     @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){

         try{
             Article articleEntity = articleRepository.findById(id).orElse(null);
             model.addAttribute("articleFormDto",articleEntity);
         }catch (Exception e){
             model.addAttribute("errorMessage","에러발생");
             model.addAttribute("articleFormDto", new ArticleFormDto());
             return "article/articleForm";
         }

         return "article/articleForm";
     }

     @PostMapping("/{id}/edit")
    public String update(@Valid ArticleFormDto articleFormDto, BindingResult bindingResult, Model model){

         if(bindingResult.hasErrors()){
             return "article/articleForm";
         }
         try{
             articleService.updateArticle(articleFormDto);
         }catch (Exception e){
             model.addAttribute("errorMessage","게시물 수정 중 오류가 발생했습니다.");
             return "article/articleForm";
         }
         return "redirect:/";
     }

     @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
         Article target = articleRepository.findById(id).orElse(null);

         if(target != null){
             articleRepository.delete(target);
         }
         return "redirect:/article/index";
     }

}
