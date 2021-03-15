package study.realWorld.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    private String body;

    @ManyToOne
    @JoinColumn(name ="ARTICLE_ID")
    private Articles articles;

    @ManyToOne
    @JoinColumn(name ="AUTHOR_ID")
    private Profile author;

    @Builder
    public Comment(String body, Articles articles, Profile author) {
        this.body = body;
        this.articles = articles;
        this.author = author;
        author.addComment(this);
        articles.addComment(this);
    }

    public void delete(){
        this.articles.removeComment(this);
        this.author.removeComment(this);
    }
}
