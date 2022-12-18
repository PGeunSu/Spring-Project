package Spring.Project.service;

import Spring.Project.dto.article.ArticleForm;
import Spring.Project.entity.article.Article;
import Spring.Project.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private ArticleRepository articleRepository;

    public List<Article> index(){
        return articleRepository.findAll();
    }

    public Article show(Long id){
        return articleRepository.findById(id).orElse(null);
    }

    public Article saveArticle(Article article){

        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
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
