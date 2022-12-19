package Spring.Project.dto.article;

import Spring.Project.entity.article.Article;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ArticleFormDto {

    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private static ModelMapper modelMapper = new ModelMapper();

    public Article createArticle(){
        return modelMapper.map(this, Article.class);
    }
    public static ArticleForm of(Article article){
        return modelMapper.map(article, ArticleForm.class);
    }
}
