package study.realWorld.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private Profile author;

    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Articles(String title, String slug, String description, String body, Profile author) {
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

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final List<Favorite> favoriteList = new ArrayList<>();

    public void addFavorite(Favorite favorite) {
        this.favoriteList.add(favorite);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
    public void removeComment(Comment comment) {this.comments.remove(comment);}

    public void removeFavorite(Favorite favorite){
        this.favoriteList.remove(favorite);
    }

    public Comment getCommentById(Long id) {
        return
                this.comments.stream().filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new RuntimeException("해당 comment를 찾을 수 없습니다."));
    }


    @Formula("(SELECT COUNT(*) FROM favorite f WHERE f.ARTICLES_ID=ARTICLES_ID)")
    public int favoritesCount;
}
