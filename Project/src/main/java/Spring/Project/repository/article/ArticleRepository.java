package Spring.Project.repository.article;

import Spring.Project.entity.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    ArrayList<Article> findAll();

}
