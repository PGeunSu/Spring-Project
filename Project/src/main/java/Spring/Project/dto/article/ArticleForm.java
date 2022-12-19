package Spring.Project.dto.article;


import Spring.Project.entity.article.Article;
import Spring.Project.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ArticleForm{


    private Long id;
    private String title;
    private String content;



}
