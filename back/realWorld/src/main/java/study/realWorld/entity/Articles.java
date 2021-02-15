package study.realWorld.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "USER_ID") //외래키 얘가 주인
    private User user;

    @Builder
    public Articles(String title, String slug, String description, String body, User user) {
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.body = body;
        this.user = user;
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
