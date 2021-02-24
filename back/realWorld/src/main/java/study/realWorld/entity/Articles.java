package study.realWorld.entity;

import com.sun.istack.NotNull;
import lombok.*;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class Articles extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLES_ID")
    private Long id;

    @NotNull
    @Column(length = 64)
    private String title;

    @NotNull
    @Column(unique = true,length = 256)
    private String slug;

    @NotNull
    @Column(length = 256)
    private String description;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID") //외래키 얘가 주인
    private User author;

    @OneToMany
    private final List<Favorite> favoriteList = new ArrayList<>();

    @Builder
    public Articles(String title, String slug, String description, String body, User author) {
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.body = body;
        this.author = author;
    }

    public void update(ArticleCreateDto updateDto){
        if (!"".equals(slug)){
            this.slug = updateDto.getSlug();
        }
        if (!"".equals(title)) {
            this.title = updateDto.getTitle();
        }
        if (!"".equals(description)) {
            this.description = updateDto.getDescription();
        }
        if (!"".equals(body)) {
            this.body = updateDto.getBody();
        }
    }
}
