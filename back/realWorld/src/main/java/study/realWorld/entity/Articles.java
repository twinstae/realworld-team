package study.realWorld.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public Articles(String title, String slug, String description, String body) {
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.body = body;
    }
}
