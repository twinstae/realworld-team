package study.realWorld.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Favorite {

    @Id
    @GeneratedValue
    @Column(name = "FAVORITE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="PROFILE_ID")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name ="ARTICLES_ID")
    private Articles article;
}
