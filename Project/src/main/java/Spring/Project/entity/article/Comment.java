package Spring.Project.entity.article;

import Spring.Project.dto.article.CommentDto;
import lombok.*;

import javax.persistence.*;

@Entity @ToString
@Getter @Setter
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "commentId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId")
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;


    public static Comment createComment(CommentDto dto, Article article) throws IllegalArgumentException{

        //예외 처리
        if(dto.getId() != null){
            throw new IllegalArgumentException("댓글 생성 실패!(아이디가 존재하지 않음)");
        }
        if(dto.getArticleId() != null){
            throw new IllegalArgumentException("댓글 생성 실패!(게시글의 ID가 잘못되었음)");
        }
        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }

    public void patch(CommentDto dto){

        //예외 발생
        if (this.id != dto.getId()) { //url에서 던진 id와 json id가 다른 경우 예외 발생
            throw new IllegalArgumentException("댓글 수정실패! 잘못된 Id가 입력되었습니다");
        }
        //객체를 갱신
        if (dto.getNickname() != null) {
            this.nickname = dto.getNickname();
            //this의 nickname을 json nickname으로 수정
        }
        if (dto.getBody() != null) {
            this.body = dto.getBody();
        }
    }

    }

