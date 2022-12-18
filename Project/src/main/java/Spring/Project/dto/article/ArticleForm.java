package Spring.Project.dto.article;


import Spring.Project.entity.article.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleForm {


    private Long id;
    private String title;
    private String content;

}
