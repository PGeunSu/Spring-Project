package Spring.Project.entity.article;

import Spring.Project.dto.article.ArticleForm;
import Spring.Project.dto.article.ArticleFormDto;
import Spring.Project.dto.member.MemberFormDto;
import Spring.Project.entity.Member;
import Spring.Project.entity.base.BaseEntity;
import Spring.Project.status.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "article")
@AllArgsConstructor
@NoArgsConstructor
public class Article extends BaseEntity {



    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "articleId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column
    private String title;
    @Column
    private String content;



    public void patch(Article article) {
        if(article.title != null){
            this.title = article.title;
        }
        if(article.content != null){
            this.content = article.content;
        }
    }

    public static Article createArticle(ArticleForm articleForm){
        Article article = new Article();
        article.setTitle(articleForm.getTitle());
        article.setContent(articleForm.getContent());
        return article;
    }


    public void updateArticle(ArticleFormDto articleFormDto) {
        this.title = articleFormDto.getTitle();
        this.content = articleFormDto.getContent();
    }
}
