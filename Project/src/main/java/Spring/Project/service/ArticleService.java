package Spring.Project.service;

import Spring.Project.dto.article.ArticleFormDto;
import Spring.Project.entity.article.Article;
import Spring.Project.repository.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> index(){
        return articleRepository.findAll();
    }


    public Long saveArticle(ArticleFormDto articleFormDto){

        Article article = articleFormDto.createArticle();
        articleRepository.save(article);

        return article.getId();
    }


    public Long updateArticle(ArticleFormDto articleFormDto) throws Exception{

        Article article = articleRepository.findById(articleFormDto.getId()).orElseThrow(EntityExistsException::new);
        article.updateArticle(articleFormDto);

        return article.getId();
    }
}
