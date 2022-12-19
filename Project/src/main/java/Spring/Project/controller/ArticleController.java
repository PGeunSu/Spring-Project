package Spring.Project.controller;

import Spring.Project.dto.article.ArticleForm;
import Spring.Project.dto.article.ArticleFormDto;
import Spring.Project.dto.article.CommentDto;
import Spring.Project.entity.Member;
import Spring.Project.entity.article.Article;
import Spring.Project.repository.ArticleRepository;
import Spring.Project.service.ArticleService;
import Spring.Project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/article")
@Controller
@RequiredArgsConstructor
public class ArticleController {

     private final ArticleRepository articleRepository;

     private final ArticleService articleService;

     private final CommentService commentService;

     @GetMapping("/new")
    public String newArticleForm(Model model){
         model.addAttribute("articleFormDto",new ArticleFormDto());
         return "article/new";
     }

     @PostMapping("/new")
    public String createArticle(@Valid ArticleFormDto articleFormDto, BindingResult bindingResult, Model model){

         if(bindingResult.hasErrors()){
             return "article/new";
         }
         articleService.saveArticle(articleFormDto);
//         try{
//            articleService.saveArticle(articleFormDto);
//         }catch (Exception e){
//             model.addAttribute("errorMessage","에러 발생");
//             return "article/new";
//         }

         return "redirect:/article/" ;
     }

     @GetMapping("/{articleId}")
    public String show(@PathVariable("articleId") Long id, Model model){

         try{
             Article articleEntity = articleRepository.findById(id).orElse(null);
             //List<CommentDto> commentDtos = commentService.comments(id);
             model.addAttribute("articleForm", articleEntity);
             //model.addAttribute("commentDtos",commentDtos);
         }catch(Exception e){
             model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
             model.addAttribute("article", new ArticleFormDto());
         }

         return "article/show";
     }

     @GetMapping("/")
    public String index(Model model){
         List<Article> articleEntiyList = articleRepository.findAll();

         model.addAttribute("articleList",articleEntiyList);

         return "article/index";
     }

     @GetMapping("/{articleId}/edit")
    public String edit(@PathVariable Long id, Model model){
         Article articleEntity = articleRepository.findById(id).orElse(null);
         model.addAttribute("article",articleEntity);

         return "article/edit";
     }

     @PostMapping("/update")
    public String update(ArticleForm form){
         Article articleEntity = new Article();
         Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

         if(target != null){ //기존 데이터가 있다면, 값을 갱신
             articleRepository.save(articleEntity);
         }
         return "redirect:/articles/" + articleEntity.getId();
     }

     @GetMapping("/{articleId}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
         Article target = articleRepository.findById(id).orElse(null);

         if(target != null){
             articleRepository.delete(target);
             rttr.addFlashAttribute("msg","삭제가 완료되었습니다.");
         }
         return "redirect:/articles";
     }

}
