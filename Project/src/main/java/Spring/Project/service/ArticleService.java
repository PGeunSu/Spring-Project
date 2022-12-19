package Spring.Project.service;

import Spring.Project.dto.article.ArticleForm;
import Spring.Project.dto.article.ArticleFormDto;
import Spring.Project.entity.article.Article;
import Spring.Project.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> index(){
        return articleRepository.findAll();
    }

    public Article show(Long id){
        return articleRepository.findById(id).orElse(null);
    }

    public Long saveArticle(ArticleFormDto articleFormDto){

        Article article = articleFormDto.createArticle();
        articleRepository.save(article);

        return article.getId();
    }

    public Article update(Long id, ArticleForm dto){
        Article article =  new Article();
        Article target = articleRepository.findById(id).orElse(null);

        if (target == null || id != article.getId()) {
            return null;
        }

        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id){
        Article target = articleRepository.findById(id).orElse(null);

        if(target == null){
            return null;
        }
        articleRepository.delete(target);
        return target;
    }



}
