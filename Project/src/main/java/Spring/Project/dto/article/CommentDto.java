package Spring.Project.dto.article;
import Spring.Project.entity.article.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {


    private Long id;

    @JsonProperty("articleId")
    private Long articleId;
    private String nickname;
    private String body;

    public static CommentDto createCommentDto(Comment comment){

        return new CommentDto(
            comment.getId(),
            comment.getArticle().getId(),
            comment.getNickname(),
            comment.getBody()
        );
    }
}
