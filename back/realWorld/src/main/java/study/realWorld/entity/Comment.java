package study.realWorld.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name ="PROFILE_ID")
    private Profile profile;

    @Builder
    public Comment(String body, Articles articles, Profile profile) {
        this.body = body;
        this.articles = articles;
        this.profile = profile;
    }
}
