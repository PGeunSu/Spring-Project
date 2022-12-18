package Spring.Project.controller;

import Spring.Project.dto.article.ArticleForm;
import Spring.Project.dto.article.CommentDto;
import Spring.Project.entity.article.Article;
import Spring.Project.repository.ArticleRepository;
import Spring.Project.service.ArticleService;
import Spring.Project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/article")
@Controller
@RequiredArgsConstructor
public class ArticleController {

     private ArticleRepository articleRepository;
     private ArticleService articleService;

     private CommentService commentService;

     @GetMapping("/new")
    public String newArticleForm(Model model){
         model.addAttribute("articleForm",new ArticleForm());
         return "article/new";
     }

     @PostMapping("/new")
    public String createArticle(ArticleForm form){

         Article article = Article.createArticle(form);
         articleService.saveArticle(article);

         return "redirect:/articles/" + article.getId();
     }

     @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model){
         Article articleEntity = articleRepository.findById(id).orElse(null);
         List<CommentDto> commentDtos = commentService.comments(id);
         //모델 등록
         model.addAttribute("article", articleEntity);
         model.addAttribute("commentDtos",commentDtos);

         return "article/show";
     }

     @GetMapping("/")
    public String index(Model model){
         List<Article> articleEntiyList = articleRepository.findAll();

         model.addAttribute("articleList",articleEntiyList);

         return "article/index";
     }

     @GetMapping("/{id}/edit")
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

     @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
         Article target = articleRepository.findById(id).orElse(null);

         if(target != null){
             articleRepository.delete(target);
             rttr.addFlashAttribute("msg","삭제가 완료되었습니다.");
         }
         return "redirect:/articles";
     }

}
